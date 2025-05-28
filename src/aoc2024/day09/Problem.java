package aoc2024.day09;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Integer> memory = new ArrayList<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day09\\problem.txt" : "src\\aoc2024\\day09\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                
                for (int i = 0; i < str.length(); i += 1) {
                    memory.add(str.charAt(i) - '0');
                }

            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        long count = 0; // Obviously not all puzzles work this way, but most do... 
        int sum = 0;
        for (int i : memory) {
            sum += i;
        }
        long[] startingmemory = new long[sum];
        int idcounter = 1; // so there's a difference between unititialized and empty
        int positioncounter = 0;
        for (int i = 0; i < memory.size() - 1; i += 2) {
            for (int j = 0; j < memory.get(i); j += 1) {
                startingmemory[positioncounter + j] = idcounter;
            }
            positioncounter += memory.get(i);
            positioncounter += memory.get(i + 1);
            idcounter += 1;
        }
        if (memory.size() % 2 == 1) {
            for (int j = 0; j < memory.get(memory.size() - 1); j += 1) {
                startingmemory[positioncounter + j] = idcounter;
            }
        }
        int startingpointer = 0;
        int endingpointer = sum - 1;
        while (startingpointer < endingpointer) {
            if (startingmemory[startingpointer] != 0) {
                startingpointer += 1;
            } else if (startingmemory[endingpointer] == 0) {
                endingpointer -= 1;
            } else {
                startingmemory[startingpointer] = startingmemory[endingpointer];
                startingmemory[endingpointer] = 0;
            }
        }
        for (int i = 0; i < startingmemory.length; i += 1) {
            if (startingmemory[i] != 0) {
                count += ((long) i) * (startingmemory[i] - 1);
            }
        }
        return count;
    }

    public static Object partTwo() {
        // gotta use like a triply linked hashmap or sum shi
        ArrayList<Tuple<Integer, Integer>> memoryQueue = new ArrayList<>();
        TreeMap<Integer, Integer> freeMemory = new TreeMap<>();
        HashMap<Integer, Integer> freeMemoryFromBack = new HashMap<>();

        int cursor = 0;
        for (int i = 0; i < memory.size(); i += 1) { // verified: no blocks of length zero
            if (i % 2 == 0) { // confirmed this will never be zero
                memoryQueue.add(new Tuple<>(cursor, cursor + memory.get(i) - 1));
                cursor += memory.get(i);
            } else { // 
                if (memory.get(i) != 0) {
                    freeMemory.put(cursor, cursor + memory.get(i) - 1);
                    freeMemoryFromBack.put(cursor + memory.get(i) - 1, cursor);
                }
                cursor += memory.get(i);
            }
        }

        for (int i = memoryQueue.size() - 1; i >= 0; i -= 1) {
            int length = memoryQueue.get(i).getSecond() - memoryQueue.get(i).getFirst() + 1;
            int memoryPosition = -1;
            for (Map.Entry<Integer, Integer> memorySpot : freeMemory.entrySet()) {
                if (memorySpot.getKey() > memoryQueue.get(i).getFirst()) {
                    break;
                }
                /* if (memoryQueue.get(i).getFirst().equals(memorySpot.getValue() + 1)) {
                    memoryPosition = memorySpot.getKey();
                    break;
                } nvm Advent of Code isn't woke like that */
                if (memorySpot.getValue() - memorySpot.getKey() + 1 >= length) {
                    memoryPosition = memorySpot.getKey();
                    break;
                }

            }
            if (memoryPosition != -1) {
                // there are three relevant pieces of free memory
                int newFreeStart = memoryQueue.get(i).getFirst();
                int newFreeEnd = memoryQueue.get(i).getSecond();
                if (freeMemoryFromBack.containsKey(newFreeStart - 1)) {
                    newFreeStart = freeMemoryFromBack.remove(newFreeStart - 1);
                    freeMemory.remove(newFreeStart);
                }
                if (freeMemory.containsKey(newFreeEnd + 1)) {
                    newFreeEnd = freeMemory.remove(newFreeEnd + 1);
                    freeMemoryFromBack.remove(newFreeEnd);

                }

                freeMemory.put(newFreeStart, newFreeEnd);
                freeMemoryFromBack.put(newFreeEnd, newFreeStart);

                memoryQueue.set(i, new Tuple<Integer,Integer>(
                    memoryPosition, 
                    memoryPosition + length - 1
                ));
                
                int end = freeMemory.remove(memoryPosition);
                freeMemoryFromBack.remove(end);

                if (memoryPosition + length <= end) {
                    freeMemory.put(memoryPosition + length, end);
                    freeMemoryFromBack.put(end, memoryPosition + length);
                }
            }
        }

        long count = 0;

        for (int i = 0; i < memoryQueue.size(); i += 1) {
            count += ((long) i) * ((long) (memoryQueue.get(i).getFirst() + memoryQueue.get(i).getSecond())) * ((long) (memoryQueue.get(i).getSecond() - memoryQueue.get(i).getFirst() + 1)) / 2;
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
                for (int i = 0; i < 9; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 9:  Part 1 : ");
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
                for (int i = 10; i <= 25; i += 1) {
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