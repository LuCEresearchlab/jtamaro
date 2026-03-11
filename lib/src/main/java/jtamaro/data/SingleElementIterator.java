package jtamaro.data;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple {@link Iterator} implementation that iterates over a single element.
 */
final class SingleElementIterator<T> implements Iterator<T> {

  private final T element;

  boolean consumed;

  SingleElementIterator(T element) {
    this.element = element;
    this.consumed = false;
  }

  @Override
  public boolean hasNext() {
    return !consumed;
  }

  @Override
  public T next() {
    if (consumed) {
      // According to specification of Iterator#next
      throw new NoSuchElementException();
    } else {
      consumed = true;
      return element;
    }
  }
}
