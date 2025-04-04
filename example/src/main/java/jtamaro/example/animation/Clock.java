package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.CENTER;
import static jtamaro.graphic.Points.TOP_CENTER;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.interact;
import static jtamaro.io.IO.showFilmStrip;

public class Clock {

  private static final Color RING_FARBE = rgb(200, 200, 200);

  private static final int STUNDEN_PRO_UMDREHUNG = 12;

  private static final int MINUTEN_PRO_UMDREHUNG = 60;

  private static final int SEKUNDEN_PRO_UMDREHUNG = 60;

  private static Graphic kreis(double durchmesser, Color farbe) {
    return ellipse(durchmesser, durchmesser, farbe);
  }

  private static int winkel(int schritte, int schritte_pro_umdrehung) {
    //return -360 * schritte / schritte_pro_umdrehung; // PyTamaro
    return -360 * schritte / schritte_pro_umdrehung;
  }

  private static Graphic hintergrund(double durchmesser) {
    return overlay(
        kreis(durchmesser, WHITE),
        kreis(durchmesser * 1.01, RING_FARBE)
    );
  }

  private static Graphic zeiger(double lange_laenge, double kurze_laenge, double breite) {
    return compose(
        pin(BOTTOM_CENTER, rectangle(breite, lange_laenge, BLACK)),
        pin(TOP_CENTER, rectangle(breite, kurze_laenge, BLACK))
    );
  }

  private static Graphic minuten_zeiger(double durchmesser) {
    return zeiger(0.46 * durchmesser, 0.12 * durchmesser, (0.052 + 0.036) / 2 * durchmesser);
  }

  private static Graphic stunden_zeiger(double durchmesser) {
    return zeiger(0.32 * durchmesser, 0.12 * durchmesser, (0.064 + 0.052) / 2 * durchmesser);
  }

  private static Graphic sekunden_zeiger(double durchmesser) {
    double breite = 0.014 * durchmesser;
    Graphic langer_teil = rectangle(breite, 0.312 * durchmesser, RED);
    Graphic kurzer_teil = rectangle(breite, 0.165 * durchmesser, RED);
    Graphic scheibe = kreis(0.105 * durchmesser, RED);
    return compose(
        pin(BOTTOM_CENTER,
            compose(
                pin(CENTER, scheibe),
                pin(TOP_CENTER, langer_teil)
            )
        ),
        pin(TOP_CENTER, kurzer_teil)
    );
  }

  private static Graphic ueberlagere_mehrere(Sequence<Graphic> grafiken) {
    Graphic resultat = emptyGraphic();
    for (Graphic grafik : grafiken) {
      resultat = compose(resultat, grafik);
    }
    return resultat;
  }

  private static Graphic striche(double aussen_radius, double breite, double laenge, int zwischen_winkel) {
    double innen_radius = aussen_radius - laenge;
    Graphic strich = rectangle(breite, laenge, BLACK);
    Graphic luecke = rectangle(breite, innen_radius, TRANSPARENT);
    Graphic positionierter_strich = pin(BOTTOM_CENTER,
        compose(
            pin(BOTTOM_CENTER, strich),
            pin(TOP_CENTER, luecke)
        )
    );
    return ueberlagere_mehrere(range(0, 360, zwischen_winkel)
        .map(w -> rotate(w, positionierter_strich)));
  }

  private static Graphic minuten_striche(double durchmesser) {
    return striche(0.485 * durchmesser, 0.014 * durchmesser, 0.035 * durchmesser, 6);
  }

  private static Graphic stunden_striche(double durchmesser) {
    return striche(0.485 * durchmesser, 0.035 * durchmesser, 0.12 * durchmesser, 30);
  }

  private static Graphic uhr(double durchmesser, int stunden, int minuten, int sekunden) {
    return ueberlagere_mehrere(of(
        rotate(winkel(sekunden, SEKUNDEN_PRO_UMDREHUNG), sekunden_zeiger(durchmesser)),
        rotate(winkel(stunden, STUNDEN_PRO_UMDREHUNG), stunden_zeiger(durchmesser)),
        rotate(winkel(minuten, MINUTEN_PRO_UMDREHUNG), minuten_zeiger(durchmesser)),
        minuten_striche(durchmesser),
        stunden_striche(durchmesser),
        hintergrund(durchmesser)
    ));
  }

  public static void main(String[] args) {
    int durchmesser = 300;
    // int stunden = 12;
    // int minuten = 30;
    // int sekunden = 10;
    //Graphic uhr = uhr(durchmesser, stunden, minuten, sekunden);
    //show(uhr);
    Sequence<Graphic> animation = range(2400)
        .map(s -> uhr(durchmesser, (s / 60) / 60, s / 60, s));
    showFilmStrip(animation);
    animate(animation, 1000);

    interact(0)
        .withCanvasSize(durchmesser, durchmesser)
        .withMsBetweenTicks(1000)
        .withTickHandler(time -> time + 1)
        .withRenderer(time -> uhr(durchmesser, (time / 60) / 60, time / 60, time))
        .run();
  }

}
