package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.vue.TerrariaApplication;
import javafx.application.Application;

/**
 * Point d’entrée alternatif pour le lancement du jeu.
 * Redirige vers la classe principale JavaFX.
 */
public class MainLauncher {
    public static void main(String[] args) {
        Application.launch(TerrariaApplication.class, args);
    }
}
