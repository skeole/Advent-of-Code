package aoc2024.day05;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static HashMap<Integer, HashSet<Integer>> backwardPairs = new HashMap<>();
    public static ArrayList<ArrayList<Integer>> instructions = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> incorrectinstructions = new ArrayList<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day05\\problem.txt" : "src\\aoc2024\\day05\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL)); // rip
            while (true) {
                String str = reader.readLine();
                if (str.length() == 0) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(str, "|");

                int first = Integer.parseInt(st.nextToken());
                int second = Integer.parseInt(st.nextToken());

                if (!backwardPairs.containsKey(second)) {
                    backwardPairs.put(second, new HashSet<>());
                }

                backwardPairs.get(second).add(first);
            }

            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }

                instructions.add(new ArrayList<>());
                StringTokenizer st = new StringTokenizer(str, ",");

                while (st.hasMoreTokens()) {
                    instructions.get(instructions.size() - 1).add(Integer.parseInt(st.nextToken()));
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        int count = 0;

        for (ArrayList<Integer> instruction : instructions) {
            HashSet<Integer> found = new HashSet<>();
            HashSet<Integer> betterNotFind = new HashSet<>();
            boolean works = true;
            for (int i : instruction) {
                if (betterNotFind.contains(i)) {
                    works = false;
                }
                found.add(i);
                if (backwardPairs.containsKey(i)) {
                    for (int j : backwardPairs.get(i)) {
                        if (!found.contains(j)) {
                            betterNotFind.add(j);
                        }
                    }
                }
            }
            if (works) {
                count += instruction.get((instruction.size() - 1) / 2);
            } else {
                incorrectinstructions.add(instruction);
            }
        }

        return count;
    }

    public static Object partTwo() { // advent of code makes this nice
        int count = 0;
        for (ArrayList<Integer> instruction : incorrectinstructions) {
            HashMap<Integer, Integer> dependencyCounts = new HashMap<>();
            for (int i : instruction) {
                if (backwardPairs.containsKey(i)) {
                    for (int j : backwardPairs.get(i)) {
                        if (!dependencyCounts.containsKey(j)) {
                            dependencyCounts.put(j, 0);
                        }
                        dependencyCounts.replace(j, dependencyCounts.get(j) + 1);
                    }
                }
            }
            for (int i : instruction) {
                if (dependencyCounts.containsKey(i) && dependencyCounts.get(i) == (instruction.size() - 1) / 2) {
                    count += i;
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
                for (int i = 0; i < 5; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 5:  Part 1 : ");
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
                for (int i = 6; i <= 25; i += 1) {
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