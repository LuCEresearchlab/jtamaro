package jtamaro.interaction;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * GUI frame that shows a trace of the events that evolved the model.
 */
final class TraceFrame extends JFrame {

  public TraceFrame(Trace trace) {
    super();
    setTitle("Event Trace");
    final TraceTableModel traceTableModel = new TraceTableModel(trace);
    final JTable table = new JTable(traceTableModel);
    final JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
    pack();
    setVisible(true);
  }
}
