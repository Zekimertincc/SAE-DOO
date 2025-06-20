package fr.iut.groupe.junglequest.modele.item.equipement;

import fr.iut.groupe.junglequest.modele.item.equipement.arme.Arc;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.Ciblable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipementTest {
    static class DummyTarget implements Ciblable {
        int pv = 50;
        final double x; final double y;
        DummyTarget(double x, double y){ this.x = x; this.y = y; }
        @Override public String getTypeCible(){ return "Ennemi"; }
        @Override public void subirDegats(int degats){ pv -= degats; }
        @Override public double getX(){ return x; }
        @Override public double getY(){ return y; }
        @Override public String getNom(){ return "Loup"; }
    }

    @Test
    void degatsContreInfligeEtUse() {
        Joueur j = new Joueur(0,0);
        Arc arc = new Arc();
        DummyTarget cible = new DummyTarget(1,1);
        arc.degatsContre(j, cible);
        assertEquals(38, cible.pv);
        assertEquals(18, arc.getDurabilite());
    }

    @Test
    void aucunePorteeReduitDurabilite() {
        Joueur j = new Joueur(0,0);
        Arc arc = new Arc();
        DummyTarget cible = new DummyTarget(100,0);
        arc.degatsContre(j, cible);
        assertEquals(50, cible.pv);
        assertEquals(18, arc.getDurabilite());
    }
}
