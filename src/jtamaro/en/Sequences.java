package jtamaro.en;

import java.util.function.Function;

import jtamaro.en.data.Cons;
import jtamaro.en.data.Cycle;
import jtamaro.en.data.Empty;
import jtamaro.en.data.Iterate;
import jtamaro.en.data.Map;
import jtamaro.en.data.Range;
import jtamaro.en.data.Repeat;
import jtamaro.en.data.Replicate;
import jtamaro.en.data.Take;

public class Sequences {

  //--- construct lazy sequences

  public static Sequence<Integer> range(int toExclusive) {
    return range(0, toExclusive);
  }

  public static Sequence<Integer> range(int from, int toExclusive) {
    return range(from, toExclusive, 1);
  }

  public static Sequence<Integer> range(int from, int toExclusive, int step) {
    return new Range(from, toExclusive, step);
  }

  public static <T> Sequence<T> repeat(T element) {
    return new Repeat<T>(element);
  }

  public static <T> Sequence<T> replicate(T element, int count) {
    return new Replicate<T>(element, count);
  }

  public static <T> Sequence<T> cycle(Sequence<T> sequence) {
    return new Cycle<T>(sequence);
  }

  public static <T> Sequence<T> iterate(Function<T, T> f, T seed) {
    return new Iterate<T>(f, seed);
  }

  public static <T> Sequence<T> take(int n, Sequence<T> sequence) {
    return new Take<T>(n, sequence);
  }

  //--- construct eager sequences

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons%29%29
  /**
   * Constructs a new Sequence that consists of the given head in front of the given tail.
   * @param <T> The type of the elements in the Sequence
   * @param head The first element in the new Sequence
   * @param tail The rest of the elements in the new Sequence
   * @return A new Sequence that consists of the given head in front of the given tail.
   */
  public static <T> Sequence<T> cons(T head, Sequence<T> tail) {
    return new Cons<T>(head, tail);
  }

  /**
   * Create an empty Sequence.
   * @param <T> The type of the elements in the Sequence
   * @return A new empty Sequence.
   */
  public static <T> Sequence<T> empty() {
    return new Empty<T>();
  }

  public static <T,U> Sequence<U> map(Function<T, U> f, Sequence<T> sequence) {
    return new Map<T,U>(f, sequence);
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
    Sequence<T> result = Sequences.empty();
    for (int i = elements.length - 1; i >= 0; i--) {
      result = Sequences.cons(elements[i], result);
    }
    return result;
  }

  //--- corresponding to Python list(range)
  public static Sequence<Integer> ofAll(Range range) {
    Sequence<Integer> result = Sequences.empty();
    for (Integer element : range) {
      result = Sequences.cons(element, result);
    }
    return result;
  }
}
