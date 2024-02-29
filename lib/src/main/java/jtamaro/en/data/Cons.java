package jtamaro.en.data;

import jtamaro.en.Sequence;

public final class Cons<T> extends Sequence<T> {

  private final T head;
  private final Sequence<T> tail;
  private final boolean hasDefiniteSize;

  public Cons(T head, Sequence<T> tail) {
    this.head = head;
    this.tail = tail;
    this.hasDefiniteSize = tail.hasDefiniteSize();
  }

  @Override
  public T first() {
    return head;
  }

  @Override
  public Sequence<T> rest() {
    return tail;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean hasDefiniteSize() {
    return hasDefiniteSize;
  }

}
