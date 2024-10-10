package org.example.map;

import static org.lwjgl.opengl.GL11.*;

import org.example.renderEngine.DisplayMananger;

public class MapSectors {
  private DisplayMananger display = new DisplayMananger();
  
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
          glPointSize(100);
          glBegin(GL_LINES);
          glVertex2d(sectorZero[x0]*100, sectorZero[y0]*100);
          glVertex2d(sectorZero[x0+2]*100, sectorZero[y0+2]*100);
          glEnd();
        }
        if (actualSector == 1) {
          glColor3f(0.0f, 1.0f, 0.0f);
          glLineWidth(2);
          glBegin(GL_LINES);
          glVertex2d(sectorOne[x0]*100, sectorOne[y0]*100);
          glVertex2d(sectorOne[x0+2]*100, sectorOne[y0+2]*100);
          glEnd();
        }
        if (actualSector == 2) {
          glColor3f(0.0f, 0.0f, 1.0f);
          glLineWidth(2);
          glBegin(GL_LINES);
          glColor3f(0.0f, 0.0f, 1.0f);
          glVertex2d(sectorTwo[x0]*100, sectorTwo[y0]*100);
          glVertex2d(sectorTwo[x0+2]*100, sectorTwo[y0+2]*100);
          glEnd();
        }
        x0 += 5;
        y0 += 5;
      }
    }
  }

  public void mapPoints() {
    glColor3f(1, 1, 1);
    glPointSize(2);
    glBegin(GL_POINTS);
    for (int x = 0; x < display.getWidth(); x += 100) { 
      for (int y = 0; y < display.getHeight(); y += 100) { 
        glVertex2i(x, y); 
      }
    }
    glEnd();

    glColor3f(0.7f, 0.7f, 0.7f);
    glPointSize(1);
    glBegin(GL_POINTS);
    for (int x = 0; x < display.getWidth(); x += 100) { 
      for (int y = 50; y < display.getHeight(); y += 100) { 
        glVertex2i(x, y); 
      }
    }
    glEnd();



  }
}
