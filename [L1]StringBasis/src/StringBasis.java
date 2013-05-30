import java.io.*;
import java.util.StringTokenizer;


public class StringBasis implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_LENGTH = 50000;
    final int[] prefixVector = new int[MAX_LENGTH];

    public static void main(String[] args) {
        new Thread(new StringBasis()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("basis.in"));
            out = new PrintWriter(new FileWriter("basis.out"));
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
        String string = nextToken();
        int strLength = string.length();
        prefixVector[0] = 0;
        int k = 0;
        for (int i = 1; i < strLength; i++) {
            while ((k > 0) && (string.charAt(k) != string.charAt(i))) {
                k = prefixVector[k - 1];
            }
            if (string.charAt(k) == string.charAt(i)) {
                k++;
            }
            prefixVector[i] = k;
        }
        out.print(strLength - prefixVector[strLength - 1]);
    }
}
