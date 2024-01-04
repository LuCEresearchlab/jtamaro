package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.graphic.Overlay;
import jtamaro.en.graphic.Rectangle;
import jtamaro.en.graphic.Text;
import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.playground.executor.StatementResult;

import static jtamaro.en.Graphics.overlay;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.Graphics.text;

final class StatementResultRenderer extends BaseObjectRenderer<StatementResult> {

    @Override
    public boolean isSupported(Object o) {
        return StatementResult.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected GraphicImpl renderImpl(StatementResult em) {
        final String text = switch (em) {
            case IMPORT -> "Import completed";
            case METHOD_DECLARATION -> "Method declared";
            case STATEMENT -> "Statement executed";
            case TYPE_DECLARATION -> "Type declared";
        };
        final Text foreground = (Text) text(text, Font.MONOSPACED, 16, Colors.WHITE);
        final Rectangle background = (Rectangle) rectangle(foreground.getWidth() + 10,
                foreground.getHeight() + 5, Colors.BLACK);
        final Overlay graphic = (Overlay) overlay(foreground, background);
        return graphic.getImplementation();
    }
}
