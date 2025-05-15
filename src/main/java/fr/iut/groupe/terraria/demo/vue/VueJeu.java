package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VueJeu extends TilePane {
    private Rectangle joueurVue;

    public VueJeu() {
        setPrefColumns(20); // map.csv'deki sütun sayısına göre sabit
        setPrefRows(12);    // map.csv'deki satır sayısına göre sabit
        setPrefTileWidth(32);
        setPrefTileHeight(32);
        setHgap(0);
        setVgap(0);

        joueurVue = new Rectangle(40, 40, Color.RED);
        joueurVue.setTranslateX(100);
        joueurVue.setTranslateY(0);

        try {
            drawSimpleMap("/fr/iut/groupe/terraria/demo/map.csv", "/fr/iut/groupe/terraria/demo/tileset.png", 32);
        } catch (Exception e) {
            System.out.println("Erreur de chargement de la map :");
            e.printStackTrace();
        }

        getChildren().add(joueurVue);
    }

    public void drawSimpleMap(String csvPath, String tilesetPath, int tileSize) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvPath)));
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] cols = line.split(",");
            for (String cell : cols) {
                cell = cell.trim();
                if (cell.equals("0") || cell.isEmpty()) {
                    getChildren().add(new Rectangle(tileSize, tileSize, Color.TRANSPARENT));
                    continue;
                }

                int tileId = Integer.parseInt(cell) - 1;
                int tilesPerRow = (int) (tileset.getWidth() / tileSize);
                int sx = (tileId % tilesPerRow) * tileSize;
                int sy = (tileId / tilesPerRow) * tileSize;

                ImageView tileView = new ImageView(tileset);
                tileView.setFitWidth(tileSize);
                tileView.setFitHeight(tileSize);
                tileView.setViewport(new Rectangle2D(sx, sy, tileSize, tileSize));

                getChildren().add(tileView);
            }
        }
    }

    public Rectangle getJoueurVue() {
        return joueurVue;
    }
}
