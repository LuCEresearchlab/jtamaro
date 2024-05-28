package jtamaro.data;

import java.util.stream.Stream;

record Empty<T>() implements Sequence<T> {

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public T first() {
    throw new UnsupportedOperationException("Empty sequence has no first element");
  }

  @Override
  public Sequence<T> rest() {
    throw new UnsupportedOperationException("Empty sequence has no rest");
  }

  @Override
  public Stream<T> stream() {
    return Stream.empty();
  }
}
