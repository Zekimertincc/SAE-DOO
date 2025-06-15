package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public class VueMap {
    private final TilePane tilePane;
    private int[][] collisionMap;

    public VueMap(int[][] map) {
        tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);

        try {
            drawSimpleMap(map, "/fr/iut/groupe/terraria/demo/tileset.png", 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** lire le csv et apres ajout avec tileset */
    public void drawSimpleMap(int[][] map, String tilesetPath, int tileSize) throws IOException {
        collisionMap = map;
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        int rowsCount = map.length;
        int cols      = map[0].length;

        int tilesPerRow = (int) (tileset.getWidth() / tileSize);

        for (int y = 0; y < rowsCount; y++) {
            for (int x = 0; x < cols; x++) {
                int idx = map[y][x];


                if (idx == 0) { // si le tile id egale a 0 ca veut dire qu'on affiche rien cest vide
                    Region placeholder = new Region();
                    placeholder.setPrefSize(tileSize, tileSize);
                    tilePane.getChildren().add(placeholder);
                    continue;
                }

                int tx = idx % tilesPerRow;
                int ty = idx / tilesPerRow;

                ImageView img = new ImageView(tileset);
                img.setViewport(new Rectangle2D(
                        tx * tileSize, ty * tileSize, tileSize, tileSize));
                img.setFitWidth(tileSize);
                img.setFitHeight(tileSize);

                tilePane.getChildren().add(img);
            }
        }
        tilePane.setPrefColumns(cols);
    }

    public TilePane getTilePane()     { return tilePane; }
    public int[][]  getCollisionMap() { return collisionMap; }
}
