package fr.iut.groupe.terraria.demo.modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        // Önce boyutları belirle
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            cols = Math.max(cols, values.length);
            rows++;
        }
        reader.close();

        map = new int[rows][cols];

        // İkinci turda verileri doldur
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

    /**
     * @return Harita verisi (collisionMap gibi)
     */
    public int[][] getMap() {
        return map;
    }
}
