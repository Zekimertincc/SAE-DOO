package fr.iut.groupe.junglequest.modele.personnages;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Couteau;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;



/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private final SujetObserve sujet = new SujetObserve() {};

    private boolean estEnAttaque;
    private boolean bouclierActif;
    private final Inventaire inventaire;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
    private int pointsDeVieMax = 100;
    private Equipement equipementActuel;

    public Joueur(double x, double y) {
        super(x, y, ConstantesJeu.TAILLE_SPRITE, ConstantesJeu.TAILLE_SPRITE);
        this.estEnAttaque = false;
        this.bouclierActif = false;
        this.inventaire = Inventaire.getInstance();
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_JOUEUR);
    }

    public void appliquerDegats(Ciblable cible) {
        int degatsFinal = equipementActuel.calculerDegats(cible, this);
        cible.subirDegats(degatsFinal);
    }

    public void changerNullEquipement() {
        if (this.equipementActuel.estCasse()){
            Couteau c = new Couteau();
            setEquipementActuel(c);
        }
    }

    private void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }

    public boolean estEnAttaque() {
        return estEnAttaque;
    }

    public void attaquer() {
        estEnAttaque = true;
    }

    public void finAttaque() {
        estEnAttaque = false;
    }

    public boolean isBouclierActif() {
        return bouclierActif;
    }

    public void activerBouclier() {
        bouclierActif = true;
    }

    public void desactiverBouclier() {
        bouclierActif = false;
    }

    public void ajouterObservateur(Observateur obs) {
        sujet.ajouterObservateur(obs);
    }

    public void retirerObservateur(Observateur obs) {
        sujet.retirerObservateur(obs);
    }

    public void notifierObservateurs() {
        sujet.notifierObservateurs();
    }


    public Inventaire getInventaire() {
        return inventaire;
    }

    public int getPointsDeVie() {
        return pointsDeVie.get();
    }

    public IntegerProperty pointsDeVieProperty() {
        return pointsDeVie;
    }

    public void subirDegats(int quantite) {
        pointsDeVie.set(Math.max(0, pointsDeVie.get() - quantite));
        notifierObservateurs(); // 🔔 avertit la vue automatiquement
    }

    public void soigner(int quantite) {
        pointsDeVie.set(Math.min(pointsDeVie.get() + quantite, ConstantesJeu.VIE_MAX_JOUEUR));
        notifierObservateurs();
    }

    public int getVieMax() {
        return this.pointsDeVieMax;
    }
    public EtatTemporaire getEtatTemporaire() {
        return null;
    }
    public void mourir() {
        this.setPointsDeVie(0);
        notifierObservateurs();
        System.out.println("Le joueur est mort.");
    }
    public void setPointsDeVie(int i) {
        this.pointsDeVie.set(i);
    }

    public void gererBouclier(boolean activer) {
        if (activer && !estEnAttaque()) {
            activerBouclier();
        } else if (!activer) {
            desactiverBouclier();
        }
    }

    public void gererMouvement(boolean gauche, boolean droite) {
        if (isBouclierActif()) {
            // Bouclier açıkken hareket etmiyoruz; istersen burada hız kesebilirsin
            arreter();
            return;
        }
        if (gauche) {
            deplacerGauche(ConstantesJeu.VITESSE_JOUEUR);
        } else if (droite) {
            deplacerDroite(ConstantesJeu.VITESSE_JOUEUR);
        } else {
            arreter();
        }
    }

    public void gererSaut(boolean toucheSaut) {
        if (toucheSaut && estAuSol() && !isBouclierActif()) {
            sauter(ConstantesJeu.IMPULSION_SAUT);
        }
    }

    /** İsteğe bağlı ama faydalı: tek yerden hasar alma */
    public void encaisserDegats(int points) {
        subirDegats(points);
    }



}
