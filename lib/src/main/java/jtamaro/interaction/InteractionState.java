package jtamaro.interaction;

import java.util.Objects;

final class InteractionState<M> {

  private final Interaction<M> interaction;

  private M model;

  private long tick;

  public InteractionState(Interaction<M> interaction) {
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
  }

  public long getTick() {
    return tick;
  }

  public M getModel() {
    return model;
  }

  private void check(String what, M model) {
    Objects.requireNonNull(model);
    if (!interaction.getWellFormedPredicate().apply(model)) {
      throw new IllegalStateException(what + " is not well formed");
    }
  }
}
