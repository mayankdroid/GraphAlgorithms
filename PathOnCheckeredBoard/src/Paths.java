import java.io.*;
import java.util.StringTokenizer;


public class Paths implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;
    static int seats;
    static int[] F;

    public static void main(String[] args) {
        new Thread(new Paths()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("comfort.in"));
            out = new PrintWriter(new FileWriter("comfort.out"));
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
    }
}
