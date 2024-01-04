package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.TextImpl;

final class DefaultRenderer extends BaseObjectRenderer<Object> {

    @Override
    public boolean isSupported(Object o) {
        return true;
    }

    @Override
    protected GraphicImpl renderImpl(Object o) {
        return new TextImpl(o.toString(), Font.MONOSPACED, 12, Colors.BLACK.getImplementation());
    }
}
