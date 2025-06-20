package fr.iut.groupe.junglequest.modele.bloc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTypeTest {
    @Test
    void fromIdRetourneType() {
        assertEquals(TileType.HERBE, TileType.fromId(1));
        assertEquals(TileType.VIDE, TileType.fromId(-1));
        assertNull(TileType.fromId(999));
    }

    @Test
    void contientIdFonctionne() {
        assertTrue(TileType.ARBRE.contientId(160));
        assertFalse(TileType.TERRE.contientId(1));
    }
}
