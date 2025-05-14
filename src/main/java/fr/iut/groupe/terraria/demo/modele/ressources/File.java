package fr.iut.groupe.terraria.demo.modele.ressources;

public class File extends Ressource {

    public File() {
        super("File");
    }

    @Override
    public void utiliser() {
        System.out.println("Tu utilises la file pour fabriquer un arc ou un piège.");
    }
}
