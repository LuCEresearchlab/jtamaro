package jtamaro.en.io;

import java.util.ArrayList;


public class BigBangState<M> {

  private final ArrayList<BigBangStateListener<M>> listeners = new ArrayList<>();
  private final BigBang<M> bang;
  private long tick;
  private M model;


  public BigBangState(BigBang<M> bang) {
    this.bang = bang;
    this.tick = 0;
    M initialModel = bang.getInitialModel();
    check("initial model", initialModel);
    this.model = initialModel;
  }

  private void check(String what, M model) {
    System.out.println("Checking " + what +": " + model);
    if (model == null) {
        throw new IllegalStateException(what + " is null");
    }
    if (!bang.getWellFormedWorldPredicate().test(model)) {
        throw new IllegalStateException(what + " is not well formed");
    }
  }

  public void updateForTick(M model) {
    this.tick++;
    update("model after tick", model);
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

  // listeners
  public void addBigBangStateListener(BigBangStateListener<M> listener) {
    listeners.add(listener);
  }

  private void fireStateChanged() {
    for (BigBangStateListener<M> listener : listeners) {
      listener.stateChanged(this);
    }
  }

}
