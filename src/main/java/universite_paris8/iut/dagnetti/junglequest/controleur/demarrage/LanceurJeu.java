package universite_paris8.iut.dagnetti.junglequest.controleur.demarrage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;

import universite_paris8.iut.dagnetti.junglequest.controleur.ControleurJeu;
import universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx.InventaireController;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.ChargeurCarte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.vue.VueBackground;
import universite_paris8.iut.dagnetti.junglequest.vue.utilitaire.ExtracteurSprites;
import universite_paris8.iut.dagnetti.junglequest.vue.CarteAffichable;

public class LanceurJeu extends Application {

    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        System.out.println("Initialisation du jeu Jungle Quest...");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/VueJeu.fxml"));
            racine = loader.load();
        } catch (IOException e) {
            racine = new Pane();
        }

        racine.setScaleX(scale);
        racine.setScaleY(scale);
        Scene scene = new Scene(racine, screenWidth, screenHeight);

        Pane pauseOverlay = (Pane) scene.lookup("#pauseOverlay");
        if (pauseOverlay != null) {
            pauseOverlay.prefWidthProperty().bind(scene.widthProperty());
            pauseOverlay.prefHeightProperty().bind(scene.heightProperty());
        }

        try {
            int[][] grille = ChargeurCarte.chargerCarteDepuisCSV(
                    "/universite_paris8/iut/dagnetti/junglequest/cartes/jungle_map_calque1.csv");
            Carte carte = new Carte(grille);
            System.out.println("Carte chargée avec succès.");

            InputStream tilesetStream = getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/tileset_jungle.png");
            Image tileset = new Image(tilesetStream);
            CarteAffichable carteAffichable = new CarteAffichable(carte, tileset, (int) gameWidth, (int) gameHeight);
            int largeurCartePx = carte.getLargeur() * ConstantesJeu.TAILLE_TUILE;

            VueBackground vueBackground = new VueBackground((int) gameWidth, (int) gameHeight - 50, largeurCartePx);
            racine.getChildren().add(vueBackground);

            InputStream personnageStream = getClass().getResourceAsStream(
                    "/universite_paris8/iut/dagnetti/junglequest/images/sprite1.png");
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

            // --- KURT SPRITE YOK, BOT VAR ---
            double xBot = 500;
            int colBot = (int) (xBot / ConstantesJeu.TAILLE_TUILE);
            int ligneSolBot = carte.chercherLigneSol(colBot);
            double yBot = ligneSolBot * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_TUILE;
            Rectangle bot = new Rectangle(ConstantesJeu.TAILLE_TUILE, ConstantesJeu.TAILLE_TUILE, Color.RED);
            bot.setX(xBot);
            bot.setY(yBot);
            racine.getChildren().add(bot);

            ProgressBar barreVie = new ProgressBar(1.0);
            barreVie.setPrefWidth(ConstantesJeu.TAILLE_SPRITE * 2);
            barreVie.setPrefHeight(6);
            barreVie.setStyle("-fx-accent: #e74c3c;");
            barreVie.setViewOrder(-9);
            racine.getChildren().add(barreVie);

            Label labelVie = new Label(Integer.toString(joueur.getPointsDeVie()));
            labelVie.setTextFill(javafx.scene.paint.Color.WHITE);
            labelVie.setViewOrder(-9);
            racine.getChildren().add(labelVie);

            InventaireController inventaireCtrl = afficherInventaire(racine, joueur, gameWidth, gameHeight);

            ControleurJeu controleurJeu = new ControleurJeu(
                    scene, carte, carteAffichable, joueur,
                    inventaireCtrl, barreVie, labelVie, pauseOverlay,
                    idle, marche, attaque, preparationSaut, volSaut, sautReload,
                    chute, atterrissage, degats, mort, sort, accroupi, bouclier,
                    bot
            );
            controleurJeu.setVueBackground(vueBackground);

        } catch (IOException e) {
            System.err.println("Erreur critique : " + e.getMessage());
            e.printStackTrace();
        }

        stage.setTitle("Jungle Quest");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println("Jeu lancé avec succès.");
    }

    private void initialiserMusique() {
        try {
            URL ressourceAudio = getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/sons/musique_jeu.mp3");
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

    private InventaireController afficherInventaire(Pane racine, Joueur joueur, double largeur, double hauteur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/universite_paris8/iut/dagnetti/junglequest/vue/interface/Inventaire.fxml"));
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
}
