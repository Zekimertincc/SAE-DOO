package fr.iut.groupe.junglequest.modele.monde;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathsTest {
    @Test
    void distanceSimple() {
        assertEquals(5.0, Maths.distance(0,0,3,4), 0.0001);
    }
}
