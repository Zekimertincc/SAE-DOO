package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.monde.AlgoAStar;
import java.util.Collections;
import java.util.List;

public class Loup extends Ennemi {
    public Loup(double x, double y) {
        super(x, y, 2, 0, 15, 1,5);
    }

    private final int tileSize = 32;
    private List<int[]> path = Collections.emptyList();
    private int pathIndex = 1;
    private long lastCalc = 0;

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
        int endRow   = (int) (joueur.getY() / tileSize);
        int endCol   = (int) (joueur.getX() / tileSize);

        long now = System.currentTimeMillis();
        if (path.isEmpty() || pathIndex >= path.size() || now - lastCalc > 300) {
            path = AlgoAStar.findPath(map, startRow, startCol, endRow, endCol);
            pathIndex = 1;
            lastCalc = now;
        }

        if (pathIndex < path.size()) {
            int[] next = path.get(pathIndex);
            double targetX = next[1] * tileSize;
            double targetY = next[0] * tileSize;

            if (targetX > getX()) setX(Math.min(getX() + this.vitesseX, targetX));
            else if (targetX < getX()) setX(Math.max(getX() - this.vitesseX, targetX));

            if (targetY > getY()) setY(Math.min(getY() + this.vitesseX, targetY));
            else if (targetY < getY()) setY(Math.max(getY() - this.vitesseX, targetY));

            if (Math.abs(getX() - targetX) < 1 && Math.abs(getY() - targetY) < 1) {
                pathIndex++;
            }
        }
    }
}