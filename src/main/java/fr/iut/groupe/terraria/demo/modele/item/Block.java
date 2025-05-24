package fr.iut.groupe.terraria.demo.modele.item;

public class Block extends Item {
    private String type; // Bois Pierre File pour savoir de quelle ressource il vient
    private final int quantiteMax;

    public Block(String type) {
        super("Bloc de " + type);
        this.type = type;
        this.quantiteMax = 5;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getQuantiteMax() {
        return quantiteMax;
    }
}
