package jtamaro.interaction;

import java.util.Objects;
import javax.swing.SwingUtilities;
import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Function3;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;

/**
 * An Interaction is the configuration for an interactive application or simulation. It's immutable
 * and provides a fluent API.
 */
public final class Interaction<M> {

  /**
   * Initial model that is evolved through the interaction.
   */
  private final M initialModel;

  /**
   * Name of this interaction.
   *
   * <p>The name will appear in the title of the window.
   */
  private final String name;

  /**
   * Milliseconds between each tick.
   */
  private final int msBetweenTicks;

  /**
   * Function that evolves the model at each tick.
   */
  private final Function1<M, M> tickHandler;

  /**
   * Model well-formed predicate. Returns <code>true</code> if the model is well-formed.
   */
  private final Function1<M, Boolean> wellFormedPredicate;

  /**
   * Stopping predicate. Returns <code>true</code> if the interaction should stop running.
   */
  private final Function1<M, Boolean> stoppingPredicate;

  /**
   * Function that evolves the model when a given keyboard key is pressed.
   */
  private final Function2<M, KeyboardKey, M> keyPressHandler;

  /**
   * Function that evolves the model when a given keyboard key is released.
   */
  private final Function2<M, KeyboardKey, M> keyReleaseHandler;

  /**
   * Function that evolves the model when a given keyboard character is typed.
   */
  private final Function2<M, KeyboardChar, M> keyTypeHandler;

  /**
   * Function that evolves the model when a mouse button is pressed.
   */
  private final Function3<M, Coordinate, MouseButton, M> mousePressHandler;

  /**
   * Function that evolves the model when a mouse button is released.
   */
  private final Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler;

  /**
   * Function that evolves the model when the mouse cursor is moved.
   */
  private final Function2<M, Coordinate, M> mouseMoveHandler;

  /**
   * Defines the width of the canvas in which the interaction is rendered.
   *
   * @implNote If the value is &lt; 0, the canvas will take the width of the first rendered frame.
   */
  private final int canvasWidth;

  /**
   * Defines the height of the canvas in which the interaction is rendered.
   *
   * @implNote If the value is &lt; 0, the canvas will take the height of the first rendered frame.
   */
  private final int canvasHeight;

  /**
   * Fixed background {@link Graphic}.
   *
   * @implNote If <code>null</code>, there is no background.
   */
  private final Graphic background;

  /**
   * Function that transforms the model into a {@link Graphic}.
   */
  private final Function1<M, Graphic> renderer;

  /**
   * Default constructor.
   */
  public Interaction(M initialModel) {
    this.initialModel = initialModel;
    this.name = "JTamaro Interaction";
    this.msBetweenTicks = 100;
    this.tickHandler = Function1.identity();

    this.wellFormedPredicate = model -> true;
    this.stoppingPredicate = model -> false;

    this.keyPressHandler = (model, k) -> model;
    this.keyReleaseHandler = (model, k) -> model;
    this.keyTypeHandler = (model, c) -> model;
    this.mousePressHandler = (model, coords, button) -> model;
    this.mouseReleaseHandler = (model, coords, button) -> model;
    this.mouseMoveHandler = (model, coords) -> model;

    this.canvasWidth = -1;
    this.canvasHeight = -1;
    this.background = null;
    this.renderer = model -> Graphics.emptyGraphic();
  }

  /**
   * Copy constructor.
   */
  private Interaction(M initialModel,
      String name,
      int msBetweenTicks,
      Function1<M, M> tickHandler,
      Function1<M, Boolean> wellFormedPredicate,
      Function1<M, Boolean> stoppingPredicate,
      Function2<M, KeyboardKey, M> keyPressHandler,
      Function2<M, KeyboardKey, M> keyReleaseHandler,
      Function2<M, KeyboardChar, M> keyTypeHandler,
      Function3<M, Coordinate, MouseButton, M> mousePressHandler,
      Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler,
      Function2<M, Coordinate, M> mouseMoveHandler,
      int canvasWidth,
      int canvasHeight,
      Graphic background,
      Function1<M, Graphic> renderer) {
    this.initialModel = initialModel;
    this.renderer = buildRenderer(renderer, background);
    this.tickHandler = tickHandler;
    this.keyPressHandler = keyPressHandler;
    this.keyReleaseHandler = keyReleaseHandler;
    this.keyTypeHandler = keyTypeHandler;
    this.mousePressHandler = mousePressHandler;
    this.mouseReleaseHandler = mouseReleaseHandler;
    this.mouseMoveHandler = mouseMoveHandler;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.msBetweenTicks = msBetweenTicks;
    this.name = name;
    this.background = background;
    this.wellFormedPredicate = wellFormedPredicate;
    this.stoppingPredicate = stoppingPredicate;
  }

