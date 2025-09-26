package jtamaro.interaction;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jtamaro.data.Sequence;

final class ModelFrame<M> extends JFrame {

  private static final Logger LOGGER = Logger.getLogger(ModelFrame.class.getName());

  private static final String EOL = System.lineSeparator();

  public ModelFrame(Interaction<M> bang, Trace<M> trace) {
    super();
    setTitle("Model");
    final JTextArea textArea = new JTextArea();
    textArea.setMargin(new Insets(8, 8, 8, 8));
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    textArea.setEditable(false);
    textArea.setFocusable(false);
    final JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    add(scrollPane);

    final TraceListener tl = _ -> {
      final M model = getLastModel(trace.getEventSequence(), bang.getInitialModel());
      textArea.setText(prettyPrint(model, 0));
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

  private String prettyPrint(Object obj, int indent) {
    return switch (obj) {
      case Sequence<?> seq -> prettyPrintSequence(seq, indent + 1);
      case Record rec -> prettyPrintRecord(rec, indent + 1);
      default -> obj.toString();
    };
  }

  private String prettyPrintSequence(Sequence<?> seq, int indent) {
    final String prefix = "  ".repeat(indent) + "• ";
    return seq.map(it -> prefix + prettyPrint(it, indent))
        .reduce("", (it, acc) -> acc + EOL + it);
  }

  private String prettyPrintRecord(Record rec, int indent) {
    final Class<?> clazz = rec.getClass();
    final String recName = clazz.getSimpleName();
    final String prefix = "  ".repeat(indent) + "• ";

    final String contents = Arrays.stream(clazz.getRecordComponents()).map(component -> {
      final String compName = component.getName();
      final StringBuilder compSb = new StringBuilder()
          .append(prefix)
          .append(compName)
          .append(": ");
      try {
        final Method accessor = component.getAccessor();
        final boolean isAccessible = accessor.canAccess(rec);
        if (!isAccessible) {
          accessor.setAccessible(true);
        }

        compSb.append(prettyPrint(accessor.invoke(rec), indent));

        if (!isAccessible) {
          accessor.setAccessible(false);
        }
      } catch (IllegalAccessException | InvocationTargetException e) {
        LOGGER.log(Level.WARNING, "Failed to read component"
            + compName
            + " of record class "
            + recName, e);
        compSb.append("???");
      }

      return compSb.toString();
    }).collect(Collectors.joining(EOL));

    return recName
        + EOL
        + contents;
  }
}
