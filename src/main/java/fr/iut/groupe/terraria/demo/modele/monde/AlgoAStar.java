package fr.iut.groupe.terraria.demo.modele.monde;

import java.util.*;

/** Simple static A* path-finding utility. */
public class AlgoAStar {
    private static class Node implements Comparable<Node> {
        int row, col;
        double g, h;
        Node parent;
        Node(int row, int col, double g, double h, Node parent) {
            this.row = row; this.col = col; this.g = g; this.h = h; this.parent = parent;
        }
        double f() { return g + h; }
        @Override public int compareTo(Node other) { return Double.compare(this.f(), other.f()); }
    }

    private static boolean isWalkable(int[][] map, int r, int c) {
        return r >= 0 && c >= 0 && r < map.length && c < map[0].length && map[r][c] == 0;
    }

    public static java.util.List<int[]> findPath(int[][] map, int startRow, int startCol, int endRow, int endCol) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Node> visited = new HashMap<>();
        Node start = new Node(startRow, startCol, 0, heuristic(startRow, startCol, endRow, endCol), null);
        open.add(start);

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.row == endRow && current.col == endCol) {
                return reconstructPath(current);
            }
            visited.put(current.row + "," + current.col, current);
            for (int[] d : new int[][]{{1,0},{-1,0},{0,1},{0,-1}}) {
                int nr = current.row + d[0];
                int nc = current.col + d[1];
                if (!isWalkable(map, nr, nc) || visited.containsKey(nr + "," + nc)) continue;
                double ng = current.g + 1;
                Node next = new Node(nr, nc, ng, heuristic(nr, nc, endRow, endCol), current);
                open.add(next);
            }
        }
        return java.util.Collections.emptyList();
    }

    private static double heuristic(int r, int c, int goalR, int goalC) {
        // Manhattan distance
        return Math.abs(r - goalR) + Math.abs(c - goalC);
    }

    private static java.util.List<int[]> reconstructPath(Node node) {
        LinkedList<int[]> path = new LinkedList<>();
        Node cur = node;
        while (cur != null) {
            path.addFirst(new int[]{cur.row, cur.col});
            cur = cur.parent;
        }
        return path;
    }
}
