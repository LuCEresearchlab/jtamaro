package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.TextImpl;


public final class Text extends AbstrakteGrafik {

  public Text(String inhalt, String schriftart, double punkte, Farbe farbe) {
    super(new TextImpl(inhalt, schriftart, punkte, farbe.getImplementation()));
  }

}
