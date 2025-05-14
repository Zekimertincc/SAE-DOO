package fr.iut.groupe.terraria.demo.modele.ressources;

public class Pierre extends Ressource {

    public Pierre() {
        super("Pierre");
    }

    @Override
    public void utiliser() {
        System.out.println("Tu utilises la pierre pour fabriquer un outil.");
    }
}
