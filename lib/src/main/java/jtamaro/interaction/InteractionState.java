package jtamaro.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class InteractionState<M> {

  private final List<InteractionStateListener<M>> listeners;

  private final Interaction<M> interaction;

  private M model;

  private long tick;

  public InteractionState(Interaction<M> interaction) {
    this.listeners = new ArrayList<>();
    this.interaction = interaction;

    final M initialModel = interaction.getInitialModel();
    check("Initial model", initialModel);
    this.model = initialModel;
    this.tick = 0;
  }

  public void tick() {
    tick++;
  }

  public void update(String what, M model) {
    check(what, model);
    this.model = model;
    fireStateChanged();
  }

  public long getTick() {
    return tick;
  }

  public M getModel() {
    return model;
  }

  public void addBigBangStateListener(InteractionStateListener<M> listener) {
    listeners.add(listener);
  }

  private void check(String what, M model) {
    Objects.requireNonNull(model);
    if (!interaction.getWellFormedPredicate().apply(model)) {
      throw new IllegalStateException(what + " is not well formed");
    }
  }

  private void fireStateChanged() {
    for (final InteractionStateListener<M> listener : listeners) {
      listener.stateChanged(this);
    }
  }
}
