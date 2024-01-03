package jtamaro.internal.shell;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import jtamaro.en.Colors;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RectangleImpl;
import jtamaro.internal.shell.executor.LocalJvmExecutionControlProvider;
import jtamaro.internal.shell.renderer.ObjectRenderer;
import jtamaro.internal.shell.renderer.ObjectRenderersProvider;

public final class CodeRunnerFrame extends JFrame {
    private static final Logger LOG = Logger.getLogger(CodeRunnerFrame.class.getName());

    private static final String DEFAULT_IMPORTS = """
            import static jtamaro.en.Colors.*;
            import static jtamaro.en.Graphics.*;
            import static jtamaro.en.Points.*;
            import static jtamaro.en.Sequences.*;
            """;
    private static final String DEFAULT_TEXT = "rotate(15, circularSector(100, 330, YELLOW))";

    private final List<ObjectRenderer<?>> renderers = ObjectRenderersProvider.getRenderers();
    private final LocalJvmExecutionControlProvider execControlProvider;
    private final JShell shell;

    public CodeRunnerFrame() {
        final GraphicCanvas canvas = new GraphicCanvas(new RenderOptions(10));

        final GraphicCanvasOutputStream outStream = new GraphicCanvasOutputStream(canvas, false);
        final GraphicCanvasOutputStream errStream = new GraphicCanvasOutputStream(canvas, true);

        execControlProvider = new LocalJvmExecutionControlProvider();
        shell = JShell.builder()
                .executionEngine(execControlProvider, Map.of())
                .out(new PrintStream(outStream))
                .err(new PrintStream(errStream))
                .build();
        shell.addToClasspath(Paths.get("").toAbsolutePath().toString());

        initUI(canvas);
        initShell();
    }

    private void initShell() {
        shell.eval(DEFAULT_IMPORTS);
    }

    private void initUI(GraphicCanvas canvas) {
        final DefaultListModel<String> inputHistoryModel = new DefaultListModel<>();

        final JTextField inputField = buildInput(tf -> {
            final String code = tf.getText();
            inputHistoryModel.addElement(code);
            evalCode(code, canvas, tf::setText, true);
        });

        final ScrollPane inputHistoryPanel = buildHistoryPanel(canvas, inputHistoryModel, inputField);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(canvas, BorderLayout.CENTER);
        mainPanel.add(inputField, BorderLayout.SOUTH);
        mainPanel.add(inputHistoryPanel, BorderLayout.EAST);

        canvas.setGraphic(new RectangleImpl(500, 200, Colors.TRANSPARENT.getImplementation()));

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private ScrollPane buildHistoryPanel(GraphicCanvas canvas,
                                         DefaultListModel<String> inputHistoryModel,
                                         JTextField inputField) {
        final JList<String> inputHistory = new JList<>(inputHistoryModel);
        inputHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inputHistory.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                evalCode(inputHistoryModel.get(inputHistory.getSelectedIndex()), canvas,
                        inputField::setText, false);
            }
        });
        final ScrollPane inputHistoryScroll = new ScrollPane();
        inputHistoryScroll.add(inputHistory);
        return inputHistoryScroll;
    }

    private JTextField buildInput(Consumer<JTextField> submitCode) {
        final JTextField inputField = new JTextField(DEFAULT_TEXT, 80);
        inputField.setFont(Font.getFont("monospace"));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitCode.accept(inputField);
                } else {
                    super.keyPressed(e);
                }
            }
        });
        return inputField;
    }

    private void evalCode(String code,
                          GraphicCanvas canvas,
                          Consumer<String> setInputFieldText,
                          boolean clearInputField) {
        final List<SnippetEvent> events = shell.eval(code);
        if (events.isEmpty()) {
            LOG.warning("Code evaluation produced no event");
        }

        final SnippetEvent event = events.get(events.size() - 1);
        final String key = event.value();
        final Object obj;
        if (key == null) {
            // TODO: handle this
            obj = "ERROR!";
        } else if (event.snippet().subKind().isExecutable()) {
            obj = execControlProvider.get().takeResult(key);
        } else {
            obj = key;
        }

        final GraphicImpl graphic = buildGraphic(obj);
        canvas.setGraphic(graphic);
        setInputFieldText.accept(clearInputField ? "" : code);
    }

    private GraphicImpl buildGraphic(Object obj) {
        for (final ObjectRenderer<?> renderer : renderers) {
            if (renderer.supportedClass().isAssignableFrom(obj.getClass())) {
                return renderer.render(obj);
            }
        }

        // Should never happen
        throw new IllegalStateException("No renderer for " + obj.getClass());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new CodeRunnerFrame();
            frame.setVisible(true);
        });
    }
}
