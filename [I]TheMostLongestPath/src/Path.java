import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Path implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_VERTICES = 22;
    final int MAX_EDGES = 1000;

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

    final int[][] adjacency = new int[4][MAX_VERTICES + MAX_EDGES + 1];
    final boolean[] colorVertex = new boolean[MAX_VERTICES + 1];
    final boolean[] color1 = new boolean[MAX_VERTICES + 1];
    final int[] longestPath = new int[MAX_VERTICES + 1];
    final int[] currentPath = new int[MAX_VERTICES + 1];

    private int vertices, edges;

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
        colorVertex[vertex] = true;
        currentPath[length] = vertex;
        currentPath[0] = length;
        int aVertices = F(vertex);
        Arrays.fill(color1, false);
        if (length - 1 + aVertices < longestPath[0]) {
            return;
        }
        int index = vertex, count = 0;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (!colorVertex[adjacency[0][index]]) {
                dfs(adjacency[0][index], length + 1);
            } else {
                count++;
            }

        }
        if (index == vertex || count == adjacency[3][vertex]) {
            if (currentPath[0] > longestPath[0]) {
                int i = 1;
                while (currentPath[i] == longestPath[i]) {
                    i++;
                }
                for (; i <= currentPath[0]; i++) {
                    longestPath[i] = currentPath[i];
                }
                longestPath[0] = currentPath[0];
            }
        }
        colorVertex[vertex] = false;
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
            adjacency[3][from]++;
        }
    }

    private int F(int vertex) {
        color1[vertex] = true;
        int result = 0;
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (!colorVertex[adjacency[0][index]] && !color1[adjacency[0][index]]) {
                result += F(adjacency[0][index]);
                result++;
            }
        }
        return result;
    }
}
