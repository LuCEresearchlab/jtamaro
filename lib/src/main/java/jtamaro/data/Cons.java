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
  public <U> Sequence<U> map(Function1<T, U> mapper) {
    return foldRight((Sequence<U>) new Empty<U>(),
        (it, acc) -> new Cons<>(mapper.apply(it), acc));
  }

  @Override
  public Sequence<T> filter(Function1<T, Boolean> predicate) {
    return foldRight((Sequence<T>) new Empty<T>(),
        (it, acc) -> predicate.apply(it)
            ? new Cons<>(it, acc)
            : acc);
  }

  @Override
  public <U> U foldRight(U initial, Function2<T, U, U> reducer) {
    U acc = initial;
    Sequence<T> itr = reversed();
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      acc = reducer.apply(head, acc);
      itr = tail;
    }
    return acc;
  }

  @Override
  public <U> U foldLeft(U initial, Function2<U, T, U> reducer) {
    U acc = initial;
    Sequence<T> itr = this;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      acc = reducer.apply(acc, head);
      itr = tail;
    }
    return acc;
  }

  @Override
  public <U> Sequence<U> flatMap(Function1<T, Sequence<U>> mapper) {
    return foldLeft((Sequence<U>) new Empty<U>(),
        (acc, it) -> acc.concat(mapper.apply(it)));
  }

  /**
   * @implSpec Cannot depend on {@link #foldRight(Object, Function2)}.
   */
  @Override
  public Sequence<T> reversed() {
    Sequence<T> result = new Empty<>();
    Sequence<T> itr = this;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      result = new Cons<>(head, result);
      itr = tail;
    }
    return result;
  }

  @Override
  public Sequence<T> take(int count) {
    Sequence<T> result = new Empty<>();
    Sequence<T> itr = this;
    int i = 0;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail) && i < count) {
      result = new Cons<>(head, result);
      itr = tail;
      i += 1;
    }
    return result.reversed();
  }

  @Override
  public Sequence<T> drop(int count) {
    Sequence<T> result = this;
    int i = 0;
    while (result instanceof Cons<T>(T ignored, Sequence<T> tail) && i < count) {
      result = tail;
      i += 1;
    }
    return result;
  }

  @Override
  public Sequence<T> concat(Sequence<T> other) {
    Sequence<T> itr = reversed();
    Sequence<T> result = other;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      result = new Cons<>(head, result);
      itr = tail;
    }
    return result;
  }

  @Override
  public Sequence<T> intersperse(T element) {
    final Empty<T> empty = new Empty<>();
    return new Cons<>(first, rest.flatMap(it -> new Cons<>(element, new Cons<>(it, empty))));
  }

  @Override
  public <U> Sequence<Pair<T, U>> zipWith(Sequence<U> that) {
    Sequence<Pair<T, U>> result = new Empty<>();
    Sequence<T> thisItr = this;
    Sequence<U> thatItr = that;

    while (thisItr instanceof Cons<T>(T thisHead, Sequence<T> thisTail)
        && thatItr instanceof Cons<U>(U thatHead, Sequence<U> thatTail)) {
      result = new Cons<>(new Pair<>(thisHead, thatHead), result);
      thisItr = thisTail;
      thatItr = thatTail;
    }

    return result.reversed();
  }

  @Override
  public Sequence<Pair<T, Integer>> zipWithIndex() {
    Sequence<T> itr = this;

    // Build a reverse list and count the number of elements
    // at the same time to avoid iterating over the list twice
    Sequence<T> queue = new Empty<>();
    int count = 0;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      queue = new Cons<>(head, queue);
      itr = tail;
      count++;
    }

    // Build the zipped sequence
    Sequence<Pair<T, Integer>> result = new Empty<>();
    while (queue instanceof Cons<T>(T head, Sequence<T> tail)) {
      result = new Cons<>(new Pair<>(head, --count), result);
      queue = tail;
    }

    assert count == 0;
    return result;
  }

  @Override
  public <U> Sequence<Pair<T, U>> crossProduct(Sequence<U> that) {
    return foldLeft((Sequence<Pair<T, U>>) new Empty<Pair<T, U>>(),
        (acc, a) -> acc.concat(that.map(b -> new Pair<>(a, b))));
  }

  @Override
  public Stream<T> stream() {
    final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
        iterator(), Spliterator.IMMUTABLE);
    return StreamSupport.stream(spliterator, false);
  }
}
