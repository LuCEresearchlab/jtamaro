package jtamaro.en.data;

import jtamaro.en.Sequence;

public final class Empty<T> extends Sequence<T> {

  @Override
  public T first() {
    throw new RuntimeException("Empty sequence has no first element");
  }

  @Override
  public Sequence<T> rest() {
    throw new RuntimeException("Empty sequence has no rest");
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean hasDefiniteSize() {
    return true;
  }

}
