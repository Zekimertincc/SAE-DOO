package fr.iut.groupe.junglequest.modele;

public interface Ciblable {
    void subirDegats(int degats);
    double getX();
    double getY();
    String getNom();
    int getPointsDeVie();
    void setPointsDeVie(int points);
    int getForce();
    int getPorteeAttaque();
    int calculerDegats(Ciblable cible);
}