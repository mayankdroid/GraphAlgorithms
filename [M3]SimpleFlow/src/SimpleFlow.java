import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class SimpleFlow implements Runnable {
    static StringTokenizer st;
    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        try {
            in = new BufferedReader(new FileReader("flow.in"));
            out = new PrintWriter(new FileWriter("flow.out"));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(9000);
        } finally {
            out.flush();
            out.close();
        }
        //new Thread(new SimpleFlow()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("flow.in"));
            out = new PrintWriter(new FileWriter("flow.out"));
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

    final static int MAX_VERTICES = 100;
    final static int MAX_EDGES = 5000;

    static int vertices, edges, commonVolume, cc = 0;
    static ArrayList[] edgesFromVertices = new ArrayList[MAX_VERTICES + 1];
    static int[][] flow = new int[3][MAX_EDGES * 2 + 3];
    static int[] used = new int[MAX_VERTICES + 1];

    static void solve() throws NumberFormatException, IOException {
        vertices = nextInt();
        edges = nextInt();
        int from, to, c;
        for (int i = 1; i <= 2 * edges; i += 2) {
            from = nextInt();
            to = nextInt();
            c = nextInt();
            if (edgesFromVertices[from] == null) edgesFromVertices[from] = new ArrayList();
            edgesFromVertices[from].add(i);
            flow[0][i] = 0;
            flow[1][i] = c;
            flow[2][i] = to;
            if (edgesFromVertices[to] == null) edgesFromVertices[to] = new ArrayList();
            edgesFromVertices[to].add(i + 1);
            flow[0][i + 1] = 0;
            flow[1][i + 1] = c;
            flow[2][i + 1] = from;
        }
        for (int i = 13; i >= 0; i--) {
            volume = 1 << i;
            while (dfs(1)) {
                commonVolume += 1 << i;
                cc++;
            }
            cc++;
        }
        out.println(commonVolume);
        for (int i = 1; i <= 2 * edges; i += 2) {
            out.println(flow[0][i]);
        }
    }

    static int volume;

    static boolean dfs(int vertex) {
        if (vertex == vertices) return true;
        used[vertex] = cc + 1;
        for (int i = 0; i < edgesFromVertices[vertex].size(); i++) {

            if (
                    flow[0][(Integer) edgesFromVertices[vertex].get(i)] < flow[1][(Integer) edgesFromVertices[vertex].get(i)] &&

                            volume + flow[0][(Integer) edgesFromVertices[vertex].get(i)] <= flow[1][(Integer) edgesFromVertices[vertex].get(i)] &&

                            used[(Integer) edgesFromVertices[2].get(i)] < cc + 1 &&

                            dfs(flow[2][(Integer) edgesFromVertices[vertex].get(i)])
                    ) {

                flow[0][(Integer) edgesFromVertices[vertex].get(i)] += volume;
                if ((Integer) edgesFromVertices[vertex].get(i) % 2 == 0) {
                    flow[0][(Integer) edgesFromVertices[vertex].get(i) - 1] -= volume;
                } else {
                    flow[0][(Integer) edgesFromVertices[vertex].get(i) + 1] -= volume;
                }
                return true;
            }
        }
        return false;
    }
}
