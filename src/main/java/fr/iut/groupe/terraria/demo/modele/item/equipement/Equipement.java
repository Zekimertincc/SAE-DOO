package fr.iut.groupe.terraria.demo.modele.item.equipement;
import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Equipement extends Item {
    private int degats, durabilite, portee;
    private String type; // ex: "outil", "arme"

    public Equipement(String nom, int degats, String type, int durabilite, int portee) {
        super(nom);
        this.degats = degats;
        this.type = type;
        this.durabilite = durabilite;
        this.portee = portee;
    }

    // Retourne les dégâts modifiés selon la cible x2 sur Arbre/Hache ou Roche/Pioche
    public int getDegatsContre(String cible) {
        int degatsFinal = degats;
        if (cible.equals("Arbre") && getNom().equals("Hache")) {
            degatsFinal = degats * 2;
        } else if (cible.equals("Roche") && getNom().equals("Pioche")) {
            degatsFinal = degats * 2;
        }
        return degatsFinal;
    }

    // reduit la durabilité de 1 à chaque utilisation
    public void utiliser() {
        if (durabilite > 0) {
            durabilite--;
        }
    }

    public boolean estCasse() {
        return durabilite <= 0;
    }

    public int getDegats() {
        return this.degats;
    }
    public String getType() {
        return type;
    }
    public int getDurabilite() {
        return durabilite;
    }

    public int getPortee() {
        return portee;
    }
}
