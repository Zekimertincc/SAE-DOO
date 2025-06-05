package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.controller.moteur.MoteurPhysique;
import fr.iut.groupe.terraria.demo.modele.carte.Carte;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.vue.CarteAffichable;
import fr.iut.groupe.terraria.demo.vue.VueBackground;
import fr.iut.groupe.terraria.demo.vue.annimation.GestionAnimation;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;


import javafx.scene.image.WritableImage;

import static fr.iut.groupe.terraria.demo.modele.donnees.ConstantesJeu.*;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private VueBackground vueBackground;

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private int frameSort = 0;
    private double offsetX = 0;

    /**
     * Initialise le contrôleur principal du jeu : clavier, animation, logique du joueur et gestion des clics.
     */
    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.clavier = new GestionClavier(scene);

        // Initialisation des animations
        this.animation = new GestionAnimation(
                idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier
        );

        // Gestion du clic gauche pour attaquer
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!joueur.estEnAttaque()) {
                    joueur.attaquer();
                    animation.reset();
                    compteurAttaque = 0;
                } else {
                    animation.demandeCombo();
                }
            }
        });

        // Lancement de la boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJour();
            }
        }.start();
    }

    /**
     * Méthode principale appelée à chaque frame pour gérer les actions du joueur et l'affichage.
     */
    private void mettreAJour() {
        // Récupération des touches clavier pressées
        boolean gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        boolean droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        boolean toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        boolean toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        boolean toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        boolean toucheDegats = clavier.estAppuyee(KeyCode.DIGIT1);
        boolean toucheMort = clavier.estAppuyee(KeyCode.DIGIT2);
        boolean toucheSort = clavier.estAppuyee(KeyCode.E);
        boolean touchePreparationSaut = clavier.estAppuyee(KeyCode.DIGIT3);
        boolean toucheAtterrissage = clavier.estAppuyee(KeyCode.DIGIT4);

        // Déplacement horizontal
        if (gauche) {
            joueur.deplacerGauche(VITESSE_JOUEUR);
        } else if (droite) {
            joueur.deplacerDroite(VITESSE_JOUEUR);
        } else {
            joueur.arreter();
        }

        // Saut
        if (toucheSaut && joueur.estAuSol()) {
            joueur.sauter(IMPULSION_SAUT);
        }

        // Mise à jour physique (gravité, collisions)
        moteur.mettreAJourPhysique(joueur, carte, offsetX);

        // Calcul du décalage pour centrer la caméra sur le joueur
        offsetX = joueur.getX() - 640;
        if (offsetX < 0) offsetX = 0;

        // Redessiner la carte avec le nouveau décalage
        carteAffichable.redessiner(offsetX);

        // Redessiner le fond si présent
        if (vueBackground != null) {
            vueBackground.mettreAJourScroll(offsetX);
        }

        // Gestion des animations
        ImageView sprite = joueur.getSprite();

        if (toucheDegats) {
            animation.animerDegats(sprite);
        } else if (toucheMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 0;
        } else if (toucheSort) {
            animation.animerSort(sprite, frameSort++);
            if (frameSort >= 8) frameSort = 0;
        } else if (touchePreparationSaut) {
            animation.animerIdle(sprite, DELAI_FRAME);
            animation.animerPreparationSaut(sprite);
        } else if (toucheAtterrissage) {
            animation.animerAtterrissage(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, DELAI_FRAME, () -> joueur.finAttaque());
            compteurAttaque++;
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (toucheBouclier) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        // Inversion du sprite si le joueur regarde à gauche
        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
    }

    /**
     * Permet de lier dynamiquement la vue du fond à ce contrôleur.
     */
    public void setVueBackground(VueBackground vueBackground) {
        this.vueBackground = vueBackground;
    }
}
