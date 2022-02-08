package jtamaro.de;

import jtamaro.de.fun.Op;


public class Demo {
  
  public static void main(String[] args) {
    Grafik h = Op.rechteck(200, 60, Farbe.WEISS);
    Grafik v = Op.rechteck(60, 200, Farbe.WEISS);
    Grafik kreuz = Op.ueberlagere(h, v);
    Grafik grund = Op.rechteck(320, 320, Farbe.ROT);
    Grafik fahne = Op.ueberlagere(kreuz, grund);
    IO.zeige(fahne);
  }

}
