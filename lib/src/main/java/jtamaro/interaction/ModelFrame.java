package jtamaro.interaction;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jtamaro.data.Sequence;

final class ModelFrame<M> extends JFrame {

  private final Interaction<M> bang;

  private final JTextArea textArea;

  public ModelFrame(Interaction<M> bang, Trace trace) {
    setTitle("Model");
    this.bang = bang;
    textArea = new JTextArea(3, 40);
    add(new JScrollPane(textArea));

    final TraceListener tl = ev -> {
      final M model = getLastModel(trace.getEventSequence(), bang.getInitialModel());
      textArea.setText(model.toString());
    };
    trace.addTraceListener(tl);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        trace.removeTraceListener(tl);
      }
    });

    pack();
  }

  private M getLastModel(Sequence<TraceEvent> events, M model) {
    if (events.isEmpty()) {
      return model;
    } else {
      return events.first().process(bang, getLastModel(events.rest(), model));
    }
  }
}
