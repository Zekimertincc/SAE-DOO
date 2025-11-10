package fr.iut.groupe.junglequest.controleur;

import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.modele.factory.MondeFactory;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;
import fr.iut.groupe.junglequest.modele.personnages.*;
import fr.iut.groupe.junglequest.modele.item.farm.RessourceModele;
import fr.iut.groupe.junglequest.vue.*;
import fr.iut.groupe.junglequest.vue.personnages.VueJoueur;
import fr.iut.groupe.junglequest.vue.personnages.VueLoup;
import fr.iut.groupe.junglequest.vue.utilitaire.BarreVie;
import fr.iut.groupe.junglequest.vue.utilitaire.ExtracteurSprites;
import fr.iut.groupe.junglequest.vue.animation.AnimationManager;
import fr.iut.groupe.junglequest.vue.animation.GestionAnimation;
import fr.iut.groupe.junglequest.controleur.interfacefx.InventaireController;
import fr.iut.groupe.junglequest.controleur.moteur.MoteurPhysique;
import fr.iut.groupe.junglequest.controleur.commandes.*;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contrôleur principal de la vue du jeu (Controller - MVC)
 * 
 * Architecture MVC avec JavaFX:
 * - Utilise @FXML pour l'injection automatique des composants UI
 * - Le contrôleur connaît le Model (Environnement) et la Vue (Pane racine + composants)
 * - Le contrôleur coordonne les interactions entre Model et View
 * - Le contrôleur NE contient PAS de logique métier (déléguée au Model)
 * 
 * Flux d'initialisation (RÈGLE D'OR):
 * 1. Le contrôleur crée l'instance envModele (via Factory)
 * 2. Le envModele crée le terrain, le joueur et les entités
 * 3. Le contrôleur crée l'envVue (éléments visuels)
 * 4. La envVue charge les images via bindings/observers sur envModele
 * 5. La envVue récupère les entités du modèle et crée leurs vues
 * 
 * Design Patterns:
 * - Observer Pattern: Le contrôleur observe les changements du modèle
 * - Command Pattern: Actions utilisateur encapsulées en commandes
 * - MVC Pattern: Séparation claire Model-View-Controller
 */
public class VueJeuController implements Initializable, Observateur {
    
    // ========== Composants FXML (injection automatique) ==========
    
    @FXML
    private Pane racine;
    
    @FXML
    private Pane pauseOverlay;
    
    // ========== Model ==========
    
    private Environnement environnement;
    
    // ========== View ==========
    
    private CarteAffichable carteAffichable;
    private VueBackground vueBackground;
    private VueJoueur vueJoueur;
    private VueLoup vueLoup;
    private List<VueRessource> vueRessources;
    private InventaireController inventaireController;
    
    // Barres de vie
    private BarreVie barreVieJoueur;
    private Label labelVieJoueur;
    private BarreVie barreVieLoup;
    private Label labelVieLoup;
    
    // Sprites des NPCs
    private ImageView spriteGuide;
    private ImageView spriteForgeron;
    
    // ========== Controller ==========
    
    private GestionClavier clavier;
    private AnimationTimer gameLoop;
    private AnimationManager animationManager;
    private GestionAnimation gestionAnimation; // Animation state manager (must persist between frames)
    private MoteurPhysique moteurPhysique;
    
    // Propriétés de caméra pour le scrolling
    private final DoubleProperty offsetXProperty = new SimpleDoubleProperty();
    private final DoubleProperty offsetYProperty = new SimpleDoubleProperty();
    
    // État du jeu
    private boolean enPause = false;
    private boolean joueurMort = false;
    private boolean loupMort = false;
    private double largeurEcran;
    private double hauteurEcran;
    
    // Compteurs pour animations et timers
    private int compteurAttaque = 0;
    private int frameMort = 0;
    private int framesAtterrissageRestants = 0;
    private int delaiDegats = 0;
    private boolean toucheAccroupi = false;
    
