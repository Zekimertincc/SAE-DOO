package fr.iut.groupe.junglequest.modele.item.equipement;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionBonus;
import fr.iut.groupe.junglequest.modele.monde.Maths;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Equipement abstrait (Model - MVC)
 * 
 * Architecture MVC:
 * - NO ImageView or Node (removed - these are View concerns)
 * - Contains only game logic and data
 * - View components create their own visuals based on this data
 */
public abstract class Equipement extends Item {
    protected int degats, durabilite, portee;
    private int quantiteMax; // 1 max par type dans l'inventaire
    private ConditionBonus conditionBonus;


    public Equipement(String nom, int degats, int durabilite, int portee, ConditionBonus conditionBonus) {
        super(nom);
        this.degats = degats;
        this.durabilite = durabilite;
        this.portee = portee;
        this.quantiteMax = 1;
        this.conditionBonus = conditionBonus;
    }

    public abstract boolean seConstruit (Equipement e);

//    public abstract int degatsBonus (String nomCible);
    public int calculerDegats(Ciblable cible, Joueur joueur){
        int degatsFinal = 0;
        if (Maths.distance(joueur.getX(), joueur.getY(), cible.getX(), cible.getY())< this.portee){
            if (conditionBonus.verification(cible)){
                degatsFinal += conditionBonus.degatsBonus(this);
            }
        }
        degatsAction(cible, joueur);
        return degatsFinal;
    }
    public void degatsAction (Ciblable cible, Joueur joueur ){
        this.utiliser();
        joueur.changerNullEquipement();
    }


    public void actionRecompense(Joueur joueur, Inventaire inventaire, Item item){
        inventaire.ajouterItem(item.getNom() , 1);
    }

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

    public double getBonusAttaque() {
        return 1.0; // Default bonus multiplier
    }

}
