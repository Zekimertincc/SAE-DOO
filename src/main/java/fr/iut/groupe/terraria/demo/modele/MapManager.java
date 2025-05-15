package fr.iut.groupe.terraria.demo.modele;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Gestionnaire de la carte.
 * Affiche la carte du jeu (temporairement un fond vert).
 */
public class MapManager {

    /**
     * Affiche la carte actuelle sur le canvas.
     * Cette version est une implémentation temporaire.
     *
     * @param contexteGraphique Le contexte graphique du canvas sur lequel dessiner.
     */
    public static void afficherCarte(GraphicsContext contexteGraphique) {
        // Fond vert de test
        contexteGraphique.setFill(Color.DARKGREEN);
        contexteGraphique.fillRect(0, 0, 1024, 640);

        System.out.println("🧭 Carte temporaire affichée.");
    }
}
