package jtamaro.internal.playground.renderer;

import java.awt.Font;
import jtamaro.en.Colors;
import jtamaro.en.Graphic;
import jtamaro.en.Graphics;
import jtamaro.internal.playground.executor.StatementResult;

final class StatementResultRenderer extends BaseObjectRenderer<StatementResult> {

    @Override
    public boolean isSupported(Object o) {
        return StatementResult.class.isAssignableFrom(o.getClass());
    }

    @Override
    protected Graphic renderImpl(StatementResult em) {
        final String text = switch (em) {
            case IMPORT -> "Import completed";
            case METHOD_DECLARATION -> "Method declared";
            case STATEMENT -> "Statement executed";
            case TYPE_DECLARATION -> "Type declared";
        };

        return Graphics.text(text, Font.MONOSPACED, 16, Colors.BLACK);
    }
}
