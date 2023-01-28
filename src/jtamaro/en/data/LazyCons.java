package jtamaro.en.data;

import java.util.function.Supplier;

import jtamaro.en.Sequence;

public class LazyCons<T> extends Sequence<T> {

  private final T head;
  private final Supplier<Sequence<T>> tailSupplier;

  public LazyCons(T head, Supplier<Sequence<T>> tailSupplier) {
    this.head = head;
    this.tailSupplier = tailSupplier;
  }

  public T first() { return head; }

  public Sequence<T> rest() { return tailSupplier.get(); }

  public boolean isEmpty() { return false; }

}
