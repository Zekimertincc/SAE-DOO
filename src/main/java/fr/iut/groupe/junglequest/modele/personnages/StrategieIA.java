package fr.iut.groupe.junglequest.modele.personnages;
import fr.iut.groupe.junglequest.modele.carte.Carte;

public interface StrategieIA {
    void mettreAJour(Personnage ennemi, Joueur joueur, Carte carte);
}
