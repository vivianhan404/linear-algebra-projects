import java.util.Arrays;

public class LinSolver {
    // the matrix (in is original mat for verification purposes)
    private final double[][] in, m;
    private final boolean[] free;
    private final int[] lead;  // equation number, which column has the leading 1
    private boolean showSteps = false;
    private double maxLim = 1e10;
    private boolean round = false;

    public LinSolver(double[][] m, boolean showSteps) {
        this.m = copy(m);
        in = copy(m);
        free = new boolean[m.length];
        lead = new int[m.length];
        for (int i = 0; i < m.length; i++) {
            lead[i] = m.length;
        }
        this.showSteps = showSteps;
    }

    public boolean solve() {
        int opCnt = 1;
        int v = 0;  // the variable we're trying to create a pivot for
        for (int i = 0; i < m.length; i++) {
            // j: scan for a row with non-zero v coef
            int j = i;
            while (j < m.length && round(m[j][v]) == 0) j++;
            if (j == m.length) {  // if all zeros, v is free; increment v (at end of if else), same row
                free[v] = true;
                i--;
            } else {  // found nonzero v coef!
                switchRow(i, j);  // switch the rows to maintain echelon form
                printOp(opCnt++ + ": switch " + (i + 1) + " and " + (j + 1));
                lead[i] = v;  // variable v is the pivot for this row
                printOp(opCnt++ + ": " + round(1 / m[i][v]) + " * R" + (i + 1) + " --> R" + (i + 1));
                mult(i, 1 / m[i][v]);  // make sure the leading entry is 1
                for (j = 0; j < m.length; j++) {
                    if (m[j][v] != 0 && i != j) {
                        printOp(opCnt++ + ": R" + (j + 1) + " + " +
                                round(-m[j][v]) + " * R" + (i + 1) + " --> R" + (j + 1));
                        add(j, i, -m[j][v]);  // remove v from all other equations (make coef 0)
                        if (!consistent(j)) return false;
                    }
                }
            }
            v++;
        }
        opCnt--;
        System.out.println("Total number of operations: " + opCnt);
        return true;  // has proper solution!
    }

    // TODO: check out print function
    public boolean consistent(int r) {
        boolean allZero = true;
        for (int i = 0; i < m[r].length - 1 && allZero; i++) {
            allZero = round(m[r][i]) == 0;
        }
        if (allZero && round(m[r][m.length]) != 0) {
            printOp("R" + (r + 1) + ": 0 = " + round(m[r][m.length]));
            System.out.println(" This system of equations cannot be solved");
            return false;
        }
        return true;
    }

    public double[] getCol(int c) {
        double[] col = new double[m.length];
        for (int i = 0; i < m.length; i++) col[i] = m[i][c];
        return col;
    }

    public double[] getCol() {
        return getCol(m[0].length - 1);
    }

    public double round(double d) {
        double r = (int)(d * 100 + 0.5);
        return r / 100;
    }

    public void switchRow(int a, int b) {
        if (a == b) return;
        double[] temp = m[a];
        m[a] = m[b];
        m[b] = temp;
    }

    public void mult(int r, double factor) {
        if (Math.abs(factor) > maxLim) System.out.println(factor);
        for (int i = 0; i < m[r].length; i++) {
            m[r][i] = m[r][i] * factor;
            if (Math.abs(m[r][i]) > maxLim) System.out.println(m[r][i]);
        }
        if (round) m[r][m[0].length - 1] = round(m[r][m[0].length - 1]);
    }

    public void setRound() { round = true; }

    // row a += factor * row b
    public void add(int a, int b, double factor) {
        if (Math.abs(factor) > maxLim) System.out.println(factor);
        for (int i = 0; i < m[a].length; i++){
            m[a][i] += m[b][i] * factor;
            if (Math.abs(m[a][i]) > maxLim) System.out.println(m[a][i]);
            if (Math.abs(m[b][i]) > maxLim) System.out.println(m[b][i]);
        }
        if (round) m[a][m[0].length - 1] = round(m[a][m[0].length - 1]);
    }

    public void verify() {
        double[] val = new double[m.length + 1];
        for (int i = 0; i < m.length; i++) {
            if (free[i]) val[i] = 10;  // assign all free vars to random value
        }
        // find values for all other variables
        for (int r = m.length - 1; r >= 0; r--) {
            for (int c = lead[r] + 1; c < m.length; c++) {
                val[lead[r]] -= m[r][c] * val[c];
            }
            val[lead[r]] += m[r][m.length];
        }
        // plug and chug into original equations
        for (int r = 0; r < in.length; r++) {
            double sum = 0;
            for (int c = 0; c < m.length; c++) {
                sum += val[c] * in[r][c];
            }
            if (round(sum - in[r][m.length]) != 0) {
                System.out.println("I'm sorry, something went wrong with equation " + (r + 1) + ".\n" +
                        "check " + sum + " vs " + in[r][m.length]);
            }
        }
    }

    public void printSol(String[] food, String[] unit, double[] size) {
        for (int i = 0; i < lead.length; i++) {
            if (lead[i] == m.length) break;
            int type = lead[i];
//            System.out.println(round(m[i][m[i].length - 1]));
            double val = round(m[i][m[i].length - 1] * 100 / size[type]);
            if (food[type].equals("")) System.out.println(val + " " + unit[type]);
            else System.out.println(val + " " + unit[type] + " of " + food[type]);
        }
        for (int i = 0; i < free.length; i++) {
            if (free[i]) System.out.println("any amount of " + food[i]);
        }
    }

    public void printOp(String s) {
        if (showSteps) System.out.println(s);
    }

    public double[][] copy(double[][] a) {
        double[][] b = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) b[i][j] = a[i][j];
        }
        return b;
    }
}
