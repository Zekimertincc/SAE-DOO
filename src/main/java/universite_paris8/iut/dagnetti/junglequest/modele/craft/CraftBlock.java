package universite_paris8.iut.dagnetti.junglequest.modele.craft;

import universite_paris8.iut.dagnetti.junglequest.modele.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Block;

public class CraftBlock {
    private Inventaire inventaire;
    private String farmSelectionne;

    public CraftBlock(Inventaire inventaire, String farmSelectionne) {
        this.inventaire = inventaire;
        this.farmSelectionne = farmSelectionne;
    }

    // Essaye de créer un bloc à partir de la ressource 2 bois = 1 bloc de bois
    public boolean craftBlock() {
        boolean craft = false;
        if (this.inventaire.getMapItems().getOrDefault(farmSelectionne, 0) >= 2) {
            inventaire.retirerItem(farmSelectionne, 2);
            inventaire.ajouterItem(new Block(farmSelectionne));
            craft = true;
        }
        return craft;
    }

    public void setFarmSelectionne(String farmSelectionne) {
        this.farmSelectionne = farmSelectionne;
    }
}
