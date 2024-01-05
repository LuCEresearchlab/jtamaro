package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;

final class DefaultRenderer extends BaseObjectRenderer<Object> {

    @Override
    public boolean isSupported(Object o) {
        return true;
    }

    @Override
    protected Graphic renderImpl(Object o) {
        return Graphics.text(o.toString(), Font.MONOSPACED, 16, Colors.BLACK);
    }
}
