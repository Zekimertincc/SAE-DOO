module fr.iut.groupe.terraria.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    exports fr.iut.groupe.terraria.demo.vue.fenetre;
    exports fr.iut.groupe.terraria.demo.controller.demarrage;
    exports fr.iut.groupe.terraria.demo.controller;
    exports fr.iut.groupe.terraria.demo.vue;
    exports fr.iut.groupe.terraria.demo.controller.moteur;
    exports fr.iut.groupe.terraria.demo.modele.personnage;
    exports fr.iut.groupe.terraria.demo.modele.carte;
    exports fr.iut.groupe.terraria.demo.modele.donnees;
    exports fr.iut.groupe.terraria.demo.vue.annimation;
    exports fr.iut.groupe.terraria.demo.vue.utilitaire;
    opens fr.iut.groupe.terraria.demo.controller.interfacefx to javafx.fxml;


}
