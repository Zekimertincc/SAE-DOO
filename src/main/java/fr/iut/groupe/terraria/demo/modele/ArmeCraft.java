package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public class ArmeCraft {
    private Inventaire inventaire;
    public ArmeCraft(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    // regarde si les ressources sont suffisante pour construire l'arme
    public boolean materiauxRequis(Equipement equipement){
        return inventaire.getMapItems().getOrDefault("Bois", 0) >= equipement.getQuantiteBois() &&
                inventaire.getMapItems().getOrDefault("Pierre", 0) >= equipement.getQuantitePierre() &&
                inventaire.getMapItems().getOrDefault("File", 0) >= equipement.getQuantiteFile();
    }
    // contruit l'arme et il le met directement dans l'inventaire du joueur
    public boolean construire(Equipement equipement){
        boolean contruire = false;
        if (materiauxRequis(equipement) && !equipement.getNom().equals("Couteau")) {

            if (inventaire.ajouterItem(equipement)){
                inventaire.retirerItem("Bois", equipement.getQuantiteBois());
                inventaire.retirerItem("Pierre", equipement.getQuantitePierre());
                inventaire.retirerItem("File", equipement.getQuantiteFile());
                contruire = true;
            }
        }
        return contruire;
    }
}
