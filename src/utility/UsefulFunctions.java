package utility;
import java.util.*;

public class UsefulFunctions {

    public static int[] deepcopy(int[] original) {
        int[] copy = new int[original.length];
        return copy;
    }

    public static double[] deepcopy(double[] original) {
        double[] copy = new double[original.length];
        return copy;
    }

    public static <T> List<T> deepcopy(List<T> original) {
        return original;
    }

    public static void println() {
        System.out.println();
    }
    
    public static void println(int i) {
        System.out.println(i);
    }

    public static String round(double d, int places) {
        return String.format("%." + places + "f", d);
    }
    
    public static void println(double d) {
        System.out.println(d);
    }
    
    public static void println(double d, int places) {
        System.out.println(round(d, places));
    }

    public static void println(char c) {
        System.out.println(c);
    }
    
    public static void println(boolean b) {
        System.out.println(b);
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void println(int[] list) {
        if (list.length == 0) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i : list) {
            sb.append(", ").append(i);
        }
        System.out.println(sb.delete(1, 3).append("]").toString());
    }

    public static void println(double[] list) {
        if (list.length == 0) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        for (double d : list) {
            sb.append(", ").append(d);
        }
        System.out.println(sb.delete(1, 3).append("]").toString());
    }

    public static void println(char[] list) {
        if (list.length == 0) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        for (char c : list) {
            sb.append(", ").append(c);
        }
        System.out.println(sb.delete(1, 3).append("]").toString());
    }

    public static void println(boolean[] list) {
        if (list.length == 0) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        for (boolean b : list) {
            sb.append(", ").append(b);
        }
        System.out.println(sb.delete(1, 3).append("]").toString());
    }

    public static void println(Object[] list) {
        if (list.length == 0) {
            System.out.println("[]");
            return;
        }
        StringBuilder sb = new StringBuilder("[");
        for (Object o : list) {
            sb.append(", ").append(o);
        }
        System.out.println(sb.delete(1, 3).append("]").toString());
    }

    public static void println(int[][] array) { // figure out max length of number

    }

    public static void println(double[][] array) { // \t each time

    }

    public static void println(char[][] array) {

    }

    public static void println(Object[][] array) {

    }

    public static int to_ascii(char c) {
        return (int) c;
    }

    public static boolean in_bounds(int r, int c, int[][] array) {
        return r > 0 && c > 0 && r < array.length && c < array[r].length;
    }

    public static ArrayList<Integer> countSubstringOccurences(CharSequence substring, CharSequence string) { // useful jumping-off code as well
        ArrayList<Integer> occurences = new ArrayList<>();
        for (int i = 0; i < string.length(); i += 1) {
            if (string.charAt(i) == substring.charAt(0) && i + substring.length() <= string.length()) {
                boolean works = true;
                for (int j = 0; j < substring.length(); j += 1) {
                    if (string.charAt(i + j) != substring.charAt(j)) {
                        works = false;
                        break;
                    }
                }
                if (works) {
                    occurences.add(i);
                }
            }
        }
        return occurences;
    }

    public static class Tuple<F, S> {
        private F first;
        private S second;

        public Tuple(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }

        public Tuple<F, S> copy() {
            return new Tuple<F, S>(first, second);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tuple<F, S> oo = (Tuple<F, S>) o;
            return Objects.equals(getFirst(), oo.getFirst()) && Objects.equals(getSecond(), oo.getSecond());
        }
    }

    public static class ComplexNumber { // rectangular by default
        public final int real;
        public final int imaginary;

        public ComplexNumber(int real, int imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        @Override
        public String toString() {
            return String.valueOf(real) + (imaginary < 0 ? " - " : " + ") + String.valueOf(Math.abs(imaginary)) + "i";
        }
    }

}
