package fr.iut.groupe.terraria.demo.controleur;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import fr.iut.groupe.terraria.demo.modele.personnages.Loup;

import static fr.iut.groupe.terraria.demo.modele.donnees.ConstantesJeu.*;

import fr.iut.groupe.terraria.demo.modele.carte.Carte;
import fr.iut.groupe.terraria.demo.controleur.moteur.MoteurPhysique;
import fr.iut.groupe.terraria.demo.modele.personnages.Joueur;
import fr.iut.groupe.terraria.demo.modele.bloc.BlocManager;
import fr.iut.groupe.terraria.demo.modele.bloc.TileType;
import fr.iut.groupe.terraria.demo.vue.CarteAffichable;
import fr.iut.groupe.terraria.demo.vue.VueBackground;
import fr.iut.groupe.terraria.demo.vue.animation.GestionAnimation;
import fr.iut.groupe.terraria.demo.vue.utilitaire.ExtracteurSprites;
import fr.iut.groupe.terraria.demo.controleur.interfacefx.InventaireController;
import fr.iut.groupe.terraria.demo.controleur.interfacefx.ParametresController;
import fr.iut.groupe.terraria.demo.vue.utilitaire.Pathfinder;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import fr.iut.groupe.terraria.demo.modele.ressource.CanneSucre;
import fr.iut.groupe.terraria.demo.modele.ressource.Roche;
import fr.iut.groupe.terraria.demo.modele.carte.ChargeurCarte;
import java.util.List;
import java.util.ArrayList;

public class ControleurJeu {

    private static MediaPlayer mediaPlayer;

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private final InventaireController inventaireController;
    private final ProgressBar barreVie;
    private final Label labelVie;
    private final Pane pauseOverlay;
    private final Pane craftOverlay;
    private final VBox craftBox;
    private VueBackground vueBackground;
    private final double largeurEcran;

    // Sprite du loup ennemi
    private final Loup loup;
    private final Timeline botTimeline;
    private List<int[]> botPath;
    private int botPathIndex;
    private final int[][] grille;
    private int prevJoueurCol = -1;
    private int prevJoueurLig = -1;
    private int cooldownChemin = 0;
    private static final int BOT_DEGATS = 10;

    // Suivi des animations du joueur
    private int compteurAttaque = 0;
    private int frameMort = 0;

    private double offsetX = 0;
    private int framesAtterrissageRestants = 0;
    private int delaiDegats = 0;
    private boolean joueurMort = false;
    private boolean enPause = false;
    private boolean enCraft = false;
    private Stage fenetreParametres;

    // --- Ressources placées dans le monde ---
    private final List<Ressource> ressources = new ArrayList<>();

