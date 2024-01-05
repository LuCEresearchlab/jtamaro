package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.OverlayImpl;
import jtamaro.internal.representation.RectangleImpl;
import jtamaro.internal.representation.TextImpl;

public class AssertionErrorRenderer extends BaseObjectRenderer<AssertionError> {

    @Override
    public boolean isSupported(Object o) {
        return AssertionError.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected GraphicImpl renderImpl(AssertionError err) {
        final String message = err.getMessage();
        final TextImpl text = new TextImpl(message == null ? "Assertion failed" : message,
                Font.SANS_SERIF, 20, Colors.WHITE.getImplementation());
        return new OverlayImpl(
                text,
                new RectangleImpl(text.getWidth() + 10, text.getHeight() + 5.0, Colors.RED.getImplementation()));
    }
}
