package aoc2024.day04;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<String> lines = new ArrayList<>();
    public static ArrayList<String> grid = new ArrayList<>();

    public static void parse() {
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader("src\\\\aoc2024\\\\day04\\\\problem.txt"));
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