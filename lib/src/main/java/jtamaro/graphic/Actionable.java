package jtamaro.graphic;

import jtamaro.data.Option;
import jtamaro.data.Options;
import jtamaro.interaction.MouseAction;
import jtamaro.interaction.MouseDragAction;
import jtamaro.interaction.MouseMoveAction;
import jtamaro.interaction.MousePressAction;
import jtamaro.interaction.MouseReleaseAction;

/**
 * An Actionable can be used to add mouse event handlers to specific graphics in an interaction.
 *
 * <p>For a given graphic, it is possible to register up to four different mouse event handlers
 * for the following events:
 * <ol>
 *   <li>Mouse button press</li>
 *   <li>Mouse button release</li>
 *   <li>Mouse move</li>
 *   <li>Mouse drag</li>
 * </ol>
 *
 * @param <T> Type of the model of the interaction
 * @see jtamaro.interaction.Interaction
 * @see MouseAction
 */
public final class Actionable<T> {

  private final Graphic graphic;

  private final Option<MousePressAction<T>> pressActionHandler;

  private final Option<MouseReleaseAction<T>> releaseActionHandler;

  private final Option<MouseMoveAction<T>> moveActionHandler;

  private final Option<MouseDragAction<T>> dragActionHandler;

  /**
   * Default constructor.
   *
   * <p>Create an Actionable with no action handler.
   */
  public Actionable(Graphic g) {
    this(
        Options.none(),
        Options.none(),
        Options.none(),
        Options.none(),
        g
    );
  }

  private Actionable(
      Option<MousePressAction<T>> pressActionHandler,
      Option<MouseReleaseAction<T>> releaseActionHandler,
      Option<MouseMoveAction<T>> moveActionHandler,
      Option<MouseDragAction<T>> dragActionHandler,
      Graphic graphic
  ) {
    this.graphic = graphic;
    this.pressActionHandler = pressActionHandler;
    this.releaseActionHandler = releaseActionHandler;
    this.moveActionHandler = moveActionHandler;
    this.dragActionHandler = dragActionHandler;
  }

  /**
   * Configure the mouse press action event handler for the actionable.
   *
   * @see MousePressAction
   */
  public Actionable<T> withMousePressHandler(MousePressAction<T> action) {
    return new Actionable<>(
        Options.some(action),
        releaseActionHandler,
        moveActionHandler,
        dragActionHandler,
        graphic
    );
  }

  /**
   * Configure the mouse release action event handler for the actionable.
   *
   * @see MouseReleaseAction
   */
  public Actionable<T> withMouseReleaseHandler(MouseReleaseAction<T> action) {
    return new Actionable<>(
        pressActionHandler,
        Options.some(action),
        moveActionHandler,
        dragActionHandler,
        graphic
    );
  }

  /**
   * Configure the mouse move action event handler for the actionable.
   *
   * @see MouseMoveAction
   */
  public Actionable<T> withMouseMoveHandler(MouseMoveAction<T> action) {
    return new Actionable<>(
        pressActionHandler,
        releaseActionHandler,
        Options.some(action),
        dragActionHandler,
        graphic
    );
  }

  /**
   * Configure the mouse drag action event handler for the actionable.
   *
   * @see MouseDragAction
   */
  public Actionable<T> withMouseDragHandler(MouseDragAction<T> action) {
    return new Actionable<>(
        pressActionHandler,
        releaseActionHandler,
        moveActionHandler,
        Options.some(action),
        graphic
    );
  }

  /**
   * Produce a graphic from the actionable.
   */
  public Graphic asGraphic() {
    return new ActionableGraphic<>(
        pressActionHandler,
        releaseActionHandler,
        moveActionHandler,
        dragActionHandler,
        graphic
    );
  }
}
