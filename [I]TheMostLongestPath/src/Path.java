import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Path implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_VERTICES = 22;
    final int MAX_EDGES = 1000;

    final int[][] adjacency = new int[3][MAX_VERTICES + MAX_EDGES + 1];
    final int[] colorVertex = new int[MAX_VERTICES + 1];
    final int[] longestPath = new int[MAX_VERTICES + 1];
    final int[] currentPath = new int[MAX_VERTICES + 1];

    final boolean[] color1 = new boolean[MAX_VERTICES + 1];

    static int vertices, edges;

    public static void main(String[] args) {
        new Thread(new Path()).start();
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
            dfs(i, 1);
        }
        out.println(longestPath[0] - 1);
        for (int i = 1; i <= longestPath[0]; i++) {
            out.print(longestPath[i] + " ");
        }
    }

    private void dfs(int vertex, int length) {
        colorVertex[vertex] = 1;
        currentPath[length] = vertex;
        int aVertices = F(vertex);
        Arrays.fill(color1, false);
        if (length - 1 + aVertices < longestPath[0]) {
            colorVertex[vertex] = 0;
            return;
        }
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (colorVertex[adjacency[0][index]] == 0) {
                dfs(adjacency[0][index], length + 1);
            }
        }
        if (longestPath[0] < length) {
            for (int i = 1; i <= length; i++) {
                longestPath[i] = currentPath[i];
            }
            longestPath[0] = length;
        }
        colorVertex[vertex] = 0;
    }

    private int F(int vertex) {
        color1[vertex] = true;
        int result = 0;
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (colorVertex[adjacency[0][index]] != 1 && !color1[adjacency[0][index]]) {
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
        for (int i = vertices + 1; i <= vertices + edges; i++) {
            from = nextInt();
            to = nextInt();
            adjacency[1][adjacency[2][from]] = i;
            adjacency[2][from] = i;
            adjacency[0][i] = to;
        }
    }
}