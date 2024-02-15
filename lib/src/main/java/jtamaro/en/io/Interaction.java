package jtamaro.en.io;

import jtamaro.en.Graphic;
import jtamaro.en.Graphics;
import jtamaro.en.Function1;
import jtamaro.en.Function2;
import jtamaro.en.Function3;

import javax.swing.*;


/**
 * An Interaction is the configuration for an interactive application or simulation.
 * It's immutable and provides a fluent API.
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
  private final boolean closeOnStop;
  private final double closeOnStopDelay;
  private final int canvasWidth;
  private final int canvasHeight;
  private final boolean fullScreen;
  private final int msBetweenTicks;
  private final long tickLimit;
  private final String name;
  private final Graphic background;
  // debugging options
  private final boolean showState;
  private final boolean recording;
  private final String recordingDirectoryName;
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
    closeOnStop = true;
    closeOnStopDelay = 1000;
    canvasWidth = -1;
    canvasHeight = -1;
    fullScreen = false;
    msBetweenTicks = 100;
    tickLimit = Long.MAX_VALUE;
    name = "JTamaro Interaction";
    showState = false;
    recording = false;
    recordingDirectoryName = ".";
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
      boolean closeOnStop,
      double closeOnStopDelay,
      int canvasWidth,
      int canvasHeight,
      boolean fullScreen,
      int msBetweenTicks,
      long tickLimit,
      String name,
      Graphic background,
      // debugging options
      boolean showState,
      boolean recording,
      String recordingDirectoryName,
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
    this.closeOnStop = closeOnStop;
    this.closeOnStopDelay = closeOnStopDelay;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.fullScreen = fullScreen;
    this.msBetweenTicks = msBetweenTicks;
    this.tickLimit = tickLimit;
    this.name = name;
    this.background = background;
    this.showState = showState;
    this.recording = recording;
    this.recordingDirectoryName = recordingDirectoryName;
    this.wellFormedWorldPredicate = wellFormedWorldPredicate;
  }

  public M getInitialModel() {
    return initialModel;
  }

  public Interaction<M> withTickHandler(Function1<M, M> tickHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function1<M, M> getTickHandler() {
    return tickHandler;
  }

  public Interaction<M> withMsBetweenTicks(int msBetweenTicks) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public int getMsBetweenTicks() {
    return msBetweenTicks;
  }

  public Interaction<M> withTickLimit(long tickLimit) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public long getTickLimit() {
    return tickLimit;
  }

  public Interaction<M> withKeyPressHandler(Function2<M, KeyboardKey, M> keyPressHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function2<M, KeyboardKey, M> getKeyPressHandler() {
    return keyPressHandler;
  }

  public Interaction<M> withKeyReleaseHandler(Function2<M, KeyboardKey, M> keyReleaseHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function2<M, KeyboardKey, M> getKeyReleaseHandler() {
    return keyReleaseHandler;
  }

  public Interaction<M> withKeyTypeHandler(Function2<M, KeyboardChar, M> keyTypeHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function2<M, KeyboardChar, M> getKeyTypeHandler() {
    return keyTypeHandler;
  }

  public Interaction<M> withMousePressHandler(Function3<M, Coordinate, MouseButton, M> mousePressHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function3<M, Coordinate, MouseButton, M> getMousePressHandler() {
    return mousePressHandler;
  }

  public Interaction<M> withMouseReleaseHandler(Function3<M, Coordinate, MouseButton, M> mouseReleaseHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function3<M, Coordinate, MouseButton, M> getMouseReleaseHandler() {
    return mouseReleaseHandler;
  }

  public Interaction<M> withMouseMoveHandler(Function2<M, Coordinate, M> mouseMoveHandler) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function2<M, Coordinate, M> getMouseMoveHandler() {
    return mouseMoveHandler;
  }

  public Interaction<M> withRenderer(Function1<M, Graphic> renderer) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function1<M, Graphic> getRenderer() {
    return renderWithBg(renderer);
  }

  public Interaction<M> withCanvasSize(int canvasWidth, int canvasHeight) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public int getCanvasWidth() {
    return canvasWidth;
  }

  public int getCanvasHeight() {
    return canvasHeight;
  }

  public Interaction<M> withFullScreen(boolean fullScreen) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public boolean getFullScreen() {
    return fullScreen;
  }

  public Interaction<M> withStoppingPredicate(Function1<M, Boolean> stoppingPredicate) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function1<M, Boolean> getStoppingPredicate() {
    return stoppingPredicate;
  }

  public Interaction<M> withFinalGraphicRenderer(Function1<M, Graphic> finalGraphicRenderer) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function1<M, Graphic> getFinalGraphicRenderer() {
    return renderWithBg(finalGraphicRenderer);
  }

  public Interaction<M> withCloseOnStop(boolean closeOnStop, double closeOnStopDelay) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public boolean getCloseOnStop() {
    return closeOnStop;
  }

  public double getCloseOnStopDelay() {
    return closeOnStopDelay;
  }

  public Interaction<M> withWellFormedPredicate(Function1<M, Boolean> wellFormedWorldPredicate) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Function1<M, Boolean> getWellFormedWorldPredicate() {
    return wellFormedWorldPredicate;
  }

  public Interaction<M> withRecording(boolean recording, String recordingDirectoryName) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public boolean getRecording() {
    return recording;
  }

  public String getRecordingDirectoryName() {
    return recordingDirectoryName;
  }

  public Interaction<M> withShowState(boolean showState) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public boolean getShowState() {
    return showState;
  }

  public Interaction<M> withName(String name) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public Interaction<M> withBackground(Graphic background) {
    return new Interaction<>(initialModel, renderer, tickHandler, keyPressHandler, keyReleaseHandler, keyTypeHandler, mousePressHandler, mouseReleaseHandler, mouseMoveHandler, stoppingPredicate, finalGraphicRenderer, closeOnStop, closeOnStopDelay, canvasWidth, canvasHeight, fullScreen, msBetweenTicks, tickLimit, name, background, showState, recording, recordingDirectoryName, wellFormedWorldPredicate);
  }

  public String getName() {
    return name;
  }

  public void run() {
    SwingUtilities.invokeLater(() -> new BigBangFrame<>(this));
  }

  private Function1<M, Graphic> renderWithBg(Function1<M, Graphic> fgRender) {
    return background == null
        ? fgRender
        : m -> Graphics.overlay(fgRender.apply(m), background);
  }
  
}
