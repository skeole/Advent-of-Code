package aoc2024.day17;

import java.io.*;
import java.util.*;

import utility.UsefulFunctions.BetterArrayDeque;

import static utility.UsefulFunctions.*;

public class Problem {

    public static ArrayList<BetterArrayDeque<Boolean>> registers = new ArrayList<>();
    public static ArrayList<Integer> instructions = new ArrayList<>();
    public static int pointer;
    public static BetterArrayDeque<Integer> outputs = new BetterArrayDeque<>();

    public static final boolean actual = true;
    public static final String fileURL = actual ? "src\\aoc2024\\day17\\problem.txt" : "src\\aoc2024\\day17\\example.txt";

    public static void parse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileURL));
            while (true) {
                String str = reader.readLine();
                if (str.length() == 0) {
                    break;
                }
                registers.add(binaryOf(Long.parseLong(str.split(" ")[2])));
            }
            
            while (true) {
                String str = reader.readLine();
                if (str == null) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(str.split(" ")[1], ",");

                while (st.hasMoreTokens()) {
                    instructions.add(Integer.parseInt(st.nextToken()));
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static BetterArrayDeque<Boolean> binaryOf(long i) {
        BetterArrayDeque<Boolean> result = new BetterArrayDeque<>();
        while (i > 0) {
            result.add(i % 2 == 1);
            i /= 2;
        }
        return result;
    }
    
    public static BetterArrayDeque<Boolean> comboOperator(int i) {
        return switch (i) {
            case 0, 1, 2, 3 -> binaryOf(i);
            case 4, 5, 6 -> new BetterArrayDeque<>(registers.get(i - 4));
            default -> throw new IllegalArgumentException();
        };
    }

    public static long deBinary(BetterArrayDeque<Boolean> binary, long maxValue) {
        long decimal = 0;
        long register = 1;
        for (boolean b : binary) {
            if (b) {
                decimal += register;
            }
            if (register > maxValue) {
                return Math.min(decimal, maxValue);
            }
            register *= 2;
        }
        return Math.min(decimal, maxValue);
    }
    
    /* The adv instruction (opcode 0) performs division. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's combo operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is truncated to an integer and then written to the A register.

       The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand, then stores the result in register B.

       The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.

       The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.

       The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)

       The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.)

       The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register. (The numerator is still read from the A register.)

       The cdv instruction (opcode 7) works exactly like the adv instruction except that the result is stored in the C register. (The numerator is still read from the A register.) */

    public static interface functional { //0, 5, 3, 6, 7 tested --> 1, 2, 4???
        void function(int operand);
    }

    public static functional adv = operand -> {
        int truncates = (int) deBinary(comboOperator(operand), registers.get(0).size());
        for (int i = 0; i < truncates; i += 1) {
            registers.get(0).removeFirst();
        }
    };
    
    public static functional bxl = operand -> { // xor -> a != b
        BetterArrayDeque<Boolean> xor = binaryOf(operand);
        for (int i = registers.get(1).size(); i < xor.size(); i += 1) {
            registers.get(1).addLast(false);
        }
        for (int i = 0; i < xor.size(); i += 1) {
            registers.get(1).set(i, registers.get(1).get(i) != xor.get(i)); // r u fucking serious
            // fuck you Josh Bloch and Neal Gafter
        }
    };

    public static functional bst = operand -> {
        BetterArrayDeque<Boolean> B = comboOperator(operand);
        for (int i = B.size(); i < 3; i += 1) {
            B.addLast(false);
        }
        registers.get(1).clear();
        for (int i = 0; i < 3; i += 1) {
            registers.get(1).addLast(B.get(i));
        }
    };

    public static functional jnz = operand -> {
        if (registers.get(0).size() != 0) {
            pointer = operand - 2; // am I a genius or am I a retard
        }
    };

    public static functional bxc = operand -> { // operand is ignored
        // bxl.function(6); // cannot do this bc bxl takes in literal not combo
        for (int i = registers.get(1).size(); i < registers.get(2).size(); i += 1) {
            registers.get(1).addLast(false);
        }
        for (int i = 0; i < registers.get(2).size(); i += 1) {
            registers.get(1).set(i, registers.get(1).get(i) != registers.get(2).get(i));
        }
    };

    public static functional out = operand -> {
        BetterArrayDeque<Boolean> output = comboOperator(operand);
        for (int i = output.size(); i < 3; i += 1) {
            output.addLast(false);
        }
        int eger = 1 * (output.get(0) ? 1 : 0) + 2 * (output.get(1) ? 1 : 0) + 4 * (output.get(2) ? 1 : 0);
        outputs.add(eger);
    };

    public static functional bdv = operand -> {
        registers.get(1).clear();
        int truncates = (int) deBinary(comboOperator(operand), registers.get(0).size());
        for (int i = truncates; i < registers.get(0).size(); i += 1) {
            registers.get(1).addLast(registers.get(0).get(i));
        }
    };

    public static functional cdv = operand -> {
        registers.get(2).clear();
        int truncates = (int) deBinary(comboOperator(operand), registers.get(0).size());
        for (int i = truncates; i < registers.get(0).size(); i += 1) {
            registers.get(2).addLast(registers.get(0).get(i));
        }
    };

    public static functional[] commands = {
        adv, bxl, bst, jnz, bxc, out, bdv, cdv
    };

    public static Object partOne() {
        outputs.clear();
        pointer = 0;
        while (true) {
            if (pointer > instructions.size() - 2) {
                break;
            }
            commands[instructions.get(pointer)].function(instructions.get(pointer + 1));
            pointer += 2;
        }
        return outputs.toString("", ",", "");
    }

    public static Object partTwo() { // yeah... this is an analysis question
        // essentially, what the program does is loop
            // 2,4,1,3,7,5,0,3,1,4,4,7

            // 7, 5: C <- A // 2 ** B
            // 0, 3: A <- A // 8
            // 1, 4: B <- B XOR 100
            // 4, 7: B <- B XOR C
            // now... note that all of this only depends on the first few digits of A!!!

            // casework on the first 3 digits of A: (written in reverse)

            // 000CCC
                // B -> 0 -> 3 -> 7
                // C -> CCC
            // 100CC
                // B -> 1 -> 2 -> 6
                // C -> 0CC
            // 010C
                // B -> 2 -> 1 -> 5
                // C -> 10C
            // 110
                // B -> 3 -> 0 -> 4
                // C -> 110
            // 001AAAACCC
                // B -> 4 -> 7 -> 3
                // C -> CCC
            // 101AAACCC
                // B -> 5 -> 6 -> 2
            // 011AACCC
                // B -> 6 -> 5 -> 1
                // C -> CCC
            // 111ACCC
                // B -> 7 -> 4 -> 0
                // C -> CCC
            
        // until A is zero, at which point it stops
        // on each loop, the value of C xor B (mod 8) is printed

        // we want to reverse-engineer from the end. Note: after the last three, there should be all zeroes

        if (!actual) {
            return "Not applicable: input follows pattern";
        }

        BetterArrayDeque<Boolean> result = new BetterArrayDeque<>();
        for (int i = 0; i < 7; i += 1) {
            result.addLast(false); // lazey
        }

        for (int i = instructions.size() - 1; i >= 0; i -= 1) {
            // add instructions.get(i)
            for (int j = 0; j < 8; j += 1) {
                // see the first one that works
                result.addFirst((j / 4) % 2 == 1);
                result.addFirst((j / 2) % 2 == 1);
                result.addFirst((j / 1) % 2 == 1);
                int b = j / 4 * 4 + 3 - (j % 4);
                // 0 -> 3
                // 3 -> 0
                // 4 -> 7
                // 7 -> 4
                int sum = 0;
                int register = 1;
                for (int k = 0; k < 3; k += 1) {
                    sum += register * (result.get(k) == result.get(b + k) ? 1 : 0);
                    register *= 2;
                }
                if (sum == instructions.get(i)) {
                    break;
                }
                result.removeFirst();
                result.removeFirst();
                result.removeFirst();
            }
        }

        long sum = 0;
        long register = 1;
        for (int i = 0; i < result.size() - 7; i += 1) {
            sum += register * (result.get(i) ? 1 : 0);
            register *= 2;
        }

        return sum; // I'm sure there's a faster way to do part one as well but oh well
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
                for (int i = 0; i < 17; i += 1) {
                    output.append(br.readLine()).append("\n");
                }
                br.readLine();
                output.append("Day 17: Part 1 : ");
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
                for (int i = 18; i <= 25; i += 1) {
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