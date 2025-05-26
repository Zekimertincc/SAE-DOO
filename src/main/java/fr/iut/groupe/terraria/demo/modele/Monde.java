package fr.iut.groupe.terraria.demo.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import java.util.ArrayList;
import java.util.List;


public class Monde {
    private int[][] map;

    /**
     * @param csvPath
     */
    public Monde(String csvPath) {
        try {
            loadMap(csvPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMap(String csvPath) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(csvPath))
        );
        String line;
        int rows = 0;
        int cols = -1;

        // determiner les tailles
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            cols = Math.max(cols, values.length);
            rows++;
        }
        reader.close();

        map = new int[rows][cols];

        // mettre les dedans les donnees de csv
        reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(csvPath))
        );
        int y = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (int x = 0; x < values.length; x++) {
                map[y][x] = Integer.parseInt(values[x]);
            }
            y++;
        }
        reader.close();
    }
    private final List<Ressource> ressources = new ArrayList<>();

    public void ajouterRessource(Ressource r) {
        ressources.add(r);
    }

    public void verifierProximite(Joueur joueur) {
        for (Ressource r : ressources) {
            double dx = Math.abs(joueur.getX() - r.getX());
            double dy = Math.abs(joueur.getY() - r.getY());
            if (dx < 40 && dy < 40) {
                System.out.println("🪓 Yakında bir " + r.getNom() + " var!");
            }
        }
    }



    public int[][] getMap() {
        return map;
    }
}
