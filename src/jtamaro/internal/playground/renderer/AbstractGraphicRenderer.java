package jtamaro.internal.playground.renderer;

import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.internal.representation.GraphicImpl;

final class AbstractGraphicRenderer extends BaseObjectRenderer<AbstractGraphic> {

    @Override
    public boolean isSupported(Object o) {
        return AbstractGraphic.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected GraphicImpl renderImpl(AbstractGraphic graphic) {
        return graphic.getImplementation();
    }
}
