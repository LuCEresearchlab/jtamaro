package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.TextImpl;

final class DefaultRenderer extends ObjectRenderer<Object> {

    @Override
    protected GraphicImpl renderImpl(Object o) {
        return new TextImpl(o.toString(), Font.MONOSPACED, 50, Colors.BLACK.getImplementation());
    }

    @Override
    public Class<Object> supportedClass() {
        return Object.class;
    }
}
