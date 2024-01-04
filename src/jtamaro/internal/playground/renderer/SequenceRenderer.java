package jtamaro.internal.playground.renderer;

import java.util.function.Function;
import jtamaro.en.Colors;
import jtamaro.en.Sequence;
import jtamaro.en.Sequences;
import jtamaro.en.data.LazyCons;
import jtamaro.internal.representation.BesideImpl;
import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.OverlayImpl;
import jtamaro.internal.representation.RectangleImpl;

public class SequenceRenderer extends BaseObjectRenderer<Sequence<?>> {
    private static final int INFINITE_SEQ_ITEMS = 8;
    private static final GraphicImpl SEPARATOR = new RectangleImpl(10, 4, Colors.BLACK.getImplementation());
    private static final GraphicImpl EMPTY_SEQ_GRAPHIC = new RectangleImpl(15, 10, Colors.RED.getImplementation());

    private final Function<Object, GraphicImpl> renderItem;

    public SequenceRenderer(Function<Object, GraphicImpl> renderItem) {
        this.renderItem = renderItem;
    }

    @Override
    public boolean isSupported(Object o) {
        return Sequence.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected GraphicImpl renderImpl(Sequence<?> seq) {
        final boolean hasIndefiniteSize = seq instanceof LazyCons<?> ls && !ls.hasDefiniteSize();
        final Sequence<?> finiteSeq = hasIndefiniteSize
                ? Sequences.take(INFINITE_SEQ_ITEMS, seq)
                : seq;

        final GraphicImpl result = Sequences.reduce((acc, item) -> new BesideImpl(acc,
                        new BesideImpl(renderItem(item), SEPARATOR)),
                (GraphicImpl) new EmptyGraphicImpl(),
                finiteSeq);
        if (result instanceof EmptyGraphicImpl) {
            return EMPTY_SEQ_GRAPHIC;
        } else {
            return new BesideImpl(result, hasIndefiniteSize ? renderItem("...") : EMPTY_SEQ_GRAPHIC);
        }
    }

    private GraphicImpl renderItem(Object item) {
        final GraphicImpl itemGraphic = renderItem.apply(item);
        final double height = itemGraphic.getHeight() + 10.0;
        final double width = itemGraphic.getWidth() + 5.0;
        final double innerHeight = height - 2.0;
        final double innerWidth = width - 2.0;

        return new BesideImpl(new OverlayImpl(new OverlayImpl(itemGraphic,
                new RectangleImpl(innerWidth, innerHeight,
                        Colors.WHITE.getImplementation())),
                new RectangleImpl(width, height, Colors.RED.getImplementation())),
                new RectangleImpl(10, height, Colors.RED.getImplementation()));
    }
}
