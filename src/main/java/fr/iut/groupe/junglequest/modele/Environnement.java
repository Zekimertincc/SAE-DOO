package fr.iut.groupe.junglequest.modele;

import fr.iut.groupe.junglequest.modele.personnages.Guide;
import fr.iut.groupe.junglequest.modele.personnages.Forgeron;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.bloc.BlocManager;
import fr.iut.groupe.junglequest.modele.farm.Ressource;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe centrale du modèle qui regroupe tous les éléments du monde du jeu.
 * Cela permet d'éviter que les contrôleurs importent directement tous les objets du modèle.
 */
public class Environnement {

    private final Joueur joueur;
    private final Loup loup;
    private final Carte carte;
    private final Guide guide;
    private final Forgeron forgeron;
    private BlocManager blocManager;
    private final List<Ressource> ressources;

    public Environnement(Joueur joueur, Loup loup, Carte carte, Guide guide, Forgeron forgeron) {
        this.joueur = joueur;
        this.loup = loup;
        this.carte = carte;
        this.guide = guide;
        this.forgeron = forgeron;
        this.ressources = new ArrayList<>();
    }


    public Joueur getJoueur() { return joueur; }
    public Loup getLoup() { return loup; }
    public Carte getCarte() { return carte; }
    public Guide getGuide() { return guide; }
    public Forgeron getForgeron() { return forgeron; }
    public BlocManager getBlocManager() { return blocManager; }
    public List<Ressource> getRessources() { return ressources; }
}
