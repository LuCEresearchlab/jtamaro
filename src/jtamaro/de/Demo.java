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

    Grafik pac = Op.kreisSektor(200, 270, Farbe.GELB);
    Grafik pacman = Op.drehe(45, pac);
    IO.zeige(pacman);

    Grafik d = Op.dreieck(300, Farbe.MAGENTA);
    Grafik dr = Op.drehe(15, d);
    IO.zeige(dr);

    IO.zeige(sektoren(60));
  }

  private static Grafik sektoren(final int nummer, final int anzahl) {
    if (nummer == 0) {
      return Op.leereGrafik();
    } else {
      final double winkel = 360 * nummer / anzahl;
      final Grafik sektor = Op.drehe(winkel, Op.kreisSektor(180, 360 / anzahl, Farbe.hsl(winkel, 1, 0.5)));
      return Op.kombiniere(sektor, sektoren(nummer - 1, anzahl));
    }
  }

  private static Grafik sektoren(final int anzahl) {
    return sektoren(anzahl, anzahl);
  }

}
