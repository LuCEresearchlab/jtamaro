package jtamaro.internal.shell.renderer;

import jtamaro.internal.representation.GraphicImpl;

final class GraphicImplRenderer extends ObjectRenderer<GraphicImpl> {

    @Override
    public Class<GraphicImpl> supportedClass() {
        return GraphicImpl.class;
    }

    @Override
    protected GraphicImpl renderImpl(GraphicImpl graphic) {
        return graphic;
    }
}
