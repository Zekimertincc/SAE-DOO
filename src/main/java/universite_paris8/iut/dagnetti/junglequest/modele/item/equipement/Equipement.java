package universite_paris8.iut.dagnetti.junglequest.modele.item.equipement;
import universite_paris8.iut.dagnetti.junglequest.modele.Ciblable;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;
import universite_paris8.iut.dagnetti.junglequest.modele.monde.Maths;
import universite_paris8.iut.dagnetti.junglequest.modele.personnage.Joueur;
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

    /**
     * calcule la distance entre le joueur et la cible, si la portée est vraie alors il peut faire des degats.
     * des degats bonus sont ajoutés si la condition dans le if est true
     * (Equipemet) getCiblePreferable retourne Ennemi pour tous les Ennemis, ressource selon la ressource.
     * @param joueur utilse la position du joueur
     * @param cible utilise la positon de la cible
     */
    public void degatsContre(Joueur joueur, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(joueur.getX(), joueur.getY(), cible.getX(), cible.getY())< this.portee){
            degatsFinal = this.degats;
            if (cible.getNom().equals(this.getCiblePreferable()) && this.type.equals("Outil")){
                degatsFinal *= 2;
            }
            // getTypeCible doit retourner Ennemi pour que ca marche
            if (cible.getTypeCible().equals(getCiblePreferable()) && this.type.equals("Arme")){
                degatsFinal += 2;
            }
        }
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
