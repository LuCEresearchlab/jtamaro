package jtamaro.interaction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jtamaro.data.Function1;
import jtamaro.data.Option;
import jtamaro.data.Pair;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.graphic.GuiGraphicCanvas;
import jtamaro.graphic.RenderOptions;
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

  private final Trace<M> eventsTrace;

  private final TraceEvent.Tick<M> tickEvent;

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

    this.eventsTrace = new Trace<>();
    // The tick event is not going to mutate across the whole lifecycle of the
    // interaction, and it would be re-created very often
    this.tickEvent = new TraceEvent.Tick<>(interaction.getTickHandler());
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
    final JMenuItem viewBackground = new JMenuItem("Inspect Background");
    viewBackground.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F1, KeyEvent.SHIFT_DOWN_MASK));
    viewBackground.addActionListener(e -> SwingUtilities.invokeLater(() -> {
      stopTimer();
      IO.show(interaction.getBackground().fold(Function1.identity(), Graphics::emptyGraphic));
    }));
    viewBackground.setEnabled(!interaction.getBackground().isEmpty());
    fileMenu.add(viewBackground);

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
    final Pair<Integer, Integer> canvasSize = computeCanvasSize(interaction);
    final RenderOptions renderOptions = new RenderOptions(
        0,
        canvasSize.first(),
        canvasSize.second(),
        false
    );
    final JPanel canvasPanel = new JPanel();
    canvasPanel.setLayout(new OverlayLayout(canvasPanel));

    graphicCanvas = new GuiGraphicCanvas(renderOptions);
    final InteractionKeyboardHandler<M> keyboardHandler = new InteractionKeyboardHandler<>(
        interaction,
        this::traceEvent
    );
    graphicCanvas.addKeyListener(keyboardHandler);
    final InteractionMouseHandler<M> mouseHandler = new InteractionMouseHandler<>(
        interaction,
        graphicCanvas,
        this::traceEvent
    );
    graphicCanvas.addMouseListener(mouseHandler);
    graphicCanvas.addMouseMotionListener(mouseHandler);
    canvasPanel.add(graphicCanvas);

    interaction.getBackground().stream().forEach(bg -> {
      final GuiGraphicCanvas bgCanvas = new GuiGraphicCanvas(renderOptions);
      bgCanvas.setGraphic(bg);
      canvasPanel.add(bgCanvas);
    });

    add(canvasPanel, BorderLayout.CENTER);

    // Show on instantiation
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
      traceEvent(tickEvent);
      state.tick();
      tickLabel.setText(String.valueOf(state.getTick()));
    }
  }

  private void traceEvent(TraceEvent<M> event) {
    if (!timer.isRunning()) {
      return;
    }

    eventsTrace.append(event);
    final M before = state.getModel();
    final M after = event.process(before);
    state.update("Model after " + event.getKind(), after);
    renderAndShowGraphic();
  }

  private void renderAndShowGraphic() {
    graphicCanvas.setGraphic(renderer.apply(state.getModel()));
  }

  private static <M> Pair<Integer, Integer> computeCanvasSize(Interaction<M> interaction) {
    // Either user-defined size...
    final int interactionWidth = interaction.getCanvasWidth();
    final int interactionHeight = interaction.getCanvasHeight();
    if (interactionWidth > 0 && interactionHeight > 0) {
      return new Pair<>(interactionWidth, interactionHeight);
    }

    // ...or the biggest between first frame and background
    final Graphic firstFrame = interaction.getRenderer().apply(interaction.getInitialModel());
    final int firstFrameWidth = (int) firstFrame.getWidth();
    final int firstFrameHeight = (int) firstFrame.getHeight();

    final Option<Graphic> bg = interaction.getBackground();
    final int bgWidth = bg.fold(g -> (int) g.getWidth(), () -> 0);
    final int bgHeight = bg.fold(g -> (int) g.getHeight(), () -> 0);
    return new Pair<>(
        Math.max(firstFrameWidth, bgWidth),
        Math.max(firstFrameHeight, bgHeight)
    );
  }

}
