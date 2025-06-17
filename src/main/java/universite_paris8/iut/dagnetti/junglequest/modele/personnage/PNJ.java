package universite_paris8.iut.dagnetti.junglequest.modele.personnage;

import universite_paris8.iut.dagnetti.junglequest.modele.monde.Maths;

public class PNJ extends PersonnageJeu{
    private String nom;
    private Quete quete;

    public PNJ(String nom, double x, double y, Quete quete) {
        super(x, y, 0, 0, 10000,1000,  0, 20);
        this.nom = nom;
        this.quete = quete;
    }

    public String getNom() {
        return nom;
    }
    public Quete getQuete() {
        return quete;
    }
}

