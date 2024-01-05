package jtamaro.internal.playground.renderer;

import jtamaro.internal.representation.GraphicImpl;

public abstract class BaseObjectRenderer<T> {

    public abstract boolean isSupported(Object o);

    @SuppressWarnings("unchecked")
    public final GraphicImpl render(Object o) {
        return renderImpl((T) o);
    }

    protected abstract GraphicImpl renderImpl(T o);
}
