package jtamaro.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

final class Trace<T> {

  private final List<TraceEvent<T>> events;

  private final List<Consumer<TraceEvent<T>>> listeners;

  public Trace() {
    events = new ArrayList<>();
    listeners = new ArrayList<>();
  }

  public void append(TraceEvent<T> event) {
    events.add(event);
    onEventAppended(event);
  }

  public int length() {
    return events.size();
  }

  public TraceEvent<T> get(int index) {
    return events.get(index);
  }

  public T getLastModel(T initialModel) {
    T model = initialModel;
    for (final TraceEvent<T> event : events) {
      model = event.process(model);
    }
    return model;
  }

  public void addTraceListener(Consumer<TraceEvent<T>> listener) {
    listeners.add(listener);
  }

  public void removeTraceListener(Consumer<TraceEvent<T>> listener) {
    listeners.remove(listener);
  }

  private void onEventAppended(TraceEvent<T> event) {
    for (final Consumer<TraceEvent<T>> listener : listeners) {
      listener.accept(event);
    }
  }
}
