package jtamaro.interaction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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

/**
 * GUI that allows the user to interact with an {@link Interaction}.
 */
final class InteractionFrame<M> extends JFrame {

  private final Interaction<M> interaction;

  private final InteractionState<M> state;

  private final Trace eventsTrace;

  private final Timer timer;

  private final GuiGraphicCanvas graphicCanvas;

  public InteractionFrame(Interaction<M> interaction) {
    this.interaction = interaction;
    this.state = new InteractionState<>(interaction);
    this.eventsTrace = new Trace();
    this.timer = new Timer(interaction.getMsBetweenTicks(), this::onTick);

    setTitle(interaction.getName());

    // Toolbar
    final InteractionToolbar<M> toolbar = new InteractionToolbar<>(interaction,
        state,
        timer,
        eventsTrace);
    add(toolbar, BorderLayout.NORTH);

    // Canvas component
    int canvasWidth = interaction.getCanvasWidth();
    int canvasHeight = interaction.getCanvasHeight();
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      final Graphic g = interaction.getRenderer().apply(state.getModel());
      canvasWidth = (int) g.getWidth();
      canvasHeight = (int) g.getHeight();
    }

    graphicCanvas = new GuiGraphicCanvas(canvasWidth, canvasHeight);
    graphicCanvas.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent ev) {
        final KeyboardKey key = new KeyboardKey(ev);
        traceEvent(new TraceEvent.KeyPressed(key));
      }

      @Override
      public void keyReleased(KeyEvent ev) {
        final KeyboardKey key = new KeyboardKey(ev);
        traceEvent(new TraceEvent.KeyReleased(key));
      }

      @Override
      public void keyTyped(KeyEvent ev) {
        final KeyboardChar ch = new KeyboardChar(ev);
        traceEvent(new TraceEvent.KeyTyped(ch));
      }
    });

    graphicCanvas.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent ev) {
        graphicCanvas.requestFocus();
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        final MouseButton button = new MouseButton(ev);
        traceEvent(new TraceEvent.MousePressed(coordinate, button));
      }

      @Override
      public void mouseReleased(MouseEvent ev) {
        graphicCanvas.requestFocus();
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        final MouseButton button = new MouseButton(ev);
        traceEvent(new TraceEvent.MouseReleased(coordinate, button));
      }
    });
    graphicCanvas.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent ev) {
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        traceEvent(new TraceEvent.MouseMoved(coordinate));
      }

      @Override
      public void mouseDragged(MouseEvent ev) {
        final Coordinate coordinate = new Coordinate(ev.getX(), ev.getY());
        traceEvent(new TraceEvent.MouseMoved(coordinate));
      }
    });

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent winEvt) {
        timer.stop();
      }
    });
    add(graphicCanvas, BorderLayout.CENTER);

    pack();

    SwingUtilities.invokeLater(() -> {
      renderAndShowGraphic();
      // Request focus to capture keyboard and mouse events.
      graphicCanvas.requestFocus();

      // Start the timer
      timer.start();
      // The timer is now running, configure the toolbar buttons
      toolbar.configureButtons();
    });
  }

  private void onTick(ActionEvent ev) {
    if (interaction.getStoppingPredicate().apply(state.getModel())) {
      timer.stop();
    } else {
      traceEvent(new TraceEvent.Tick());
      state.tick();
    }
  }

  private void traceEvent(TraceEvent event) {
    if (!timer.isRunning()) {
      return;
    }

    eventsTrace.append(event);
    final M before = state.getModel();
    final M after = event.process(interaction, before);
    state.update("Model after " + event.getKind(), after);
    renderAndShowGraphic();
  }

  private void renderAndShowGraphic() {
    final Graphic graphic = interaction.getRenderer().apply(state.getModel());
    graphicCanvas.setGraphic(graphic);
  }
}
