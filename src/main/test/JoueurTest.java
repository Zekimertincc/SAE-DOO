//import fr.iut.groupe.terraria.demo.*;
//import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;
//import fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Hache;
//import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
//import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
//import org.junit.jupiter.api.BeforeEach;
//import org.testng.annotations.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JoueurTest {
//    private Joueur joueur;
//    private Hache hache;
//    private Arc arc;
//    private Arbre arbre;
//    private Arbre arbre2;
//
//    @BeforeEach
//    public void setUp() {
//        Joueur joueur = new Joueur(1, 1, 5);
//        Hache hache = new Hache();
//        Arc arc = new Arc();
//        Arbre arbre = new Arbre(2, 2);
//        Arbre arbre2 = new Arbre(100, 100);
//    }
//    @Test
//    public void testUtiliserEquipementSur() {
//        joueur.setEquipementActuel(hache);
//        joueur.utiliserEquipementSur(arbre); // 20hp -> 16hp
//        assertEquals(16, arbre.getVie());
//    }
//
//}
