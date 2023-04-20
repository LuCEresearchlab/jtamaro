package jtamaro.en.io;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;


public final class TraceTableModel implements TableModel {

  private static final int INDEX_COLUMN = 0;
  private static final int TIME_COLUMN = 1;
  private static final int EVENT_COLUMN = 2;
  private static final int COLUMN_COUNT = 3;

  private static final String[] COLUMN_NAMES = {
      "Index", "Time", "Event"
  };

  private static final Class<?>[] COLUMN_CLASSES = {
      Integer.class, Long.class, String.class
  };

  private final ArrayList<TableModelListener> listeners;
  private final Trace trace;

  public TraceTableModel(Trace trace) {
    this.trace = trace;
    listeners = new ArrayList<>();
    trace.addTraceListener(event -> fireTableDataChanged());
  }

  @Override
  public void addTableModelListener(TableModelListener li) {
    listeners.add(li);
  }

  @Override
  public void removeTableModelListener(TableModelListener li) {
    listeners.remove(li);
  }

  @Override
  public int getColumnCount() {
    return COLUMN_COUNT;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return COLUMN_NAMES[columnIndex];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return COLUMN_CLASSES[columnIndex];
  }

  @Override
  public int getRowCount() {
    return trace.length();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    TraceEvent event = trace.get(getRowCount() - 1 - rowIndex);
    switch (columnIndex) {
      case INDEX_COLUMN:
        return rowIndex;
      case TIME_COLUMN:
        TraceEvent first = trace.get(getRowCount() - 1);
        long startTime = first.getTimeStamp();
        long eventTime = event.getTimeStamp();
        long delta = eventTime - startTime;
        int minutes = (int) (delta / 60000);
        int seconds = (int) ((delta % 60000) / 1000);
        int millis = (int) (delta % 1000);
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
      case EVENT_COLUMN:
        return event.getKind();
      default:
        throw new IllegalArgumentException("Invalid column index: " + columnIndex);
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    throw new UnsupportedOperationException();
  }

  private void fireTableDataChanged() {
    for (TableModelListener listener : listeners) {
      listener.tableChanged(null);
    }
  }

}
