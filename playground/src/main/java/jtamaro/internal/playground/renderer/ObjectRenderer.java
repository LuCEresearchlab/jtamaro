package jtamaro.internal.playground.renderer;

import java.util.List;
import jtamaro.internal.representation.GraphicImpl;

public final class ObjectRenderer {

    private static final List<BaseObjectRenderer<?>> RENDERERS = List.of(
            // Always first
            new NullRenderer(),
            // Others
            new AbstractGraphicRenderer(),
            new AssertionErrorRenderer(),
            new GraphicImplRenderer(),
            new StatementResultRenderer(),
            new SequenceRenderer(ObjectRenderer::render),
            // Always last
            new ExceptionRenderer(),
            new DefaultRenderer()
    );

    private ObjectRenderer() {
    }

    public static GraphicImpl render(Object obj) {
        return getRenderer(obj).render(obj);
    }

    @SuppressWarnings("unchecked")
    private static <T> BaseObjectRenderer<? extends T> getRenderer(Object o) {
        for (BaseObjectRenderer<?> renderer : RENDERERS) {
            if (renderer.isSupported(o)) {
                return (BaseObjectRenderer<? extends T>) renderer;
            }
        }
        throw new IllegalArgumentException("No renderer for class: " + o.getClass().getName());
    }

}
