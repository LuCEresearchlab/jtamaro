package jtamaro.interaction;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jtamaro.data.Sequence;

final class ModelFrame<M> extends JFrame {

  private final JTextArea textArea;

  public ModelFrame(Interaction<M> bang, Trace<M> trace) {
    super();
    setTitle("Model");
    textArea = new JTextArea(3, 40);
    add(new JScrollPane(textArea));

    final TraceListener tl = ev -> {
      final M model = getLastModel(trace.getEventSequence(), bang.getInitialModel());
      textArea.setText(model.toString());
    };
    // Load current model
    tl.eventAppended(null);
    // Register listener
    trace.addTraceListener(tl);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        trace.removeTraceListener(tl);
      }
    });

    pack();
  }

  private M getLastModel(Sequence<TraceEvent<M>> events, M model) {
    return events.isEmpty()
        ? model
        : events.first().process(getLastModel(events.rest(), model));
  }
}
