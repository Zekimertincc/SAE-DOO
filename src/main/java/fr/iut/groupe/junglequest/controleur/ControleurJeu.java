package fr.iut.groupe.junglequest.controleur;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.farm.Ressource;
import fr.iut.groupe.junglequest.modele.personnages.*;
import fr.iut.groupe.junglequest.vue.VueBackground;
import fr.iut.groupe.junglequest.vue.CarteAffichable;
import fr.iut.groupe.junglequest.vue.animation.GestionAnimation;
import fr.iut.groupe.junglequest.vue.personnages.VueJoueur;
import fr.iut.groupe.junglequest.vue.personnages.VueLoup;
import fr.iut.groupe.junglequest.vue.utilitaire.BarreVie;
import fr.iut.groupe.junglequest.controleur.interfacefx.*;
import fr.iut.groupe.junglequest.controleur.moteur.MoteurPhysique;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu.*;

public class ControleurJeu {

    private final MoteurPhysique moteur = new MoteurPhysique();
    private final Carte carte;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final Loup loup;
    private final VueJoueur vueJoueur;
    private final VueLoup vueLoup;
    private final GestionClavier clavier;
    private final GestionAnimation animation;
    private final InventaireController inventaireController;
    private final BarreVie barreVie;
    private final javafx.scene.control.Label labelVie;
    private final BarreVie barreVieLoup;
    private final javafx.scene.control.Label labelVieLoup;
    private final Guide guide;
    private final Forgeron forgeron;
    private final Pane pauseOverlay;
    private VueBackground vueBackground;

    private boolean enPause = false;
    private boolean joueurMort = false;
    private boolean loupMort = false;

    private final DoubleProperty offsetXProperty = new SimpleDoubleProperty();
    private final DoubleProperty offsetYProperty = new SimpleDoubleProperty();

    private final double largeurEcran;
    private final double hauteurEcran;
    private final int largeurFrameLoup;
    private final int hauteurFrameLoup;
    private final int largeurFrameLoupAttaque;
    private final int hauteurFrameLoupAttaque;

    private int compteurAttaque = 0;
    private int frameMort = 0;
    private int framesAtterrissageRestants = 0;
    private int delaiDegats = 0;
    private int frameLoup = 0;
    private int compteurLoup = 0;

    private Stage fenetreParametres;
    private Stage fenetreForge;
    private boolean dialogueGuideAffiche = false;

    private List<Ressource> ressources;

    // États du clavier
    private boolean gauche;
    private boolean droite;
    private boolean toucheSaut;
    private boolean toucheAccroupi;
    private boolean toucheBouclier;
    private boolean toucheDegats;

