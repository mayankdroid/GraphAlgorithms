import java.io.*;
import java.util.StringTokenizer;


public class GraphGame implements Runnable {
    final static int MAX_VERTICES = 100000;
    final static int MAX_EDGES = 1000000;
    final static int[][] adjacency = new int[2][MAX_VERTICES + MAX_EDGES + 1];
    final static int[] usedVertex = new int[MAX_VERTICES + 1];

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
                edges = nextInt();
                startVertex = nextInt();
                testN++;
                readAdjacencyList(edgeCounter);
                if (graphHasObviousCycle(vertices, edges, edgeCounter)) {
                    out.print("Players can avoid first player winning in game " + testN);
                } else {
                    cc++;
                    if (getWinner(startVertex) == 1) {
                        out.print("First player always wins in game " + testN);
                    } else {
                        out.print("Players can avoid first player winning in game " + testN);
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    private int getWinner(int startVertex) {
        dfs(startVertex, 0);
        return 1;
    }

    private int dfs(int startVertex, int startLevel) {
        int index = startVertex;
        int nextLevel = startLevel + 1;
        usedVertex[startVertex] = cc;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (usedVertex[adjacency[0][index]] == cc) return 2;
            if (dfs(adjacency[0][index], nextLevel) == 2) return 2;
        }
        if (adjacency[0][index] < vertices && startLevel % 2 == 0) {
            return 2;
        }
        return 1;
    }

    private boolean graphHasObviousCycle(int vertices, int edges, int edgeCounter) {
        return (edges - edgeCounter) == vertices ? true : false;
    }

    void readAdjacencyList(int moreThanOneEdgeCounter) throws NumberFormatException, IOException {
        int from, to;
        for (int i = 1; i <= vertices; i++) {
            adjacency[0][i] = i;
            adjacency[1][0] = 0;
        }
        for (int i = vertices + 1; i <= vertices + edges; i++) {
            from = nextInt();
            to = nextInt();
            while (adjacency[1][from] != 0) {
                from = adjacency[1][from];
            }
            if (from > vertices) moreThanOneEdgeCounter++;
            adjacency[1][from] = i;
            adjacency[0][i] = to;
        }
    }
}
