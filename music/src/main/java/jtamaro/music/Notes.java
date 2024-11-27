package jtamaro.music;


public class Notes {

  public static Note note(int noteNumber) {
    assert noteNumber >= 0 && noteNumber <= 127;
    return new Note(noteNumber);
  }

  public static Note transpose(Note note, int semitones) {
    return note.transpose(semitones);
  }

  // https://en.wikipedia.org/wiki/Scientific_pitch_notation
  public static final Note C0 = note(12);  // lowest note on 108-key piano

  public static final Note CS0 = note(13);

  public static final Note DF0 = CS0;

  public static final Note D0 = note(14);

  public static final Note DS0 = note(15);

  public static final Note EF0 = DS0;

  public static final Note E0 = note(16);

  public static final Note F0 = note(17);

  public static final Note FS0 = note(18);

  public static final Note GF0 = FS0;

  public static final Note G0 = note(19);

  public static final Note GS0 = note(20);

  public static final Note AF0 = GS0;

  public static final Note A0 = note(21); // lowest note on 88-key piano

  public static final Note AS0 = note(22);

  public static final Note BF0 = AS0;

  public static final Note B0 = note(23);

  public static final Note C1 = note(24);

  public static final Note CS1 = note(25);

  public static final Note DF1 = CS1;

  public static final Note D1 = note(26);

  public static final Note DS1 = note(27);

  public static final Note EF1 = DS1;

  public static final Note E1 = note(28);

  public static final Note F1 = note(29);

  public static final Note FS1 = note(30);

  public static final Note GF1 = FS1;

  public static final Note G1 = note(31);

  public static final Note GS1 = note(32);

  public static final Note AF1 = GS1;

  public static final Note A1 = note(33);

  public static final Note AS1 = note(34);

  public static final Note BF1 = AS1;

  public static final Note B1 = note(35);

  public static final Note C2 = note(36);

  public static final Note CS2 = note(37);

  public static final Note DF2 = CS2;

  public static final Note D2 = note(38);

  public static final Note DS2 = note(39);

  public static final Note EF2 = DS2;

  public static final Note E2 = note(40);

  public static final Note F2 = note(41);

  public static final Note FS2 = note(42);

  public static final Note GF2 = FS2;

  public static final Note G2 = note(43);

  public static final Note GS2 = note(44);

  public static final Note AF2 = GS2;

  public static final Note A2 = note(45);

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

  public static final Note C6 = note(84);

  public static final Note CS6 = note(85);

  public static final Note DF6 = CS6;

  public static final Note D6 = note(86);

  public static final Note DS6 = note(87);

  public static final Note EF6 = DS6;

  public static final Note E6 = note(88);

  public static final Note F6 = note(89);

  public static final Note FS6 = note(90);

  public static final Note GF6 = FS6;

  public static final Note G6 = note(91);

  public static final Note GS6 = note(92);

  public static final Note AF6 = GS6;

  public static final Note A6 = note(93);

  public static final Note AS6 = note(94);

  public static final Note BF6 = AS6;

  public static final Note B6 = note(95);

  public static final Note C7 = note(96);

  public static final Note CS7 = note(97);

  public static final Note DF7 = CS7;

  public static final Note D7 = note(98);

  public static final Note DS7 = note(99);

  public static final Note EF7 = DS7;

  public static final Note E7 = note(100);

  public static final Note F7 = note(101);

  public static final Note FS7 = note(102);

  public static final Note GF7 = FS7;

  public static final Note G7 = note(103);

  public static final Note GS7 = note(104);

  public static final Note AF7 = GS7;

  public static final Note A7 = note(105);

  public static final Note AS7 = note(106);

  public static final Note BF7 = AS7;

  public static final Note B7 = note(107);

  public static final Note C8 = note(108); // highest note on 88-key piano

  public static final Note CS8 = note(109);

  public static final Note DF8 = CS8;

  public static final Note D8 = note(110);

  public static final Note DS8 = note(111);

  public static final Note EF8 = DS8;

  public static final Note E8 = note(112);

  public static final Note F8 = note(113);

  public static final Note FS8 = note(114);

  public static final Note GF8 = FS8;

  public static final Note G8 = note(115);

  public static final Note GS8 = note(116);

  public static final Note AF8 = GS8;

  public static final Note A8 = note(117);

  public static final Note AS8 = note(118);

  public static final Note BF8 = AS8;

  public static final Note B8 = note(119); // highest note on 108-key piano

  private Notes() {
  }
}
