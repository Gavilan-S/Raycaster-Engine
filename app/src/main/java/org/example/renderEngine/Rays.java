package org.example.renderEngine;

import org.example.map.MapSectors;
import org.example.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class Rays {
  private Player player = new Player();
  private MapSectors map = new MapSectors();

  private double raysStartX, raysStartY, raysFinalX, raysFinalY, raysAngle, raysDistance;

  public Rays() {
  }

  public void updateRays() {
    raysDistance = Math.sqrt(raysFinalX*raysFinalX+raysFinalY*raysFinalY);

    player.movePlayer();
    raysStartX = player.getPlayerPositionX();
    raysStartY = player.getPlayerPositionY();

    double rayLength = 800;
    raysFinalX = player.getPlayerPositionX() + Math.cos(Math.toRadians(player.getPlayerAngle())) * rayLength;
    raysFinalY = player.getPlayerPositionY() + Math.sin(Math.toRadians(player.getPlayerAngle())) * rayLength;

      //double scale = (400 - player.getPlayerPositionY()) / (raysFinalY - player.getPlayerPositionY());

      //raysFinalX = player.getPlayerPositionX() + (raysFinalX - player.getPlayerPositionX()) * scale;
  }


  public void hitRays() {
    updateRays();
    // horizontal lines
    // player looking y up 
    if ( player.getPlayerAngle() < 180 && player.getPlayerAngle() > 0 ) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY < raysFinalY; raysMoveY += 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = (float)Math.ceil(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) /
                           (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 &&
              map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
              raysMoveX >= map.getSectorZero()[yMoveMap-1]*100 && 
              raysMoveX <= map.getSectorZero()[yMoveMap+1]*100) {
            raysFinalX = raysMoveX;
            raysFinalY = raysMoveY;
          }
        }
        // point into limits
        if (raysMoveX >= Math.min(raysStartX, raysFinalX) && raysMoveX <= Math.max(raysStartX, raysFinalX)) {
           // drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }
    // player looking y down
    if ( player.getPlayerAngle() > 180 && player.getPlayerAngle() < 360 ) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY > raysFinalY; raysMoveY -= 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = (float)Math.floor(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) / (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 && 
              map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
              raysMoveX >= map.getSectorZero()[yMoveMap-1]*100 && 
              raysMoveX <= map.getSectorZero()[yMoveMap+1]*100) {
            raysFinalX = raysMoveX;
            raysFinalY = raysMoveY;
          }
        }

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
          raysMoveX = (float)Math.ceil(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
              map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
              raysMoveY >= map.getSectorZero()[xMoveMap+1]*100 && 
              raysMoveY <= map.getSectorZero()[xMoveMap+3]*100) {
            raysFinalX = raysMoveX;
            raysFinalY = raysMoveY;
          }
        }



        // point into limits
        if (raysMoveY >= Math.min(raysStartY, raysFinalY) && raysMoveY <= Math.max(raysStartY, raysFinalY)) {
           // drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }

    // player looking left (angle between 90 and 270)
    if (player.getPlayerAngle() > 90 && player.getPlayerAngle() < 270) {
      for (float raysMoveX = (float) raysStartX; raysMoveX > raysFinalX; raysMoveX -= 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = (float)Math.floor(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(player.getPlayerAngle()));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
              map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
              raysMoveY >= map.getSectorZero()[xMoveMap+1]*100 && 
              raysMoveY <= map.getSectorZero()[xMoveMap+3]*100) {
            raysFinalX = raysMoveX;
            raysFinalY = raysMoveY;
          }
        }

        // point into limits
        if (raysMoveY >= Math.min(raysStartY, raysFinalY) && raysMoveY <= Math.max(raysStartY, raysFinalY)) {
           // drawPointsForRays(raysMoveX, raysMoveY);
        }
      }
    }
    // ------------------------------------------------------
    // diagonal lines

    float nearestDistance = Float.MAX_VALUE;

    for (int diagonalMoveMap = 0; diagonalMoveMap < map.getSectorZero().length; diagonalMoveMap += 5) {
      float mSlope = (float)((map.getSectorZero()[diagonalMoveMap+3]-map.getSectorZero()[diagonalMoveMap+1])/
                             (map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]));

      float bIntercept = (float)(map.getSectorZero()[diagonalMoveMap+1]-mSlope*map.getSectorZero()[diagonalMoveMap])*100;

      float xDelta = (float)(map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]);

      float denominator = (float)(Math.sin(Math.toRadians(player.getPlayerAngle())) - mSlope * Math.cos(Math.toRadians(player.getPlayerAngle())));
      if (denominator != 0) {

        float tScale = (mSlope * player.getPlayerPositionX() - mSlope * xDelta + bIntercept - player.getPlayerPositionY()) / denominator;
        if (tScale > 0) {
          float raysXIntercept = (float)(player.getPlayerPositionX() + tScale * Math.cos(Math.toRadians(player.getPlayerAngle())));
          float raysYIntercept = (float)(player.getPlayerPositionY() + tScale * Math.sin(Math.toRadians(player.getPlayerAngle())));

          // to do not let ray get out of the bounds
          float tolerance = 1.0f;
          if (raysXIntercept >= Math.min(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) - tolerance &&
          raysXIntercept <= Math.max(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) + tolerance &&
          raysYIntercept >= Math.min(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) - tolerance &&
          raysYIntercept <= Math.max(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) + tolerance) {

            float distanceToIntersection = (float)Math.sqrt(Math.pow(raysXIntercept - player.getPlayerPositionX(), 2) + 
              Math.pow(raysYIntercept - player.getPlayerPositionY(), 2));

            if (distanceToIntersection < nearestDistance) {
              raysFinalX = raysXIntercept;
              raysFinalY = raysYIntercept;

              nearestDistance = distanceToIntersection;
            }
          }
        }
      }
    }
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
