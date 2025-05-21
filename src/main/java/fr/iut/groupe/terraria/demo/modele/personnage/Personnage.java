package fr.iut.groupe.terraria.demo.modele.personnage;

public abstract class Personnage {
    protected double x, y;
    protected int vie, vieMax;
    protected int degats;

    public Personnage(double x, double y, int vie, int vieMax, int degats) {
        this.x = x;
        this.y = y;
        this.vie = vie;
        this.vieMax = vieMax;
        this.degats = degats;
    }

    /*
        La fonction prend en parametre le nombre de vie, et l'ajoute sur le personnage
        Si la vie depasse 5 coeurs, alors le perso à 5 coeur, la vie est en dessous 0 alors le personnage a 0 coeur
    */
    public void gagnerVie(int quantite) {
        vie += quantite;
        if (vie > vieMax) {
            vie = vieMax;
        }
        if (vie < 0) {
            vie = 0;
        }
    }
    // utilise la fonction gagner de la vie mais envoie un nombre negatif pour faire perdre de la vie au joueur
    public void subirDegats(int degats) {
        gagnerVie(-degats);
    }

    // verifie si le personnage est mort
    public boolean estMort() {
        return vie <= 0;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getVie() {
        return vie;
    }
    public int getVieMax() {
        return vieMax;
    }
    public double getX() { return x; }
    public double getY() { return y; }
}
