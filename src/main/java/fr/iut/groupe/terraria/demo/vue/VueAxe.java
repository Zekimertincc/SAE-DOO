package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VueAxe extends ImageView {
    public VueAxe(){
        super(new Image(VueCouteau.class.getResource("/fr/iut/groupe/terraria/demo/axe.png").toExternalForm()));
        setFitWidth(32);
        setFitHeight(32);
        setTranslateX(55);
        setTranslateY(40);
    }

}
