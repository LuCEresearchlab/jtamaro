package jtamaro.internal.shell.renderer;

import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.internal.representation.GraphicImpl;

final class AbstractGraphicRenderer extends ObjectRenderer<AbstractGraphic> {

    @Override
    public Class<AbstractGraphic> supportedClass() {
        return AbstractGraphic.class;
    }

    @Override
    protected GraphicImpl renderImpl(AbstractGraphic graphic) {
        return graphic.getImplementation();
    }
}
