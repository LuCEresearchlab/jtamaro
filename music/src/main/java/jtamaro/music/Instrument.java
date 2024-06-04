package jtamaro.music;

import java.util.Arrays;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

/**
 * A MIDI Instrument.
 */
public enum Instrument {

  ACOUSTIC_GRAND_PIANO(1),
  BRIGHT_ACOUSTIC_PIANO(2),
  ELECTRIC_GRAND_PIANO(3),
  HONKY_TONK_PIANO(4),
  ELECTRIC_PIANO_1(5),
  ELECTRIC_PIANO_2(6),
  HARPSICHORD(7),
  CLAVI(8),
  CELESTA(9),
  GLOCKENSPIEL(10),
  MUSIC_BOX(11),
  VIBRAPHONE(12),
  MARIMBA(13),
  XYLOPHONE(14),
  TUBULAR_BELLS(15),
  DULCIMER(16),
  DRAWBAR_ORGAN(17),
  PERCUSSIVE_ORGAN(18),
  ROCK_ORGAN(19),
  CHURCH_ORGAN(20),
  REED_ORGAN(21),
  ACCORDION(22),
  HARMONICA(23),
  TANGO_ACCORDION(24),
  ACOUSTIC_GUITAR_NYLON(25),
  ACOUSTIC_GUITAR_STEEL(26),
  ELECTRIC_GUITAR_JAZZ(27),
  ELECTRIC_GUITAR_CLEAN(28),
  ELECTRIC_GUITAR_MUTED(29),
  OVERDRIVEN_GUITAR(30),
  DISTORTION_GUITAR(31),
  GUITAR_HARMONICS(32),
  ACOUSTIC_BASS(33),
  ELECTRIC_BASS_FINGER(34),
  ELECTRIC_BASS_PICK(35),
  FRETLESS_BASS(36),
  SLAP_BASS_1(37),
  SLAP_BASS_2(38),
  SYNTH_BASS_1(39),
  SYNTH_BASS_2(40),
  VIOLIN(41),
  VIOLA(42),
  CELLO(43),
  CONTRABASS(44),
  TREMOLO_STRINGS(45),
  PIZZICATO_STRINGS(46),
  ORCHESTRAL_HARP(47),
  TIMPANI(48),
  STRING_ENSEMBLE_1(49),
  STRING_ENSEMBLE_2(50),
  SYNTH_STRINGS_1(51),
  SYNTH_STRINGS_2(52),
  CHOIR_AAHS(53),
  VOICE_OOHS(54),
  SYNTH_VOICE(55),
  ORCHESTRA_HIT(56),
  TRUMPET(57),
  TROMBONE(58),
  TUBA(59),
  MUTED_TRUMPET(60),
  FRENCH_HORN(61),
  BRASS_SECTION(62),
  SYNTH_BRASS_1(63),
  SYNTH_BRASS_2(64),
  SOPRANO_SAX(65),
  ALTO_SAX(66),
  TENOR_SAX(67),
  BARITONE_SAX(68),
  OBOE(69),
  ENGLISH_HORN(70),
  BASSOON(71),
  CLARINET(72),
  PICCOLO(73),
  FLUTE(74),
  RECORDER(75),
  PAN_FLUTE(76),
  BLOWN_BOTTLE(77),
  SHAKUHACHI(78),
  WHISTLE(79),
  OCARINA(80),
  LEAD_1_SQUARE(81),
  LEAD_2_SAWTOOTH(82),
  LEAD_3_CALLIOPE(83),
  LEAD_4_CHIFF(84),
  LEAD_5_CHARANG(85),
  LEAD_6_VOICE(86),
  LEAD_7_FIFTHS(87),
  LEAD_8_BASS_LEAD(88),
  PAD_1_NEW_AGE(89),
  PAD_2_WARM(90),
  PAD_3_POLYSYNTH(91),
  PAD_4_CHOIR(92),
  PAD_5_BOWED(93),
  PAD_6_METALLIC(94),
  PAD_7_HALO(95),
  PAD_8_SWEEP(96),
  FX_1_RAIN(97),
  FX_2_SOUNDTRACK(98),
  FX_3_CRYSTAL(99),
  FX_4_ATMOSPHERE(100),
  FX_5_BRIGHTNESS(101),
  FX_6_GOBLINS(102),
  FX_7_ECHOES(103),
  FX_8_SCI_FI(104),
  SITAR(105),
  BANJO(106),
  SHAMISEN(107),
  KOTO(108),
  KALIMBA(109),
  BAG_PIPE(110),
  FIDDLE(111),
  SHANAI(112),
  TINKLE_BELL(113),
  AGOGO(114),
  STEEL_DRUMS(115),
  WOODBLOCK(116),
  TAIKO_DRUM(117),
  MELODIC_TOM(118),
  SYNTH_DRUM(119),
  REVERSE_CYMBAL(120),
  GUITAR_FRET_NOISE(121),
  BREATH_NOISE(122),
  SEASHORE(123),
  BIRD_TWEET(124),
  TELEPHONE_RING(125), // use for rotary phone demo?
  HELICOPTER(126),
  APPLAUSE(127),
  GUNSHOT(128);


  private final int pcNumber;

  private final InstrumentFamily family;


  private Instrument(int pcNumber) {
    this.pcNumber = pcNumber;
    this.family = InstrumentFamily.familyForPcNumber(pcNumber);
  }

  /**
   * Get the "program change" number, in the form shown to users (1-based).
   *
   * @return program change number (1-based)
   */
  public int pcNumber() {
    return pcNumber;
  }

  /**
   * Get the "program change" number, in the form used inside the system (0-based).
   *
   * @return program change number (0-based)
   */
  public int internalPcNumber() {
    return pcNumber - 1;
  }

  public InstrumentFamily family() {
    return family;
  }

  public String capitalizedName() {
    return InstrumentFamily.capitalizedName(name());
  }

  @Override
  public String toString() {
    return family().capitalizedName() + ": " + capitalizedName() + " [PC# " + pcNumber + "]";
  }

  /**
   * Get a sequence of all available instruments.
   *
   * @return a sequence of the available instruments.
   */
  public static Sequence<Instrument> instruments() {
    return Sequences.fromStream(Arrays.stream(values()));
  }


  public static void main(String[] args) {
    for (Instrument instrument : values()) {
      System.out.println(instrument);
      Note root = Notes.C4;
      MusicIO.playChords(
          Sequences.of(
              Music.chord(Sequences.of(root)),
              Chord.MAJOR_TRIAD.on(root)
          ),
          80,
          0,
          instrument
      );
    }
  }
}
