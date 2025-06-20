package fr.iut.groupe.junglequest.modele.personnages;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EtatTemporaireTest {
    @Test
    void appliquerEtExpirationEffet() {
        EtatTemporaire etat = new EtatTemporaire();
        etat.appliquerEffet(2.0, true, true);
        assertTrue(etat.isInvincible());
        assertTrue(etat.isVulnerable());
        assertEquals(2.0, etat.getVitesseTemporaire());
        etat.setEffetFin(System.currentTimeMillis() - 1);
        etat.verifierExpiration();
        assertFalse(etat.isInvincible());
        assertFalse(etat.isVulnerable());
        assertEquals(1.5, etat.getVitesseTemporaire());
    }
}
