package org.example.map;

import static org.lwjgl.opengl.GL11.*;

public class Map {
  private int mapUnitsX = 8, mapUnitsY = 9, mapSquare = 64;
  private int[] map =
  {
    1, 1, 1, 1, 1, 1, 1, 1,       
    1, 0, 1, 0, 0, 0, 0, 1,       
    1, 0, 1, 0, 0, 0, 0, 1,       
    1, 0, 1, 0, 0, 0, 0, 1,       
    1, 0, 1, 0, 0, 0, 0, 1,       
    1, 0, 1, 0, 1, 1, 0, 1,       
    1, 0, 0, 0, 0, 0, 0, 1,       
    1, 0, 0, 0, 0, 0, 0, 1,       
    1, 1, 1, 1, 1, 1, 1, 1,       
  };

  public Map() {
    this.mapUnitsX = mapUnitsX;
    this.mapUnitsY = mapUnitsY;
    this.mapSquare = mapSquare;
  }

  public void drawMap2d() {
    int squareX;
    int squareY;

    for (int unitY = 0; unitY < mapUnitsY; unitY++) {
      for (int unitX = 0; unitX < mapUnitsX; unitX++) {
        if(map[unitY * mapUnitsX + unitX] == 1) {
          glColor3f(1,1,1);
        } else {
          glColor3f(0,0,0);
        }

        squareX = unitX * mapSquare;
        squareY = unitY * mapSquare;

        glBegin(GL_QUADS);
        glVertex2i(squareX + 1             , squareY + 1);
        glVertex2i(squareX + 1             , squareY + mapSquare - 1);
        glVertex2i(squareX + mapSquare - 1 , squareY + mapSquare - 1);
        glVertex2i(squareX + mapSquare - 1 , squareY + 1);
        glEnd();

      }

    }

  }



}
