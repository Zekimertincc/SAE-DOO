# SAE-DOO

This project is a small JavaFX game developed as part of an academic project.
The source code is organised following the **Model–View–Controller (MVC)** pattern.

- `fr.iut.groupe.terraria.demo.modele` contains all the game logic and data.
- `fr.iut.groupe.terraria.demo.vue` provides the FXML views and visual helpers.
- `fr.iut.groupe.terraria.demo.controleur` holds controllers that link the model
  and the views.

The menu screen (`MenuPrincipal`) has been refactored to load an FXML view with a
dedicated controller (`MenuPrincipalController`) so that the launcher also
respects the MVC organisation.

Use Maven to run the tests:

```bash
mvn test
```

Running the JavaFX application requires a Java 17+ environment.
