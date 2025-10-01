package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.Recette;
import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

public class Forgeron extends Personnage {
    private final ImageView sprite;
    private Recette recette;

    public Forgeron(double x, double y, ImageView sprite) {
        super(x, y, ConstantesJeu.LARGEUR_FORGERON, ConstantesJeu.HAUTEUR_FORGERON);
        this.sprite = sprite;
        this.recette = null;
    }

    public boolean estConstruction (Inventaire inventaire, Equipement p){
        return recette.verificationConstructiion(inventaire, p);
    }

    // le joueur choisir une arme à construire
    public void setRecette(Recette recette) {
        this.recette = recette;
    }
    public ImageView getSprite() {
        return sprite;
    }

}