module fr.iut.groupe.junglequest {
    // Dépendances JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    
    // Exports pour accès externe
    exports fr.iut.groupe.junglequest;
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
    exports fr.iut.groupe.junglequest.vue.personnages;
    exports fr.iut.groupe.junglequest.modele.observateurs;
    exports fr.iut.groupe.junglequest.modele;
    exports fr.iut.groupe.junglequest.modele.item.farm;
    
    // Opens pour FXML (injection via @FXML)
    opens fr.iut.groupe.junglequest.controleur to javafx.fxml;
    opens fr.iut.groupe.junglequest.controleur.interfacefx to javafx.fxml;
    opens fr.iut.groupe.junglequest.controleur.demarrage to javafx.fxml;
}