    /**
     * Initialisation du contrôleur (appelée automatiquement par JavaFX après @FXML)
     * 
     * RÈGLE D'OR MVC:
     * 1. Créer EnvModele (Model)
     * 2. EnvModele crée terrain + entités
     * 3. Créer EnvVue (View) en écoutant Model
     * 4. EnvVue charge images et crée vues des entités
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("===== Initialisation VueJeuController =====");
        
        // Récupération des dimensions
        // Note: Les dimensions seront mises à jour dans initializationComplete()
        this.vueRessources = new ArrayList<>();
    }
    
    /**
     * Initialisation complète une fois la scène disponible.
     * Cette méthode est appelée par LanceurJeu après le chargement FXML.
     * 
     * @param largeur Largeur de l'écran
     * @param hauteur Hauteur de l'écran
     */
    public void initializationComplete(double largeur, double hauteur) {
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
        
        try {
            // ===== ÉTAPE 1: Créer EnvModele =====
            System.out.println("Étape 1: Création du modèle (Environnement)");
            this.environnement = MondeFactory.creerMonde();
            
            // Observer Pattern: S'abonner aux changements du modèle
            environnement.ajouterObservateur(this);
            
            // ===== ÉTAPE 2: Créer EnvVue =====
            System.out.println("Étape 2: Création de la vue");
            creerVue();
            
            // ===== ÉTAPE 3: Initialiser les gestionnaires =====
            System.out.println("Étape 3: Initialisation des gestionnaires");
            initialiserGestionnaires();
            
            // ===== ÉTAPE 4: Démarrer la boucle de jeu =====
            System.out.println("Étape 4: Démarrage de la boucle de jeu");
            demarrerBoucleJeu();
            
            System.out.println("===== Initialisation terminée =====");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Crée tous les éléments visuels (EnvVue).
     * La vue récupère les données du modèle et crée les composants graphiques.
     */
    private void creerVue() throws IOException {
        // 1. Chargement des images du terrain via le modèle
        InputStream tilesetStream = getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/tileset_jungle.png");
        if (tilesetStream == null) {
            throw new IOException("Tileset introuvable");
        }
        Image tileset = new Image(tilesetStream);
        
        // 2. Création de la carte affichable
        carteAffichable = new CarteAffichable(
            environnement.getCarte(), 
            tileset, 
            (int) largeurEcran, 
            (int) hauteurEcran
        );
        
        // 3. Création du background
        int largeurCartePx = environnement.getCarte().getLargeur() * ConstantesJeu.TAILLE_TUILE;
        vueBackground = new VueBackground((int) largeurEcran, (int) hauteurEcran, largeurCartePx);
        
        // 4. Ajout du background et de la carte
        racine.getChildren().addAll(vueBackground, carteAffichable);
        
        // 5. Création de la vue du joueur via binding avec le modèle
        creerVueJoueur();
        
        // 6. Création des vues des NPCs via binding avec le modèle
        creerVueNPCs();
        
        // 7. Création de la vue du loup via binding avec le modèle
        creerVueLoup();
        
        // 8. Création des vues des ressources via binding avec le modèle
        creerVueRessources();
        
        // 9. Création des barres de vie
        creerBarresVie();
        
        // 10. Création de l'interface d'inventaire
        creerInterfaceInventaire();
        
        System.out.println("Vue créée avec succès");
    }
    
    /**
     * Crée la vue du joueur avec ses animations
     */
    private void creerVueJoueur() throws IOException {
        InputStream personnageStream = getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/sprite1.png");
        if (personnageStream == null) {
            throw new IOException("Sprite joueur introuvable");
        }
        Image personnage = new Image(personnageStream);
        WritableImage[] idle = ExtracteurSprites.idle(personnage);
        
        ImageView spriteJoueur = new ImageView(idle[0]);
        spriteJoueur.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
        spriteJoueur.setFitHeight(ConstantesJeu.TAILLE_SPRITE);
        
        vueJoueur = new VueJoueur(environnement.getJoueur(), spriteJoueur);
        vueJoueur.lierPosition(offsetXProperty, offsetYProperty);
        
        racine.getChildren().add(spriteJoueur);
        System.out.println("Vue joueur créée");
    }
    
    /**
     * Crée les vues des NPCs (Guide, Forgeron)
     * 
     * Architecture MVC:
     * - Les NPCs sont créés dans le Model (Environnement)
     * - Les sprites sont créés ici dans la Vue
     * - Les sprites suivent la position des NPCs du Model
     */
    private void creerVueNPCs() throws IOException {
        // Guide
        Guide guide = environnement.getGuide();
        Image imgGuide = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/guide.png"));
        WritableImage[] framesGuide = ExtracteurSprites.extraireLigne(
            imgGuide,
            ConstantesJeu.NB_FRAMES_GUIDE,
            ConstantesJeu.LARGEUR_GUIDE,
            ConstantesJeu.HAUTEUR_GUIDE);
        
        spriteGuide = new ImageView(framesGuide[0]);
        spriteGuide.setFitWidth(ConstantesJeu.LARGEUR_GUIDE);
        spriteGuide.setFitHeight(ConstantesJeu.HAUTEUR_GUIDE);
        // Position initiale basée sur le modèle
        spriteGuide.setX(guide.getX());
        spriteGuide.setY(guide.getY());
        racine.getChildren().add(spriteGuide);
        
        // Animation du guide
        final int[] indexGuide = {0};
        Timeline animGuide = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            spriteGuide.setImage(framesGuide[indexGuide[0]]);
            indexGuide[0] = (indexGuide[0] + 1) % framesGuide.length;
        }));
        animGuide.setCycleCount(Animation.INDEFINITE);
        animGuide.play();
        
