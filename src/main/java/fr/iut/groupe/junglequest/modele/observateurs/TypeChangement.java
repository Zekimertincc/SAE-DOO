package fr.iut.groupe.junglequest.modele.observateurs;

/**
 * Énumère les différents types de changements qui peuvent survenir dans le modèle.
 * Utilisé pour le Observer Pattern pour notifier la Vue des changements dans le Model.
 */
public enum TypeChangement {
    // Changements sur les entités
    POSITION,           // Changement de position
    POINTS_DE_VIE,     // Changement des points de vie
    ETAT,              // Changement d'état (marche, saut, etc.)
    DIRECTION,         // Changement de direction (gauche/droite)
    INVENTAIRE,        // Modification de l'inventaire
    ANIMATION,         // Changement d'animation
    
    // Changements sur l'environnement
    CARTE_CHARGEE,     // La carte a été chargée
    JOUEUR_CREE,       // Le joueur a été créé
    NPC_CREE,          // Un NPC a été créé
    ENNEMI_CREE,       // Un ennemi a été créé
    RESSOURCES_CHARGEES, // Les ressources ont été chargées
    RESSOURCE_RECOLTEE,  // Une ressource a été récoltée
    
    // Actions de jeu
    COMBAT,            // Un combat a lieu
    DIALOGUE           // Un dialogue est déclenché
}