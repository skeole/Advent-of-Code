package aoc2024.day08;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static int numrows;
    public static int numcols;

    public static HashMap<Character, ArrayList<Tuple<Integer, Integer>>> antennae = new HashMap<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day08\\problem.txt" : "src\\aoc2024\\day08\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                numcols = str.length();

                for (int i = 0; i < str.length(); i += 1) {
                    if (str.charAt(i) != '.') {
                        if (!antennae.containsKey(str.charAt(i))) {
                            antennae.put(str.charAt(i), new ArrayList<>());
                        }
                        antennae.get(str.charAt(i)).add(new Tuple<>(numrows, i));
                    }
                }
                numrows += 1;
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        int count = 0; // Obviously not all puzzles work this way, but most do... 
        boolean[][] grid = new boolean[numrows][numcols];

        for (ArrayList<Tuple<Integer, Integer>> positions : antennae.values()) {
            for (int i = 0; i < positions.size(); i += 1) {
                for (int j = 0; j < positions.size(); j += 1) {
                    if (i == j) continue;
                    int first = 2 * positions.get(i).getFirst() - positions.get(j).getFirst();
                    int second = 2 * positions.get(i).getSecond() - positions.get(j).getSecond();
                    if (0 <= first && first < numrows && 0 <= second && second < numcols) {
                        if (!grid[first][second]) {
                            count += 1;
                            grid[first][second] = true;
                        }
                    }
                }
            }
        }

        return count;
    }

    public static Object partTwo() {
        int count = 0;
        boolean[][] grid = new boolean[numrows][numcols];

        for (ArrayList<Tuple<Integer, Integer>> positions : antennae.values()) {
            for (int i = 0; i < positions.size(); i += 1) {
                for (int j = 0; j < positions.size(); j += 1) {
                    if (i == j) continue;
                    int first = positions.get(i).getFirst();
                    int second = positions.get(i).getSecond();
                    int deltafirst = positions.get(i).getFirst() - positions.get(j).getFirst();
                    int deltasecond = positions.get(i).getSecond() - positions.get(j).getSecond();
                    while (0 <= first && first < numrows && 0 <= second && second < numcols) {
                        if (!grid[first][second]) {
                            count += 1;
                            grid[first][second] = true;
                        }
                        first += deltafirst;
                        second += deltasecond;
                    }
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
        double firstTime = endTime - startTime;
        println("Time taken to compute: " + round(firstTime, 2) + " ms");
        println("-----------------------");
        System.out.print("Part two answer: ");
        startTime = System.nanoTime() / 1000000.0;
        answer = partTwo();
        endTime = System.nanoTime() / 1000000.0;
        println(answer);
        double secondTime = endTime - startTime;
        println("Time taken to compute: " + round(secondTime, 2) + " ms");

        if (actual) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("src\\\\aoc2024\\\\stats.txt"));
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < 8; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 8:  Part 1 : ");
                output.append(
                    firstTime >= 99999.5 ? "" + round(firstTime / 1000.0, 3) + " s" : 
                    firstTime >= 9999.5 ? " " + round(firstTime / 1000.0, 3) + " s" : 
                    firstTime >= 999.5 ? "  " + round(firstTime / 1000.0, 3) + " s" : 
                    firstTime >= 99.5 ? "" + round(firstTime, 2) + " ms" : 
                    firstTime >= 9.5 ? " " + round(firstTime, 2) + " ms" : 
                    "  " + round(firstTime, 2) + " ms"
                );
                output.append(" | Part 2 : ");
                output.append(
                    secondTime >= 99999.5 ? "" + round(secondTime / 1000.0, 3) + " s" : 
                    secondTime >= 9999.5 ? " " + round(secondTime / 1000.0, 3) + " s" : 
                    secondTime >= 999.5 ? "  " + round(secondTime / 1000.0, 3) + " s" : 
                    secondTime >= 99.5 ? "" + round(secondTime, 2) + " ms" : 
                    secondTime >= 9.5 ? " " + round(secondTime, 2) + " ms" : 
                    "  " + round(secondTime, 2) + " ms"
                );
                output.append("\n");
                for (int i = 9; i <= 25; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter("src\\\\aoc2024\\\\stats.txt"));
                bw.write(output.toString());
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}