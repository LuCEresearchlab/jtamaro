package jtamaro.en;

import java.util.stream.Stream;

import jtamaro.en.data.Cons;
import jtamaro.en.data.Empty;
import jtamaro.en.data.IteratorCell;
import jtamaro.en.data.LazyCons;

/**
 * Static methods for working with sequences.
 *
 * @see jtamaro.en.Sequence
 */
public final class Sequences {

  // prevent instantiation
  private Sequences() {
  }


  //--- queries

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons~3f%29%29
  //
  // We deliberately do not provide isCons, because we have various kinds of cons cells
  // (e.g., Cons, LazyCons, and IteratorCell).
  // Students should call isEmpty instead.
  //
  ///**
  // * Determines whether the sequence has at least one element.
  // *
  // * @return true if the sequence has at least one element, false otherwise.
  // */
  //public static <T> boolean isCons(Sequence<T> sequence) {
  //  return !sequence.isEmpty();
  //}

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._empty~3f%29%29

  /**
   * Determines whether this is an empty sequence
   * (i.e., it has no elements).
   *
   * @return true of the sequence is empty, false otherwise.
   */
  public static <T> boolean isEmpty(Sequence<T> sequence) {
    return sequence.isEmpty();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._first%29%29

  /**
   * Returns the first element of a non-empty sequence.
   *
   * @return The first element of the given sequence.
   */
  public static <T> T first(Sequence<T> sequence) {
    assert !sequence.isEmpty() : "Cannot get the first element of an empty sequence";
    return sequence.first();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._rest%29%29

  /**
   * Returns the rest of a non-empty sequence.
   *
   * @return The rest of the given sequence.
   */
  public static <T> Sequence<T> rest(Sequence<T> sequence) {
    assert !sequence.isEmpty() : "Cannot get the rest of an empty sequence";
    return sequence.rest();
  }


  //--- construction

  /**
   * Create an empty Sequence.
   *
   * @param <T> The type of the elements in the Sequence
   * @return A new empty Sequence.
   */
  public static <T> Sequence<T> empty() {
    return new Empty<>();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons%29%29

  /**
   * Constructs a new Sequence that consists of the given first element in front of the given rest
   * (prepends an element to the given rest).
   *
   * @param <T>   The type of the elements in the Sequence
   * @param first The first element in the new Sequence
   * @param rest  The rest of the elements in the new Sequence
   * @return A new Sequence that consists of the given first in front of the given rest.
   */
  public static <T> Sequence<T> cons(T first, Sequence<T> rest) {
    return new Cons<>(first, rest);
  }

  /**
   * Create a LazyCons cell, which is needed to support infinite sequences.
   * Instead of providing the rest, we provide a lambda that will produce the rest on-demand.
   */
  private static <T> Sequence<T> lazyCons(T first, Function0<Sequence<T>> restSupplier, boolean hasDefiniteSize) {
    return new LazyCons<>(first, restSupplier, hasDefiniteSize);
  }

  //--- lazy construction sequences
  /**
   * Create a Sequence of integers starting from the given integer, counting upwards.
   * The sequence is infinite;
   * once Integer.MAX_VALUE is reached, it wraps around to Integer.MIN_VALUE
   * and continues from there:
   * 
   * from, from+1, ..., Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE+1, ...
   * 
   * @param from the value of the first element
   * @return a new Sequence starting with the given number and counting up.
   */
  public static Sequence<Integer> from(int from) {
    return lazyCons(from, () -> from(from + 1), false);
  }

  /**
   * Create a Sequence of integers starting at 0,
   * with the last element being toExclusive - 1.
   * 
   * <code>range(3) === of(0, 1, 2)</code>
   * 
   * @param toExclusive the number that would come right after the last value of the sequence
   * @return a new Sequence of integers starting at 0, and ending just before the given number.
   */
  public static Sequence<Integer> range(int toExclusive) {
    return range(0, toExclusive);
  }

  /**
   * Create a Sequence of integers starting at from,
   * with the last element being toExclusive - 1.
   * 
   * <code>range(1, 3) === of(1, 2)</code>
   * 
   * @param from the value of the first element
   * @param toExclusive the number that would come right after the last value of the sequence
   * @return a new Sequence of integers starting at 0, and ending just before the given number.
   */
  public static Sequence<Integer> range(int from, int toExclusive) {
    return range(from, toExclusive, 1);
  }

  public static Sequence<Integer> range(int from, int toExclusive, int step) {
    assert step != 0 : "step must not be zero";
    if (from == toExclusive) {
      return empty();
    } else if (step > 0) {
      if (from > toExclusive) {
        return empty();
      } else {
        return lazyCons(from, () -> range(from + step, toExclusive, step), true);
      }
    } else {
      if (from < toExclusive) {
        return empty();
      } else {
        return lazyCons(from, () -> range(from + step, toExclusive, step), true);
      }
    }
  }

  public static Sequence<Integer> rangeClosed(int to) {
    return rangeClosed(0, to, 1);
  }

  public static Sequence<Integer> rangeClosed(int from, int to) {
    return rangeClosed(from, to, 1);
  }

  public static Sequence<Integer> rangeClosed(int from, int to, int step) {
    assert step != 0 : "step must not be zero";
    if (from == to) {
      return cons(from, empty());
    } else if (step > 0) {
      if (from > to) {
        return empty();
      } else {
        return lazyCons(from, () -> rangeClosed(from + step, to, step), true);
      }
    } else {
      if (from < to) {
        return empty();
      } else {
        return lazyCons(from, () -> rangeClosed(from + step, to, step), true);
      }
    }
  }

  public static Sequence<Character> range(char toExclusive) {
    return range((char) 0, toExclusive);
  }

  public static Sequence<Character> range(char from, char toExclusive) {
    return range(from, toExclusive, 1);
  }

  public static Sequence<Character> range(char from, char toExclusive, int step) {
    return map(i -> (char) i.intValue(), range((int) from, (int) toExclusive, step));
  }

  public static Sequence<Character> rangeClosed(char to) {
    return rangeClosed((char) 0, to);
  }

  public static Sequence<Character> rangeClosed(char from, char to) {
    return rangeClosed(from, to, 1);
  }

  public static Sequence<Character> rangeClosed(char from, char to, int step) {
    return map(i -> (char) i.intValue(), rangeClosed((int) from, (int) to, step));
  }

  public static <T> Sequence<T> repeat(T element) {
    return lazyCons(element, () -> repeat(element), false);
  }

  public static <T> Sequence<T> replicate(T element, int count) {
    assert count >= 0 : "n must be non-negative";
    if (count == 0) {
      return empty();
    } else {
      return cons(element, replicate(element, count - 1));
    }
  }

  public static <T> Sequence<T> replicateLazy(T element, int count) {
    assert count >= 0 : "n must be non-negative";
    if (count == 0) {
      return empty();
    } else {
      return lazyCons(element, () -> replicateLazy(element, count - 1), true);
    }
  }

  public static <T> Sequence<T> cycle(Sequence<T> sequence) {
    assert !sequence.isEmpty() : "Empty sequences cannot be cycled";
    return cycle(sequence, sequence);
  }

  private static <T> Sequence<T> cycle(Sequence<T> sequence, Sequence<T> cycle) {
    if (sequence.isEmpty()) {
      return cycle(cycle);
    } else {
      return lazyCons(sequence.first(), () -> cycle(sequence.rest(), cycle), false);
    }
  }

  public static <T> Sequence<T> iterate(Function1<T, T> f, T seed) {
    return lazyCons(seed, () -> iterate(f, f.apply(seed)), false);
  }

  public static <T> Sequence<T> take(int n, Sequence<T> sequence) {
    assert n >= 0 : "n must be non-negative";
    if (n == 0 || sequence.isEmpty()) {
      return empty();
    } else {
      return lazyCons(sequence.first(), () -> take(n - 1, sequence.rest()), true);
    }
  }

  public static <T> Sequence<T> drop(int n, Sequence<T> sequence) {
    assert n >= 0 : "n must be non-negative";
    while (n-- > 0 && !sequence.isEmpty()) {
      sequence = sequence.rest();
    }
    return sequence;
  }

  public static <T, U> Sequence<U> map(Function1<T, U> f, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return empty();
    } else {
      return lazyCons(f.apply(sequence.first()), () -> map(f, sequence.rest()), sequence.rest().hasDefiniteSize());
    }
  }

  public static <T> Sequence<String> mapToString(Sequence<T> sequence) {
    return map(Object::toString, sequence);
  }

  public static <T, U> Sequence<U> flatMap(Function1<T, Sequence<U>> f, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return empty();
    } else {
      return concat(f.apply(sequence.first()), flatMap(f, sequence.rest()));
    }
  }

  public static <T> Sequence<T> filter(Function1<T, Boolean> predicate, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return sequence;
    } else {
      Sequence<T> current = sequence;
      while (!current.isEmpty() && !predicate.apply(current.first())) {
        current = current.rest();
      }
      final Sequence<T> finalCurrent = current;
      return current.isEmpty() ? empty()
          : lazyCons(current.first(), () -> filter(predicate, finalCurrent.rest()), finalCurrent.rest().hasDefiniteSize());
    }
  }

