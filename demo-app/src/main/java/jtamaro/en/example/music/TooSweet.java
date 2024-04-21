package jtamaro.en.example.music;

import static jtamaro.en.IO.play;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.music.Notes.*;
import static jtamaro.en.Music.*;

import jtamaro.en.Sequence;
import jtamaro.en.music.Instrument;
import jtamaro.en.music.TimedChord;

public class TooSweet {
  
  public static void main(String[] args) {
    Sequence<TimedChord> tooSweet = of(
      pause(1),
      timed(1, chord(of(F2))),
      timed(2, chord(of(G2))),
      timed(2, chord(of(G2))),
      pause(1),
      timed(1, chord(of(G2))),
      timed(2, chord(of(G2))),
      timed(2, chord(of(G2))),
      timed(1, chord(of(A2))),
      timed(1, chord(of(A2))),
      timed(1, chord(of(A2))),
      timed(1, chord(of(A2))),

      pause(1),
      timed(1, chord(of(AS2))),
      timed(2, chord(of(AS2))),
      timed(2, chord(of(AS2))),
      pause(1),
      timed(1, chord(of(AS2))),
      timed(2, chord(of(AS2))),
      timed(2, chord(of(AS2))),
      timed(1, chord(of(C3))),
      timed(1, chord(of(C3))),
      timed(1, chord(of(D3))),
      timed(1, chord(of(D3))),

      pause(1),
      timed(1, chord(of(DS2))),
      timed(2, chord(of(DS2))),
      timed(2, chord(of(DS2))),
      pause(1),
      timed(1, chord(of(DS2))),
      timed(2, chord(of(DS2))),
      timed(2, chord(of(DS2))),
      pause(1),
      timed(1, chord(of(DS2))),
      timed(1, chord(of(DS2))),
      pause(1),

      pause(1),
      timed(1, chord(of(F2))),
      timed(2, chord(of(F2))),
      timed(2, chord(of(F2))),
      pause(1),
      timed(1, chord(of(F2))),
      timed(2, chord(of(D2))),
      timed(2, chord(of(D2))),
      timed(1, chord(of(D2))),
      timed(1, chord(of(D2))),
      pause(1),
      pause(1)

    );
    //play(tooSweet, 117 * 2, 0, Instrument.ELECTRIC_BASS_PICK);
    play(tooSweet, 117 * 2, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

}
