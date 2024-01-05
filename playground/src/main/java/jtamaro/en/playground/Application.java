package jtamaro.en.playground;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class Application {

    static {
        final ClassLoader cl = ClassLoader.getSystemClassLoader();
        cl.clearAssertionStatus();
        cl.setDefaultAssertionStatus(true);
    }

    private Application() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> loadFrame().setVisible(true));
    }

    @SuppressWarnings("unchecked")
    private static JFrame loadFrame() {
        try {
            // Load using reflection, otherwise assertion will not be forcefully enabled
            final Class<JFrame> frameClass = (Class<JFrame>) ClassLoader.getSystemClassLoader()
                    .loadClass("jtamaro.internal.playground.PlaygroundFrame");
            return frameClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}