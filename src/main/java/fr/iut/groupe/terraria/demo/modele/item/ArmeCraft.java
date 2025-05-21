package fr.iut.groupe.terraria.demo.modele.item;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Pioche;

import java.util.HashMap;

public class ArmeCraft {
    private Equipement equipement;
    private Inventaire inventaire;
    public ArmeCraft(Inventaire inventaire, Equipement equipement) {
        this.inventaire = inventaire;
        this.equipement = equipement;
    }

    // regarde si les ressources sont suffisante pour construire l'arme
    public boolean materiauxRequis(){
        return inventaire.getMapItems().getOrDefault("Bois", 0) >= equipement.getQuantiteBois() &&
                inventaire.getMapItems().getOrDefault("Pierre", 0) >= equipement.getQuantitePierre() &&
                inventaire.getMapItems().getOrDefault("File", 0) >= equipement.getQuantiteFile();
    }
    // contruit l'arme et il le met directement dans l'inventaire du joueur
    public boolean construire(){
        boolean contruire = false;
        if (!materiauxRequis() && !equipement.nom.equals("Couteau")) {
            if (inventaire.ajouterItem(new Pioche())){
                inventaire.retirerItem("Bois", equipement.getQuantiteBois());
                inventaire.retirerItem("Pierre", equipement.getQuantitePierre());
                inventaire.retirerItem("File", equipement.getQuantiteFile());
                contruire = true;
            }
        }
        return contruire;
    }
}
