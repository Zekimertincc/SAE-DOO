package fr.iut.groupe.junglequest;

import fr.iut.groupe.junglequest.controleur.demarrage.LanceurJeu;
import javafx.application.Application;

/**
 * Point d'entrée principal de l'application.
 * Cette classe se contente de lancer l'application JavaFX.
 * 
 * Architecture MVC:
 * Main → LanceurJeu (JavaFX Application) → ControleurPrincipal → [Model + View]
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(LanceurJeu.class, args);
    }
}

