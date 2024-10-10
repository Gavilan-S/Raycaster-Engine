package org.example.renderEngine;

import org.example.player.Player;

public class Rays {
  private Player player = new Player();

  private double raysStartX, raysStartY, raysFinalX, raysFinalY, raysAngle, raysDistance;

  public Rays() {
  }

  public void updateRays() {
    raysDistance = Math.sqrt(raysFinalX*raysFinalX+raysFinalY*raysFinalY);
    player.movePlayer();
    raysStartX = player.getPlayerPositionX();
    raysStartY = player.getPlayerPositionY();

    double rayLength = 200;
    raysFinalX = player.getPlayerPositionX() + Math.cos(Math.toRadians(player.getPlayerAngle())) * rayLength;
    raysFinalY = player.getPlayerPositionY() + Math.sin(Math.toRadians(player.getPlayerAngle())) * rayLength;

    if (raysFinalY >= 400 && raysFinalX > 200 && raysFinalX < 300) {
      double scale = (400 - player.getPlayerPositionY()) / (raysFinalY - player.getPlayerPositionY());

      raysFinalX = player.getPlayerPositionX() + (raysFinalX - player.getPlayerPositionX()) * scale;
      raysFinalY = 400; 
    }

  }


  public Player getPlayer() {
    return player;
  }
  public double getRaysStartX() {
    return raysStartX;
  }
  public double getRaysStartY() {
    return raysStartY;
  }
  public double getRaysFinalX() {
    return raysFinalX;
  }
  public double getRaysFinalY() {
    return raysFinalY;
  }
  public double getRaysDistance() {
    return raysDistance;
  }
}
