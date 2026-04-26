package jtamaro.interaction;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * GUI frame that shows a trace of the events that evolved the model.
 */
final class TraceFrame<T> extends JFrame {

  public TraceFrame(Trace<T> trace) {
    super();
    setTitle("Event Trace");
    final TraceTableModel<T> traceTableModel = new TraceTableModel<>(trace);
    final Consumer<TraceEvent<T>> tl =
        _ -> traceTableModel.fireTableDataChanged();
    trace.addTraceListener(tl);
    final JTable table = new JTable(traceTableModel);
    final JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        trace.removeTraceListener(tl);
      }
    });
    pack();
    setVisible(true);
  }
}
