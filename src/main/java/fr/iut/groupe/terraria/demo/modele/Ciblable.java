package fr.iut.groupe.terraria.demo.modele;

public interface Ciblable {
    String getTypeCible(); // retourne "ressource", "ennemi", etc.
    void subirDegats(int degats);
    String getNom();
    double getX();
    double getY();
}
