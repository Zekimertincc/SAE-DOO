package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VueMap {
    private final TilePane tilePane;
    private int[][] collisionMap;

    public VueMap() {
        tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);

        try {
            drawSimpleMap(
                    "/fr/iut/groupe/terraria/demo/map.csv",
                    "/fr/iut/groupe/terraria/demo/tileset.png",
                    32
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** CSV haritayı okur, 0 → boş, 1+ → tileset’ten kare çiz */
    public void drawSimpleMap(String csvPath, String tilesetPath, int tileSize) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(csvPath))
        );
        Image tileset = new Image(getClass().getResourceAsStream(tilesetPath));

        List<String[]> rows = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) rows.add(line.split(","));
        br.close();

        int rowsCount = rows.size();
        int cols      = rows.get(0).length;
        collisionMap  = new int[rowsCount][cols];

        int tilesPerRow = (int) (tileset.getWidth() / tileSize);

        for (int y = 0; y < rowsCount; y++) {
            String[] values = rows.get(y);
            for (int x = 0; x < values.length; x++) {
                int idx = Integer.parseInt(values[x]);
                collisionMap[y][x] = idx;

                /* === 0 ise boş, sadece yer tutucu ekle === */
                if (idx == 0) {
                    // saydam bir Region ekleyelim ki grid düzeni bozulmasın
                    Region placeholder = new Region();
                    placeholder.setPrefSize(tileSize, tileSize);
                    tilePane.getChildren().add(placeholder);
                    continue;
                }
                /* ========================================= */

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
