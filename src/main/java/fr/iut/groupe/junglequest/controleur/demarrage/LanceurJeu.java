package fr.iut.groupe.junglequest.controleur.demarrage;

import fr.iut.groupe.junglequest.controleur.VueJeuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.io.IOException;

/**
 * Point d'entrée de l'application JavaFX.
 * 
 * Architecture MVC - Rôle de LanceurJeu:
 * 1. Initialiser JavaFX (Stage, Scene)
 * 2. Charger le FXML principal (VueJeu.fxml)
 * 3. Obtenir le contrôleur via FXML
 * 4. Déléguer l'initialisation complète au contrôleur
 * 
 * Règle d'OR MVC:
 * - Le contrôleur crée l'instance envVue et envModele
 * - Le envModele crée l'instance terrain qui charge les données de la map, crée le joueur et les entités
 * - Le envVue charge les images du terrain via le envModele, via un bind ou observable il récupère les entités
 * 
 * Cette classe:
 * - NE contient PAS de logique métier
 * - NE crée PAS les entités du jeu
 * - NE charge PAS les images du jeu (sauf musique de fond)
 * - Se limite au démarrage et à la configuration JavaFX
 * 
 * Design Patterns:
 * - MVC Pattern: Séparation claire des responsabilités
 * - FXML avec Controller: Injection automatique via @FXML
 */
public class LanceurJeu extends Application {

    private static MediaPlayer mediaPlayer;

    /**
     * Point d'entrée principal de l'application JavaFX.
     * 
     * Flux MVC simplifié:
     * 1. Charger le FXML avec son contrôleur
     * 2. Le contrôleur crée le Model (Environnement)
     * 3. Le contrôleur crée la View (éléments visuels)
     * 4. Le contrôleur relie Model et View via bindings/observers
     */
    @Override
    public void start(Stage stage) {
        System.out.println("===== Démarrage de Jungle Quest =====");
        
        // Initialisation de la musique de fond
        initialiserMusique();

        // Récupération des dimensions de l'écran
        Rectangle2D ecran = Screen.getPrimary().getBounds();
        double largeur = ecran.getWidth();
        double hauteur = ecran.getHeight();

        try {
            // Chargement du FXML avec injection automatique du contrôleur
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fr/iut/groupe/junglequest/vue/VueJeu.fxml"));
            Pane racine = loader.load();
            
            // Récupération du contrôleur créé par FXML
            VueJeuController controller = loader.getController();
            
            // Création de la scène
            Scene scene = new Scene(racine, largeur, hauteur);
            
            // Binding du pauseOverlay sur les dimensions de la scène
            if (controller.getRacine().lookup("#pauseOverlay") != null) {
                Pane pauseOverlay = (Pane) controller.getRacine().lookup("#pauseOverlay");
                pauseOverlay.prefWidthProperty().bind(scene.widthProperty());
                pauseOverlay.prefHeightProperty().bind(scene.heightProperty());
            }
            
            // Initialisation complète du contrôleur
            // Le contrôleur va:
            // 1. Créer l'Environnement (Model)
            // 2. Créer les vues des entités
            // 3. Établir les bindings
            controller.initializationComplete(largeur, hauteur);
            
            // Configuration de la fenêtre
            stage.setTitle("Jungle Quest");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
            
            System.out.println("===== Jungle Quest lancé avec succès =====");
            
        } catch (IOException e) {
            System.err.println("Erreur critique lors du démarrage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialise et lance la musique de fond du jeu.
     * C'est une responsabilité acceptable pour LanceurJeu car c'est une configuration globale.
     */
    private void initialiserMusique() {
        try {
            URL ressourceAudio = getClass().getResource("/fr/iut/groupe/junglequest/sons/musique_jeu.mp3");
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
}
