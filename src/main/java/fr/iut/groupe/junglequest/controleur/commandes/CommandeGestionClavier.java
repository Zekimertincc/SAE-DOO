package fr.iut.groupe.junglequest.controleur.commandes;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import javafx.scene.input.KeyCode;
import fr.iut.groupe.junglequest.controleur.GestionClavier;
import fr.iut.groupe.junglequest.controleur.interfacefx.InventaireController;

/**
 * Commande pour gérer les entrées clavier du joueur.
 */
public class CommandeGestionClavier implements Commande {
    private final GestionClavier clavier;
    private final Joueur joueur;
    private final InventaireController inventaireController;

    public CommandeGestionClavier(GestionClavier clavier, Joueur joueur, InventaireController inventaireController) {
        this.clavier = clavier;
        this.joueur = joueur;
        this.inventaireController = inventaireController;
    }

    @Override
    public void executer() {
        boolean gauche = clavier.estAppuyee(KeyCode.Q) || clavier.estAppuyee(KeyCode.LEFT);
        boolean droite = clavier.estAppuyee(KeyCode.D) || clavier.estAppuyee(KeyCode.RIGHT);
        boolean toucheSaut = clavier.estAppuyee(KeyCode.SPACE);
        boolean toucheBouclier = clavier.estAppuyee(KeyCode.SHIFT);
        boolean toucheDegats = clavier.estAppuyee(KeyCode.M);

        // Gestion du mouvement
        joueur.gererBouclier(toucheBouclier);
        joueur.gererMouvement(gauche, droite);
        joueur.gererSaut(toucheSaut);

        // Gestion de l'inventaire
        if (inventaireController != null) {
            gererTouchesInventaire();
        }

        // Gestion des dégâts (mode debug)
        if (toucheDegats) {
            joueur.encaisserDegats(1);
        }
    }

    private void gererTouchesInventaire() {
        KeyCode[] chiffres = {
            KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3,
            KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6,
            KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9
        };

        for (int i = 0; i < chiffres.length; i++) {
            if (clavier.estAppuyee(chiffres[i])) {
                inventaireController.selectionnerIndex(i);
            }
        }

        if (clavier.estAppuyee(KeyCode.E)) {
            inventaireController.deselectionner();
        }
    }
}