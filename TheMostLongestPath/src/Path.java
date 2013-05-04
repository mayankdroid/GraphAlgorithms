import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

    static int vertices, edges, neededLength = 0;

    List<Integer> startVertices = new LinkedList<Integer>();

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
            startVertices.add(i);
        }
        while (!startVertices.isEmpty()) {
            neededLength++;
            Iterator index = startVertices.iterator();
            while (index.hasNext()) {
                Integer startVertex = (Integer) index.next();
                if (!dfs(startVertex, 0)) {
                    index.remove();
                } else {
                    break;
                }
            }
        }
        out.println(neededLength - 1);
        for (int i = 0; i <= neededLength - 1; i++) {
            out.print(longestPath[i] + " ");
        }
    }

    private boolean dfs(int vertex, int length) {
        if (length == neededLength) {
            longestPath[length] = vertex;
            return true;
        }
        colorVertex[vertex] = 1;
        int index = vertex;
        while (adjacency[1][index] != 0) {
            index = adjacency[1][index];
            if (colorVertex[adjacency[0][index]] == 0) {
                if (dfs(adjacency[0][index], length + 1) == true) {
                    longestPath[length] = vertex;
                    colorVertex[vertex] = 0;
                    return true;
                }
            }
        }
        colorVertex[vertex] = 0;
        return false;
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
