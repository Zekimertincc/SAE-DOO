package fr.iut.groupe.terraria.demo.modele.monde.carte;

public class Carte {
    private int[][] grille;

    public Carte(int largeur, int hauteur) {
        grille = new int[hauteur][largeur];
    }

    public void setTuile(int x, int y, int id) {
        grille[y][x] = id;
    }

    public int getTuile(int x, int y) {
        return grille[y][x];
    }

    public int getLargeur() {
        return grille[0].length;
    }

    public int getHauteur() {
        return grille.length;
    }
}
