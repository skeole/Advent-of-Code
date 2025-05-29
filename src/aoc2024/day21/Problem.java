package aoc2024.day21;

import java.io.*;
import java.util.*;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<Integer> codes = new ArrayList<Integer>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day21\\problem.txt" : "src\\aoc2024\\day21\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                codes.add(Integer.parseInt(str.split("A")[0]));
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Object partOne() {
        return solve(3);
    }

    public static Object partTwo() {
        return solve(26);
    }
    public static long solve(int numberOfRobots) {
        long[][][] intermediateStage = new long[numberOfRobots][5][5]; // one for each directional stage, and one for you!
        // note: intermediateStage[k][i][j] represents the number of time it takes to go from all As before k at i to all As after k at i

        int A_KEY = 0, RIGHT_KEY = 1, UP_KEY = 2, LEFT_KEY = 3, DOWN_KEY = 4; // lmao

        // manual stage: all zeroes --> :)

        for (int i = 1; i < numberOfRobots; i += 1) {
            long[][][][] temp = new long[5][5][5][5];

            for (int j = 0; j < 5; j += 1) {
                for (int k = 0; k < 5; k += 1) {
                    for (int l = 0; l < 5; l += 1) {
                        for (int m = 0; m < 5; m += 1) {
                            temp[j][k][l][m] = Long.MAX_VALUE / 2; // if we need longs, we will go to longs
                        }
                    }
                }
            }

            for (int j = 0; j < 5; j += 1) {
                for (int k = 0; k < 5; k += 1) {
                    for (int l = 0; l < 5; l += 1) {
                        temp[j][l][k][l] = intermediateStage[i - 1][j][k];
                    }
                }
            }

            temp[RIGHT_KEY][LEFT_KEY][RIGHT_KEY][DOWN_KEY] = 1; // lmao actually a really funny error
            temp[LEFT_KEY][DOWN_KEY][LEFT_KEY][LEFT_KEY] = 1;
            temp[LEFT_KEY][A_KEY][LEFT_KEY][UP_KEY] = 1;
            temp[RIGHT_KEY][UP_KEY][RIGHT_KEY][A_KEY] = 1;
            temp[DOWN_KEY][UP_KEY][DOWN_KEY][DOWN_KEY] = 1;
            temp[UP_KEY][DOWN_KEY][UP_KEY][UP_KEY] = 1;
            temp[RIGHT_KEY][DOWN_KEY][RIGHT_KEY][RIGHT_KEY] = 1;
            temp[LEFT_KEY][RIGHT_KEY][LEFT_KEY][DOWN_KEY] = 1;
            temp[UP_KEY][RIGHT_KEY][UP_KEY][A_KEY] = 1;
            temp[DOWN_KEY][A_KEY][DOWN_KEY][RIGHT_KEY] = 1;

            for (int o = 0; o < 5; o += 1) {
                for (int p = 0; p < 5; p += 1) {
                    for (int j = 0; j < 5; j += 1) {
                        for (int k = 0; k < 5; k += 1) {
                            for (int l = 0; l < 5; l += 1) {
                                for (int m = 0; m < 5; m += 1) {
                                    temp[j][k][l][m] = Math.min(
                                        temp[j][k][l][m], 
                                        temp[j][k][o][p] + temp[o][p][l][m]
                                    );
                                }
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < 5; j += 1) {
                for (int k = 0; k < 5; k += 1) {
                    intermediateStage[i][j][k] = temp[0][j][0][k]; // let us PRAY that this works :D
                }
            }
        }

        long[][][][] tempFinalGraph = new long[5][11][5][11];

        for (int j = 0; j < 5; j += 1) {
            for (int k = 0; k < 11; k += 1) {
                for (int l = 0; l < 5; l += 1) {
                    for (int m = 0; m < 11; m += 1) {
                        tempFinalGraph[j][k][l][m] = Long.MAX_VALUE / 2; // if we need longs, we will go to longs
                    }
                }
            }
        }

        for (int j = 0; j < 5; j += 1) {
            for (int k = 0; k < 5; k += 1) {
                for (int l = 0; l < 11; l += 1) {
                    tempFinalGraph[j][l][k][l] = intermediateStage[numberOfRobots - 1][j][k];
                }
            }
        }
        
        for (int i = 0; i < 8; i += 1) { // 15 * 2 in total between the 2
            tempFinalGraph[UP_KEY][i][UP_KEY][i + 3] = 1;
            tempFinalGraph[DOWN_KEY][i + 3][DOWN_KEY][i] = 1;

        }

        for (int i = 0; i < 11; i += 1) {
            if (i % 3 == 1) {
                continue;
            }
            tempFinalGraph[RIGHT_KEY][i][RIGHT_KEY][i + 1] = 1;
            tempFinalGraph[LEFT_KEY][i + 1][LEFT_KEY][i] = 1;
        }
        

        for (int o = 0; o < 5; o += 1) {
            for (int p = 0; p < 11; p += 1) {
                for (int j = 0; j < 5; j += 1) {
                    for (int k = 0; k < 11; k += 1) {
                        for (int l = 0; l < 5; l += 1) {
                            for (int m = 0; m < 11; m += 1) {
                                tempFinalGraph[j][k][l][m] = Math.min(
                                    tempFinalGraph[j][k][l][m], 
                                    tempFinalGraph[j][k][o][p] + tempFinalGraph[o][p][l][m]
                                );
                            }
                        }
                    }
                }
            }
        }

        long[][] finalGraph = new long[11][11];

        for (int j = 0; j < 11; j += 1) {
            for (int k = 0; k < 11; k += 1) {
                finalGraph[j][k] = tempFinalGraph[0][j][0][k] + 1; // let us PRAY that this works :D
            }
        }

        long count = 0;

        for (int i : codes) {
            count += ((long) i) * (
                finalGraph[1][(i / 100) % 10 == 0 ? 0 : (i / 100) % 10 + 1] + 
                finalGraph[(i / 100) % 10 == 0 ? 0 : (i / 100) % 10 + 1][(i / 10) % 10 == 0 ? 0 : (i / 10) % 10 + 1] + 
                finalGraph[(i / 10) % 10 == 0 ? 0 : (i / 10) % 10 + 1][i % 10 == 0 ? 0 : i % 10 + 1] + 
                finalGraph[i % 10 == 0 ? 0 : i % 10 + 1][1]
            );
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
                for (int i = 0; i < 21; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 21: Part 1 : ");
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
                for (int i = 22; i <= 25; i += 1) {
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