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
    return foldRight(
        (Sequence<U>) new Empty<U>(),
        (it, acc) -> new Cons<>(mapper.apply(it), acc)
    );
  }

  @Override
  public Sequence<T> filter(Function1<T, Boolean> predicate) {
    return foldRight(
        (Sequence<T>) new Empty<T>(),
        (it, acc) -> predicate.apply(it)
            ? new Cons<>(it, acc)
            : acc
    );
  }

  @Override
  public <U> U foldRight(U initial, Function2<T, U, U> reducer) {
    // Note: This method is widely used, so we want to optimize the
    // implementation for performance (both space and time wise) over clarity.

    // A simple implementation would be the following one-liner:
    //     reverse().foldLeft(initial, (acc, it) -> reducer.apply(it, acc));

    // Copy all elements into a temporary array in reverse order, so that we
    // don't have to allocate N+1 Sequence objects for iterating over the
    // elements of this sequence.

    // Step 1. Count how many elements are in this Sequence so we allocate.
    Sequence<T> itr = this;
    int idx = 0;
    while (itr instanceof Cons<T>(T _, Sequence<T> tail)) {
      idx++;
      itr = tail;
    }
    // Step 2. Allocate an array with the right amount of slots to avoid wasting space.
    final int n = idx;
    @SuppressWarnings("unchecked") final T[] arr = (T[]) new Object[n];
    itr = this;
    while (itr instanceof Cons<T>(T head, Sequence<T> tail)) {
      arr[--idx] = head;
      itr = tail;
    }
    // Step 3. Iterate over the array and apply the reduction.
    U acc = initial;
    while (idx < n) {
      acc = reducer.apply(arr[idx++], acc);
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
    return foldLeft(
        (Sequence<U>) new Empty<U>(),
        (acc, it) -> acc.concat(mapper.apply(it))
    );
  }

  @Override
  public Sequence<T> reverse() {
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
    return result.reverse();
  }

  @Override
  public Sequence<T> drop(int count) {
    Sequence<T> result = this;
    int i = 0;
    while (result instanceof Cons<T>(T _, Sequence<T> tail) && i < count) {
      result = tail;
      i += 1;
    }
    return result;
  }

  @Override
  public Sequence<T> concat(Sequence<T> other) {
    Sequence<T> itr = reverse();
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

    return result.reverse();
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
  public Stream<T> stream() {
    final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(
        iterator(), Spliterator.IMMUTABLE);
    return StreamSupport.stream(spliterator, false);
  }
}
