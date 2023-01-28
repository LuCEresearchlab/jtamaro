package jtamaro.en.data;

import jtamaro.en.Sequence;

public class Cycle<T> extends Sequence<T> {

  private final Sequence<T> sequence;
  private final Sequence<T> remaining;

  public Cycle(Sequence<T> sequence) {
    this.sequence = sequence;
    this.remaining = sequence;
  }

  private Cycle(Sequence<T> sequence, Sequence<T> remaining) {
    this.sequence = sequence;
    this.remaining = remaining;
  }

  public boolean isCons() {
    return true;
  }

  public boolean isEmpty() {
    return false;
  }

  public T first() {
    return sequence.first();
  }

  public Cycle<T> rest() {
    return remaining.isEmpty() ? new Cycle<T>(sequence, sequence) : new Cycle<T>(sequence, remaining.rest());
  }
  
}
