# Architecture MVC - Jungle Quest

## Vue d'ensemble

Ce document explique l'architecture MVC (Model-View-Controller) mise en place dans le projet Jungle Quest, conformément aux principes enseignés et aux règles d'or du développement JavaFX.

---

## RÈGLES D'OR MVC

### Principe de base

- **Le contrôleur connaît la vue et le model**
- **Le model ne connaît pas la vue**
- **La vue connaît le model**

### Règle cruciale

**VOUS NE DEVEZ PAS charger d'image ou autres éléments visuels dans le dossier model !**

C'est une erreur d'architecture qui se corrige avec les différents bindings (listener, observable).

---

## FLUX D'INITIALISATION DU JEU

### Phase de chargement

#### 1. Le contrôleur créé l'instance `envVue` et `envModele`

```
LanceurJeu (Application) 
    ↓
    Charge VueJeu.fxml avec VueJeuController
    ↓
VueJeuController.initializationComplete()
    ↓
    Crée Environnement via MondeFactory (Model)
    Crée les vues (View)
```

#### 2. Le `envModele` crée l'instance terrain

Le modèle (`Environnement`) charge:
- Les **données** de la map (grille CSV)
- Crée le **joueur** avec position
- Crée les **entités** (NPCs, ennemis, ressources)

**IMPORTANT**: Le modèle NE charge PAS les images !

#### 3. Le `envVue` charge les images via le `envModele`

La vue (`VueJeuController`):
- Récupère les entités du modèle via **binding** ou **observable**
- Charge les images depuis les ressources
- Crée les `ImageView` pour chaque entité
- Ajoute les sprites au terrain

### Phase d'action

Le contrôleur récupère l'action → donne à l'environnement → l'environnement effectue l'action (casse, tape, etc.)

---

## STRUCTURE DU PROJET

### Model (Modèle)

**Package**: `fr.iut.groupe.junglequest.modele`

#### Environnement.java (Singleton simplifié)

**Responsabilités**:
- Gestion centralisée du monde de jeu
- Création du terrain et des entités
- Exécution des actions de jeu
- **Notification des observateurs** (Observer Pattern)

**Corrections apportées**:
- ✅ Suppression des blocs `synchronized` inutiles (jeu mono-thread)
- ✅ Ajout de logique métier (création terrain, entités)
- ✅ Implémentation de `SujetObserve` pour l'Observer Pattern
- ✅ Méthode `initialiserMonde()` qui crée tout le monde
- ✅ Méthode `executerAction()` pour les actions de jeu

#### RessourceModele.java (NOUVEAU)

**Remplace**: `Ressource.java` (qui contenait `ImageView`)

**Responsabilités**:
- Données de la ressource (position, type, récompense)
- Logique métier (peut-on récolter? avec quel outil?)
- **NE contient PAS** d'éléments visuels

```java
public class RessourceModele {
    private final String nom;
    private final String itemRecompense;
    private final double x, y;
    // PAS de ImageView !
}
```

#### Guide.java et Forgeron.java (Refactorés)

**Correction**: Suppression de `ImageView` du modèle

**Avant** (INCORRECT):
```java
public class Guide {
    private final ImageView sprite; // ❌ Interdit dans le Model!
}
```

**Après** (CORRECT):
```java
public class Guide extends Personnage {
    // ✅ Pas d'éléments visuels
    public String getMessageDialogue() { ... }
}
```

#### MondeFactory.java (Factory Pattern corrigé)

**Problème précédent**: Appelait `getInstance()` directement

**Solution**:
```java
public static Environnement creerMonde() throws IOException {
    Environnement env = Environnement.getInstance();
    env.initialiserMonde(cheminCarte); // Délègue l'initialisation
    return env;
}
```

La factory **encapsule** la création sans faire le travail elle-même.

---

### View (Vue)

**Package**: `fr.iut.groupe.junglequest.vue`

#### VueRessource.java (NOUVEAU)

Gère l'affichage visuel des ressources:
- Charge les images
- Crée les `ImageView`
- Synchronise avec `RessourceModele`
- Binding sur la position de la caméra

