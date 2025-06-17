package universite_paris8.iut.dagnetti.junglequest.controleur;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Loup;

import static universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu.*;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.controleur.moteur.MoteurPhysique;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.BlocManager;
import universite_paris8.iut.dagnetti.junglequest.modele.bloc.TileType;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.animation.GestionAnimation;
import javafx.scene.image.WritableImage;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ParametresController;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.Pathfinder;
import java.util.List;

public class ControleurJeu {

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
    private Stage fenetreParametres;

    // --- Gestion de la vie du bot ---
    private int botVie = 3;
    private int botDelaiCouleur = 0;
    private double opaciteLoupBase;

    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur,
                         InventaireController inventaireController, ProgressBar barreVie, Label labelVie, Pane pauseOverlay,
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
        if (this.pauseOverlay != null) {
            this.pauseOverlay.setVisible(false);
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
            } else if (e.getCode() == KeyCode.ENTER) {
                enPause = !enPause;
                if (pauseOverlay != null) {
                    pauseOverlay.setVisible(enPause);
                }
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
        if (enPause) return;
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
        labelVie.setText(Integer.toString(joueur.getPointsDeVie()));
        double ratioVie = joueur.getPointsDeVie() / (double) VIE_MAX_JOUEUR;
        barreVie.setProgress(ratioVie);
        Color couleurVie = Color.GREEN.interpolate(Color.RED, 1 - ratioVie);
        String hex = String.format("#%02X%02X%02X",
                (int) (couleurVie.getRed() * 255),
                (int) (couleurVie.getGreen() * 255),
                (int) (couleurVie.getBlue() * 255));
        barreVie.setStyle("-fx-accent: " + hex + ";");

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
