package jtamaro.internal.shell.renderer;

import jtamaro.en.Colors;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.TextImpl;

final class DefaultRenderer extends ObjectRenderer<Object> {

    @Override
    protected GraphicImpl renderImpl(Object o) {
        return new TextImpl(o.toString(), "monospace", 50, Colors.BLACK.getImplementation());
    }

    @Override
    public Class<Object> supportedClass() {
        return Object.class;
    }
}
