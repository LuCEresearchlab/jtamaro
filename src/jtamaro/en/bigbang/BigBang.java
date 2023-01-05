package jtamaro.en.bigbang;

import jtamaro.en.Graphic;
import jtamaro.en.fun.Op;
import jtamaro.en.oo.AbstractGraphic;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.RenderOptions;

import java.util.function.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
    private int width;
    private int height;
    private boolean fullScreen;
    private int msBetweenTicks;
    private long tickLimit;
    private String name;
    // debugging options
    private boolean showState;
    private boolean recording;
    private String recordingDirectoryName;
    private Predicate<Model> wellFormedWorldPredicate;
    
    // state
    private Model model;
    private long tick;
    
    // GUI
    private JFrame frame;
    private GraphicCanvas graphicCanvas;
    private Timer timer;
    
    
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
        width = -1;
        height = -1;
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
    
    public void setTickHandler(Function<Model,Model> tickHandler) {
        this.tickHandler = tickHandler;
    }
    
    public void setMsBetweenTicks(int msBetweenTicks) {
        this.msBetweenTicks = msBetweenTicks;
    }
    
    public void setTickLimit(long tickLimit) {
        this.tickLimit = tickLimit;
    }
    
    public void setKeyPressHandler(BiFunction<Model,KeyboardKey,Model> keyPressHandler) {
        this.keyPressHandler = keyPressHandler;
    }
    
    public void setKeyReleaseHandler(BiFunction<Model,KeyboardKey,Model> keyReleaseHandler) {
        this.keyReleaseHandler = keyReleaseHandler;
    }
    
    public void setKeyTypeHandler(BiFunction<Model,KeyboardChar,Model> keyTypeHandler) {
        this.keyTypeHandler = keyTypeHandler;
    }
    
    public void setMousePressHandler(TriFunction<Model,Coordinate,MouseButton,Model> mousePressHandler) {
        this.mousePressHandler = mousePressHandler;
    }
    
    public void setMouseReleaseHandler(TriFunction<Model,Coordinate,MouseButton,Model> mouseReleaseHandler) {
        this.mouseReleaseHandler = mouseReleaseHandler;
    }
    
    public void setMouseMoveHandler(BiFunction<Model,Coordinate,Model> mouseMoveHandler) {
        this.mouseMoveHandler = mouseMoveHandler;
    }
    
    public void setRenderer(Function<Model,Graphic> renderer) {
        this.renderer = renderer;
    }
    
    public void setRenderingSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }
    
    public void setStoppingPredicate(Predicate<Model> stoppingPredicate) {
        this.stoppingPredicate = stoppingPredicate;
    }
    
    public void setFinalGraphicRenderer(Function<Model,Graphic> finalGraphicRenderer) {
        this.finalGraphicRenderer = finalGraphicRenderer;
    }

    public void setCloseOnStop(boolean closeOnStop, double delay) {
        this.closeOnStop = closeOnStop;
        this.closeOnStopDelay = delay;
    }

    public void checkWith(Predicate<Model> wellFormedWorldPredicate) {
        this.wellFormedWorldPredicate = wellFormedWorldPredicate;
    }
    
    public void setRecording(boolean recording, String recordingDirectoryName) {
        this.recording = recording;
        this.recordingDirectoryName = recordingDirectoryName;
    }

    public void setShowState(boolean showState) {
        this.showState = showState;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    private void setGraphic(Graphic graphic) {
        AbstractGraphic abstractGraphic = (AbstractGraphic)graphic;
        graphicCanvas.setGraphic(abstractGraphic.getImplementation());
    }

    private void check(String what) {
        System.out.println("Checking " + what +": " + model);
        if (model == null) {
            throw new IllegalStateException(what + " is null");
        }
        if (!wellFormedWorldPredicate.test(model)) {
            throw new IllegalStateException(what + " is not well formed");
        }
    }
    
    public void run() {
        tick = 0;
        model = initialModel;
        check("initial model");

        if (width < 0 || height < 0) {
            System.out.println("Rendering initial model to determine size...");
            Graphic g = renderer.apply(model);
            width = (int)g.getWidth();
            height = (int)g.getHeight();
            System.out.println("Rendering size is " + width + "x" + height);
        }
        frame = new JFrame(name);
        RenderOptions renderOptions = new RenderOptions(0, width, height);
        graphicCanvas = new GraphicCanvas(renderOptions);
        graphicCanvas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ev) {
                model = keyPressHandler.apply(model, new KeyboardKey(ev));
                check("model after key press");
                renderAndShowGraphic();
            }
            public void keyReleased(KeyEvent ev) {
                model = keyReleaseHandler.apply(model, new KeyboardKey(ev));
                check("model after key release");
                renderAndShowGraphic();
            }
            public void keyTyped(KeyEvent ev) {
                model = keyTypeHandler.apply(model, new KeyboardChar(ev));
                check("model after key type");
                renderAndShowGraphic();
            }
        });
        graphicCanvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent ev) {
                model = mousePressHandler.apply(model, new Coordinate(ev.getX(), ev.getY()), new MouseButton(ev));
                check("model after mouse press");
                renderAndShowGraphic();
            }
            public void mouseReleased(MouseEvent ev) {
                model = mouseReleaseHandler.apply(model, new Coordinate(ev.getX(), ev.getY()), new MouseButton(ev));
                check("model after mouse release");
                renderAndShowGraphic();
            }
        });
        graphicCanvas.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent ev) {
                model = mouseMoveHandler.apply(model, new Coordinate(ev.getX(), ev.getY()));
                check("model after mouse move (move)");
                renderAndShowGraphic();
            }
            public void mouseDragged(MouseEvent ev) {
                model = mouseMoveHandler.apply(model, new Coordinate(ev.getX(), ev.getY()));
                check("model after mouse move (drag)");
                renderAndShowGraphic();
            }
        });
        frame.add(graphicCanvas, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        SwingUtilities.invokeLater(() -> {graphicCanvas.requestFocus();});
        
        timer = new Timer(msBetweenTicks, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (tick >= tickLimit || stoppingPredicate.test(model)) {
                    stop();
                } else {
                    model = tickHandler.apply(model);
                    check("model after tick");
                    renderAndShowGraphic();
                }
            }
        });
        
        timer.start();
    }
    
    private void renderAndShowGraphic() {
        Graphic graphic = renderer.apply(model);
        setGraphic(graphic);
    }
    
    // called through the timer
    private void stop() {
        System.out.println("stopping");
        timer.stop();
        Graphic finalGraphic = finalGraphicRenderer.apply(model);
        setGraphic(finalGraphic);
        if (closeOnStop) {
            frame.setVisible(false);
        }
    }
    
}