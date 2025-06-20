package fr.iut.groupe.junglequest.modele.personnages;

import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

public class Forgeron extends Personnage {
    private final ImageView sprite;

    public Forgeron(double x, double y, ImageView sprite) {
        super(x, y, ConstantesJeu.LARGEUR_FORGERON, ConstantesJeu.HAUTEUR_FORGERON);
        this.sprite = sprite;
    }

    public ImageView getSprite() {
        return sprite;
    }
}