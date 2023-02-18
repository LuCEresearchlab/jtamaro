package jtamaro.en.io;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class TraceFrame extends JFrame {
  
  public TraceFrame(final Trace trace) {
    setTitle("Event Trace");
    final TraceTableModel traceTableModel = new TraceTableModel(trace);
    final JTable table = new JTable(traceTableModel);
    final JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
    pack();
    setVisible(true);
  }

}
