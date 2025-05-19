package fr.iut.groupe.terraria.demo.vue;

import javafx.animation.AnimationTimer;
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

    private Image[] idleFrames;
    private Image[] runFrames;
    private int frameIndex = 0;
    private long lastTime = 0;

    private String currentState = "idle";
    private boolean lookingRight = true;

    public VueJeu() {
        tilePane = new TilePane();
        tilePane.setHgap(0);
        tilePane.setVgap(0);

        try {
            drawSimpleMap("/fr/iut/groupe/terraria/demo/map.csv", "/fr/iut/groupe/terraria/demo/tileset.png", 32);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Frame’leri yükle
        idleFrames = new Image[]{
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i2.png"))
        };

        runFrames = new Image[]{
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k2.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k3.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k4.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k5.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k6.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k7.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k8.png"))
        };

        joueurVue = new ImageView(idleFrames[0]);
        joueurVue.setFitWidth(64);
        joueurVue.setFitHeight(96);

        double offset = joueurVue.getFitHeight() - 32;
        joueurVue.setTranslateX(3 * 32);
        joueurVue.setTranslateY((collisionMap.length - 1) * 32 - offset);

        getChildren().addAll(tilePane, joueurVue);

        // Animasyonu sürekli oynatmak için dışta başlatılıyor
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime > 120_000_000) { // 120ms
                    frameIndex++;
                    lastTime = now;

                    if (currentState.equals("idle")) {
                        frameIndex %= idleFrames.length;
                        joueurVue.setImage(idleFrames[frameIndex]);
                    } else if (currentState.equals("run")) {
                        frameIndex %= runFrames.length;
                        joueurVue.setImage(runFrames[frameIndex]);
                    }
                }
            }
        };
        animationTimer.start();
    }

    public void updateSprite(String state, boolean isRight) {
        if (!currentState.equals(state)) {
            frameIndex = 0;
            lastTime = 0;
            currentState = state;
        }

        if (isRight != lookingRight) {
            joueurVue.setScaleX(isRight ? 1 : -1);
            lookingRight = isRight;
        }
    }

    public ImageView getJoueurVue() {
        return joueurVue;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
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
                tileView.setViewport(new javafx.geometry.Rectangle2D(sx, sy, tileSize, tileSize));
                tilePane.getChildren().add(tileView);
            }
        }
    }
}
