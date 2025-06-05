package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VueJoueur {
    private final ImageView joueurVue;
    private final Image[] idleFrames;
    private final Image[] runFrames;
    private int frameIndex = 0;
    private long lastTime = 0;
    private String currentState = "idle";
    private boolean lookingRight = true;

    public VueJoueur() {
        // Chargement des frames
        idleFrames = new Image[] {
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i2.png"))
        };
        runFrames = new Image[] {
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k2.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k3.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k4.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k5.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k6.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k7.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k8.png"))
        };

        joueurVue = new ImageView(idleFrames[0]);
        joueurVue.setFitWidth(64);
        joueurVue.setFitHeight(96);

        // Animation frame par frame
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime > 120_000_000) {
                    frameIndex = (frameIndex + 1) % (
                            currentState.equals("idle") ? idleFrames.length : runFrames.length
                    );
                    joueurVue.setImage(
                            currentState.equals("idle") ? idleFrames[frameIndex] : runFrames[frameIndex]
                    );
                    lastTime = now;
                }
            }
        }.start();
    }

    /**
     * Met à jour le sprite affiché selon l'état ("idle" ou "run") et la direction.
     */
    public void updateSprite(String state, boolean isRight) {
        if (!state.equals(currentState)) {
            frameIndex = 0;
        }
        currentState = state;

        if (isRight != lookingRight) {
            joueurVue.setScaleX(isRight ? 1 : -1);
            lookingRight = isRight;
        }
    }

    /**
     * Lie le joueur modèle à la vue. Appelé une seule fois.
     */
    public void setJoueur(Joueur joueur) {
        joueurVue.layoutXProperty().bind(joueur.xProperty());
        joueurVue.layoutYProperty().bind(joueur.yProperty());
    }

    public ImageView getJoueurVue() {
        return joueurVue;
    }
}
