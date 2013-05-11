import java.io.*;
import java.util.StringTokenizer;


public class CurvedMirrors implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    private int balcony;
    private int[] balconyState = new int[31];
    private int monstersCount = 0;

    public static void main(String[] args) {
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
        for (int i = 1; i <= balcony; i++) {
            balconyState[i] = nextInt();
            monstersCount += balconyState[i];
        }
        out.print(processState(balconyState));
    }

    private int processState(int[] balconyState) {
        if (monstersCount == 0) return 0;
        int killedMonsters, maxKilledMonsters = balconyState[balcony] + balconyState[1] + balconyState[2];
        int[] shot = new int[]{balcony, 1, 2};
        for (int i = 2; i < balcony; i++) {
            killedMonsters = balconyState[i - 1] + balconyState[i] + balconyState[i + 1];
            if (maxKilledMonsters < killedMonsters) {
                maxKilledMonsters = killedMonsters;
                shot[0] = i - 1;
                shot[1] = i;
                shot[2] = i + 1;
            }
        }
        killedMonsters = balconyState[balcony - 1] + balconyState[balcony] + balconyState[1];
        if (maxKilledMonsters < killedMonsters) {
            maxKilledMonsters = killedMonsters;
            shot[0] = balcony - 1;
            shot[1] = balcony;
            shot[2] = 1;
        }
        balconyState[shot[0]] = 0;
        balconyState[shot[1]] = 0;
        balconyState[shot[2]] = 0;
        monstersCount -= maxKilledMonsters;
        int damage = monstersCount;
        return damage + processState(balconyState);
    }
}
