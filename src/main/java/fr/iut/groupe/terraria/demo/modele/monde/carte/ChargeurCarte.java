package fr.iut.groupe.terraria.demo.modele.monde.carte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChargeurCarte {
    public static Carte charger(String cheminRessource) {
        try (InputStream input = ChargeurCarte.class.getResourceAsStream(cheminRessource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            List<int[]> lignes = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tiles = line.split(",");
                int[] ligne = new int[tiles.length];
                for (int i = 0; i < tiles.length; i++) {
                    ligne[i] = Integer.parseInt(tiles[i].trim());
                }
                lignes.add(ligne);
            }

            int hauteur = lignes.size();
            int largeur = lignes.get(0).length;
            Carte carte = new Carte(largeur, hauteur);

            for (int y = 0; y < hauteur; y++) {
                for (int x = 0; x < largeur; x++) {
                    carte.setTuile(x, y, lignes.get(y)[x]);
                }
            }

            return carte;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
