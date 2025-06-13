
package universite_paris8.iut.dagnetti.junglequest.vue.utilitaire;

import java.util.*;

public class Pathfinder {

    public static class Node implements Comparable<Node> {
        public int x, y, g, h;
        public Node parent;

        public Node(int x, int y, int g, int h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        public int f() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f(), other.f());
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) return false;
            Node other = (Node) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan distance
    }

    public static List<int[]> aStar(int[][] grid, int[] start, int[] goal) {
        int width = grid[0].length;
        int height = grid.length;

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        Node startNode = new Node(start[0], start[1], 0, heuristic(start[0], start[1], goal[0], goal[1]), null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (current.x == goal[0] && current.y == goal[1]) {
                List<int[]> path = new ArrayList<>();
                while (current != null) {
                    path.add(0, new int[]{current.x, current.y});
                    current = current.parent;
                }
                return path;
            }

            closedSet.add(current.x + "," + current.y);

            for (int[] dir : new int[][]{{-1,0},{1,0},{0,-1},{0,1}}) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (nx < 0 || ny < 0 || nx >= width || ny >= height) continue;
                if (grid[ny][nx] == -1) continue;

                Node neighbor = new Node(nx, ny, current.g + 1, heuristic(nx, ny, goal[0], goal[1]), current);
                String key = nx + "," + ny;
                if (!closedSet.contains(key)) {
                    openList.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // No path found
    }
}
