package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Cons<T> extends Sequence<T> {

  private final T head;
  private final Sequence<T> tail;
  private final boolean hasDefiniteSize;

  public Cons(T head, Sequence<T> tail) {
    this.head = head;
    this.tail = tail;
    this.hasDefiniteSize = tail.hasDefiniteSize();
  }

  public T first() {
    return head;
  }

  public Sequence<T> rest() {
    return tail;
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean hasDefiniteSize() {
    return hasDefiniteSize;
  }

}
