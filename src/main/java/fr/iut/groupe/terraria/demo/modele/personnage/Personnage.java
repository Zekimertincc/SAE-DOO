package fr.iut.groupe.terraria.demo.modele.personnage;

public abstract class Personnage {
    protected double x, y;
    protected int vie, vieMax;
    protected int degats;


    public Personnage(double y, double x, int vie, int vieMax, int degats) {
        this.y = y;
        this.x = x;
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

    // retourne true si un enemi est proche du joueur
    // par exemple si le parametre distance = 50, retourne true si le joueur est à moins de 50 pixels de l’ennemi.
    public boolean estProcheDe(Joueur joueur, double distance) {
        double dx = this.x - joueur.getX();
        double dy = this.y - joueur.getY();
        double d = Math.sqrt(dx * dx + dy * dy);
        return d <= distance;
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
