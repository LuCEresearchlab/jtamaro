package jtamaro.en.io;

import javax.swing.JPanel;
import javax.swing.JLabel;


public class BigBangToolbar<M> extends JPanel {

  private final JLabel tickLabel;


  public BigBangToolbar(BigBangState<M> state) {
    tickLabel = new JLabel("" + state.getTick());
    add(tickLabel);

    state.addBigBangStateListener(s -> {tickLabel.setText("" + s.getTick());});
  }
  
}
