module fr.iut.groupe.terraria.demo {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.fxml;

    exports fr.iut.groupe.terraria.demo.vue;
    exports fr.iut.groupe.terraria.demo.vue.animation;
    exports fr.iut.groupe.terraria.demo.vue.fenetre;
    exports fr.iut.groupe.terraria.demo.vue.utilitaire;

    exports fr.iut.groupe.terraria.demo.controleur;
    exports fr.iut.groupe.terraria.demo.controleur.demarrage;
    exports fr.iut.groupe.terraria.demo.controleur.moteur;
    exports fr.iut.groupe.terraria.demo.controleur.interfacefx;

    exports fr.iut.groupe.terraria.demo.modele;
    exports fr.iut.groupe.terraria.demo.modele.bloc;
    exports fr.iut.groupe.terraria.demo.modele.carte;
    exports fr.iut.groupe.terraria.demo.modele.craft;
    exports fr.iut.groupe.terraria.demo.modele.donnees;
    exports fr.iut.groupe.terraria.demo.modele.farm;
    exports fr.iut.groupe.terraria.demo.modele.item;
    exports fr.iut.groupe.terraria.demo.modele.item.equipement;
    exports fr.iut.groupe.terraria.demo.modele.item.equipement.arme;
    exports fr.iut.groupe.terraria.demo.modele.item.equipement.outil;
    exports fr.iut.groupe.terraria.demo.modele.item.nourriture;
    exports fr.iut.groupe.terraria.demo.modele.monde;
    exports fr.iut.groupe.terraria.demo.modele.personnage;
    exports fr.iut.groupe.terraria.demo.modele.personnage.ennemi;
    exports fr.iut.groupe.terraria.demo.modele.personnages;
    exports fr.iut.groupe.terraria.demo.modele.ressource;
    exports fr.iut.groupe.terraria.demo.modele.zone;

    opens fr.iut.groupe.terraria.demo.controleur.interfacefx to javafx.fxml;
}
