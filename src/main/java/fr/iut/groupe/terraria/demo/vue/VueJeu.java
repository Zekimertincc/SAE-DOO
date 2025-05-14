package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VueJeu extends Pane {
    private Rectangle joueurVue;

    public VueJeu() {
        setPrefSize(960, 640);

        joueurVue = new Rectangle(40, 40, Color.RED);
        joueurVue.setTranslateX(100);
        joueurVue.setTranslateY(0);

        try {
            System.out.println("Tileset: " + getClass().getResource("/fr/iut/groupe/terraria/demo/tileset.png"));
            System.out.println("Map: " + getClass().getResource("/fr/iut/groupe/terraria/demo/map.csv"));
        } catch (Exception e) {
            System.out.println("MAP DRAW ERROR:");
            e.printStackTrace();
        }

        getChildren().add(joueurVue);
    }

    public void drawSimpleMap(String csvPath, String tilesetPath, int tileSize) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvPath)));
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        java.util.List<String[]> lines = new java.util.ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line.split(","));
        }

        int totalRows = lines.size();
        double offsetY = getPrefHeight() - (totalRows * tileSize);

        for (int row = 0; row < totalRows; row++) {
            String[] cols = lines.get(row);
            for (int col = 0; col < cols.length; col++) {
                String cell = cols[col].trim();
                if (cell.equals("0") || cell.isEmpty()) continue;

                int tileId = Integer.parseInt(cell) - 1;
                int tilesPerRow = (int) (tileset.getWidth() / tileSize);

                int sx = (tileId % tilesPerRow) * tileSize;
                int sy = (tileId / tilesPerRow) * tileSize;

                ImageView tileView = new ImageView(tileset);
                tileView.setViewport(new Rectangle2D(sx, sy, tileSize, tileSize));
                tileView.setTranslateX(col * tileSize);
                tileView.setTranslateY(offsetY + row * tileSize); // ekranın altına oturt

                getChildren().add(tileView);
            }
        }
    }

    public Rectangle getJoueurVue() {
        return joueurVue;
    }
}
