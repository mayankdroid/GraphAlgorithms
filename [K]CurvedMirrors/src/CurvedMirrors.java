import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;


public class CurvedMirrors implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final int MAX_BALCONY = 30;
    static long startTime, currentTime;

    private int balcony;
    private final int[] balconyState = new int[MAX_BALCONY];
    private final int[] sortState = new int[MAX_BALCONY];
    private int monstersCount = 0;

    private final HashMap<Integer, Integer> savedStates = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        new Thread(new CurvedMirrors()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("mirror.in"));
            out = new PrintWriter(new FileWriter("mirror.out"));
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
        balcony = nextInt();
        for (int i = 0; i < balcony; i++) {
            balconyState[i] = nextInt();
            monstersCount += balconyState[i];
        }
        out.print(processState());
        // out.print(System.currentTimeMillis() - startTime);
    }

    private int processState() {
        int killedMonsters, left, center, right;
        int currentDamage, minDamage = Integer.MAX_VALUE;
        int enterHash = getCurrentHash();

        for (int i = 0; i < balcony; i++) {
            int a = (i + balcony - 1) % balcony, b = i % balcony, c = (i + 1) % balcony;
            left = balconyState[a];
            center = balconyState[b];
            right = balconyState[c];
            killedMonsters = left + center + right;
            if (center == 0) continue;
            balconyState[a] = 0;
            balconyState[b] = 0;
            balconyState[c] = 0;
            monstersCount -= killedMonsters;

            if (monstersCount != 0) {
                int currentHash = getCurrentHash();
                if (savedStates.containsKey(currentHash)) {
                    currentDamage = monstersCount + savedStates.get(currentHash);
                } else {
                    if (minDamage < monstersCount + F()) {
                        currentDamage = minDamage;
                    } else {
                        currentDamage = monstersCount + processState();
                    }
                }
            } else {
                minDamage = 0;
                balconyState[a] = left;
                balconyState[b] = center;
                balconyState[c] = right;
                monstersCount += killedMonsters;
                break;
            }
            if (minDamage > currentDamage) {
                minDamage = currentDamage;
            }
            currentTime = System.currentTimeMillis();
            if (currentTime - startTime > 400) return minDamage;
            balconyState[a] = left;
            balconyState[b] = center;
            balconyState[c] = right;
            monstersCount += killedMonsters;
        }
        savedStates.put(enterHash, minDamage);
        return minDamage;
    }

    private int F() {
        int j, result = 0;
        for (j = 0; j < balcony; j++) {
            sortState[j] = balconyState[j];
        }
        quickSort(sortState, 0, balcony - 1);
        for (j = balcony - 4; j > 1; j -= 3) {
            if (sortState[j] == 0) break;
            result += sortState[j];
            result += sortState[j - 1];
            result += sortState[j - 2];
        }
        return result;
    }

    private Integer getCurrentHash() {
        int seed = 131313;
        int hash = 0;
        for (int i = 0; i < balcony; i++) {
            hash = (hash * seed) + balconyState[i] + i;
        }
        return hash;
    }

    private int[] quickSort(int[] targetArray, int low, int high) {
        int i = low;
        int j = high;
        int x = targetArray[(low + high) / 2];
        do {
            while (targetArray[i] < x) ++i;
            while (targetArray[j] > x) --j;
            if (i <= j) {
                int temp = targetArray[i];
                targetArray[i] = targetArray[j];
                targetArray[j] = temp;
                i++;
                j--;
            }
        } while (i < j);
        if (low < j) quickSort(targetArray, low, j);
        if (i < high) quickSort(targetArray, i, high);
        return targetArray;
    }
}