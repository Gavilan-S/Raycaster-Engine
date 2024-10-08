package org.example.renderEngine;

import static org.lwjgl.opengl.GL11.*;

public class Pixels{
  private int[] rgb;
  private DisplayMananger display = new DisplayMananger();

  public Pixels() {
    this.rgb = new int[3];
  }

  public void pixel(int pixelX, int pixelY, int pixelColor) {
    // yellow
    if (pixelColor == 0) {
      rgb[0] = 255;
      rgb[1] = 255;
      rgb[2] = 0;
    }

    // yellow darker
    if (pixelColor == 1) {
      rgb[0] = 160;
      rgb[1] = 160;
      rgb[2] = 0;
    }

    // green 
    if (pixelColor == 2) {
      rgb[0] = 0;
      rgb[1] = 255;
      rgb[2] = 0;
    }

    // green darker 
    if (pixelColor == 3) {
      rgb[0] = 0;
      rgb[1] = 160;
      rgb[2] = 0;
    }

    // yellow
    if (pixelColor == 4) {
      rgb[0] = 255;
      rgb[1] = 255;
      rgb[2] = 0;
    }

    // cyan
    if (pixelColor == 5) {
      rgb[0] = 0;
      rgb[1] = 255;
      rgb[2] = 255;
    }

    // cyan darker
    if (pixelColor == 6) {
      rgb[0] = 0;
      rgb[1] = 160;
      rgb[2] = 160;
    }

    // brown 
    if (pixelColor == 7) {
      rgb[0] = 160;
      rgb[1] = 100;
      rgb[2] = 0;
    }

    // background 
    if (pixelColor == 8) {
      rgb[0] = 0;
      rgb[1] = 60;
      rgb[2] = 130;
    }

    glColor3ub((byte)rgb[0], (byte)rgb[1], (byte)rgb[2]);
    glVertex2i((int)(pixelX*display.getPixelScale()+2),(int)(pixelY*display.getPixelScale())+2);
  }

  public void clearBackground() {
    for (int y = 0; y < display.getScreenGLHeight() ; y++) {
      for (int x = 0; x < display.getScreenGLWidth(); x++) {
        pixel(x,y,8);
      }
    }
  }
}