    // --- Gestion de la vie du bot ---
    private int botVie = 3;
    private int botDelaiCouleur = 0;
    private double opaciteLoupBase;

    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur,
                         InventaireController inventaireController, ProgressBar barreVie, Label labelVie, Pane pauseOverlay,
                         Pane craftOverlay, VBox craftBox,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque, WritableImage[] preparationSaut, WritableImage[] volSaut,
                         WritableImage[] sautReload, WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort, WritableImage[] sort,
                         WritableImage[] accroupi, WritableImage[] bouclier,
                         Loup loup // ennemi loup
    ) {
        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.labelVie = labelVie;
        this.pauseOverlay = pauseOverlay;
        this.craftOverlay = craftOverlay;
        this.craftBox = craftBox;
        if (this.pauseOverlay != null) {
            this.pauseOverlay.setVisible(false);
        }
        if (this.craftOverlay != null) {
            this.craftOverlay.setVisible(false);
        }
        if (this.labelVie != null) {
            this.labelVie.textProperty().bind(joueur.pointsDeVieProperty().asString());
        }
        if (this.barreVie != null) {
            this.barreVie.progressProperty().bind(
                    Bindings.divide(joueur.pointsDeVieProperty(), (double) VIE_MAX_JOUEUR)
            );
            this.barreVie.progressProperty().addListener((obs, ov, nv) -> {
                Color couleurVie = Color.GREEN.interpolate(Color.RED, 1 - nv.doubleValue());
                String hex = String.format("#%02X%02X%02X",
                        (int) (couleurVie.getRed() * 255),
                        (int) (couleurVie.getGreen() * 255),
                        (int) (couleurVie.getBlue() * 255));
                barreVie.setStyle("-fx-accent: " + hex + ";");
            });
        }
        this.clavier = new GestionClavier(scene);
        this.largeurEcran = scene.getWidth();

        // Loup paramètres
        this.loup = loup;
        this.opaciteLoupBase = loup.getSprite().getOpacity();
        this.grille = carte.getGrille();
        botTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            if (botPath != null && botPathIndex < botPath.size()) {
                int[] etape = botPath.get(botPathIndex);
                loup.setX(etape[0] * TAILLE_TUILE);
                loup.setY(etape[1] * TAILLE_TUILE);
                botPathIndex++;
            }
        }));
        botTimeline.setCycleCount(Timeline.INDEFINITE);

        // Animasyon ve input
        this.animation = new GestionAnimation(idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier);

        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!joueur.estEnAttaque()) {
                    joueur.attaquer();
                    animation.reset();
                    compteurAttaque = 0;
                } else {
                    animation.demandeCombo();
                }
            }
        });
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                gererClicDroit(e.getX(), e.getY());
            } });

        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.P) {
                if (fenetreParametres == null) {
                    ouvrirParametres(scene);
                }
            } else if (e.getCode() == KeyCode.I) {
                if (inventaireController != null) {
                    inventaireController.basculerAffichage();
                }
            } else if (e.getCode() == KeyCode.C) {
                if (craftOverlay != null) {
                    enCraft = !craftOverlay.isVisible();
                    craftOverlay.setVisible(enCraft);
                    enPause = enCraft;
                }
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
                if (pauseOverlay != null) {
                    pauseOverlay.setVisible(enPause);
                }
            }
        });

        initialiserCraft();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJour();
            }
        }.start();
    }

    private void mettreAJour() {
        if (enPause || enCraft) return;
        if (delaiDegats > 0) delaiDegats--;
        if (framesAtterrissageRestants > 0) framesAtterrissageRestants--;
        if (cooldownChemin > 0) cooldownChemin--;

        if (botDelaiCouleur > 0) {
            botDelaiCouleur--;
            if (botDelaiCouleur == 0 && botVie > 0) {
                loup.getSprite().setOpacity(opaciteLoupBase);
            }
        }

        offsetX = joueur.getX() - largeurEcran / 2;
        if (offsetX < 0) offsetX = 0;
        double maxOffset = carte.getLargeur() * TAILLE_TUILE - largeurEcran;
        if (offsetX > maxOffset) offsetX = maxOffset;
        carteAffichable.redessiner(offsetX);
        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }

        // Met à jour la position des ressources affichées
        for (Ressource r : ressources) {
            if (r.getVueNode() != null) {
                r.getVueNode().setTranslateX(-offsetX);
            }
        }

        // --- BOT TAKİP ---
        int botCol = (int) (loup.getX() / TAILLE_TUILE);
        int botLig = (int) (loup.getY() / TAILLE_TUILE);
        int joueurCol = (int) (joueur.getX() / TAILLE_TUILE);
        int joueurLig = (int) (joueur.getY() / TAILLE_TUILE);
        // Recalcule le chemin uniquement lorsque c'est nécessaire et que le
        // délai de récalcul est à zéro pour limiter les coûts.
        boolean besoinChemin = botPath == null || botPathIndex >= botPath.size();
        boolean joueurBouge = joueurCol != prevJoueurCol || joueurLig != prevJoueurLig;
        if (Math.abs(botCol - joueurCol) + Math.abs(botLig - joueurLig) <= 8) {
            if ((besoinChemin || joueurBouge) && cooldownChemin == 0) {
                botPath = Pathfinder.aStar(grille,
                        new int[]{botCol, botLig}, new int[]{joueurCol, joueurLig});
                botPathIndex = 0;
                prevJoueurCol = joueurCol;
                prevJoueurLig = joueurLig;
                cooldownChemin = 10; // attente avant un nouveau calcul
            }
            botTimeline.play();
        } else {
            botTimeline.stop();
            botPath = null;
        }
        // Positionne l'affichage du bot en fonction du décalage de la caméra
        loup.getSprite().setTranslateX(-offsetX);

        // Collision simple entre le bot et le joueur
        if (loup.getSprite().getBoundsInParent().intersects(joueur.getSprite().getBoundsInParent()) && delaiDegats == 0 && botVie > 0) {
            joueur.subirDegats(BOT_DEGATS);
            delaiDegats = 20; // petite invulnérabilité après un coup
        }

        // Dégâts infligés au bot par l'attaque du joueur
        if (botVie > 0 && joueur.estEnAttaque() && !animation.isAttaqueTerminee()
                && botDelaiCouleur == 0
                && loup.getSprite().getBoundsInParent().intersects(joueur.getSprite().getBoundsInParent())) {
            botVie--;
            loup.getSprite().setOpacity(0.5);
            botDelaiCouleur = 10;
            if (botVie <= 0) {
                botTimeline.stop();
                loup.getSprite().setVisible(false);
            }
        }

        // --- Geriye kalan orijinal kodun aynen devam ediyor ---
        boolean gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        boolean droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        boolean toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        boolean toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        boolean toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        boolean toucheDegats = clavier.estAppuyee(KeyCode.M);

        if (inventaireController != null) {
            for (int i = 1; i <= 9; i++) {
                KeyCode code = KeyCode.valueOf("DIGIT" + i);
                if (clavier.estAppuyee(code)) {
                    inventaireController.selectionnerIndex(i - 1);
                    break;
                }
            }
            if (clavier.estAppuyee(KeyCode.E)) inventaireController.deselectionner();
        }

        if (toucheDegats) {
            joueur.subirDegats(1);
        }

        if (joueur.getPointsDeVie() <= 0) {
            joueurMort = true;
        }

        if (!joueurMort) {
            if (gauche) joueur.deplacerGauche(VITESSE_JOUEUR);
            else if (droite) joueur.deplacerDroite(VITESSE_JOUEUR);
            else joueur.arreter();
        } else {
            joueur.arreter();
        }

        if (!joueurMort && toucheSaut && joueur.estAuSol()) {
            joueur.sauter(IMPULSION_SAUT);
        }

        boolean aAtterri = moteur.mettreAJourPhysique(joueur, carte);
        if (aAtterri) framesAtterrissageRestants = animation.getNbFramesAtterrissage();

        joueur.getSprite().setX(joueur.getX() - offsetX);
        joueur.getSprite().setY(joueur.getY());

        barreVie.setLayoutX(joueur.getX() - offsetX);
        barreVie.setLayoutY(joueur.getY() - 10);
        labelVie.setLayoutX(joueur.getX() - offsetX);
        labelVie.setLayoutY(joueur.getY() - 25);

        // Dilersen istersen bot ve oyuncunun çarpışma kontrolünü de burada ekleyebilirsin.
        // (Ama Loup ile ilgili bir kod yok!)

        ImageView sprite = joueur.getSprite();

        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
            sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
            return;
        } else if (toucheDegats || delaiDegats > 0) {
            animation.animerDegats(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, DELAI_FRAME, () -> joueur.finAttaque());
            compteurAttaque++;
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (framesAtterrissageRestants > 0) {
            int frame = animation.getNbFramesAtterrissage() - framesAtterrissageRestants;
            animation.animerAtterrissage(sprite, frame);
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (toucheBouclier) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }
        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
    }

    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
    }

    public void ajouterRessource(Ressource ressource) {
        ressources.add(ressource);
    }

    public void retirerRessource(Ressource ressource) {
        ressources.remove(ressource);
    }

    private void gererClicDroit(double xScene, double yScene) {
        int colonne = (int) ((xScene + offsetX) / TAILLE_TUILE);
        int ligne = (int) (yScene / TAILLE_TUILE);
        boolean dansCarte = colonne >= 0 && colonne < carte.getLargeur()
                && ligne >= 0 && ligne < carte.getHauteur();

        if (dansCarte) {
            String selection = inventaireController != null ? inventaireController.getItemSelectionne() : null;

            if (selection != null) {
                TileType type = switch (selection.toLowerCase()) {
                    case "bois" -> TileType.ARBRE;
                    case "terre" -> TileType.TERRE;
                    case "herbe" -> TileType.HERBE;
                    default -> null;
                };
                if (type != null && joueur.getInventaire().retirerItem(selection, 1)) {
                    carte.setValeurTuile(ligne, colonne, type.getId());
                }
                if (inventaireController != null) {
                    inventaireController.deselectionner();
                    inventaireController.rafraichir();
                }
            } else {
                int idAvant = carte.getValeurTuile(ligne, colonne);
                if (BlocManager.casserBloc(carte, ligne, colonne) && idAvant != Carte.TUILE_VIDE) {
                    joueur.getInventaire().ajouterItem("Bois", 1);
                    if (inventaireController != null) {
                        inventaireController.rafraichir();
                    }
                }
            }
            carteAffichable.redessiner(offsetX);
        }
    }

    private void ouvrirParametres(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fr/iut/groupe/terraria/demo/vue/interface/Parametres.fxml"));
            Parent root = loader.load();
            fenetreParametres = new Stage();
            fenetreParametres.initOwner(scene.getWindow());
            fenetreParametres.initModality(Modality.WINDOW_MODAL);
            fenetreParametres.setTitle("Paramètres");
            fenetreParametres.setScene(new Scene(root));
            ParametresController controller = loader.getController();
            controller.setStage(fenetreParametres);
            enPause = true;
            if (pauseOverlay != null) {
                pauseOverlay.setVisible(true);
            }
            fenetreParametres.setOnHidden(e -> {
                enPause = false;
                if (pauseOverlay != null) {
                    pauseOverlay.setVisible(false);
                }
                fenetreParametres = null;
            });
            fenetreParametres.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialiserCraft() {
        if (craftBox == null) return;
        craftBox.getChildren().clear();
        java.util.List<fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement> eqs = java.util.List.of(
                new fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Hache(),
                new fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Pioche(),
                new fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc(),
                new fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Bombe()
        );
        for (var eq : eqs) {
            javafx.scene.control.Button b = new javafx.scene.control.Button(eq.getNom());
            b.setOnAction(e -> tenterCraft(eq));
            craftBox.getChildren().add(b);
        }
    }

    private void tenterCraft(fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement eq) {
        var inv = joueur.getInventaire();
        if (inv.contient("Bois", 1) && inv.contient("Pierre", 1) && inv.contient("File", 1)) {
            inv.retirerItem("Bois", 1);
            inv.retirerItem("Pierre", 1);
            inv.retirerItem("File", 1);
            inv.ajouterItem(eq.getNom(), 1);
            if (inventaireController != null) {
                inventaireController.rafraichir();
            }
            if (craftOverlay != null) craftOverlay.setVisible(false);
            enCraft = false;
            enPause = false;
        }
    }

    /* --------------------------------------------------------- */
    /*           Fonctions utilitaires pour le LanceurJeu        */
    /* --------------------------------------------------------- */

    public static ControleurJeu initialiserJeu(Stage stage) throws IOException {
        initialiserMusique();

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double screenWidth = screen.getWidth();
        double screenHeight = screen.getHeight();

        double gameWidth = 1280;
        double gameHeight = 720;

        double scaleX = screenWidth / gameWidth;
        double scaleY = screenHeight / gameHeight;
        double scale = Math.min(scaleX, scaleY);

        Pane racine;
        try {
            FXMLLoader loader = new FXMLLoader(ControleurJeu.class.getResource(
                    "/fr/iut/groupe/terraria/demo/vue/VueJeu.fxml"));
            racine = loader.load();
        } catch (IOException e) {
            racine = new Pane();
        }

        racine.setScaleX(scale);
        racine.setScaleY(scale);
        Scene scene = new Scene(racine, screenWidth, screenHeight);

        Pane pauseOverlay = (Pane) scene.lookup("#pauseOverlay");
        Pane craftOverlay = (Pane) scene.lookup("#craftOverlay");
        VBox craftBox = (VBox) scene.lookup("#craftBox");
        if (pauseOverlay != null) {
            pauseOverlay.prefWidthProperty().bind(scene.widthProperty());
            pauseOverlay.prefHeightProperty().bind(scene.heightProperty());
            pauseOverlay.setViewOrder(-20);
        }
        if (craftOverlay != null) {
            craftOverlay.prefWidthProperty().bind(scene.widthProperty());
            craftOverlay.prefHeightProperty().bind(scene.heightProperty());
            craftOverlay.setViewOrder(-20);
        }

        int[][] grille = ChargeurCarte.chargerCarteDepuisCSV(
                "/fr/iut/groupe/terraria/demo/cartes/jungle_map_calque1.csv");
        Carte carte = new Carte(grille);

        InputStream tilesetStream = ControleurJeu.class.getResourceAsStream(
                "/fr/iut/groupe/terraria/demo/images/tileset_jungle.png");
        Image tileset = new Image(tilesetStream);
        CarteAffichable carteAffichable = new CarteAffichable(carte, tileset, (int) gameWidth, (int) gameHeight);
        int largeurCartePx = carte.getLargeur() * ConstantesJeu.TAILLE_TUILE;

        VueBackground vueBackground = new VueBackground((int) gameWidth, (int) gameHeight - 50, largeurCartePx);
        racine.getChildren().add(vueBackground);

        InputStream personnageStream = ControleurJeu.class.getResourceAsStream(
                "/fr/iut/groupe/terraria/demo/images/sprite1.png");
        Image personnage = new Image(personnageStream);
        WritableImage[] idle = ExtracteurSprites.idle(personnage);
        WritableImage[] attaque = ExtracteurSprites.attaque(personnage);
        WritableImage[] marche = ExtracteurSprites.marche(personnage);
        WritableImage[] accroupi = ExtracteurSprites.accroupi(personnage);
        WritableImage[] preparationSaut = ExtracteurSprites.preparationSaut(personnage);
        WritableImage[] volSaut = ExtracteurSprites.volSaut(personnage);
        WritableImage[] sautReload = ExtracteurSprites.sautReload(personnage);
        WritableImage[] chute = ExtracteurSprites.chute(personnage);
        WritableImage[] atterrissage = ExtracteurSprites.atterrissage(personnage);
        WritableImage[] degats = ExtracteurSprites.degats(personnage);
        WritableImage[] mort = ExtracteurSprites.mort(personnage);
        WritableImage[] sort = ExtracteurSprites.sort(personnage);
        WritableImage[] bouclier = ExtracteurSprites.bouclier(personnage);

        double xInitial = 320;
        int colonne = (int) (xInitial / ConstantesJeu.TAILLE_TUILE);
        int ligneSol = carte.chercherLigneSol(colonne);
        double yInitial = ligneSol * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE;

        ImageView spriteJoueur = new ImageView(idle[0]);
        spriteJoueur.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
        spriteJoueur.setFitHeight(ConstantesJeu.TAILLE_SPRITE);
        Joueur joueur = new Joueur(spriteJoueur, xInitial, yInitial);
        joueur.setEstAuSol(true);
        racine.getChildren().addAll(carteAffichable, spriteJoueur);

        double xBot = 500;
        int colBot = (int) (xBot / ConstantesJeu.TAILLE_TUILE);
        int ligneSolBot = carte.chercherLigneSol(colBot);
        double yBot = ligneSolBot * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE;

        InputStream loupStream = ControleurJeu.class.getResourceAsStream(
                "/fr/iut/groupe/terraria/demo/images/wolf.gif");
        Image imageLoup = new Image(loupStream);
        ImageView spriteLoup = new ImageView(imageLoup);
        spriteLoup.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
        spriteLoup.setFitHeight(ConstantesJeu.TAILLE_SPRITE);
        Loup loup = new Loup(spriteLoup, xBot, yBot, 10);
        loup.setEstAuSol(true);
        racine.getChildren().add(spriteLoup);

        ProgressBar barreVie = new ProgressBar(1.0);
        barreVie.setPrefWidth(ConstantesJeu.TAILLE_SPRITE * 2);
        barreVie.setPrefHeight(6);
        barreVie.setStyle("-fx-accent: #e74c3c;");
        barreVie.setViewOrder(-9);
        racine.getChildren().add(barreVie);

        Label labelVie = new Label(Integer.toString(joueur.getPointsDeVie()));
        labelVie.setTextFill(Color.WHITE);
        labelVie.setViewOrder(-9);
        racine.getChildren().add(labelVie);

        InventaireController inventaireCtrl = afficherInventaire(racine, joueur);

        ControleurJeu controleurJeu = new ControleurJeu(
                scene, carte, carteAffichable, joueur,
                inventaireCtrl, barreVie, labelVie, pauseOverlay,
                craftOverlay, craftBox,
                idle, marche, attaque, preparationSaut, volSaut, sautReload,
                chute, atterrissage, degats, mort, sort, accroupi, bouclier,
                loup
        );
        controleurJeu.setVueBackground(vueBackground);

        int[] colsArbre = {25, 27, 30};
        for (int colArbre : colsArbre) {
            int solArbre = carte.chercherLigneSol(colArbre);
            double yArbre = solArbre * ConstantesJeu.TAILLE_TUILE - 32;
            Arbre arbre = new Arbre(colArbre * ConstantesJeu.TAILLE_TUILE, yArbre);
            ajouterRessourceRectangle(racine, controleurJeu, joueur, inventaireCtrl, arbre,
                    Color.GREEN, ConstantesJeu.TAILLE_TUILE, 32);
        }

        int[] colsCanne = {35, 37, 40};
        for (int colCanne : colsCanne) {
            int solCanne = carte.chercherLigneSol(colCanne);
            double yCanne = solCanne * ConstantesJeu.TAILLE_TUILE - 16;
            CanneSucre canne = new CanneSucre(colCanne * ConstantesJeu.TAILLE_TUILE, yCanne);
            ajouterRessourceRectangle(racine, controleurJeu, joueur, inventaireCtrl, canne,
                    Color.YELLOW, ConstantesJeu.TAILLE_TUILE, 16);
        }

        int[] colsRoche = {45, 48, 52};
        for (int colRoche : colsRoche) {
            int solRoche = carte.chercherLigneSol(colRoche);
            double yRoche = solRoche * ConstantesJeu.TAILLE_TUILE - 16;
            Roche roche = new Roche(colRoche * ConstantesJeu.TAILLE_TUILE, yRoche);
            ajouterRessourceRectangle(racine, controleurJeu, joueur, inventaireCtrl, roche,
                    Color.BLACK, ConstantesJeu.TAILLE_TUILE, 16);
        }

        stage.setTitle("Jungle Quest");
        stage.setScene(scene);
        stage.setResizable(false);

        return controleurJeu;
    }

    private static void initialiserMusique() {
        try {
            URL ressourceAudio = ControleurJeu.class.getResource(
                    "/fr/iut/groupe/terraria/demo/sons/musique_jeu.mp3");
            if (ressourceAudio != null) {
                Media media = new Media(ressourceAudio.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.25);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Erreur musique : " + e.getMessage());
        }
    }

    private static InventaireController afficherInventaire(Pane racine, Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(ControleurJeu.class.getResource(
                    "/fr/iut/groupe/terraria/demo/vue/interface/Inventaire.fxml"));
            Node inventaireUI = loader.load();
            InventaireController inventaireController = loader.getController();
            inventaireController.setInventaire(joueur.getInventaire());
            inventaireUI.setLayoutX(10);
            inventaireUI.setLayoutY(10);
            inventaireUI.setViewOrder(-10);
            racine.getChildren().add(inventaireUI);
            return inventaireController;
        } catch (IOException e) {
            System.err.println("Inventaire UI non chargé : " + e.getMessage());
            return null;
        }
    }

    private static void ajouterRessourceRectangle(Pane racine, ControleurJeu controleur, Joueur joueur,
                                                  InventaireController inventaireCtrl, Ressource ressource,
                                                  Color couleur, int largeur, int hauteur) {
        Rectangle rect = new Rectangle(largeur, hauteur, couleur);
        rect.setX(ressource.getX());
        rect.setY(ressource.getY());
        racine.getChildren().add(rect);
        ressource.setVueNode(rect);
        rect.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                joueur.getInventaire().ajouterItem(ressource.getItemProduit().getNom(), 1);
                if (inventaireCtrl != null) {
                    inventaireCtrl.rafraichir();
                }
                racine.getChildren().remove(rect);
                controleur.retirerRessource(ressource);
                e.consume();
            }
        });
        controleur.ajouterRessource(ressource);
    }
}
