package fr.iut.groupe.junglequest.modele.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockPlaceTest {
    @Test
    void subirDegatsEtDestruction() {
        BlockPlace bp = new BlockPlace("Bois", 1, 1);
        bp.subirDegats(3);
        assertEquals(7, bp.getVie());
        assertFalse(bp.estDetruit());
        bp.subirDegats(10);
        assertEquals(0, bp.getVie());
        assertTrue(bp.estDetruit());
    }
}
