package aoc2024.day01;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Integer> first = new ArrayList<>(), second = new ArrayList<>();
    public static HashMap<Integer, Integer> sfirst = new HashMap<>(), ssecond = new HashMap<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day01\\problem.txt" : "src\\aoc2024\\day01\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL)); // rip
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
                for (int i = 0; i < 1; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 1:  Part 1 : ");
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
                for (int i = 2; i <= 25; i += 1) {
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