```java
public class VueRessource {
    private final RessourceModele modele; // ✅ Connaît le Model
    private final ImageView sprite;      // ✅ Éléments visuels dans la Vue
}
```

#### VueJeu.fxml

**Correction**: Ajout du contrôleur FXML

```xml
<Pane fx:id="racine" 
      fx:controller="fr.iut.groupe.junglequest.controleur.VueJeuController"
      ...>
```

---

### Controller (Contrôleur)

**Package**: `fr.iut.groupe.junglequest.controleur`

#### Main.java (NOUVEAU)

Point d'entrée propre:
```java
public class Main {
    public static void main(String[] args) {
        Application.launch(LanceurJeu.class, args);
    }
}
```

#### LanceurJeu.java (Simplifié)

**Avant**: 400+ lignes avec toute la logique

**Après**: ~130 lignes, délègue au contrôleur

```java
public void start(Stage stage) {
    // 1. Charge FXML avec contrôleur
    FXMLLoader loader = new FXMLLoader(...);
    Pane racine = loader.load();
    VueJeuController controller = loader.getController();
    
    // 2. Délègue l'initialisation
    controller.initializationComplete(largeur, hauteur);
    
    // 3. Affiche
    stage.setScene(new Scene(racine, largeur, hauteur));
    stage.show();
}
```

#### VueJeuController.java (NOUVEAU - avec @FXML)

**Architecture**:
- Utilise `@FXML` pour l'injection automatique
- Implémente `Observateur` pour observer le Model
- Crée le Model via `MondeFactory`
- Crée la Vue en chargeant les images

**Flux d'initialisation**:
```java
public void initializationComplete(double largeur, double hauteur) {
    // 1. Créer EnvModele
    this.environnement = MondeFactory.creerMonde();
    environnement.ajouterObservateur(this); // Observer Pattern
    
    // 2. Créer EnvVue
    creerVue();
    
    // 3. Créer le contrôleur de jeu
    creerControleurJeu();
    
    // 4. Démarrer la boucle
    demarrerBoucleJeu();
}
```

**Méthode Observer**:
```java
@Override
public void mettreAJour(SujetObserve sujet, TypeChangement type) {
    switch (type) {
        case RESSOURCE_RECOLTEE -> {
            // Mettre à jour l'affichage
        }
        case COMBAT -> {
            // Effets visuels de combat
        }
    }
}
```

---

## DESIGN PATTERNS UTILISÉS

### 1. Singleton Pattern (Environnement)

**Simplifié** - sans synchronisation car mono-thread:

```java
public class Environnement {
    private static Environnement instance;
    
    public static Environnement getInstance() {
        if (instance == null) {
            instance = new Environnement();
        }
        return instance;
    }
}
```

### 2. Factory Pattern (MondeFactory, PersonnageFactory)

Encapsule la création d'objets:

```java
public interface PersonnageFactory {
    Joueur createJoueur(double x, double y);
    Guide createGuide(double x, double y);
    // ...
}
```

### 3. Observer Pattern

**Model → View communication sans couplage**:

```java
// Model
public interface SujetObserve {
    void ajouterObservateur(Observateur obs);
    void notifierObservateurs(TypeChangement type);
}

// View
public interface Observateur {
    void mettreAJour(SujetObserve sujet, TypeChangement type);
}
```

### 4. State Pattern (États du joueur)

```java
public interface EtatJoueur {
    void entrer(Joueur joueur);
    void mettreAJour(Joueur joueur);
    void sortir(Joueur joueur);
}
```

### 5. Strategy Pattern (Comportement du loup)

```java
public interface ComportementLoup {
    void executerComportement(Loup loup, Joueur joueur, Carte carte);
}
```

### 6. Command Pattern (Actions utilisateur)

```java
public interface Commande {
    void executer();
}
```

---

## UTILISATION DES BINDINGS

### Property Binding (JavaFX)

**Exemple**: Barre de vie liée aux points de vie

