package fr.iut.groupe.junglequest.modele.monde;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import java.util.*;

public class AStar {

    private static class Node {
        final int x, y; // coordinates
        double g, h;
        Node parent;
        Node(int x, int y, double g, double h, Node parent) {
            this.x = x; this.y = y; this.g = g; this.h = h; this.parent = parent;
        }
        double f() { return g + h; }
    }

    private static double heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static List<int[]> chercherChemin(Carte carte, int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(Node::f));
        Map<String, Node> nodes = new HashMap<>();
        Set<String> closed = new HashSet<>();
        Node start = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY), null);
        open.add(start);
        nodes.put(startX + "," + startY, start);
        int[][] dirs = { {1,0},{-1,0},{0,1},{0,-1} };

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.x == goalX && current.y == goalY) {
                LinkedList<int[]> path = new LinkedList<>();
                Node n = current;
                while (n != null) {
                    path.addFirst(new int[]{n.x, n.y});
                    n = n.parent;
                }
                return path;
            }
            closed.add(current.x + "," + current.y);
            for (int[] d : dirs) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];
                if (carte.estSolide(ny, nx)) continue;
                String key = nx + "," + ny;
                if (closed.contains(key)) continue;
                double g = current.g + 1;
                Node node = nodes.get(key);
                if (node == null || g < node.g) {
                    node = new Node(nx, ny, g, heuristic(nx, ny, goalX, goalY), current);
                    nodes.put(key, node);
                    open.add(node);
                }
            }
        }
        return Collections.emptyList();
    }
}