  // Naming inspired by:
  // https://www.javadoc.io/static/io.vavr/vavr/1.0.0-alpha-4/io/vavr/collection/Seq.html#intersperse-T-
  public static <T> Sequence<T> intersperse(T element, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return sequence;
    } else {
      return lazyCons(sequence.first(), () -> {
        final Sequence<T> rest = sequence.rest();
        return rest.isEmpty() ? rest : lazyCons(element, () -> intersperse(element, rest), rest.hasDefiniteSize());
      }, sequence.rest().hasDefiniteSize());
    }
  }

  public static <T> Sequence<T> concat(Sequence<T> firstPart, Sequence<T> secondPart) {
    if (firstPart.isEmpty()) {
      return secondPart;
    } else {
      return lazyCons(firstPart.first(), () -> concat(firstPart.rest(), secondPart), firstPart.hasDefiniteSize() && secondPart.hasDefiniteSize());
    }
  }

  public static <T, U> Sequence<Pair<T, U>> zip(Sequence<T> first, Sequence<U> second) {
    if (first.isEmpty() || second.isEmpty()) {
      return empty();
    } else {
      return lazyCons(new Pair<>(first.first(), second.first()),
          () -> zip(first.rest(), second.rest()),
          first.rest().hasDefiniteSize() || second.rest().hasDefiniteSize());
    }
  }

  public static <T, U> Sequence<Pair<T, Integer>> zipWithIndex(Sequence<T> sequence) {
    return zip(sequence, from(0));
  }

  public static <F, S> Pair<Sequence<F>, Sequence<S>> unzip(Sequence<Pair<F, S>> sequence) {
    if (sequence.isEmpty()) {
      return new Pair<>(empty(), empty());
    } else {
      return new Pair<>(
          lazyCons(
              sequence.first().first(),
              () -> unzip(sequence.rest()).first(),
              sequence.rest().hasDefiniteSize()
          ),
          lazyCons(
              sequence.first().second(),
              () -> unzip(sequence.rest()).second(),
              sequence.rest().hasDefiniteSize()
          )
      );
    }
  }

  public static <T, U> Sequence<Pair<T, U>> crossProduct(Sequence<T> first, Sequence<U> second) {
    if (first.isEmpty() || second.isEmpty()) {
      return empty();
    } else {
      return flatMap(f -> map(s -> new Pair<>(f, s), second), first);
    }
  }


  //--- folding
  public static <R, E> R foldl(R initial, Function2<R, E, R> f, Sequence<E> sequence) {
    return isEmpty(sequence) 
      ? initial 
      : foldl(f.apply(initial, first(sequence)), f, rest(sequence));
  }

  public static <R, E> R foldr(R initial, Function2<E, R, R> f, Sequence<E> sequence) {
    return isEmpty(sequence)
      ? initial
      : f.apply(first(sequence), foldr(initial, f, rest(sequence)));
  }

  //TODO: make reduce call foldl? Update workbooks wrt f's parameter order?
  public static <T, U> U reduce(Function2<U, T, U> f, U initial, Sequence<T> sequence) {
    U result = initial;
    for (T element : sequence) {
      result = f.apply(result, element);
    }
    return result;
  }

  //--- corresponding to PF1 "BSL with List Abbreviations"
  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._list%29%29

  /**
   * Constructs a new Sequence that consists of the given elements.
   *
   * @param <T>      The type of the elements in the Sequence
   * @param elements The elements in the new Sequence
   * @return A new Sequence that consists of the given elements.
   */
  @SafeVarargs
  public static <T> Sequence<T> of(T... elements) {
    Sequence<T> result = empty();
    for (int i = elements.length - 1; i >= 0; i--) {
      result = cons(elements[i], result);
    }
    return result;
  }

  //--- from Strings
  public static Sequence<Character> ofStringCharacters(String string) {
    Sequence<Character> result = empty();
    for (int i = string.length() - 1; i >= 0; i--) {
      result = cons(string.charAt(i), result);
    }
    return result;
  }

  public static Sequence<String> ofStringLinesBUGGY(String string) {
    final String[] lines = string.split(System.lineSeparator());
    Sequence<String> result = empty();
    for (int i = lines.length - 1; i >= 0; i--) {
      result = cons(lines[i], result);
    }
    return result;
  }

  /**
   * Returns a sequence of the lines in the given string (eager version).
   * This will eagerly split the string and build the entire sequence,
   * which means that the returned Sequence will have a definite size.
   * 
   * @param string the String to split into lines
   * @return a Sequence of the lines in the given string, with a definite size
   */
  public static Sequence<String> ofStringLines(String string) {
    final String[] lines = string.lines().toArray(String[]::new);
    Sequence<String> result = empty();
    for (int i = lines.length - 1; i >= 0; i--) {
      result = cons(lines[i], result);
    }
    return result;
  }

  /**
   * Returns a sequence of the lines in the given string (lazy version).
   * This will lazily split the string and build sequence cells as needed,
   * which means that the returned Sequence will not have a definite size.
   * 
   * @param string the String to split into lines
   * @return a Sequence of the lines in the given string, without a definite size
   */
  public static Sequence<String> ofStringLinesLazy(String string) {
    return IteratorCell.fromIterator(string.lines().iterator());
  }
  
  public static <T> Sequence<T> fromIterable(Iterable<T> iterable) {
    return IteratorCell.fromIterator(iterable.iterator());
  }

  public static <T> Sequence<T> fromStream(Stream<T> stream) {
    return IteratorCell.fromIterator(stream.iterator());
  }

}
