package fr.iut.groupe.terraria.demo.modele;

public class Joueur {
    private double x, y;
    private double vitesseY = 0;
    private double vitesseX = 4;
    private boolean enLair = false;
    private final int tileSize = 32;

    public Joueur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void gauche(int[][] map) {
        double nextX = x - vitesseX;
        if (!collisionHorizontale(map, nextX)) {
            x = Math.max(0, nextX); // ekran sol dışına çıkma
        }
    }

    public void droite(int[][] map, int mapWidthPx) {
        double nextX = x + vitesseX;
        if (!collisionHorizontale(map, nextX)) {
            x = Math.min(mapWidthPx - getLargeur(), nextX); // ekran sağ dışına çıkma
        }
    }

    public void sauter() {
        if (!enLair) {
            vitesseY = -10;
            enLair = true;
        }
    }

    public void appliquerGravite(int[][] map) {
        vitesseY += 0.5;
        double nextY = y + vitesseY;

        int tileX = (int) (x + getLargeur() / 2) / tileSize;
        int tileY = (int) (nextY + getHauteur()) / tileSize;

        if (tileY >= map.length || tileX < 0 || tileX >= map[0].length) {
            y = nextY;
            return;
        }

        if (map[tileY][tileX] == 1) {
            vitesseY = 0;
            enLair = false;
            y = tileY * tileSize - getHauteur();
        } else {
            y = nextY;
            enLair = true;
        }
    }

    private boolean collisionHorizontale(int[][] map, double nextX) {
        int leftTile = (int) nextX / tileSize;
        int rightTile = (int) (nextX + getLargeur() - 1) / tileSize;
        int footTile = (int) (y + getHauteur() - 1) / tileSize;
        int headTile = (int) y / tileSize;

        for (int ty = headTile; ty <= footTile; ty++) {
            if (leftTile < 0 || rightTile >= map[0].length || ty < 0 || ty >= map.length) return true;
            if (map[ty][leftTile] == 1 || map[ty][rightTile] == 1) return true;
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLargeur() {
        return 64;
    }

    public int getHauteur() {
        return 96;
    }
}
