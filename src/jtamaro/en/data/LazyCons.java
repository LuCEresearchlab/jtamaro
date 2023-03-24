package jtamaro.en.data;

import jtamaro.en.Sequence;
import jtamaro.en.Function0;

public class LazyCons<T> extends Sequence<T> {

  private final T head;
  private final Function0<Sequence<T>> tailSupplier;
  private final boolean hasDefiniteSize;

  public LazyCons(T head, Function0<Sequence<T>> tailSupplier, boolean hasDefiniteSize) {
    this.head = head;
    this.tailSupplier = tailSupplier;
    this.hasDefiniteSize = hasDefiniteSize;
  }

  public T first() {
    return head;
  }

  public Sequence<T> rest() {
    return tailSupplier.apply();
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean hasDefiniteSize() {
    return hasDefiniteSize;
  }

}
