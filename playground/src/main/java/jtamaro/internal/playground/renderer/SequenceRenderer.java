package jtamaro.internal.playground.renderer;

import java.util.function.Function;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;
import jtamaro.en.Sequence;
import jtamaro.en.Sequences;
import jtamaro.en.data.LazyCons;
import jtamaro.en.graphic.EmptyGraphic;
import jtamaro.internal.representation.BesideImpl;
import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.OverlayImpl;
import jtamaro.internal.representation.RectangleImpl;

public class SequenceRenderer extends BaseObjectRenderer<Sequence<?>> {
    private static final int INFINITE_SEQ_ITEMS = 8;
    private static final Graphic SEPARATOR = Graphics.rectangle(8, 4, Colors.BLACK);
    private static final Graphic EMPTY_SEQ_GRAPHIC = Graphics.rectangle(12, 12, Colors.RED);

    private final Function<Object, Graphic> renderItem;

    public SequenceRenderer(Function<Object, Graphic> renderItem) {
        this.renderItem = renderItem;
    }

    @Override
    public boolean isSupported(Object o) {
        return Sequence.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected Graphic renderImpl(Sequence<?> seq) {
        final boolean hasIndefiniteSize = seq instanceof LazyCons<?> ls && !ls.hasDefiniteSize();
        final Sequence<?> finiteSeq = hasIndefiniteSize
                ? Sequences.take(INFINITE_SEQ_ITEMS, seq)
                : seq;

        final Graphic result = Sequences.reduce(
                (acc, item) -> Graphics.beside(acc, Graphics.beside(renderItem(item), SEPARATOR)),
                Graphics.emptyGraphic(),
                finiteSeq);
        if (result instanceof EmptyGraphic) {
            return EMPTY_SEQ_GRAPHIC;
        } else {
            return Graphics.beside(result, hasIndefiniteSize ? renderItem("...") : EMPTY_SEQ_GRAPHIC);
        }
    }

    private Graphic renderItem(Object item) {
        final Graphic itemGraphic = renderItem.apply(item);
        final double outerHeight = itemGraphic.getHeight() + 12.0;
        final double outerWidth = itemGraphic.getWidth() + 8.0;
        final double innerHeight = outerHeight - 2.0;
        final double innerWidth = outerWidth - 2.0;

        return Graphics.beside(
                Graphics.overlay(
                        Graphics.overlay(
                                itemGraphic,
                                Graphics.rectangle(innerWidth, innerHeight, Colors.WHITE)),
                        Graphics.rectangle(outerWidth, outerHeight, Colors.RED)),
                Graphics.rectangle(8, outerHeight, Colors.RED));
    }
}
