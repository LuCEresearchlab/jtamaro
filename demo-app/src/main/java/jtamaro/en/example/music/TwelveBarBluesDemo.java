package jtamaro.en.example.music;

import static jtamaro.en.Music.*;
import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.example.Toolbelt.concats;
import static jtamaro.en.example.Toolbelt.times;
import static jtamaro.en.IO.*;
import static jtamaro.en.music.Notes.*;

import jtamaro.en.Sequence;
import jtamaro.en.music.AbsoluteChord;
import jtamaro.en.music.Note;


public class TwelveBarBluesDemo {
  
  public static void main(String[] args) {
    playAsChords();
    playMelody();
  }

  public static void playMelody() {
    Sequence<Note> twelveBarBlues_C = of(
      C3, E3, G3, A3, BF3, A3, G3, E3
    );
    Sequence<Note> twelveBarBlues_F = of(
      F3, A3, C4, D4, EF4, D4, C4, A3
    );
    Sequence<Note> twelveBarBlues_G = of(
      G3, B3, D4, E4, F4, E4, D4, B3
    );
    Sequence<Note> twelveBarBlues = concats(of(
      times(4, twelveBarBlues_C),
      times(2, twelveBarBlues_F),
      times(2, twelveBarBlues_C),
      twelveBarBlues_G,
      twelveBarBlues_F,
      times(2, twelveBarBlues_C)
    ));
    
    playChords(map(n->chord(of(n)), twelveBarBlues), 800);
    playChords(map(n->chord(of(transpose(n, 12))), twelveBarBlues), 800);
    playChords(map(n->chord(of(transpose(n, 24))), twelveBarBlues), 800);
  }

  public static void playAsChords() {
    // https://en.wikipedia.org/wiki/Chord_progression
    AbsoluteChord C_ = chord(of(C4, E4, G4));
    AbsoluteChord F_ = chord(of(F4, A4, C5));
    AbsoluteChord G_ = chord(of(G4, B4, D5));
    Sequence<AbsoluteChord> twelveBarBluesChords = of(
      C_, C_, C_, C_, F_, F_, C_, C_, G_, F_, C_, C_
    );
    playChords(twelveBarBluesChords, 100);
  }

}
