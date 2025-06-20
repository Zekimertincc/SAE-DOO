package fr.iut.groupe.junglequest.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import fr.iut.groupe.junglequest.modele.carte.Carte;

import java.util.HashMap;
import java.util.Map;

public class CarteAffichable extends Pane {

    private static final int TAILLE_TUILE = 32;

    private final Carte carteLogique;
    private final Image tileset;
    private final PixelReader lecteurPixels;
    private final int colonnesTileset;
    private final int tuilesEcranLargeur;
    private final int tuilesEcranHauteur;
    private double offsetX = 0;
    private double offsetY = 0;

    private final ImageView[][] tuilesAffichees;
    private final Map<Integer, Image> cacheImages = new HashMap<>();

    public CarteAffichable(Carte carte, Image tileset, int largeurEcranPx, int hauteurEcranPx) {
        this.carteLogique = carte;
        this.tileset = tileset;
        this.lecteurPixels = tileset.getPixelReader();

        this.colonnesTileset = (int) tileset.getWidth() / TAILLE_TUILE;
        this.tuilesEcranLargeur = largeurEcranPx / TAILLE_TUILE + 2;
        this.tuilesEcranHauteur = hauteurEcranPx / TAILLE_TUILE + 2;

        this.setPrefSize(largeurEcranPx, hauteurEcranPx);

        tuilesAffichees = new ImageView[tuilesEcranHauteur][tuilesEcranLargeur];
        for (int ligne = 0; ligne < tuilesEcranHauteur; ligne++) {
            for (int col = 0; col < tuilesEcranLargeur; col++) {
                ImageView iv = new ImageView();
                iv.setFitWidth(TAILLE_TUILE);
                iv.setFitHeight(TAILLE_TUILE);
                tuilesAffichees[ligne][col] = iv;
                this.getChildren().add(iv);
            }
        }

        redessiner(0, 0);
    }

    private Image imagePourId(int idTuile) {
        return cacheImages.computeIfAbsent(idTuile, id -> {
            int xTileset = (id % colonnesTileset) * TAILLE_TUILE;
            int yTileset = (id / colonnesTileset) * TAILLE_TUILE;
            return new WritableImage(lecteurPixels, xTileset, yTileset, TAILLE_TUILE, TAILLE_TUILE);
        });
    }

    public void redessiner(double offsetX, double offsetY) {
        
        int tuileDebutX = (int) (offsetX / TAILLE_TUILE);
        int tuileDebutY = (int) (offsetY / TAILLE_TUILE);
        double decalagePixelsX = offsetX % TAILLE_TUILE;
        double decalagePixelsY = offsetY % TAILLE_TUILE;


        int hauteurCarte = carteLogique.getHauteur();
        int largeurCarte = carteLogique.getLargeur();
        for (int ligne = 0; ligne < tuilesEcranHauteur; ligne++) {
            int ligneCarte = tuileDebutY + ligne;
            for (int colonne = 0; colonne < tuilesEcranLargeur; colonne++) {
                int colonneCarte = tuileDebutX + colonne;
                ImageView vueTuile = tuilesAffichees[ligne][colonne];

                if (ligneCarte >= 0 && ligneCarte < hauteurCarte &&
                    colonneCarte >= 0 && colonneCarte < largeurCarte) {
                    int idTuile = carteLogique.getValeurTuile(ligneCarte, colonneCarte);
                    if (idTuile >= 0) {
                        vueTuile.setImage(imagePourId(idTuile));
                        vueTuile.setVisible(true);
                    } else {
                        vueTuile.setVisible(false);
                    }
                } else {
                    vueTuile.setVisible(false);
                }

                vueTuile.setX((colonne * TAILLE_TUILE) - decalagePixelsX);
                vueTuile.setY((ligne * TAILLE_TUILE) - decalagePixelsY);
            }
        }
    }
    public void mettreAJourOffset(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        redessiner(offsetX, offsetY);
    }

    public Carte getCarte() {
        return carteLogique;
    }
}
