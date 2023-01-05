package jtamaro.en.bigbang;

import jtamaro.en.Graphic;
import jtamaro.en.fun.Op;

import java.util.function.*;


/**
 * A BigBang is the configuration for an interactive application or simulation.
 */
public class BigBang<Model> {
    
    // configuration options
    private Model initialModel;
    private Function<Model,Graphic> renderer;
    private Function<Model,Model> tickHandler;
    private BiFunction<Model,KeyboardKey,Model> keyPressHandler;
    private BiFunction<Model,KeyboardKey,Model> keyReleaseHandler;
    private BiFunction<Model,KeyboardChar,Model> keyTypeHandler;
    private TriFunction<Model,Coordinate,MouseButton,Model> mousePressHandler;
    private TriFunction<Model,Coordinate,MouseButton,Model> mouseReleaseHandler;
    private BiFunction<Model,Coordinate,Model> mouseMoveHandler;
    private Predicate<Model> stoppingPredicate;
    private Function<Model,Graphic> finalGraphicRenderer;
    private boolean closeOnStop;
    private double closeOnStopDelay;
    private int canvasWidth;
    private int canvasHeight;
    private boolean fullScreen;
    private int msBetweenTicks;
    private long tickLimit;
    private String name;
    // debugging options
    private boolean showState;
    private boolean recording;
    private String recordingDirectoryName;
    private Predicate<Model> wellFormedWorldPredicate;
        
    
    public BigBang() {
        renderer = m -> Op.emptyGraphic();
        tickHandler = m -> m;
        keyPressHandler = (m, k) -> m;
        keyReleaseHandler = (m, k) -> m;
        keyTypeHandler = (m, k) -> m;
        mousePressHandler = (m, c, b) -> m;
        mouseReleaseHandler = (m, c, b) -> m;
        mouseMoveHandler = (m, c) -> m;
        stoppingPredicate = m -> false;
        finalGraphicRenderer = m -> Op.emptyGraphic();
        closeOnStop = true;
        closeOnStopDelay = 1000;
        canvasWidth = -1;
        canvasHeight = -1;
        fullScreen = false;
        msBetweenTicks = 100;
        tickLimit = Long.MAX_VALUE;
        name = "Big Bang";
        showState = false;
        recording = false;
        wellFormedWorldPredicate = (m) -> true;
    }
    
    public void setInitialModel(Model initialModel) {
        this.initialModel = initialModel;
    }

    public Model getInitialModel() {
        return initialModel;
    }
    
    public void setTickHandler(Function<Model,Model> tickHandler) {
        this.tickHandler = tickHandler;
    }
    
    public Function<Model,Model> getTickHandler() {
        return tickHandler;
    }

    public void setMsBetweenTicks(int msBetweenTicks) {
        this.msBetweenTicks = msBetweenTicks;
    }

    public int getMsBetweenTicks() {
        return msBetweenTicks;
    }
    
    public void setTickLimit(long tickLimit) {
        this.tickLimit = tickLimit;
    }

    public long getTickLimit() {
        return tickLimit;
    }
    
    public void setKeyPressHandler(BiFunction<Model,KeyboardKey,Model> keyPressHandler) {
        this.keyPressHandler = keyPressHandler;
    }

    public BiFunction<Model,KeyboardKey,Model> getKeyPressHandler() {
        return keyPressHandler;
    }
    
    public void setKeyReleaseHandler(BiFunction<Model,KeyboardKey,Model> keyReleaseHandler) {
        this.keyReleaseHandler = keyReleaseHandler;
    }
    
    public BiFunction<Model,KeyboardKey,Model> getKeyReleaseHandler() {
        return keyReleaseHandler;
    }

    public void setKeyTypeHandler(BiFunction<Model,KeyboardChar,Model> keyTypeHandler) {
        this.keyTypeHandler = keyTypeHandler;
    }

    public BiFunction<Model,KeyboardChar,Model> getKeyTypeHandler() {
        return keyTypeHandler;
    }
    
    public void setMousePressHandler(TriFunction<Model,Coordinate,MouseButton,Model> mousePressHandler) {
        this.mousePressHandler = mousePressHandler;
    }

    public TriFunction<Model,Coordinate,MouseButton,Model> getMousePressHandler() {
        return mousePressHandler;
    }
    
    public void setMouseReleaseHandler(TriFunction<Model,Coordinate,MouseButton,Model> mouseReleaseHandler) {
        this.mouseReleaseHandler = mouseReleaseHandler;
    }

    public TriFunction<Model,Coordinate,MouseButton,Model> getMouseReleaseHandler() {
        return mouseReleaseHandler;
    }
    
    public void setMouseMoveHandler(BiFunction<Model,Coordinate,Model> mouseMoveHandler) {
        this.mouseMoveHandler = mouseMoveHandler;
    }

    public BiFunction<Model,Coordinate,Model> getMouseMoveHandler() {
        return mouseMoveHandler;
    }
    
    public void setRenderer(Function<Model,Graphic> renderer) {
        this.renderer = renderer;
    }

    public Function<Model,Graphic> getRenderer() {
        return renderer;
    }
    
    public void setCanvasSize(int width, int height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean getFullScreen() {
        return fullScreen;
    }
    
    public void setStoppingPredicate(Predicate<Model> stoppingPredicate) {
        this.stoppingPredicate = stoppingPredicate;
    }

    public Predicate<Model> getStoppingPredicate() {
        return stoppingPredicate;
    }
    
    public void setFinalGraphicRenderer(Function<Model,Graphic> finalGraphicRenderer) {
        this.finalGraphicRenderer = finalGraphicRenderer;
    }

    public Function<Model,Graphic> getFinalGraphicRenderer() {
        return finalGraphicRenderer;
    }

    public void setCloseOnStop(boolean closeOnStop, double delay) {
        this.closeOnStop = closeOnStop;
        this.closeOnStopDelay = delay;
    }

    public boolean getCloseOnStop() {
        return closeOnStop;
    }

    public double getCloseOnStopDelay() {
        return closeOnStopDelay;
    }

    public void checkWith(Predicate<Model> wellFormedWorldPredicate) {
        this.wellFormedWorldPredicate = wellFormedWorldPredicate;
    }

    public Predicate<Model> getWellFormedWorldPredicate() {
        return wellFormedWorldPredicate;
    }
    
    public void setRecording(boolean recording, String recordingDirectoryName) {
        this.recording = recording;
        this.recordingDirectoryName = recordingDirectoryName;
    }

    public boolean getRecording() {
        return recording;
    }

    public String getRecordingDirectoryName() {
        return recordingDirectoryName;
    }

    public void setShowState(boolean showState) {
        this.showState = showState;
    }

    public boolean getShowState() {
        return showState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
   
    public void run() {
        new BigBangFrame<>(this);
    }

}