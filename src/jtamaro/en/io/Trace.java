package jtamaro.en.io;

import jtamaro.en.Sequence;
import jtamaro.en.Sequences;

import java.util.ArrayList;


public class Trace {

  private Sequence<TraceEvent> events;
  private int length; // cache for performance
  private final ArrayList<TraceListener> listeners;

  public Trace() {
    events = Sequences.empty();
    length = 0;
    listeners = new ArrayList<>();
  }

  public void append(TraceEvent event) {
    events = Sequences.cons(event, events);
    length++;
    fireEventAppended(event);
  }

  public int length() {
    return length;
  }

  public TraceEvent get(int index) {
    return events.get(index);
  }

  // can e.g., be used to map to Sequence<Model> and then Sequence<Graphic>
  public Sequence<TraceEvent> getEventSequence() {
    return events;
  }

  public void addTraceListener(TraceListener listener) {
    listeners.add(listener);
  }

  public void removeTraceListener(TraceListener listener) {
    listeners.remove(listener);
  }

  public void fireEventAppended(TraceEvent event) {
    for (TraceListener listener : listeners) {
      listener.eventAppended(event);
    }
  }

}
