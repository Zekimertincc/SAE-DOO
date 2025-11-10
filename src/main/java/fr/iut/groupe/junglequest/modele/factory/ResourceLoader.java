package fr.iut.groupe.junglequest.modele.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Méthodes utilitaires pour charger les ressources du jeu (Model - MVC)
 * 
 * Architecture MVC:
 * - NO JavaFX imports (pure data loading)
 * - Loads only data, not images
 */
class ResourceLoader {
    /**
     * Charge les données de la carte depuis un fichier CSV
     */
    public static int[][] chargerMapData(String fichierCarte) {
        List<int[]> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCarte))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] elements = ligne.split(",");
                int[] row = new int[elements.length];
                for (int i = 0; i < elements.length; i++) {
                    row[i] = Integer.parseInt(elements[i].trim());
                }
                lignes.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new int[0][0];
        }

        int[][] mapData = new int[lignes.size()][lignes.get(0).length];
        for (int i = 0; i < lignes.size(); i++) {
            mapData[i] = lignes.get(i);
        }
        return mapData;
    }
}