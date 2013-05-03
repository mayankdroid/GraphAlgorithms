/**
 * Main.java                 02.05.2013
 * Class for precompute.
 */

public class Main {
    private static final int BOARD_DEPTH = 46;
    private static final int[][] usedCheck = new int[BOARD_DEPTH][BOARD_DEPTH];
    private static final long[] result = new long[23];
    private static int neededDepth = 0;

    public static void main(String[] args) {
        int[] startVertex = new int[]{23, 23};

        for (int i = 21; i <= 22; i++) {
            neededDepth = i;
            dfs(startVertex, 0);
            System.out.println("\n" + result[i]);
        }
        System.out.println("\n\n THE END!!!");
    }

    private static void dfs(int[] startVertex, int startDepth) {
        if (startDepth == neededDepth) {
            result[neededDepth]++;
            return;
        }
        usedCheck[startVertex[0]][startVertex[1]] = 1;
        int[] nearVertex = startVertex;
        nearVertex[0]++;
        if (usedCheck[nearVertex[0]][nearVertex[1]] == 0) dfs(nearVertex, startDepth + 1);
        nearVertex[0] -= 2;
        if (usedCheck[nearVertex[0]][nearVertex[1]] == 0) dfs(nearVertex, startDepth + 1);
        nearVertex[0]++;
        nearVertex[1]++;
        if (usedCheck[nearVertex[0]][nearVertex[1]] == 0) dfs(nearVertex, startDepth + 1);
        nearVertex[1] -= 2;
        if (usedCheck[nearVertex[0]][nearVertex[1]] == 0) dfs(nearVertex, startDepth + 1);
        nearVertex[1]++;
        usedCheck[startVertex[0]][startVertex[1]] = 0;
    }
}
