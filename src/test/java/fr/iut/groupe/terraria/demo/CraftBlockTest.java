package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.ArmeCraft;
import fr.iut.groupe.terraria.demo.modele.farm.Bois;
import fr.iut.groupe.terraria.demo.modele.farm.Farm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CraftBlockTest {
    private Inventaire inventaire;
    private ArrayList<Farm> listFarm;

    private ArmeCraft armeCraft;
    private CraftBlock craftBlock;

    @BeforeEach
    public void setUp() {
        inventaire = new Inventaire();

        armeCraft = new ArmeCraft(inventaire);
        craftBlock = new CraftBlock(inventaire, "Bois");
    }

    @Test
    public void CraftBlockTest() {
        inventaire.ajouterItem(new Bois(), 4);

        assertTrue(craftBlock.craftBlock());
        assertTrue(craftBlock.craftBlock());
        assertFalse(craftBlock.craftBlock());

        inventaire.afficherMap();
    }
}