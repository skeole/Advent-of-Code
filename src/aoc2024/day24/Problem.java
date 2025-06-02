package aoc2024.day24;

import java.io.*;
import java.util.*;

import utility.UsefulFunctions.DoubleHashMap;
import utility.UsefulFunctions.HashMultiSet;
import utility.UsefulFunctions.Tuple;
import utility.UsefulFunctions.UnorderedTuple;

import static utility.UsefulFunctions.*;

public class Problem {
    
    public static HashMap<String, HashMultiSet<String>> forwardConnections = new HashMap<>();
    public static DoubleHashMap<String, Tuple<UnorderedTuple<String>, Integer>> backwardConnections = new DoubleHashMap<>();
        // 0 -> and, 1 -> or, 2 -> xor

    public static HashMap<String, Boolean> wires = new HashMap<>();

    public static ArrayList<Tuple<String, Boolean>> xs = new ArrayList<>();
    public static ArrayList<Tuple<String, Boolean>> ys = new ArrayList<>();
    public static ArrayList<String> zs = new ArrayList<>();

    public static final int AND = 0, OR = 1, XOR = 2, ERROR = -1;

    public static final boolean actual = false;
    public static final String fileURL = actual ? "src\\aoc2024\\day24\\problem.txt" : "src\\aoc2024\\day24\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str.length() == 0) {
                    break;
                }
                String[] split = str.split(": ");
                if (split[0].startsWith("x")) {
                    xs.add(new Tuple<>(split[0], Integer.parseInt(split[1]) == 1)); // relying on invariant that xs, ys are added in order - which they are
                } else {
                    ys.add(new Tuple<>(split[0], Integer.parseInt(split[1]) == 1));
                }
            }

            zs = new ArrayList<>();
            for (int i = 0; i < xs.size() + 1; i += 1) {
                zs.add("");
            }
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                
                String[] split = str.split(" ");
                String first = split[0];
                String second = split[2];
                String third = split[4];

                int operator = 
                    split[1].equals("AND") ? AND : 
                    split[1].equals("OR") ? OR : 
                    split[1].equals("XOR") ? XOR : ERROR;
                
                if (operator == ERROR) {
                    reader.close();
                    throw new IllegalArgumentException();
                }
                
                if (third.charAt(0) == 'z') {
                    zs.set(Integer.parseInt("" + third.charAt(1) + third.charAt(2)), third);
                }

                if (!forwardConnections.containsKey(first)) {
                    forwardConnections.put(first, new HashMultiSet<>());
                }
                if (!forwardConnections.containsKey(second)) {
                    forwardConnections.put(second, new HashMultiSet<>());
                }

                if (!backwardConnections.containsKey(third)) {
                    backwardConnections.put(third, 
                        new Tuple<>(new UnorderedTuple<>(first, second), operator)
                    );
                }

                forwardConnections.get(first).add(third);
                forwardConnections.get(second).add(third);
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static boolean evaluate() {
        wires.clear();
        LinkedList<String> queue = new LinkedList<>();
        for (Tuple<String, Boolean> x : xs) {
            wires.put(x.getFirst(), x.getSecond());
            queue.add(x.getFirst());
        }
        for (Tuple<String, Boolean> y : ys) {
            wires.put(y.getFirst(), y.getSecond());
            queue.add(y.getFirst());
        }
        while (queue.size() > 0) {
            String wire = queue.removeFirst();
            if (forwardConnections.containsKey(wire)) {
                for (String connection : forwardConnections.get(wire)) {
                    Tuple<UnorderedTuple<String>, Integer> fb = backwardConnections.get(connection);
                    if (
                        wires.containsKey(fb.getFirst().getFirst()) && 
                        wires.containsKey(fb.getFirst().getSecond()) && 
                        !wires.containsKey(connection) // important for when we add our own!
                    ) {
                        boolean fst = wires.get(fb.getFirst().getFirst());
                        boolean snd = wires.get(fb.getFirst().getSecond());
                        wires.put(connection, 
                            fb.getSecond() == AND ? fst && snd :
                            fb.getSecond() == OR ? fst || snd :
                            fst != snd
                        );
                        queue.add(connection);
                    }
                }
            }
        }
        return wires.size() == backwardConnections.size() + xs.size() + ys.size();
    }

    public static Object partOne() { // no short circuit logic --> my christmas
        evaluate();

        long sum = 0;
        long register = 1;
        for (String z : zs) {
            if (wires.get(z)) {
                sum += register;
            }
            register *= 2;
        }
        return sum;
    }

    public static void swap(String wire1, String wire2) {    
        forwardConnections.get(backwardConnections.get(wire1).getFirst().getFirst()).remove(wire1);
        forwardConnections.get(backwardConnections.get(wire1).getFirst().getSecond()).remove(wire1);
        
        forwardConnections.get(backwardConnections.get(wire2).getFirst().getFirst()).remove(wire2);
        forwardConnections.get(backwardConnections.get(wire2).getFirst().getSecond()).remove(wire2);
        
        forwardConnections.get(backwardConnections.get(wire1).getFirst().getFirst()).add(wire2);
        forwardConnections.get(backwardConnections.get(wire1).getFirst().getSecond()).add(wire2);
        
        forwardConnections.get(backwardConnections.get(wire2).getFirst().getFirst()).add(wire1);
        forwardConnections.get(backwardConnections.get(wire2).getFirst().getSecond()).add(wire1);

        backwardConnections.put("temp", backwardConnections.get(wire1));
        backwardConnections.put(wire1, backwardConnections.get(wire2));
        backwardConnections.put(wire2, backwardConnections.get("temp"));
        backwardConnections.remove("temp");

        // hmmmm I think I know what esta pasando
    }

    public static Object partTwo() {

        // oh bruh ts another analysis question 🥀

        // AHA! Find what they should be + what they are

        String prevCarry = null;
        TreeSet<String> swappers = new TreeSet<>();
        for (int i = 0; i < xs.size(); i += 1) {
            Tuple<UnorderedTuple<String>, Integer> first = new Tuple<>(
                new UnorderedTuple<>(xs.get(i).getFirst(), ys.get(i).getFirst()), 
                XOR
            );
            String firstWire = backwardConnections.getFirstKeyOfValue(first); // yeah O(1) yippee
            String realFirstWire = firstWire;
            if (prevCarry != null) {
                first = new Tuple<>(
                    new UnorderedTuple<>(firstWire, prevCarry), 
                    XOR
                );
                realFirstWire = backwardConnections.getFirstKeyOfValue(first);
            }

            if (realFirstWire == null) {
                UnorderedTuple<String> prevTuple = backwardConnections.get(zs.get(i)).getFirst();
                if (firstWire.equals(prevTuple.getFirst())) {
                    swappers.add(prevCarry);
                    swappers.add(prevTuple.getSecond());
                    swap(prevCarry, prevTuple.getSecond());
                    prevCarry = prevTuple.getSecond();
                } else if (firstWire.equals(prevTuple.getSecond())) {
                    swappers.add(prevCarry);
                    swappers.add(prevTuple.getFirst());
                    swap(prevCarry, prevTuple.getFirst());
                    prevCarry = prevTuple.getFirst();
                } else if (prevCarry.equals(prevTuple.getFirst())) {
                    swappers.add(firstWire);
                    swappers.add(prevTuple.getSecond());
                    swap(firstWire, prevTuple.getSecond());
                    firstWire = prevTuple.getSecond();
                } else if (prevCarry.equals(prevTuple.getSecond())) {
                    swappers.add(firstWire);
                    swappers.add(prevTuple.getFirst());
                    swap(firstWire, prevTuple.getFirst());
                    firstWire = prevTuple.getFirst();
                } else {
                    printValues(firstWire, prevTuple);
                    System.out.println("something went wrong");
                }
                realFirstWire = zs.get(i);
            } else if (!zs.get(i).equals(realFirstWire)) {
                swappers.add(realFirstWire);
                swappers.add(zs.get(i));
                swap(realFirstWire, zs.get(i));
                realFirstWire = zs.get(i);
            }

            Tuple<UnorderedTuple<String>, Integer> second = new Tuple<>(
                new UnorderedTuple<>(xs.get(i).getFirst(), ys.get(i).getFirst()), 
                AND
            );
            String nextCarry = backwardConnections.getFirstKeyOfValue(second); // yeah O(1) yippee
            String realNextCarry = nextCarry;
            if (prevCarry != null) {
                Tuple<UnorderedTuple<String>, Integer> otherNextCarry = new Tuple<>(
                    new UnorderedTuple<>(firstWire, prevCarry), 
                    AND
                );
                String otherPossibleNextCarry = backwardConnections.getFirstKeyOfValue(otherNextCarry);
                if (otherPossibleNextCarry == null) {
                    printValues(otherPossibleNextCarry, firstWire, prevCarry, "opnw");
                }
                Tuple<UnorderedTuple<String>, Integer> combinedNextCarry = new Tuple<>(
                    new UnorderedTuple<>(nextCarry, otherPossibleNextCarry), 
                    OR
                );
                realNextCarry = backwardConnections.getFirstKeyOfValue(combinedNextCarry);
                if (otherPossibleNextCarry == null) {
                    printValues(nextCarry, otherPossibleNextCarry, "cnc");
                }
            }

            prevCarry = realNextCarry;

            if (i == xs.size() - 1) {
                if (!zs.get(i + 1).equals(realNextCarry)) {
                    printValues(realNextCarry, i);
                }
            }
        }

        /* Bit adder form
            * first = xs xor ys
            * zs = first xor prevcarry
            * carrytemp = xs and ys
            * carrytemptwo = prevcarry and first
            * carry = carrytemp or carrytemptwo */

        // compe problem 

        return swappers;
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
                for (int i = 0; i < 24; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 24: Part 1 : ");
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
                for (int i = 25; i <= 25; i += 1) {
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