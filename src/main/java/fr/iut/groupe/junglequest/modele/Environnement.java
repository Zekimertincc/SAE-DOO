package fr.iut.groupe.junglequest.modele;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.item.farm.Ressource;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.personnages.Guide;
import fr.iut.groupe.junglequest.modele.personnages.Forgeron;
import fr.iut.groupe.junglequest.controleur.moteur.MoteurPhysique;

import java.util.ArrayList;
import java.util.List;

public class Environnement {

    private final Carte carte;
    private final Joueur joueur;
    private final Loup loup;
    private final Guide guide;
    private final Forgeron forgeron;
    private final MoteurPhysique moteur;
    private List<Ressource> ressources = new ArrayList<>();

    public Environnement(Joueur joueur, Loup loup, Carte carte, Guide guide, Forgeron forgeron) {
        this.joueur = joueur;
        this.loup = loup;
        this.carte = carte;
        this.guide = guide;
        this.forgeron = forgeron;
        this.moteur = new MoteurPhysique();
    }

    public Carte getCarte() { return carte; }
    public Joueur getJoueur() { return joueur; }
    public Loup getLoup() { return loup; }
    public Guide getGuide() { return guide; }
    public Forgeron getForgeron() { return forgeron; }
    public MoteurPhysique getMoteurPhysique() { return moteur; }

    public List<Ressource> getRessources() { return ressources; }
    public void setRessources(List<Ressource> ressources) { this.ressources = ressources; }
}
