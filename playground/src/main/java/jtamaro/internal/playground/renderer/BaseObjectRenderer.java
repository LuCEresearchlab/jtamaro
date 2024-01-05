package jtamaro.internal.playground.renderer;

import jtamaro.en.Graphic;

public abstract class BaseObjectRenderer<T> {

    public abstract boolean isSupported(Object o);

    @SuppressWarnings("unchecked")
    public final Graphic render(Object o) {
        return renderImpl((T) o);
    }

    abstract Graphic renderImpl(T o);
}
