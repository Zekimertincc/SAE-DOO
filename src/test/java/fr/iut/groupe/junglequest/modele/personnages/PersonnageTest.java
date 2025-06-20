package fr.iut.groupe.junglequest.modele.personnages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonnageTest {

    static class TestPerso extends Personnage {
        TestPerso() { super(0,0,10,10); }
    }

    @Test
    void sautUniquementAuSol() {
        TestPerso p = new TestPerso();
        p.setEstAuSol(true);
        p.sauter(5);
        assertEquals(-5, p.getVitesseY());
        assertFalse(p.estAuSol());
        p.sauter(5);
        assertEquals(-5, p.getVitesseY());
    }

    @Test
    void appliquerGraviteLimiteLaVitesse() {
        TestPerso p = new TestPerso();
        p.appliquerGravite(1,3);
        p.appliquerGravite(1,3);
        p.appliquerGravite(1,3);
        p.appliquerGravite(1,3);
        assertEquals(3, p.getVitesseY());
    }

    @Test
    void deplacementGaucheDroiteEtArret() {
        TestPerso p = new TestPerso();
        p.deplacerDroite(2);
        assertEquals(2, p.getVitesseX());
        assertFalse(p.estVersGauche());
        p.deplacerGauche(1);
        assertEquals(-1, p.getVitesseX());
        assertTrue(p.estVersGauche());
        p.arreter();
        assertEquals(0, p.getVitesseX());
    }
}
