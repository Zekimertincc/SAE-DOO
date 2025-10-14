package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

public class Forgeron extends Personnage {
    private final ImageView sprite;
    private Equipement equipementSelectionner;

    public Forgeron(double x, double y, ImageView sprite) {
        super(x, y, ConstantesJeu.LARGEUR_FORGERON, ConstantesJeu.HAUTEUR_FORGERON);
        this.sprite = sprite;
        this.equipementSelectionner = null;
    }

    public void estConstructable (Inventaire inventaire, Equipement equipementSelectionner){
         if (equipementSelectionner.seConstruit(inventaire, equipementSelectionner)){
             construction(inventaire, equipementSelectionner);
         }
    }
    public void construction (Inventaire inventaire, Equipement equipementSelectionner){
        inventaire.ajouterItem(equipementSelectionner);
    }

    // le joueur choisir une arme à construire
    public void setRecette(Equipement equipement) {
        this.equipementSelectionner = equipement;
    }

    public ImageView getSprite() {
        return sprite;
    }

}