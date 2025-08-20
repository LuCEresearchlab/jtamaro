package jtamaro.interaction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Option;
import jtamaro.data.Options;
import jtamaro.data.Pair;
import jtamaro.graphic.GuiGraphicCanvas;

/**
 * Mouse handler for a {@link Interaction} running in a {@link InteractionFrame}.
 *
 * @param <M> Type of the model of the interaction
 * @see MouseAction
 */
final class InteractionMouseHandler<M> extends MouseAdapter {

  private final GuiGraphicCanvas canvas;

  private final Consumer<? super TraceEvent<M>> traceEvent;

  private final GlobalMouseAction<M> pressHandler;

  private final GlobalMouseAction<M> releaseHandler;

  private final GlobalMouseAction<M> moveHandler;

  public InteractionMouseHandler(
      Interaction<M> interaction,
      GuiGraphicCanvas canvas,
      Consumer<? super TraceEvent<M>> traceEvent
  ) {
    this.canvas = canvas;
    this.traceEvent = traceEvent;
    this.pressHandler = interaction.getMousePressHandler();
    this.releaseHandler = interaction.getMouseReleaseHandler();
    this.moveHandler = interaction.getMouseMoveHandler();
  }

  @Override
  public void mousePressed(MouseEvent ev) {
    handleAction("Mouse pressed", GuiGraphicCanvas::getMousePressAction, ev, pressHandler);
  }

  @Override
  public void mouseReleased(MouseEvent ev) {
    handleAction("Mouse released", GuiGraphicCanvas::getMouseReleaseAction, ev, releaseHandler);
  }

  @Override
  public void mouseDragged(MouseEvent ev) {
    handleAction("Mouse dragged", GuiGraphicCanvas::getMouseDragAction, ev, moveHandler);
  }

  @Override
  public void mouseMoved(MouseEvent ev) {
    handleAction("Mouse moved", GuiGraphicCanvas::getMouseMoveAction, ev, moveHandler);
  }

  private void handleAction(
      String kind,
      Function2<GuiGraphicCanvas, Coordinate,
          Option<Pair<MouseAction<?>, Coordinate>>> actionAtCoordinates,
      MouseEvent ev,
      GlobalMouseAction<M> globalAction
  ) {
    canvas.requestFocus();
    final Coordinate absoluteCoordinates = new Coordinate(ev.getX(), ev.getY());
    final MouseButton button = new MouseButton(ev);

    final Option<TraceEvent<M>> localEventOpt = actionAtCoordinates.apply(
        canvas,
        absoluteCoordinates
    ).flatMap(p -> {
      final MouseAction<?> action = p.first();
      final Coordinate relativeCoordinates = p.second();
      return Options.some(new TraceEvent.MouseEvent<>(
          kind,
          relativeCoordinates,
          button,
          // Need to erase type parameters to skip static type checking
          // because it cannot do reification of type parameters
          new UnsafeMouseAction<>(action)
      ));
    });
    traceEvent.accept(localEventOpt.fold(
        Function1.identity(),
        () -> new TraceEvent.GlobalMouseEvent<>(kind, absoluteCoordinates, button, globalAction)
    ));
  }

  private static final class UnsafeMouseAction<T> implements MouseAction<T> {

    private final MouseAction<?> erasedAction;

    public UnsafeMouseAction(MouseAction<?> action) {
      this.erasedAction = action;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T apply(Coordinate coordinate, MouseButton mouseButton) {
      return (T) erasedAction.apply(coordinate, mouseButton);
    }
  }
}
