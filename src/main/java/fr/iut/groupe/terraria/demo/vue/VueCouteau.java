package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueCouteau extends Rectangle {
    public VueCouteau() {
        super(5, 20); // genişlik, yükseklik
        setFill(Color.DARKRED);
        setTranslateX(55);  // oyuncunun sağı gibi
        setTranslateY(40);  // el hizası gibi
    }
}
