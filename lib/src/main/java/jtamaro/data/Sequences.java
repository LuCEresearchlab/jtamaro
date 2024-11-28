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
   * Determines whether this is an empty sequence (i.e., it has no elements).
   *
   * @param <T> the type of the elements in the sequence
   * @param seq the sequence to deconstruct
   * @return true if the sequence is empty, false otherwise
   */
  public static <T> boolean isEmpty(Sequence<T> seq) {
    return seq.isEmpty();
  }

  /**
   * Returns the first element of a non-empty sequence.
   *
   * @param <T> the type of the elements in the sequence
   * @param seq the sequence to deconstruct
   * @return the first element of the given sequence
   */
  public static <T> T first(Sequence<T> seq) {
    return seq.first();
  }

  /**
   * Returns the rest of a non-empty sequence.
   *
   * @param <T> the type of the elements in the sequence
   * @param seq the sequence to deconstruct
   * @return the rest of the given sequence
   */
  public static <T> Sequence<T> rest(Sequence<T> seq) {
    return seq.rest();
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
    return map(x -> (char) x.shortValue(), rangeClosed((short) from, (short) to));
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
   * Reverse a Sequence.
   */
  public static <T> Sequence<T> reverse(Sequence<T> seq) {
    Sequence<T> result = new Empty<>();
    Sequence<T> itr = seq;
    while (!itr.isEmpty()) {
      result = new Cons<>(itr.first(), result);
      itr = itr.rest();
    }
    return result;
  }

  /**
   * Perform a mapping operation on each element of the sequence to produce another sequence.
   *
   * @param mapper The function applied to each element
   * @param seq    The sequence that is being mapped
   * @param <T>    Type of the original sequence
   * @param <U>    Type of the new sequence
   * @return The mapped sequence
   */
  public static <T, U> Sequence<U> map(Function1<T, U> mapper, Sequence<T> seq) {
    return foldRight(Sequences.empty(), (it, acc) -> new Cons<>(mapper.apply(it), acc), seq);
  }

  /**
   * Filter the elements of a sequence by testing a given predicate.
   *
   * @param predicate The test predicate. If this produces a <code>true</code> the tested element is
   *                  kept in the newly produced Sequence, otherwise it is discarded.
   * @param seq       The sequence that is being filtered
   * @param <T>       Type of the original sequence
   * @return The filtered sequence
   */
  public static <T> Sequence<T> filter(Function1<T, Boolean> predicate, Sequence<T> seq) {
    return foldRight(Sequences.empty(), (it, acc) -> predicate.apply(it)
        ? new Cons<>(it, acc)
        : acc, seq);
  }

  /**
   * Perform a reduction on a given sequence.
   *
   * @param initial The neutral element of the reduction (initial reduction value)
   * @param reducer The function that takes an element of the sequence and an accumulator and
   *                produces a reduced value
   * @param seq     The sequence that is being reduced
   * @param <T>     Type of the original sequence
   * @param <U>     Type of the reduced value
   * @return The accumulated result of the reduction
   */
  public static <T, U> U reduce(U initial, Function2<T, U, U> reducer, Sequence<T> seq) {
    return foldRight(initial, reducer, seq);
  }

  /**
   * Fold a sequence on the right.
   *
   * @param initial The neutral element of the reduction (initial folding value)
   * @param reducer The function that takes an element of the sequence and an accumulator and
   *                produces a folded value
   * @param seq     The sequence that is being folded
   * @param <T>     Type of the original sequence
   * @param <U>     Type of the reduced value
   * @return The accumulated result of the fold
   */
  public static <T, U> U foldRight(U initial, Function2<T, U, U> reducer, Sequence<T> seq) {
    U acc = initial;
    for (Sequence<T> itr = reverse(seq);
         !itr.isEmpty();
         itr = itr.rest()) {
      acc = reducer.apply(itr.first(), acc);
    }
    return acc;
  }

  /**
   * Fold a sequence on the left.
   *
   * @param initial The neutral element of the reduction (initial folding value)
   * @param reducer The function that takes the accumulator and an element of the sequence and
   *                produces a folded value
   * @param seq     The sequence that is being folded
   * @param <T>     Type of the original sequence
   * @param <U>     Type of the reduced value
   * @return The accumulated result of the fold
   */
  public static <T, U> U foldLeft(U initial, Function2<U, T, U> reducer, Sequence<T> seq) {
    U acc = initial;
    for (Sequence<T> itr = seq;
         !itr.isEmpty();
         itr = itr.rest()) {
      acc = reducer.apply(acc, itr.first());
    }
    return acc;
  }

  /**
   * Perform a flat-mapping operation on each element of the sequence to produce another sequence.
   *
   * @param mapper The function applied to each element. It produces a sequence that is concatenated
   *               with the results of the mapping of other elements
   * @param seq    The sequence that is being flat-mapped
   * @param <T>    Type of the original sequence
   * @param <U>    Type of the new sequence
   * @return The flat-mapped sequence
   */
  public static <T, U> Sequence<U> flatMap(Function1<T, Sequence<U>> mapper, Sequence<T> seq) {
    return foldLeft(
        empty(),
        (acc, it) -> concat(acc, mapper.apply(it)),
        seq
    );
  }

  /**
   * Take up to the first n elements of a sequence.
   *
   * @param count The number of elements to take from the given sequence.
   * @param seq   The given sequence
   * @return A sequence of length <code>min(length(seq), n)</code> containing up to the first n
   * elements of seq
   */
  public static <T> Sequence<T> take(int count, Sequence<T> seq) {
    Sequence<T> result = new Empty<>();
    Sequence<T> itr = seq;
    int i = 0;
    while (!itr.isEmpty() && i++ < count) {
      result = new Cons<>(itr.first(), result);
      itr = itr.rest();
    }
    return reverse(result);
  }

  /**
   * Drop up to the first n elements of a sequence.
   *
   * @param count The number of elements to drop from the given sequence.
   * @param seq   The given sequence
   * @return A sequence of length <code>min(length(seq) - n, 0)</code> containing up to the
   * remaining <code>length(seq) - n</code> elements of seq
   */
  public static <T> Sequence<T> drop(int count, Sequence<T> seq) {
    Sequence<T> result = seq;
    int i = 0;
    while (!result.isEmpty() && i++ < count) {
      result = result.rest();
    }
    return result;
  }

  /**
   * Concatenate two sequences.
   *
   * @param first  The first sequence (prepended)
   * @param second The second sequence (appended)
   * @param <T>    The type of the elements of the produced sequence
   */
  public static <T> Sequence<T> concat(Sequence<T> first, Sequence<T> second) {
    if (second.isEmpty()) {
      // Not necessary, but avoid double reversing a
      return first;
    } else {
      Sequence<T> itr = reverse(first);
      Sequence<T> result = second;
      while (!itr.isEmpty()) {
        result = new Cons<>(itr.first(), result);
        itr = itr.rest();
      }
      return result;
    }
  }

  /**
   * Inserts an element between all elements of this Sequence.
   *
   * <p><code>intersperse("-", of("1", "2", "3")) === of("1", "-", "2", "-", "3")</code>
   *
   * @param element The element to insert
   * @param seq     The original sequence
   * @param <T>     The type of the elements of the produced sequence
   * @return A sequence with the elements of the original sequence internally separated by
   * <code>element</code>
   */
  public static <T> Sequence<T> intersperse(T element, Sequence<T> seq) {
    if (seq.isEmpty()) {
      return seq;
    } else {
      return new Cons<>(seq.first(),
          fromStream(seq.rest().stream().flatMap(it -> Stream.of(element, it))));
    }
  }

  /**
   * Returns a Sequence formed by combining corresponding elements in pairs from two given
   * sequences.
   *
   * <p>If one of the two sequences is longer than the other, its remaining elements are ignored.
   * The length of the returned sequence is the minimum of the lengths of the two sequences.
   *
   * @param first  The first sequence of this operation. Its element will appear in the
   *               {@link Pair#first()} of the produced sequence
   * @param second The second sequence of this operation. Its element will appear in the
   *               {@link Pair#second()} of the produced sequence
   * @param <A>    The type of the elements of the first sequence
   * @param <B>    The type of the elements of the second sequence
   */
  public static <A, B> Sequence<Pair<A, B>> zip(Sequence<A> first, Sequence<B> second) {
    Sequence<Pair<A, B>> result = new Empty<>();
    Sequence<A> itrFirst = first;
    Sequence<B> itrSecond = second;
    while (!(itrFirst.isEmpty() || itrSecond.isEmpty())) {
      result = new Cons<>(new Pair<>(itrFirst.first(), itrSecond.first()), result);
      itrFirst = itrFirst.rest();
      itrSecond = itrSecond.rest();
    }
    return reverse(result);
  }

  /**
   * Zips a sequence with its indices (starting from 0).
   *
   * @param seq The sequence to zip with indices.
   * @param <T> The type of the elements of the given sequence
   */
  public static <T> Sequence<Pair<T, Integer>> zipWithIndex(Sequence<T> seq) {
    Sequence<T> itr = seq;
    Sequence<T> queue = new Empty<>();
    int count = 0;
    while (!itr.isEmpty()) {
      queue = new Cons<>(itr.first(), queue);
      itr = itr.rest();
      count++;
    }

    Sequence<Pair<T, Integer>> result = new Empty<>();
    while (!queue.isEmpty()) {
      result = new Cons<>(new Pair<>(queue.first(), --count), result);
      queue = queue.rest();
    }

    return result;
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
    Sequence<Pair<A, B>> itr = reverse(seq);
    while (!itr.isEmpty()) {
      a = new Cons<>(itr.first().first(), a);
      b = new Cons<>(itr.first().second(), b);
      itr = itr.rest();
    }
    return new Pair<>(a, b);
  }

  /**
   * Perform a cross-product between two sequences.
   *
   * <p><code>of(1, 2) × of("a", "b") === of((1, "a"), (1, "b"), (2, "a"), (2, "b"))</code>
   *
   * @param first  The first sequence of this operation. Its element will appear in the
   *               {@link Pair#first()} of the produced sequence
   * @param second The second sequence of this operation. Its element will appear in the
   *               {@link Pair#second()} of the produced sequence
   * @param <A>    The type of the elements of the first sequence
   * @param <B>    The type of the elements of the second sequence
   */
  public static <A, B> Sequence<Pair<A, B>> crossProduct(Sequence<A> first, Sequence<B> second) {
    return foldLeft(
        empty(),
        (acc, a) -> concat(
            acc,
            map(b -> new Pair<>(a, b), second)
        ),
        first
    );
  }

  private static <T> Sequence<T> fromIterator(Iterator<T> iterator) {
    Sequence<T> result = new Empty<>();
    while (iterator.hasNext()) {
      result = new Cons<>(iterator.next(), result);
    }
    return reverse(result);
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
