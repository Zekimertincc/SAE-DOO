package fr.iut.groupe.terraria.demo.modele.equipement;
import fr.iut.groupe.terraria.demo.modele.*;

public class Equipement extends Item{
    private int degats;
    private String type; // ex: "outil", "arme"

    public Equipement(String nom, int degats, String type) {
        super(nom);
        this.degats = degats;
        this.type = type;
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


    public int getDegats() {
        return this.degats;
    }
    public String getType() {
        return type;
    }
}
