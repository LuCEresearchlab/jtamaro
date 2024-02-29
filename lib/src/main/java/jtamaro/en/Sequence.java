package jtamaro.en;

import java.util.Iterator;

/**
 * A Sequence is a list of elements of type T,
 * it corresponds directly to what you called "List-of-T" in PF1.
 *
 * <p>To work with a Sequence, you can use the methods in the Sequences class.
 *
 * @see <a href="https://htdp.org/2022-8-7/Book/part_two.html">HTDP</a>
 * @see Sequences
 */
public abstract class Sequence<T> implements Iterable<T> {

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._empty~3f%29%29

  /**
   * Determines whether this is an empty sequence
   * (i.e., it has no elements).
   *
   * @return true of the sequence is empty, false otherwise.
   */
  public abstract boolean isEmpty();

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._first%29%29

  /**
   * Returns the first element of a non-empty sequence.
   *
   * @return The first element of this sequence.
   */
  public abstract T first();

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._rest%29%29

  /**
   * Returns the rest of a non-empty sequence.
   *
   * @return The rest of this sequence.
   */
  public abstract Sequence<T> rest();

  /**
   * Checks if this Sequence is known to have a finite size.
   *
   * @return true if this Sequence is known to have a finite size, false otherwise.
   */
  public abstract boolean hasDefiniteSize();

  //--- to allow use in for-each loop

  /**
   * Returns an iterator over the elements in this sequence.
   *
   * @return An iterator over the elements in this sequence.
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterator<>() {
      private Sequence<T> current = Sequence.this;

      @Override
      public boolean hasNext() {
        return !current.isEmpty();
      }

      @Override
      public T next() {
        final T result = current.first();
        current = current.rest();
        return result;
      }
    };
  }

  //--- TODO: Should we add get, and indexof, and length, and even set?
  //--- TODO: If yes, move all that into the Sequences class.
  public T get(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("index must be non-negative");
    }
    Sequence<T> current = this;
    while (index > 0) {
      if (current.isEmpty()) {
        throw new IndexOutOfBoundsException("index is too large");
      }
      current = current.rest();
      index--;
    }
    if (current.isEmpty()) {
      throw new IndexOutOfBoundsException("index is too large");
    }
    return current.first();
  }

}
