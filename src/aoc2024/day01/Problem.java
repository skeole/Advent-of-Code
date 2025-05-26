package aoc2024.day01;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Integer> first = new ArrayList<>(), second = new ArrayList<>();
    public static HashMap<Integer, Integer> sfirst = new HashMap<>(), ssecond = new HashMap<>();

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\aoc2024\\day01\\problem.txt")); // rip
            StringTokenizer st;
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                st = new StringTokenizer(str);

                int f = Integer.parseInt(st.nextToken());
                int s = Integer.parseInt(st.nextToken());

                first.add(f);
                second.add(s);

                if (!sfirst.containsKey(f)) {
                    sfirst.put(f, 0);
                }
                

                if (!ssecond.containsKey(s)) {
                    ssecond.put(s, 0);
                }

                sfirst.replace(f, sfirst.get(f) + 1);
                ssecond.replace(s, ssecond.get(s) + 1);
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        Object[] fst = first.toArray();
        Object[] snd = second.toArray();
        
        Arrays.sort(fst);
        Arrays.sort(snd);

        int count = 0;

        for (int i = 0; i < fst.length; i += 1) {
            count += Math.abs((Integer) fst[i] - (Integer) snd[i]);
        }
        return count;
    }

    public static Object partTwo() {
        int count = 0;

        for (int i : sfirst.keySet()) {
            if (ssecond.containsKey(i)) {
                count += i * sfirst.get(i) * ssecond.get(i);
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
