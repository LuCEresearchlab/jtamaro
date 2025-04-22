package jtamaro.interaction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import jtamaro.data.Function2;

final class InteractionKeyboardHandler<M> extends KeyAdapter {

  private final Consumer<TraceEvent<M>> traceEvent;

  private final Function2<M, KeyboardKey, M> pressHandler;

  private final Function2<M, KeyboardKey, M> releaseHandler;

  private final Function2<M, KeyboardChar, M> typedHandler;

  InteractionKeyboardHandler(
      Interaction<M> interaction,
      Consumer<TraceEvent<M>> traceEvent
  ) {
    this.traceEvent = traceEvent;
    this.pressHandler = interaction.getKeyPressHandler();
    this.releaseHandler = interaction.getKeyReleaseHandler();
    this.typedHandler = interaction.getKeyTypeHandler();
  }

  @Override
  public void keyPressed(KeyEvent ev) {
    traceEvent.accept(new TraceEvent.KeyboardKeyEvent<>(
        new KeyboardKey(ev),
        pressHandler
    ));
  }

  @Override
  public void keyReleased(KeyEvent ev) {
    traceEvent.accept(new TraceEvent.KeyboardKeyEvent<>(
        new KeyboardKey(ev),
        releaseHandler
    ));
  }

  @Override
  public void keyTyped(KeyEvent ev) {
    traceEvent.accept(new TraceEvent.KeyboardCharEvent<>(
        new KeyboardChar(ev),
        typedHandler
    ));
  }
}
