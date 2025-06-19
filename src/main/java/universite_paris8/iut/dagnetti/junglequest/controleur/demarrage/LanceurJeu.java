package universite_paris8.iut.dagnetti.junglequest.controleur.demarrage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import universite_paris8.iut.dagnetti.junglequest.controleur.ControleurJeu;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.DialogueController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.HacheEchangeController;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.ForgeController;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.ChargeurCarte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Loup;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Guide;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Forgeron;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.ExtracteurSprites;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.PositionFrame;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.BarreVie;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;

public class LanceurJeu extends Application {

    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        System.out.println("Initialisation du jeu Jungle Quest...");
        initialiserMusique();

        Rectangle2D ecran = Screen.getPrimary().getBounds();
        double largeur = ecran.getWidth();
        double hauteur = ecran.getHeight();

        Pane racine;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/VueJeu.fxml"));
            racine = loader.load();
        } catch (IOException e) {
            racine = new Pane();
        }
        Scene scene = new Scene(racine, largeur, hauteur);
        Pane pauseOverlay = (Pane) scene.lookup("#pauseOverlay");
        if (pauseOverlay != null) {
            pauseOverlay.prefWidthProperty().bind(scene.widthProperty());
            pauseOverlay.prefHeightProperty().bind(scene.heightProperty());
        }

        try {
            int[][] grille = ChargeurCarte.chargerCarteDepuisCSV("/universite_paris8/iut/dagnetti/junglequest/cartes/jungle_map_calque1.csv");
            Carte carte = new Carte(grille);
            System.out.println("Carte chargée avec succès.");

            InputStream tilesetStream = getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/tileset_jungle.png");
            if (tilesetStream == null) {
                throw new IOException("Ressource tileset_jungle.png introuvable");
            }
            Image tileset = new Image(tilesetStream);
            if (tileset.isError()) System.err.println("Erreur de chargement du tileset.");
            else System.out.println("Tileset jungle chargé.");


            CarteAffichable carteAffichable = new CarteAffichable(carte, tileset, (int) largeur, (int) hauteur);
            int largeurCartePx = carte.getLargeur() * ConstantesJeu.TAILLE_TUILE;
            VueBackground vueBackground = new VueBackground((int) largeur, (int) hauteur, largeurCartePx);
            System.out.println("Background dynamique initialisé.");

            racine.getChildren().add(vueBackground);

            System.out.println("Extraction des animations du personnage...");
            InputStream personnageStream = getClass().getResourceAsStream("/universite_paris8/iut/dagnetti/junglequest/images/sprite1.png");
            if (personnageStream == null) {
                throw new IOException("Ressource sprite1.png introuvable");
            }
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
            System.out.println("Animations extraites.");

            double xInitial = 320;
            int colonne = (int) (xInitial / ConstantesJeu.TAILLE_TUILE);
            int ligneSol = carte.chercherLigneSol(colonne);
            // La carte est stockée comme base en haut à gauche. Pour placer le
            // personnage sur le sol trouvé, on se base directement sur l'indice de
            // ligne plutôt que d'inverser par rapport à la hauteur de la carte.
            double yInitial = ligneSol != -1 ? ligneSol * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE : 56;
            System.out.printf("Position initiale du joueur : (%.0f, %.0f)\n", xInitial, yInitial);

            ImageView spriteJoueur = new ImageView(idle[0]);
            spriteJoueur.setFitWidth(ConstantesJeu.TAILLE_SPRITE);
            spriteJoueur.setFitHeight(ConstantesJeu.TAILLE_SPRITE);

            Joueur joueur = new Joueur(spriteJoueur, xInitial, yInitial);
            joueur.setEstAuSol(true);
            racine.getChildren().addAll(carteAffichable, spriteJoueur);

            // --- Guide et Forgeron ---
            Image imgGuide = new Image(getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/guide.png"));
            WritableImage[] framesGuide = ExtracteurSprites.extraireLigne(
                    imgGuide,
                    ConstantesJeu.NB_FRAMES_GUIDE,
                    ConstantesJeu.LARGEUR_GUIDE,
                    ConstantesJeu.HAUTEUR_GUIDE);
            ImageView spriteGuide = new ImageView(framesGuide[0]);
            spriteGuide.setFitWidth(ConstantesJeu.LARGEUR_GUIDE);
            spriteGuide.setFitHeight(ConstantesJeu.HAUTEUR_GUIDE);
            double xGuide = ConstantesJeu.POSITION_GUIDE_X;
            int colGuide = (int) (xGuide / ConstantesJeu.TAILLE_TUILE);
            int ligneSolGuide = carte.chercherLigneSol(colGuide);
            double yGuide = ligneSolGuide != -1
                    ? ligneSolGuide * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.HAUTEUR_GUIDE
                    : yInitial;
            Guide guide = new Guide(spriteGuide, xGuide, yGuide);
            guide.setEstAuSol(true);
            racine.getChildren().add(spriteGuide);
            spriteGuide.setOnMouseClicked(e -> ouvrirDialogue());


            // Animation du guide
            final int[] indexGuide = {0};
            Timeline animGuide = new Timeline(new KeyFrame(Duration.millis(150), event -> {
                spriteGuide.setImage(framesGuide[indexGuide[0]]);
                indexGuide[0] = (indexGuide[0] + 1) % framesGuide.length;
            }));
            animGuide.setCycleCount(Animation.INDEFINITE);
            animGuide.play();

            Image imgForgeron = new Image(getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/forgeron.png"));
            WritableImage[] framesForgeron = ExtracteurSprites.extraireLigne(
                    imgForgeron,
                    ConstantesJeu.NB_FRAMES_FORGERON,
                    ConstantesJeu.LARGEUR_FORGERON,
                    ConstantesJeu.HAUTEUR_FORGERON);
            ImageView spriteForgeron = new ImageView(framesForgeron[0]);
            spriteForgeron.setFitWidth(ConstantesJeu.LARGEUR_FORGERON);
            spriteForgeron.setFitHeight(ConstantesJeu.HAUTEUR_FORGERON);
            double xForgeron = ConstantesJeu.POSITION_FORGERON_X;
            int colForgeron = (int) (xForgeron / ConstantesJeu.TAILLE_TUILE);
            int ligneSolForgeron = carte.chercherLigneSol(colForgeron);
            double yForgeron = 380;
            Forgeron forgeron = new Forgeron(
                    spriteForgeron,
                    xForgeron,
                    yForgeron);
            forgeron.setEstAuSol(true);
            racine.getChildren().add(spriteForgeron);
            spriteForgeron.setOnMouseClicked(e -> ouvrirForge(joueur));

            // Animation du forgeron
            final int[] indexForgeron = {0};
            Timeline animForgeron = new Timeline(new KeyFrame(Duration.millis(150), event -> {
                spriteForgeron.setImage(framesForgeron[indexForgeron[0]]);
                indexForgeron[0] = (indexForgeron[0] + 1) % framesForgeron.length;
            }));
            animForgeron.setCycleCount(Animation.INDEFINITE);
            animForgeron.play();


            Image imgLoupWalk = new Image(getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/black_wolf_walk.png"));
            Image imgLoupRun = new Image(getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/black_wolf_run.png"));
            Image imgLoupAttack = new Image(getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/black_wolf_attack.png"));
            ImageView spriteLoup = new ImageView(imgLoupWalk);
            spriteLoup.setFitWidth(imgLoupWalk.getWidth());
            spriteLoup.setFitHeight(imgLoupWalk.getHeight());
            double xLoup = 1500;
            int colLoup = (int) (xLoup / ConstantesJeu.TAILLE_TUILE);
            int ligneSolLoup = carte.chercherLigneSol(colLoup);
            // Même principe que pour le joueur : placement direct par rapport à
            // l'indice de ligne trouvé.
            double yLoup = ligneSolLoup != -1
                    ? ligneSolLoup * ConstantesJeu.TAILLE_TUILE - imgLoupWalk.getHeight()
                    : 56;
            // Le loup inflige désormais 20 points de dégâts par attaque
            Loup loup = new Loup(spriteLoup, imgLoupWalk, imgLoupRun, imgLoupAttack, xLoup, yLoup, 20);

            loup.setEstAuSol(true);
            racine.getChildren().add(spriteLoup);

            BarreVie barreVieLoup = new BarreVie(ConstantesJeu.TAILLE_SPRITE * 1.5, 4);

            barreVieLoup.setViewOrder(-9);
            racine.getChildren().add(barreVieLoup);
            javafx.scene.control.Label labelVieLoup = new javafx.scene.control.Label(Integer.toString(loup.getPointsDeVie()));
            labelVieLoup.setTextFill(javafx.scene.paint.Color.WHITE);
            labelVieLoup.setViewOrder(-9);
            racine.getChildren().add(labelVieLoup);

            BarreVie barreVie = new BarreVie(ConstantesJeu.TAILLE_SPRITE * 1.5, 4);

            barreVie.setViewOrder(-9);
            racine.getChildren().add(barreVie);
            javafx.scene.control.Label labelVie = new javafx.scene.control.Label(Integer.toString(joueur.getPointsDeVie()));
            labelVie.setTextFill(javafx.scene.paint.Color.WHITE);
            labelVie.setViewOrder(-9);
            racine.getChildren().add(labelVie);
            InventaireController inventaireCtrl = afficherInventaire(racine, joueur, largeur, hauteur);

            ControleurJeu controleurJeu = new ControleurJeu(scene, carte, carteAffichable, joueur, loup,
                    guide, forgeron, inventaireCtrl, barreVie, labelVie, barreVieLoup, labelVieLoup, pauseOverlay,
                    idle, marche, attaque, preparationSaut, volSaut, sautReload,
                    chute, atterrissage, degats, mort, sort, accroupi, bouclier);

            controleurJeu.setVueBackground(vueBackground);

        } catch (IOException e) {
            System.err.println("Erreur critique : " + e.getMessage());
            e.printStackTrace();
        }

        stage.setTitle("Jungle Quest");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
        System.out.println("Jeu lancé avec succès.");
    }

    private void initialiserMusique() {
        try {
            URL ressourceAudio = getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/sons/musique_jeu.mp3");
            if (ressourceAudio != null) {
                Media media = new Media(ressourceAudio.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.25);
                mediaPlayer.play();
                System.out.println("Musique de fond lancée.");
            }
        } catch (Exception e) {
            System.err.println("Erreur musique : " + e.getMessage());
        }
    }

    private InventaireController afficherInventaire (Pane racine, Joueur joueur, double largeur, double hauteur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/vue/interface/Inventaire.fxml"));
            Node inventaireUI = loader.load();
            InventaireController inventaireController = loader.getController();
            inventaireController.setInventaire(joueur.getInventaire());
            inventaireUI.setLayoutX(10);
            inventaireUI.setLayoutY(10);
            inventaireUI.setViewOrder(-10);
            racine.getChildren().add(inventaireUI);
            System.out.println("Interface d'inventaire chargée.");
            return inventaireController;
        } catch (IOException e) {
            System.err.println("Inventaire UI non chargé : " + e.getMessage());
            return null;
        }
    }
    private void ouvrirDialogue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/vue/interface/dialogue.fxml"));
            Pane root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(null);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            DialogueController controller = loader.getController();
            controller.setStage(stage);
            controller.setMessage("Bonjour voyageur !");
            stage.show();
        } catch (IOException e) {
            System.err.println("Dialogue non chargé : " + e.getMessage());
        }
    }

    private void ouvrirForge(Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/dagnetti/junglequest/vue/interface/Forge.fxml"));
            Pane root = loader.load();
            Stage stage = new Stage();
            stage.initOwner(null);
            stage.setScene(new Scene(root));
            ForgeController controller = loader.getController();
            controller.setStage(stage);
            controller.setJoueur(joueur);
            stage.show();
        } catch (IOException e) {
            System.err.println("Forge non chargée : " + e.getMessage());
        }
    }

    private List<PositionFrame> creerListeFrames(int debutCol, int ligne, int finCol, int ligneFin) {
        List<PositionFrame> frames = new ArrayList<>();
        for (int l = ligne; l <= ligneFin; l++) {
            int start = (l == ligne) ? debutCol : 0;
            int end = (l == ligneFin) ? finCol : 7;
            for (int c = start; c <= end; c++) {
                frames.add(new PositionFrame(c, l));
            }
        }
        return frames;
    }

    private List<PositionFrame> concatFrames(List<PositionFrame> a, List<PositionFrame> b) {
        List<PositionFrame> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }
}
