package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

public class EtatTemporaire {
    private boolean invincible = false;
    private boolean vulnerable = false;
    private double vitesseTemporaire = 1.5;
    private long effetFin; // millis

    public void appliquerEffet(double vitesse, boolean invincible, boolean vulnerable) {
        this.vitesseTemporaire = vitesse;
        this.invincible = invincible;
        this.vulnerable = vulnerable;
        this.effetFin = 0;
    }

    // retire les effets sur le joueur
    public void verifierExpiration() {
        if (System.currentTimeMillis() > effetFin) {
            this.invincible = false;
            this.vulnerable = false;
            this.vitesseTemporaire = 1.5;
            this.effetFin = 0;
        }
    }

    public boolean isInvincible() { return invincible; }
    public boolean isVulnerable() { return vulnerable; }
    public double getVitesseTemporaire() { return vitesseTemporaire; }
    public void setInvincible(boolean invincible) { this.invincible = invincible; }
    public void setVulnerable(boolean vulnerable) { this.vulnerable = vulnerable; }
    public void setVitesseTemporaire(double vitesse) { this.vitesseTemporaire = vitesse; }
    public void setEffetFin(long millis) { this.effetFin = millis; }
    public long getEffetFin() {
        return effetFin;
    }

}

