package utility;

import java.io.*;

public class Populate { // Run this function once

    public static int year = 2024;
    public static void main(String[] args) {
        String directory = "src\\\\aoc" + String.valueOf(year);
        File maindir = new File(directory);
        File stats = new File(directory, "stats.txt");

        try {
            if (!maindir.exists()) {
                maindir.mkdirs(); // Create directory and any necessary parent directories
            }
            
            if (stats.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(directory + "\\\\stats.txt"));
                    bw.write("Stats for " + String.valueOf(year) + " Advent of Code solutions\n");
                    for (int i = 1; i <= 25; i += 1) {
                        bw.write("Day " + String.valueOf(i) + ":" + (i < 10 ? " " : "") + " Part 1 : N/A       | Part 2 : N/A\n");
                        // intention: 
                    }
                    bw.flush();
                    bw.close();

            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 25; i += 4) {
            directory = "src\\\\aoc" + String.valueOf(year) + "\\\\day" + (i < 10 ? "0" : "") + String.valueOf(i);

            File dir = new File(directory);
            File example = new File(dir, "example.txt");
            File problem = new File(dir, "problem.txt");
            File actual = new File(dir, "Problem.java");

            try {
                if (!dir.exists()) {
                    dir.mkdirs(); // Create directory and any necessary parent directories
                }

                example.createNewFile();
                problem.createNewFile();
                if (actual.createNewFile()) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(directory + "\\\\Problem.java"));
                    bw.write(
"package aoc" + String.valueOf(year) + ".day" + (i < 10 ? "0" : "") + String.valueOf(i) + ";\n\n" + 
"import java.io.*;\n" + 
"import java.util.*;\n\n" + 
"import static utility.UsefulFunctions.*;\n\n" + 
"public class Problem {\n\n" + 
"    public static final boolean actual = false;\n" + 
"    public static final String fileURL = actual ? \"src\\\\aoc" + String.valueOf(year)  + "\\\\day" + (i < 10 ? "0" : "") + String.valueOf(i) + "\\\\problem.txt\" : \"src\\\\aoc" + String.valueOf(year)  + "\\\\day" + (i < 10 ? "0" : "") + String.valueOf(i) + "\\\\example.txt\";\n\n" + 
"    public static void parse() {\n" + 
"        try {\n" + 
"            BufferedReader reader = new BufferedReader(new FileReader(fileURL));\n" + 
"            while (true) {\n" + 
"                String str = reader.readLine();\n" + 
"                if (str == null) {\n" + 
"                    break;\n" + 
"                }\n" + 
"                StringTokenizer st = new StringTokenizer(str);\n\n" + 
"            }\n" + 
"            reader.close();\n" + 
"        } catch (IOException e) {\n" + 
"            throw new IllegalArgumentException();\n" + 
"        }\n" + 
"    }\n\n" + 
"    public static Object partOne() {\n" + 
"        int count = 0; // Obviously not all puzzles work this way, but most do... \n" + 
"        return count;\n" + 
"    }\n\n" + 
"    public static Object partTwo() {\n" + 
"        int count = 0;\n" + 
"        return count;\n" + 
"    }\n\n" + 
"    public static void main(String[] args) {\n" + 
"        double startTime = System.nanoTime() / 1000000.0;\n" + 
"        parse();\n" + 
"        double endTime = System.nanoTime() / 1000000.0;\n" + 
"        println(\"Finished parsing in \" + round(endTime - startTime, 2) + \" ms\");\n" + 
"        println(\"-----------------------\");\n" + 
"        System.out.print(\"Part one answer: \");\n" + 
"        startTime = System.nanoTime() / 1000000.0;\n" + 
"        Object answer = partOne();\n" + 
"        endTime = System.nanoTime() / 1000000.0;\n" + 
"        println(answer);\n" + 
"        double firstTime = endTime - startTime;\n" + 
"        println(\"Time taken to compute: \" + round(firstTime, 2) + \" ms\");\n" + 
"        println(\"-----------------------\");\n" + 
"        System.out.print(\"Part two answer: \");\n" + 
"        startTime = System.nanoTime() / 1000000.0;\n" + 
"        answer = partTwo();\n" + 
"        endTime = System.nanoTime() / 1000000.0;\n" + 
"        println(answer);\n" + 
"        double secondTime = endTime - startTime;\n" + 
"        println(\"Time taken to compute: \" + round(secondTime, 2) + \" ms\");\n\n" + 
"        if (actual) {\n" + 
"            try {\n" + 
"                BufferedReader br = new BufferedReader(new FileReader(\"src\\\\\\\\aoc" + String.valueOf(year) + "\\\\\\\\stats.txt\"));\n" + 
"                StringBuilder output = new StringBuilder();\n" + 
"                for (int i = 0; i < " + String.valueOf(i) + "; i += 1) {\n" + 
"                    output.append(br.readLine()).append(\"\\n\");\n" + 
"                }\n" + 
"                br.readLine();\n" + 
"                output.append(\"Day " + String.valueOf(i) + ":" + (i < 10 ? " " : "") + " Part 1 : \");\n" + 
"                output.append(\n" + 
"                    firstTime >= 99999.5 ? \"\" + round(firstTime / 1000.0, 3) + \" s\" : \n" + 
"                    firstTime >= 9999.5 ? \" \" + round(firstTime / 1000.0, 3) + \" s\" : \n" + 
"                    firstTime >= 999.5 ? \"  \" + round(firstTime / 1000.0, 3) + \" s\" : \n" + 
"                    firstTime >= 99.5 ? \"\" + round(firstTime, 2) + \" ms\" : \n" + 
"                    firstTime >= 9.5 ? \" \" + round(firstTime, 2) + \" ms\" : \n" + 
"                    \"  \" + round(firstTime, 2) + \" ms\"\n" + 
"                );\n" + 
"                output.append(\" | Part 2 : \");\n" + 
"                output.append(\n" + 
"                    secondTime >= 99999.5 ? \"\" + round(secondTime / 1000.0, 3) + \" s\" : \n" + 
"                    secondTime >= 9999.5 ? \" \" + round(secondTime / 1000.0, 3) + \" s\" : \n" + 
"                    secondTime >= 999.5 ? \"  \" + round(secondTime / 1000.0, 3) + \" s\" : \n" + 
"                    secondTime >= 99.5 ? \"\" + round(secondTime, 2) + \" ms\" : \n" + 
"                    secondTime >= 9.5 ? \" \" + round(secondTime, 2) + \" ms\" : \n" + 
"                    \"  \" + round(secondTime, 2) + \" ms\"\n" + 
"                );\n" + 
"                output.append(\"\\n\");\n" + 
"                for (int i = " + String.valueOf(i + 1) + "; i <= 25; i += 1) {\n" + 
"                    output.append(br.readLine()).append(\"\\n\");\n" + 
"                }\n" + 
"                br.close();\n" + 
"                BufferedWriter bw = new BufferedWriter(new FileWriter(\"src\\\\\\\\aoc" + String.valueOf(year) + "\\\\\\\\stats.txt\"));\n" + 
"                bw.write(output.toString());\n" + 
"                bw.flush();\n" + 
"                bw.close();\n" + 
"            } catch (IOException e) {\n" + 
"                e.printStackTrace();\n" + 
"            }\n" + 
"        }\n" + 
"    }\n" + 
"}"
                    );
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
