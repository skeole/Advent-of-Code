package aoc2024.day03;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<String> inputs = new ArrayList<>();
    public static String expected = "mul(";
    public static String activate = "do()";
    public static String deactivate = "don't()";

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day03\\problem.txt" : "src\\aoc2024\\day03\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL)); // rip
            while (true) {
                String str = reader.readLine();

                if (str == null) {
                    break;
                }

                inputs.add(str);
            }
            
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() { // shoule be 171183089, getting 173257740 172520259
        int count = 0; // afaik, no negatives
        for (String input : inputs) {
            int dex = 0;
            boolean second = false;
            StringBuilder sb = new StringBuilder();
            int first = 0;
            for (int i = 0; i < input.length(); i += 1) {
                if (input.charAt(i) == 'm') {
                    dex = 0;
                }
                if (dex == 4) {
                    if ('0' <= input.charAt(i) && input.charAt(i) <= '9') {
                        sb.append(input.charAt(i));
                    } else if (input.charAt(i) == ',' && !second) {
                        second = true;
                        first = Integer.parseInt(sb.toString());
                        sb = new StringBuilder();
                    } else if (input.charAt(i) == ')' && second) {
                        dex = 0;
                        count += first * Integer.parseInt(sb.toString());
                        second = false;
                    } else {
                        dex = 0;
                        second = false;
                    }
                } else if (input.charAt(i) == expected.charAt(dex)) {
                    dex += 1;
                    second = false;
                    if (dex == 4) {
                        sb = new StringBuilder();
                    }
                } else {
                    dex = 0;
                }
            }
        }
        return count;
    }

    public static Object partTwo() {
        int count = 0; // afaik, no negatives
        boolean active = true;
        for (String input : inputs) {
            int dex = 0;
            int dex2 = 0;
            int dex3 = 0;
            boolean second = false;
            StringBuilder sb = new StringBuilder();
            int first = 0;
            for (int i = 0; i < input.length(); i += 1) {
                if (input.charAt(i) == 'd') {
                    dex2 = 0;
                    dex3 = 0;
                }
                if (input.charAt(i) == 'm') {
                    dex = 0;
                }
                if (dex == 4) {
                    if ('0' <= input.charAt(i) && input.charAt(i) <= '9') {
                        sb.append(input.charAt(i));
                    } else if (input.charAt(i) == ',' && !second) {
                        second = true;
                        first = Integer.parseInt(sb.toString());
                        sb = new StringBuilder();
                    } else if (input.charAt(i) == ')' && second) {
                        dex = 0;
                        count += first * Integer.parseInt(sb.toString()) * (active ? 1 : 0);
                        second = false;
                    } else {
                        dex = 0;
                        second = false;
                    }
                } else if (input.charAt(i) == expected.charAt(dex)) {
                    dex += 1;
                    second = false;
                    if (dex == 4) {
                        sb = new StringBuilder();
                    }
                } else {
                    dex = 0;
                }

                if (input.charAt(i) == activate.charAt(dex2)) {
                    dex2 += 1;
                    if (dex2 == 4) {
                        dex2 = 0;
                        active = true;
                    }
                } else {
                    dex2 = 0;
                }

                if (input.charAt(i) == deactivate.charAt(dex3)) {
                    dex3 += 1;
                    if (dex3 == 7) {
                        dex3 = 0;
                        active = false;
                    }
                } else {
                    dex3 = 0;
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
                for (int i = 0; i < 3; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 3:  Part 1 : ");
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
                for (int i = 4; i <= 25; i += 1) {
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