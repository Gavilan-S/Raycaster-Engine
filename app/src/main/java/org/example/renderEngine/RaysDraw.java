package org.example.renderEngine;

import org.example.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class RaysDraw {
  private Player player = new Player();
  private Rays rays = new Rays();

  public RaysDraw() {

  }

  public void drawRays() {
    rays.updateRay();

  }
}
