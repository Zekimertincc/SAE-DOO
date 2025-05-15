package fr.iut.groupe.terraria.demo.modele.item;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.nourriture.Nourriture;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

import java.util.ArrayList;
import java.util.Random;

public class Coffre {
    private double x, y;
    private boolean ouvert;
    private ArrayList<Item> listContenu;

    public Coffre(double x, double y, ArrayList<Item> contenu) {
        this.x = x;
        this.y = y;
        this.ouvert = false;
        this.listContenu = contenu;
    }
    // choisir un item dans le coffre
    public Item ouvrir() {
        if (this.ouvert || listContenu.isEmpty()) return null;
        Random rand = new Random();

        return listContenu.get(rand.nextInt(listContenu.size()));
    }
    // mettre effet sur la personnage.
    public void interactionAvecCoffre(Joueur joueur, Inventaire inventaire) {
        double distance = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        if (distance < 10) {
            Item item = ouvrir();
            if (item != null) {
                if (item instanceof Nourriture) {
                    ((Nourriture) item).utiliserSur(joueur);
                } else {
                    inventaire.ajouter(item);
                }
            }
        }
    }

    public boolean estOuvert() {
        return ouvert;
    }
    public double getX() { return x; }
    public double getY() { return y; }
}

