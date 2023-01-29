package jtamaro.en.data;

import java.util.function.Supplier;

import jtamaro.en.Sequence;

public class LazyCons<T> extends Sequence<T> {

  private final T head;
  private final Supplier<Sequence<T>> tailSupplier;
  private final boolean hasDefiniteSize;

  public LazyCons(T head, Supplier<Sequence<T>> tailSupplier, boolean hasDefiniteSize) {
    this.head = head;
    this.tailSupplier = tailSupplier;
    this.hasDefiniteSize = hasDefiniteSize;
  }

  public T first() {
    return head;
  }

  public Sequence<T> rest() {
    return tailSupplier.get();
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean hasDefiniteSize() {
    return hasDefiniteSize;
  }

}
