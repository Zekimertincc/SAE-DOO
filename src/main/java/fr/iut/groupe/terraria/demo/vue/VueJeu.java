package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VueJeu extends Pane {
    private ImageView joueurVue;
    private TilePane tilePane;
    private int[][] collisionMap;

    public VueJeu() {
        tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);

        try {
            drawSimpleMap("/fr/iut/groupe/terraria/demo/map.csv", "/fr/iut/groupe/terraria/demo/tileset.png", 32);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image spriteSheet = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/char_a_p1_0bas_humn_v01.png"));
        ImageView joueurSprite = new ImageView(spriteSheet);
        joueurSprite.setFitWidth(64);
        joueurSprite.setFitHeight(96);
        joueurSprite.setViewport(new Rectangle2D(0, 0, 64, 64));

        double offset = joueurSprite.getFitHeight() - 32;
        joueurSprite.setTranslateX(3 * 32);
        joueurSprite.setTranslateY((collisionMap.length - 1) * 32 - offset);

        this.joueurVue = joueurSprite;
        getChildren().addAll(tilePane, joueurVue);
    }

    public void drawSimpleMap(String csvPath, String tilesetPath, int tileSize) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvPath)));
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        java.util.List<String[]> rows = new java.util.ArrayList<>();
        int maxCols = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] cols = line.trim().split(",");
            maxCols = Math.max(maxCols, cols.length);
            rows.add(cols);
        }

        collisionMap = new int[rows.size()][maxCols];

        tilePane.setPrefColumns(maxCols);
        tilePane.setPrefRows(rows.size());
        tilePane.setPrefTileWidth(tileSize);
        tilePane.setPrefTileHeight(tileSize);

        for (int y = 0; y < rows.size(); y++) {
            String[] cols = rows.get(y);
            for (int x = 0; x < maxCols; x++) {
                String cell = (x < cols.length) ? cols[x].trim() : "0";
                int val = cell.equals("0") || cell.isEmpty() ? 0 : 1;
                collisionMap[y][x] = val;

                if (val == 0) {
                    tilePane.getChildren().add(new Rectangle(tileSize, tileSize, Color.TRANSPARENT));
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
                tilePane.getChildren().add(tileView);
            }
        }
    }

    public ImageView getJoueurVue() {
        return joueurVue;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }
}
