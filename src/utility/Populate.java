package utility;

import java.io.*;

public class Populate { // Run this function once

    public static int year = 2024;
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i += 1) {
            String directory = "src\\\\aoc" + String.valueOf(year) + "\\\\day" + (i < 10 ? "0" : "") + String.valueOf(i);

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
"import java.lang.*; // Math, etc.\n" + 
"import java.util.*;\n\n" + 
"import static utility.UsefulFunctions.*;\n\n" + 
"public class Problem {\n\n" + 
"    public static void parse() {\n" + 
"        try {\n" + 
"            BufferedReader reader = new BufferedReader(new FileReader(\"src\\\\\\\\aoc2024\\\\\\\\day" + (i < 10 ? "0" : "") + String.valueOf(i) + "\\\\\\\\problem.txt\"));\n" + 
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
"        println(\"Time taken to compute: \" + round(endTime - startTime, 2) + \" ms\");\n" + 
"        println(\"-----------------------\");\n" + 
"        System.out.print(\"Part two answer: \");\n" + 
"        startTime = System.nanoTime() / 1000000.0;\n" + 
"        answer = partTwo();\n" + 
"        endTime = System.nanoTime() / 1000000.0;\n" + 
"        println(answer);\n" + 
"        println(\"Time taken to compute: \" + round(endTime - startTime, 2) + \" ms\");\n" + 
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
