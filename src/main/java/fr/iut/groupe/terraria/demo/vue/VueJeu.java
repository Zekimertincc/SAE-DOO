package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.personnage.EtatTemporaire;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import fr.iut.groupe.terraria.demo.modele.ressource.CanneSucre;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.ressource.Roche;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;
    private final List<Ressource> ressources;
    private final Joueur joueur;

    public VueJeu() {
        this.setPrefSize(640, 383);

        vueMap = new VueMap();
        ressources = new ArrayList<>();
        int[][] mapCollision = vueMap.getCollisionMap();

        // ✅ Position plus visible pour le joueur
        joueur = new Joueur(100, 200, 100, new EtatTemporaire(), mapCollision);

        vueJoueur = new VueJoueur();
        vueJoueur.setJoueur(joueur); // binding unique

        // Ajout de la map d'abord
        this.getChildren().add(vueMap.getTilePane());

        // ➕ Arbres
        ressources.add(new Arbre(300, 200));
        ressources.add(new Arbre(500, 220));
        ressources.add(new Arbre(150, 250));

        // ➕ Roches
        ressources.add(new Roche(400, 300));
        ressources.add(new Roche(600, 350));

        // ➕ Canne à sucre
        ressources.add(new CanneSucre(200, 250));
        ressources.add(new CanneSucre(500, 280));

        // ➕ Affichage des ressources
        for (Ressource r : ressources) {
            Image img;
            if (r instanceof Arbre) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/tree.png"));
            } else if (r instanceof Roche) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/roche.png"));
            } else if (r instanceof CanneSucre) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/CanneSucre.png"));
            } else {
                continue;
            }

            ImageView imgView = new ImageView(img);
            if (r instanceof Roche) {
                imgView.setFitWidth(147);
                imgView.setFitHeight(135);
            } else {
                imgView.setFitWidth(96);
                imgView.setFitHeight(64);
            }

            imgView.setLayoutX(r.getX());
            imgView.setLayoutY(r.getY());
            r.setImageView(imgView);
            this.getChildren().add(imgView);
        }

        // ✅ Ajout du joueur après tout (il est au-dessus)
        this.getChildren().add(vueJoueur.getJoueurVue());
    }

    public int[][] getCollisionMap() {
        return vueMap.getCollisionMap();
    }

    public VueJoueur getVueJoueur() {
        return vueJoueur;
    }

    public List<Ressource> getRessources() {
        return ressources;
    }

    public Joueur getJoueur() {
        return joueur;
    }
}
