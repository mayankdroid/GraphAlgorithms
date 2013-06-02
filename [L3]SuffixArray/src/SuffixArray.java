import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class SuffixArray implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final int MAX_LENGTH = 100000;
    private final int ALPHABET_HIGH = 127;
    private final int ALPHABET_LOW = 10;

    private final int[] permutation = new int[MAX_LENGTH + 1];
    private final int[] nPermutation = new int[MAX_LENGTH + 1];
    private final int[] counter = new int[MAX_LENGTH + 1];
    private final int[] nEquivalenceClasses = new int[MAX_LENGTH + 1];
    private int[] equivalenceClasses = new int[MAX_LENGTH + 1];

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
            String line = in.readLine();
            if (line == null) return "";
            st = new StringTokenizer(line);
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
        StringBuilder string = new StringBuilder(nextToken());
        string.append('\n');
        int[] suffixArray = suffixArray(string.toString());
        for (int i = 1; i < string.length(); i++) {
            out.print(suffixArray[i] + 1 + " ");
        }
    }

    private int[] suffixArray(String s) {
        int strLen = s.length();
        for (int i = 0; i < strLen; ++i)
            ++counter[s.charAt(i)];
        for (int i = ALPHABET_LOW; i <= ALPHABET_HIGH; ++i)
            counter[i] += counter[i - 1];
        for (int i = 0; i < strLen; ++i)
            permutation[--counter[s.charAt(i)]] = i;
        equivalenceClasses[permutation[0]] = 0;
        int classes = 1;
        for (int i = 1; i < strLen; ++i) {
            if (s.charAt(permutation[i]) != s.charAt(permutation[i - 1])) {
                ++classes;
            }
            equivalenceClasses[permutation[i]] = classes - 1;
        }
        for (int h = 0; (1 << h) < strLen; ++h) {
            for (int i = 0; i < strLen; ++i) {
                nPermutation[i] = permutation[i] - (1 << h);
                if (nPermutation[i] < 0) nPermutation[i] += strLen;
            }
            Arrays.fill(counter, 0, classes, 0);
            for (int i = 0; i < strLen; ++i)
                ++counter[equivalenceClasses[nPermutation[i]]];
            for (int i = 1; i < classes; ++i)
                counter[i] += counter[i - 1];
            for (int i = strLen - 1; i >= 0; --i)
                permutation[--counter[equivalenceClasses[nPermutation[i]]]] = nPermutation[i];
            nEquivalenceClasses[permutation[0]] = 0;
            classes = 1;
            for (int i = 1; i < strLen; ++i) {
                int mid1 = (permutation[i] + (1 << h)) % strLen, mid2 = (permutation[i - 1] + (1 << h)) % strLen;
                if (equivalenceClasses[permutation[i]] != equivalenceClasses[permutation[i - 1]] || equivalenceClasses[mid1] != equivalenceClasses[mid2])
                    ++classes;
                nEquivalenceClasses[permutation[i]] = classes - 1;
            }
            equivalenceClasses = Arrays.copyOf(nEquivalenceClasses, strLen);
        }
        return permutation;
    }
}
