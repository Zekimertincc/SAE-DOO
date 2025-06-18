package fr.iut.groupe.terraria.demo;


import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Hache;
import fr.iut.groupe.terraria.demo.modele.item.nourriture.Pomme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventaireTest {
    private Inventaire inventaire;
    private Pomme pomme;
    private Pomme pomme2;
    private Hache hache;
    private Hache hache2;

    @BeforeEach
    public void setUp() {
        inventaire = new Inventaire();
        pomme = new Pomme();
        pomme2 = new Pomme();
        hache = new Hache();
    }
    @Test
    public void testClassInventaire() {
        assertTrue(inventaire.ajouterItem(pomme));
        assertTrue(inventaire.mettreAJourInventaire());

        assertTrue(inventaire.ajouterItem(hache));
        assertTrue(inventaire.mettreAJourInventaire());
        assertFalse(inventaire.mettreAJourInventaire());

        assertFalse(inventaire.ajouterItem(hache));

        assertTrue(inventaire.retirerItem("Pomme"));
        assertFalse(inventaire.retirerItem("Pomme"));

    }

}