```java
// Dans VueJeuController
barreVieJoueur.ratioProperty().bind(
    environnement.getJoueur().pointsDeVieProperty()
        .divide((double) ConstantesJeu.VIE_MAX_JOUEUR)
);
```

**Avantage**: Mise à jour automatique quand le modèle change !

### Position Binding (Scrolling caméra)

```java
// Dans VueRessource
sprite.xProperty().bind(
    offsetXProperty.negate().add(modele.getX())
);
```

---

## MODULE JAVA (module-info.java)

**Correction**: Ajout des `opens` pour FXML:

```java
module fr.iut.groupe.junglequest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    
    // Exports
    exports fr.iut.groupe.junglequest;
    exports fr.iut.groupe.junglequest.modele;
    exports fr.iut.groupe.junglequest.vue;
    // ...
    
    // Opens pour FXML (injection @FXML)
    opens fr.iut.groupe.junglequest.controleur to javafx.fxml;
    opens fr.iut.groupe.junglequest.controleur.interfacefx to javafx.fxml;
}
```

---

## FICHIERS PRINCIPAUX MODIFIÉS/CRÉÉS

### Créés

- ✅ `Main.java` - Point d'entrée propre
- ✅ `VueJeuController.java` - Contrôleur FXML principal
- ✅ `RessourceModele.java` - Modèle de ressource sans ImageView
- ✅ `VueRessource.java` - Vue de ressource avec ImageView

### Refactorés

- ✅ `Environnement.java` - Singleton simplifié avec logique
- ✅ `MondeFactory.java` - Factory Pattern correct
- ✅ `LanceurJeu.java` - Simplifié, délègue au contrôleur
- ✅ `Guide.java` - Suppression ImageView
- ✅ `Forgeron.java` - Suppression ImageView
- ✅ `PersonnageFactory.java` - Signature sans ImageView
- ✅ `TypeChangement.java` - Ajout événements environnement
- ✅ `VueJeu.fxml` - Ajout contrôleur
- ✅ `module-info.java` - Opens pour FXML

---

## AVANTAGES DE CETTE ARCHITECTURE

### 1. Séparation des responsabilités

- **Model**: Logique métier pure, pas d'UI
- **View**: Affichage pur, pas de logique
- **Controller**: Coordination, pas de logique métier

### 2. Testabilité

Le modèle peut être testé **sans** JavaFX car il n'en dépend plus !

### 3. Maintenabilité

Changer l'UI ne touche pas le modèle. Changer la logique métier ne touche pas l'UI.

### 4. Réutilisabilité

Le modèle peut être réutilisé dans un autre contexte (serveur, autre UI).

### 5. Conformité JavaFX

Utilisation correcte de FXML avec @FXML et contrôleurs.

---

## POINTS D'ATTENTION RESTANTS

### ControleurJeu.java

**TODO**: Ce contrôleur a encore un constructeur massif avec trop de paramètres. Il devrait être refactoré pour utiliser l'injection FXML comme VueJeuController.

### Loup.java

**Note**: Le Loup stocke encore des `Image` (pas `ImageView`). Ceci devrait idéalement être déplacé dans la Vue dans une future version.

---

## COMPILATION ET EXÉCUTION

### Avec Maven

```bash
# Compilation
mvn clean compile

# Exécution
mvn javafx:run
```

### Point d'entrée

**Main class**: `fr.iut.groupe.junglequest.Main`

---

## CONCLUSION

L'architecture a été profondément refactorée pour respecter les principes MVC enseignés:

1. ✅ **Pas d'ImageView dans le Model**
2. ✅ **Utilisation de @FXML et contrôleurs**
3. ✅ **Observer Pattern pour Model → View**
4. ✅ **Factory Pattern correct**
5. ✅ **Singleton simplifié (pas de synchronized inutile)**
6. ✅ **Environnement contient de la logique**
7. ✅ **Séparation claire Model/View/Controller**

Le code est maintenant **maintenable**, **testable** et **conforme** aux bonnes pratiques JavaFX et MVC.

