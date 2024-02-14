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

    Grafik d = Op.gleichseitigesDreieck(300, Farbe.MAGENTA);
    Grafik dr = Op.drehe(15, d);
    IO.zeige(dr);

    Grafik ka = Op.ellipse(300, 200, Farbe.BLAU);
    Grafik kb = Op.ellipse(200, 200, Farbe.CYAN);
    Grafik ks = Op.neben(ka, kb);
    IO.zeige(ks);

    Grafik da = Op.gleichseitigesDreieck(300, Farbe.BLAU);
    Grafik db = Op.gleichseitigesDreieck(200, Farbe.CYAN);
    Grafik ds = Op.ueber(da, db);
    IO.zeige(ds);

    Grafik ra = Op.rechteck(200, 200, Farbe.WEISS);
    Grafik rb = Op.rechteck(300, 300, Farbe.ROT);
    Grafik rs = Op.ueberlagere(ra, rb);
    IO.zeige(rs);

    //TODO: expose way to refer to baseline location
    //Grafik ta = Op.fixiere("rechts", "basis", Op.text("Hi", "Arial", 200, Farbe.SCHWARZ));
    //Grafik tb = Op.fixiere("links", "basis", Op.text("oo", "Arial", 200, Farbe.ROT));
    //Grafik ts = Op.kombiniere(ta, tb);
    //IO.zeige(ts);

    IO.zeige(sektoren(60));
  }

  private static Grafik sektoren(final int nummer, final int anzahl) {
    if (nummer == 0) {
      return Op.leereGrafik();
    } else {
      final double winkel = 360.0 * nummer / anzahl;
      final Grafik sektor = Op.drehe(winkel, Op.kreisSektor(180, 360.0 / anzahl, Farbe.hsl(winkel, 1, 0.5)));
      return Op.kombiniere(sektor, sektoren(nummer - 1, anzahl));
    }
  }

  private static Grafik sektoren(final int anzahl) {
    return sektoren(anzahl, anzahl);
  }

}
