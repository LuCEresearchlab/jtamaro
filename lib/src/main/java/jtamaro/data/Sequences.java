package jtamaro.data;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Static methods for working with sequences.
 *
 * @see jtamaro.data.Sequence
 */
public final class Sequences {

  private Sequences() {
  }

  /**
   * Constructs an empty sequence.
   *
   * @param <T> the type of the elements in the sequence
   * @return a new empty sequence
   */
  public static <T> Sequence<T> empty() {
    return new Empty<>();
  }

  /**
   * Constructs a sequence that consists of the given first element in front of the given rest
   * (prepends an element to the given rest).
   *
   * @param <T>   the type of the elements in the sequence
   * @param first the first element in the new sequence
   * @param rest  the rest of the elements in the new sequence
   * @return a new Sequence that consists of the given first in front of the given rest
   */
  public static <T> Sequence<T> cons(T first, Sequence<T> rest) {
    return new Cons<>(first, rest);
  }

  /**
   * Constructs a sequence that consists of one given element.
   *
   * @param <T>     the type of the elements in the sequence
   * @param element the element in the new sequence
   * @return a new sequence that consists of the given element
   */
  public static <T> Sequence<T> of(T element) {
    return new Cons<>(element, new Empty<>());
  }

  /**
   * Constructs a sequence that consists of the two given elements.
   *
   * @param <T> the type of the elements in the sequence
   * @param a   the first element in the new sequence
   * @param b   the second element in the new sequence
   * @return a new sequence that consists of the two given elements
   */
  public static <T> Sequence<T> of(T a, T b) {
    return new Cons<>(a, new Cons<>(b, new Empty<>()));
  }

  /**
   * Constructs a sequence that consists of the given elements.
   *
   * @param <T>      the type of the elements in the sequence
   * @param elements the elements in the new sequence
   * @return a new sequence that consists of the given elements
   */
  @SafeVarargs
  public static <T> Sequence<T> of(T... elements) {
    Sequence<T> result = new Empty<>();
    for (int i = elements.length - 1; i >= 0; i--) {
      result = new Cons<>(elements[i], result);
    }
    return result;
  }

  /**
   * Constructs a sequence that consists of the characters of a given String.
   *
   * @param s the string which characters the sequence will consist of
   * @return a new sequence that consists of the characters of the given String
   */
  public static Sequence<Character> ofStringCharacters(String s) {
    Sequence<Character> result = new Empty<>();
    for (int i = s.length() - 1; i >= 0; i--) {
      result = new Cons<>(s.charAt(i), result);
    }
    return result;
  }

  /**
   * Constructs a sequence that consists of the lines of a given String.
   *
   * @param s the string which lines the sequence will consist of
   * @return a new sequence that consists of the lines of the given String
   */
  public static Sequence<String> ofStringLines(String s) {
    return of(s.lines().toArray(String[]::new));
  }

  /**
   * Constructs a sequence of integers starting at 0, with the last element being
   * <code>toExclusive-1</code>.
   *
   * <p><code>range(3) === of(0, 1, 2)</code>
   *
   * @param toExclusive the number that would come right after the last value of the sequence
   * @return a new sequence of integers starting at 0, and ending just before the given number
   */
  public static Sequence<Integer> range(int toExclusive) {
    return range(0, toExclusive, 1);
  }

  /**
   * Constructs a sequence of integers starting at <code>from</code>, with the last element being
   * <code>toExclusive - 1</code>.
   *
   * <p><code>range(1, 3) === of(1, 2)</code>
   *
   * @param from        the value of the first element
   * @param toExclusive the number that would come right after the last value of the sequence
   */
  public static Sequence<Integer> range(int from, int toExclusive) {
    return range(from, toExclusive, 1);
  }

  /**
   * Constructs a sequence of integers starting at <code>from</code>, with the last element being
   * <code>toExclusive - 1</code> with each element being <code>previousElement + step</code>.
   *
   * <p><code>range(0, 8, 2) === of(0, 2, 4, 6)</code>
   *
   * @param from        the value of the first element
   * @param toExclusive the number that would come right after the last value of the sequence
   * @param step        the difference between an element of the sequence and its predecessor
   */
  public static Sequence<Integer> range(int from, int toExclusive, int step) {
    if (step == 0) {
      throw new IllegalArgumentException("Step must not be zero");
    } else if (from == toExclusive) {
      return new Empty<>();
    } else if ((step > 0 && from > toExclusive) || (step < 0 && from < toExclusive)) {
      return new Empty<>();
    } else {
      Sequence<Integer> seq = new Empty<>();
      int lastElement = getLastElementClosed(from, toExclusive, step);
      if (lastElement == toExclusive) {
        lastElement -= step;
      }

      for (int i = lastElement;
           step > 0 ? (i >= from) : (i <= from);
           i -= step) {
        seq = new Cons<>(i, seq);
      }
      return seq;
    }
  }

