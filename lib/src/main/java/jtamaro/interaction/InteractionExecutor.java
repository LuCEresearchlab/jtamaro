package jtamaro.interaction;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;
import jtamaro.data.Function1;

final class InteractionExecutor<M> implements Runnable {

  private final InteractionState<M> state;

  private final Function1<M, M> tickHandler;

  private final Function1<M, Boolean> stopPredicate;

  private final Consumer<TraceEvent<M>> dispatchEvent;

  private final ReentrantLock lock;

  public InteractionExecutor(
      Interaction<M> interaction,
      Consumer<TraceEvent<M>> dispatchEvent,
      ReentrantLock lock
  ) {
    this.state = new InteractionState<>(interaction);
    this.tickHandler = interaction.getTickHandler();
    this.stopPredicate = interaction.getStoppingPredicate();
    this.dispatchEvent = dispatchEvent;
    this.lock = lock;
  }

  @Override
  public void run() {
    lock.lock();
    try {
      if (stopPredicate.apply(getCurrentModel())) {
        Thread.currentThread().interrupt();
        return;
      }

      traceEvent(new TraceEvent.Tick<>(tickHandler, state.tick()));
    } finally {
      lock.unlock();
    }
  }

  public void traceEvent(TraceEvent<M> event) {
    if (lock.isLocked() && !lock.isHeldByCurrentThread()) {
      // Ignore events while lock is held by another thread
      return;
    }

    lock.lock();
    try {
      final M before = state.getModel();
      final M after = event.process(before);
      state.update("Model after " + event.getKind(), after);
      SwingUtilities.invokeLater(() -> dispatchEvent.accept(event));
    } finally {
      lock.unlock();
    }
  }

  public M getCurrentModel() {
    return state.getModel();
  }
}
