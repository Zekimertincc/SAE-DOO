package universite_paris8.iut.dagnetti.junglequest.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarteAffichable extends Pane {

    private static final int TAILLE_TUILE = ConstantesJeu.TAILLE_TUILE;

    private final Carte carteLogique;

    private final List<Tileset> tilesets;
    private final int tuilesEcranLargeur;
    private final int tuilesEcranHauteur;
    private double offsetX = 0;

    private final List<ImageView> tuilesAffichees = new ArrayList<>();

    public CarteAffichable(Carte carte, Image tileset, int largeurEcranPx, int hauteurEcranPx) {
        this(carte, List.of(new Tileset(tileset, 0)), largeurEcranPx, hauteurEcranPx);
    }
    public CarteAffichable(Carte carte, List<Tileset> tilesets, int largeurEcranPx, int hauteurEcranPx) {
        this.carteLogique = carte;
        this.tilesets = new ArrayList<>(tilesets);
        this.tilesets.sort(Comparator.comparingInt(Tileset::getFirstGid));

        this.tuilesEcranLargeur = largeurEcranPx / TAILLE_TUILE + 2;
        this.tuilesEcranHauteur = hauteurEcranPx / TAILLE_TUILE;

        this.setPrefSize(largeurEcranPx, hauteurEcranPx);
        redessiner(0);
    }

    public void redessiner(double offsetX) {
        this.getChildren().clear();
        tuilesAffichees.clear();

        int tuileDebutX = (int) (offsetX / TAILLE_TUILE);
        double decalagePixelsX = offsetX % TAILLE_TUILE;

        int hauteurCarte = carteLogique.getHauteur();
        int largeurCarte = carteLogique.getLargeur();
        for (int ligne = 0; ligne < tuilesEcranHauteur; ligne++) {
            int ligneCarte = ligne;  // Logique naturelle : du haut vers le bas

            if (ligneCarte >= hauteurCarte) continue;

            for (int colonne = 0; colonne < tuilesEcranLargeur; colonne++) {
                int colonneCarte = tuileDebutX + colonne;

                if (colonneCarte < 0 || colonneCarte >= largeurCarte) continue;

                int idTuile = carteLogique.getValeurTuile(ligneCarte, colonneCarte);
                if (idTuile < 0) continue;
                Tileset tsSelectionne = null;
                for (Tileset ts : tilesets) {
                    if (idTuile >= ts.getFirstGid()) {
                        tsSelectionne = ts;
                    } else {
                        break;
                    }
                }
                if (tsSelectionne == null) continue;
                int localId = idTuile - tsSelectionne.getFirstGid();

                int xTileset = (localId % tsSelectionne.getColumns()) * TAILLE_TUILE;
                int yTileset = (localId / tsSelectionne.getColumns()) * TAILLE_TUILE;
                if (xTileset < 0 || yTileset < 0 ||
                        xTileset + TAILLE_TUILE > tsSelectionne.getImage().getWidth() ||
                        yTileset + TAILLE_TUILE > tsSelectionne.getImage().getHeight()) {
                    continue;
                }


                WritableImage imageTuile = new WritableImage(
                        tsSelectionne.getReader(), xTileset, yTileset, TAILLE_TUILE, TAILLE_TUILE
                );

                ImageView vueTuile = new ImageView(imageTuile);
                vueTuile.setX((colonne * TAILLE_TUILE) - decalagePixelsX);
                vueTuile.setY(ligne * TAILLE_TUILE);

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
