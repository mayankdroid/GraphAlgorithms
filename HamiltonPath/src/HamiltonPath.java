import java.io.*;
import java.util.StringTokenizer;


public class HamiltonPath implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_VERTICES = 10000;
    final int MAX_EDGES = 100000;

    final int[][] adjacency = new int[4][MAX_VERTICES + MAX_EDGES + 1];
    final int[] colorVertex = new int[MAX_VERTICES + 1];
    final int[] longestPath = new int[MAX_VERTICES + 1];

    static int vertices, edges, neededLength = 0;

    int[] startVertices = new int[MAX_VERTICES + 1];
    int[] path = new int[MAX_VERTICES + 1];


    public static void main(String[] args) {
        new Thread(new HamiltonPath()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("path.in"));
            out = new PrintWriter(new FileWriter("path.out"));
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
        vertices = nextInt();
        edges = nextInt();
        readAdjacencyList();

        for (int i = 1; i <= vertices; i++) {
            dfs(i);
        }
    }

    private void dfs(int vertex) {
    }

    void readAdjacencyList() throws NumberFormatException, IOException {
        int from, to;
        for (int i = 1; i <= vertices + edges; i++) {
            adjacency[0][i] = i;
            adjacency[1][i] = 0;
            adjacency[2][i] = i;
        }
        for (int i = vertices + 1; i <= vertices + edges; i++) {
            from = nextInt();
            to = nextInt();
            adjacency[0][i] = to;
            adjacency[1][adjacency[2][from]] = i;
            adjacency[2][from] = i;
            adjacency[3][i]++;
        }
    }
}
