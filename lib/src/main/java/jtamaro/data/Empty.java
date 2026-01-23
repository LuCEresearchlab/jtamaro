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
  public <U> Sequence<U> map(Function1<T, U> mapper) {
    return new Empty<>();
  }

  @Override
  public Sequence<T> filter(Function1<T, Boolean> predicate) {
    return this;
  }

  @Override
  public <U> U foldRight(U initial, Function2<T, U, U> reducer) {
    return initial;
  }

  @Override
  public <U> U foldLeft(U initial, Function2<U, T, U> reducer) {
    return initial;
  }

  @Override
  public <U> Sequence<U> flatMap(Function1<T, Sequence<U>> mapper) {
    return new Empty<>();
  }

  @Override
  public Sequence<T> reverse() {
    return this;
  }

  @Override
  public Sequence<T> take(int count) {
    return this;
  }

  @Override
  public Sequence<T> drop(int count) {
    return this;
  }

  @Override
  public Sequence<T> concat(Sequence<T> other) {
    return other;
  }

  @Override
  public Sequence<T> intersperse(T element) {
    return this;
  }

  @Override
  public <U> Sequence<Pair<T, U>> zipWith(Sequence<U> that) {
    return new Empty<>();
  }

  @Override
  public Sequence<Pair<T, Integer>> zipWithIndex() {
    return new Empty<>();
  }

  @Override
  public Stream<T> stream() {
    return Stream.empty();
  }
}
