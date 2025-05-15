package fr.iut.groupe.terraria.demo.modele.personnage;

public abstract class PersonnageJeu extends Personnage {
    public PersonnageJeu(double x, double y, int vie, int vieMax, int degats) {
        super(x, y, vie, vieMax, degats);
    }
    // retourne true si un enemi est proche du joueur mais ne fait rien c'est juste une detection
    public abstract boolean estProcheDe(Joueur joueur);

}
