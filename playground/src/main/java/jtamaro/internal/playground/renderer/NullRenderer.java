package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.OverlayImpl;
import jtamaro.internal.representation.RectangleImpl;
import jtamaro.internal.representation.TextImpl;

final class NullRenderer extends BaseObjectRenderer<Object> {

    @Override
    public boolean isSupported(Object o) {
        return o == null;
    }

    @Override
    protected GraphicImpl renderImpl(Object o) {
        final TextImpl text = new TextImpl("null", Font.SANS_SERIF, 16, Colors.WHITE.getImplementation());
        return new OverlayImpl(
                text,
                new RectangleImpl(text.getWidth() + 8.0, text.getHeight() + 2.0, Colors.MAGENTA.getImplementation()));
    }
}
