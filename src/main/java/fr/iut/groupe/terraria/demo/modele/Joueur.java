package fr.iut.groupe.terraria.demo.modele;

public class Joueur {
    private double x, y;
    private double vitesseY = 0;
    private final double vitesseX = 2;
    private boolean enLair = false;
    private final int tileSize = 32;
    private final int[][] map;

    public Joueur(double x, double y, int[][] map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    public void gauche() {
        double nextX = x - vitesseX;
        if (!collisionHorizontale(nextX)) {
            x = Math.max(0, nextX);
        }
    }

    public void droite() {
        double nextX = x + vitesseX;
        int maxX = map[0].length * tileSize - getLargeur();
        if (!collisionHorizontale(nextX)) {
            x = Math.min(maxX, nextX);
        }
    }

    public void sauter() {
        if (!enLair) {
            vitesseY = -10;
            enLair = true;
        }
    }

    public void appliquerGravite() {
        vitesseY += 0.5;
        double nextY = y + vitesseY;

        int leftTile  = (int) x / tileSize;
        int rightTile = (int) (x + getLargeur() - 1) / tileSize;
        int footTile  = (int) (nextY + getHauteur()) / tileSize;

        boolean collisionSol = false;
        for (int tx = leftTile; tx <= rightTile; tx++) {
            if (footTile < 0 || footTile >= map.length || tx < 0 || tx >= map[0].length) continue;
            if (map[footTile][tx] == 1) {
                collisionSol = true;
                break;
            }
        }

        if (collisionSol) {
            vitesseY = 0;
            enLair = false;
            y = footTile * tileSize - getHauteur();
        } else {
            y = nextY;
            enLair = true;
        }
    }

    private boolean collisionHorizontale(double nextX) {
        int topTile    = (int) y / tileSize;
        int bottomTile = (int) (y + getHauteur() - 1) / tileSize;
        int leftTile   = (int) nextX / tileSize;
        int rightTile  = (int) (nextX + getLargeur() - 1) / tileSize;

        for (int ty = topTile; ty <= bottomTile; ty++) {
            if (leftTile  >= 0 && leftTile  < map[0].length && ty >= 0 && ty < map.length) {
                if (map[ty][leftTile] == 1) return true;
            }
            if (rightTile >= 0 && rightTile < map[0].length && ty >= 0 && ty < map.length) {
                if (map[ty][rightTile] == 1) return true;
            }
        }
        return false;
    }

    public double getX()       { return x; }
    public double getY()       { return y; }
    public int    getLargeur() { return 64; }
    public int    getHauteur() { return 96; }
}
