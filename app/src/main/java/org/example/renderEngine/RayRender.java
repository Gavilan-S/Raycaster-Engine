package org.example.renderEngine;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.example.mainClass.Main;

public class RayRender {
  public void renderRays(List<Ray> rays) {
    for (Ray ray : rays) {
      glColor3f(1.0f, 1.0f, 1.0f);
      glLineWidth(1);
      glBegin(GL_LINES);
      glVertex2d(ray.getRayStartX(), ray.getRayStartY());
      glVertex2d(ray.getRayEndX(), ray.getRayEndY());
      glEnd();
    }
  }

  public void render3DWalls(List<Ray> rays, double wallHeightConstant) {
    int numRays = rays.size();
    double columnWidth = (double) Main.getGameDisplayWidth() / numRays;

    for (int i = 0; i < numRays; i++) {
      Ray ray = rays.get(i);

      double wallHeight = (wallHeightConstant * Main.getGameDisplayHeight()) / ray.getRayDistance();
      int wallTop = (int) (Main.getGameDisplayHeight() / 2 - wallHeight / 2);
      int wallBottom = (int) (Main.getGameDisplayHeight() / 2 + wallHeight / 2);
      int columnX = (int) (i * columnWidth);


      glColor3f(1.0f, 1.0f, 1.0f);
      glBegin(GL_QUADS);
      glVertex2i(columnX, wallTop);
      glVertex2i((int) (columnX + columnWidth), wallTop);
      glVertex2i((int) (columnX + columnWidth), wallBottom);
      glVertex2i(columnX, wallBottom);
      glEnd();
    }
  }
}
