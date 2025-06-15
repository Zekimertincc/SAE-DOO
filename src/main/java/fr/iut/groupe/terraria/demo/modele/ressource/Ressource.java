package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.farm.Farm;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Ressource implements Ciblable {
    protected DoubleProperty x, y;
    protected String nom;
    protected int quantite;
    protected String outilRequis;
    protected IntegerProperty vie;
    private transient ImageView imageView;
    private transient Node vueNode;

    public Ressource(String nom, int quantite, double x, double y, String outilRequis, int vie) {
        this.nom = nom;
        this.quantite = quantite;
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.outilRequis = outilRequis;
        this.vie = new SimpleIntegerProperty(vie);
    }

    public abstract Farm getItemProduit();

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
        vie.set(vie.get() - degats);
        if (vie.get() <=0) {
            vie.set(0);
        }
    }

    @Override
    public String getTypeCible() {
        return "Ressource";
    }

    public double getX() { return x.get(); }
    public DoubleProperty xProperty() { return x; }
    public double getY() { return y.get(); }
    public DoubleProperty yProperty() { return y; }
    public int getQuantite() { return quantite; }
    public int getVie() { return vie.get(); }
    public IntegerProperty vieProperty() { return vie; }
    public abstract String getNom();

    public void setImageView(ImageView imageView) { this.imageView = imageView; }
    public ImageView getImageView() { return imageView; }

    public void setVueNode(Node vueNode) { this.vueNode = vueNode; }
    public Node getVueNode() { return vueNode; }
}
