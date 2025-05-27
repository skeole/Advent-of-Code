package aoc2024.day07;

import java.io.*;
import java.util.*;

import utility.UsefulFunctions.Tuple;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Tuple<Long, ArrayList<Long>>> equations = new ArrayList<>();
    public static ArrayList<Long> powersOfTwo = new ArrayList<Long>();
    public static ArrayList<Long> powersOfTen = new ArrayList<Long>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day07\\problem.txt" : "src\\aoc2024\\day07\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(str);

                equations.add(new Tuple<>(Long.parseLong(st.nextToken().split(":")[0]), new ArrayList<>()));
                while (st.hasMoreTokens()) {
                    equations.get(equations.size() - 1).getSecond().add(Long.parseLong(st.nextToken()));
                }

            }
            reader.close();
            long two = 1;
            long ten = 1;
            powersOfTwo.add(two);
            for (int i = 0; i < 15; i += 1) {
                two *= 2;
                powersOfTwo.add(two);

                ten *= 10;
                powersOfTen.add(ten);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static boolean worksPartOne(long target, ArrayList<Long> numbers) {
        LinkedList<Tuple<Long, Integer>> queue = new LinkedList<>(); // indices left, current target
        queue.addFirst(new Tuple<Long, Integer>(target, numbers.size() - 1)); // worse for memory but slightly cleaner to read
        while (!queue.isEmpty()) {
            Tuple<Long, Integer> curr = queue.removeFirst();
            if (curr.getSecond() == 0) {
                if (curr.getFirst().equals(numbers.get(0))) {
                    return true;
                }
            } else {
                if (curr.getFirst() > numbers.get(curr.getSecond())) {
                    queue.addFirst(new Tuple<>(curr.getFirst() - numbers.get(curr.getSecond()), curr.getSecond() - 1));
                }
                if (curr.getFirst() % numbers.get(curr.getSecond()) == 0) {
                    queue.addFirst(new Tuple<>(curr.getFirst() / numbers.get(curr.getSecond()), curr.getSecond() - 1));
                }
            }
        }
        return false;
    }

    public static Object partOne() { // bruh I legit think it's just ...
        long count = 0; // Obviously not all puzzles work this way, but most do... 

        for (Tuple<Long, ArrayList<Long>> operation : equations) {
            if (worksPartOne(operation.getFirst(), operation.getSecond())) {
                count += operation.getFirst();
            }
        }
        return count;
    }

    public static boolean works(long target, ArrayList<Long> numbers) {
        LinkedList<Tuple<Long, Integer>> queue = new LinkedList<>(); // indices left, current target
        queue.addFirst(new Tuple<Long, Integer>(target, numbers.size() - 1)); // worse for memory but slightly cleaner to read
        while (!queue.isEmpty()) {
            Tuple<Long, Integer> curr = queue.removeFirst();
            if (curr.getSecond() == 0) {
                if (curr.getFirst().equals(numbers.get(0))) { // WHAT WHY DOES == NOT WORK KMS
                    return true;                                    // I'm just surprised it works sometimes but not always and not never
                }
            } else {
                if (curr.getFirst() > numbers.get(curr.getSecond())) {
                    queue.addFirst(new Tuple<>(curr.getFirst() - numbers.get(curr.getSecond()), curr.getSecond() - 1));
                }
                if (curr.getFirst() % numbers.get(curr.getSecond()) == 0) {
                    queue.addFirst(new Tuple<>(curr.getFirst() / numbers.get(curr.getSecond()), curr.getSecond() - 1));
                }
                if ((curr.getFirst() - numbers.get(curr.getSecond())) % powersOfTen.get((int) Math.log10(numbers.get(curr.getSecond()) + 0.5)) == 0) {
                    queue.addFirst(new Tuple<>((curr.getFirst() - numbers.get(curr.getSecond())) / powersOfTen.get((int) Math.log10(numbers.get(curr.getSecond()) + 0.5)), curr.getSecond() - 1));
                }
            }
        }
        return false;
    }

    public static Object partTwo() { // I meannn... it should still only take like 1.5 seconds...
        long count = 0;
        for (Tuple<Long, ArrayList<Long>> operation : equations) {
            if (works(operation.getFirst(), operation.getSecond())) {
                count += operation.getFirst();
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
                for (int i = 0; i < 7; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 7:  Part 1 : ");
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
                for (int i = 8; i <= 25; i += 1) {
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