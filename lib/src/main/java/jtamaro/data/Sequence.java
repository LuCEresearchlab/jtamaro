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
public sealed interface Sequence<T>
    extends Iterable<T>
    permits Cons, Empty {

  /**
   * Determines whether this is an empty sequence (i.e., it has no elements).
   *
   * @return true if the sequence is empty, false otherwise.
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
   * Perform a mapping operation on each element of this sequence to produce another sequence.
   *
   * @param mapper The function applied to each element
   * @param <U>    Type of the new sequence
   * @return The mapped sequence
   */
  <U> Sequence<U> map(Function1<T, U> mapper);

  /**
   * Filter the elements of this sequence by testing a given predicate.
   *
   * @param predicate The test predicate. If this produces a <code>true</code> the tested element is
   *                  kept in the newly produced Sequence, otherwise it is discarded.
   * @return The filtered sequence
   */
  Sequence<T> filter(Function1<T, Boolean> predicate);

  /**
   * Perform a reduction on this sequence.
   *
   * @param initial The neutral element of the reduction (initial folding value)
   * @param reducer The function that takes an element of the sequence and an accumulator as
   *                arguments and produces a folded value
   * @param <U>     The type of the reduced value
   * @return The accumulated result of the reduction
   */
  default <U> U reduce(U initial, Function2<T, U, U> reducer) {
    return foldRight(initial, reducer);
  }

  /**
   * Fold this sequence on the right.
   *
   * @param initial The neutral element of the reduction (initial folding value)
   * @param reducer The function that takes an element of the sequence and an accumulator as
   *                arguments and produces a folded value
   * @param <U>     The type of the reduced value
   * @return The accumulated result of the folding
   */
  <U> U foldRight(U initial, Function2<T, U, U> reducer);

  /**
   * Fold this sequence on the left.
   *
   * @param initial The neutral element of the reduction (initial folding value)
   * @param reducer The function that takes the accumulator and an element of the sequence as
   *                arguments and produces a folded value
   * @param <U>     The type of the reduced value
   * @return The accumulated result of the folding
   */
  <U> U foldLeft(U initial, Function2<U, T, U> reducer);

  /**
   * Perform a flat-mapping operation on each element of this sequence to produce another sequence.
   *
   * @param mapper The function applied to each element. It produces a sequence that is concatenated
   *               with the results of the mapping of other elements
   * @param <U>    Type of the new sequence
   * @return The flat-mapped sequence
   */
  <U> Sequence<U> flatMap(Function1<T, Sequence<U>> mapper);

  /**
   * Returns a sequence that is the reverse of this one.
   *
   * <p>Invoking this method twice should return a Sequence that is equal to
   * this sequence: <code>s.equals(s.reversed().reversed())</code>.
   *
   * @return A reversed sequence
   */
  Sequence<T> reverse();

  /**
   * Take up to the first n elements of this sequence.
   *
   * @param count The number of elements to take from the given sequence.
   * @return A sequence of length <code>min(length(seq), n)</code> containing up to the first n
   * elements of seq
   */
  Sequence<T> take(int count);

  /**
   * Drop up to the first n elements of this sequence.
   *
   * @param count The number of elements to drop from the given sequence.
   * @return A sequence of length <code>min(length(seq) - n, 0)</code> containing up to the
   * remaining <code>length(seq) - n</code> elements of seq
   */
  Sequence<T> drop(int count);

  /**
   * Concatenate this sequence with another one.
   *
   * @param other The other sequence (appended)
   * @return A sequence that contains first all the elements of this sequence and then all the
   * elements of the other sequence
   */
  Sequence<T> concat(Sequence<T> other);

  /**
   * Inserts an element between all elements of this sequence.
   *
   * <p><code>intersperse("-", of("1", "2", "3")) === of("1", "-", "2", "-", "3")</code>
   *
   * @param element The element to insert
   * @return A sequence with the elements of the original sequence internally separated by
   * <code>element</code>
   */
  Sequence<T> intersperse(T element);

  /**
   * Returns a Sequence formed by combining corresponding elements in pairs from this sequence and
   * another given sequence.
   *
   * <p>If one of the two sequences is longer than the other, its remaining elements are ignored.
   * The length of the returned sequence is the minimum of the lengths of the two sequences.
   *
   * @param that The other sequence of this operation. Its element will appear in the
   *             {@link Pair#second()} of the produced sequence
   * @param <U>  The type of the elements of the other sequence
   */
  <U> Sequence<Pair<T, U>> zipWith(Sequence<U> that);

  /**
   * Zips the elements of this sequence with their indices (starting from 0).
   */
  Sequence<Pair<T, Integer>> zipWithIndex();

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
