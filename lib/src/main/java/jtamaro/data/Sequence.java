package jtamaro.data;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * A Sequence is an ordered list of elements of type <code>T</code>, it corresponds directly to what
 * you called "List-of-T" in <code>PF1</code>.
 *
 * <p>To work with a Sequence, you can use the methods in the {@link Sequences} class.
 *
 * @see <a href="https://htdp.org/2022-8-7/Book/part_two.html">HTDP</a>
 * @see Sequences
 */
public sealed interface Sequence<T> extends Iterable<T> permits Cons, Empty {

  /**
   * Determines whether this is an empty sequence (i.e., it has no elements).
   *
   * @return true of the sequence is empty, false otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the first element of a non-empty sequence.
   *
   * @return The first element of this sequence.
   */
  T first();

  /**
   * Returns the rest of a non-empty sequence.
   *
   * @return The rest of this sequence.
   */
  Sequence<T> rest();

  /**
   * Returns a sequential {@link Stream} with this sequence as its source.
   *
   * @return a {@link Stream}.
   */
  Stream<T> stream();

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an {@link Iterator}.
   */
  @Override
  default Iterator<T> iterator() {
    return new Iterator<>() {
      private Sequence<T> current = Sequence.this;

      @Override
      public boolean hasNext() {
        return !current.isEmpty();
      }

      @Override
      public T next() {
        final T value = current.first();
        current = current.rest();
        return value;
      }
    };
  }
}
