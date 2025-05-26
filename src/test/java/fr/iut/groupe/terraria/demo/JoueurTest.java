package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;
import fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Hache;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Loup;
import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
class JoueurTest {
    private Joueur joueur;
    private Hache hache;
    private Arc arc;
    private Arbre arbre; // 20hp
    private Arbre arbre2;
    private Loup loup; // 15 hp

    @BeforeEach
    public void setUp() {
        joueur = new Joueur(1, 1, 5);
        hache = new Hache();
        arc = new Arc();
        arbre = new Arbre(2, 2);
        arbre2 = new Arbre(100, 100);
        loup = new Loup(3,3);
    }

    @Test
    public void testUtiliserEquipementSur() {
        // Hache 3 degats, eleve 6 de degats car il y a degats x2
        joueur.setEquipementActuel(hache);
        joueur.utiliserEquipementSur(arbre); // 20hp -> 14hp
        assertEquals(14, arbre.getVie());

        // Arc 10 degats, enleve 0 car c'est pas l'outil requis pour attaquer
        joueur.setEquipementActuel(arc);
        joueur.utiliserEquipementSur(arbre); // 14hp -> 14hp
        assertEquals(14, arbre.getVie());

        // Arc 10 degats, enleve 0 car la distance est trop grande
        joueur.setEquipementActuel(arc);
        joueur.utiliserEquipementSur(arbre2); // 20hp -> 20hp
        assertEquals(20, arbre2.getVie());

        // Arc 10 degats, enleve 12 car ciblePreferable est bien un ennemi
        joueur.utiliserEquipementSur(loup); // 15hp -> 3hp
        assertEquals(3, loup.getVie());
        joueur.utiliserEquipementSur(loup);
        assertEquals(0, loup.getVie());
        assertTrue(loup.estMort());
    }
}
*/