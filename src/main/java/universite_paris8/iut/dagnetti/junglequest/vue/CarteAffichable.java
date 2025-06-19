package universite_paris8.iut.dagnetti.junglequest.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;

import java.util.ArrayList;
import java.util.List;

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

    private final List<ImageView> tuilesAffichees = new ArrayList<>();

    public CarteAffichable(Carte carte, Image tileset, int largeurEcranPx, int hauteurEcranPx) {
        this.carteLogique = carte;
        this.tileset = tileset;
        this.lecteurPixels = tileset.getPixelReader();

        this.colonnesTileset = (int) tileset.getWidth() / TAILLE_TUILE;
        this.tuilesEcranLargeur = largeurEcranPx / TAILLE_TUILE + 2;
        this.tuilesEcranHauteur = hauteurEcranPx / TAILLE_TUILE + 2;

        this.setPrefSize(largeurEcranPx, hauteurEcranPx);
        redessiner(0, 0);
    }

    public void redessiner(double offsetX, double offsetY) {
        this.getChildren().clear();
        tuilesAffichees.clear();

        int tuileDebutX = (int) (offsetX / TAILLE_TUILE);
        int tuileDebutY = (int) (offsetY / TAILLE_TUILE);
        double decalagePixelsX = offsetX % TAILLE_TUILE;
        double decalagePixelsY = offsetY % TAILLE_TUILE;


        int hauteurCarte = carteLogique.getHauteur();
        int largeurCarte = carteLogique.getLargeur();
        for (int ligne = 0; ligne < tuilesEcranHauteur; ligne++) {
            int ligneCarte = tuileDebutY + ligne;

            if (ligneCarte >= hauteurCarte) continue;

            for (int colonne = 0; colonne < tuilesEcranLargeur; colonne++) {
                int colonneCarte = tuileDebutX + colonne;

                if (colonneCarte < 0 || colonneCarte >= largeurCarte) continue;

                int idTuile = carteLogique.getValeurTuile(ligneCarte, colonneCarte);
                if (idTuile < 0) continue;

                int xTileset = (idTuile % colonnesTileset) * TAILLE_TUILE;
                int yTileset = (idTuile / colonnesTileset) * TAILLE_TUILE;

                WritableImage imageTuile = new WritableImage(
                        lecteurPixels, xTileset, yTileset, TAILLE_TUILE, TAILLE_TUILE
                );

                ImageView vueTuile = new ImageView(imageTuile);
                vueTuile.setX((colonne * TAILLE_TUILE) - decalagePixelsX);
                vueTuile.setY((ligne * TAILLE_TUILE) - decalagePixelsY);

                this.getChildren().add(vueTuile);
                tuilesAffichees.add(vueTuile);
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
