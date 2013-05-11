import java.io.*;
import java.util.StringTokenizer;


public class Paths implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final long[] paths = new long[]{
            1,
            4,
            12,
            36,
            100,
            284,
            780,
            2172,
            5916,
            16268,
            44100,
            120292,
            324932,
            881500,
            2374444,
            6416596,
            17245332,
            46466676,
            124658732,
            335116620,
            897697164,
            2408806028L,
            6444560484L
    };

    public static void main(String[] args) {
        new Thread(new Paths()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("paths.in"));
            out = new PrintWriter(new FileWriter("paths.out"));
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
        try {
            while (true) {
                int a = nextInt();
                out.print(paths[a]);
            }
        } catch (Exception e) {
        }

    }
}
