package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;

final class ExceptionRenderer extends BaseObjectRenderer<Exception> {

    @Override
    public boolean isSupported(Object o) {
        return Exception.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected Graphic renderImpl(Exception e) {
        return Graphics.text("Error: " + e.getMessage(),
                Font.MONOSPACED, 16, Colors.RED);
    }
}
