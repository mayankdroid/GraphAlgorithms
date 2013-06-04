import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


public class JapaneseComputer implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    public static void main(String[] args) {
        new Thread(new JapaneseComputer()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("computer.in"));
            out = new PrintWriter(new FileWriter("computer.out"));
            solve();
        } catch (Exception e) {
            if (e.getMessage() != "FE") {
                e.printStackTrace();
                System.exit(9000);
            }
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

    final private int MAX_NUMBER = 42;

    private int number, neededNumbers;
    private boolean[] numbers = new boolean[MAX_NUMBER + 1];
    private boolean[] knownNumbers = new boolean[MAX_NUMBER + 1];
    private int[] calculatedNumbers = new int[MAX_NUMBER + 1];
    private String[] result = new String[MAX_NUMBER + 1];
    int cnt = 0, currentDepth;

    void solve() throws NumberFormatException, IOException {
        neededNumbers = number = nextInt();
        for (int i = 0; i < number; i++) {
            numbers[nextInt()] = true;
        }
        knownNumbers[1] = true;
        calculatedNumbers[cnt] = 1;
        cnt++;
        for (currentDepth = number; currentDepth < MAX_NUMBER; currentDepth++) {
            processState(currentDepth);
            Arrays.fill(knownNumbers, 2, MAX_NUMBER, false);
            Arrays.fill(calculatedNumbers, 1, MAX_NUMBER, 0);
        }
    }


    void processState(int depth) {
        boolean[] usedNumbers = new boolean[MAX_NUMBER + 1];
        if (depth == 0) {
            if (neededNumbers == 0) {
                out.println(cnt - 1);
                for (int i = 1; i < cnt; i++) {
                    out.println(result[cnt - i]);
                }
                out.flush();
                out.close();
                System.exit(0);
            }
            return;
        } else {
            if (neededNumbers > depth) {
                return;
            }
        }
        for (int i = 0; i < cnt; i++) {
            int k = 1, val = calculatedNumbers[i] << k;
            while (val < MAX_NUMBER) {
                if (!usedNumbers[val]) {
                    usedNumbers[val] = true;
                    result[depth] = calculatedNumbers[i] + "<<" + k;
                    addValue(val, depth);
                }
                val = calculatedNumbers[i] << ++k;
            }
            for (k = 0; k < cnt; k++) {
                val = calculatedNumbers[i] + calculatedNumbers[k];
                if (val > MAX_NUMBER || knownNumbers[val]) continue;
                if (usedNumbers[val]) {
                    continue;
                } else {
                    usedNumbers[val] = true;
                }
                result[depth] = calculatedNumbers[i] + "+" + calculatedNumbers[k];
                addValue(val, depth);
            }
            for (k = 0; k < cnt; k++) {
                if (calculatedNumbers[i] >= calculatedNumbers[k]) {
                    val = calculatedNumbers[i] - calculatedNumbers[k];
                    if (val == 0) continue;
                    if (usedNumbers[val]) {
                        continue;
                    } else {
                        usedNumbers[val] = true;
                    }
                    result[depth] = calculatedNumbers[i] + "-" + calculatedNumbers[k];
                    addValue(val, depth);
                } else {
                    val = calculatedNumbers[k] - calculatedNumbers[i];
                    if (usedNumbers[val]) {
                        continue;
                    } else {
                        usedNumbers[val] = true;
                    }
                    result[depth] = calculatedNumbers[k] + "-" + calculatedNumbers[i];
                    addValue(val, depth);
                }

            }
        }
    }

    private void addValue(int val, int depth) {
        if (val > MAX_NUMBER || knownNumbers[val]) {
            return;
        }
        calculatedNumbers[cnt] = val;
        cnt++;
        knownNumbers[val] = true;
        if (numbers[val]) neededNumbers--;
        processState(depth - 1);
        cnt--;
        knownNumbers[val] = false;
        if (numbers[val]) neededNumbers++;
    }
}
