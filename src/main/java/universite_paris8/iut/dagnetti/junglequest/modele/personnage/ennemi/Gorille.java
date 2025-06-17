package universite_paris8.iut.dagnetti.junglequest.modele.personnage.ennemi;

import universite_paris8.iut.dagnetti.junglequest.modele.monde.Maths;
import universite_paris8.iut.dagnetti.junglequest.modele.personnage.Joueur;

public class Gorille extends Ennemi {

    public Gorille(double x, double y) {
        super(x, y, 1, 0, 30, 2, 10);
    }

    // quand le gorille a moins de 50% de ses hp max il saute de 3px vers le joueur
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 2 && Maths.distance(this.getX(), this.getY(), joueur.getX(), joueur.getY()) > 5) {
            if (joueur.getX() > getX()) setX(getX() + 3);
            else setX(getX() - 3);
        }
    }

    @Override
    public String getNom() {
        return "Gorille";
    }
}
