package fr.iut.groupe.junglequest.modele.personnages;


// Classe  représentant un personnage générique avec position, vitesse et sprite.
// Elle sert de base commune pour le joueur et d'autres entités futures.
// Cette classe est 100% pur Java, sans dépendance à JavaFX.

public abstract class Personnage {

    protected double x;
    protected double y;
    protected double vitesseX;
    protected double vitesseY;
    protected boolean versGauche;
    protected boolean estAuSol;

    private double largeur;
    private double hauteur;

    public Personnage(double x, double y, double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.x = x;
        this.y = y;
        this.vitesseX = 0;
        this.vitesseY = 0;
        this.versGauche = false;
        this.estAuSol = false;
    }

    
    // Met à jour la position en fonction des vitesses actuelles.
     
    public void mettreAJourPosition() {
        this.x += this.vitesseX;
        this.y += this.vitesseY;
    }

    
    // Applique la gravité si le personnage n'est pas au sol.
     
    public void appliquerGravite(double gravite, double vitesseChuteMax) {
        if (!this.estAuSol) {
            this.vitesseY = Math.min(this.vitesseY + gravite, vitesseChuteMax);
        }
    }

    
    //Pose le personnage au sol à une hauteur précise.
     
    public void poserAuSol(double ySol) {
        this.y = ySol;
        this.vitesseY = 0;
        this.estAuSol = true;
    }

    
    // Lance un saut si le personnage est au sol.

    public void sauter(double impulsion) {
        if (this.estAuSol) {
            this.vitesseY = -impulsion;
            this.estAuSol = false;
        }
    }

    
    // Déplace vers la gauche.
    
    public void deplacerGauche(double vitesse) {
        this.vitesseX = -vitesse;
        this.versGauche = true;
    }

    
    // Déplace vers la droite.
    public void deplacerDroite(double vitesse) {
        this.vitesseX = vitesse;
        this.versGauche = false;
    }

    
     //Arrête le mouvement horizontal.
     
    public void arreter() {
        this.vitesseX = 0;
    }

    // Getters utiles

    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getVitesseY() { return vitesseY; }
    public double getVitesseX() { return vitesseX; }
    public boolean estAuSol() { return estAuSol; }
    public boolean estVersGauche() { return versGauche; }

    public void setEstAuSol(boolean auSol) {
        this.estAuSol = auSol;
    }

    
    // Positionne le personnage horizontalement.
     
    public void setX(double x) {
        this.x = x;
    }

    
    // Positionne le personnage verticalement.
     
    public void setY(double y) {
        this.y = y;
    }

    
    // Modifie la vitesse horizontale du personnage.
     
    public void setVitesseX(double vitesseX) {
        this.vitesseX = vitesseX;
    }

    
    // Modifie la vitesse verticale du personnage.
    
    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }
    
    protected void setPointsDeVie(int i) {
    }
}
