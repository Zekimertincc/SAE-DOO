package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VueMap {
    private final TilePane tilePane;
    private final ScrollPane scrollPane;
    private int[][] collisionMap;

    public VueMap() {
        scrollPane = new ScrollPane(getTilePane());
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);

        try {
            drawSimpleMap(
                    "/fr/iut/groupe/terraria/demo/sol.csv",
                    "/fr/iut/groupe/terraria/demo/pont.csv",
                    "/fr/iut/groupe/terraria/demo/tileset.png",
                    32
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


    /** lire les deux csv et superposer les calques avec le tileset */
    public void drawSimpleMap(String csvPathLayer1, String csvPathLayer2, String tilesetPath, int tileSize) throws IOException {
        BufferedReader br1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvPathLayer1)));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvPathLayer2)));
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        List<String[]> rows1 = new ArrayList<>();
        List<String[]> rows2 = new ArrayList<>();
        String line;

        while ((line = br1.readLine()) != null) rows1.add(line.split(","));
        while ((line = br2.readLine()) != null) rows2.add(line.split(","));
        br1.close();
        br2.close();

        int rowsCount = rows1.size();
        int cols = rows1.get(0).length;
        collisionMap = new int[rowsCount][cols];
        int tilesPerRow = (int) (tileset.getWidth() / tileSize);

        for (int y = 0; y < rowsCount; y++) {
            String[] values1 = rows1.get(y);
            String[] values2 = rows2.get(y);

            for (int x = 0; x < cols; x++) {
                int idx1 = Integer.parseInt(values1[x]);
                int idx2 = Integer.parseInt(values2[x]);

                // Superposition : idx2 (pont) remplace idx1 (sol) si idx2 != 0
                int idx = (idx2 != 0) ? idx2 : idx1;
                collisionMap[y][x] = idx;

                if (idx == 0) {
                    Region placeholder = new Region();
                    placeholder.setPrefSize(tileSize, tileSize);
                    .getChildren().add(placeholder);
                    continue;
                }

                int tx = idx % tilesPerRow;
                int ty = idx / tilesPerRow;

                ImageView img = new ImageView(tileset);
                img.setViewport(new Rectangle2D(tx * tileSize, ty * tileSize, tileSize, tileSize));
                img.setFitWidth(tileSize);
                img.setFitHeight(tileSize);

                tilePane.getChildren().add(img);
            }
        }
        tilePane.setPrefColumns(cols);
    }

    public TilePane getTilePane() { return tilePane; }
    public int[][] getCollisionMap() { return collisionMap; }
}
