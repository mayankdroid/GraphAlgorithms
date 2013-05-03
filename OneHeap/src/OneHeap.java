import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class OneHeap implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;
    int variation;
    int[] var;
    ArrayList<Boolean> Heaps;

    public static void main(String[] args) {
        new Thread(new OneHeap()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("heaps2.in"));
            out = new PrintWriter(new FileWriter("heaps2.out"));
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
        variation = nextInt();
        var = new int[variation];
        for (int i = 0; i < variation; i++) {
            var[i] = nextInt();
        }
        int heaps = nextInt();
        Heaps = new ArrayList<Boolean>();
        Heaps.add(false);
        for (int i = 0; i < heaps; i++) {
            String res = SpragueGrandy(nextInt());
            out.println(res);
        }
    }

    String SpragueGrandy(int stones) {
        if (Heaps.size() < stones + 1) {
            for (int i = Heaps.size(); i <= stones; i++) {
                Heaps.add(i, false);
                for (int j = 0; j < variation; j++) {
                    if (i < var[j]) continue;
                    if (i == var[j]) {
                        Heaps.set(i, true);
                        break;
                    }
                    if (!Heaps.get(i - var[j])) {
                        Heaps.set(i, true);
                        break;
                    }
                }
            }
        }
        if (Heaps.get(stones)) return "First";
        return "Second";
    }
}
