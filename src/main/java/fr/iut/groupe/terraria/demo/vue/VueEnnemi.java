package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Simple rectangular view for an enemy.
 */
public class VueEnnemi extends Rectangle {
    private final Ennemi ennemi;

    public VueEnnemi(Ennemi ennemi) {
        super(32, 32, Color.DARKRED);
        this.ennemi = ennemi;
        update();
    }

    /** Update the rectangle position to match the enemy. */
    public void update() {
        setTranslateX(ennemi.getX());
        setTranslateY(ennemi.getY());
    }
}
