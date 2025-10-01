package fr.iut.groupe.junglequest.modele.item.equipement;
import fr.iut.groupe.junglequest.modele.item.Inventaire;

public interface Recette {
    public boolean verificationConstructiion(Inventaire inventaire, Equipement p);

}
