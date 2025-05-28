package aoc2024.day10;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Tuple<Integer, Integer>> ninePositions = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
    public static ArrayList<ArrayList<HashSet<Integer>>> canReach = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> scores = new ArrayList<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day10\\problem.txt" : "src\\aoc2024\\day10\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }

                grid.add(new ArrayList<>());
                canReach.add(new ArrayList<>());
                scores.add(new ArrayList<>());
                for (int i = 0; i < str.length(); i += 1) {
                    grid.get(grid.size() - 1).add(str.charAt(i) - '0');
                    scores.get(scores.size() - 1).add(0);
                    canReach.get(scores.size() - 1).add(new HashSet<>());
                    if (str.charAt(i) == '9') {
                        scores.get(scores.size() - 1).set(i, 1);
                        ninePositions.add(new Tuple<>(grid.size() - 1, i));
                    }
                }
            }
            reader.close();

            for (int i = 0; i < ninePositions.size(); i += 1) {
                canReach.get(ninePositions.get(i).getFirst()).get(ninePositions.get(i).getSecond()).add(i);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    

    public static Object partOne() {
        LinkedList<Tuple<Integer, Integer>> queue = new LinkedList<>();
        for (Tuple<Integer, Integer> pos : ninePositions) {
            queue.addLast(pos);
        }

        int count = 0;

        while (!queue.isEmpty()) { // BFS --> some optimizations can be made :)
            Tuple<Integer, Integer> pos = queue.removeFirst();
            int value = grid.get(pos.getFirst()).get(pos.getSecond());
            if (value == 0) {
                count += canReach.get(pos.getFirst()).get(pos.getSecond()).size();
            }
            if (pos.getFirst() > 0) {
                if (grid.get(pos.getFirst() - 1).get(pos.getSecond()).equals(value - 1)) {
                    if (canReach.get(pos.getFirst() - 1).get(pos.getSecond()).size() == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst() - 1, pos.getSecond()));
                    }
                    for (int i : canReach.get(pos.getFirst()).get(pos.getSecond())) {
                        canReach.get(pos.getFirst() - 1).get(pos.getSecond()).add(i);
                    }
                }
            }
            if (pos.getFirst() < grid.size() - 1) {
                if (grid.get(pos.getFirst() + 1).get(pos.getSecond()).equals(value - 1)) {
                    if (canReach.get(pos.getFirst() + 1).get(pos.getSecond()).size() == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst() + 1, pos.getSecond()));
                    }
                    for (int i : canReach.get(pos.getFirst()).get(pos.getSecond())) {
                        canReach.get(pos.getFirst() + 1).get(pos.getSecond()).add(i);
                    }
                }
            }
            if (pos.getSecond() > 0) {
                if (grid.get(pos.getFirst()).get(pos.getSecond() - 1).equals(value - 1)) {
                    if (canReach.get(pos.getFirst()).get(pos.getSecond() - 1).size() == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst(), pos.getSecond() - 1));
                    }
                    for (int i : canReach.get(pos.getFirst()).get(pos.getSecond())) {
                        canReach.get(pos.getFirst()).get(pos.getSecond() - 1).add(i);
                    }
                }
            }
            if (pos.getSecond() < grid.get(0).size() - 1) {
                if (grid.get(pos.getFirst()).get(pos.getSecond() + 1).equals(value - 1)) {
                    if (canReach.get(pos.getFirst()).get(pos.getSecond() + 1).size() == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst(), pos.getSecond() + 1));
                    }
                    for (int i : canReach.get(pos.getFirst()).get(pos.getSecond())) {
                        canReach.get(pos.getFirst()).get(pos.getSecond() + 1).add(i);
                    }
                }
            }
        }
        return count;
    }

    public static Object partTwo() { // lmao this is what I originally did
        LinkedList<Tuple<Integer, Integer>> queue = new LinkedList<>();
        for (Tuple<Integer, Integer> pos : ninePositions) {
            queue.addLast(pos);
        }

        int count = 0;

        while (!queue.isEmpty()) { // BFS --> some optimizations can be made :)
            Tuple<Integer, Integer> pos = queue.removeFirst();
            int value = grid.get(pos.getFirst()).get(pos.getSecond());
            if (value == 0) {
                count += scores.get(pos.getFirst()).get(pos.getSecond());
            }
            if (pos.getFirst() > 0) {
                if (grid.get(pos.getFirst() - 1).get(pos.getSecond()).equals(value - 1)) {
                    if (scores.get(pos.getFirst() - 1).get(pos.getSecond()) == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst() - 1, pos.getSecond()));
                    }
                    scores.get(pos.getFirst() - 1).set(pos.getSecond(), 
                        scores.get(pos.getFirst() - 1).get(pos.getSecond()) + scores.get(pos.getFirst()).get(pos.getSecond())
                    );
                }
            }
            if (pos.getFirst() < grid.size() - 1) {
                if (grid.get(pos.getFirst() + 1).get(pos.getSecond()).equals(value - 1)) {
                    if (scores.get(pos.getFirst() + 1).get(pos.getSecond()) == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst() + 1, pos.getSecond()));
                    }
                    scores.get(pos.getFirst() + 1).set(pos.getSecond(), 
                        scores.get(pos.getFirst() + 1).get(pos.getSecond()) + scores.get(pos.getFirst()).get(pos.getSecond())
                    );
                }
            }
            if (pos.getSecond() > 0) {
                if (grid.get(pos.getFirst()).get(pos.getSecond() - 1).equals(value - 1)) {
                    if (scores.get(pos.getFirst()).get(pos.getSecond() - 1) == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst(), pos.getSecond() - 1));
                    }
                    scores.get(pos.getFirst()).set(pos.getSecond() - 1, 
                        scores.get(pos.getFirst()).get(pos.getSecond() - 1) + scores.get(pos.getFirst()).get(pos.getSecond())
                    );
                }
            }
            if (pos.getSecond() < grid.get(0).size() - 1) {
                if (grid.get(pos.getFirst()).get(pos.getSecond() + 1).equals(value - 1)) {
                    if (scores.get(pos.getFirst()).get(pos.getSecond() + 1) == 0) {
                        queue.add(new Tuple<Integer,Integer>(pos.getFirst(), pos.getSecond() + 1));
                    }
                    scores.get(pos.getFirst()).set(pos.getSecond() + 1, 
                        scores.get(pos.getFirst()).get(pos.getSecond() + 1) + scores.get(pos.getFirst()).get(pos.getSecond())
                    );
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
                for (int i = 0; i < 10; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 10: Part 1 : ");
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
                for (int i = 11; i <= 25; i += 1) {
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