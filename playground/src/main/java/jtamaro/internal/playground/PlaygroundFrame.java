package jtamaro.internal.playground;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.GraphicFrame;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.playground.executor.LocalJvmExecutionControlProvider;
import jtamaro.internal.playground.executor.StatementResult;
import jtamaro.internal.playground.renderer.ObjectRenderer;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RectangleImpl;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public final class PlaygroundFrame extends JFrame {
    private static final Logger LOG = Logger.getLogger(PlaygroundFrame.class.getName());

    private static final String DEFAULT_IMPORTS = """
            import static jtamaro.en.Colors.*;
            import static jtamaro.en.Graphics.*;
            import static jtamaro.en.Points.*;
            import static jtamaro.en.Sequences.*;
            """;
    private static final String DEFAULT_TEXT = "rotate(15, circularSector(100, 330, YELLOW))";

    private final LocalJvmExecutionControlProvider execControlProvider;
    private final JShell shell;

    public PlaygroundFrame() {
        execControlProvider = new LocalJvmExecutionControlProvider();
        shell = JShell.builder()
                .executionEngine(execControlProvider, Map.of())
                .build();
        shell.addToClasspath(Paths.get("").toAbsolutePath().toString());

        initUI();
        initShell();
    }

    private void initShell() {
        shell.eval(DEFAULT_IMPORTS);
    }

    private void initUI() {
        final GraphicCanvas canvas = new GraphicCanvas(new RenderOptions(10));
        final DefaultListModel<SnippetEvent> inputHistoryModel = new DefaultListModel<>();

        // Main panel
        final RSyntaxTextArea inputTextArea = buildInput();
        final RTextScrollPane inputScrollPanel = new RTextScrollPane(inputTextArea);

        final ScrollPane inputHistoryPanel = buildHistoryPanel(canvas, inputHistoryModel, inputTextArea::setText);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputScrollPanel, BorderLayout.CENTER);
        mainPanel.add(canvas, BorderLayout.SOUTH);
        mainPanel.add(inputHistoryPanel, BorderLayout.EAST);

        // Menu bar
        final JMenuBar menuBar = new JMenuBar();

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem evalMenuItem = new JMenuItem("Evaluate");
        evalMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.ALT_DOWN_MASK));
        evalMenuItem.addActionListener(e -> evalCode(inputTextArea.getText(), canvas, inputTextArea::setText,
                inputHistoryModel::addElement));
        final JMenuItem exportCodeMenuItem = new JMenuItem("Export session");
        exportCodeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exportCodeMenuItem.addActionListener(e -> saveCode(inputHistoryModel));
        fileMenu.add(evalMenuItem);
        fileMenu.add(exportCodeMenuItem);

        final JMenu graphicsMenu = new JMenu("Graphics");
        final JMenuItem inspectGraphicMenuItem = new JMenuItem("Inspect");
        inspectGraphicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        inspectGraphicMenuItem.addActionListener(e -> openInspector(canvas.getGraphic()));
        graphicsMenu.add(inspectGraphicMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(graphicsMenu);

        setJMenuBar(menuBar);

        // Set default canvas graphic to empty
        canvas.setGraphic(new RectangleImpl(500, 200, Colors.TRANSPARENT.getImplementation()));

        setTitle("Playground");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private ScrollPane buildHistoryPanel(GraphicCanvas canvas,
                                         DefaultListModel<SnippetEvent> inputHistoryModel,
                                         Consumer<String> setInputText) {
        final JList<SnippetEvent> inputHistory = new JList<>(inputHistoryModel);
        inputHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inputHistory.setCellRenderer(new SnippetEventListCellRenderer());
        inputHistory.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        inputHistory.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySnippetResult(inputHistoryModel.get(inputHistory.getSelectedIndex()), canvas,
                        setInputText, inputHistoryModel::addElement, false);
            }
        });
        final ScrollPane inputHistoryScroll = new ScrollPane();
        inputHistoryScroll.add(inputHistory);
        return inputHistoryScroll;
    }

    private static RSyntaxTextArea buildInput() {
        final RSyntaxTextArea textArea = new RSyntaxTextArea(DEFAULT_TEXT, 8, 120);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setAutoIndentEnabled(true);

        return textArea;
    }

    private void evalCode(String code,
                          GraphicCanvas canvas,
                          Consumer<String> setInputFieldText,
                          Consumer<SnippetEvent> logInHistory) {
        final List<SnippetEvent> events = shell.eval(code);
        final int size = events.size();
        if (size == 0) {
            LOG.warning("Code evaluation produced no event");
            return;
        }

        final SnippetEvent event = events.get(size - 1);
        displaySnippetResult(event, canvas, setInputFieldText, logInHistory, true);
    }

    private void displaySnippetResult(SnippetEvent event,
                                      GraphicCanvas canvas,
                                      Consumer<String> setInputFieldText,
                                      Consumer<SnippetEvent> logInHistory,
                                      boolean newEntry) {
        final String key = event.value();
        final String source = event.snippet().source();
        final Snippet.Kind kind = event.snippet().kind();

        final Object obj;
        if (key != null) {
            obj = event.snippet().subKind().isExecutable()
                    ? execControlProvider.get().takeResult(key)
                    : key;

        } else if (event.causeSnippet() == null) {
            obj = Objects.requireNonNullElseGet(event.exception(), () -> switch (kind) {
                case ERRONEOUS -> new Exception("Syntax error");
                case IMPORT -> StatementResult.IMPORT;
                case METHOD -> StatementResult.METHOD_DECLARATION;
                case STATEMENT -> StatementResult.STATEMENT;
                case TYPE_DECL -> StatementResult.TYPE_DECLARATION;
                default -> "???";
            });
        } else {
            throw new IllegalStateException("How about we explore the area ahead of us later?");
        }

        if (kind != Snippet.Kind.ERRONEOUS) {
            if (newEntry) {
                logInHistory.accept(event);
                setInputFieldText.accept("");
            } else {
                setInputFieldText.accept(source);
            }
        }

        final Graphic graphic = ObjectRenderer.render(obj);
        if (graphic instanceof AbstractGraphic ag) {
            canvas.setGraphic(ag.getImplementation());
        } else {
            LOG.severe("Can't render a " + graphic.getClass().getName() + " representation of " + obj);
        }
    }

    private void saveCode(DefaultListModel<SnippetEvent> historyModel) {
        final List<SnippetEvent> history = new ArrayList<>(historyModel.size());
        for (int i = 0; i < historyModel.size(); i++) {
            history.add(historyModel.get(i));
        }

        final Optional<Exception> saveError = SessionFileGenerator.exportSnippetHistory(this, history);
        if (saveError.isPresent()) {
            final Exception exception = saveError.get();
            if (exception instanceof CancellationException) {
                // User aborted
                return;
            }
            JOptionPane.showMessageDialog(this,
                    "Failed to save session: " + exception.getMessage(),
                    "Session export",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Session exported successfully!",
                    "Session export",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openInspector(GraphicImpl graphic) {
        final GraphicFrame frame = new GraphicFrame();
        frame.setGraphic(graphic);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private static final class SnippetEventListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (value.getClass() != SnippetEvent.class) {
                throw new IllegalStateException("This class must be used with SnippetEvents only!");
            }
            final String textualValue = ((SnippetEvent) value).snippet().source();
            return super.getListCellRendererComponent(list, textualValue, index, isSelected, cellHasFocus);
        }
    }

    private static final class PlaygroundDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb,
                                 int offset,
                                 String text,
                                 AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, text.replace("\n", ""), attr);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb,
                            int offset,
                            int length,
                            String text,
                            AttributeSet attrs) throws BadLocationException {
            final int numNL = (int) text.chars().filter(c -> c == '\n').count();
            super.replace(fb, offset, length - numNL, text.replace("\n", ""), attrs);
        }
    }
}
