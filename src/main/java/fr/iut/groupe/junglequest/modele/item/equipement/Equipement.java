package fr.iut.groupe.junglequest.modele.item.equipement;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.monde.Maths;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    protected String type; // ex: "outil", "arme"
    private int quantiteMax; // 1 max par type dans l'inventaire
    protected String ciblePreferable;

    // la quantité pour craft une arme
    protected int quantiteBois;
    protected int quantitePierre;
    protected int quantiteFile;
    private transient ImageView imageView;
    private transient Node vueNode;


    public Equipement(String nom, int degats, String type, int durabilite, int portee, int quantiteBois, int quantitePierre, int quantiteFile, String ciblePreferable) {
        super(nom);
        this.degats = degats;
        this.type = type;
        this.durabilite = durabilite;
        this.portee = portee;
        this.quantiteMax = 1;
        this.quantiteBois = quantiteBois;
        this.quantitePierre = quantitePierre;
        this.quantiteFile = quantiteFile;
        this.ciblePreferable = ciblePreferable;
    }

    public abstract int degatsBonus (String nomCible);

    public void calculerDegats(Ciblable cible, Joueur joueur){
        int degatsFinal = 0;
        if (Maths.distance(joueur.getX(), joueur.getY(), cible.getX(), cible.getY())< this.portee){
            degatsFinal = this.degats + degatsBonus(cible.getNom());
        }
        degatsAction(degatsFinal, cible, joueur);
    }

    public void degatsAction (int degatsFinal, Ciblable cible, Joueur joueur ){
        cible.subirDegats(degatsFinal);
        this.utiliser();
        joueur.changerNullEquipement();
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
    public boolean estOutil() {
        return this.type.equals("Outil");
    }
    public int getQuantiteBois() {
        return quantiteBois;
    }
    public int getQuantitePierre() {
        return quantitePierre;
    }
    public int getQuantiteFile() {
        return quantiteFile;
    }
    public String getCiblePreferable() {
        return ciblePreferable;
    }

    public String getType() {
        return type;
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
