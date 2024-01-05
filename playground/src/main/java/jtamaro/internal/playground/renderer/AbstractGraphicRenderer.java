package jtamaro.internal.playground.renderer;

import jtamaro.en.Graphic;
import jtamaro.en.graphic.AbstractGraphic;

final class AbstractGraphicRenderer extends BaseObjectRenderer<AbstractGraphic> {

    @Override
    public boolean isSupported(Object o) {
        return AbstractGraphic.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected Graphic renderImpl(AbstractGraphic graphic) {
        return graphic;
    }
}
