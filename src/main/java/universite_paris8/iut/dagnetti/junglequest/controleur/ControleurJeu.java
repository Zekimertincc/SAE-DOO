package universite_paris8.iut.dagnetti.junglequest.controleur;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.BarreVie;

import static universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu.*;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.controleur.moteur.MoteurPhysique;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Loup;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.BlocManager;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.animation.GestionAnimation;
import javafx.scene.image.WritableImage;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ParametresController;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final Loup loup;
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

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private double offsetX = 0;
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

    /**
     * Initialise le contrôleur principal du jeu : clavier, animation, logique du joueur et gestion des clics.
     */
    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur,Loup loup, InventaireController inventaireController, BarreVie barreVie, javafx.scene.control.Label labelVie, BarreVie barreVieLoup, javafx.scene.control.Label labelVieLoup, Pane pauseOverlay,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.loup = loup;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.labelVie = labelVie;
        this.barreVieLoup = barreVieLoup;
        this.labelVieLoup = labelVieLoup;
        this.pauseOverlay = pauseOverlay;
        if (this.pauseOverlay != null) {
            this.pauseOverlay.setVisible(false);
        }
        this.clavier = new GestionClavier(scene);
        this.largeurEcran = scene.getWidth();

        Image imgLoup = loup.getSprite().getImage();
        this.largeurFrameLoup = (int) (imgLoup.getWidth() / 6);
        this.hauteurFrameLoup = (int) imgLoup.getHeight();
        Image imgLoupAttack = loup.getAttackImage();
        this.largeurFrameLoupAttaque = (int) (imgLoupAttack.getWidth() / 6);
        this.hauteurFrameLoupAttaque = (int) imgLoupAttack.getHeight();
        loup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        loup.getSprite().setFitWidth(largeurFrameLoup);
        loup.getSprite().setFitHeight(hauteurFrameLoup);

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
                    double xMonde = e.getX() + offsetX;
                    double yMonde = e.getY();
                    boolean cliqueLoup = xMonde >= loup.getX() && xMonde <= loup.getX() + loup.getSprite().getFitWidth()
                            && yMonde >= loup.getY() && yMonde <= loup.getY() + loup.getSprite().getFitHeight();

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
        if (enPause) {
            return;
        }
        if (delaiDegats > 0) delaiDegats--;
        if (framesAtterrissageRestants > 0) framesAtterrissageRestants--;
        // Récupération des touches clavier pressées
        boolean gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        boolean droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        boolean toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        boolean toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        boolean toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        if (toucheBouclier && !joueur.estEnAttaque()) {
            joueur.activerBouclier();
        } else if (!toucheBouclier) {
            joueur.desactiverBouclier();
        }
        boolean toucheDegats = clavier.estAppuyee(KeyCode.M);

        // Gestion de la sélection des objets de l'inventaire
        if (inventaireController != null) {
            if (clavier.estAppuyee(KeyCode.DIGIT1)) inventaireController.selectionnerIndex(0);
            else if (clavier.estAppuyee(KeyCode.DIGIT2)) inventaireController.selectionnerIndex(1);
            else if (clavier.estAppuyee(KeyCode.DIGIT3)) inventaireController.selectionnerIndex(2);
            else if (clavier.estAppuyee(KeyCode.DIGIT4)) inventaireController.selectionnerIndex(3);
            else if (clavier.estAppuyee(KeyCode.DIGIT5)) inventaireController.selectionnerIndex(4);
            else if (clavier.estAppuyee(KeyCode.DIGIT6)) inventaireController.selectionnerIndex(5);
            else if (clavier.estAppuyee(KeyCode.DIGIT7)) inventaireController.selectionnerIndex(6);
            else if (clavier.estAppuyee(KeyCode.DIGIT8)) inventaireController.selectionnerIndex(7);
            else if (clavier.estAppuyee(KeyCode.DIGIT9)) inventaireController.selectionnerIndex(8);
            if (clavier.estAppuyee(KeyCode.E)) inventaireController.deselectionner();
        }

        if (toucheDegats) {
            joueur.subirDegats(1);
        }

        if (joueur.getPointsDeVie() <= 0) {
            joueurMort = true;
        }

        // Déplacement horizontal
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

        // Saut
        if (!joueurMort && toucheSaut && joueur.estAuSol() && !joueur.isBouclierActif()) {
            joueur.sauter(IMPULSION_SAUT);
        }

        boolean aAtterri = moteur.mettreAJourPhysique(joueur, carte);
        if (aAtterri) {
            framesAtterrissageRestants = animation.getNbFramesAtterrissage();
        }

        // Mise à jour du loup
        if (!loupMort) {
            loup.mettreAJourIA(joueur);
            moteur.mettreAJourPhysique(loup, carte);
        } else {
            loup.arreter();
        }

        offsetX = joueur.getX() - largeurEcran / 2;
        if (offsetX < 0) offsetX = 0;
        double maxOffset = carte.getLargeur() * TAILLE_TUILE -largeurEcran;
        if(offsetX > maxOffset)
            offsetX = maxOffset;

        // Redessiner la carte avec le nouveau décalage
        carteAffichable.redessiner(offsetX);

        // Redessiner le fond si présent
        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }
        joueur.getSprite().setX(joueur.getX() - offsetX);
        loup.getSprite().setX(loup.getX() - offsetX);
        loup.getSprite().setY(loup.getY());
        barreVie.setLayoutX(joueur.getX() - offsetX);
        barreVie.setLayoutY(joueur.getY() - 10);
        labelVie.setLayoutX(joueur.getX() - offsetX);
        labelVie.setLayoutY(joueur.getY() - 25);
        labelVie.setText(Integer.toString(joueur.getPointsDeVie()));
        double ratioVie = joueur.getPointsDeVie() / (double) VIE_MAX_JOUEUR;
        barreVie.mettreAJour(ratioVie);

        if (!loupMort) {
            barreVieLoup.setLayoutX(loup.getX() - offsetX);
            barreVieLoup.setLayoutY(loup.getY() - 10);
            labelVieLoup.setLayoutX(loup.getX() - offsetX);
            labelVieLoup.setLayoutY(loup.getY() - 25);
            labelVieLoup.setText(Integer.toString(loup.getPointsDeVie()));
            double ratioVieLoup = loup.getPointsDeVie() / (double) VIE_MAX_LOUP;
            barreVieLoup.mettreAJour(ratioVieLoup);
        } else {
            barreVieLoup.setVisible(false);
            labelVieLoup.setVisible(false);
        }

        double distanceLoupJoueur = Math.abs(joueur.getX() - loup.getX());
        boolean collision = distanceLoupJoueur < 20
                && joueur.getX() < loup.getX() + loup.getSprite().getFitWidth()
                && joueur.getX() + joueur.getSprite().getFitWidth() > loup.getX()
                && joueur.getY() < loup.getY() + loup.getSprite().getFitHeight()
                && joueur.getY() + joueur.getSprite().getFitHeight() > loup.getY();
        if (!loupMort && collision && delaiDegats == 0 && !loup.estEnAttaque() && !joueur.isBouclierActif()) {
            joueur.subirDegats(loup.getDegats());

            // Durée pendant laquelle le joueur reste en animation de dégâts
            delaiDegats = DUREE_DEGATS_JOUEUR;
            loup.attaquer(joueur.getX());
            if (joueur.getPointsDeVie() <= 0) {
                joueurMort = true;
            }
        }

        // Gestion des animations
        ImageView sprite = joueur.getSprite();

        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
            sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
            return;
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

        // Inversion du sprite si le joueur regarde à gauche
        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
        // Animation et orientation du loup
        if (loupMort) {
            loup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        } else if (loup.estEnAttaque()) {
            if (compteurLoup++ >= DELAI_FRAME) {
                loup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoupAttaque, 0,
                        largeurFrameLoupAttaque, hauteurFrameLoupAttaque));
                frameLoup++;
                compteurLoup = 0;
                if (frameLoup >= 6) {
                    frameLoup = 0;
                    loup.finAttaque();
                    loup.getSprite().setImage(loup.getWalkImage());
                }
            }
        } else if (loup.getVitesseX() != 0) {
            if (compteurLoup++ >= DELAI_FRAME) {
                loup.getSprite().setViewport(new Rectangle2D(frameLoup * largeurFrameLoup, 0, largeurFrameLoup, hauteurFrameLoup));
                frameLoup = (frameLoup + 1) % 6;
                compteurLoup = 0;
            }
        } else {
            loup.getSprite().setViewport(new Rectangle2D(0, 0, largeurFrameLoup, hauteurFrameLoup));
        }
        loup.getSprite().setScaleX(loup.estVersGauche() ? 1 : -1);
    }

    /**
     * Permet de lier dynamiquement la vue du fond à ce contrôleur.
     */
    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
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
}