  /**
   * Constructs a sequence of floating point numbers starting at <code>from</code>, with the last
   * element being &lt; <code>toExclusive</code> with each element being <code>previousElement +
   * step</code>.
   *
   * <p><code>range(0, 1, 0.2) === of(0.0, 0.2, 0.4, 0.6, 0.8)</code>
   *
   * @param from        the value of the first element
   * @param toExclusive the number which all elements of the sequence are less than by at most
   *                    <code>step</code>
   * @param step        the difference between an element of the sequence and its predecessor
   */
  public static Sequence<Double> range(double from, double toExclusive, double step) {
    if (step == 0) {
      throw new IllegalArgumentException("Step must not be zero");
    } else if (from - toExclusive == 0.0) {
      return new Empty<>();
    } else if ((step > 0 && from > toExclusive) || (step < 0 && from < toExclusive)) {
      return new Empty<>();
    } else {
      Sequence<Double> seq = new Empty<>();
      double curr = from;
      do {
        seq = new Cons<>(curr, seq);
        curr += step;
      } while (step > 0 ? curr < toExclusive : curr > toExclusive);

      return seq.reverse();
    }
  }

  /**
   * Constructs a sequence of integers starting at <code>from</code>, with the last element being
   * <code>toExclusive</code>.
   *
   * <p><code>rangeClosed(1, 3) === of(1, 2, 3)</code>
   *
   * @param from the value of the first element
   * @param to   the value of the last element
   */
  public static Sequence<Integer> rangeClosed(int from, int to) {
    return rangeClosed(from, to, 1);
  }

  /**
   * Constructs a sequence of integers starting at <code>from</code>, with the last element being
   * <code>toExclusive</code>.
   *
   * <p><code>range(0, 8, 2) === of(0, 2, 4, 6, 8)</code>
   *
   * @param from the value of the first element
   * @param to   the value of which the last element is less than or equal
   * @param step the difference between an element of the sequence and its predecessor
   */
  public static Sequence<Integer> rangeClosed(int from, int to, int step) {
    if (step == 0) {
      throw new IllegalArgumentException("Step must not be zero");
    } else if (from == to) {
      return of(from);
    } else if ((step > 0 && from > to) || (step < 0 && from < to)) {
      return new Empty<>();
    } else {
      Sequence<Integer> seq = new Empty<>();
      final int lastElement = getLastElementClosed(from, to, step);
      for (int i = lastElement;
           step > 0 ? (i >= from) : (i <= from);
           i -= step) {
        seq = new Cons<>(i, seq);
      }
      return seq;
    }
  }

  /**
   * Constructs a sequence of characters starting at <code>from</code>, with the last element being
   * <code>toExclusive</code>.
   *
   * <p><code>rangeClosed('a', 'd') === of('a', 'b','c','d')</code>
   *
   * @param from the value of the first element
   * @param to   the value of the last element
   */
  public static Sequence<Character> rangeClosed(char from, char to) {
    return rangeClosed((short) from, (short) to)
        .map(x -> (char) x.shortValue());
  }

  /**
   * Constructs a sequence with the given <code>element</code> repeated <code>count</code> times.
   *
   * @param <T>     the type of the elements in the sequence
   * @param element the element to repeat
   * @param count   the number of times to repeat the element (the length of the resulting
   *                sequence)
   * @return a sequence consisting of the given element repeated count times
   */
  public static <T> Sequence<T> replicate(T element, int count) {
    Sequence<T> result = new Empty<>();
    for (int i = 0; i < count; i++) {
      result = new Cons<>(element, result);
    }
    return result;
  }

  /**
   * Construct a sequence from an {@link Iterable}.
   */
  public static <T> Sequence<T> fromIterable(Iterable<T> iterable) {
    return fromIterator(iterable.iterator());
  }

  /**
   * Collect a {@link Stream} into a Sequence.
   */
  public static <T> Sequence<T> fromStream(Stream<T> stream) {
    return fromIterator(stream.iterator());
  }

  /**
   * Unzips the element of a given sequence by mapping them to a pair of distinct sequences.
   *
   * @param seq The sequence to unzip
   * @param <A> Type of the {@link Pair#first()} of the elements of the original sequence
   * @param <B> Type of the {@link Pair#second()} of the elements of the original sequence
   * @return A pair containing two sequences, each with the first and second elements of the pairs
   * of the original sequence respectively
   */
  public static <A, B> Pair<Sequence<A>, Sequence<B>> unzip(Sequence<Pair<A, B>> seq) {
    Sequence<A> a = new Empty<>();
    Sequence<B> b = new Empty<>();
    Sequence<Pair<A, B>> itr = seq.reverse();
    while (itr instanceof Cons(Pair(A p1, B p2), Sequence<Pair<A, B>> rest)) {
      a = new Cons<>(p1, a);
      b = new Cons<>(p2, b);
      itr = rest;
    }
    return new Pair<>(a, b);
  }

  private static <T> Sequence<T> fromIterator(Iterator<T> iterator) {
    Sequence<T> result = new Empty<>();
    while (iterator.hasNext()) {
      result = new Cons<>(iterator.next(), result);
    }
    return result.reverse();
  }

  private static int getLastElementClosed(int start, int end, int step) {
    if (step > 0) {
      return start < end
          ? end - mod(mod(end, step) - mod(start, step), step)
          : end;
    } else if (step < 0) {
      return start > end
          ? end + mod(mod(start, -step) - mod(end, -step), -step)
          : end;
    } else {
      throw new IllegalArgumentException("Step can't be zero");
    }
  }

  private static int mod(int a, int b) {
    final int m = a % b;
    return m < 0 ? m + b : m;
  }
}
