package fr.iut.groupe.terraria.demo.modele.item.equipement;
import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Item;

public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    protected String type; // ex: "outil", "arme"
    private int quantiteMax; // 1 max par type dans l'inventaire

    public Equipement(String nom, int degats, String type, int durabilite, int portee) {
        super(nom);
        this.degats = degats;
        this.type = type;
        this.durabilite = durabilite;
        this.portee = portee;
        this.quantiteMax = 1;
    }
    // retourne les degats selon la situation
    public abstract int degatsContre(double x1, double y2, Ciblable cible);

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

}