    public ControleurJeu(Scene scene, Carte carte, CarteAffichable carteAffichable, Joueur joueur, VueJoueur vueJoueur,
                         Loup loup, VueLoup vueLoup, Guide guide,
                         Forgeron forgeron,
                         InventaireController inventaireController, BarreVie barreVie, javafx.scene.control.Label labelVie,
                         BarreVie barreVieLoup, javafx.scene.control.Label labelVieLoup, Pane pauseOverlay,
                         WritableImage[] idle, WritableImage[] marche,
                         WritableImage[] attaque,
                         WritableImage[] preparationSaut, WritableImage[] volSaut, WritableImage[] sautReload,
                         WritableImage[] chute, WritableImage[] atterrissage,
                         WritableImage[] degats, WritableImage[] mort,
                         WritableImage[] sort, WritableImage[] accroupi, WritableImage[] bouclier) {

        this.carte = carte;
        this.carteAffichable = carteAffichable;
        this.joueur = joueur;
        this.vueJoueur = vueJoueur;
        this.loup = loup;
        this.vueLoup = vueLoup;
        this.guide = guide;
        this.forgeron = forgeron;
        this.inventaireController = inventaireController;
        this.barreVie = barreVie;
        this.labelVie = labelVie;
        this.barreVieLoup = barreVieLoup;
        this.labelVieLoup = labelVieLoup;
        this.pauseOverlay = pauseOverlay;

        this.clavier = new GestionClavier(scene);
        this.largeurEcran = scene.getWidth();
        this.hauteurEcran = scene.getHeight();

        // Lier vie
        if (labelVie != null) labelVie.textProperty().bind(joueur.pointsDeVieProperty().asString());
        if (barreVie != null) barreVie.ratioProperty().bind(joueur.pointsDeVieProperty().divide((double) VIE_MAX_JOUEUR));
        if (labelVieLoup != null) labelVieLoup.textProperty().bind(loup.pointsDeVieProperty().asString());
        if (barreVieLoup != null) barreVieLoup.ratioProperty().bind(loup.pointsDeVieProperty().divide((double) VIE_MAX_LOUP));

        // Initialisation visuelle
        if (pauseOverlay != null) pauseOverlay.setVisible(false);
        vueJoueur.lierPosition(offsetXProperty, offsetYProperty);
        vueLoup.lierPosition(offsetXProperty, offsetYProperty);

        var imgLoup = vueLoup.getSprite().getImage();
        largeurFrameLoup = (int) (imgLoup.getWidth() / 6);
        hauteurFrameLoup = (int) imgLoup.getHeight();
        var imgLoupAttack = loup.getAttackImage();
        largeurFrameLoupAttaque = (int) (imgLoupAttack.getWidth() / 6);
        hauteurFrameLoupAttaque = (int) imgLoupAttack.getHeight();

        // Animations
        this.animation = new GestionAnimation(
                idle, marche, attaque,
                preparationSaut, volSaut, sautReload,
                chute, atterrissage,
                degats, mort,
                sort, accroupi, bouclier
        );

        // Clic gauche → attaque / ressource
        scene.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double xMonde = e.getX() + offsetXProperty.get();
                double yMonde = e.getY() + offsetYProperty.get();

                if (essayerCasserRessource(xMonde, yMonde)) return;

                double distance = Math.abs(joueur.getX() - loup.getX());
                if (!joueur.estEnAttaque()) {
                    joueur.attaquer();
                    animation.reset();
                    compteurAttaque = 0;
                    if (distance <= PORTEE_ATTAQUE_JOUEUR) loup.subirDegats(DEGATS_JOUEUR_LOUP);
                } else {
                    animation.demandeCombo();
                    if (distance <= PORTEE_ATTAQUE_JOUEUR) loup.subirDegats(DEGATS_COMBO_SUPPLEMENTAIRES);
                }
                if (loup.getPointsDeVie() <= 0) loupMort = true;
                if (joueur.isBouclierActif()) joueur.desactiverBouclier();
            }
        });

        // Clic droit → interaction (placer bloc, etc.)
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) gererClicDroit(e.getX(), e.getY());
        });

        // Raccourcis clavier
        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
                case P -> { if (fenetreParametres == null) ouvrirParametres(scene); }
                case I -> { if (inventaireController != null) inventaireController.basculerAffichage(); }
                case C -> {
                    double dist = Math.abs(joueur.getX() - forgeron.getX());
                    if (dist <= 40 && fenetreForge == null) ouvrirForge(scene);
                }
                case ENTER -> {
                    enPause = !enPause;
                    if (pauseOverlay != null) pauseOverlay.setVisible(enPause);
                }
            }
        });

        // Boucle de jeu
        new AnimationTimer() {
            @Override public void handle(long now) { mettreAJour(); }
        }.start();
    }

    private void mettreAJour() {
        if (enPause) return;

        if (delaiDegats > 0) delaiDegats--;
        if (framesAtterrissageRestants > 0) framesAtterrissageRestants--;

        gererClavierEtInventaire();
        mettreAJourMouvements();
        mettreAJourAffichageEtAnimations();
    }

    private void gererClavierEtInventaire() {
        gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        toucheAccroupi = clavier.estAppuyee(KeyCode.CONTROL);
        toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        toucheDegats = clavier.estAppuyee(KeyCode.M);

        joueur.gererBouclier(toucheBouclier);
        joueur.gererMouvement(gauche, droite);
        joueur.gererSaut(toucheSaut);

        if (inventaireController != null) {
            KeyCode[] chiffres = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
                    KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6,
                    KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9};
            for (int i = 0; i < chiffres.length; i++) {
                if (clavier.estAppuyee(chiffres[i])) inventaireController.selectionnerIndex(i);
            }
            if (clavier.estAppuyee(KeyCode.E)) inventaireController.deselectionner();
        }

        if (toucheDegats) joueur.encaisserDegats(1);
        if (joueur.getPointsDeVie() <= 0) joueurMort = true;
    }

    private void mettreAJourMouvements() {
        boolean aAtterri = moteur.mettreAJourPhysique(joueur, carte,
                vueJoueur.getLargeur(), vueJoueur.getHauteur());
        if (aAtterri) framesAtterrissageRestants = animation.getNbFramesAtterrissage();

        if (!loupMort) {
            loup.mettreAJourComportement( joueur,carte);
        } else {
            loup.arreter();
        }
    }

    private void mettreAJourAffichageEtAnimations() {
        double offsetX = Math.max(0, Math.min(joueur.getX() - largeurEcran / 2,
                carte.getLargeur() * TAILLE_TUILE - largeurEcran));
        double offsetY = Math.max(0, Math.min(joueur.getY() - hauteurEcran / 2,
                carte.getHauteur() * TAILLE_TUILE - hauteurEcran));
        offsetXProperty.set(offsetX);
        offsetYProperty.set(offsetY);

        carteAffichable.redessiner(offsetX, offsetY);
        if (vueBackground != null) vueBackground.mettreAJourScroll(offsetX);

        guide.getSprite().setX(guide.getX() - offsetX);
        guide.getSprite().setY(guide.getY() - offsetY);
        forgeron.getSprite().setX(forgeron.getX() - offsetX);
        forgeron.getSprite().setY(forgeron.getY() - offsetY);

        if (ressources != null) {
            for (Ressource r : ressources) {
                r.getSprite().setX(r.getX() - offsetX);
                r.getSprite().setY(r.getY() - offsetY);
            }
        }

        // Collisions joueur-loup
        double distanceLoupJoueur = Math.abs(joueur.getX() - loup.getX());
        boolean collision = distanceLoupJoueur < 20 &&
                joueur.getX() < loup.getX() + vueLoup.getSprite().getFitWidth() &&
                joueur.getX() + vueJoueur.getSprite().getFitWidth() > loup.getX() &&
                joueur.getY() < loup.getY() + vueLoup.getSprite().getFitHeight() &&
                joueur.getY() + vueJoueur.getSprite().getFitHeight() > loup.getY();

        if (!loupMort && collision && delaiDegats == 0 && !loup.estEnAttaque() && !joueur.isBouclierActif()) {
            joueur.encaisserDegats(loup.getDegats());
            delaiDegats = DUREE_DEGATS_JOUEUR;
            loup.attaquer(joueur.getX());
            if (joueur.getPointsDeVie() <= 0) joueurMort = true;
        }

        // Animations
        ImageView sprite = vueJoueur.getSprite();
        if (joueurMort) {
            animation.animerMort(sprite, frameMort++);
            if (frameMort >= 12) frameMort = 11;
        } else if (toucheDegats || delaiDegats > 0) {
            animation.animerDegats(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, Math.max(1, DELAI_FRAME - 2), joueur::finAttaque);
            compteurAttaque++;
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (framesAtterrissageRestants > 0) {
            animation.animerAtterrissage(sprite, animation.getNbFramesAtterrissage() - framesAtterrissageRestants);
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (joueur.isBouclierActif()) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
    }

    private boolean essayerCasserRessource(double xMonde, double yMonde) {
        if (ressources == null) return false;
        Iterator<Ressource> it = ressources.iterator();
        while (it.hasNext()) {
            Ressource r = it.next();
            ImageView iv = r.getSprite();
            double w = iv.getFitWidth(), h = iv.getFitHeight();
            if (xMonde >= r.getX() && xMonde <= r.getX() + w &&
                    yMonde >= r.getY() && yMonde <= r.getY() + h) {
                if (r.interagir(joueur)) {
                    it.remove();
                    if (inventaireController != null) inventaireController.rafraichir();
                }
                return true;
            }
        }
        return false;
    }

    private void gererClicDroit(double xScene, double yScene) {
        // (unchanged)
    }

    private void ouvrirParametres(Scene scene) { /* (unchanged) */ }
    private void ouvrirForge(Scene scene) { /* (unchanged) */ }

    public void setVueBackground(VueBackground vueBackground) { this.vueBackground = vueBackground; }
    public void setRessources(List<Ressource> ressources) { this.ressources = ressources; }
}
