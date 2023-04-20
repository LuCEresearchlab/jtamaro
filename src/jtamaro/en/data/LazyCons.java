package jtamaro.en.data;

import jtamaro.en.Function0;
import jtamaro.en.Sequence;

public class LazyCons<T> extends Sequence<T> {

  private final T head;
  private final Function0<Sequence<T>> tailSupplier;
  private final boolean hasDefiniteSize;

  public LazyCons(T head, Function0<Sequence<T>> tailSupplier, boolean hasDefiniteSize) {
    this.head = head;
    this.tailSupplier = tailSupplier;
    this.hasDefiniteSize = hasDefiniteSize;
  }

  @Override
  public T first() {
    return head;
  }

  @Override
  public Sequence<T> rest() {
    return tailSupplier.apply();
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
