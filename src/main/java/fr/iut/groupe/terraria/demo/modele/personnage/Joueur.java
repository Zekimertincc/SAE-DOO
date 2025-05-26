    package fr.iut.groupe.terraria.demo.modele.personnage;

    import fr.iut.groupe.terraria.demo.modele.Inventaire;
    import fr.iut.groupe.terraria.demo.modele.item.Item;
    import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
    import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
    import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;

    public class Joueur  extends Personnage {
        private double x, y;
        private double vitesseY = 0;
        private final double vitesseX = 2;
        private boolean enLair = false;
        private final int tileSize = 32;
        private final int[][] map;
        private Equipement equipementActuel;
        private Inventaire inventaire;
        private EtatTemporaire etatTemporaire;




        public Joueur(double x, double y, int vieMax, EtatTemporaire etatTemporaire, int[][] map ) {
            super(x, y, vieMax, vieMax, 0);
            this.equipementActuel = new Couteau();
            this.inventaire = new Inventaire();
            this.etatTemporaire = etatTemporaire;
            this.map = map;
        }


        public void gauche() {
            double nextX = x - vitesseX;
            if (!collisionHorizontale(nextX)) {
                x = Math.max(0, nextX);
            }
        }

        public void droite() {
            double nextX = x + vitesseX;
            int maxX = map[0].length * tileSize - getLargeur();
            if (!collisionHorizontale(nextX)) {
                x = Math.min(maxX, nextX);
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
            double nextY = y + vitesseY;

            int leftTile  = (int) x / tileSize;
            int rightTile = (int) (x + getLargeur() - 1) / tileSize;
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
                y = footTile * tileSize - getHauteur();
            } else {
                y = nextY;
                enLair = true;
            }
        }

        private boolean collisionHorizontale(double nextX) {
            int topTile    = (int) y / tileSize;
            int bottomTile = (int) (y + getHauteur() - 1) / tileSize;
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
                System.out.println("📦 " + item.getNom() + " ajouté à l'inventaire");
            } else {
                System.out.println("❌ Inventaire plein pour: " + item.getNom());
            }

            inventaire.afficherMap(); // en bas yazdır tüm envanter

        }


        public Inventaire getInventaire() {
            return inventaire;
        }



        public double getX()       { return x; }
        public double getY()       { return y; }
        public int    getLargeur() { return 64; }
        public int    getHauteur() { return 96; }


       // --------------------------------------------------------------------------------------------------------------


        public boolean changerNullEquipement() {
            boolean changer = false;
            if (this.equipementActuel.estCasse()){
                setEquipementActuel(null);
                changer = true;
            }
            return changer;
        }

        @Override
        public void subirDegats(int degats) {
            if (!this.getEtatTemporaire().isInvincible() && this.getEtatTemporaire().isVulnerable()) {
                degats *= 2;
            }
            super.subirDegats(degats);
        }


        public Equipement getEquipementActuel() {
            return equipementActuel;
        }

        public void setEquipementActuel(Equipement equipement) {
            this.equipementActuel = equipement;
        }
        /*
        public void setVitesseX(double vitesseH) {
            this.vitesseH = vitesseH;
        }
    */
        public double getVitesseY() {
            return vitesseY;
        }


        public EtatTemporaire getEtatTemporaire() {
            return etatTemporaire;
        }

        public void setEtatTemporaire(EtatTemporaire etatTemporaire) {
            this.etatTemporaire = etatTemporaire;
        }
    }
