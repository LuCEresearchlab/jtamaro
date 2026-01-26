package jtamaro.example.music;

import jtamaro.data.Sequence;
import jtamaro.music.AbsoluteChord;
import jtamaro.music.Note;

import static jtamaro.data.Sequences.of;
import static jtamaro.example.Toolbelt.concats;
import static jtamaro.example.Toolbelt.times;
import static jtamaro.music.Music.chord;
import static jtamaro.music.MusicIO.playChords;
import static jtamaro.music.Notes.A3;
import static jtamaro.music.Notes.A4;
import static jtamaro.music.Notes.B3;
import static jtamaro.music.Notes.B4;
import static jtamaro.music.Notes.BF3;
import static jtamaro.music.Notes.C3;
import static jtamaro.music.Notes.C4;
import static jtamaro.music.Notes.C5;
import static jtamaro.music.Notes.D4;
import static jtamaro.music.Notes.D5;
import static jtamaro.music.Notes.E3;
import static jtamaro.music.Notes.E4;
import static jtamaro.music.Notes.EF4;
import static jtamaro.music.Notes.F3;
import static jtamaro.music.Notes.F4;
import static jtamaro.music.Notes.G3;
import static jtamaro.music.Notes.G4;
import static jtamaro.music.Notes.transpose;

public final class TwelveBarBluesDemo {

  private TwelveBarBluesDemo() {}

  public static void main() {
    playAsChords();
    playMelody();
  }

  private static void playMelody() {
    final Sequence<Note> twelveBarBlues_C = of(
        C3, E3, G3, A3, BF3, A3, G3, E3
    );
    final Sequence<Note> twelveBarBlues_F = of(
        F3, A3, C4, D4, EF4, D4, C4, A3
    );
    final Sequence<Note> twelveBarBlues_G = of(
        G3, B3, D4, E4, F4, E4, D4, B3
    );
    final Sequence<Note> twelveBarBlues = concats(of(
        times(4, twelveBarBlues_C),
        times(2, twelveBarBlues_F),
        times(2, twelveBarBlues_C),
        twelveBarBlues_G,
        twelveBarBlues_F,
        times(2, twelveBarBlues_C)
    ));

    playChords(twelveBarBlues.map(n -> chord(of(n))), 800);
    playChords(twelveBarBlues.map(n -> chord(of(transpose(n, 12)))), 800);
    playChords(twelveBarBlues.map(n -> chord(of(transpose(n, 24)))), 800);
  }

  private static void playAsChords() {
    // https://en.wikipedia.org/wiki/Chord_progression
    final AbsoluteChord C_ = chord(of(C4, E4, G4));
    final AbsoluteChord F_ = chord(of(F4, A4, C5));
    final AbsoluteChord G_ = chord(of(G4, B4, D5));
    final Sequence<AbsoluteChord> twelveBarBluesChords = of(
        C_, C_, C_, C_, F_, F_, C_, C_, G_, F_, C_, C_
    );
    playChords(twelveBarBluesChords, 100);
  }
}
