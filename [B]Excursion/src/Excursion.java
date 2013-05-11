import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Excursion implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;
    int vertexN, edgeN;
    LinkedList<int[]>[] graph;
    LinkedList<Integer>[] pathFromStart;

    public static void main(String[] args) {
        new Thread(new Excursion()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("excurs.in"));
            out = new PrintWriter(new FileWriter("excurs.out"));
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
        vertexN = nextInt();
        edgeN = nextInt();
        graph = new LinkedList[vertexN + 1];
        pathFromStart = new LinkedList[vertexN + 1];
        initGraph();
        readGraphToBerj();
        int minPath = findMinPath();
        out.print(minPath);
    }

    private int findMinPath() {
        int[] bfsQueue = new int[edgeN];
        int qBegin, qEnd;
        int minCycleLength = Integer.MAX_VALUE;
        try {
            int startVertex, cc;
            int[] usedVertex, usedEdge, lengthFromStart;
            usedVertex = new int[vertexN + 1];
            usedEdge = new int[edgeN + 1];
            lengthFromStart = new int[vertexN + 1];
            startVertex = 0;
            cc = 0;
            while (true) {
                startVertex++;
                cc++;
                qBegin = qEnd = 0;
                lengthFromStart = new int[vertexN + 1];
                bfsQueue[qEnd] = startVertex;
                qEnd++;
                while (qBegin != qEnd) {
                    int currentBfsVertex = bfsQueue[qBegin];
                    qBegin++;
                    usedVertex[currentBfsVertex] = cc;
                    if (2 * lengthFromStart[currentBfsVertex] >= minCycleLength) continue;
                    for (int[] currentChild : graph[currentBfsVertex]) {
                        if (usedEdge[currentChild[2]] == cc) continue;
                        if (usedVertex[currentChild[0]] == cc) {
                            int cycleLength = (lengthFromStart[currentBfsVertex] + currentChild[1]) + lengthFromStart[currentChild[0]];
                            if (cycleLength < minCycleLength) minCycleLength = cycleLength;
                        } else {
                            lengthFromStart[currentChild[0]] = lengthFromStart[currentBfsVertex] + currentChild[1];
                            usedVertex[currentChild[0]] = cc;
                            bfsQueue[qEnd] = currentChild[0];
                            qEnd++;
                        }
                        usedEdge[currentChild[2]] = cc;
                    }
                }
            }
        } catch (Exception e) {
            return minCycleLength;
        }
    }

    private void readGraphToBerj() {
        Integer from, to, length, i = 0;
        while (true) {
            try {
                i++;
                from = nextInt();
                to = nextInt();
                length = nextInt();
                int[] triple = {to, length, i};
                graph[from].addLast(triple);
                triple = new int[3];
                triple[0] = from;
                triple[1] = length;
                triple[2] = i;
                graph[to].addLast(triple);
            } catch (Exception e) {
                break;
            }

        }
    }

    private void initGraph() {
        Integer i = 1;
        while (true) {
            try {
                graph[i] = new LinkedList<int[]>();
                pathFromStart[i] = new LinkedList<Integer>();
                i++;
            } catch (Exception e) {
                return;
            }
        }

    }
}
