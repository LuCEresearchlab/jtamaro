package jtamaro.interaction;

interface InteractionStateListener<M> {

  void stateChanged(InteractionState<M> state);
}
