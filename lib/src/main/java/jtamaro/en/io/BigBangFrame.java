package jtamaro.en.io;

import jtamaro.en.Graphic;
import jtamaro.en.Sequence;
import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.RenderOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import static jtamaro.en.Sequences.*;


public class BigBangFrame<M> extends JFrame {

  private final Interaction<M> bang;
  private final GraphicCanvas graphicCanvas;
  private final Timer timer;
  private final BigBangState<M> state;
  private final Trace trace;


  public BigBangFrame(Interaction<M> bang) {
    this.bang = bang;
    state = new BigBangState<>(bang);
    trace = new Trace();
    timer = new Timer(bang.getMsBetweenTicks(), ev -> {
      if (state.getTick() >= bang.getTickLimit() || bang.getStoppingPredicate().apply(state.getModel())) {
        stop();
      } else {
        handle(TraceEvent.createTick());
        state.tick();
      }
    });

    setTitle(bang.getName());
    final BigBangToolbar<M> toolbar = new BigBangToolbar<>(bang, state, timer, trace);
    add(toolbar, BorderLayout.NORTH);

    int canvasWidth = bang.getCanvasWidth();
    int canvasHeight = bang.getCanvasHeight();
    if (canvasWidth < 0 || canvasHeight < 0) {
      Graphic g = bang.getRenderer().apply(state.getModel());
      canvasWidth = (int) g.getWidth();
      canvasHeight = (int) g.getHeight();
      // TODO: maybe add a warning notice at the top of the window
      //       stating that the width/height are based on the first frame only
      //       and asking to use withCanvasSize(int,int) if needed.
    }

    RenderOptions renderOptions = new RenderOptions(0, canvasWidth, canvasHeight);
    graphicCanvas = new GraphicCanvas(renderOptions);
    graphicCanvas.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent ev) {
        final KeyboardKey key = new KeyboardKey(ev);
        handle(TraceEvent.createKeyPress(key));
      }

      @Override
      public void keyReleased(KeyEvent ev) {
        final KeyboardKey key = new KeyboardKey(ev);
        handle(TraceEvent.createKeyRelease(key));
      }

      @Override
      public void keyTyped(KeyEvent ev) {
        final KeyboardChar ch = new KeyboardChar(ev);
        handle(TraceEvent.createKeyType(ch));
      }
    });
    graphicCanvas.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent ev) {
        graphicCanvas.requestFocus();
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        final MouseButton button = new MouseButton(ev);
        handle(TraceEvent.createMousePress(coordinate, button));
      }

      @Override
      public void mouseReleased(MouseEvent ev) {
        graphicCanvas.requestFocus();
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        final MouseButton button = new MouseButton(ev);
        handle(TraceEvent.createMouseRelease(coordinate, button));
      }
    });
    graphicCanvas.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent ev) {
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        handle(TraceEvent.createMouseMove(coordinate));
      }

      @Override
      public void mouseDragged(MouseEvent ev) {
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        handle(TraceEvent.createMouseMove(coordinate));
      }
    });
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent winEvt) {
        timer.stop();
      }
    });
    add(graphicCanvas, BorderLayout.CENTER);
    pack();
    setVisible(true);

    SwingUtilities.invokeLater(() -> {
      renderAndShowGraphic();
      graphicCanvas.requestFocus();
    });

    timer.start();
    toolbar.configureButtons(); // because timer is now running
  }

  private void handle(final TraceEvent event) {
    trace.append(event);
    M before = state.getModel();
    M after = event.process(bang, before);
    state.update("model after " + event.getKind(), after);
    renderAndShowGraphic();
  }

  private void renderAndShowGraphic() {
    Graphic graphic = bang.getRenderer().apply(state.getModel());
    setGraphic(graphic);
  }

  // called through the timer
  private void stop() {
    timer.stop();
    Graphic finalGraphic = bang.getFinalGraphicRenderer().apply(state.getModel());
    setGraphic(finalGraphic);
    if (bang.getCloseOnStop()) {
      //setVisible(false);
      dispose();
    }
  }

  private void setGraphic(Graphic graphic) {
    AbstractGraphic abstractGraphic = (AbstractGraphic) graphic;
    graphicCanvas.setGraphic(abstractGraphic.getImplementation());
  }

  public Sequence<TraceEvent> getSequenceOfEvents() {
    return trace.getEventSequence();
  }

  public Sequence<M> getSequenceOfModels() {
    //TODO: make lazy
    return reduce(
      empty(),
      (TraceEvent ev, Sequence<M> models) -> cons(ev.process(bang, models.first()), models),
      trace.getEventSequence()
    );
  }

  public Sequence<Graphic> getSequenceOfGraphics() {
    return map((M model) -> bang.getRenderer().apply(model), getSequenceOfModels());
  }

}
