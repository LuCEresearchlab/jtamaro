package jtamaro.internal.shell.renderer;

import jtamaro.en.Colors;
import jtamaro.en.graphic.Overlay;
import jtamaro.en.graphic.Rectangle;
import jtamaro.en.graphic.Text;
import jtamaro.internal.representation.GraphicImpl;

import static jtamaro.en.Graphics.overlay;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.Graphics.text;

final class ExceptionRenderer extends ObjectRenderer<Exception> {

    @Override
    public Class<Exception> supportedClass() {
        return Exception.class;
    }

    @Override
    protected GraphicImpl renderImpl(Exception e) {
        final Text message = (Text) text(e.getMessage(), "monospace", 50, Colors.WHITE);
        final Rectangle background = (Rectangle) rectangle(message.getWidth() + 10,
                message.getHeight() + 5, Colors.RED);
        final Overlay graphic = (Overlay) overlay(message, background);
        return graphic.getImplementation();
    }
}
