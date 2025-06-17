//package fr.iut.groupe.terraria.demo;
//
//import universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture.Champignon;
//import universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture.Pomme;
//import universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture.Poulet;
//import universite_paris8.iut.dagnetti.junglequest.modele.personnage.EtatTemporaire;
//import universite_paris8.iut.dagnetti.junglequest.modele.personnage.Joueur;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class NourritureTest {
//    private Joueur joueur1;
//    private Joueur joueur2;
//    private Joueur joueur3;
//
//    private Pomme pomme;
//    private Poulet poulet;
//    private Champignon champignon;
//
//    private EtatTemporaire etatTemporaire1;
//    private EtatTemporaire etatTemporaire2;
//    private EtatTemporaire etatTemporaire3;
//
//
//
//    @BeforeEach
//    void setUp() {
//        joueur1 = new Joueur(0, 0, 5, etatTemporaire1);
//        joueur2 = new Joueur(0, 0, 5, etatTemporaire2);
//        joueur3 = new Joueur(0, 0, 10, etatTemporaire3);
//
//        pomme = new Pomme();
//        poulet = new Poulet();
//        champignon = new Champignon();
//        etatTemporaire1 = new EtatTemporaire();
//        etatTemporaire2 = new EtatTemporaire();
//        etatTemporaire3 = new EtatTemporaire();
//    }
//
//    @Test
//    void testEffetPommeVitessePuisExpire() {
//        pomme.utiliserSur(joueur1);
//
//        assertEquals(3.0, joueur1.getEtatTemporaire().getVitesseTemporaire());
//        assertTrue(joueur1.getEtatTemporaire().getEffetFin() > System.currentTimeMillis());
//
//        joueur1.getEtatTemporaire().setEffetFin(System.currentTimeMillis() - 1);
//        joueur1.getEtatTemporaire().verifierExpiration();
//
//        assertEquals(1.5, joueur1.getEtatTemporaire().getVitesseTemporaire());
//        assertEquals(0, joueur1.getEtatTemporaire().getEffetFin());
//    }
//
//    @Test
//    void testEffetPouletInvinciblePuisExpire() {
//        poulet.utiliserSur(joueur2);
//
//        assertTrue(joueur2.getEtatTemporaire().isInvincible());
//        assertFalse(joueur2.getEtatTemporaire().isVulnerable());
//
//        joueur2.subirDegats(999);  // invincible
//        assertEquals(5, joueur2.getVie());
//
//        joueur2.getEtatTemporaire().setEffetFin(System.currentTimeMillis() - 1);
//        joueur2.getEtatTemporaire().verifierExpiration();
//
//        assertFalse(joueur2.getEtatTemporaire().isInvincible());
//
//        joueur2.subirDegats(1);
//        assertEquals(4, joueur2.getVie());
//    }
//
//    @Test
//    void testEffetChampignonVulnerablePuisExpire() {
//        champignon.utiliserSur(joueur3);
//
//        assertTrue(joueur3.getEtatTemporaire().isVulnerable());
//        assertFalse(joueur3.getEtatTemporaire().isInvincible());
//
//        joueur3.subirDegats(2); // x2 vulnérabilité
//        assertEquals(6, joueur3.getVie());
//
//        joueur3.getEtatTemporaire().setEffetFin(System.currentTimeMillis() - 1);
//        joueur3.getEtatTemporaire().verifierExpiration();
//
//        assertFalse(joueur3.getEtatTemporaire().isVulnerable());
//
//        joueur3.subirDegats(2);  // plus de x2
//        assertEquals(4, joueur3.getVie());
//    }
//}
