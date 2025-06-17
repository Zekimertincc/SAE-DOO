package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.monde.AStarPathFinding;

public class Loup extends Ennemi {
    public Loup(double x, double y) {
        super(x, y, 2, 0, 15, 1,5);
    }

    private final int tileSize = 32;

    // quand le loup a moins de 33% de ses hp max il s'enfuit de 2px vers la droite
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 3) {
            setX(getX() - 2);
        }
    }

    @Override
    public String getNom() {
        return "Loup";
    }

    @Override
    public void seDeplacerVers(Joueur joueur) {
        int[][] map = joueur.getMap();
        int startRow = (int) (getY() / tileSize);
        int startCol = (int) (getX() / tileSize);
        int endRow = (int) (joueur.getY() / tileSize);
        int endCol = (int) (joueur.getX() / tileSize);

        java.util.List<int[]> path = AStarPathFinding.findPath(map, startRow, startCol, endRow, endCol);
        if (path.size() > 1) {
            int[] next = path.get(1);
            double targetX = next[1] * tileSize;
            double targetY = next[0] * tileSize;

            if (targetX > getX()) setX(Math.min(getX() + this.vitesseX, targetX));
            else if (targetX < getX()) setX(Math.max(getX() - this.vitesseX, targetX));

            if (targetY > getY()) setY(Math.min(getY() + this.vitesseX, targetY));
            else if (targetY < getY()) setY(Math.max(getY() - this.vitesseX, targetY));
        }
    }
}
