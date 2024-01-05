package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;

public class AssertionErrorRenderer extends BaseObjectRenderer<AssertionError> {

    @Override
    public boolean isSupported(Object o) {
        return AssertionError.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected Graphic renderImpl(AssertionError err) {
        final String message = err.getMessage();
        return Graphics.text(message == null ? "Assertion failed" : message,
                Font.SANS_SERIF, 20, Colors.WHITE);
    }
}
