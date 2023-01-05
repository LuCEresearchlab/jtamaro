package jtamaro.en.bigbang;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jtamaro.en.Graphic;
import jtamaro.en.oo.AbstractGraphic;
import jtamaro.internal.gui.GraphicCanvas;
import jtamaro.internal.gui.RenderOptions;


public class BigBangFrame<M> extends JFrame {
  
  private final BigBang<M> bang;
  private final GraphicCanvas graphicCanvas;
  private final Timer timer;
  private final BigBangState<M> state;


  public BigBangFrame(BigBang<M> bang) {
    this.bang = bang;
    state = new BigBangState<>(bang);
    
    setTitle(bang.getName());
    add(new BigBangToolbar<>(state), BorderLayout.NORTH);

    int canvasWidth = bang.getCanvasWidth();
    int canvasHeight = bang.getCanvasHeight();
    if (canvasWidth < 0 || canvasHeight < 0) {
      System.out.println("Rendering initial model to determine size...");
      Graphic g = bang.getRenderer().apply(state.getModel());
      canvasWidth = (int)g.getWidth();
      canvasHeight = (int)g.getHeight();
      System.out.println("Canvas size is " + canvasWidth + "x" + canvasHeight);
    }

    RenderOptions renderOptions = new RenderOptions(0, canvasWidth, canvasHeight);
    graphicCanvas = new GraphicCanvas(renderOptions);
    graphicCanvas.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        state.update(
          "model after key press", 
          bang.getKeyPressHandler().apply(state.getModel(), new KeyboardKey(ev)));
        renderAndShowGraphic();
      }
      public void keyReleased(KeyEvent ev) {
        state.update(
          "model after key release",
          bang.getKeyReleaseHandler().apply(state.getModel(), new KeyboardKey(ev)));
        renderAndShowGraphic();
      }
      public void keyTyped(KeyEvent ev) {
        state.update(
          "model after key type",
          bang.getKeyTypeHandler().apply(state.getModel(), new KeyboardChar(ev)));
        renderAndShowGraphic();
      }
    });
    graphicCanvas.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent ev) {
        state.update(
          "model after mouse press",
          bang.getMousePressHandler().apply(state.getModel(), new Coordinate(ev.getX(), ev.getY()), new MouseButton(ev)));
        renderAndShowGraphic();
      }
      public void mouseReleased(MouseEvent ev) {
        state.update(
          "model after mouse release",
          bang.getMouseReleaseHandler().apply(state.getModel(), new Coordinate(ev.getX(), ev.getY()), new MouseButton(ev)));
        renderAndShowGraphic();
      }
    });
    graphicCanvas.addMouseMotionListener(new MouseAdapter() {
      public void mouseMoved(MouseEvent ev) {
        state.update(
          "model after mouse move (move)",
          bang.getMouseMoveHandler().apply(state.getModel(), new Coordinate(ev.getX(), ev.getY())));
        renderAndShowGraphic();
      }
      public void mouseDragged(MouseEvent ev) {
        state.update(
          "model after mouse move (drag)",
          bang.getMouseMoveHandler().apply(state.getModel(), new Coordinate(ev.getX(), ev.getY())));
        renderAndShowGraphic();
      }
    });
    add(graphicCanvas, BorderLayout.CENTER);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    
    SwingUtilities.invokeLater(() -> {
      renderAndShowGraphic();
      graphicCanvas.requestFocus();
    });
    
    timer = new Timer(bang.getMsBetweenTicks(), new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            if (state.getTick() >= bang.getTickLimit() || bang.getStoppingPredicate().test(state.getModel())) {
                stop();
            } else {
                state.updateForTick(bang.getTickHandler().apply(state.getModel()));
                renderAndShowGraphic();
            }
        }
    });
    
    timer.start();
  }

  private void renderAndShowGraphic() {
    Graphic graphic = bang.getRenderer().apply(state.getModel());
    setGraphic(graphic);
  }

  // called through the timer
  private void stop() {
      System.out.println("stopping");
      timer.stop();
      Graphic finalGraphic = bang.getFinalGraphicRenderer().apply(state.getModel());
      setGraphic(finalGraphic);
      if (bang.getCloseOnStop()) {
          setVisible(false);
      }
  }

  private void setGraphic(Graphic graphic) {
    AbstractGraphic abstractGraphic = (AbstractGraphic)graphic;
    graphicCanvas.setGraphic(abstractGraphic.getImplementation());
  }

}
