module fr.iut.groupe.junglequest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    exports fr.iut.groupe.junglequest.vue.fenetre;
    exports fr.iut.groupe.junglequest.controleur.demarrage;
    exports fr.iut.groupe.junglequest.controleur;
    exports fr.iut.groupe.junglequest.vue;
    exports fr.iut.groupe.junglequest.controleur.moteur;
    exports fr.iut.groupe.junglequest.modele.personnages;
    exports fr.iut.groupe.junglequest.modele.carte;
    exports fr.iut.groupe.junglequest.modele.donnees;
    exports fr.iut.groupe.junglequest.modele.bloc;
    exports fr.iut.groupe.junglequest.vue.animation;
    exports fr.iut.groupe.junglequest.vue.utilitaire;
    opens fr.iut.groupe.junglequest.controleur.interfacefx to javafx.fxml;


}
