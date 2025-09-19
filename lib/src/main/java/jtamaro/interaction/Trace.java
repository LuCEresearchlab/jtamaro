package jtamaro.interaction;

import java.util.ArrayList;
import java.util.List;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

final class Trace<T> {

  private Sequence<TraceEvent<T>> events;

  // Cache for performance
  private int length;

  private final List<TraceListener> listeners;

  public Trace() {
    events = Sequences.empty();
    length = 0;
    listeners = new ArrayList<>();
  }

  public void append(TraceEvent<T> event) {
    events = Sequences.cons(event, events);
    length++;
    fireEventAppended(event);
  }

  public int length() {
    return length;
  }

  public TraceEvent<T> get(int index) {
    Sequence<TraceEvent<T>> itr = events;
    int i = 0;
    while (i < index && !itr.isEmpty()) {
      itr = itr.rest();
      i++;
    }
    return itr.first();
  }

  public Sequence<TraceEvent<T>> getEventSequence() {
    return events;
  }

  public void addTraceListener(TraceListener listener) {
    listeners.add(listener);
  }

  public void removeTraceListener(TraceListener listener) {
    listeners.remove(listener);
  }

  private void fireEventAppended(TraceEvent<T> event) {
    for (final TraceListener listener : listeners) {
      listener.eventAppended(event);
    }
  }
}
