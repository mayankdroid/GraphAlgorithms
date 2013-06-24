import java.io.*;
import java.util.*;


public class Substrings implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_LENGTH = 100000;
    final int MAX_WORDS = 10;
    final long p = 3571;

    String[] strings = new String[MAX_WORDS];
    final long[][] hashStrings = new long[MAX_WORDS + 1][MAX_LENGTH + 1];
    final long[] degrees = new long[MAX_LENGTH + 1];
    int counter, size, last;

    public static void main(String[] args) {
        new Thread(new Substrings()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("substr3.in"));
            out = new PrintWriter(new FileWriter("substr3.out"));
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

    private int minLength = MAX_LENGTH;
    private int minWord = 0;
    private HashMap<Long, Integer> result = new HashMap<Long, Integer>();
    private HashMap<Long, Integer> temp = new HashMap<Long, Integer>();

    void solve() throws NumberFormatException, IOException {
        int length = 0, words = nextInt();
        int resSS = 0;
        degrees[0] = 1;
        for (int i = 0; i < words; i++) {
            strings[i] = nextToken();
            if (minLength > strings[i].length()) {
                minLength = strings[i].length();
                minWord = i;
            }
            addStringPrefixHashes(i);
        }
        int curLength;
        long hash = 0, temp_hash;
        int a = 0, b = minLength;
        while (true) {
            if (b == a) break;
            curLength = (b + a + 1) / 2;
            int j = 0, i;
            for (i = 0; i < minLength - curLength + 1; i++) {
                hash = getHash(minWord, i, i + curLength);
                result.put(hash, i);
            }
            for (j = 0; j < words; j++) {
                if (j == minWord) continue;
                for (i = 0; i < strings[j].length() - curLength + 1; i++) {
                    hash = getHash(j, i, i + curLength);
                    temp.put(hash, i);
                }
                Iterator index = result.keySet().iterator();
                while (index.hasNext()) {
                    temp_hash = (Long) index.next();
                    if (!temp.containsKey(temp_hash)) {
                        index.remove();
                    } else {
                        hash = temp_hash;
                    }
                }
                if (result.isEmpty()) {
                    break;
                }
                temp.clear();
            }

            if (j == words) {
                a = curLength;
                resSS = result.get(hash);
            } else {
                b = curLength - 1;
            }
            result.clear();
        }
        out.print(strings[minWord].substring(resSS, resSS + a));
    }

    private void addStringPrefixHashes(int word) {
        degrees[0] = 1;
        hashStrings[word][0] = 0;
        int strLen = strings[word].length();
        for (int i = 0; i < strLen; i++) {
            if (degrees[i + 1] == 0) degrees[i + 1] = degrees[i] * p;
            hashStrings[word][i + 1] = hashStrings[word][i] * p + strings[word].charAt(i);
        }
    }

    long getHash(int nString, int l, int r) {
        return hashStrings[nString][r] - hashStrings[nString][l] * degrees[r - l];
    }
}
