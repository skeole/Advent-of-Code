package aoc2024.day06;

import java.io.*;
import java.util.*;

import utility.UsefulFunctions.Tuple;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<ArrayList<Boolean>> grid = new ArrayList<>();
    public static Tuple<Integer, Integer> startingPoint;

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day06\\problem.txt" : "src\\aoc2024\\day06\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL)); // rip
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                grid.add(new ArrayList<>());
                for (int i = 0; i < str.length(); i += 1) {
                    switch (str.charAt(i)) {
                        case '#' -> grid.get(grid.size() - 1).add(false);
                        case '.' -> grid.get(grid.size() - 1).add(true);
                        case '^' -> {
                            grid.get(grid.size() - 1).add(true); // I love switch expressions
                            startingPoint = new Tuple<Integer, Integer>(grid.size() - 1, i);
                        }
                        default -> { throw new IllegalArgumentException(); }
                    };
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static LinkedList<Tuple<Integer, Integer>> validPositions = new LinkedList<>();

    public static Object partOne() {
        boolean[][] visitedSquares = new boolean[grid.size()][grid.get(0).size()];
        int direction = 3; // 1 -> down, 2 -> left, 3 -> up, 0 -> right
        int count = 0; // Obviously not all puzzles work this way, but most do...
        Tuple<Integer, Integer> currentPosition = new Tuple<>(startingPoint.getFirst(), startingPoint.getSecond());
        while (true) {
            if (!visitedSquares[currentPosition.getFirst()][currentPosition.getSecond()]) {
                count += 1;
                visitedSquares[currentPosition.getFirst()][currentPosition.getSecond()] = true;
                if (count != 1) { // assures we don't add the start position and that we only add once
                    validPositions.add(currentPosition);
                }
            }
            Tuple<Integer, Integer> peek = switch (direction) {
                case 0 -> new Tuple<>(currentPosition.getFirst(), currentPosition.getSecond() + 1);
                case 1 -> new Tuple<>(currentPosition.getFirst() + 1, currentPosition.getSecond());
                case 2 -> new Tuple<>(currentPosition.getFirst(), currentPosition.getSecond() - 1);
                case 3 -> new Tuple<>(currentPosition.getFirst() - 1, currentPosition.getSecond());
                default -> { throw new IllegalArgumentException(); }
            };
            if (peek.getFirst() < 0 || peek.getSecond() < 0 || peek.getFirst() >= visitedSquares.length || peek.getSecond() >= visitedSquares[0].length) {
                return count;
            }
            if (!grid.get(peek.getFirst()).get(peek.getSecond())) {
                direction = (direction + 1) % 4;
            } else {
                currentPosition = peek;
            }
        }
    }

    public static Tuple<Tuple<Integer, Integer>, Integer> move(Tuple<Tuple<Integer, Integer>, Integer> current) {
        Tuple<Integer, Integer> peek = switch (current.getSecond()) {
            case 0 -> new Tuple<>(current.getFirst().getFirst(), current.getFirst().getSecond() + 1);
            case 1 -> new Tuple<>(current.getFirst().getFirst() + 1, current.getFirst().getSecond());
            case 2 -> new Tuple<>(current.getFirst().getFirst(), current.getFirst().getSecond() - 1);
            case 3 -> new Tuple<>(current.getFirst().getFirst() - 1, current.getFirst().getSecond());
            default -> { throw new IllegalArgumentException(); }
        };
        if (peek.getFirst() < 0 || peek.getSecond() < 0 || peek.getFirst() >= grid.size() || peek.getSecond() >= grid.get(0).size()) {
            return null;
        }
        if (!grid.get(peek.getFirst()).get(peek.getSecond())) {
            return new Tuple<>(current.getFirst().copy(), (current.getSecond() + 1) % 4);
        } else {
            return new Tuple<>(peek, current.getSecond());
        }

    }

    public static boolean loops() {
        Tuple<Tuple<Integer, Integer>, Integer> currentFirst = new Tuple<>(new Tuple<>(startingPoint.getFirst(), startingPoint.getSecond()), 3);
        Tuple<Tuple<Integer, Integer>, Integer> currentSecond = new Tuple<>(new Tuple<>(startingPoint.getFirst(), startingPoint.getSecond()), 3);
        while (true) {
            currentFirst = move(currentFirst);
            currentSecond = move(currentSecond);
            if (currentSecond == null) {
                return false;
            }
            currentSecond = move(currentSecond);
            if (currentSecond == null) {
                return false;
            }
            if (currentFirst.equals(currentSecond)) {
                return true;
            }
        }
    }

    public static Object partTwo() {
        int count = 0;

        for (Tuple<Integer, Integer> toBlock : validPositions) {
            grid.get(toBlock.getFirst()).set(toBlock.getSecond(), false);
            if (loops()) {
                count += 1;
            }
            grid.get(toBlock.getFirst()).set(toBlock.getSecond(), true);
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
                for (int i = 0; i < 6; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 6:  Part 1 : ");
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
                for (int i = 7; i <= 25; i += 1) {
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