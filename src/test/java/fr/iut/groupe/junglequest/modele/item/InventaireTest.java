package fr.iut.groupe.junglequest.modele.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventaireTest {
    @Test
    void ajouterEtContientItem() {
        Inventaire inv = new Inventaire();
        assertTrue(inv.ajouterItem("Bois", 3));
        assertTrue(inv.contient("Bois", 3));
        assertFalse(inv.ajouterItem("Pierre", 0));
    }

    @Test
    void retirerItemFonctionne() {
        Inventaire inv = new Inventaire();
        inv.ajouterItem("Bois", 5);

        assertTrue(inv.retirerItem("Bois", 3));
        assertEquals(2, inv.getItems().get("Bois"));
        assertFalse(inv.retirerItem("Bois", 3));
        assertTrue(inv.retirerItem("Bois", 2));
        assertFalse(inv.getItems().containsKey("Bois"));
    }

    @Test
    void viderSupprimeTout() {
        Inventaire inv = new Inventaire();
        inv.ajouterItem("Bois", 1);
        inv.ajouterItem("Pierre", 2);
        inv.vider();
        assertTrue(inv.getItems().isEmpty());
    }
}
