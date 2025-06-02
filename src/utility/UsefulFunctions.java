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

    public static void println(int[][] array) { // figure out max length of number

    }

    public static void println(double[][] array) { // \t each time

    }

    public static void println(char[][] array) {

    }

    public static <E> void println(E[] array) {

    }

    public static void println(Object[][] array) {

    }

    public static void printValues(Object... toPrint) {
        StringBuilder sb = new StringBuilder();
        for (Object o : toPrint) {
            sb.append(o).append(' ');
        }
        System.out.println(sb);
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
        protected F first;
        protected S second;

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

        public F updateFirst(F first) {
            F ret = first;
            this.first = first;
            return ret;
        }

        public S updateSecond(S second) {
            S ret = second;
            this.second = second;
            return ret;
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
            Tuple<F, S> t = (Tuple<F, S>) o;
            return Objects.equals(getFirst(), t.getFirst()) && Objects.equals(getSecond(), t.getSecond());
        }

        @Override
        public int hashCode() {
            return first.hashCode() * 31 + second.hashCode();
        }
    }

    public static class UnorderedTuple<E> extends Tuple<E, E> {
        public UnorderedTuple(E first, E second) {
            super(first, second);
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
            UnorderedTuple<E> ut = (UnorderedTuple<E>) o;
            return 
                (Objects.equals(getFirst(), ut.getFirst()) && Objects.equals(getSecond(), ut.getSecond())) || 
                (Objects.equals(getFirst(), ut.getSecond()) && Objects.equals(getSecond(), ut.getFirst()));
        }

        @Override
        public int hashCode() {
            return first.hashCode() ^ second.hashCode(); // XOR not exponentiation... I believe
        }
    }

    public static class ComplexNumber { // rectangular by default
        public final int real;
        public final int imaginary;

        public ComplexNumber(int real, int imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public ComplexNumber rotateCounterClockwise() {
            return new ComplexNumber(-imaginary, real);

        }

        public ComplexNumber rotateClockwise() {
            // return this.rotateCounterClockwise().rotateCounterClockwise().rotateCounterClockwise(); // CIS 1200 reference
            return new ComplexNumber(imaginary, -real);
        }

        public ComplexNumber plus(ComplexNumber c) {
            return new ComplexNumber(real + c.real, imaginary + c.imaginary);
        }

        public ComplexNumber minus(ComplexNumber c) {
            return new ComplexNumber(real - c.real, imaginary - c.imaginary);
        }

        public ComplexNumber times(int i) {
            return new ComplexNumber(real * i, imaginary * i);
        }

        public ComplexNumber times(ComplexNumber c) {
            return new ComplexNumber(real * c.real - imaginary * c.imaginary, imaginary * c.real + real * c.imaginary);
        }

        /** (0, 0) refers to the bottom left corner of the graph. 
         * This means (x + yi) -> 
         * @return graph[length - 1 - y][x], or null if out of bounds. Note: null does not necessarily mean out of bounds
         */
        public <E> E getPositionInGraph(E[][] graph) {
            if (imaginary < 0 || imaginary >= graph.length || real < 0 || real >= graph[graph.length - 1 - imaginary].length) {
                return null;
            } else {
                return graph[graph.length - 1 - imaginary][real];
            }
        }

        @Override
        public String toString() {
            return String.valueOf(real) + (imaginary < 0 ? " - " : " + ") + String.valueOf(Math.abs(imaginary)) + "i";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ComplexNumber cn = (ComplexNumber) o;
            return (real == cn.real) && (imaginary == cn.imaginary);
        }

        @Override
        public int hashCode() {
            return real + 31 * imaginary;
        }
    }

    public static class BetterArrayDeque<E> extends AbstractCollection<E> implements Deque<E>, List<E>, Cloneable { // #CancelJoshBlochAndNealGafter

        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[2];

        int firstIndex = 0;
        int lastIndex = 0;

        public BetterArrayDeque() {

        }

        public BetterArrayDeque(Collection<? extends E> c) { // huh
            c.forEach(this::addLast);
        }

        int modifiedIndex(int i) {
            return (i % array.length + array.length) % array.length;
        }

        public E get(int i) { // THE HOLY GRAIL
            if (i < 0 || i >= size()) {
                throw new IndexOutOfBoundsException();
            }
            return array[modifiedIndex(i + firstIndex)];
        }

        @Override
        public E set(int i, E e) {
            if (i < 0 || i >= size()) {
                throw new IndexOutOfBoundsException();
            }
            E ret = array[modifiedIndex(i + firstIndex)];
            array[modifiedIndex(i + firstIndex)] = e;
            return ret;
        }

        private void expand() {
            @SuppressWarnings("unchecked")
            E[] arr = (E[]) new Object[2 * array.length]; // sigh

            for (int i = firstIndex; i < lastIndex; i += 1) {
                arr[i - firstIndex] = array[modifiedIndex(i)];
            }

            firstIndex = 0;
            lastIndex = array.length;

            array = arr;
        }

        private void contract() {
            if (array.length <= 2) {
                return;
            }

            @SuppressWarnings("unchecked")
            E[] arr = (E[]) new Object[array.length / 2]; // sigh

            for (int i = firstIndex; i < lastIndex; i += 1) {
                arr[i - firstIndex] = array[modifiedIndex(i)];
            }

            firstIndex = 0;
            lastIndex = array.length / 4;

            array = arr;
        }

        @Override
        public int size() {
            return lastIndex - firstIndex;
        }

        public E[] asArray() {
            @SuppressWarnings("unchecked")
            E[] arr = (E[]) new Object[size()]; // much more interesting imo

            for (int i = firstIndex; i < lastIndex; i += 1) {
                arr[i - firstIndex] = array[modifiedIndex(i)];
            }

            return arr;
        }

        @Override
        public void addFirst(E e) {
            if (size() == array.length) {
                expand();
            }
            firstIndex -= 1;
            array[modifiedIndex(firstIndex)] = e;
        }

        @Override
        public boolean add(E e) {
            addLast(e);
            return true;
        }

        @Override
        public void addLast(E e) {
            if (size() == array.length) {
                expand();
            }
            lastIndex += 1;
            array[modifiedIndex(lastIndex - 1)] = e;
        }

        @Override
        public E pollFirst() {
            if (size() == 0) {
                return null;
            }
            if (size() == array.length / 4) {
                contract();
            }
            E ret = array[modifiedIndex(firstIndex)];
            array[modifiedIndex(firstIndex)] = null;
            firstIndex += 1;
            return ret;
        }

        @Override
        public E poll() {
            return pollFirst();
        }

        @Override
        public E removeFirst() {
            if (size() == 0) {
                throw new NoSuchElementException("Queue is empty, no first element");
            }
            return pollFirst();
        }
        
        @Override
        public E pop() {
            return removeFirst();
        }

        @Override
        public E remove() {
            return removeFirst();
        }

        @Override
        public E pollLast() {
            if (size() == 0) {
                return null;
            }
            if (size() == array.length / 4) {
                contract();
            }
            lastIndex -= 1;
            E ret = array[modifiedIndex(lastIndex)];
            array[modifiedIndex(lastIndex)] = null;
            return ret;
        }

        @Override
        public E removeLast() {
            if (size() == 0) {
                throw new NoSuchElementException("Queue is empty, no first element");
            }
            return pollLast();
        }

        @Override
        public E peekFirst() {
            if (size() == 0) {
                return null;
            }
            return array[modifiedIndex(firstIndex)];
        }

        @Override
        public E peek() {
            return peekFirst();
        }

        @Override
        public E getFirst() {
            if (size() == 0) {
                throw new NoSuchElementException("Queue is empty, no first element");
            }
            return array[modifiedIndex(firstIndex)];
        }

        @Override
        public E element() {
            return getFirst();
        }

        @Override
        public E peekLast() {
            if (size() == 0) {
                return null;
            }
            return array[modifiedIndex(lastIndex - 1)];
        }

        @Override
        public E getLast() {
            if (size() == 0) {
                throw new NoSuchElementException("Queue is empty, no last element");
            }
            return array[modifiedIndex(lastIndex - 1)];
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                E[] arr = asArray();
                int nextIndex = 0;
                @Override
                public boolean hasNext() {
                    return nextIndex < arr.length;
                }

                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("No next element");
                    }
                    nextIndex += 1;
                    return arr[nextIndex - 1];
                }
            };
        }

        @Override
        public int indexOf(Object o) {
            int index = 0;
            for (E i : this) {
                if (Objects.equals(o, i)) {
                    return index;
                }
                index += 1;
            }
            return -1;
        }

        @Override
        public boolean removeFirstOccurrence(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeLastOccurrence(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int i, E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ListIterator<E> listIterator(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ListIterator<E> listIterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offerFirst(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offerLast(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offer(E e) {
            return offerLast(e);
        }
        
        @Override
        public int lastIndexOf(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void push(E e) {
            addFirst(e);
        }

        @Override
        public Iterator<E> descendingIterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException();
        }

        @Override @SuppressWarnings("unchecked")
        public void clear() {
            array = (E[]) new Object[2];

            firstIndex = 0;
            lastIndex = 0;
        }

        @Override
        public BetterArrayDeque<E> clone() {
            return new BetterArrayDeque<E>(this);
        }

        public String toString(String open, String between, String close) {
            if (isEmpty()) {
                return open + close;
            }
            StringBuilder sb = new StringBuilder(open);
            Iterator<E> iterator = iterator();
            while (true) {
                sb.append(iterator.next());
                if (!iterator.hasNext()) {
                    return sb.append(close).toString();
                }
                sb.append(between);
            }
        }
    }

    public static class HashMultiSet<E> extends AbstractCollection<E> { // can make abstract --> can be a TreeSet as well :D
        private int size = 0;
        private Map<E, Integer> map = new HashMap<>();
        private Class<? extends Object> c = null;

        @Override
        public int size() {
            return size;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                Iterator<E> keys = map.keySet().iterator();
                boolean started = false;
                E current = null;
                int currentCounter = 0;

                @Override
                public boolean hasNext() {
                    return keys.hasNext() || (started && currentCounter <= map.get(current));
                }

                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    if (!started) {
                        current = keys.next();
                    }
                    if (currentCounter > map.get(current)) {
                        currentCounter = 0;
                        current = keys.next();
                        return current;
                    } else {
                        currentCounter += 1;
                        return current;
                    }
                }
                
            };
        }

        @Override
        public boolean contains(Object o) {
            return map.containsKey(o);
        }

        @Override
        public boolean add(E e) {
            if (e != null && c == null) {
                c = e.getClass();
            }
            if (!map.containsKey(e)) {
                map.put(e, 0);
            }
            map.replace(e, map.get(e) + 1);
            size += 1;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (size() == 0) {
                return false;
            }
            if (c == null) {
                if (o == null) {
                    if (map.containsKey(null)) {
                        map.replace(null, map.get(null) - 1);
                        size -= 1;
                        if (map.get(null) == 0) {
                            map.remove(null);
                        }
                        return true;
                    }
                }
            } else if (c.isInstance(o)) {
                @SuppressWarnings(value = "unchecked")
                E oelt = (E) o;
                
                if (map.containsKey(oelt)) {
                    map.replace(oelt, map.get(oelt) - 1);
                    size -= 1;
                    if (map.get(oelt) == 0) {
                        map.remove(oelt);
                    }
                    return true;
                }
            } // this is all very useful code so I'll keep it - but in practice it is not needed, oops lol
            return false;
        }
    }

    public static class DoubleHashMap<A, B> extends AbstractMap<A, B> { // once again, can make abstract. However, the reverse should still be a HashMap
        private HashMap<A, B> first = new HashMap<>();
        private HashMap<B, HashSet<A>> second = new HashMap<>();

        @Override
        public int size() {
            return first.size();
        }

        @Override
        public boolean containsKey(Object key) {
            return first.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return second.containsKey(value);
        }

        @Override
        public B get(Object key) {
            return first.get(key);
        };

        public Collection<A> getKeysOfValue(Object value) {
            return second.get(value);
        }

        public A getFirstKeyOfValue(Object value) {
            if (containsValue(value)) {
                return second.get(value).iterator().next();
            } else {
                return null;
            }
        }

        @Override
        public B put(A key, B value) {

            if (!second.containsKey(value)) {
                second.put(value, new HashSet<>());
            }
            second.get(value).add(key);

            if (!first.containsKey(key)) {
                first.put(key, value);
                return null;
            } else {
                B old = first.replace(key, value);
                second.get(old).remove(key);
                if (second.get(old).size() == 0) {
                    second.remove(old);
                }
                return old;
            }
        }

        @Override
        public B remove(Object key) {
            if (first.containsKey(key)) {
                B old = first.remove(key);
                second.get(old).remove(key);
                if (second.get(old).size() == 0) {
                    second.remove(old);
                }
                return old;
            }
            return null;
        }

        @Override
        public void clear() {
            first.clear();
            second.clear();
        }

        @Override
        public Set<Entry<A, B>> entrySet() {
            return first.entrySet();
        }
    }
}
