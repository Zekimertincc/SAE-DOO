package fr.iut.groupe.junglequest.controleur;

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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;
import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.vue.utilitaire.BarreVie;
import fr.iut.groupe.junglequest.vue.personnages.VueJoueur;
import fr.iut.groupe.junglequest.vue.personnages.VueLoup;
import fr.iut.groupe.junglequest.vue.CarteAffichable;
import fr.iut.groupe.junglequest.vue.VueBackground;
import fr.iut.groupe.junglequest.vue.animation.GestionAnimation;
import fr.iut.groupe.junglequest.controleur.moteur.MoteurPhysique;
import fr.iut.groupe.junglequest.controleur.interfacefx.InventaireController;
import fr.iut.groupe.junglequest.controleur.interfacefx.ParametresController;
import fr.iut.groupe.junglequest.controleur.interfacefx.DialogueController;
import fr.iut.groupe.junglequest.controleur.interfacefx.ForgeController;
import fr.iut.groupe.junglequest.modele.bloc.BlocManager;
import fr.iut.groupe.junglequest.modele.bloc.TileType;
import fr.iut.groupe.junglequest.modele.farm.Ressource;

import static fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Environnement environnement;
    private final CarteAffichable carteAffichable;
    private final VueJoueur vueJoueur;
    private final VueLoup vueLoup;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private final InventaireController inventaireController;
    private final BarreVie barreVie;
    private final javafx.scene.control.Label labelVie;
    private final BarreVie barreVieLoup;
    private final javafx.scene.control.Label labelVieLoup;
    private final Pane pauseOverlay;
    private VueBackground vueBackground;
    private final double largeurEcran;
    private final double hauteurEcran;

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private final DoubleProperty offsetXProperty = new SimpleDoubleProperty();
    private final DoubleProperty offsetYProperty = new SimpleDoubleProperty();
    private int framesAtterrissageRestants = 0;
    private int delaiDegats = 0;

    private boolean joueurMort = false;
    private boolean loupMort = false;

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

    private boolean gauche;
    private boolean droite;
    private boolean toucheSaut;
    private boolean toucheAccroupi;
    private boolean toucheBouclier;
    private boolean toucheDegats;

    public ControleurJeu(Scene scene, Environnement environnement,
                         CarteAffichable carteAffichable, VueJoueur vueJoueur,
                         VueLoup vueLoup,
                         InventaireController inventaireController, BarreVie barreVie, javafx.scene.control.Label labelVie,
                         BarreVie barreVieLoup, javafx.scene.control.Label labelVieLoup, Pane pauseOverlay,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.environnement = environnement;
        this.carteAffichable = carteAffichable;
        this.vueJoueur = vueJoueur;
        this.vueLoup = vueLoup;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.labelVie = labelVie;
        this.barreVieLoup = barreVieLoup;
        this.labelVieLoup = labelVieLoup;
        this.pauseOverlay = pauseOverlay;

        if (this.labelVie != null) {
            this.labelVie.textProperty().bind(environnement.getJoueur().pointsDeVieProperty().asString());
        }
        if (this.barreVie != null) {
            this.barreVie.ratioProperty().bind(environnement.getJoueur().pointsDeVieProperty().divide((double) VIE_MAX_JOUEUR));
        }
        if (this.labelVieLoup != null) {
            this.labelVieLoup.textProperty().bind(environnement.getLoup().pointsDeVieProperty().asString());
        }
        if (this.barreVieLoup != null) {
            this.barreVieLoup.ratioProperty().bind(environnement.getLoup().pointsDeVieProperty().divide((double) VIE_MAX_LOUP));
        }

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
        Image imgLoupAttack = environnement.getLoup().getAttackImage();
        this.largeurFrameLoupAttaque = (int) (imgLoupAttack.getWidth() / 6);
        this.hauteurFrameLoupAttaque = (int) imgLoupAttack.getHeight();
        vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        vueLoup.getSprite().setFitWidth(largeurFrameLoup);
        vueLoup.getSprite().setFitHeight(hauteurFrameLoup);

        this.animation = new GestionAnimation(
                idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier
        );

        // clic gauche → attaque
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double xMonde = e.getX() + offsetXProperty.get();
                double yMonde = e.getY() + offsetYProperty.get();

                if (essayerCasserRessource(xMonde, yMonde)) return;

                boolean cliqueLoup = xMonde >= environnement.getLoup().getX()
                        && xMonde <= environnement.getLoup().getX() + vueLoup.getSprite().getFitWidth()
                        && yMonde >= environnement.getLoup().getY()
                        && yMonde <= environnement.getLoup().getY() + vueLoup.getSprite().getFitHeight();
                double distance = Math.abs(environnement.getJoueur().getX() - environnement.getLoup().getX());

                if (!environnement.getJoueur().estEnAttaque()) {
                    environnement.getJoueur().attaquer();
                    animation.reset();
                    compteurAttaque = 0;
                    if (distance <= PORTEE_ATTAQUE_JOUEUR && cliqueLoup) {
                        environnement.getLoup().subirDegats(DEGATS_JOUEUR_LOUP);
                        if (environnement.getLoup().getPointsDeVie() <= 0) loupMort = true;
                    }
                } else {
                    animation.demandeCombo();
                    if (distance <= PORTEE_ATTAQUE_JOUEUR && cliqueLoup) {
                        environnement.getLoup().subirDegats(DEGATS_COMBO_SUPPLEMENTAIRES);
                        if (environnement.getLoup().getPointsDeVie() <= 0) loupMort = true;
                    }
                }

                if (environnement.getJoueur().isBouclierActif()) {
                    environnement.getJoueur().desactiverBouclier();
                }
            }
        });

        // clic droit → interactions
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                gererClicDroit(e.getX(), e.getY());
            }
        });

        // touches clavier
        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.P) {
                if (fenetreParametres == null) ouvrirParametres(scene);
            } else if (e.getCode() == KeyCode.I) {
                if (inventaireController != null) inventaireController.basculerAffichage();
            } else if (e.getCode() == KeyCode.C) {
                double distanceForgeron = Math.abs(environnement.getJoueur().getX() - environnement.getForgeron().getX());
                if (distanceForgeron <= 40 && fenetreForge == null) ouvrirForge(scene);
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
                if (pauseOverlay != null) pauseOverlay.setVisible(enPause);
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJour();
            }
        }.start();
    }

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

        if (toucheBouclier && !environnement.getJoueur().estEnAttaque()) {
            environnement.getJoueur().activerBouclier();
        } else if (!toucheBouclier) {
            environnement.getJoueur().desactiverBouclier();
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
            environnement.getJoueur().subirDegats(1);
        }

        if (environnement.getJoueur().getPointsDeVie() <= 0) {
            joueurMort = true;
        }
    }

    // ---------------------
    // eksik metodlar buraya
    // ---------------------

    private void mettreAJourMouvements() {
        if (!joueurMort && !environnement.getJoueur().isBouclierActif()) {
            if (gauche) {
                environnement.getJoueur().deplacerGauche(VITESSE_JOUEUR);
            } else if (droite) {
                environnement.getJoueur().deplacerDroite(VITESSE_JOUEUR);
            } else {
                environnement.getJoueur().arreter();
            }
        } else {
            environnement.getJoueur().arreter();
        }

        if (!joueurMort && toucheSaut && environnement.getJoueur().estAuSol() && !environnement.getJoueur().isBouclierActif()) {
            environnement.getJoueur().sauter(IMPULSION_SAUT);
        }

        boolean aAtterri = moteur.mettreAJourPhysique(environnement.getJoueur(), environnement.getCarte(),
                vueJoueur.getLargeur(), vueJoueur.getHauteur());
        if (aAtterri) {
            framesAtterrissageRestants = animation.getNbFramesAtterrissage();
        }

        if (!loupMort) {
            environnement.getLoup().mettreAJourIA(environnement.getJoueur(), environnement.getCarte());
            moteur.mettreAJourPhysique(environnement.getLoup(), environnement.getCarte(),
                    vueLoup.getLargeur(), vueLoup.getHauteur());
        } else {
            environnement.getLoup().arreter();
        }
    }

    private void mettreAJourAffichageEtAnimations() {
        double offsetX = environnement.getJoueur().getX() - largeurEcran / 2;
        if (offsetX < 0) offsetX = 0;
        double maxOffset = environnement.getCarte().getLargeur() * TAILLE_TUILE - largeurEcran;
        if (offsetX > maxOffset) offsetX = maxOffset;
        offsetXProperty.set(offsetX);

        double offsetY = environnement.getJoueur().getY() - hauteurEcran / 2;
        if (offsetY < 0) offsetY = 0;
        double maxOffsetY = environnement.getCarte().getHauteur() * TAILLE_TUILE - hauteurEcran;
        if (offsetY > maxOffsetY) offsetY = maxOffsetY;
        offsetYProperty.set(offsetY);

        carteAffichable.redessiner(offsetX, offsetY);

        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }

        environnement.getGuide().getSprite().setX(environnement.getGuide().getX() - offsetX);
        environnement.getGuide().getSprite().setY(environnement.getGuide().getY() - offsetY);
        environnement.getForgeron().getSprite().setX(environnement.getForgeron().getX() - offsetX);
        environnement.getForgeron().getSprite().setY(environnement.getForgeron().getY() - offsetY);

        for (Ressource r : environnement.getRessources()) {
            r.getSprite().setX(r.getX() - offsetX);
            r.getSprite().setY(r.getY() - offsetY);
        }

        // vie joueur
        barreVie.setLayoutX(environnement.getJoueur().getX() - offsetX);
        barreVie.setLayoutY(environnement.getJoueur().getY() - offsetY - 10);
        labelVie.setLayoutX(environnement.getJoueur().getX() - offsetX);
        labelVie.setLayoutY(environnement.getJoueur().getY() - offsetY - 25);

        if (!dialogueGuideAffiche) {
            double distanceGuide = Math.abs(environnement.getJoueur().getX() - environnement.getGuide().getX());
            if (distanceGuide <= 40) {
                ouvrirDialogueGuide();
                dialogueGuideAffiche = true;
            }
        }

        // vie loup
        if (!loupMort) {
            barreVieLoup.setLayoutX(environnement.getLoup().getX() - offsetX);
            barreVieLoup.setLayoutY(environnement.getLoup().getY() - offsetY - 10);
            labelVieLoup.setLayoutX(environnement.getLoup().getX() - offsetX);
            labelVieLoup.setLayoutY(environnement.getLoup().getY() - offsetY - 25);
        } else {
            barreVieLoup.setVisible(false);
            labelVieLoup.setVisible(false);
        }

        // collisions / animations
        double distanceLoupJoueur = Math.abs(environnement.getJoueur().getX() - environnement.getLoup().getX());
        boolean collision = distanceLoupJoueur < 20
                && environnement.getJoueur().getX() < environnement.getLoup().getX() + vueLoup.getSprite().getFitWidth()
                && environnement.getJoueur().getX() + vueJoueur.getSprite().getFitWidth() > environnement.getLoup().getX()
                && environnement.getJoueur().getY() < environnement.getLoup().getY() + vueLoup.getSprite().getFitHeight()
                && environnement.getJoueur().getY() + vueJoueur.getSprite().getFitHeight() > environnement.getLoup().getY();

        if (!loupMort && collision && delaiDegats == 0 && !environnement.getLoup().estEnAttaque() && !environnement.getJoueur().isBouclierActif()) {
            environnement.getJoueur().subirDegats(environnement.getLoup().getDegats());
            delaiDegats = DUREE_DEGATS_JOUEUR;
            environnement.getLoup().attaquer(environnement.getJoueur().getX());
            if (environnement.getJoueur().getPointsDeVie() <= 0) joueurMort = true;
        }

        ImageView sprite = vueJoueur.getSprite();

        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
            sprite.setScaleX(environnement.getJoueur().estVersGauche() ? -1 : 1);
        } else if (toucheDegats || delaiDegats > 0) {
            animation.animerDegats(sprite);
        } else if (environnement.getJoueur().estEnAttaque()) {
            animation.animerAttaque(sprite, Math.max(1, DELAI_FRAME - 2), () -> environnement.getJoueur().finAttaque());
            compteurAttaque++;
        } else if (!environnement.getJoueur().estAuSol()) {
            animation.animerSaut(sprite, environnement.getJoueur().getVitesseY());
        } else if (framesAtterrissageRestants > 0) {
            int frame = animation.getNbFramesAtterrissage() - framesAtterrissageRestants;
            animation.animerAtterrissage(sprite, frame);
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (environnement.getJoueur().isBouclierActif()) {
            animation.animerBouclier(sprite);
        } else if (environnement.getJoueur().getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        sprite.setScaleX(environnement.getJoueur().estVersGauche() ? -1 : 1);

        if (loupMort) {
            vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        } else if (environnement.getLoup().estEnAttaque()) {
            if (compteurLoup++ >= DELAI_FRAME) {
                vueLoup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoupAttaque, 0,
                        largeurFrameLoupAttaque, hauteurFrameLoupAttaque));
                frameLoup++;
                compteurLoup = 0;
                if (frameLoup >= 6) {
                    frameLoup = 0;
                    environnement.getLoup().finAttaque();
                    vueLoup.getSprite().setImage(environnement.getLoup().getWalkImage());
                }
            }
        } else if (environnement.getLoup().getVitesseX() != 0) {
            if (compteurLoup++ >= DELAI_FRAME) {
                vueLoup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoup, 0, largeurFrameLoup, hauteurFrameLoup));
                frameLoup = (frameLoup + 1) % 6;
                compteurLoup = 0;
            }
        } else {
            vueLoup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        }
        vueLoup.getSprite().setScaleX(environnement.getLoup().estVersGauche() ? 1 : -1);
    }


    private boolean essayerCasserRessource(double xMonde, double yMonde) {
        Iterator<Ressource> it = environnement.getRessources().iterator();
        while (it.hasNext()) {
            Ressource r = it.next();
            ImageView iv = r.getSprite();
            double w = iv.getFitWidth();
            double h = iv.getFitHeight();

            boolean icinde = xMonde >= r.getX() && xMonde <= r.getX() + w
                    && yMonde >= r.getY() && yMonde <= r.getY() + h;
            if (!icinde) continue;

            boolean peutCasser = r.getNom().equalsIgnoreCase("Arbre")
                    || environnement.getJoueur().getInventaire().contient("Hache", 1);

            if (peutCasser) {
                iv.setVisible(false);
                it.remove();
                environnement.getJoueur().getInventaire().ajouterItem(r.getItemRecompense(), 1);
                if (inventaireController != null) {
                    inventaireController.rafraichir();
                }
            }
            return true; // bir ressource’a tıklandı (kırılsa da kırılmasa da)
        }
        return false;
    }


    private void gererClicDroit(double xScene, double yScene) {
        double xMonde = xScene + offsetXProperty.get();
        double yMonde = yScene + offsetYProperty.get();

        if (essayerCasserRessource(xMonde, yMonde)) return;

        int colonne = (int) (xMonde / TAILLE_TUILE);
        int ligne   = (int) (yMonde / TAILLE_TUILE);

        boolean dansCarte = colonne >= 0 && colonne < environnement.getCarte().getLargeur()
                && ligne   >= 0 && ligne   < environnement.getCarte().getHauteur();
        if (!dansCarte) return;

        String selection = (inventaireController != null) ? inventaireController.getItemSelectionne() : null;

        if (selection != null) {
            TileType type = switch (selection.toLowerCase()) {
                case "bois"  -> TileType.ARBRE;
                case "terre" -> TileType.TERRE;
                case "herbe" -> TileType.HERBE;
                default      -> null;
            };
            if (type != null && environnement.getJoueur().getInventaire().retirerItem(selection, 1)) {
                environnement.getCarte().setValeurTuile(ligne, colonne, type.getId());
            }
            if (inventaireController != null) {
                inventaireController.deselectionner();
                inventaireController.rafraichir();
            }
        } else {
            int idAvant = environnement.getCarte().getValeurTuile(ligne, colonne);
            TileType typeAvant = TileType.fromId(idAvant);

            // bloğu kır ve drop ver
            if (BlocManager.casserBloc(environnement.getCarte(), ligne, colonne) && typeAvant != null) {
                if (typeAvant == TileType.ARBRE) {
                    environnement.getJoueur().getInventaire().ajouterItem("Bois", 1);
                    if (inventaireController != null) inventaireController.rafraichir();
                }
            }
        }

        carteAffichable.redessiner(offsetXProperty.get(), offsetYProperty.get());
    }


    private void ouvrirParametres(Scene scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fr/iut/groupe/junglequest/vue/interface/Parametres.fxml"));
            Parent root = loader.load();

            fenetreParametres = new Stage();
            fenetreParametres.initOwner(scene.getWindow());
            fenetreParametres.initModality(Modality.WINDOW_MODAL);
            fenetreParametres.setTitle("Paramètres");
            fenetreParametres.setScene(new Scene(root));

            ParametresController controller = loader.getController();
            controller.setStage(fenetreParametres);

            enPause = true;
            if (pauseOverlay != null) pauseOverlay.setVisible(true);

            fenetreParametres.setOnHidden(e -> {
                enPause = false;
                if (pauseOverlay != null) pauseOverlay.setVisible(false);
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
                    "/fr/iut/groupe/junglequest/vue/interface/Forge.fxml"));
            Pane root = loader.load();

            fenetreForge = new Stage();
            if (scene.getWindow() != null) {
                fenetreForge.initOwner(scene.getWindow());
                fenetreForge.initModality(Modality.WINDOW_MODAL);
            }
            fenetreForge.setScene(new Scene(root));

            ForgeController controller = loader.getController();
            controller.setStage(fenetreForge);
            controller.setJoueur(environnement.getJoueur());
            controller.setInventaireController(inventaireController);

            enPause = true;
            if (pauseOverlay != null) pauseOverlay.setVisible(true);

            fenetreForge.setOnHidden(e -> {
                enPause = false;
                if (pauseOverlay != null) pauseOverlay.setVisible(false);
                if (inventaireController != null) inventaireController.rafraichir();
                fenetreForge = null;
            });

            fenetreForge.show();
        } catch (IOException e) {
            System.err.println("Forge non chargée : " + e.getMessage());
        }
    }
    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
    }


    private void ouvrirDialogueGuide() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/fr/iut/groupe/junglequest/vue/interface/dialogue.fxml"));
            Pane root = loader.load();

            Stage stage = new Stage();
            if (pauseOverlay != null && pauseOverlay.getScene() != null) {
                stage.initOwner(pauseOverlay.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
            }
            stage.setScene(new Scene(root));

            DialogueController controller = loader.getController();
            controller.setStage(stage);
            controller.setMessage(
                    "Oh… vous venez d’arriver, n’est-ce pas ? Vous avez l’air un peu désorienté." +
                            "\nHeureusement, le village n’est pas loin." +
                            "\nMais soyez tout de même vigilant. Certains animaux… moins amicaux que moi, rôdent parfois — des loups surtout." +
                            "\nCherchez le forgeron du village. C’est un homme honnête. Si vous lui apportez quelques ressources de la forêt, il vous fournira des armes solides." +
                            "\nChaque aventure commence par de petites rencontres… et la vôtre ne fait que commencer. Bon courage."
            );

            stage.show();
        } catch (IOException e) {
            System.err.println("Dialogue non chargé : " + e.getMessage());
        }
    }

}
