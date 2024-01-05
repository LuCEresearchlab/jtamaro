package jtamaro.internal.playground.renderer;

import java.util.List;
import jtamaro.internal.representation.GraphicImpl;

public final class ObjectRenderer {

    private static final List<BaseObjectRenderer<?>> RENDERERS = List.of(
            new AbstractGraphicRenderer(),
            new ExceptionRenderer(),
            new GraphicImplRenderer(),
            new StatementResultRenderer(),
            new SequenceRenderer(ObjectRenderer::render),
            // Always last
            new DefaultRenderer()
    );

    private ObjectRenderer() {
    }

    public static GraphicImpl render(Object obj) {
        return getRenderer(obj, obj.getClass()).render(obj);
    }

    @SuppressWarnings("unchecked")
    private static <T> BaseObjectRenderer<? extends T> getRenderer(Object o, Class<T> clazz) {
        for (BaseObjectRenderer<?> renderer : RENDERERS) {
            if (renderer.isSupported(o)) {
                return (BaseObjectRenderer<? extends T>) renderer;
            }
        }
        throw new IllegalArgumentException("No renderer for class: " + clazz.getName());
    }

}
