package fr.iut.groupe.terraria.demo.modele.item;

public class Block extends Item {
    private String type;
    private int quantiteMax = 5;
    private double x, y;
    private int vie;

    public Block(String type) {
        super("Bloc de " + type);
        this.type = type;
        this.vie = 10;
    }

    public void placer(double x, double y) {
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

    public String getType()     { return type; }
    public double getX()        { return x; }
    public double getY()        { return y; }
    public int getVie()         { return vie; }

    @Override
    public int getQuantiteMax() { return quantiteMax; }
}
