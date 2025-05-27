package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.farm.Farm;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import javafx.scene.image.ImageView;
import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Ressource implements Ciblable {
    protected double x, y;
    protected String nom;
    protected int quantite; // farm que contient une ressource
    protected String outilRequis; // couteau, hache, pioche
    protected int vie;
    private transient ImageView imageView;

    public Ressource(String nom, int quantite, double x, double y, String outilRequis, int vie) {
        this.nom = nom;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.outilRequis = outilRequis;
        this.vie = vie;
    }
    // recuperer objet bois, pierre..
    public abstract Farm getItemProduit();

    // verifie si la ressource peut être récoltée avec l'outil fourni
    public boolean peutEtreRecolteeAvec(Equipement outil) {
        boolean peutEtreRecoltee = false;
        if (outil != null) {
            String nomOutil = outil.getNom();
            if (nomOutil.equals("Couteau") || nomOutil.equals("Hache")|| nomOutil.equals("Pioche")) {
                peutEtreRecoltee = true;
            }
        }
        return peutEtreRecoltee;
    }

    public boolean distanceRecolte(Joueur joueur) {
        return Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY())<20;
    }

    @Override
    public void subirDegats(int degats) {
        vie -= degats;
        if (vie <=0) {
            this.vie = 0;
        }
    }
    @Override
    public String getTypeCible() {
        return "Ressource";
    }


// -----------------------------------------------------------------------------------------------------------

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getQuantite() {
        return quantite;
    }
    public int getVie() {
        return vie;
    }
    public abstract String getNom();
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    public ImageView getImageView() {
        return imageView;
    }
}


