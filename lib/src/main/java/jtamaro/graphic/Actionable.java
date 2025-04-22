package jtamaro.graphic;

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

  private static <T> MouseDragAction<T> noop() {
    return (model, coordinates, button) -> model;
  }

  private final Graphic graphic;

  private final MousePressAction<T> pressActionHandler;

  private final MouseReleaseAction<T> releaseActionHandler;

  private final MouseMoveAction<T> moveActionHandler;

  private final MouseDragAction<T> dragActionHandler;

  /**
   * Default constructor.
   *
   * <p>Create an Actionable with no action handler.
   */
  public Actionable(Graphic g) {
    this(
        (model, coordinate, mouseButton) -> model,
        (model, coordinate, mouseButton) -> model,
        (model, coordinate, mouseButton) -> model,
        (model, coordinate, mouseButton) -> model,
        g
    );
  }

  private Actionable(
      MousePressAction<T> pressActionHandler,
      MouseReleaseAction<T> releaseActionHandler,
      MouseMoveAction<T> moveActionHandler,
      MouseDragAction<T> dragActionHandler,
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
    return new Actionable<>(new ActionableGraphic<>(
        action,
        releaseActionHandler,
        moveActionHandler,
        dragActionHandler,
        graphic
    ));
  }

  /**
   * Configure the mouse release action event handler for the actionable.
   *
   * @see MouseReleaseAction
   */
  public Actionable<T> withMouseReleaseHandler(MouseReleaseAction<T> action) {
    return new Actionable<>(new ActionableGraphic<>(
        pressActionHandler,
        action,
        moveActionHandler,
        dragActionHandler,
        graphic
    ));
  }

  /**
   * Configure the mouse move action event handler for the actionable.
   *
   * @see MouseMoveAction
   */
  public Actionable<T> withMouseMoveHandler(MouseMoveAction<T> action) {
    return new Actionable<>(new ActionableGraphic<>(
        pressActionHandler,
        releaseActionHandler,
        action,
        dragActionHandler,
        graphic
    ));
  }

  /**
   * Configure the mouse drag action event handler for the actionable.
   *
   * @see MouseDragAction
   */
  public Actionable<T> withMouseDragHandler(MouseDragAction<T> action) {
    return new Actionable<>(new ActionableGraphic<>(
        pressActionHandler,
        releaseActionHandler,
        moveActionHandler,
        action,
        graphic
    ));
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
