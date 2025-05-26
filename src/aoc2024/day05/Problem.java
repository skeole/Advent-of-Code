package aoc2024.day05;

import java.io.*;
import java.lang.*; // Math, etc.
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static HashMap<Integer, HashSet<Integer>> forwardPairs = new HashMap<>();
    public static HashMap<Integer, HashSet<Integer>> backwardPairs = new HashMap<>();
    public static ArrayList<ArrayList<Integer>> instructions = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> incorrectinstructions = new ArrayList<>();

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\\\aoc2024\\\\day05\\\\problem.txt"));
            while (true) {
                String str = reader.readLine();
                if (str.length() == 0) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(str, "|");

                int first = Integer.parseInt(st.nextToken());
                int second = Integer.parseInt(st.nextToken());

                if (!forwardPairs.containsKey(first)) {
                    forwardPairs.put(first, new HashSet<>());
                }

                if (!backwardPairs.containsKey(second)) {
                    backwardPairs.put(second, new HashSet<>());
                }

                forwardPairs.get(first).add(second);
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

    public static Object partTwo() { // going to assume nice
        int count = 0;
        for (ArrayList<Integer> instruction : incorrectinstructions) {
            HashMap<Integer, Integer> sigh = new HashMap<>();
            for (int i : instruction) {
                if (backwardPairs.containsKey(i)) {
                    for (int j : backwardPairs.get(i)) {
                        if (!sigh.containsKey(j)) {
                            sigh.put(j, 0);
                        }
                        sigh.replace(j, sigh.get(j) + 1);
                    }
                }
            }
            for (int i : instruction) {
                if (sigh.containsKey(i) && sigh.get(i) == (instruction.size() - 1) / 2) {
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