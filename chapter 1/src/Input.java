import java.io.*;
import java.util.*;

public class Input {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("diet.in"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("diet_data.txt"));
//        for (int i = 0; i < 20; i++) {
//            String s = br.readLine() + br.readLine();
//            s = s.replace(" ", "");
//            s = s.replace("\"", "");
//            bw.write(s + "\n");
//        }
//        String[] f = new String[20];
//        bw.write("A\n");
//        for (int i = 0; i < 20; i++) f[i] = br.readLine();
//        for (int i = 0; i < 20; i++) bw.write(f[i] + "," + br.readLine() + "\n");
//
//        br.readLine();  // empty line for formatting
//        bw.write("v\n");
//        String s = "";
//        for (int i = 0; i < 5; i++) s += br.readLine();
//        s = s.replaceAll("\",\",|[ \"`]", "");
//        bw.write(s + "\n");
//
//        br.readLine();  // empty line for formatting
//        bw.write("v2\n");  // these are here for human readability
//        s = "";
//        for (int i = 0; i < 4; i++) s += br.readLine();
//        s = s.replaceAll("\",\",|[ \"`]", "");
//        bw.write(s + "\n");

//        int n = 20;
//        for (int i = 0; i < n; i++){
//            String[] s = br.readLine().split(" / ");
//            double d = Double.parseDouble(s[0]);
//            if (s.length > 1) d /= Double.parseDouble(s[1]);
//            bw.write(d + ",");
//        }
//        bw.write("\n");
//        String unit = br.readLine() + br.readLine();
//        unit = unit.replace(" ", "");
//        bw.write(unit + "\n");

        String[] s = new String[417];
        for (int i = 0; i < s.length; i++) s[i] = br.readLine();
        br.readLine();
        for (int i = 0; i < s.length; i++) {
            String t = br.readLine();
            if (!s[i].equals(t)) System.out.println(s[i] + " vs " + t);
        }

        br.close();
        bw.close();
    }
}
