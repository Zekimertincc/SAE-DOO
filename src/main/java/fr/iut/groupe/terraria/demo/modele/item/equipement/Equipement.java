package fr.iut.groupe.terraria.demo.modele.item.equipement;
import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    protected String type; // ex: "outil", "arme"
    private int quantiteMax; // 1 max par type dans l'inventaire
    protected String ciblePreferable;

    // la quantité pour craft une arme
    protected int quantiteBois;
    protected int quantitePierre;
    protected int quantiteFile;

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

    // retourne les degats selon la situation
    public int degatsContre(double x1, double y1, Ciblable cible, String equipementType){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())< this.portee){
            degatsFinal = this.degats;
            // class fille getNom equals ciblePrefarable
            if (cible.getNom().equals(this.getCiblePreferable()) && equipementType.equals("Outil")){
                degatsFinal *= 2;
            }
            // Ennemi equals Ennemi.
            if (cible.getTypeCible().equals(getCiblePreferable()) && equipementType.equals("Arme")){
                degatsFinal += 2;
            }
        }
        return degatsFinal;
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
}
