package universite_paris8.iut.dagnetti.junglequest.vue;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu; /**
 * Association d'un tileset avec son identifiant de départ dans la carte.
 */
public class Tileset {
    private final Image image;
    private final PixelReader reader;
    private final int columns;
    private final int firstGid;

    public Tileset(Image image, int firstGid) {
        this.image = image;
        this.firstGid = firstGid;
        this.reader = image.getPixelReader();
        this.columns = (int) image.getWidth() / ConstantesJeu.TAILLE_TUILE;
    }

    public Image getImage() { return image; }
    public PixelReader getReader() { return reader; }
    public int getColumns() { return columns; }
    public int getFirstGid() { return firstGid; }
}
