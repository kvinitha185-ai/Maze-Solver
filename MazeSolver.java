import java.util.*;

public class MazeSolver {

    // 0 = open path
    // 1 = wall

    static int[][] maze = {
        {0, 1, 0, 0, 0, 0},
        {0, 1, 0, 1, 1, 0},
        {0, 0, 0, 1, 0, 0},
        {1, 1, 0, 1, 0, 1},
        {0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 0}
    };

    static int rows = maze.length;
    static int cols = maze[0].length;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    // Node represents one position in the maze
    static class Node {
        int row;
        int col;
        Node parent;

        Node(int row, int col, Node parent) {
            this.row = row;
            this.col = col;
            this.parent = parent;
        }
    }

    public static List<Node> solveMaze() {

        Queue<Node> queue = new LinkedList<>();

        boolean[][] visited = new boolean[rows][cols];

        Node start = new Node(0, 0, null);

        queue.add(start);
        visited[0][0] = true;

        Node destination = null;

        // Breadth First Search
        while (!queue.isEmpty()) {

            Node current = queue.remove();

            // Destination
            if (current.row == rows - 1 &&
                current.col == cols - 1) {

                destination = current;
                break;
            }

            // Check four directions
            for (int i = 0; i < 4; i++) {

                int newRow = current.row + dr[i];
                int newCol = current.col + dc[i];

                // Check boundaries
                if (newRow < 0 || newRow >= rows ||
                    newCol < 0 || newCol >= cols) {
                    continue;
                }

                // Check wall
                if (maze[newRow][newCol] == 1) {
                    continue;
                }

                // Check visited
                if (visited[newRow][newCol]) {
                    continue;
                }

                visited[newRow][newCol] = true;

                Node next =
                    new Node(newRow, newCol, current);

                queue.add(next);
            }
        }

        // No path
        if (destination == null) {
            return new ArrayList<>();
        }

        // Store path using Stack
        Stack<Node> stack = new Stack<>();

        Node current = destination;

        while (current != null) {
            stack.push(current);
            current = current.parent;
        }

        List<Node> path = new ArrayList<>();

        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }

        return path;
    }

    public static void main(String[] args) {

        List<Node> path = solveMaze();

        if (path.isEmpty()) {

            System.out.println("No path found!");

        } else {

            System.out.println("Maze Solved!");
            System.out.println("Path:");

            for (Node node : path) {
                System.out.println(
                    "(" + node.row + ", " + node.col + ")"
                );
            }
        }
    }
}