package fr.iut.groupe.junglequest.modele.personnages;

import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

public class Guide extends Personnage {
    private final ImageView sprite;

    public Guide(ImageView sprite, double x, double y) {
        super(x, y, ConstantesJeu.LARGEUR_GUIDE, ConstantesJeu.HAUTEUR_GUIDE);
        this.sprite = sprite;
    }

    public ImageView getSprite() {
        return sprite;
    }
}