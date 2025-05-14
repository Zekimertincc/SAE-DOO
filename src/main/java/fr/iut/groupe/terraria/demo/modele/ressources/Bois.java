package fr.iut.groupe.terraria.demo.modele.ressources;

public class Bois extends Ressource {

    public Bois() {
        super("Bois");
    }

    @Override
    public void utiliser() {
        System.out.println("Tu utilises le bois pour construire.");
    }
}
