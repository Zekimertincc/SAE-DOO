package fr.iut.groupe.terraria.demo.modele.personnage;

/**
 * Représente un état temporaire du joueur (ex: invincible, affaibli, boost de vitesse).
 */
public class EtatTemporaire {

    private boolean invincible = false;
    private boolean vulnerable = false;
    private double vitesseTemporaire = 1.5;
    private long effetFin = 0;

    public void appliquerEffet(double vitesse, boolean invincible, boolean vulnerable, long dureeMillis) {
        this.vitesseTemporaire = vitesse;
        this.invincible = invincible;
        this.vulnerable = vulnerable;
        this.effetFin = System.currentTimeMillis() + dureeMillis;
    }

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
    public long getEffetFin() { return effetFin; }

    public void setInvincible(boolean invincible) { this.invincible = invincible; }
    public void setVulnerable(boolean vulnerable) { this.vulnerable = vulnerable; }
    public void setVitesseTemporaire(double vitesseTemporaire) { this.vitesseTemporaire = vitesseTemporaire; }
    public void setEffetFin(long effetFin) { this.effetFin = effetFin; }
}
