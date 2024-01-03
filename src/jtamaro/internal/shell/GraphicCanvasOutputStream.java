package jtamaro.internal.shell;

import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.logging.Logger;
import jtamaro.en.Colors;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.TextImpl;

final class GraphicCanvasOutputStream extends OutputStream {
    private static final Logger LOG = Logger.getLogger(CodeRunnerFrame.class.getName());

    private final WeakReference<GraphicCanvas> canvasRef;
    private final boolean isError;
    private StringBuffer buffer;


    public GraphicCanvasOutputStream(GraphicCanvas canvas, boolean isError) {
        this.canvasRef = new WeakReference<>(canvas);
        this.isError = isError;
        this.buffer = new StringBuffer();
    }

    @Override
    public void write(int b) {
        buffer.append((char) b);
        drawCanvas();
    }

    public void clear() {
        buffer = new StringBuffer();
        setGraphic(new EmptyGraphicImpl());
    }

    private void drawCanvas() {
        setGraphic(new TextImpl(buffer.toString(),
                "monospace",
                20,
                (isError ? Colors.RED : Colors.BLACK).getImplementation()));
    }

    private void setGraphic(GraphicImpl graphic) {
        final GraphicCanvas canvas = canvasRef.get();
        if (canvas == null) {
            LOG.severe("Can't draw textual output due to missing canvas reference");
        } else {
            canvas.setGraphic(graphic);
        }
    }
}
