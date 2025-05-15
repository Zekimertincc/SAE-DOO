package fr.iut.groupe.terraria.demo.modele.personnage;

public class PNJ extends Personnage {
    private String message;

    public PNJ(double x, double y, String message) {
        super(x, y, 10000, 10000, 0);
        this.message = message;
    }

    public String parler() {
        return message;
    }

    public void setMessage(String nouveauMessage) {
        this.message = nouveauMessage;
    }

}
