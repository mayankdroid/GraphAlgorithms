import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Pairs implements Runnable {
    static StringTokenizer st;
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        try {
            in = new BufferedReader(new FileReader("pairs.in"));
            out = new PrintWriter(new FileWriter("pairs.out"));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(9000);
        } finally {
            out.flush();
            out.close();
        }
        //new Thread(new Snails()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("pairs.in"));
            out = new PrintWriter(new FileWriter("pairs.out"));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(9000);
        } finally {
            out.flush();
            out.close();
        }
    }

    static String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }

    static int nextInt() throws NumberFormatException, IOException {
        return Integer.parseInt(nextToken());
    }

    static long nextLong() throws NumberFormatException, IOException {
        return Long.parseLong(nextToken());
    }

    static double nextDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(nextToken());
    }

    static final int MAX_VERTICES = 502;
    static final int MAX_EDGES = MAX_VERTICES * MAX_VERTICES / 4;

    static int countA, countB, s, t, commonVolume = 0;
    static int[][] edgesFromVertices = new int[MAX_VERTICES + 1][MAX_EDGES / 100];
    static int[][] flow = new int[3][MAX_EDGES * 2 + 1];
    static boolean[] used = new boolean[MAX_VERTICES + 1];

    static void solve() throws NumberFormatException, IOException {
        countA = nextInt();
        countB = nextInt();
        int from, to, j = 0;
        boolean[] added = new boolean[countB + 1];
        for (int i = 1; i <= countA; i++) {
            int current = nextInt();
            while (current != 0) {
                edgesFromVertices[i][++edgesFromVertices[i][0]] = j;
                flow[0][j] = 0;
                flow[1][j] = 1;
                flow[2][j] = countA + current;
                edgesFromVertices[countA + current][++edgesFromVertices[countA + current][0]] = j + 1;
                flow[0][j + 1] = 0;
                flow[1][j + 1] = 0;
                flow[2][j + 1] = i;
                j += 2;
                if (!added[current]) {
                    edgesFromVertices[countA + current][++edgesFromVertices[countA + current][0]] = j;
                    flow[0][j] = 0;
                    flow[1][j] = 1;
                    flow[2][j] = countA + countB + 1;
                    edgesFromVertices[countA + countB + 1][++edgesFromVertices[countA + countB + 1][0]] = j + 1;
                    flow[0][j + 1] = 0;
                    flow[1][j + 1] = 0;
                    flow[2][j + 1] = countA + current;
                    added[current] = true;
                    j += 2;
                }
                current = nextInt();
            }
            edgesFromVertices[0][++edgesFromVertices[0][0]] = j;
            flow[0][j] = 0;
            flow[1][j] = 1;
            flow[2][j] = i;
            edgesFromVertices[i][++edgesFromVertices[i][0]] = j + 1;
            flow[0][j + 1] = 0;
            flow[1][j + 1] = 0;
            flow[2][j + 1] = 0;
            j += 2;
        }
        int count = 0;
        while (dfs(0)) {
            count++;
            Arrays.fill(used, 1, countA + 1, false);
        }
        int[] res;
        for (int i = 0; i < count; i++) {
            res = printPath();
            out.print(res[1] + " " + (res[2] - countB));
            out.println();
        }
    }

    static private int[] printPath() {
        int[] res = new int[4];
        res[0] = 0;
        int v = 0;
        while (v != countA + countB + 1) {
            for (int i = 1; i <= edgesFromVertices[v][0]; i++) {
                if (flow[0][(Integer) edgesFromVertices[v][i]] == 1) {
                    flow[0][(Integer) edgesFromVertices[v][i]] = 0;
                    res[++res[0]] = flow[2][(Integer) edgesFromVertices[v][i]];
                    v = flow[2][(Integer) edgesFromVertices[v][i]];
                    break;
                }
            }
        }
        return res;
    }

    static boolean dfs(int vertex) {
        if (vertex == countA + countB + 1) return true;
        used[vertex] = true;

        for (int i = 1; i <= edgesFromVertices[vertex][0]; i++) {
            if (flow[0][edgesFromVertices[vertex][i]] < flow[1][(Integer) edgesFromVertices[vertex][i]] &&
                    !used[flow[2][(Integer) edgesFromVertices[vertex][i]]] &&
                    dfs(flow[2][(Integer) edgesFromVertices[vertex][i]])) {
                flow[0][(Integer) edgesFromVertices[vertex][i]]++;
                if ((Integer) edgesFromVertices[vertex][i] % 2 != 0) {
                    flow[0][(Integer) edgesFromVertices[vertex][i] - 1]--;
                } else {
                    flow[0][(Integer) edgesFromVertices[vertex][i] + 1]--;
                }
                return true;
            }
        }
        return false;
    }
}