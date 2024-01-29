package jtamaro.en.music;

import static jtamaro.en.IO.*;
import static jtamaro.en.Music.*;
import static jtamaro.en.Sequences.*;

import java.util.Arrays;
import jtamaro.en.Sequence;


/**
 * In MIDI, percussion instruments corresponds to notes.
 * If those note numbers are played on channel 10,
 * then the sounds produced is that of the percussion instrument.
 * 
 * This quirky design makes it somewhat difficult to cleanly model things.
 * TODO: Improve the design of Note/Percussion/AbsoluteChord/TimedChord.
 */
public enum Percussion {

  ACOUSTIC_BASS_DRUM(35),
  BASS_DRUM_1(36),
  SIDE_STICK(37),
  ACOUSTIC_SNARE(38),
  HAND_CLAP(39),
  ELECTRIC_SNARE(40),
  LOW_FLOOR_TOM(41),
  CLOSED_HI_HAT(42),
  HIGH_FLOOR_TOM(43),
  PEDAL_HI_HAT(44),
  LOW_TOM(45),
  OPEN_HI_HAT(46),
  LOW_MID_TOM(47),
  HI_MID_TOM(48),
  CRASH_CYMBAL_1(49),
  HIGH_TOM(50),
  RIDE_CYMBAL_1(51),
  CHINESE_CYMBAL(52),
  RIDE_BELL(53),
  TAMBOURINE(54),
  SPLASH_CYMBAL(55),
  COWBELL(56),
  CRASH_CYMBAL_2(57),
  VIBRASLAP(58),
  RIDE_CYMBAL_2(59),
  HI_BONGO(60),
  LOW_BONGO(61),
  MUTE_HI_CONGA(62),
  OPEN_HI_CONGA(63),
  LOW_CONGA(64),
  HIGH_TIMBALE(65),
  LOW_TIMBALE(66),
  HIGH_AGOGO(67),
  LOW_AGOGO(68),
  CABASA(69),
  MARACAS(70),
  SHORT_WHISTLE(71),
  LONG_WHISTLE(72),
  SHORT_GUIRO(73),
  LONG_GUIRO(74),
  CLAVES(75),
  HI_WOOD_BLOCK(76),
  LOW_WOOD_BLOCK(77),
  MUTE_CUICA(78),
  OPEN_CUICA(79),
  MUTE_TRIANGLE(80),
  OPEN_TRIANGLE(81);


  private final int midiNoteNumber;

  private Percussion(int midiNoteNumber) {
    this.midiNoteNumber = midiNoteNumber;
  }

  public Note asNote() {
    return new Note(midiNoteNumber);
  }

  public String capitalizedName() {
    return InstrumentFamily.capitalizedName(name());
  }

  @Override
  public String toString() {
    return capitalizedName() + " [Key# " + midiNoteNumber + "]";
  }

  /**
   * Get a sequence of all available percussion instruments.
   * 
   * @return a sequence of the available percussion instruments.
   */
  public static Sequence<Percussion> percussions() {
    return fromStream(Arrays.stream(values()));
  }

  public static void main(String[] args) {
    // TODO: It seems that Java's default sound bank (Gervill?)
    //       only includes a subset of the General MIDI percussion sounds.
    //       https://www.midi.org/specifications-old/item/gm-level-1-sound-set

    //System.out.println("Play through all in one go:");
    //playDrumNotes(map(pe->pe.asNote(), percussions()), 80);

    System.out.println("Play a piece:");
    playDrumChords(
      of(
        chord(of(ACOUSTIC_BASS_DRUM.asNote())),
        chord(of(ACOUSTIC_SNARE.asNote())),
        chord(of(ACOUSTIC_BASS_DRUM.asNote())),
        chord(of(ACOUSTIC_SNARE.asNote())),
        chord(of(CLOSED_HI_HAT.asNote())),
        chord(of(OPEN_HI_HAT.asNote())),
        chord(of(LOW_FLOOR_TOM.asNote())),
        chord(of(HIGH_FLOOR_TOM.asNote())),
        chord(of(LOW_TOM.asNote())),
        chord(of(LOW_MID_TOM.asNote())),
        chord(of(HI_MID_TOM.asNote())),
        chord(of(HIGH_TOM.asNote())),
        chord(of(CRASH_CYMBAL_1.asNote())),
        chord(of(LOW_FLOOR_TOM.asNote(), HIGH_TOM.asNote()))
      ),
      80
    );

    System.out.println("Play each percussion instrument individually:");
    for (Percussion percussion : values()) {
      System.out.println(percussion);
      playDrumNotes(
        of(percussion.asNote()),
        80
      );
    }
  }

}
