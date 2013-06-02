import java.io.*;
import java.util.StringTokenizer;


public class SuffixArrayLCP implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final int MAX_LENGTH = 100000;
    private final int[] invariantSuffixArray = new int[MAX_LENGTH + 1];
    private final int[] suffixArray = new int[MAX_LENGTH + 1];
    private final int[] lcp = new int[MAX_LENGTH + 1];

    private int stringLength;

    public static void main(String[] args) {
        new Thread(new SuffixArrayLCP()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("sufflcp.in"));
            out = new PrintWriter(new FileWriter("sufflcp.out"));
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(9000);
        } finally {
            out.flush();
            out.close();
        }
    }

    String nextToken(boolean flag) throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        if (flag) return st.nextToken();
        else return st.nextToken("");

    }

    int nextInt() throws NumberFormatException, IOException {
        return Integer.parseInt(nextToken(true));
    }

    long nextLong() throws NumberFormatException, IOException {
        return Long.parseLong(nextToken(true));
    }

    double nextDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(nextToken(true));
    }

    void solve() throws NumberFormatException, IOException {
        stringLength = nextInt();
        String string = nextToken(false);
        for (int i = 0; i < stringLength; i++) {
            suffixArray[i] = nextInt() - 1;
        }
        for (int i = 0; i < stringLength; i++) {
            invariantSuffixArray[suffixArray[i]] = i;
        }
        int l = 0;
        for (int i = 0; i < stringLength; i++) {
            l = Math.max(0, l - 1);
            int p = invariantSuffixArray[i];
            if (p + 1 < stringLength) {
                try {
                    while (string.charAt(i + l) == string.charAt(suffixArray[p + 1] + l)) {
                        ++l;
                    }
                } catch (Exception e) {
                }
            }
            lcp[p] = l;
        }
        for (int i = 0; i < stringLength - 1; i++) {
            out.print(lcp[i] + " ");
        }
    }
}
