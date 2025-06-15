package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.modele.monde.AStarPathFinding;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarPathFindingTest {
    @Test
    void testFindPathSimple() {
        int[][] map = {
                {0,0,0},
                {1,1,0},
                {0,0,0}
        };
        List<int[]> path = AStarPathFinding.findPath(map,0,0,2,2);
        assertFalse(path.isEmpty());
        int[] end = path.get(path.size()-1);
        assertEquals(2, end[0]);
        assertEquals(2, end[1]);
    }
}
