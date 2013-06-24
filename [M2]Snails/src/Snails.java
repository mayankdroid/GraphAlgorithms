import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class Snails implements Runnable {
    static StringTokenizer st;
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        try {
            in = new BufferedReader(new FileReader("snails.in"));
            out = new PrintWriter(new FileWriter("snails.out"));
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
            in = new BufferedReader(new FileReader("snails.in"));
            out = new PrintWriter(new FileWriter("snails.out"));
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

    static final int MAX_VERTICES = 100000;
    static final int MAX_EDGES = 100000;

    static int vertices, edges, s, t, commonVolume = 0;
    static ArrayList[] edgesFromVertices = new ArrayList[MAX_VERTICES + 1];
    static int[][] flow = new int[3][MAX_EDGES * 2 + 1];
    static boolean[] used = new boolean[MAX_VERTICES + 1];

    static void solve() throws NumberFormatException, IOException {
        vertices = nextInt();
        edges = nextInt();
        s = nextInt();
        t = nextInt();
        int from, to, c;
        for (int i = 1; i <= 2 * edges; i += 2) {
            from = nextInt();
            to = nextInt();
            if (edgesFromVertices[from] == null) edgesFromVertices[from] = new ArrayList();
            edgesFromVertices[from].add(i);
            flow[0][i] = 0;
            flow[1][i] = 1;
            flow[2][i] = to;
            if (edgesFromVertices[to] == null) edgesFromVertices[to] = new ArrayList();
            edgesFromVertices[to].add(i + 1);
            flow[0][i + 1] = 0;
            flow[1][i + 1] = 0;
            flow[2][i + 1] = from;
        }
        while (dfs(s) && commonVolume < 2) {
            commonVolume++;
            Arrays.fill(used, 1, vertices + 1, false);
        }
        if (commonVolume == 2) {
            out.println("YES");
            printPath();
            out.println();
            printPath();
        } else {
            out.println("NO");
        }
    }

    static private void printPath() {
        out.print(s + " ");
        int v = s;
        while (v != t) {
            for (int i = 0; i < edgesFromVertices[v].size(); i++) {
                if (flow[0][(Integer) edgesFromVertices[v].get(i)] == 1) {
                    flow[0][(Integer) edgesFromVertices[v].get(i)] = 0;
                    out.print(flow[2][(Integer) edgesFromVertices[v].get(i)] + " ");
                    v = flow[2][(Integer) edgesFromVertices[v].get(i)];
                    break;
                }
            }
        }
    }

    static boolean dfs(int vertex) {
        if (vertex == t) return true;
        used[vertex] = true;

        for (int i = 0; i < edgesFromVertices[vertex].size(); i++) {
            if (flow[0][(Integer) edgesFromVertices[vertex].get(i)] < flow[1][(Integer) edgesFromVertices[vertex].get(i)] &&
                    !used[flow[2][(Integer) edgesFromVertices[vertex].get(i)]] &&
                    dfs(flow[2][(Integer) edgesFromVertices[vertex].get(i)])) {
                flow[0][(Integer) edgesFromVertices[vertex].get(i)]++;
                if ((Integer) edgesFromVertices[vertex].get(i) % 2 == 0) {
                    flow[0][(Integer) edgesFromVertices[vertex].get(i) - 1]--;
                } else {
                    flow[0][(Integer) edgesFromVertices[vertex].get(i) + 1]--;
                }
                return true;
            }
        }
        return false;
    }
}