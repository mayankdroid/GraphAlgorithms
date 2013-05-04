import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Function implements Runnable {
    StringTokenizer st;
    BufferedReader in;
    PrintWriter out;

    final BigInteger MOD = BigInteger.valueOf(1l << 32);
    HashMap<Long, BigInteger> result = new HashMap<Long, BigInteger>(100000);
    final BigInteger ZERO = BigInteger.valueOf(0);
    final BigInteger ONE = BigInteger.valueOf(1);

    public static void main(String[] args) {
        new Thread(new Function()).start();
    }

    public void run() {
        try {
            in = new BufferedReader(new FileReader("function.in"));
            out = new PrintWriter(new FileWriter("function.out"));
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
        long arg = nextLong();
        //for(long arg = 1; arg <= 100000; arg++) {
        BigInteger res = function(arg);
        out.println(res.mod(MOD));
        //}
    }

    private BigInteger function(long arg) {
        if (arg <= 2) return ONE;
        if (arg % 2 == 0) {
            BigInteger tempA = calculate(arg - 1);
            BigInteger tempB = calculate(arg - 3);
            return tempA.add(tempB);
        }
        if (arg % 2 == 1) {
            long a = (long) Math.floor(6 * (double) arg / 7);
            long b = (long) Math.floor(2 * (double) arg / 3);
            BigInteger tempA = calculate(a);
            BigInteger tempB = calculate(b);
            return tempA.add(tempB);
        }
        return ZERO;
    }

    private BigInteger calculate(long a) {
        BigInteger resA;
        if (result.containsKey(a)) {
            resA = result.get(a);
        } else {
            resA = function(a);
            result.put(a, resA);
        }
        return resA;
    }
}
