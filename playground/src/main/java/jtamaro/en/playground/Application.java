package jtamaro.en.playground;

import javax.swing.SwingUtilities;
import jtamaro.internal.playground.PlaygroundFrame;

public final class Application {


    private Application() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlaygroundFrame().setVisible(true));
    }

}