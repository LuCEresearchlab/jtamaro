package jtamaro.data;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

record Cons<T>(T first, Sequence<T> rest) implements Sequence<T> {

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public Stream<T> stream() {
    final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
        iterator(), Spliterator.IMMUTABLE);
    return StreamSupport.stream(spliterator, false);
  }
}
