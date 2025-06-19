package universite_paris8.iut.dagnetti.junglequest.controleur;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Guide;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Forgeron;
import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.BarreVie;

import static universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu.*;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.controleur.moteur.MoteurPhysique;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Loup;
import universite_paris8.iut.dagnetti.junglequest.vue.personnages.VueJoueur;
import universite_paris8.iut.dagnetti.junglequest.vue.personnages.VueLoup;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.BlocManager;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.animation.GestionAnimation;
import javafx.scene.image.WritableImage;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ParametresController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.DialogueController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ForgeController;
import java.io.IOException;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final Loup loup;
    private final VueJoueur vueJoueur;
    private final VueLoup vueLoup;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private final InventaireController inventaireController;
    private final BarreVie barreVie;
    private final javafx.scene.control.Label labelVie;
    private final BarreVie barreVieLoup;
    private final javafx.scene.control.Label labelVieLoup;
    private final Guide guide;
    private final Forgeron forgeron;
    private final Pane pauseOverlay;
    private VueBackground vueBackground;
    private final double largeurEcran;
    private final double hauteurEcran;

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private final DoubleProperty offsetXProperty = new SimpleDoubleProperty();
    private final DoubleProperty offsetYProperty = new SimpleDoubleProperty();
    // Durée restante de l'animation d'atterrissage
    private int framesAtterrissageRestants = 0;

    // Délai entre deux pertes de vie lors d'un contact avec un ennemi
    private int delaiDegats = 0;


    private boolean joueurMort = false;
    private boolean loupMort = false;

    // Animation du loup
    private final int largeurFrameLoup;
    private final int hauteurFrameLoup;
    private final int largeurFrameLoupAttaque;
    private final int hauteurFrameLoupAttaque;
    private int frameLoup = 0;
    private int compteurLoup = 0;

    private boolean enPause = false;
    private Stage fenetreParametres;
    private Stage fenetreForge;
    private boolean dialogueGuideAffiche = false;

    // États de contrôle issus du clavier
    private boolean gauche;
    private boolean droite;
    private boolean toucheSaut;
    private boolean toucheAccroupi;
    private boolean toucheBouclier;
    private boolean toucheDegats;

    /**
     * Initialise le contrôleur principal du jeu : clavier, animation, logique du joueur et gestion des clics.
     */
    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur, VueJoueur vueJoueur,
                         Loup loup, VueLoup vueLoup, Guide guide,
                         Forgeron forgeron,
                         InventaireController inventaireController, BarreVie barreVie, javafx.scene.control.Label labelVie,
                         BarreVie barreVieLoup, javafx.scene.control.Label labelVieLoup, Pane pauseOverlay,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.vueJoueur = vueJoueur;
        this.loup = loup;
        this.vueLoup = vueLoup;
        this.guide = guide;
        this.forgeron = forgeron;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.labelVie = labelVie;
        this.barreVieLoup = barreVieLoup;
        this.labelVieLoup = labelVieLoup;
        if (this.labelVie != null) {
            this.labelVie.textProperty().bind(joueur.pointsDeVieProperty().asString());
        }
        if (this.barreVie != null) {
            this.barreVie.ratioProperty().bind(joueur.pointsDeVieProperty().divide((double) VIE_MAX_JOUEUR));
        }
        if (this.labelVieLoup != null) {
            this.labelVieLoup.textProperty().bind(loup.pointsDeVieProperty().asString());
        }
        if (this.barreVieLoup != null) {
            this.barreVieLoup.ratioProperty().bind(loup.pointsDeVieProperty().divide((double) VIE_MAX_LOUP));
        }
        this.pauseOverlay = pauseOverlay;
        if (this.pauseOverlay != null) {
            this.pauseOverlay.setVisible(false);
        }
        this.clavier = new GestionClavier(scene);
        this.largeurEcran = scene.getWidth();
        this.hauteurEcran = scene.getHeight();

        vueJoueur.lierPosition(offsetXProperty, offsetYProperty);
        vueLoup.lierPosition(offsetXProperty, offsetYProperty);

        Image imgLoup = vueLoup.getSprite().getImage();
        this.largeurFrameLoup = (int) (imgLoup.getWidth() / 6);
        this.hauteurFrameLoup = (int) imgLoup.getHeight();
        Image imgLoupAttack = loup.getAttackImage();
        this.largeurFrameLoupAttaque = (int) (imgLoupAttack.getWidth() / 6);
        this.hauteurFrameLoupAttaque = (int) imgLoupAttack.getHeight();
        vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        vueLoup.getSprite().setFitWidth(largeurFrameLoup);
        vueLoup.getSprite().setFitHeight(hauteurFrameLoup);

        // Initialisation des animations
        this.animation = new GestionAnimation(
                idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier
        );

        // Gestion du clic gauche pour attaquer
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double distance = Math.abs(joueur.getX() - loup.getX());
                if (distance <= PORTEE_ATTAQUE_JOUEUR) {
                    if (joueur.isBouclierActif()) {
                        joueur.desactiverBouclier();
                    }
                    double xMonde = e.getX() + offsetXProperty.get();
                    double yMonde = e.getY() + offsetYProperty.get();
                    boolean cliqueLoup = xMonde >= loup.getX() && xMonde <= loup.getX() + vueLoup.getSprite().getFitWidth()
                            && yMonde >= loup.getY() && yMonde <= loup.getY() + vueLoup.getSprite().getFitHeight();

                    if (!joueur.estEnAttaque()) {
                        joueur.attaquer();
                        animation.reset();
                        compteurAttaque = 0;
                        if (cliqueLoup) {
                            loup.subirDegats(DEGATS_JOUEUR_LOUP);
                            if (loup.getPointsDeVie() <= 0) {
                                loupMort = true;
                            }
                        }
                    } else {
                        animation.demandeCombo();
                        if (cliqueLoup) {
                            loup.subirDegats(DEGATS_COMBO_SUPPLEMENTAIRES);
                            if (loup.getPointsDeVie() <= 0) {
                                loupMort = true;
                            }
                        }
                    }
                }
            }
        });
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                gererClicDroit(e.getX(), e.getY());
            } });

        // Appuyer sur 'P' permet d'afficher ou de masquer l'inventaire. L'événement
        // est écouté directement sur la scène afin de ne pas interférer avec la
        // gestion classique du clavier.
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
                double distanceForgeron = Math.abs(joueur.getX() - forgeron.getX());
                if (distanceForgeron <= 40 && fenetreForge == null) {
                    ouvrirForge(scene);
                }
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
                if (pauseOverlay != null) {
                    pauseOverlay.setVisible(enPause);
                }
            }
        });

        // Lancement de la boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJour();
            }
        }.start();
    }

    /**
     * Méthode principale appelée à chaque frame pour gérer les actions du joueur et l'affichage.
     */
    private void mettreAJour() {
        if (!enPause) {
            if (delaiDegats > 0) delaiDegats--;
            if (framesAtterrissageRestants > 0) framesAtterrissageRestants--;

            gererClavierEtInventaire();
            mettreAJourMouvements();
            mettreAJourAffichageEtAnimations();
        }
    }

    private void gererClavierEtInventaire() {
        gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);

        if (toucheBouclier && !joueur.estEnAttaque()) {
            joueur.activerBouclier();
        } else if (!toucheBouclier) {
            joueur.desactiverBouclier();
        }

        toucheDegats = clavier.estAppuyee(KeyCode.M);

        if (inventaireController != null) {
            KeyCode[] chiffres = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
                    KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6,
                    KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9};
            for (int i = 0; i < chiffres.length; i++) {
                if (clavier.estAppuyee(chiffres[i])) {
                    inventaireController.selectionnerIndex(i);
                    break;
                }
            }
            if (clavier.estAppuyee(KeyCode.E)) {
                inventaireController.deselectionner();
            }
        }

        if (toucheDegats) {
            joueur.subirDegats(1);
        }

        if (joueur.getPointsDeVie() <= 0) {
            joueurMort = true;
        }
    }

    private void mettreAJourMouvements() {
        if (!joueurMort && !joueur.isBouclierActif()) {
            if (gauche) {
                joueur.deplacerGauche(VITESSE_JOUEUR);
            } else if (droite) {
                joueur.deplacerDroite(VITESSE_JOUEUR);
            } else {
                joueur.arreter();
            }
        } else {
            joueur.arreter();
        }

        if (!joueurMort && toucheSaut && joueur.estAuSol() && !joueur.isBouclierActif()) {
            joueur.sauter(IMPULSION_SAUT);
        }

        boolean aAtterri = moteur.mettreAJourPhysique(joueur, carte,
                vueJoueur.getLargeur(), vueJoueur.getHauteur());
        if (aAtterri) {
            framesAtterrissageRestants = animation.getNbFramesAtterrissage();
        }

        if (!loupMort) {
            loup.mettreAJourIA(joueur, carte);
            moteur.mettreAJourPhysique(loup, carte,
                    vueLoup.getLargeur(), vueLoup.getHauteur());
        } else {
            loup.arreter();
        }
    }

    private void mettreAJourAffichageEtAnimations() {
        double offsetX = joueur.getX() - largeurEcran / 2;
        if (offsetX < 0) offsetX = 0;
        double maxOffset = carte.getLargeur() * TAILLE_TUILE - largeurEcran;
        if (offsetX > maxOffset) {
            offsetX = maxOffset;
        }
        offsetXProperty.set(offsetX);

        double offsetY = joueur.getY() - hauteurEcran / 2;
        if (offsetY < 0) offsetY = 0;
        double maxOffsetY = carte.getHauteur() * TAILLE_TUILE - hauteurEcran;
        if (offsetY > maxOffsetY) {
            offsetY = maxOffsetY;
        }
        offsetYProperty.set(offsetY);

        carteAffichable.redessiner(offsetX, offsetY);

        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }
        // La position du loup est maintenant liée à son modèle, inutile de la mettre à jour manuellement
        guide.getSprite().setX(guide.getX() - offsetX);
        guide.getSprite().setY(guide.getY() - offsetY);
        forgeron.getSprite().setX(forgeron.getX() - offsetX);
        forgeron.getSprite().setY(forgeron.getY() - offsetY);
        barreVie.setLayoutX(joueur.getX() - offsetX);
        barreVie.setLayoutY(joueur.getY() - offsetY - 10);
        labelVie.setLayoutX(joueur.getX() - offsetX);
        labelVie.setLayoutY(joueur.getY() - offsetY - 25);
        if (!dialogueGuideAffiche) {
            double distanceGuide = Math.abs(joueur.getX() - guide.getX());
            if (distanceGuide <= 40) {
                ouvrirDialogueGuide();
                dialogueGuideAffiche = true;
            }
        }

        if (!loupMort) {
            barreVieLoup.setLayoutX(loup.getX() - offsetX);
            barreVieLoup.setLayoutY(loup.getY() - offsetY - 10);
            labelVieLoup.setLayoutX(loup.getX() - offsetX);
            labelVieLoup.setLayoutY(loup.getY() - offsetY - 25);
        } else {
            barreVieLoup.setVisible(false);
            labelVieLoup.setVisible(false);
        }

        double distanceLoupJoueur = Math.abs(joueur.getX() - loup.getX());
        boolean collision = distanceLoupJoueur < 20
                && joueur.getX() < loup.getX() + vueLoup.getSprite().getFitWidth()
                && joueur.getX() + vueJoueur.getSprite().getFitWidth() > loup.getX()
                && joueur.getY() < loup.getY() + vueLoup.getSprite().getFitHeight()
                && joueur.getY() + vueJoueur.getSprite().getFitHeight() > loup.getY();
        if (!loupMort && collision && delaiDegats == 0 && !loup.estEnAttaque() && !joueur.isBouclierActif()) {
            joueur.subirDegats(loup.getDegats());

            delaiDegats = DUREE_DEGATS_JOUEUR;
            loup.attaquer(joueur.getX());
            if (joueur.getPointsDeVie() <= 0) {
                joueurMort = true;
            }
        }

        ImageView sprite = vueJoueur.getSprite();

        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
            sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
        } else if (toucheDegats || delaiDegats > 0) {
            animation.animerDegats(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, Math.max(1, DELAI_FRAME - 2), () -> joueur.finAttaque());
            compteurAttaque++;
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (framesAtterrissageRestants > 0) {
            int frame = animation.getNbFramesAtterrissage() - framesAtterrissageRestants;
            animation.animerAtterrissage(sprite, frame);
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (joueur.isBouclierActif()) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
        if (loupMort) {
            vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        } else if (loup.estEnAttaque()) {
            if (compteurLoup++ >= DELAI_FRAME) {
                vueLoup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoupAttaque, 0,
                        largeurFrameLoupAttaque, hauteurFrameLoupAttaque));
                frameLoup++;
                compteurLoup = 0;
                if (frameLoup >= 6) {
                    frameLoup = 0;
                    loup.finAttaque();
                    vueLoup.getSprite().setImage(loup.getWalkImage());
                }
            }
        } else if (loup.getVitesseX() != 0) {
            if (compteurLoup++ >= DELAI_FRAME) {
                vueLoup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoup, 0, largeurFrameLoup, hauteurFrameLoup));
                frameLoup = (frameLoup + 1) % 6;
                compteurLoup = 0;
            }
        } else {
            vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        }
        vueLoup.getSprite().setScaleX(loup.estVersGauche() ? 1 : -1);
    }

    /**
     * Permet de lier dynamiquement la vue du fond à ce contrôleur.
     */
    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
    }

    private void gererClicDroit(double xScene, double yScene) {
        int colonne = (int) ((xScene + offsetXProperty.get()) / TAILLE_TUILE);
        int ligne = (int) ((yScene + offsetYProperty.get()) / TAILLE_TUILE);
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

            carteAffichable.redessiner(offsetXProperty.get(), offsetYProperty.get());
        }
    }

    private void ouvrirParametres(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/interface/Parametres.fxml"));
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

    private void ouvrirForge(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/interface/Forge.fxml"));
            Pane root = loader.load();
            fenetreForge = new Stage();
            if (scene.getWindow() != null) {
                fenetreForge.initOwner(scene.getWindow());
                fenetreForge.initModality(Modality.WINDOW_MODAL);
            }
            fenetreForge.setScene(new Scene(root));
            ForgeController controller = loader.getController();
            controller.setStage(fenetreForge);
            controller.setJoueur(joueur);
            controller.setInventaireController(inventaireController);
            enPause = true;
            if (pauseOverlay != null) {
                pauseOverlay.setVisible(true);
            }
            fenetreForge.setOnHidden(e -> {
                enPause = false;
                if (pauseOverlay != null) {
                    pauseOverlay.setVisible(false);
                }
                if (inventaireController != null) {
                    inventaireController.rafraichir();
                }
                fenetreForge = null;
            });
            fenetreForge.show();
        } catch (IOException e) {
            System.err.println("Forge non chargée : " + e.getMessage());
        }
    }

    private void ouvrirDialogueGuide() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/interface/dialogue.fxml"));
            Pane root = loader.load();
            Stage stage = new Stage();
            if (pauseOverlay != null && pauseOverlay.getScene() != null) {
                stage.initOwner(pauseOverlay.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
            } else {
                stage.initOwner(null);
            }
            stage.setScene(new Scene(root));
            DialogueController controller = loader.getController();
            controller.setStage(stage);
            controller.setMessage(
                    "Oh… vous venez d’arriver, n’est-ce pas ? Vous avez l’air un peu désorienté." +
                            "\nHeureusement, le village n’est pas loin." +
                            "\nMais soyez tout de même vigilant. Certains animaux… moins amicaux que moi, rôdent parfois — des loups surtout." +
                            "\nCherchez le forgeron du village. C’est un homme honnête. Si vous lui apportez quelques ressources de la forêt, il vous fournira des armes solides." +
                            "\nChaque aventure commence par de petites rencontres… et la vôtre ne fait que commencer. Bon courage.");
            stage.show();
        } catch (IOException e) {
            System.err.println("Dialogue non chargé : " + e.getMessage());
        }
    }
}
