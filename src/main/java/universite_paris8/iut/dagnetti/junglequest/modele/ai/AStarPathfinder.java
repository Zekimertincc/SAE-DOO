package universite_paris8.iut.dagnetti.junglequest.modele.ia;

import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;

import java.util.*;

/**
 * Utility class implementing a simple A* path finding algorithm on the game map.
 */
public final class AStarPathfinder {
    private AStarPathfinder() {}

    private static class Node implements Comparable<Node> {
        final int l;
        final int c;
        final double g;
        final double h;
        final Node parent;
        Node(int l, int c, double g, double h, Node parent) {
            this.l = l;
            this.c = c;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }
        double f() { return g + h; }
        @Override
        public int compareTo(Node o) { return Double.compare(this.f(), o.f()); }
    }

    public static List<int[]> calculerChemin(Carte carte, int lStart, int cStart, int lGoal, int cGoal) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Double> gScore = new HashMap<>();
        Set<String> closed = new HashSet<>();

        Node start = new Node(lStart, cStart, 0, heuristique(lStart, cStart, lGoal, cGoal), null);
        open.add(start);
        gScore.put(cle(lStart, cStart), 0.0);

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        int hauteur = carte.getHauteur();
        int largeur = carte.getLargeur();

        while (!open.isEmpty()) {
            Node courant = open.poll();
            if (courant.l == lGoal && courant.c == cGoal) {
                return reconstruire(courant);
            }
            String k = cle(courant.l, courant.c);
            if (!closed.add(k)) continue;
            for (int[] d : dirs) {
                int nl = courant.l + d[0];
                int nc = courant.c + d[1];
                if (nl < 0 || nl >= hauteur || nc < 0 || nc >= largeur) continue;
                if (carte.estSolide(nl, nc)) continue;
                double tentative = courant.g + 1;
                String nk = cle(nl, nc);
                if (tentative < gScore.getOrDefault(nk, Double.MAX_VALUE)) {
                    gScore.put(nk, tentative);
                    open.add(new Node(nl, nc, tentative, heuristique(nl, nc, lGoal, cGoal), courant));
                }
            }
        }
        return List.of();
    }

    private static double heuristique(int l, int c, int lGoal, int cGoal) {
        return Math.abs(lGoal - l) + Math.abs(cGoal - c);
    }

    private static List<int[]> reconstruire(Node node) {
        List<int[]> chemin = new ArrayList<>();
        while (node.parent != null) {
            chemin.add(0, new int[]{node.l, node.c});
            node = node.parent;
        }
        return chemin;
    }

    private static String cle(int l, int c) {
        return l + "," + c;
    }
}