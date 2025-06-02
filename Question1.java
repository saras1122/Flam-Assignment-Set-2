import java.util.*;

public class Question1 {

    private static boolean isSafePlace(int n, char[][] nQueens, int row, int col) {
        for (int i = 0; i < n; i++) {
            if (nQueens[i][col] == 'Q') {
                return false;
            }
        }

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (nQueens[i][j] == 'Q') {
                return false;
            }
        }

        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (nQueens[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }

    private static void placeQueens(int n, List<List<String>> output, char[][] nQueens, int row) {
        if (row == n) {
            List<String> solution = new ArrayList<>();
            for (char[] rowArray : nQueens) {
                solution.add(new String(rowArray));
            }
            output.add(solution);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafePlace(n, nQueens, row, col)) {
                nQueens[row][col] = 'Q';
                placeQueens(n, output, nQueens, row + 1);
                nQueens[row][col] = '.';
            }
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> output = new ArrayList<>();
        char[][] nQueens = new char[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(nQueens[i], '.');
        }

        placeQueens(n, output, nQueens, 0);
        return output;
    }

    public static void main(String[] args) {
        // Test case 1
        int n1 = 4;
        System.out.println("Solutions for n = " + n1 + ":");
        List<List<String>> result1 = solveNQueens(n1);
        for (List<String> board : result1) {
            for (String row : board) {
                System.out.println(row);
            }
            System.out.println();
        }

        // Test case 2
        int n2 = 1;
        System.out.println("Solutions for n = " + n2 + ":");
        List<List<String>> result2 = solveNQueens(n2);
        for (List<String> board : result2) {
            for (String row : board) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}
