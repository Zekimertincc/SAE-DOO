package fr.iut.groupe.terraria.demo;

import universite_paris8.iut.dagnetti.junglequest.modele.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.craft.ArmeCraft;
import universite_paris8.iut.dagnetti.junglequest.modele.craft.CraftBlock;
import universite_paris8.iut.dagnetti.junglequest.modele.farm.Bois;
import universite_paris8.iut.dagnetti.junglequest.modele.farm.Farm;
import universite_paris8.iut.dagnetti.junglequest.modele.farm.File;
import universite_paris8.iut.dagnetti.junglequest.modele.farm.Pierre;
import universite_paris8.iut.dagnetti.junglequest.modele.item.equipement.outil.Hache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArmeCraftTest {
    private Inventaire inventaire;
    private ArrayList<Farm> listFarm;

    private ArmeCraft armeCraft;
    private CraftBlock craftBlock;

    @BeforeEach
    public void setUp() {
        inventaire = new Inventaire();
        listFarm = new ArrayList<>();

        armeCraft = new ArmeCraft(inventaire);
        craftBlock = new CraftBlock(inventaire, "Bois");
    }
    @Test
    public void ArmeCraftTest() {
        assertFalse(armeCraft.construire(new Hache()));
        inventaire.ajouterItem(new Bois(), 10 );
        inventaire.ajouterItem(new Pierre(), 10);
        inventaire.ajouterItem(new File(), 10);
        assertTrue(armeCraft.construire(new Hache()));

        inventaire.afficherMap();
    }

}
