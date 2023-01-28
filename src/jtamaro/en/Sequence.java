package jtamaro.en;

import java.util.Iterator;

/**
 * A Sequence is a list of elements of type T,
 * it corresponds directly to what you called "List-of-T" in PF1.
 * See: https://htdp.org/2022-8-7/Book/part_two.html
 */
public abstract class Sequence<T> implements Iterable<T> {
  
  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._cons~3f%29%29
  /**
   * Determines whether this is a constructed sequence
   * (i.e., it has at least one element).
   * @return true of the sequence is constructed, false otherwise.
   */
  public abstract boolean isCons();

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._empty~3f%29%29
  /**
   * Determines whether this is an empty sequence
   * (i.e., it has no elements).
   * @return true of the sequence is empty, false otherwise.
   */
  public abstract boolean isEmpty();
 
  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._first%29%29
  /**
   * Returns the first element of a non-empty sequence.
   * @return The first element of this sequence.
   */
  public abstract T first();

  // https://docs.racket-lang.org/htdp-langs/beginner.html#%28def._htdp-beginner._%28%28lib._lang%2Fhtdp-beginner..rkt%29._rest%29%29
  /**
   * Returns the rest of a non-empty sequence.
   * @return The rest of this sequence.
   */
  public abstract Sequence<T> rest();

  //--- to allow use in for-each loop
  /**
   * Returns an iterator over the elements in this sequence.
   * @return An iterator over the elements in this sequence.
   */
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private Sequence<T> current = Sequence.this;
      public boolean hasNext() {
        return current.isCons();
      }
      public T next() {
        final T result = current.first();
        current = current.rest();
        return result;
      }
    };
  }

  //--- should we add these?
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
