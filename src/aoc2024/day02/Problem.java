package aoc2024.day02;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<ArrayList<Integer>> grid = new ArrayList<>();

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\aoc2024\\day02\\problem.txt")); // rip
            StringTokenizer st;

            while (true) {
                String str = reader.readLine();

                if (str == null) {
                    break;
                }

                grid.add(new ArrayList<>());
                st = new StringTokenizer(str);

                while (st.hasMoreTokens()) {
                    grid.get(grid.size() - 1).add(Integer.parseInt(st.nextToken()));
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static int convert(int i, int ignore) {
        if (ignore == -1) {
            return i;
        } else {
            return i < ignore ? i : i + 1;
        }
    }

    public static boolean works(ArrayList<Integer> row, int ignore) {
        if (row.get(convert(0, ignore)) == row.get(convert(1, ignore))) {
            return false;
        }
        int number = ignore == -1 ? row.size() - 1 : row.size() - 2;
        boolean increasing = row.get(convert(1, ignore)) > row.get(convert(0, ignore));
        for (int i = 0; i < number; i += 1) {
            int difference = row.get(convert(i + 1, ignore)) - row.get(convert(i, ignore));
            if (difference > 3 || difference < -3 || difference == 0 || difference > 0 != increasing) {
                return false;
            }
        }
        return true;
    }

    public static Object partOne() {
        int count = 0;
        
        for (ArrayList<Integer> row : grid) {
            if (works(row, -1)) {
                count += 1;
            }
        }

        return count;
    }

    public static Object partTwo() { // answer: 459
        int count = 0;
        
        for (ArrayList<Integer> row : grid) {
            for (int i = 0; i < row.size(); i += 1) {
                if (works(row, i)) {
                    count += 1;
                    break; // sigh
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        double startTime = System.nanoTime() / 1000000.0;
        parse();
        double endTime = System.nanoTime() / 1000000.0;
        println("Finished parsing in " + round(endTime - startTime, 2) + " ms");
        println("-----------------------");
        System.out.print("Part one answer: ");
        startTime = System.nanoTime() / 1000000.0;
        Object answer = partOne();
        endTime = System.nanoTime() / 1000000.0;
        println(answer);
        println("Time taken to compute: " + round(endTime - startTime, 2) + " ms");
        println("-----------------------");
        System.out.print("Part two answer: ");
        startTime = System.nanoTime() / 1000000.0;
        answer = partTwo();
        endTime = System.nanoTime() / 1000000.0;
        println(answer);
        println("Time taken to compute: " + round(endTime - startTime, 2) + " ms");
    }
}
