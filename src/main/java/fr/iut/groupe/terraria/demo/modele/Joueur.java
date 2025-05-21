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

        if (!collisionVerticale(nextY)) {
            y = nextY;
        } else {
            if (vitesseY > 0) {
                y = (int) (y / tileSize) * tileSize;
                enLair = false;
            }
            vitesseY = 0;
        }
    }

    public boolean collisionHorizontale(double nextX) {
        int leftTile = (int) nextX / tileSize;
        int rightTile = (int) (nextX + getLargeur() - 1) / tileSize;
        int topTile = (int) y / tileSize;
        int bottomTile = (int) (y + getHauteur() - 1) / tileSize;

        return checkCollision(leftTile, topTile) || checkCollision(leftTile, bottomTile)
                || checkCollision(rightTile, topTile) || checkCollision(rightTile, bottomTile);
    }

    public boolean collisionVerticale(double nextY) {
        int topTile = (int) nextY / tileSize;
        int bottomTile = (int) (nextY + getHauteur() - 1) / tileSize;
        int leftTile = (int) x / tileSize;
        int rightTile = (int) (x + getLargeur() - 1) / tileSize;

        return checkCollision(leftTile, topTile) || checkCollision(rightTile, topTile)
                || checkCollision(leftTile, bottomTile) || checkCollision(rightTile, bottomTile);
    }

    private boolean checkCollision(int tileX, int tileY) {
        if (tileX < 0 || tileX >= map[0].length || tileY < 0 || tileY >= map.length) {
            return true;
        }
        return map[tileY][tileX] != 0;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getLargeur() { return 32; }
    public int getHauteur() { return 32; }
}
