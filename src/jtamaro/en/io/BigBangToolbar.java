package jtamaro.en.io;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;


public class BigBangToolbar<M> extends JPanel {

  private final JLabel tickLabel;
  private final JButton startButton;
  private final JButton stopButton;
  private final Timer timer;
  private final Trace trace;

  public BigBangToolbar(final Interaction<M> bang, final BigBangState<M> state, final Timer timer, final Trace trace) {
    tickLabel = new JLabel("" + state.getTick());
    this.timer = timer;
    this.trace = trace;
    add(tickLabel);
    startButton = new JButton("Start");
    stopButton = new JButton("Stop");
    configureButtons();
    add(startButton);
    add(stopButton);
    final JButton showTraceTableButton = new JButton("Show Trace");
    add(showTraceTableButton);
    final JButton showModelButton = new JButton("Show Model");
    add(showModelButton);

    // listeners
    startButton.addActionListener(ev -> start());
    stopButton.addActionListener(ev -> stop());
    showTraceTableButton.addActionListener(ev -> new TraceFrame(trace).setVisible(true));
    showModelButton.addActionListener(ev -> new ModelFrame<M>(bang, trace).setVisible(true));
    state.addBigBangStateListener(s -> {tickLabel.setText("" + s.getTick());});
  }

  public void configureButtons() {
    startButton.setEnabled(!timer.isRunning());
    stopButton.setEnabled(timer.isRunning());
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
