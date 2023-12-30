package jtamaro.en.data;

import jtamaro.en.Sequence;
import jtamaro.en.Sequences;

import java.util.Iterator;


/**
 * A lazy cons cell that gets its tail from an iterator.
 * Be careful! The Iterators provided as arguments must not be advanced afterwards,
 * because they will be lazily advanced by this code when this cell is traversed later!
 * User code should call Sequences.fromIterable() and Sequences.fromStream(),
 * to ensure that Iterators are fresh and not shared with user code.
 */
public class IteratorCell<T> extends Sequence<T> {

  private final T head;
  private final Iterator<T> tailIterator;
  private Sequence<T> tail;

  /**
   * Careful! After calling this method, the iterator must not be advanced anymore!
   * Don't expose this method to users.
   * Instead, provide Sequences.fromIterable() and Sequences.fromStream() methods,
   * which can produce fresh iterators that are not shared with user code.
   */
  public static <T> Sequence<T> fromIterator(Iterator<T> iterator) {
    // Be careful! Iterator is mutable!
    // We must not call iterator.next() more than once per element.
    // Thus we have to cache the result of IteratorCell's rest() method!
    return iterator.hasNext() ? new IteratorCell<>(iterator.next(), iterator) : Sequences.empty();
  }

  public IteratorCell(T head, Iterator<T> tailIterator) {
    this.head = head;
    this.tailIterator = tailIterator;
  }

  public T first() {
    return head;
  }

  public Sequence<T> rest() {
    // Be careful! Iterator is mutable! Cache result of call to next()!
    if (tail == null) {
      if (tailIterator.hasNext()) {
        tail = new IteratorCell<>(tailIterator.next(), tailIterator);
      } else {
        tail = Sequences.empty();
      }
    }
    return tail;
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean hasDefiniteSize() {
    return false;
  }

}
