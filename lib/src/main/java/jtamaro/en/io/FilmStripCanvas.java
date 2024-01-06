package jtamaro.en.io;

import jtamaro.en.Graphic;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;
import jtamaro.en.graphic.AbstractGraphic;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;

import javax.swing.*;
import java.awt.*;

import static jtamaro.en.Sequences.*;


public class FilmStripCanvas extends JComponent {

  private static final double GAP_FRACTION = 0.05;
  private static final double TRACK_FRACTION = 0.2;
  private static final int MARK_DASH_COUNT = 20;
  private static final int HOLES = 8;
  private static final int MARGIN = 20;
  private static final Color BACKGROUND_COLOR = new Color(200, 200, 200);

  private final Sequence<Pair<Graphic, Integer>> indexedGraphics;
  private final int frameWidth;
  private final int frameHeight;
  private final int completeFrameWidth;
  private final int completeFrameHeight;
  private final int trackHeight;
  private final int gapWidth;
  private final double framesToShow;
  private final Stroke markStroke;
  private final Font frameNumberFont;
  private int position;


  public FilmStripCanvas(Sequence<Graphic> graphics, int frameWidth, int frameHeight) {
    indexedGraphics = zipWithIndex(graphics);
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;
    completeFrameWidth = computeCompleteFrameWidth(frameWidth);
    gapWidth = (int) (frameWidth * GAP_FRACTION);
    completeFrameHeight = (int) (frameHeight * (1 + 2 * TRACK_FRACTION));
    trackHeight = (int) (frameHeight * TRACK_FRACTION);
    position = 0;
    framesToShow = 2.3;
    final float sepDashLength = (float)completeFrameHeight / MARK_DASH_COUNT * 1 / 10;
    final float sepGapLength = (float)completeFrameHeight / MARK_DASH_COUNT * 9 / 10;
    markStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] {sepDashLength, sepGapLength}, sepDashLength + sepGapLength / 2);
    frameNumberFont = new Font(Font.MONOSPACED, Font.PLAIN, trackHeight / 5);
  }

  public int getNetWidth() {
    return getWidth() - 2 * MARGIN;
  }

  public static int computeCompleteFrameWidth(int frameWidth) {
    return (int) (frameWidth * (1 + GAP_FRACTION));
  }

  public void setPosition(int position) {
    this.position = position;
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(
      2 * MARGIN + (int)(framesToShow * completeFrameWidth), 
      2 * MARGIN + completeFrameHeight
    );
  }

  @Override
  protected void paintComponent(Graphics g) {
    final Graphics2D g2 = (Graphics2D) g;
    g2.setColor(BACKGROUND_COLOR);
    g2.fillRect(0, 0, getWidth(), getHeight());
    final RenderOptions renderOptions = new RenderOptions(0);
    g2.setRenderingHints(new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON)
    );
    final int windowWidth = getWidth();
    final int positionAtLeftWindowEdge = position - MARGIN;
    final int positionAtRightWindowEdge = positionAtLeftWindowEdge + windowWidth;
    final int firstVisibleFrame = Math.max(0, positionAtLeftWindowEdge / completeFrameWidth);
    final int lastVisibleFrame = Math.max(0, positionAtRightWindowEdge / completeFrameWidth);
    final int visibleFrameCount = lastVisibleFrame - firstVisibleFrame + 1;
    // System.out.println(
    //   "  |<: " + positionAtLeftWindowEdge + 
    //   "  |: " + position + 
    //   "  >|: " + positionAtRightWindowEdge + 
    //   ", #<: " + firstVisibleFrame +
    //   ", >#: " + lastVisibleFrame +
    //   ", #count: " + visibleFrameCount +
    //   ", windowWidth: " + windowWidth);
    final Sequence<Pair<Graphic,Integer>> visibleGraphics = 
      take(visibleFrameCount, drop(firstVisibleFrame, indexedGraphics));
    g2.translate(0, MARGIN);
    for (Pair<Graphic,Integer> indexedGraphic : visibleGraphics) {
      final Graphic graphic = indexedGraphic.first();
      final int frameIndex = indexedGraphic.second();
      final int offset = frameIndex * completeFrameWidth - position + MARGIN;
      // System.out.println(
      //   "   frameIndex: " + frameIndex +
      //   ", offset: " + offset
      // );
      g2.translate(offset, 0);
      drawFrameBackground(g2, graphic, renderOptions, frameIndex);
      final AbstractGraphic abstractGraphic = (AbstractGraphic) graphic;
      final GraphicImpl graphicImpl = abstractGraphic.getImplementation();
      //g2.setClip(0, 0, frameWidth, frameHeight);
      g2.translate(-graphicImpl.getBBox().getMinX() + gapWidth / 2.0, -graphicImpl.getBBox().getMinY() + trackHeight);
      graphicImpl.render(g2, renderOptions);
      g2.translate(graphicImpl.getBBox().getMinX() - gapWidth / 2.0, graphicImpl.getBBox().getMinY() - trackHeight);
      g2.translate(-offset, 0);
    }
  }

  private void drawFrameBackground(Graphics2D g2, Graphic graphic, RenderOptions renderOptions, int frameIndex) {
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, completeFrameWidth, completeFrameHeight);
    g2.setColor(Color.WHITE);
    g2.fillRect(gapWidth / 2, trackHeight, frameWidth, frameHeight);
    final int holeHeight = trackHeight * 5 / 10;
    final int holeY = trackHeight * 4 / 10;
    final int indexMargin = trackHeight * 1 / 10;
    final int stepWidth = completeFrameWidth / HOLES;
    final int holeWidth = stepWidth / 2;
    final int holeX = (stepWidth - holeWidth) / 2;
    for (int i = 0; i < HOLES; i++) {
      g2.fillRect(stepWidth * i + holeX, holeY, holeWidth, holeHeight);
      g2.fillRect(stepWidth * i + holeX, completeFrameHeight - holeHeight - holeY, holeWidth, holeHeight);
    }
    if (frameIndex > 0) {
      g2.setColor(Color.WHITE);
      final Stroke defaultStroke = g2.getStroke();
      g2.setStroke(markStroke);
      g2.drawLine(0, 0, 0, completeFrameHeight);
      g2.setStroke(defaultStroke);
    }
    g2.setColor(Color.YELLOW);
    g2.setFont(frameNumberFont);
    final FontMetrics metrics = g2.getFontMetrics(frameNumberFont);
    final String text = "Frame #" + frameIndex;
    g2.drawString(text, (completeFrameWidth - metrics.stringWidth(text)) / 2, completeFrameHeight - indexMargin);
  }

}
