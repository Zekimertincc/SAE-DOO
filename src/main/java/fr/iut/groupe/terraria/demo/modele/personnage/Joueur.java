    package fr.iut.groupe.terraria.demo.modele.personnage;

    import fr.iut.groupe.terraria.demo.modele.Ciblable;
    import fr.iut.groupe.terraria.demo.modele.Inventaire;
    import fr.iut.groupe.terraria.demo.modele.item.BlockPlace;
    import fr.iut.groupe.terraria.demo.modele.item.Item;
    import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
    import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
    import fr.iut.groupe.terraria.demo.modele.monde.Maths;
    import fr.iut.groupe.terraria.demo.modele.monde.Monde;
    import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;

    public class Joueur  extends Personnage {
        private boolean enLair = false;
        private final int tileSize = 32;
        private final int[][] map;

        private Equipement equipementActuel;
        private Inventaire inventaire;
        private EtatTemporaire etatTemporaire;

        public Joueur(double x, double y, int vieMax, EtatTemporaire etatTemporaire, int[][] map ) {
            super(x, y, 0.5, 0, vieMax, vieMax, 0);
            this.equipementActuel = new Couteau();
            this.inventaire = new Inventaire();
            this.etatTemporaire = etatTemporaire;
            this.map = map;

            // ajout de couteau dans linventaire
            this.inventaire.ajouterItem(equipementActuel, 1);
            this.inventaire.setItemActif(equipementActuel);
        }


        public void gauche() {
            double nextX = getX() - vitesseX;
            if (!collisionHorizontale(nextX)) {
                setX(Math.max(0, nextX));
            }
        }
        public void droite() {
            double nextX = getX() + vitesseX;
            int maxX = map[0].length * tileSize - getLargeur();
            if (!collisionHorizontale(nextX)) {
                setX(Math.min(maxX, nextX));
            }
        }

        public void sauter() {
            if (!enLair) {
                vitesseY = -10;
                enLair = true;
            }
        }

        public void appliquerGravite() {
            vitesseY += 0.5;
            double nextY = getY() + vitesseY;

            int leftTile  = (int) getX() / tileSize;
            int rightTile = (int) (getX() + getLargeur() - 1) / tileSize;
            int footTile  = (int) (nextY + getHauteur()) / tileSize;

            boolean collisionSol = false;
            for (int tx = leftTile; tx <= rightTile; tx++) {
                if (footTile < 0 || footTile >= map.length || tx < 0 || tx >= map[0].length) continue;
                if (map[footTile][tx] == 1) {
                    collisionSol = true;
                    break;
                }
            }

            if (collisionSol) {
                vitesseY = 0;
                enLair = false;
                setY(footTile * tileSize - getHauteur());
            } else {
                setY(nextY);
                enLair = true;
            }
        }

        private boolean collisionHorizontale(double nextX) {
            int topTile    = (int) getY() / tileSize;
            int bottomTile = (int) (getY() + getHauteur() - 1) / tileSize;
            int leftTile   = (int) nextX / tileSize;
            int rightTile  = (int) (nextX + getLargeur() - 1) / tileSize;

            for (int ty = topTile; ty <= bottomTile; ty++) {
                if (leftTile  >= 0 && leftTile  < map[0].length && ty >= 0 && ty < map.length) {
                    if (map[ty][leftTile] == 1) return true;
                }
                if (rightTile >= 0 && rightTile < map[0].length && ty >= 0 && ty < map.length) {
                    if (map[ty][rightTile] == 1) return true;
                }
            }
            return false;
        }


        public void casserRessource(Ressource r) {
            System.out.println("Tu as cassé: " + r.getNom());
            Item item = r.getItemProduit();

            boolean ok = inventaire.ajouterItem(item);
            if (ok) {
                System.out.println(item.getNom() + " ajouté à l'inventaire");
            } else {
                System.out.println(" Inventaire plein pour: " + item.getNom());
            }

            inventaire.afficherMap(); // en bas yazdır tüm envanter

        }

       // --------------------------------------------------------------------------------------------------------------

       // mettre des degats sur les ennemis/ressources selon l'equipement actuel il y a une portée et des bonus
       public void utiliserEquipementSur(Ciblable cible) {
           if (equipementActuel != null){
               equipementActuel.degatsContre(this, cible);
           }
       }

        public void changerNullEquipement() {
            if (this.equipementActuel.estCasse()){
                setEquipementActuel(null);
            }
        }

        @Override
        public void subirDegats(int degats) {
            if (!this.getEtatTemporaire().isInvincible() && this.getEtatTemporaire().isVulnerable()) {
                degats *= 2;
            }
            super.subirDegats(degats);
        }
        // mettre le joueur à 1hp (class aigle)
        public void mettreAPV(int nouvelleVie) {
            vie.set(Math.max(0, Math.min(nouvelleVie, this.vieMax)));
        }


        public boolean construireEtPlacerBloc(String type, double x, double y, Monde monde) {
            boolean peutPlacer = true;
            if (inventaire.getMapItems().getOrDefault(type, 0) < 2) {
                peutPlacer = false;
            }
            for (BlockPlace b : monde.getListBlock()) {
                if (Maths.distance(b.getX(), b.getY(), x, y) < 32) {
                    peutPlacer = false;
                }
            }
            if (peutPlacer) {
                inventaire.retirerItem(type, 2);
                monde.ajouterBlock(new BlockPlace(type, x, y));
            }
            return peutPlacer;
        }

// --------------------------------------------------------------------------------------------------------------------------

        public Equipement getEquipementActuel() {
            return equipementActuel;
        }
        public void setEquipementActuel(Equipement equipement) {
            this.equipementActuel = equipement;
        }
        public double getVitesseY() {
            return vitesseY;
        }
        public EtatTemporaire getEtatTemporaire() {
            return etatTemporaire;
        }
        public void setEtatTemporaire(EtatTemporaire etatTemporaire) {
            this.etatTemporaire = etatTemporaire;
        }
        public Inventaire getInventaire() {
            return inventaire;
        }
        public int    getLargeur() { return 64; }
        public int    getHauteur() { return 96; }
    }
