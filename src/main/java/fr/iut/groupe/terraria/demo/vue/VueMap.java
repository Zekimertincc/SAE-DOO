/*package fr.iut.groupe.terraria.demo.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VueMap extends Pane {

    private final int TAILLE_TUILE = 32;
    private int[][] collisionMap;
    private final Pane coucheTuiles;
    private Image tileset;
    private int tilesParLigne;

    public VueMap() {
        this.coucheTuiles = new Pane();
        this.getChildren().add(coucheTuiles);

        try {
            chargerMap(
                    "/fr/iut/groupe/terraria/demo/sol.csv",
                    "/fr/iut/groupe/terraria/demo/jungle_terrain.png"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerMap(String cheminCSV, String cheminTileset) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(cheminCSV))
        );
        tileset = new Image(getClass().getResourceAsStream(cheminTileset));
        tilesParLigne = (int) (tileset.getWidth() / TAILLE_TUILE);

        String ligne;
        int y = 0;
        int[][] tempCollision = new int[500][];
        while ((ligne = br.readLine()) != null) {
            String[] valeurs = ligne.split(",");
            int largeur = valeurs.length;
            tempCollision[y] = new int[largeur];

            for (int x = 0; x < largeur; x++) {
                int idx = Integer.parseInt(valeurs[x]);

                // --- Affichage graphique (tout sauf -1) ---
                if (idx >= 0) {
                    int tx = idx % tilesParLigne;
                    int ty = idx / tilesParLigne;

                    ImageView tuile = new ImageView(tileset);
                    tuile.setViewport(new Rectangle2D(
                            tx * TAILLE_TUILE,
                            ty * TAILLE_TUILE,
                            TAILLE_TUILE,
                            TAILLE_TUILE
                    ));
                    tuile.setFitWidth(TAILLE_TUILE);
                    tuile.setFitHeight(TAILLE_TUILE);
                    tuile.setLayoutX(x * TAILLE_TUILE);
                    tuile.setLayoutY(y * TAILLE_TUILE);

                    coucheTuiles.getChildren().add(tuile);
                }

                // --- Logique de collision ---
                if (idx >= 0 && idx != 1) {
                    tempCollision[y][x] = 1; // solide
                } else {
                    tempCollision[y][x] = 0; // vide ou herbe
                }
            }
            y++;
        }
        br.close();

        // Finalisation du tableau collisionMap
        collisionMap = new int[y][];
        System.arraycopy(tempCollision, 0, collisionMap, 0, y);
    }

    public void scrollMap(double offsetX, double offsetY) {
        this.setTranslateX(-offsetX);
        this.setTranslateY(-offsetY);
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }
}
*/