  /**
   * Set a name for the interaction.
   *
   * <p>The name will appear in the title of the window.
   */
  public Interaction<M> withName(String name) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a time interval between ticks, in milliseconds.
   */
  public Interaction<M> withMsBetweenTicks(int msBetweenTicks) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed at every tick to evolve the model.
   */
  public Interaction<M> withTickHandler(Function1<M, M> tickHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that checks the well-formedness of the model at each tick.
   *
   * <p>If this function returns <code>false</code>, an {@link Exception} is thrown.
   */
  public Interaction<M> withWellFormedPredicate(Function1<M, Boolean> wellFormedPredicate) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that checks the well-formedness of the model at each tick. If this function
   * returns <code>false</code>, an {@link Exception} is thrown.
   */
  public Interaction<M> withStoppingPredicate(Function1<M, Boolean> stoppingPredicate) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a keyboard key is pressed to evolve the model.
   *
   * @see KeyboardKey
   */
  public Interaction<M> withKeyPressHandler(Function2<M, KeyboardKey, M> keyPressHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a keyboard key is released to evolve the model.
   *
   * @see KeyboardKey
   */
  public Interaction<M> withKeyReleaseHandler(Function2<M, KeyboardKey, M> keyReleaseHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a keyboard char is typed to evolve the model.
   *
   * @see KeyboardChar
   */
  public Interaction<M> withKeyTypeHandler(Function2<M, KeyboardChar, M> keyTypeHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a mouse button is pressed to evolve the model.
   *
   * @see MouseButton
   */
  public Interaction<M> withMousePressHandler(Function3<M, Coordinate, MouseButton, M> mousePressHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a mouse button is released to evolve the model.
   *
   * @see MouseButton
   */
  public Interaction<M> withMouseReleaseHandler(Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that is executed when a mouse is moved to evolve the model.
   *
   * @see Coordinate
   */
  public Interaction<M> withMouseMoveHandler(Function2<M, Coordinate, M> mouseMoveHandler) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify the canvas size. If this is not specified, the canvas size will be set to the size of
   * the graphic of the first tick.
   *
   * @see #withRenderer(Function1)
   */
  public Interaction<M> withCanvasSize(int canvasWidth, int canvasHeight) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Specify a function that takes the current model and produces a graphic at each tick.
   */
  public Interaction<M> withRenderer(Function1<M, Graphic> renderer) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Set a static background graphic.
   *
   * @implNote The foreground graphic (rendered with the function specified with
   * {@link #withRenderer(Function1)}) is {@link jtamaro.graphic.Compose}'d on top of this
   * background. The pin position of both the background and the foreground is not changed by the
   * interaction, so make sure to pin the produced graphics appropriately.
   */
  public Interaction<M> withBackground(Graphic background) {
    return new Interaction<>(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        background,
        renderer);
  }

  /**
   * Execute this interaction.
   */
  public void run() {
    SwingUtilities.invokeLater(() ->
        new InteractionFrame<>(this).setVisible(true));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Interaction<?> that)) {
      return false;
    }
    return msBetweenTicks == that.msBetweenTicks
        && canvasWidth == that.canvasWidth
        && canvasHeight == that.canvasHeight
        && Objects.equals(initialModel, that.initialModel)
        && Objects.equals(name, that.name)
        && Objects.equals(tickHandler, that.tickHandler)
        && Objects.equals(wellFormedPredicate, that.wellFormedPredicate)
        && Objects.equals(stoppingPredicate, that.stoppingPredicate)
        && Objects.equals(keyPressHandler, that.keyPressHandler)
        && Objects.equals(keyReleaseHandler, that.keyReleaseHandler)
        && Objects.equals(keyTypeHandler, that.keyTypeHandler)
        && Objects.equals(mousePressHandler, that.mousePressHandler)
        && Objects.equals(mouseReleaseHandler, that.mouseReleaseHandler)
        && Objects.equals(mouseMoveHandler, that.mouseMoveHandler)
        && Objects.equals(renderer, that.renderer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(initialModel,
        name,
        msBetweenTicks,
        tickHandler,
        wellFormedPredicate,
        stoppingPredicate,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        canvasWidth,
        canvasHeight,
        renderer);
  }

  /* **** Internal getters **** */

  M getInitialModel() {
    return initialModel;
  }

  String getName() {
    return name;
  }

  int getMsBetweenTicks() {
    return msBetweenTicks;
  }

  Function1<M, M> getTickHandler() {
    return tickHandler;
  }

  Function1<M, Boolean> getWellFormedPredicate() {
    return wellFormedPredicate;
  }

  Function1<M, Boolean> getStoppingPredicate() {
    return stoppingPredicate;
  }

  Function2<M, KeyboardKey, M> getKeyPressHandler() {
    return keyPressHandler;
  }

  Function2<M, KeyboardKey, M> getKeyReleaseHandler() {
    return keyReleaseHandler;
  }

  Function2<M, KeyboardChar, M> getKeyTypeHandler() {
    return keyTypeHandler;
  }

  Function3<M, Coordinate, MouseButton, M> getMousePressHandler() {
    return mousePressHandler;
  }

  Function3<M, Coordinate, MouseButton, M> getMouseReleaseHandler() {
    return mouseReleaseHandler;
  }

  Function2<M, Coordinate, M> getMouseMoveHandler() {
    return mouseMoveHandler;
  }

  int getCanvasWidth() {
    return canvasWidth;
  }

  int getCanvasHeight() {
    return canvasHeight;
  }

  Function1<M, Graphic> getRenderer() {
    return renderer;
  }

  private static <M> Function1<M, Graphic> buildRenderer(Function1<M, Graphic> fgRenderer,
      Graphic background) {
    return background == null
        ? fgRenderer
        : m -> Graphics.compose(fgRenderer.apply(m), background);
  }
}
