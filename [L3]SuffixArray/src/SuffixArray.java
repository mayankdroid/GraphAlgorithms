import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;


public class SuffixArray implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final int[] rank = new int[100000];
    private final Long[] rank2 = new Long[100000];

    public static void main(String[] args) {
        new Thread(new SuffixArray()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("suffarray.in"));
            out = new PrintWriter(new FileWriter("suffarray.out"));
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
        return st.nextToken("");
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
        CharSequence string = nextToken();
        Integer[] suffixArray = suffixArray(string);
        for (int i = 0; i < string.length(); i++) {
            out.print(suffixArray[i] + " ");
        }
    }

    private Integer[] suffixArray(CharSequence s) {
        int n = s.length();
        Integer[] sa = new Integer[n];
        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = s.charAt(i);
        }
        for (int len = 1; len < n; len *= 2) {
            for (int i = 0; i < n; i++)
                rank2[i] = ((long) rank[i] << 32) + (i + len < n ? rank[i + len] + 1 : 0);

            Arrays.sort(sa, new RankComparator());

            for (int i = 0; i < n; i++)
                rank[sa[i]] = i > 0 && rank2[sa[i - 1]] == rank2[sa[i]] ? rank[sa[i - 1]] : i;
        }
        return sa;
    }

    private class RankComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            if (rank2[a] == null || rank2[b] == null)
                return 0;
            if (rank2[a] > rank2[b])
                return 1;
            if (rank2[b] > rank2[a])
                return -1;
            return 0;
        }
    }
}
