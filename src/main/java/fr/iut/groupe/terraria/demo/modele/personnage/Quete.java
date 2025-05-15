package fr.iut.groupe.terraria.demo.modele.personnage;

public class Quete {
    private String description;
    private String typeObjectif; // "tuer", "ramasser", etc.
    private String cible;        // "Loup", "Pomme", etc.
    private int objectif;
    private int progression;
    private boolean terminee;

    public Quete(String description, String typeObjectif, String cible, int objectif) {
        this.description = description;
        this.typeObjectif = typeObjectif;
        this.cible = cible;
        this.objectif = objectif;
        this.progression = 0;
        this.terminee = false;
    }

    public void progresser() {
        if (!terminee) {
            progression++;
            if (progression >= objectif) {
                terminee = true;
            }
        }
    }

    public boolean estTerminee() {
        return terminee;
    }

    public String getCible() {
        return cible;
    }

    public String getDescription() {
        return description;
    }

    public int getProgression() {
        return progression;
    }

    public int getObjectif() {
        return objectif;
    }
}


