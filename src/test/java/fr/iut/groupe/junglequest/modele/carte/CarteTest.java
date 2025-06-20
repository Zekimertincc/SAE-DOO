package fr.iut.groupe.junglequest.modele.carte;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTest {
    @Test
    void getValeurTuileOutOfBounds() {
        int[][] grid = {{1}};
        Carte carte = new Carte(grid);
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(-1, 0));
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(0, -1));
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(1, 0));
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(0, 1));
    }

    @Test
    void setValeurTuileModifieSiValide() {
        int[][] grid = {{0,0},{0,0}};
        Carte carte = new Carte(grid);
        carte.setValeurTuile(1,1,5);
        assertEquals(5, carte.getValeurTuile(1,1));
        carte.setValeurTuile(2,0,9);
        assertEquals(Carte.TUILE_VIDE, carte.getValeurTuile(2,0));
    }

    @Test
    void estSolidePrendEnCompteListeNonSolide() {
        int[][] grid = {{0,29}};
        Carte carte = new Carte(grid);
        assertFalse(carte.estSolide(0,0));
        assertTrue(carte.estSolide(0,1));
    }

    @Test
    void chercherLigneSolRenvoieBonneLigne() {
        int[][] grid = {
            {-1,-1},
            {1,0},
            {29,29}
        };
        Carte carte = new Carte(grid);
        assertEquals(2, carte.chercherLigneSol(0));
        assertEquals(2, carte.chercherLigneSol(1));
    }
}
