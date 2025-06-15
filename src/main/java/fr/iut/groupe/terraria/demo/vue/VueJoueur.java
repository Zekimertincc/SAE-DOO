package fr.iut.groupe.terraria.demo.vue;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueJoueur {
    private final Group joueurGroup;
    private final ImageView joueurSprite;
    private final ImageView couteauSprite;
    private final Rectangle healthBarBg;
    private final Rectangle healthBar;
    private final Image[] idleFrames;
    private final Image[] runFrames;
    private int frameIndex = 0;
    private long lastTime  = 0;
    private String currentState = "idle";
    private boolean lookingRight = true;

    public VueJoueur() {
        idleFrames = new Image[]{
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/i2.png"))
        };
        runFrames = new Image[]{
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k1.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k2.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k3.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k4.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k5.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k6.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k7.png")),
                new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/k8.png"))
        };

        joueurSprite = new ImageView(idleFrames[0]);
        joueurSprite.setFitWidth(64);
        joueurSprite.setFitHeight(96);

        healthBarBg = new Rectangle(64, 6, Color.DARKRED);
        healthBarBg.setTranslateY(-10);
        healthBar = new Rectangle(64, 6, Color.LIMEGREEN);
        healthBar.setTranslateY(-10);

        // couteau.png sprite
        couteauSprite = new ImageView(new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/couteau.png")));
        couteauSprite.setFitWidth(32);
        couteauSprite.setFitHeight(32);
        couteauSprite.setTranslateX(55); //
        couteauSprite.setTranslateY(40);

        joueurGroup = new Group(joueurSprite, couteauSprite, healthBarBg, healthBar);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime > 120_000_000) {
                    frameIndex = (frameIndex + 1) %
                            (currentState.equals("idle") ? idleFrames.length : runFrames.length);
                    joueurSprite.setImage(currentState.equals("idle")
                            ? idleFrames[frameIndex]
                            : runFrames[frameIndex]);
                    lastTime = now;
                }
            }
        }.start();
    }

    public void updateSprite(String state, boolean isRight) {
        if (!state.equals(currentState)) frameIndex = 0;
        currentState = state;

        if (isRight != lookingRight) {
            joueurSprite.setScaleX(isRight ? 1 : -1);
            couteauSprite.setScaleX(isRight ? 1 : -1);
            lookingRight = isRight;
        }
    }

    /** Update the size of the health bar according to current life. */
    public void updateHealth(int vie, int vieMax) {
        double ratio = vieMax == 0 ? 0 : (double) vie / vieMax;
        healthBar.setWidth(64 * ratio);
    }

    public Group getJoueurVue() {
        return joueurGroup;
    }
}
