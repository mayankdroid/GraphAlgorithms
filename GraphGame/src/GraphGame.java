import java.io.*;
import java.util.StringTokenizer;


public class GraphGame implements Runnable {
    final static int MAX_VERTICES = 100000;
    final static int MAX_EDGES = 100000;
    final static int[][] adjacency = new int[3][MAX_VERTICES + MAX_EDGES + 1];
    final static int[] colorVertex = new int[MAX_VERTICES + 1];

    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    static int vertices, edges, cc = 0;

    public static void main(String[] args) {
        new Thread(new GraphGame()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("gg.in"));
            out = new PrintWriter(new FileWriter("gg.out"));
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

    void solve() throws NumberFormatException, IOException {
        int testN = 0;
        while (true) {
            try {
                int startVertex, edgeCounter = 0;
                vertices = nextInt();
                if (vertices == 0) break;
                edges = nextInt();
                startVertex = nextInt();
                testN++;
                edgeCounter = readAdjacencyList();
                if (graphHasObviousCycle(vertices, edges, edgeCounter)) {
                    out.print("Players can avoid first player winning in game " + testN + ".\n");
                } else {
                    cc += 2;
                    if (getWinner(startVertex) == 1) {
                        out.print("First player always wins in game " + testN + ".\n");
                    } else {
                        out.print("Players can avoid first player winning in game " + testN + ".\n");
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private int getWinner(int startVertex) {
        return dfs(startVertex, 0);
    }

    private int dfs(int startVertex, int startLevel) {
        int index = startVertex;
        int nextLevel = startLevel + 1;
        colorVertex[startVertex] = cc - 1;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (colorVertex[adjacency[0][index]] <= cc - 2) {
                if (dfs(adjacency[0][index], nextLevel) == 2) return 2;
            } else {
                if (colorVertex[adjacency[0][index]] == cc - 1) {
                    return 2;
                }
                if (adjacency[1][adjacency[0][index]] == 0 && nextLevel % 2 == 0) { /* Don't have any outcoming edges from next vertex */
                    return 2;
                }
            }
        }
        if (index <= vertices && startLevel % 2 == 0) { /* Don't have any outcoming edges from current vertex */
            return 2;
        }
        colorVertex[startVertex] = cc;
        return 1;
    }

    private boolean graphHasObviousCycle(int vertices, int edges, int edgeCounter) {
        return (edges - edgeCounter) == vertices ? true : false;
    }

    int readAdjacencyList() throws NumberFormatException, IOException {
        int from, to, moreThanOneEdgeCounter = 0;
        for (int i = 1; i <= vertices + edges; i++) {
            adjacency[0][i] = i;
            adjacency[1][i] = 0;
            adjacency[2][i] = i;
        }
        for (int i = vertices + 1; i <= vertices + edges; i++) {
            from = nextInt();
            to = nextInt();
            if (adjacency[2][from] > vertices) moreThanOneEdgeCounter++;
            adjacency[1][adjacency[2][from]] = i;
            adjacency[2][from] = i;
            adjacency[0][i] = to;
        }
        return moreThanOneEdgeCounter;
    }
}
