package jtamaro.de.fun;

import jtamaro.de.Farbe;
import jtamaro.de.Grafik;
import jtamaro.de.Punkt;
import jtamaro.de.oo.*;


public final class Op {

  // prevent instantiation
  private Op() {
  }


  //-- nullary graphic operations
  public static Grafik leereGrafik() {
    return new LeereGrafik();
  }

  public static Grafik kreisSektor(double radius, double winkel, Farbe farbe) {
    return new KreisSektor(radius, winkel, farbe);
  }

  public static Grafik ellipse(double breite, double hoehe, Farbe farbe) {
    return new Ellipse(breite, hoehe, farbe);
  }

  public static Grafik rechteck(double breite, double hoehe, Farbe farbe) {
    return new Rechteck(breite, hoehe, farbe);
  }

  public static Grafik text(String inhalt, String schriftart, double punkte, Farbe farbe) {
    return new Text(inhalt, schriftart, punkte, farbe);
  }

  //OLD
  public static Grafik gleichseitigesDreieck(double seite, Farbe farbe) {
    return new GleichseitigesDreieck(seite, farbe);
  }

  public static Grafik dreieck(double seite1, double seite2, double winkel, Farbe farbe) {
    return new Dreieck(seite1, seite2, winkel, farbe);
  }


  //-- unary graphic operations
  public static Grafik drehe(double winkel, Grafik grafik) {
    return new Drehe(winkel, grafik);
  }

  public static Grafik fixiere(Punkt punkt, Grafik grafik) {
    return new Fixiere(punkt, grafik);
  }


  //-- binary graphic operations
  public static Grafik kombiniere(Grafik vordereGrafik, Grafik hintereGrafik) {
    return new Kombiniere(vordereGrafik, hintereGrafik);
  }

  public static Grafik ueberlagere(Grafik vordereGrafik, Grafik hintereGrafik) {
    return new Ueberlagere(vordereGrafik, hintereGrafik);
  }

  public static Grafik neben(Grafik linkeGrafik, Grafik rechteGrafik) {
    return new Neben(linkeGrafik, rechteGrafik);
  }

  public static Grafik ueber(Grafik obereGrafik, Grafik untereGrafik) {
    return new Ueber(obereGrafik, untereGrafik);
  }

}
