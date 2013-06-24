import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Hedgehog implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    public static void main(String[] args) {
        new Thread(new Hedgehog()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("hedgehog.in"));
            out = new PrintWriter(new FileWriter("hedgehog.out"));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(9000);
        } finally {
            out.flush();
            out.close();
        }
    }

    String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }

    int nextInt() throws NumberFormatException, IOException {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws NumberFormatException, IOException {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(nextToken());
    }

    final int MAX_VERTICES = 15;
    final int MAX_EDGES = (MAX_VERTICES - 1) * MAX_VERTICES / 2;

    private int vertices, edges, countCV = 0, countIV1 = 0, countIV2 = 0;
    final int[] countVertices = new int[MAX_VERTICES + 1];
    final int[][] listEdges = new int[2][MAX_EDGES];
    final boolean[] controlSet = new boolean[MAX_VERTICES + 1];
    final boolean[] independentSet1 = new boolean[MAX_VERTICES + 1];
    final boolean[] independentSet2 = new boolean[MAX_VERTICES + 1];

    void solve() throws NumberFormatException, IOException {
        vertices = nextInt();
        edges = nextInt();
        int i = 0;
        while (i < edges) {
            listEdges[0][i] = nextInt();
            listEdges[1][i] = nextInt();
            if (!independentSet1[listEdges[0][i]] && !independentSet2[listEdges[0][i]]) {
                independentSet1[listEdges[0][i]] = true;
                countIV1++;
            }
            if (!independentSet2[listEdges[1][i]] && !independentSet1[listEdges[1][i]]) {
                independentSet2[listEdges[1][i]] = true;
                countIV2++;
            }
            countVertices[listEdges[0][i]]++;
            countVertices[listEdges[1][i]]++;
            i++;
        }
        for (i = 0; i < edges; i++) {
            if (independentSet2[listEdges[0][i]] && independentSet2[listEdges[1][i]]) {
                independentSet2[listEdges[0][i]] = false;
                independentSet2[listEdges[1][i]] = false;
                countIV2--;
            }
            if (independentSet1[listEdges[0][i]] && independentSet1[listEdges[1][i]]) {
                independentSet1[listEdges[0][i]] = false;
                independentSet1[listEdges[1][i]] = false;
                countIV1--;
            }
            if (controlSet[listEdges[0][i]] || controlSet[listEdges[1][i]]) {
                continue;
            } else {
                if (countVertices[listEdges[0][i]] >= countVertices[listEdges[1][i]]) {
                    controlSet[listEdges[0][i]] = true;
                    countCV++;
                } else {
                    controlSet[listEdges[1][i]] = true;
                    countCV++;
                }
            }
        }
        out.println(countCV + " " + Math.max(countIV1, countIV2));
    }
}
