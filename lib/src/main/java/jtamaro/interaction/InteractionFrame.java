package jtamaro.interaction;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
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
 *
 * @implNote The interaction involves the use of two threads: the AWT thread and the "executor"
 * thread. The first is the UI / AWT thread and is also the thread of "this" class. The other is a
 * thread that is responsible for evolving the state. (See the {@link InteractionExecutor} class.)
 * The AWT thread and the executor thread share a {@link ReentrantLock} that is used by the AWT
 * thread to allow the user to suspend the evolution of the interaction ("Start" / "Stop" actions).
 * The executor thread can communicate back to the AWT thread by using the
 * {@link SwingUtilities#invokeLater(Runnable)} method, while the AWT thread can communicate by
 * scheduling new events using {@link ScheduledExecutorService#schedule(Runnable, long, TimeUnit)}
 * on the {@link #schedulerService} field.
 */
final class InteractionFrame<M> extends JFrame {

  static {
    // Use system menu bar on macOS
    System.setProperty("apple.laf.useScreenMenuBar", "true");
  }

  private final ScheduledExecutorService schedulerService;

  private final ReentrantLock executorLock;

  private final InteractionExecutor<M> executor;

  private final Trace<M> eventsTrace;

  private final Function1<M, Graphic> renderer;

  private final JMenuItem startStopItem;

  private final JButton startStopButton;

  private final JLabel tickLabel;

  private final GuiGraphicCanvas graphicCanvas;

  public InteractionFrame(Interaction<M> interaction) {
    super();
    executorLock = new ReentrantLock();
    // We will unlock once the UI has been set up so that ticks are not executed
    // while the UI is getting ready
    executorLock.lock();
    executor = new InteractionExecutor<>(interaction, this::onEvent, executorLock);
    schedulerService = Executors.newSingleThreadScheduledExecutor();
    schedulerService.scheduleWithFixedDelay(
        executor,
        0L,
        interaction.getMsBetweenTicks(),
        TimeUnit.MILLISECONDS
    );

    renderer = interaction.getRenderer();
    eventsTrace = new Trace<>();

    setTitle(interaction.getName());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent winEvt) {
        // No need to call the stopExecutor() method, we don't need
        // to update UI components at this point.
        schedulerService.shutdownNow();
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
    startStopItem.addActionListener(e -> toggleExecutor());
    fileMenu.add(startStopItem);

    final JMenuItem viewFrame = new JMenuItem("Inspect Frame");
    viewFrame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    viewFrame.addActionListener(e -> SwingUtilities.invokeLater(() -> {
      stopExecutor();
      IO.show(renderer.apply(executor.getCurrentModel()));
    }));
    fileMenu.add(viewFrame);
    final JMenuItem viewBackground = new JMenuItem("Inspect Background");
    viewBackground.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F1, KeyEvent.SHIFT_DOWN_MASK));
    viewBackground.addActionListener(e -> SwingUtilities.invokeLater(() -> {
      stopExecutor();
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
    startStopButton.addActionListener(e -> toggleExecutor());
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
        this::dispatchToExecutor
    );
    graphicCanvas.addKeyListener(keyboardHandler);
    final InteractionMouseHandler<M> mouseHandler = new InteractionMouseHandler<>(
        interaction,
        graphicCanvas,
        this::dispatchToExecutor
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
      // Request focus to capture keyboard and mouse events
      graphicCanvas.requestFocus();

      // Render initial model
      renderAndShowGraphic();

      // Ready to go!
      executorLock.unlock();

      // The ticks are now ticking, configure the toolbar
      viewFrame.setEnabled(true);
      startStopItem.setText("Stop");
      startStopButton.setText("Stop");
    });
  }

  /**
   * We consider the interaction "stopped" if the ticksLock is locked and is currently being held by
   * this thread (the AWT thread).
   */
  private boolean isStopped() {
    return executorLock.isLocked() && executorLock.isHeldByCurrentThread();
  }

  private void toggleExecutor() {
    if (isStopped()) {
      startExecutor();
    } else {
      stopExecutor();
    }
  }

  private void stopExecutor() {
    if (isStopped()) {
      return;
    }

    executorLock.lock();

    SwingUtilities.invokeLater(() -> {
      startStopItem.setText("Start");
      startStopButton.setText("Start");
    });
  }

  private void startExecutor() {
    if (!isStopped()) {
      return;
    }

    executorLock.unlock();

    SwingUtilities.invokeLater(() -> {
      startStopItem.setText("Stop");
      startStopButton.setText("Stop");
    });
  }

  private void dispatchToExecutor(TraceEvent<M> event) {
    schedulerService.schedule(() -> executor.traceEvent(event), 0L, TimeUnit.MILLISECONDS);
  }

  private void onEvent(TraceEvent<M> event) {
    eventsTrace.append(event);

    SwingUtilities.invokeLater(() -> {
      if (event instanceof TraceEvent.Tick<M> tickEvent) {
        tickLabel.setText(String.valueOf(tickEvent.getTickNumber()));
      }

      renderAndShowGraphic();
    });
  }

  private void renderAndShowGraphic() {
    final M model = executor.getCurrentModel();
    graphicCanvas.setGraphic(renderer.apply(model));
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
