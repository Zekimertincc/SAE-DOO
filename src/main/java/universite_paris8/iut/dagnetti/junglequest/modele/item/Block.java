package universite_paris8.iut.dagnetti.junglequest.modele.item;

public class Block extends Item {
    private final String type;         // Ex: "Bois", "Pierre", "File"
    private final int quantiteMax = 5; // Limite par stack dans l'inventaire

    public Block(String type) {
        super("Bloc de " + type); // Nom visible : "Bloc de Bois", etc.
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getQuantiteMax() {
        return quantiteMax;
    }
}
