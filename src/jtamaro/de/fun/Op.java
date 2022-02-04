package jtamaro.de.fun;

import jtamaro.de.Farbe;
import jtamaro.de.Grafik;
import jtamaro.de.oo.Drehe;
import jtamaro.de.oo.Dreieck;
import jtamaro.de.oo.Ellipse;
import jtamaro.de.oo.Fixiere;
import jtamaro.de.oo.Kombiniere;
import jtamaro.de.oo.KreisSektor;
import jtamaro.de.oo.LeereGrafik;
import jtamaro.de.oo.Neben;
import jtamaro.de.oo.Rechteck;
import jtamaro.de.oo.Text;
import jtamaro.de.oo.Ueber;
import jtamaro.de.oo.Ueberlagere;


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

  public Grafik fixiere(String horizontalePosition, String vertikalePosition, Grafik grafik) {
    return new Fixiere(horizontalePosition, vertikalePosition, grafik);
  }

  
  //-- binary graphic operations
  public Grafik kombiniere(Grafik vordereGrafik, Grafik hintereGrafik) {
    return new Kombiniere(vordereGrafik, hintereGrafik);
  }

  public Grafik ueberlagere(Grafik vordereGrafik, Grafik hintereGrafik) {
    return new Ueberlagere(vordereGrafik, hintereGrafik);
  }

  public Grafik neben(Grafik linkeGrafik, Grafik rechteGrafik) {
    return new Neben(linkeGrafik, rechteGrafik);
  }

  public Grafik ueber(Grafik obereGrafik, Grafik untereGrafik) {
    return new Ueber(obereGrafik, untereGrafik);
  }

}
