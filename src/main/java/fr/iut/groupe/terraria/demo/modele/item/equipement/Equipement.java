package fr.iut.groupe.terraria.demo.modele.item.equipement;
import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Item;

public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    protected String type; // ex: "outil", "arme"

    public Equipement(String nom, int degats, String type, int durabilite, int portee) {
        super(nom);
        this.degats = degats;
        this.type = type;
        this.durabilite = durabilite;
        this.portee = portee;
    }
    // retourne les degats selon la situation
    public abstract int degatsContre(double x1, double y2, Ciblable cible);

    // reduit la durabilité de l'equipement
    public abstract void utiliser();
    public boolean estCasse() {
        return durabilite <= 0;
    }

    public int getDurabilite() {
        return durabilite;
    }
}
