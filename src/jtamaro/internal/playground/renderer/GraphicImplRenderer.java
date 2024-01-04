package jtamaro.internal.playground.renderer;

import jtamaro.internal.representation.GraphicImpl;

final class GraphicImplRenderer extends BaseObjectRenderer<GraphicImpl> {

    @Override
    public boolean isSupported(Object o) {
        return GraphicImpl.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected GraphicImpl renderImpl(GraphicImpl graphic) {
        return graphic;
    }
}
