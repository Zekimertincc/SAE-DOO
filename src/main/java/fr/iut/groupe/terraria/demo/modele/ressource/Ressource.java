package fr.iut.groupe.terraria.demo.modele.ressource;

import javafx.scene.image.ImageView;

public abstract class Ressource {
    protected double x, y;
    private transient ImageView imageView;

    public Ressource(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public abstract String getNom();
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public ImageView getImageView() {
        return imageView;
    }

}
