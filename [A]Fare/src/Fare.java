import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Fare implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;
    int vertex, edge, capital;
    int[] vertices;
    int[][] adjacency;
    boolean v[];
    int[] dist;

    public static void main(String[] args) {
        new Thread(new Fare()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("bfsrev.in"));
            out = new PrintWriter(new FileWriter("bfsrev.out"));
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
        vertex = nextInt();
        capital = nextInt();
        edge = nextInt();
        vertices = new int[vertex + 1];
        adjacency = new int[2][edge + 1];
        int from, to;
        for (int i = 1; i <= edge; i++) {
            from = nextInt();
            to = nextInt();
            if (vertices[to] == 0) {
                vertices[to] = i;
            } else {
                int next = vertices[to];
                while (adjacency[1][next] != 0) {
                    next = adjacency[1][next];
                }
                adjacency[1][next] = i;
            }
            adjacency[0][i] = from;
        }
        v = new boolean[vertex + 1];
        dist = new int[vertex + 1];

        for (int i = 1; i <= vertex; i++) dist[i] = -1;
        bfs();
        for (int i = 1; i <= vertex; i++) {
            out.print(dist[i] + " ");
        }
        out.close();
    }

    void bfs() {
        Queue<Integer> queueVertices = new LinkedList<Integer>();
        queueVertices.add(capital);
        dist[capital] = 0;
        v[queueVertices.element()] = true;
        while (!queueVertices.isEmpty()) {
            int index, temp = queueVertices.remove();
            index = vertices[temp];
            while (index != 0) {
                if (!v[adjacency[0][index]]) {
                    queueVertices.add(adjacency[0][index]);
                    dist[adjacency[0][index]] = dist[temp] + 1;
                    v[adjacency[0][index]] = true;
                }
                index = adjacency[1][index];
            }
        }
    }
}
