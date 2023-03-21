package jtamaro.en;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import jtamaro.en.data.Cons;
import jtamaro.en.data.Empty;
import jtamaro.en.data.LazyCons;

/**
 * A collection of static methods for working with sequences in JTamaro.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public class Sequences {

  //--- queries
    
  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons~3f%29%29
  /**
   * Determines whether the sequence has at least one element.
   * @return true if the sequence has at least one element, false otherwise.
   */
  public static <T> boolean isCons(Sequence<T> sequence) {
    return !sequence.isEmpty();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._empty~3f%29%29
  /**
   * Determines whether this is an empty sequence
   * (i.e., it has no elements).
   * @return true of the sequence is empty, false otherwise.
   */
  public static <T> boolean isEmpty(Sequence<T> sequence) {
    return sequence.isEmpty();
  }
 
  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._first%29%29
  /**
   * Returns the first element of a non-empty sequence.
   * @return The first element of the given sequence.
   */
  public static <T> T first(Sequence<T> sequence) {
    return sequence.first();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._rest%29%29
  /**
   * Returns the rest of a non-empty sequence.
   * @return The rest of the given sequence.
   */
  public static <T> Sequence<T> rest(Sequence<T> sequence) {
    return sequence.rest();
  }


  //--- construction
  /**
   * Create an empty Sequence.
   * @param <T> The type of the elements in the Sequence
   * @return A new empty Sequence.
   */
  public static <T> Sequence<T> empty() {
    return new Empty<T>();
  }

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons%29%29
  /**
   * Constructs a new Sequence that consists of the given first element in front of the given rest
   * (prepends an element to the given rest).
   * @param <T> The type of the elements in the Sequence
   * @param first The first element in the new Sequence
   * @param rest The rest of the elements in the new Sequence
   * @return A new Sequence that consists of the given first in front of the given rest.
   */
  public static <T> Sequence<T> cons(T first, Sequence<T> rest) {
    return new Cons<T>(first, rest);
  }

  private static <T> Sequence<T> lazyCons(T first, Supplier<Sequence<T>> restSupplier, boolean hasDefiniteSize) {
    return new LazyCons<T>(first, restSupplier, hasDefiniteSize);
  }

  //--- lazy construction sequences
  public static Sequence<Integer> from(int from) {
    return lazyCons(from, () -> from(from + 1), false);
  }

  public static Sequence<Integer> range(int toExclusive) {
    return range(0, toExclusive);
  }

  public static Sequence<Integer> range(int from, int toExclusive) {
    return range(from, toExclusive, 1);
  }

  public static Sequence<Integer> range(int from, int toExclusive, int step) {
    assert step != 0: "step must not be zero";
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

  public static Sequence<Integer> rangeClosed(int from, int to) {
    return null; // TODO
  }

  public static Sequence<Integer> rangeClosed(int from, int to, int step) {
    return null; // TODO
  }

  public static Sequence<Character> range(char toExclusive) {
    return range((char)0, toExclusive);
  }

  public static Sequence<Character> range(char from, char toExclusive) {
    return range(from, toExclusive, 1);
  }

  public static Sequence<Character> range(char from, char toExclusive, int step) {
    return map(i -> (char) i.intValue(), range((int) from, (int) toExclusive, step));
  }

  public static Sequence<Character> rangeClosed(char from, char to) {
    return null; // TODO
  }

  public static Sequence<Character> rangeClosed(char from, char to, int step) {
    return null; // TODO
  }

  public static <T> Sequence<T> repeat(T element) {
    return lazyCons(element, () -> repeat(element), false);
  }

  public static <T> Sequence<T> replicate(T element, int count) {
    assert count >= 0 : "n must be non-negative";
    if (count == 0) {
      return empty();
    } else {
      return lazyCons(element, () -> replicate(element, count - 1), false);
    }
  }

  public static <T> Sequence<T> cycle(Sequence<T> sequence) {
    assert !sequence.isEmpty() : "Empty sequences cannot be cycled.";
    return cycle(sequence, sequence);
  }

  private static <T> Sequence<T> cycle(Sequence<T> sequence, Sequence<T> cycle) {
    if (sequence.isEmpty()) {
      return cycle(cycle);
    } else {
      return lazyCons(sequence.first(), () -> cycle(sequence.rest(), cycle), false);
    }
  }

  public static <T> Sequence<T> iterate(Function<T, T> f, T seed) {
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

  public static <T,U> Sequence<U> map(Function<T, U> f, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return empty();
    } else {
      return lazyCons(f.apply(sequence.first()), () -> map(f, sequence.rest()), sequence.rest().hasDefiniteSize());
    }
  }

  public static <T> Sequence<String> mapToString(Sequence<T> sequence) {
    return map(Object::toString, sequence);
  }

  public static <T,U> Sequence<U> flatMap(Function<T, Sequence<U>> f, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return empty();
    } else {
      return concat(f.apply(sequence.first()), flatMap(f, sequence.rest()));
    }
  }

  public static <T> Sequence<T> filter(Predicate<T> predicate, Sequence<T> sequence) {
    if (sequence.isEmpty()) {
      return sequence;
    } else {
      Sequence<T> current = sequence;
      while (!current.isEmpty() && !predicate.test(current.first())) {
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

  public static <T,U> Sequence<Pair<T,U>> zip(Sequence<T> first, Sequence<U> second) {
    if (first.isEmpty() || second.isEmpty()) {
      return empty();
    } else {
      return lazyCons(new Pair<>(first.first(), second.first()),
                      () -> zip(first.rest(), second.rest()),
                      first.rest().hasDefiniteSize() || second.rest().hasDefiniteSize());
    }
  }

  public static <T,U> Sequence<Pair<T,Integer>> zipWithIndex(Sequence<T> sequence) {
    return zip(sequence, from(0));
  }

  public static <F,S> Pair<Sequence<F>,Sequence<S>> unzip(Sequence<Pair<F,S>> sequence) {
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

  public static <T,U> Sequence<Pair<T,U>> crossProduct(Sequence<T> first, Sequence<U> second) {
    if (first.isEmpty() || second.isEmpty()) {
      return empty();
    } else {
      return flatMap(f -> map(s -> new Pair<>(f, s), second), first);
    }
  }


  //--- folding
  public static <T,U> U reduce(BiFunction<U, T, U> f, U initial, Sequence<T> sequence) {
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
   * @param <T> The type of the elements in the Sequence
   * @param elements The elements in the new Sequence
   * @return A new Sequence that consists of the given elements.
   */
  @SafeVarargs public static <T> Sequence<T> of(T... elements) {
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

  public static Sequence<String> ofStringLines(String string) {
    final String[] lines = string.split(System.lineSeparator());
    Sequence<String> result = empty();
    for (int i = lines.length - 1; i >= 0; i--) {
      result = cons(lines[i], result);
    }
    return result;
  }

}
