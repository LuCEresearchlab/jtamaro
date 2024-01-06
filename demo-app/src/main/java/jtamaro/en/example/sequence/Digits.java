package jtamaro.en.example.sequence;

import static jtamaro.en.IO.print;
import static jtamaro.en.Sequences.*;

import jtamaro.en.Sequence;


public class Digits {

  public static void main(String[] args) {
    final Sequence<Character> binaryDigits = of('0', '1');
    print(binaryDigits);
    System.out.println();

    final Sequence<Character> octalDigits = rangeClosed('0', '7');
    print(octalDigits);
    System.out.println();

    final Sequence<Character> decimalDigits = rangeClosed('0', '9');
    print(decimalDigits);
    System.out.println();

    final Sequence<Character> hexadecimalDigits = concat(
      rangeClosed('0', '9'),
      rangeClosed('A', 'F')
    );
    print(hexadecimalDigits);
    System.out.println();

    final Sequence<Character> rotaryPhoneDigits = concat(
      rangeClosed('1', '9'),
      of('0')
    );
    print(rotaryPhoneDigits);
    System.out.println();
  }

}
