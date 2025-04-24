package jtamaro.interaction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jtamaro.data.Function1;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.GuiGraphicCanvas;
import jtamaro.io.IO;

/**
 * GUI that allows the user to interact with an {@link Interaction}.
 */
final class InteractionFrame<M> extends JFrame {

  static {
    // Use system menu bar on macOS
    System.setProperty("apple.laf.useScreenMenuBar", "true");
  }

  private final Interaction<M> interaction;

  private final InteractionState<M> state;

  // Save reference to the renderer for performance
  private final Function1<M, Graphic> renderer;

  private final Trace eventsTrace;

  private final Timer timer;

  private final JMenuItem startStopItem;

  private final JButton startStopButton;

  private final JLabel tickLabel;

  private final GuiGraphicCanvas graphicCanvas;

  public InteractionFrame(Interaction<M> interaction) {
    super();
    this.interaction = interaction;
    this.state = new InteractionState<>(interaction);
    this.renderer = interaction.getRenderer();
    this.eventsTrace = new Trace();
    this.timer = new Timer(interaction.getMsBetweenTicks(), this::onTick);

    setTitle(interaction.getName());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent winEvt) {
        // No need to call the stopTimer() method, we don't need
        // to update UI components at this point.
        timer.stop();
      }
    });

    // Menu bar
    final JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    final JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(fileMenu);

    startStopItem = new JMenuItem();
    startStopItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
    startStopItem.addActionListener(e -> toggleTimer());
    fileMenu.add(startStopItem);

    final JMenuItem viewFrame = new JMenuItem("Inspect Frame");
    viewFrame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    viewFrame.addActionListener(e -> SwingUtilities.invokeLater(() -> {
      stopTimer();
      IO.show(renderer.apply(state.getModel()));
    }));
    fileMenu.add(viewFrame);

    final JMenu viewMenu = new JMenu("View");
    viewMenu.setMnemonic(KeyEvent.VK_V);
    menuBar.add(viewMenu);

    final JMenuItem viewTrace = new JMenuItem("Trace");
    viewTrace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
    viewTrace.addActionListener(e -> SwingUtilities.invokeLater(() ->
        new TraceFrame(eventsTrace).setVisible(true)));
    viewMenu.add(viewTrace);

    final JMenuItem viewModel = new JMenuItem("Model");
    viewModel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
    viewModel.addActionListener(e -> SwingUtilities.invokeLater(() ->
        new ModelFrame<>(interaction, eventsTrace).setVisible(true)));
    viewMenu.add(viewModel);

    // Toolbar
    final JPanel toolbar = new JPanel();
    add(toolbar, BorderLayout.NORTH);

    tickLabel = new JLabel();
    toolbar.add(tickLabel, BorderLayout.WEST);

    toolbar.add(Box.createHorizontalStrut(20));

    startStopButton = new JButton();
    startStopButton.addActionListener(e -> toggleTimer());
    toolbar.add(startStopButton, BorderLayout.EAST);

    // Canvas component
    int canvasWidth = interaction.getCanvasWidth();
    int canvasHeight = interaction.getCanvasHeight();
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      final Graphic g = renderer.apply(state.getModel());
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
    add(graphicCanvas, BorderLayout.CENTER);

    pack();

    SwingUtilities.invokeLater(() -> {
      renderAndShowGraphic();
      // Request focus to capture keyboard and mouse events.
      graphicCanvas.requestFocus();

      // Start the timer
      startTimer();
      // The timer is now running, configure the toolbar buttons
      viewFrame.setEnabled(true);
    });
  }

  private void toggleTimer() {
    if (timer.isRunning()) {
      stopTimer();
    } else {
      startTimer();
    }
  }

  private void stopTimer() {
    if (!timer.isRunning()) {
      return;
    }

    startStopItem.setText("Start");
    startStopButton.setText("Start");
    timer.stop();
  }

  private void startTimer() {
    if (timer.isRunning()) {
      return;
    }

    startStopItem.setText("Stop");
    startStopButton.setText("Stop");
    timer.start();
  }

  private void onTick(ActionEvent ev) {
    if (interaction.getStoppingPredicate().apply(state.getModel())) {
      stopTimer();
    } else {
      traceEvent(new TraceEvent.Tick());
      state.tick();
      tickLabel.setText(String.valueOf(state.getTick()));
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
    graphicCanvas.setGraphic(renderer.apply(state.getModel()));
  }
}
