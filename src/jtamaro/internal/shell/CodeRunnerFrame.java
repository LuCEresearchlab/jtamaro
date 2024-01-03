package jtamaro.internal.shell;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import jtamaro.en.Colors;
import jtamaro.en.Function1;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.RectangleImpl;
import jtamaro.internal.representation.TextImpl;
import jtamaro.internal.shell.executor.LocalJvmExecutionControlProvider;
import jtamaro.internal.shell.renderer.ObjectRenderer;
import jtamaro.internal.shell.renderer.ObjectRenderersProvider;

public final class CodeRunnerFrame extends JFrame {
    private static final String DEFAULT_IMPORTS = """
            import static jtamaro.en.Colors.*;
            import static jtamaro.en.Graphics.*;
            import static jtamaro.en.Points.*;
            import static jtamaro.en.Sequences.*;
            """;
    private static final String DEFAULT_TEXT = "rotate(15, circularSector(100, 330, YELLOW))";
    private static final int DEFAULT_TEXT_SIZE = 50;

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

    private void initUI(GraphicCanvas canvas) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        final JTextField textField = buildInput(canvas, this::evalInput);
        mainPanel.add(canvas, BorderLayout.NORTH);
        mainPanel.add(textField, BorderLayout.SOUTH);

        canvas.setGraphic(new RectangleImpl(500, 500, Colors.TRANSPARENT.getImplementation()));

        setContentPane(mainPanel);
        pack();
    }

    private void initShell() {
        shell.eval(DEFAULT_IMPORTS);
    }

    private static JTextField buildInput(GraphicCanvas canvas,
                                         Function1<String, GraphicImpl> evalFn) {
        final JTextField textField = new JTextField(DEFAULT_TEXT, 80);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    canvas.setGraphic(evalFn.apply(textField.getText()));
                } else {
                    super.keyPressed(e);
                }
            }
        });
        return textField;
    }

    private GraphicImpl evalInput(String code) {
        final List<SnippetEvent> events = shell.eval(code);
        if (events.isEmpty()) {
            return new EmptyGraphicImpl();
        }

        final SnippetEvent event = events.get(events.size() - 1);
        final String key = event.value();
        if (key == null) {
            // Exception
            return new TextImpl("ERROR",
                    "monospace", DEFAULT_TEXT_SIZE, Colors.RED.getImplementation());
        }

        final Object result = event.snippet().subKind().isExecutable()
                ? execControlProvider.get().takeResult(key)
                : key;

        for (final ObjectRenderer<?> renderer : renderers) {
            if (renderer.supportedClass().isAssignableFrom(result.getClass())) {
                return renderer.render(result);
            }
        }

        // Should never happen
        throw new IllegalStateException("No renderer for " + result.getClass());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new CodeRunnerFrame();
            frame.setVisible(true);
        });
    }
}
