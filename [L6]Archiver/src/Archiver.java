import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Archiver implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final int MAX_LENGTH = 4000;
    private String string;
    private int[] result = new int[MAX_LENGTH];
    private int[] prefixVector = new int[MAX_LENGTH];

    public static void main(String[] args) {
        new Thread(new Archiver()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("archiver.in"));
            out = new PrintWriter(new FileWriter("archiver.out"));
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
        string = nextToken();
    }

    private void zFunction(String string) {
        int strLen = string.length();
        for (int i = 1, l = 0, r = 0; i < strLen; ++i) {
            if (i <= r)
                result[i] = Math.min(r - i + 1, result[i - l]);
            while (i + result[i] < strLen && string.charAt(result[i]) == string.charAt(i + result[i])) {
                ++result[i];
            }
            if (i + result[i] - 1 > r) {
                l = i;
                r = i + result[i] - 1;
            }
        }
    }
}
