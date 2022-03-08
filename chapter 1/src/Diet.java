import java.io.*;
import java.util.Random;

public class Diet {

    static boolean showSteps = false;

    public static void main(String[] args) throws IOException {
        // read in data
        BufferedReader br = new BufferedReader(new FileReader("diet.csv"));
        double[][] m = new double[20][21];

        br.readLine(); // "A"
        for (int i = 0; i < m.length; i++) {
            String[] line = br.readLine().split(",");
            for (int j = 0; j < m[i].length - 1; j++) m[i][j] = Double.parseDouble(line[j]);
        }

        br.readLine(); // "servings
        String[] food = br.readLine().split(",");
        String[] line = br.readLine().split(",");
        double[] size = new double[m.length];  // serving size
        for (int i = 0; i < m.length; i++) size[i] = Double.parseDouble(line[i]);
        String[] unit = br.readLine().split(",");

        for (int i = 0; i < 2; i++) {
            System.out.println(br.readLine());  // "v" or "v2"
            line = br.readLine().split(",");
            for (int j = 0; j < m.length; j++) m[j][20] = Double.parseDouble(line[j]);
            print(m);
//            LinSolver ls = new LinSolver(m, i == 1);
            LinSolver ls = new LinSolver(m, i == 1);
//            ls.setRound();
            ls.solve();
            ls.verify();
            System.out.println("result: ");
            ls.printSol(food, unit, size);
            System.out.println();
        }
        br.close();

    }

    public static void print(double[][] m) {
        for (double[] a: m) {
            for (double i : a) System.out.print(i + " ");
            System.out.println();
        }
    }
}

