package jtamaro.example.music;

import jtamaro.data.Sequence;
import jtamaro.music.Instrument;
import jtamaro.music.TimedChord;

import static jtamaro.data.Sequences.of;
import static jtamaro.music.MusicIO.play;
import static jtamaro.music.Music.chord;
import static jtamaro.music.Music.pause;
import static jtamaro.music.Music.timed;
import static jtamaro.music.Notes.A2;
import static jtamaro.music.Notes.AS2;
import static jtamaro.music.Notes.C3;
import static jtamaro.music.Notes.D2;
import static jtamaro.music.Notes.D3;
import static jtamaro.music.Notes.DS2;
import static jtamaro.music.Notes.F2;
import static jtamaro.music.Notes.G2;

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
