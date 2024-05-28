package jtamaro.io.graphic;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jtamaro.data.Sequence;

final class ModelFrame<M> extends JFrame {

  private final Interaction<M> bang;

  private final JTextArea textArea;

  public ModelFrame(final Interaction<M> bang, final Trace trace) {
    setTitle("Model");
    this.bang = bang;
    textArea = new JTextArea(3, 40);
    add(new JScrollPane(textArea));

    trace.addTraceListener(ev -> {
      final M model = getLastModel(trace.getEventSequence(), bang.getInitialModel());
      textArea.setText(model.toString());
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
