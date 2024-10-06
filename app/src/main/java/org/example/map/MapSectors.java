package org.example.map;

import static org.lwjgl.opengl.GL11.*;

public class MapSectors {
  
  // sectors: <id> <index> <nWalls> <floor> <ceiling>
  private double[] sectors = {
  1, 0, 8, 0.0, 5.0,
  2, 8, 3, 1.0, 2.0,
  3, 11, 4, 0.2, 6.0
  };

  // sector: <x0> <y0> <x1> <y1> <portal¿?>
  private double[] sectorZero = {
  2, 1, 3, 1, 0,
  3, 1, 4, 2, 0,
  4, 2, 4, 3, 3,
  4, 3, 3, 4, 0, 
  3, 4, 2, 4, 0,
  2, 4, 1, 3, 2,
  1, 3, 1, 2, 0,
  1, 2, 2, 1, 0
  };

  private double[] sectorOne = {
  1, 3, 2, 4, 1,
  2, 4, 0.5, 4.5, 0,
  0.5, 4.5, 1, 3, 0
  };

  private double[] sectorTwo = {
  4, 2, 8, 2, 0,
  8, 2, 8, 3, 0,
  8, 3, 4, 3, 0,
  4, 3, 4, 2, 1
  };

  public void drawMapSectors() {
    int nWalls = -3;
    for (int actualSector = 0; actualSector < 3; actualSector++) {
      nWalls += 5;
      int x0 = 0;
      int y0 = 1;
      for (int actualWall = 0; actualWall < sectors[nWalls]; actualWall++) { 
        if (actualSector == 0) {
          glColor3f(1.0f, 0.0f, 0.0f);
          glLineWidth(2);
          glBegin(GL_LINES);
          glVertex2d(sectorZero[x0]*200, sectorZero[y0]*200);
          glVertex2d(sectorZero[x0+2]*200, sectorZero[y0+2]*200);
          glEnd();
        }
        if (actualSector == 1) {
          glColor3f(0.0f, 1.0f, 0.0f);
          glLineWidth(2);
          glBegin(GL_LINES);
          glVertex2d(sectorOne[x0]*200, sectorOne[y0]*200);
          glVertex2d(sectorOne[x0+2]*200, sectorOne[y0+2]*200);
          glEnd();
        }
        if (actualSector == 2) {
          glColor3f(0.0f, 0.0f, 1.0f);
          glLineWidth(2);
          glBegin(GL_LINES);
          glColor3f(0.0f, 0.0f, 1.0f);
          glVertex2d(sectorTwo[x0]*200, sectorTwo[y0]*200);
          glVertex2d(sectorTwo[x0+2]*200, sectorTwo[y0+2]*200);
          glEnd();
        }
        x0 += 5;
        y0 += 5;
      }
    }
  }

  public void lineline() {
    glColor3f(0, 1, 0);
    glLineWidth(20);
    glBegin(GL_LINES);
    glVertex2f(0.0f, 0.0f);  // Punto inicial (X1, Y1)
    glVertex2f(25.5f, 53.5f);
    glEnd();
  }




}
