package fr.iut.groupe.junglequest.modele.item;

public class BlockPlace {
    private String type; // "Bois", "Pierre", "File"
    private double x, y;
    private int vie = 10;

    public BlockPlace(String type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void subirDegats(int degats) {
        vie -= degats;
        if (vie < 0) vie = 0;
    }

    public boolean estDetruit() {
        return vie <= 0;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getVie() { return vie; }
    public String getType() { return type; }
}
