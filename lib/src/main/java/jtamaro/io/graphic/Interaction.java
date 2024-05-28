package jtamaro.io.graphic;

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

  // configuration options
  private final M initialModel;

  private final Function1<M, Graphic> renderer;

  private final Function1<M, M> tickHandler;

  private final Function2<M, KeyboardKey, M> keyPressHandler;

  private final Function2<M, KeyboardKey, M> keyReleaseHandler;

  private final Function2<M, KeyboardChar, M> keyTypeHandler;

  private final Function3<M, Coordinate, MouseButton, M> mousePressHandler;

  private final Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler;

  private final Function2<M, Coordinate, M> mouseMoveHandler;

  private final Function1<M, Boolean> stoppingPredicate;

  private final Function1<M, Graphic> finalGraphicRenderer;

  private final int canvasWidth;

  private final int canvasHeight;

  private final int msBetweenTicks;

  private final long tickLimit;

  private final String name;

  private final Graphic background;

  private final Function1<M, Boolean> wellFormedWorldPredicate;

  public Interaction(M initialModel) {
    this.initialModel = initialModel;
    renderer = m -> Graphics.emptyGraphic();
    tickHandler = m -> m;
    keyPressHandler = (m, k) -> m;
    keyReleaseHandler = (m, k) -> m;
    keyTypeHandler = (m, k) -> m;
    mousePressHandler = (m, c, b) -> m;
    mouseReleaseHandler = (m, c, b) -> m;
    mouseMoveHandler = (m, c) -> m;
    stoppingPredicate = m -> false;
    finalGraphicRenderer = m -> Graphics.emptyGraphic();
    canvasWidth = -1;
    canvasHeight = -1;
    msBetweenTicks = 100;
    tickLimit = Long.MAX_VALUE;
    name = "JTamaro Interaction";
    wellFormedWorldPredicate = (m) -> true;
    background = null;
  }

  // copy constructor
  private Interaction(
      M initialModel,
      Function1<M, Graphic> renderer,
      Function1<M, M> tickHandler,
      Function2<M, KeyboardKey, M> keyPressHandler,
      Function2<M, KeyboardKey, M> keyReleaseHandler,
      Function2<M, KeyboardChar, M> keyTypeHandler,
      Function3<M, Coordinate, MouseButton, M> mousePressHandler,
      Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler,
      Function2<M, Coordinate, M> mouseMoveHandler,
      Function1<M, Boolean> stoppingPredicate,
      Function1<M, Graphic> finalGraphicRenderer,
      int canvasWidth,
      int canvasHeight,
      int msBetweenTicks,
      long tickLimit,
      String name,
      Graphic background,
      Function1<M, Boolean> wellFormedWorldPredicate
  ) {
    this.initialModel = initialModel;
    this.renderer = renderer;
    this.tickHandler = tickHandler;
    this.keyPressHandler = keyPressHandler;
    this.keyReleaseHandler = keyReleaseHandler;
    this.keyTypeHandler = keyTypeHandler;
    this.mousePressHandler = mousePressHandler;
    this.mouseReleaseHandler = mouseReleaseHandler;
    this.mouseMoveHandler = mouseMoveHandler;
    this.stoppingPredicate = stoppingPredicate;
    this.finalGraphicRenderer = finalGraphicRenderer;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.msBetweenTicks = msBetweenTicks;
    this.tickLimit = tickLimit;
    this.name = name;
    this.background = background;
    this.wellFormedWorldPredicate = wellFormedWorldPredicate;
  }

  M getInitialModel() {
    return initialModel;
  }

  /**
   * Specify a function that is executed at every tick to evolve the model.
   */
  public Interaction<M> withTickHandler(Function1<M, M> tickHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function1<M, M> getTickHandler() {
    return tickHandler;
  }

  /**
   * Specify a time interval between ticks, in milliseconds.
   */
  public Interaction<M> withMsBetweenTicks(int msBetweenTicks) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  int getMsBetweenTicks() {
    return msBetweenTicks;
  }

  /**
   * Specify a limit to the number of ticks that will be performed.
   */
  public Interaction<M> withTickLimit(long tickLimit) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  long getTickLimit() {
    return tickLimit;
  }

  /**
   * Specify a function that is executed when a keyboard key is pressed to evolve the model.
   *
   * @see KeyboardKey
   */
  public Interaction<M> withKeyPressHandler(Function2<M, KeyboardKey, M> keyPressHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function2<M, KeyboardKey, M> getKeyPressHandler() {
    return keyPressHandler;
  }

  /**
   * Specify a function that is executed when a keyboard key is released to evolve the model.
   *
   * @see KeyboardKey
   */
  public Interaction<M> withKeyReleaseHandler(Function2<M, KeyboardKey, M> keyReleaseHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function2<M, KeyboardKey, M> getKeyReleaseHandler() {
    return keyReleaseHandler;
  }

  /**
   * Specify a function that is executed when a keyboard char is typed to evolve the model.
   *
   * @see KeyboardChar
   */
  public Interaction<M> withKeyTypeHandler(Function2<M, KeyboardChar, M> keyTypeHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function2<M, KeyboardChar, M> getKeyTypeHandler() {
    return keyTypeHandler;
  }

  /**
   * Specify a function that is executed when a mouse button is pressed to evolve the model.
   *
   * @see MouseButton
   */
  public Interaction<M> withMousePressHandler(Function3<M, Coordinate, MouseButton, M> mousePressHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function3<M, Coordinate, MouseButton, M> getMousePressHandler() {
    return mousePressHandler;
  }

  /**
   * Specify a function that is executed when a mouse button is released to evolve the model.
   *
   * @see MouseButton
   */
  public Interaction<M> withMouseReleaseHandler(Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function3<M, Coordinate, MouseButton, M> getMouseReleaseHandler() {
    return mouseReleaseHandler;
  }

  /**
   * Specify a function that is executed when a mouse is moved to evolve the model.
   *
   * @see Coordinate
   */
  public Interaction<M> withMouseMoveHandler(Function2<M, Coordinate, M> mouseMoveHandler) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function2<M, Coordinate, M> getMouseMoveHandler() {
    return mouseMoveHandler;
  }

  /**
   * Specify a function that takes the current model and produces a graphic at each tick.
   */
  public Interaction<M> withRenderer(Function1<M, Graphic> renderer) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function1<M, Graphic> getRenderer() {
    return renderWithBg(renderer);
  }

  /**
   * Specify the canvas size. If this is not specified, the canvas size will be set to the size of
   * the graphic of the first tick.
   *
   * @see #withRenderer(Function1)
   */
  public Interaction<M> withCanvasSize(int canvasWidth, int canvasHeight) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  int getCanvasWidth() {
    return canvasWidth;
  }

  int getCanvasHeight() {
    return canvasHeight;
  }

  /**
   * Specify a function that stops the interaction when it returns <code>true</code>.
   */
  public Interaction<M> withStoppingPredicate(Function1<M, Boolean> stoppingPredicate) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function1<M, Boolean> getStoppingPredicate() {
    return stoppingPredicate;
  }

  /**
   * Specify a function that renders a graphic when the interaction is stopped.
   */
  public Interaction<M> withFinalGraphicRenderer(Function1<M, Graphic> finalGraphicRenderer) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function1<M, Graphic> getFinalGraphicRenderer() {
    return renderWithBg(finalGraphicRenderer);
  }

  /**
   * Specify a function that checks the well-formedness of the model at each tick. If this function
   * returns <code>false</code>, an {@link Exception} is thrown.
   */
  public Interaction<M> withWellFormedPredicate(Function1<M, Boolean> wellFormedWorldPredicate) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  Function1<M, Boolean> getWellFormedWorldPredicate() {
    return wellFormedWorldPredicate;
  }

  /**
   * Set a name for the interaction. The name will appear in the title of the window.
   */
  public Interaction<M> withName(String name) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  String getName() {
    return name;
  }

  /**
   * Set a static background graphic.
   *
   * <p>Note: The foreground graphic (rendered with the function specified
   * with {@link #withRenderer(Function1)}) is {@link jtamaro.graphic.Compose}'d on top of this
   * background. The pin position of both the background and the foreground is not changed by the
   * interaction, so make sure to pin the produced graphics appropriately.
   */
  public Interaction<M> withBackground(Graphic background) {
    return new Interaction<>(initialModel,
        renderer,
        tickHandler,
        keyPressHandler,
        keyReleaseHandler,
        keyTypeHandler,
        mousePressHandler,
        mouseReleaseHandler,
        mouseMoveHandler,
        stoppingPredicate,
        finalGraphicRenderer,
        canvasWidth,
        canvasHeight,
        msBetweenTicks,
        tickLimit,
        name,
        background,
        wellFormedWorldPredicate);
  }

  /**
   * Execute this interaction.
   */
  public void run() {
    SwingUtilities.invokeLater(() -> new BigBangFrame<>(this));
  }

  private Function1<M, Graphic> renderWithBg(Function1<M, Graphic> fgRender) {
    // TODO: turn this into another swing component to avoid redrawing.
    //       Where to "pin"? TOP_LEFT?
    return background == null
        ? fgRender
        : m -> Graphics.compose(fgRender.apply(m), background);
  }
}
