package jtamaro.interaction;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import jtamaro.io.IO;

final class InteractionToolbar<M> extends JPanel {

  private final JLabel tickLabel;

  private final JButton startButton;

  private final JButton stopButton;

  private final JButton showGraphicButton;

  private final Timer timer;

  public InteractionToolbar(Interaction<M> bang,
                            InteractionState<M> state,
                            Timer timer,
                            Trace trace) {
    this.timer = timer;

    tickLabel = new JLabel(String.valueOf(state.getTick()));
    add(tickLabel);

    startButton = new JButton("Start");
    stopButton = new JButton("Stop");
    showGraphicButton = new JButton("Inspect frame");
    configureButtons();
    add(startButton);
    add(stopButton);

    final JButton showTraceTableButton = new JButton("Show Trace");
    add(showTraceTableButton);
    final JButton showModelButton = new JButton("Show Model");
    add(showModelButton);
    add(showGraphicButton);

    // listeners
    startButton.addActionListener(ev -> start());
    stopButton.addActionListener(ev -> stop());
    showGraphicButton.addActionListener(ev -> IO.show(bang.getRenderer().apply(state.getModel())));
    showTraceTableButton.addActionListener(ev -> new TraceFrame(trace).setVisible(true));
    showModelButton.addActionListener(ev -> new ModelFrame<>(bang, trace).setVisible(true));
    state.addBigBangStateListener(s -> tickLabel.setText(String.valueOf(s.getTick())));
  }

  public void configureButtons() {
    final boolean isRunning = timer.isRunning();
    startButton.setEnabled(!isRunning);
    stopButton.setEnabled(isRunning);
    showGraphicButton.setEnabled(!isRunning);
  }

  private void start() {
    timer.start();
    configureButtons();
  }

  private void stop() {
    timer.stop();
    configureButtons();
  }
}
