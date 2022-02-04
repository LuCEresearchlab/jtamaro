package jtamaro.de.fun;

import jtamaro.de.Farbe;
import jtamaro.de.Grafik;
import jtamaro.de.oo.Drehe;
import jtamaro.de.oo.Dreieck;
import jtamaro.de.oo.Ellipse;
import jtamaro.de.oo.KreisSektor;
import jtamaro.de.oo.LeereGrafik;
import jtamaro.de.oo.Rechteck;
import jtamaro.de.oo.Text;


public final class Op {

  // prevent instantiation
  private Op() {
  }
    
  //-- nullary graphic operations
  public Grafik leereGrafik() {
    return new LeereGrafik();
  }

  public Grafik kreisSektor(double radius, double winkel, Farbe farbe) {
    return new KreisSektor(radius, winkel, farbe);
  }

  public Grafik ellipse(double breite, double hoehe, Farbe farbe) {
    return new Ellipse(breite, hoehe, farbe);
  }

  public Grafik rechteck(double breite, double hoehe, Farbe farbe) {
    return new Rechteck(breite, hoehe, farbe);
  }

  public Grafik text(String inhalt, String schriftart, double punkte, Farbe farbe) {
    return new Text(inhalt, schriftart, punkte, farbe);
  }

  public Grafik dreieck(double seite, Farbe farbe) {
    return new Dreieck(seite, farbe);
  }

  //-- unary graphic operations
  public Grafik drehe(double winkel, Grafik grafik) {
    return new Drehe(winkel, grafik);
  }

}
