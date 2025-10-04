package fr.iut.groupe.junglequest.controleur.moteur;

import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.modele.farm.Ressource;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import javafx.scene.image.ImageView;
import java.util.Iterator;

public class ControleurInteraction {
    private final Environnement env;

    public ControleurInteraction(Environnement env) {
        this.env = env;
    }

    public boolean essayerCasserRessource(double xMonde, double yMonde) {
        Joueur joueur = env.getJoueur();
        Iterator<Ressource> it = env.getRessources().iterator();

        while (it.hasNext()) {
            Ressource r = it.next();
            ImageView iv = r.getSprite();
            double w = iv.getFitWidth();
            double h = iv.getFitHeight();

            if (xMonde >= r.getX() && xMonde <= r.getX() + w && yMonde >= r.getY() && yMonde <= r.getY() + h) {
                if (r.getNom().equalsIgnoreCase("Arbre") || joueur.getInventaire().contient("Hache", 1)) {
                    iv.setVisible(false);
                    it.remove();
                    joueur.getInventaire().ajouterItem(r.getItemRecompense(), 1);
                    return true;
                }
            }
        }
        return false;
    }
}