        // Forgeron
        Forgeron forgeron = environnement.getForgeron();
        Image imgForgeron = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/forgeron.png"));
        WritableImage[] framesForgeron = ExtracteurSprites.extraireLigne(
            imgForgeron,
            ConstantesJeu.NB_FRAMES_FORGERON,
            ConstantesJeu.LARGEUR_FORGERON,
            ConstantesJeu.HAUTEUR_FORGERON);
        
        spriteForgeron = new ImageView(framesForgeron[0]);
        spriteForgeron.setFitWidth(ConstantesJeu.LARGEUR_FORGERON);
        spriteForgeron.setFitHeight(ConstantesJeu.HAUTEUR_FORGERON);
        // Position initiale basée sur le modèle
        spriteForgeron.setX(forgeron.getX());
        spriteForgeron.setY(forgeron.getY());
        racine.getChildren().add(spriteForgeron);
        
        // Animation du forgeron
        final int[] indexForgeron = {0};
        Timeline animForgeron = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            spriteForgeron.setImage(framesForgeron[indexForgeron[0]]);
            indexForgeron[0] = (indexForgeron[0] + 1) % framesForgeron.length;
        }));
        animForgeron.setCycleCount(Animation.INDEFINITE);
        animForgeron.play();
        
        System.out.println("Vues NPCs créées");
    }
    
    /**
     * Crée la vue du loup
     */
    private void creerVueLoup() throws IOException {
        Image imgLoupWalk = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/black_wolf_walk.png"));
        Image imgLoupRun = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/black_wolf_run.png"));
        Image imgLoupAttack = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/black_wolf_attack.png"));
        
        ImageView spriteLoup = new ImageView(imgLoupWalk);
        spriteLoup.setFitWidth(imgLoupWalk.getWidth());
        spriteLoup.setFitHeight(imgLoupWalk.getHeight());
        
        vueLoup = new VueLoup(environnement.getLoup(), spriteLoup, imgLoupWalk, imgLoupRun, imgLoupAttack);
        vueLoup.lierPosition(offsetXProperty, offsetYProperty);
        
        racine.getChildren().add(spriteLoup);
        System.out.println("Vue loup créée");
    }
    
    /**
     * Crée les vues des ressources via binding avec le modèle
     */
    private void creerVueRessources() throws IOException {
        // Chargement des images des ressources
        Image arbreImg = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/arbre.png"));
        Image canneImg = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/canne.png"));
        Image rocheImg = new Image(getClass().getResourceAsStream(
            "/fr/iut/groupe/junglequest/images/roche.png"));
        
        // Création des vues pour chaque ressource du modèle
        for (RessourceModele ressourceModele : environnement.getRessources()) {
            Image img = switch (ressourceModele.getNom().toLowerCase()) {
                case "arbre" -> arbreImg;
                case "canne" -> canneImg;
                case "roche" -> rocheImg;
                default -> arbreImg;
            };
            
            VueRessource vueRessource = new VueRessource(ressourceModele, img);
            vueRessource.lierPosition(offsetXProperty, offsetYProperty);
            vueRessources.add(vueRessource);
            racine.getChildren().add(vueRessource.getSprite());
        }
        
        System.out.println("Vues ressources créées: " + vueRessources.size());
    }
    
    /**
     * Crée les barres de vie avec binding sur les propriétés du modèle
     */
    private void creerBarresVie() {
        // Barre de vie du joueur
        barreVieJoueur = new BarreVie(ConstantesJeu.TAILLE_SPRITE * 1.5, 4);
        barreVieJoueur.setViewOrder(-9);
        barreVieJoueur.ratioProperty().bind(
            environnement.getJoueur().pointsDeVieProperty()
                .divide((double) ConstantesJeu.VIE_MAX_JOUEUR)
        );
        
        labelVieJoueur = new Label();
        labelVieJoueur.textProperty().bind(
            environnement.getJoueur().pointsDeVieProperty().asString()
        );
        labelVieJoueur.setTextFill(javafx.scene.paint.Color.WHITE);
        labelVieJoueur.setViewOrder(-9);
        
        // Barre de vie du loup
        barreVieLoup = new BarreVie(ConstantesJeu.TAILLE_SPRITE * 1.5, 4);
        barreVieLoup.setViewOrder(-9);
        barreVieLoup.ratioProperty().bind(
            environnement.getLoup().pointsDeVieProperty()
                .divide((double) ConstantesJeu.VIE_MAX_LOUP)
        );
        
        labelVieLoup = new Label();
        labelVieLoup.textProperty().bind(
            environnement.getLoup().pointsDeVieProperty().asString()
        );
        labelVieLoup.setTextFill(javafx.scene.paint.Color.WHITE);
        labelVieLoup.setViewOrder(-9);
        
        racine.getChildren().addAll(
            barreVieJoueur, labelVieJoueur,
            barreVieLoup, labelVieLoup
        );
        
        System.out.println("Barres de vie créées avec bindings");
    }
    
    /**
     * Crée l'interface d'inventaire
     */
    private void creerInterfaceInventaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fr/iut/groupe/junglequest/vue/interface/Inventaire.fxml"));
            Node inventaireUI = loader.load();
            inventaireController = loader.getController();
            inventaireController.setInventaire(environnement.getJoueur().getInventaire());
            
            inventaireUI.setLayoutX(10);
            inventaireUI.setLayoutY(10);
            inventaireUI.setViewOrder(-10);
            
            racine.getChildren().add(inventaireUI);
            System.out.println("Interface d'inventaire créée");
        } catch (IOException e) {
            System.err.println("Erreur chargement inventaire: " + e.getMessage());
        }
    }
    
    /**
     * Initialise les gestionnaires (animations, physique, clavier, événements).
     * Remplace l'ancien ControleurJeu avec son constructeur massif.
     */
    private void initialiserGestionnaires() throws IOException {
        // 1. Gestionnaire d'animations (remplace le passage de 13 tableaux!)
        animationManager = AnimationManager.create("/fr/iut/groupe/junglequest/images/sprite1.png");
        
        // 2. Création de l'objet de gestion d'animations (IMPORTANT: créé une seule fois!)
        gestionAnimation = new GestionAnimation(
            animationManager.getIdle(),
            animationManager.getMarche(),
            animationManager.getAttaque(),
            animationManager.getPreparationSaut(),
            animationManager.getVolSaut(),
            animationManager.getSautReload(),
            animationManager.getChute(),
            animationManager.getAtterrissage(),
            animationManager.getDegats(),
            animationManager.getMort(),
            animationManager.getSort(),
            animationManager.getAccroupi(),
            animationManager.getBouclier()
        );
        
        // 3. Moteur physique
        moteurPhysique = new MoteurPhysique();
        
        // 4. Gestionnaire clavier
        clavier = new GestionClavier(racine.getScene());
        
        // 5. Événements de la scène
        configurerEvenementsScene();
        
        System.out.println("Gestionnaires initialisés (Animation, Physique, Clavier)");
    }
    
    /**
     * Configure les événements de la scène (souris, clavier).
     */
    private void configurerEvenementsScene() {
        Scene scene = racine.getScene();
        
        // Clic gauche → attaque / récolte ressource
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY && !enPause) {
                double xMonde = e.getX() + offsetXProperty.get();
                double yMonde = e.getY() + offsetYProperty.get();
                
                // Interaction avec ressources
                for (VueRessource vr : vueRessources) {
                    if (vr.contient(xMonde, yMonde) && !vr.getModele().estRecoltee()) {
                        // Récolter la ressource directement via le modèle
                        if (vr.getModele().interagir(environnement.getJoueur())) {
                            vr.mettreAJour(); // Mettre à jour l'affichage
                            if (inventaireController != null) {
                                inventaireController.rafraichir();
                            }
                        }
                        break;
                    }
                }
                
                // Attaque du loup si à portée
                double distanceAttaque = Math.abs(environnement.getJoueur().getX() 
                    - environnement.getLoup().getX());
                Commande commandeAttaque = new CommandeAttaquerJoueur(
                    environnement.getJoueur(), environnement.getLoup(), distanceAttaque);
                commandeAttaque.executer();
                
                if (environnement.getLoup().getPointsDeVie() <= 0) {
                    loupMort = true;
                }
            }
        });
        
        // Raccourcis clavier
        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.I && inventaireController != null) {
                inventaireController.basculerAffichage();
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
                if (pauseOverlay != null) pauseOverlay.setVisible(enPause);
            } else if (e.getCode() == KeyCode.ESCAPE) {
                enPause = !enPause;
                if (pauseOverlay != null) pauseOverlay.setVisible(enPause);
            }
        });
    }
    
    /**
     * Démarre la boucle de jeu principale.
     * Utilise AnimationTimer pour les mises à jour à chaque frame.
     */
    private void demarrerBoucleJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourJeu();
            }
        };
        gameLoop.start();
        System.out.println("Boucle de jeu démarrée");
    }
    
    /**
     * Boucle principale du jeu - Met à jour tous les éléments à chaque frame.
     * 
     * Architecture MVC:
     * - Le Controller coordonne les mises à jour
     * - La logique métier est dans le Model (Joueur, Loup, Environnement)
     * - L'affichage est mis à jour dans la View
     */
    private void mettreAJourJeu() {
        if (enPause) return;
        
        // 1. Mise à jour des compteurs d'animation
        if (delaiDegats > 0) delaiDegats--;
        if (framesAtterrissageRestants > 0) framesAtterrissageRestants--;
        
        // 2. Gestion des entrées clavier (Command Pattern)
        gererClavierEtInventaire();
        
        // 3. Mise à jour de la physique
        boolean aAtterri = moteurPhysique.mettreAJourPhysique(
            environnement.getJoueur(), 
            environnement.getCarte(),
            vueJoueur.getLargeur(), 
            vueJoueur.getHauteur()
        );
        if (aAtterri) {
            framesAtterrissageRestants = animationManager.getNbFramesAtterrissage();
        }
        
        // 4. Mise à jour du modèle (State Pattern dans Joueur)
        environnement.getJoueur().mettreAJour();
        
        // 5. Mise à jour de l'IA du loup (Strategy Pattern)
        if (!loupMort && environnement.getLoup().getPointsDeVie() > 0) {
            environnement.getLoup().mettreAJourIA(
                environnement.getJoueur(), 
                environnement.getCarte()
            );
        } else if (!loupMort) {
            environnement.getLoup().arreter();
            loupMort = true;
        }
        
        // 6. Gestion des collisions (Command Pattern)
        double distanceLoupJoueur = Math.abs(
            environnement.getJoueur().getX() - environnement.getLoup().getX()
        );
        Commande gestionCollision = new CommandeGestionCollision(
            environnement.getJoueur(), 
            environnement.getLoup(), 
            distanceLoupJoueur,
            vueJoueur.getSprite(), 
            vueLoup.getSprite(), 
            delaiDegats
        );
        gestionCollision.executer();
        
        // 7. Mise à jour de l'affichage et des animations
        mettreAJourAffichage();
        mettreAJourAnimations();
    }
    
    /**
     * Gère les entrées clavier via Command Pattern.
     * Délègue les actions au modèle (Joueur).
     */
    private void gererClavierEtInventaire() {
        // Command Pattern: Encapsulation de la gestion clavier
        Commande gestionClavier = new CommandeGestionClavier(
            clavier, 
            environnement.getJoueur(), 
            inventaireController
        );
        gestionClavier.executer();
        
        // Mise à jour de l'état du jeu
        if (environnement.getJoueur().getPointsDeVie() <= 0) {
            joueurMort = true;
        }
        
        // Mise à jour des états pour les animations
        toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
    }
    
    /**
     * Met à jour l'affichage de tous les éléments visuels.
     * Gère le scrolling de la caméra et le positionnement des sprites.
     */
    private void mettreAJourAffichage() {
        Joueur joueur = environnement.getJoueur();
        
        // Calcul du scroll de la caméra (centré sur le joueur)
        double offsetX = Math.max(0, Math.min(
            joueur.getX() - largeurEcran / 2,
            environnement.getCarte().getLargeur() * ConstantesJeu.TAILLE_TUILE - largeurEcran
        ));
        double offsetY = Math.max(0, Math.min(
            joueur.getY() - hauteurEcran / 2,
            environnement.getCarte().getHauteur() * ConstantesJeu.TAILLE_TUILE - hauteurEcran
        ));
        
        offsetXProperty.set(offsetX);
        offsetYProperty.set(offsetY);
        
        // Mise à jour de l'affichage de la carte et du background
        carteAffichable.redessiner(offsetX, offsetY);
        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }
        
        // Positionnement des NPCs relatif à la caméra
        if (spriteGuide != null) {
            spriteGuide.setX(environnement.getGuide().getX() - offsetX);
            spriteGuide.setY(environnement.getGuide().getY() - offsetY);
        }
        if (spriteForgeron != null) {
            spriteForgeron.setX(environnement.getForgeron().getX() - offsetX);
            spriteForgeron.setY(environnement.getForgeron().getY() - offsetY);
        }
        
        // Mise à jour des vues de ressources
        for (VueRessource vr : vueRessources) {
            vr.mettreAJour();
        }
    }
    
    /**
     * Met à jour les animations du joueur et du loup.
     * Utilise AnimationManager et le Command Pattern pour le joueur.
     * Met à jour directement le loup via VueLoup.
     * 
     * IMPORTANT: gestionAnimation est réutilisé à chaque frame pour conserver
     * les compteurs d'animation (frameIdle, compteurMarche, etc.)
     */
    private void mettreAJourAnimations() {
        // Animation du joueur via Command Pattern
        // IMPORTANT: Réutilisation de gestionAnimation pour ne pas réinitialiser les compteurs!
        Commande animationJoueur = new CommandeAnimationJoueur(
            environnement.getJoueur(),
            gestionAnimation, // Utilise l'instance persistante au lieu d'en créer une nouvelle
            vueJoueur.getSprite(),
            toucheAccroupi,
            delaiDegats,
            framesAtterrissageRestants,
            compteurAttaque,
            frameMort,
            joueurMort
        );
        animationJoueur.executer();
        
        // Animation du loup (reads animationState from Model and updates View)
        if (vueLoup != null && !loupMort) {
            vueLoup.mettreAJourAnimation();
        }
    }
    
    /**
     * Observer Pattern: Notifié des changements dans le modèle
     * 
     * @param sujet Le sujet qui a changé
     * @param type Le type de changement survenu
     */
    @Override
    public void mettreAJour(SujetObserve sujet, TypeChangement type) {
        switch (type) {
            case RESSOURCE_RECOLTEE -> {
                // Mettre à jour l'affichage des ressources
                for (VueRessource vr : vueRessources) {
                    vr.mettreAJour();
                }
            }
            case COMBAT -> {
                // Gérer les effets visuels de combat
            }
            case DIALOGUE -> {
                // Ouvrir la fenêtre de dialogue
            }
            default -> {
                // Autres types de changements
            }
        }
    }
    
    // ========== Getters ==========
    
    public Pane getRacine() {
        return racine;
    }
    
    public Environnement getEnvironnement() {
        return environnement;
    }
}

