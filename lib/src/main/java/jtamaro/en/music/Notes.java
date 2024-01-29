package jtamaro.en.music;


public class Notes {

  public static Note note(int noteNumber) {
    assert noteNumber >= 0 && noteNumber <= 127;
    return new Note(noteNumber);
  }

  public static Note transpose(Note note, int semitones) {
    return note.transpose(semitones);
  }

  // https://en.wikipedia.org/wiki/Scientific_pitch_notation
  public static final Note AS2 = note(46);
  public static final Note BF2 = AS2;
  public static final Note B2 = note(47);

  public static final Note C3 = note(48);
  public static final Note CS3 = note(49);
  public static final Note DF3 = CS3;
  public static final Note D3 = note(50);
  public static final Note DS3 = note(51);
  public static final Note EF3 = DS3;
  public static final Note E3 = note(52);
  public static final Note F3 = note(53);
  public static final Note FS3 = note(54);
  public static final Note GF3 = FS3;
  public static final Note G3 = note(55);
  public static final Note GS3 = note(56);
  public static final Note AF3 = GS3;
  public static final Note A3 = note(57);
  public static final Note AS3 = note(58);
  public static final Note BF3 = AS3;
  public static final Note B3 = note(59);
  
  public static final Note C4 = note(60);
  public static final Note CS4 = note(61);
  public static final Note DF4 = CS4;
  public static final Note D4 = note(62);
  public static final Note DS4 = note(63);
  public static final Note EF4 = DS4;
  public static final Note E4 = note(64);
  public static final Note F4 = note(65);
  public static final Note FS4 = note(66);
  public static final Note GF4 = FS4;
  public static final Note G4 = note(67);
  public static final Note GS4 = note(68);
  public static final Note AF4 = GS4;
  public static final Note A4 = note(69);
  public static final Note AS4 = note(70);
  public static final Note BF4 = AS4;
  public static final Note B4 = note(71);
  
  public static final Note C5 = note(72);
  public static final Note CS5 = note(73);
  public static final Note DF5 = CS5;
  public static final Note D5 = note(74);
  public static final Note DS5 = note(75);
  public static final Note EF5 = DS5;
  public static final Note E5 = note(76);
  public static final Note F5 = note(77);
  public static final Note FS5 = note(78);
  public static final Note GF5 = FS5;
  public static final Note G5 = note(79);
  public static final Note GS5 = note(80);
  public static final Note AF5 = GS5;
  public static final Note A5 = note(81);
  public static final Note AS5 = note(82);
  public static final Note BF5 = AS5;
  public static final Note B5 = note(83);
  
}
