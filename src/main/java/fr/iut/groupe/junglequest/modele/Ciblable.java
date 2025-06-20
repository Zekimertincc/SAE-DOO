package fr.iut.groupe.junglequest.modele;

public interface Ciblable {
    String getTypeCible(); // retourne "ressource", "ennemi", etc.
    void subirDegats(int degats);
    double getX();
    double getY();
    String getNom();
}