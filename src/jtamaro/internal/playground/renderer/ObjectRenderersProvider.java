package jtamaro.internal.playground.renderer;

import java.util.List;

public final class ObjectRenderersProvider {

    private static final List<ObjectRenderer<?>> RENDERERS = List.of(
            new AbstractGraphicRenderer(),
            new ExceptionRenderer(),
            new GraphicImplRenderer(),
            new StatementResultRenderer(),
            // Always last
            new DefaultRenderer()
    );

    private ObjectRenderersProvider() {
    }

    public static List<ObjectRenderer<?>> getRenderers() {
        return RENDERERS;
    }
}
