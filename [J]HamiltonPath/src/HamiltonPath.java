import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class HamiltonPath implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

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

    final private int MAX_VERTICES = 2368;
    final private int MAX_EDGES = MAX_VERTICES * (MAX_VERTICES - 1);

    private int vertices, edges;
    private boolean pathFinded, flagNext;
    final private int[][] adjacency = new int[3][MAX_VERTICES + 2 * MAX_EDGES + 1];
    final private int[] path = new int[MAX_VERTICES];
    final private boolean[] color = new boolean[MAX_VERTICES + 1];
    final private boolean[] color1 = new boolean[MAX_VERTICES + 1];

    void solve() throws NumberFormatException, IOException {
        vertices = nextInt();
        edges = nextInt();
        readAdjacencyList();
        for (int i = 1; i <= vertices; i++) {
            dfs(i, 1);
            Arrays.fill(color, false);
            flagNext = false;
            if (pathFinded) break;
        }
        for (int i = 1; i <= vertices; i++) {
            out.print(path[i] + " ");
        }
    }

    private void dfs(int vertex, int depth) {
        color[vertex] = true;
        path[depth] = vertex;
        if (depth == vertices) {
            pathFinded = true;
            return;
        }
        int achievableTop = F(vertex);
        Arrays.fill(color1, false);
        if (depth + achievableTop < vertices - 1) {
            flagNext = true;
            color[vertex] = false;
            return;
        }
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (color[adjacency[0][index]]) {
                continue;
            }
            dfs(adjacency[0][index], depth + 1);
            if (pathFinded || flagNext) {
                color[vertex] = false;
                return;
            }
        }
        color[vertex] = false;
    }

    private int F(int vertex) {
        color1[vertex] = true;
        int result = 0;
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (!color[adjacency[0][index]] && !color1[adjacency[0][index]]) {
                result += F(adjacency[0][index]);
                result++;
            }
        }
        return result;
    }

    void readAdjacencyList() throws NumberFormatException, IOException {
        int from, to;
        for (int i = 1; i <= vertices + edges; i++) {
            adjacency[0][i] = i;
            adjacency[1][i] = 0;
            adjacency[2][i] = i;
        }
        for (int i = vertices + 1; i <= vertices + edges * 2; i += 2) {
            from = nextInt();
            to = nextInt();
            adjacency[0][i] = to;
            adjacency[1][adjacency[2][from]] = i;
            adjacency[2][from] = i;

            adjacency[0][i + 1] = from;
            adjacency[1][adjacency[2][to]] = i + 1;
            adjacency[2][to] = i + 1;
        }
    }
}
