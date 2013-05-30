import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


public class StringCompare implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_LENGTH = 100000;
    final long p = 3571;

    final long[] degrees = new long[MAX_LENGTH + 1];
    final long[] hashString = new long[MAX_LENGTH + 1];

    public static void main(String[] args) {
        new Thread(new StringCompare()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("substrcmp.in"));
            out = new PrintWriter(new FileWriter("substrcmp.out"));
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
        int strLen = string.length();
        degrees[0] = 1;
        hashString[0] = string.charAt(0);
        for (int i = 1; i < strLen; i++) {
            degrees[i] = degrees[i - 1] * p;
            hashString[i] = hashString[i - 1] + degrees[i] * (string.charAt(i));
        }
        int testN = nextInt(), l1, l2, r1, r2;
        while (testN > 0) {
            testN--;
            l1 = nextInt() - 1;
            r1 = nextInt() - 1;
            l2 = nextInt() - 1;
            r2 = nextInt() - 1;
            if ((hashString[r1] - (l1 == 0 ? 0 : hashString[l1 - 1])) * degrees[l2] == (hashString[r2] - (l2 == 0 ? 0 : hashString[l2 - 1])) * degrees[l1]) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
    }
}
