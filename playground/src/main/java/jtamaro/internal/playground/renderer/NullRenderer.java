package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;

final class NullRenderer extends BaseObjectRenderer<Object> {

    @Override
    public boolean isSupported(Object o) {
        return o == null;
    }

    @Override
    protected Graphic renderImpl(Object o) {
        return Graphics.text("null", Font.MONOSPACED, 20, Colors.MAGENTA);
    }
}
