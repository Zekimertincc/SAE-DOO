package fr.iut.groupe.junglequest.modele.monde;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AStarTest {
    @Test
    void chercherCheminSimple() {
        int[][] grid = {
                {0,0,0},
                {0,29,0},
                {0,0,0}
        };
        Carte carte = new Carte(grid);
        List<int[]> path = AStar.chercherChemin(carte,0,0,2,2);
        assertFalse(path.isEmpty());
        assertArrayEquals(new int[]{0,0}, path.get(0));
        assertArrayEquals(new int[]{2,2}, path.get(path.size()-1));
    }

    @Test
    void chercherCheminBloqueRenvoieVide() {
        int[][] grid = {
                {0,29,0},
                {29,29,29},
                {0,29,0}
        };
        Carte carte = new Carte(grid);
        List<int[]> path = AStar.chercherChemin(carte,0,0,2,2);
        assertTrue(path.isEmpty());
    }
}
