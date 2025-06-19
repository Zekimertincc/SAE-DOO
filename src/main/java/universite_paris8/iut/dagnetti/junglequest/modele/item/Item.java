package universite_paris8.iut.dagnetti.junglequest.modele.item;

public abstract class Item {
    protected String nom;
    public Item(String nom) {
        this.nom = nom;
    }
    public abstract int getQuantiteMax();

    public String getNom() {
        return nom;
    }
}
