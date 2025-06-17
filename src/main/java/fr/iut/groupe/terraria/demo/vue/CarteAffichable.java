package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import fr.iut.groupe.terraria.demo.modele.carte.Carte;
import fr.iut.groupe.terraria.demo.modele.donnees.ConstantesJeu;

import java.util.ArrayList;
import java.util.List;

public class CarteAffichable extends Pane {

    private final Carte carteLogique;
    private final Image tileset;
    private final PixelReader lecteurPixels;
    private final int colonnesTileset;
    private final int tuilesEcranLargeur;
    private final int tuilesEcranHauteur;
    private double offsetX = 0;

    private final List<ImageView> tuilesAffichees = new ArrayList<>();

    public CarteAffichable(Carte carte, Image tileset, int largeurEcranPx, int hauteurEcranPx) {
        this.carteLogique = carte;
        this.tileset = tileset;
        this.lecteurPixels = tileset.getPixelReader();

        this.colonnesTileset = (int) tileset.getWidth() / ConstantesJeu.TAILLE_TUILE_ORIG;
        this.tuilesEcranLargeur = largeurEcranPx / ConstantesJeu.TAILLE_TUILE + 2;
        this.tuilesEcranHauteur = hauteurEcranPx / ConstantesJeu.TAILLE_TUILE;

        this.setPrefSize(largeurEcranPx, hauteurEcranPx);
        redessiner(0);
    }

    public void redessiner(double offsetX) {
        this.getChildren().clear();
        tuilesAffichees.clear();

        int tuileDebutX = (int) (offsetX / ConstantesJeu.TAILLE_TUILE);
        double decalagePixelsX = offsetX % ConstantesJeu.TAILLE_TUILE;

        int hauteurCarte = carteLogique.getHauteur();
        int largeurCarte = carteLogique.getLargeur();

        for (int ligne = 0; ligne < tuilesEcranHauteur; ligne++) {
            int ligneCarte = ligne;
            if (ligneCarte >= hauteurCarte) continue;

            for (int colonne = 0; colonne < tuilesEcranLargeur; colonne++) {
                int colonneCarte = tuileDebutX + colonne;
                if (colonneCarte < 0 || colonneCarte >= largeurCarte) continue;

                int idTuile = carteLogique.getValeurTuile(ligneCarte, colonneCarte);
                if (idTuile < 0) continue;

                int xTileset = (idTuile % colonnesTileset) * ConstantesJeu.TAILLE_TUILE_ORIG;
                int yTileset = (idTuile / colonnesTileset) * ConstantesJeu.TAILLE_TUILE_ORIG;

                WritableImage imageTuile = new WritableImage(
                        lecteurPixels, xTileset, yTileset,
                        ConstantesJeu.TAILLE_TUILE_ORIG, ConstantesJeu.TAILLE_TUILE_ORIG
                );

                ImageView vueTuile = new ImageView(imageTuile);
                vueTuile.setFitWidth(ConstantesJeu.TAILLE_TUILE);
                vueTuile.setFitHeight(ConstantesJeu.TAILLE_TUILE);
                vueTuile.setX((colonne * ConstantesJeu.TAILLE_TUILE) - decalagePixelsX);
                vueTuile.setY(ligne * ConstantesJeu.TAILLE_TUILE);

                this.getChildren().add(vueTuile);
                tuilesAffichees.add(vueTuile);
            }
        }
    }

    public void mettreAJourOffset(double nouvelleValeur) {
        this.offsetX = nouvelleValeur;
        redessiner(offsetX);
    }

    public Carte getCarte() {
        return carteLogique;
    }
}
