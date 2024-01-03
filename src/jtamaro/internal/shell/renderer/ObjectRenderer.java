package jtamaro.internal.shell.renderer;

import jtamaro.internal.representation.GraphicImpl;

public abstract class ObjectRenderer<T> {


    @SuppressWarnings("unchecked") // Actually checked
    public GraphicImpl render(Object o) {
        if (supportedClass().isAssignableFrom(o.getClass())) {
            return renderImpl((T) o);
        } else {
            throw new IllegalArgumentException("Wrong class");
        }
    }

    public abstract Class<T> supportedClass();

    protected abstract GraphicImpl renderImpl(T o);
}
