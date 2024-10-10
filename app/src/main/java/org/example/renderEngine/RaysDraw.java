package org.example.renderEngine;

import org.example.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class RaysDraw {
  private Rays rays = new Rays();
  private Player player = new Player();

  public RaysDraw() {

  }

  public void draw() {
    rays.updateRays();

    for (int ray = 1; ray < 2; ray++) {

      glColor3f(1.0f, 1.0f, 1.0f);
      glLineWidth(1);
      glBegin(GL_LINES);
      glVertex2d(rays.getRaysStartX(), rays.getRaysStartY()); 
      glVertex2d(rays.getRaysFinalX(), rays.getRaysFinalY());
      glEnd();

    }
  }
}
