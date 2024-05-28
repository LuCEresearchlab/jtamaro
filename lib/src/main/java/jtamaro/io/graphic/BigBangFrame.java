package jtamaro.io.graphic;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.GuiGraphicCanvas;

final class BigBangFrame<M> extends JFrame {

  private final Interaction<M> bang;

  private final GuiGraphicCanvas graphicCanvas;

  private final Timer timer;

  private final BigBangState<M> state;

  private final Trace trace;

  public BigBangFrame(Interaction<M> bang) {
    this.bang = bang;
    this.state = new BigBangState<>(bang);
    this.trace = new Trace();
    this.timer = new Timer(bang.getMsBetweenTicks(), ev -> {
      if (state.getTick() >= bang.getTickLimit()
          || bang.getStoppingPredicate().apply(state.getModel())) {
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
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      final Graphic g = bang.getRenderer().apply(state.getModel());
      canvasWidth = (int) g.getWidth();
      canvasHeight = (int) g.getHeight();
      // TODO: maybe add a warning notice at the top of the window
      //       stating that the width/height are based on the first frame only
      //       and asking to use withCanvasSize(int,int) if needed.
    }

    graphicCanvas = new GuiGraphicCanvas(canvasWidth, canvasHeight);
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
    addWindowListener(new WindowAdapter() {
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
    if (!timer.isRunning()) {
      return;
    }

    trace.append(event);
    final M before = state.getModel();
    final M after = event.process(bang, before);
    state.update("model after " + event.getKind(), after);
    renderAndShowGraphic();
  }

  private void renderAndShowGraphic() {
    final Graphic graphic = bang.getRenderer().apply(state.getModel());
    setGraphic(graphic);
  }

  private void stop() {
    // called through the timer
    timer.stop();
    final Graphic finalGraphic = bang.getFinalGraphicRenderer()
        .apply(state.getModel());
    setGraphic(finalGraphic);
  }

  private void setGraphic(Graphic graphic) {
    graphicCanvas.setGraphic(graphic);
  }
}
