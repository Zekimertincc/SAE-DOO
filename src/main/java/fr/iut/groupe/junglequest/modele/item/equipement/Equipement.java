package fr.iut.groupe.junglequest.modele.item.equipement;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.monde.Maths;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    private int quantiteMax; // 1 max par type dans l'inventaire

    private transient ImageView imageView;
    private transient Node vueNode;


    public Equipement(String nom, int degats, int durabilite, int portee) {
        super(nom);
        this.degats = degats;
        this.durabilite = durabilite;
        this.portee = portee;
        this.quantiteMax = 1;
    }

    public abstract boolean seConstruit (Inventaire inventaire, Equipement e);

    public abstract int degatsBonus (String nomCible);
    public void calculerDegats(Ciblable cible, Joueur joueur){
        int degatsFinal = 0;
        if (Maths.distance(joueur.getX(), joueur.getY(), cible.getX(), cible.getY())< this.portee){
            degatsFinal = this.degats + this.degatsBonus(cible.getNom());
        }
        degatsAction(degatsFinal, cible, joueur);
    }

    public void degatsAction (int degatsFinal, Ciblable cible, Joueur joueur ){
        cible.subirDegats(degatsFinal);
        this.utiliser();
        joueur.changerNullEquipement();
    }
    @Override
    public void ajouter(Joueur joueur, Inventaire inventaire, Item item){
        inventaire.ajouterItem(item.getNom() , 1);
    }

    // reduit la durabilité de l'equipement
    public abstract void utiliser();
    public boolean estCasse() {
        return durabilite <= 0;
    }

    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }


    //---------------------------------------------------------------------------------------------------------------------------------
    public int getDurabilite() {
        return durabilite;
    }
    public int getDegats() {
        return degats;
    }
    public int getPortee() {
        return portee;
    }
    public void setImageView(ImageView iv) {
        this.imageView = iv;
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void setVueNode(Node vueNode) {
        this.vueNode = vueNode;
    }
    public Node getVueNode() {
        return vueNode;
    }

}
