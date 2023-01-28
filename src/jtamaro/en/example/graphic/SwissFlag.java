package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;


public class SwissFlag {

  public static void main(String[] args) {
    Graphic h = rectangle(200, 60, WHITE);
    Graphic v = rectangle(60, 200, WHITE);
    Graphic cross = overlay(h, v);
    Graphic square = rectangle(320, 320, RED);
    Graphic flag = overlay(cross, square);
    show(flag);
  }
  
}
