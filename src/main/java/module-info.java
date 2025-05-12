module fr.iut.groupe.terraria.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens fr.iut.groupe.terraria.demo to javafx.fxml;
    exports fr.iut.groupe.terraria.demo;
    exports fr.iut.groupe.terraria.demo.controller;
    opens fr.iut.groupe.terraria.demo.controller to javafx.fxml;
}