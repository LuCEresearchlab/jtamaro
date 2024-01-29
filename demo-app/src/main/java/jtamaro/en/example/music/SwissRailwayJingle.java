package jtamaro.en.example.music;

import static jtamaro.en.Music.*;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.example.Toolbelt.concats;
import static jtamaro.en.IO.*;
import static jtamaro.en.music.Notes.*;

import jtamaro.en.Sequence;
import jtamaro.en.music.Instrument;
import jtamaro.en.music.TimedChord;

public class SwissRailwayJingle {

  public static void main(String[] args) {
    // https://onlinesequencer.net/3133236
    Sequence<TimedChord> sbbChords = of(
      timed(1, chord(of(EF4))),
      timed(1, chord(of(BF4))),
      timed(2, chord(of(BF4, EF4, GF4)))
      //timed(2, chord(of(EF4, GF4)))
    );
    // https://onlinesequencer.net/3133249
    Sequence<TimedChord> cffChords = of(
      timed(1, chord(of(C5))),
      timed(1, chord(of(F4))),
      timed(2, chord(of(F4, CS4)))
    );
    // https://onlinesequencer.net/3133243
    Sequence<TimedChord> ffsChords = of(
      timed(1, chord(of(F4))),
      timed(1, chord(of(F4))),
      timed(2, chord(of(EF4, F4)))
    );
    Sequence<TimedChord> sbb_cff_ffsChords = concats(of(
      sbbChords,
      of(pause(1)),
      cffChords,
      of(pause(1)),
      ffsChords
    ));
    //play(sbb_cff_ffsChords, 240, 0, Instrument.VIBRAPHONE);
    //play(sbbChords, 240, 0, Instrument.VIBRAPHONE);
    //play(cffChords, 240, 0, Instrument.VIBRAPHONE);
    play(ffsChords, 240, 0, Instrument.VIBRAPHONE);
    /*
    Sequence<Note> sbb = of(EF4, BF4, BF4);
    playNotes(sbb, 240);
    Sequence<Note> cff = of(C5, F4, F4);
    playNotes(cff, 240);
    Sequence<Note> ffs = of(F4, F4, EF4);
    playNotes(ffs, 240);
    */
  }
  
}
