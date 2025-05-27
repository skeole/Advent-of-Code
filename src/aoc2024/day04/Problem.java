package aoc2024.day04;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<String> lines = new ArrayList<>();
    public static ArrayList<String> grid = new ArrayList<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day04\\problem.txt" : "src\\aoc2024\\day04\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL)); // rip
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                grid.add(str);
            }
            reader.close();

            for (int i = 0; i < grid.size(); i += 1) {
                lines.add(grid.get(i));
            }
            for (int i = 0; i < grid.get(0).length(); i += 1) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < grid.size(); j += 1) {
                    sb.append(grid.get(j).charAt(i));
                }
                lines.add(sb.toString());
            }
            for (int i = 0; i < grid.size() + grid.get(0).length() - 1; i += 1) {
                StringBuilder sb = new StringBuilder();
                for (int j = Math.min(i, grid.size() - 1); j >= Math.max(i - grid.get(0).length() + 1, 0); j -= 1) {
                    sb.append(grid.get(j).charAt(i - j));
                }
                lines.add(sb.toString());
            }
            for (int i = 0; i < grid.size() + grid.get(0).length() - 1; i += 1) {
                StringBuilder sb = new StringBuilder();
                for (int j = Math.min(i, grid.size() - 1); j >= Math.max(i - grid.get(0).length() + 1, 0); j -= 1) {
                    sb.append(grid.get(grid.size() - 1 - j).charAt(i - j));
                }
                lines.add(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        int count = 0;
        for (String s : lines) {
            count += countSubstringOccurences("XMAS", s).size() + countSubstringOccurences("SAMX", s).size();
        }
        return count;
    }

    public static Object partTwo() {
        int count = 0;
        for (int i = 1; i < grid.size() - 1; i += 1) {
            for (int j = 1; j < grid.get(0).length() - 1; j += 1) {
                if (grid.get(i).charAt(j) == 'A') {
                    if ((
                            grid.get(i - 1).charAt(j + 1) == 'M' && grid.get(i + 1).charAt(j - 1) == 'S' || 
                            grid.get(i - 1).charAt(j + 1) == 'S' && grid.get(i + 1).charAt(j - 1) == 'M'
                        ) && (
                            grid.get(i - 1).charAt(j - 1) == 'M' && grid.get(i + 1).charAt(j + 1) == 'S' || 
                            grid.get(i - 1).charAt(j - 1) == 'S' && grid.get(i + 1).charAt(j + 1) == 'M'
                        )) {
                            count += 1;
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
                for (int i = 0; i < 4; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 4:  Part 1 : ");
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
                for (int i = 5; i <= 25; i += 1) {
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