import java.io.*;
import java.util.*;


public class Dictionary implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private final int ALPHABET_CARDINALITY = 26;
    private final int MAX_VERTICES = 100000;
    private final TrieNode vertices[] = new TrieNode[MAX_VERTICES + 1];
    private int size, cnt = 0, dictionaryCardinality;
    private boolean[] result;

    public static void main(String[] args) {
        new Thread(new Dictionary()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("dictionary.in"));
            out = new PrintWriter(new FileWriter("dictionary.out"));
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
        String text = nextToken();
        dictionaryCardinality = nextInt();
        initTrie();
        for (int i = 0; i < dictionaryCardinality; i++) {
            put(nextToken());
        }
        checkSubStrings(text);
        for (int i = 1; i <= dictionaryCardinality; i++) {
            if (result[i]) {
                out.println("Yes");
            } else {
                out.println("No");
            }
        }
    }

    private boolean[] checkSubStrings(String text) {
        int state = 0;
        int i = 0;
        result = new boolean[dictionaryCardinality + 1];
        while (i < text.length()) {
            state = go(state, (char) (text.charAt(i) - 'a' + 1));
            i++;
            if (vertices[state].leaf != 0) {
                result[vertices[state].leaf] = true;
            }
        }
        return result;
    }

    private void initTrie() {
        vertices[0] = new TrieNode();
        vertices[0].p = vertices[0].link = -1;
        Arrays.fill(vertices[0].next, -1);
        Arrays.fill(vertices[0].go, -1);
        size = 1;
    }

    private class TrieNode {
        int[] next = new int[ALPHABET_CARDINALITY + 1];
        int leaf;
        int p;
        char pch;
        int link;
        int[] go = new int[ALPHABET_CARDINALITY + 1];
    }

    public void put(String s) {
        int v = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = (char) (s.charAt(i) - 'a' + 1);
            if (vertices[v] == null) {
                vertices[v] = new TrieNode();
            }
            if (vertices[v].next[c] == -1) {
                vertices[size] = new TrieNode();
                Arrays.fill(vertices[size].next, -1);
                Arrays.fill(vertices[size].go, -1);
                vertices[size].link = -1;
                vertices[size].p = v;
                vertices[size].pch = c;
                vertices[v].next[c] = size++;
            }
            v = vertices[v].next[c];
        }
        vertices[v].leaf = ++cnt;
    }

    public boolean find(String s) {
        return false;
    }

    int getLink(int v) {
        if (vertices[v].link == -1)
            if (v == 0 || vertices[v].p == 0)
                vertices[v].link = 0;
            else
                vertices[v].link = go(getLink(vertices[v].p), vertices[v].pch);
        return vertices[v].link;
    }

    int go(int v, char c) {
        if (vertices[v].leaf != 0) {
            result[vertices[v].leaf] = true;
        }
        if (vertices[v].go[c] == -1)
            if (vertices[v].next[c] != -1)
                vertices[v].go[c] = vertices[v].next[c];
            else
                vertices[v].go[c] = v == 0 ? 0 : go(getLink(v), c);
        return vertices[v].go[c];
    }
}
