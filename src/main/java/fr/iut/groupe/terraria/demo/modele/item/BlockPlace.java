package fr.iut.groupe.terraria.demo.modele.item;

public class BlockPlace {
    private Block block;
    private double x, y;
    private int vie;

    public BlockPlace(Block block, double x, double y) {
        this.block = block;
        this.x = x;
        this.y = y;
        this.vie = 10;
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
    public Block getBlock() { return block; }
}
