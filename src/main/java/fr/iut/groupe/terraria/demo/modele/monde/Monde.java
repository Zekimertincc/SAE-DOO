package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import fr.iut.groupe.terraria.demo.modele.personnage.Personnage;

import java.util.ArrayList;

public class Monde {
    private ArrayList<Ennemi> ennemis;

    public Monde() {
        ennemis = new ArrayList<>();
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
    }


    /*
     boucle qui gere les ennemis et leurs comportement
     le premier if c'est pour voir si le personnage est dans la zone des ennemis
     si oui alors il va se deplacer vers le personnage
     et si il est assez proche du personnage il le met des degats
    */
    public void mettreAJour(Joueur joueur) {
        for (Ennemi ennemi : ennemis) {
            if (ennemi.estDansZone(ennemi.getX() - 50, ennemi.getX() + 50)) {
                if (ennemi.estProcheDe(joueur, 100)) {
                    ennemi.seDeplacerVers(joueur);
                }
                if (ennemi.estProcheDe(joueur, 10)) {
                    ennemi.attaquer(joueur);
                }
            }
        }
    }

    public ArrayList<Ennemi> getEnnemis() {
        return ennemis;
    }
}
