import java.util.*;

public class FastNQueens {

    static int N;
    static long FULL_MASK;
    static int[] board;
    static boolean found = false;
    static Random rand = new Random();

    static void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j)
                    System.out.print("|Q");
                else
                    System.out.print("| ");
            }
            System.out.println("|");
        }
    }

    static void solve(long cols, long diagL, long diagR, int row) {
        if (found) return;

        if (cols == FULL_MASK) {
            printBoard();
            found = true;
            return;
        }

        long safe = FULL_MASK & ~(cols | diagL | diagR);

        // Store all possible positions
        List<Long> options = new ArrayList<>();
        while (safe != 0) {
            long bit = safe & -safe;
            safe -= bit;
            options.add(bit);
        }

        // Shuffle to get random solution
        Collections.shuffle(options, rand);

        for (long bit : options) {
            int col = Long.numberOfTrailingZeros(bit);
            board[row] = col;

            solve(
                cols | bit,
                (diagL | bit) << 1,
                (diagR | bit) >> 1,
                row + 1
            );

            if (found) return;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter N: ");
        N = sc.nextInt();

        FULL_MASK = (1L << N) - 1;
        board = new int[N];

        solve(0, 0, 0, 0);

        if (!found) {
            System.out.println("No solution found");
        }
    }
}