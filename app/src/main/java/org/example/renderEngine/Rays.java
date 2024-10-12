package org.example.renderEngine;

import org.example.player.Player;

import static org.lwjgl.opengl.GL11.*;

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

      //double scale = (400 - player.getPlayerPositionY()) / (raysFinalY - player.getPlayerPositionY());

      //raysFinalX = player.getPlayerPositionX() + (raysFinalX - player.getPlayerPositionX()) * scale;
  }


  public void hitRays() {
    updateRays();
    // horizontal lines
    // player looking y positive
    if ( player.getPlayerAngle() < 180 && player.getPlayerAngle() > 0 ) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY < raysFinalY; raysMoveY += 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = Math.round(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) / (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        // point into limits
        if (raysMoveX >= Math.min(raysStartX, raysFinalX) && raysMoveX <= Math.max(raysStartX, raysFinalX)) {
          // drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }
    // player looking y """"negative""""" 
    if ( player.getPlayerAngle() > 180 && player.getPlayerAngle() < 360 ) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY > raysFinalY; raysMoveY -= 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = Math.round(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) / (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        // point into limits
        if (raysMoveX >= Math.min(raysStartX, raysFinalX) && raysMoveX <= Math.max(raysStartX, raysFinalX)) {
          // drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }
    // ------------------------------------------
    // vertical lines
    // player looking right (angle near 0)
    if (player.getPlayerAngle() < 90 || player.getPlayerAngle() > 270) {
      for (float raysMoveX = (float) raysStartX; raysMoveX < raysFinalX; raysMoveX += 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = Math.round(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        // point into limits
        if (raysMoveY >= Math.min(raysStartY, raysFinalY) && raysMoveY <= Math.max(raysStartY, raysFinalY)) {
           drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }

    // player looking left (angle between 90 and 270)
    if (player.getPlayerAngle() > 90 && player.getPlayerAngle() < 270) {
      for (float raysMoveX = (float) raysStartX; raysMoveX > raysFinalX; raysMoveX -= 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = Math.round(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        // point into limits
        if (raysMoveY >= Math.min(raysStartY, raysFinalY) && raysMoveY <= Math.max(raysStartY, raysFinalY)) {
           drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }
    // ------------------------------------------------------
  }

  public void drawPointsForRays(float raysMoveXForDraw, float raysMoveYForDraw) {
    glColor3f(0.2f, 1.0f, 1.0f);
    glPointSize(5);
    glBegin(GL_POINTS);
    glVertex2f(raysMoveXForDraw, raysMoveYForDraw);
    glEnd();
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
