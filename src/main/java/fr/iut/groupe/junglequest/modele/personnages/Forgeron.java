package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

/**
 * Forgeron NPC - Personnage non joueur qui permet de forger des équipements
 * 
 * Architecture MVC:
 * - Cette classe est dans le Model, elle ne contient PAS d'éléments visuels
 * - Les éléments visuels (sprites, animations) sont gérés par la Vue
 * - La communication avec la Vue se fait via la position et les propriétés
 * 
 * Responsabilités:
 * - Vérifier si un équipement peut être construit
 * - Construire l'équipement et l'ajouter à l'inventaire
 */
public class Forgeron extends Personnage {
    private Equipement equipementSelectionner;

    /**
     * Constructeur du Forgeron
     * 
     * @param x Position X du forgeron
     * @param y Position Y du forgeron
     */
    public Forgeron(double x, double y) {
        super(x, y, ConstantesJeu.LARGEUR_FORGERON, ConstantesJeu.HAUTEUR_FORGERON);
        this.equipementSelectionner = null;
    }

    /**
     * Vérifie si un équipement peut être construit et le construit si possible
     * 
     * @param equipementSelectionner L'équipement à construire
     */
    public void estConstructable (Equipement equipementSelectionner){
        Inventaire inventaire = Inventaire.getInstance();
         if (equipementSelectionner.seConstruit(equipementSelectionner)){
             construction(inventaire, equipementSelectionner);
         }
    }

    /**
     * Construit l'équipement et l'ajoute à l'inventaire
     * 
     * @param inventaire L'inventaire du joueur
     * @param equipementSelectionner L'équipement à ajouter
     */
    public void construction (Inventaire inventaire, Equipement equipementSelectionner){
        inventaire.ajouterItem(equipementSelectionner);
    }

    /**
     * Permet au joueur de choisir une arme à construire
     * 
     * @param equipementSelectionner L'équipement sélectionné
     */
    public void setEquipementSelectionner(Equipement equipementSelectionner) {
        this.equipementSelectionner = equipementSelectionner;
    }
}
