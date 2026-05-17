import java.util.*;

class Node {
    int[][] state;
    int g, h;
    int x, y;      // position of 0
    Node parent;

    Node(int[][] state, int g, int x, int y, Node parent, int[][] goal) {
        this.state = state;
        this.g = g;
        this.x = x;
        this.y = y;
        this.parent = parent;
        // Updated to use Hamming Distance
        this.h = hammingHeuristic(state, goal);
    }

    int f() {
        return g + h;
    }

    // Hamming Distance: Count of misplaced tiles (excluding the empty tile 0)
    static int hammingHeuristic(int[][] curr, int[][] goal) {
        int misplaced = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (curr[i][j] != 0) { // Do not count the blank tile
                    if (curr[i][j] != goal[i][j]) {
                        misplaced++;
                    }
                }
            }
        }
        return misplaced;
    }

    String key() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : state)
            for (int v : row)
                sb.append(v).append(",");
        return sb.toString();
    }
}

public class AStarAlgo {
    static int[][] goal;
    static int[] dx = {-1, 1, 0, 0};  // up, down
    static int[] dy = {0, 0, -1, 1};  // left, right

    static void print(Node n, int iteration) {
        System.out.println("Iteration: " + iteration);
        System.out.println("Current State:");
        for (int[] row : n.state) {
            for (int v : row) {
                System.out.print("| ");
                if (v == 0) System.out.print("_ ");
                else System.out.print(v + " ");
                if (v==row[row.length-1])  System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("f(n) = " + n.f() + " (g: " + n.g + ", h: " + n.h + ")");
        System.out.println("-------------------");
    }

    static boolean valid(int x, int y) {
        return x >= 0 && y >= 0 && x < 3 && y < 3;
    }

    static void solve(int[][] start, int sx, int sy) {
        // Priority Queue ordered by f(n) = g(n) + h(n)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        Set<String> visited = new HashSet<>();

        Node startNode = new Node(start, 0, sx, sy, null, goal);
        pq.add(startNode);

        int iterations = 0;

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            iterations++;

            print(curr, iterations);

            if (curr.h == 0) {
                System.out.println("GOAL REACHED!");
                System.out.println("Total Iterations: " + iterations);
                return;
            }

            visited.add(curr.key());

            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];

                if (valid(nx, ny)) {
                    int[][] newState = new int[3][3];
                    for (int r = 0; r < 3; r++)
                        newState[r] = curr.state[r].clone();

                    // Swap blank (0) with adjacent tile
                    newState[curr.x][curr.y] = newState[nx][ny];
                    newState[nx][ny] = 0;

                    Node child = new Node(newState, curr.g + 1, nx, ny, curr, goal);

                    if (!visited.contains(child.key())) {
                        pq.add(child);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] start = new int[3][3];
        goal = new int[3][3];

        int zx = 0, zy = 0;

        System.out.println("Enter the initial state (3x3 grid, use 0 for blank):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                start[i][j] = sc.nextInt();
                if (start[i][j] == 0) {
                    zx = i; zy = j;
                }
            }
        }

        System.out.println("Enter the goal state:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goal[i][j] = sc.nextInt();
            }
        }

        solve(start, zx, zy);
        sc.close();
    }
}
