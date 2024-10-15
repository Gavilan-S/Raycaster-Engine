package org.example.renderEngine;

import org.example.map.MapSectors;
import org.example.player.Player;

import static org.lwjgl.opengl.GL11.*;

public class Rays {
  private Player player = new Player(); 
  private MapSectors map = new MapSectors();

  private double raysStartX, raysStartY, raysFinalX, raysFinalY, rayAngle;
  private float raysDistance;
  private float nearestDistance = Float.MAX_VALUE;

  public Rays() {
  }

  public void updateRay() {
    player.movePlayer();
    player.drawPlayer();

    int nOfRays = 500;
    float FOV =  60.0f;

    double angleBetweenRays = FOV / nOfRays;
    double startRayAngle = player.getPlayerAngle() - (FOV / 2);

    double rayLength = 800;

    for (int ray = 0; ray < nOfRays; ray++) {
      rayAngle = (float)(startRayAngle + (ray * angleBetweenRays));

      raysStartX = player.getPlayerPositionX();
      raysStartY = player.getPlayerPositionY();

      raysFinalX = player.getPlayerPositionX() + Math.cos(Math.toRadians(rayAngle)) * rayLength;
      raysFinalY = player.getPlayerPositionY() + Math.sin(Math.toRadians(rayAngle)) * rayLength;

      hitRay(rayAngle);

      glColor3f(1.0f, 1.0f, 1.0f);
      glLineWidth(1);
      glBegin(GL_LINES);
      glVertex2d(raysStartX, raysStartY); 
      glVertex2d(raysFinalX, raysFinalY);
      glEnd();
    }
  }


  private void hitRay(double currentRayAngle) {
    // horizontal lines
    detectHorizontalCollisions(currentRayAngle, nearestDistance);

    // diagonal lines
    detectDiagonalCollisions(currentRayAngle, nearestDistance);

    // vertical lines
    detectVerticalCollisions(currentRayAngle, nearestDistance);
  }

  private void detectHorizontalCollisions(double currentRayAngle, float nearestDistance) {
    // player looking y up 
    if (currentRayAngle < 180 && currentRayAngle > 0 ) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY < raysFinalY; raysMoveY += 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = (float)Math.ceil(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) /
                           (float)Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 &&
          map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
          raysMoveY >= Math.min(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100) &&
          raysMoveY <= Math.max(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100)) {

            raysDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (raysDistance < nearestDistance) {
              raysFinalX = raysMoveX;
              raysFinalY = raysMoveY;
              nearestDistance = raysDistance;
            }


          }
        }
      }
    }
    // player looking y down
    if (currentRayAngle > 180 && currentRayAngle < 360) {
      for ( float raysMoveY = (float) raysStartY; raysMoveY > raysFinalY; raysMoveY -= 50 ) {
        if ( raysMoveY % 50 != 0 ) {
          raysMoveY = (float)Math.floor(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - raysStartY) / (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveX = (float)raysStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 && 
          map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
          raysMoveX >= Math.min(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100) &&
          raysMoveX <= Math.max(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100)) {

            raysDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (raysDistance < nearestDistance) {
              raysFinalX = raysMoveX;
              raysFinalY = raysMoveY;
              nearestDistance = raysDistance;
            }
          }
        }
      }
    }
  }

  private void detectVerticalCollisions(double currentRayAngle, float nearestDistance) {
    // player looking right (angle near 0)
    if (currentRayAngle < 90 || currentRayAngle > 270) {
      for (float raysMoveX = (float) raysStartX; raysMoveX < raysFinalX; raysMoveX += 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = (float)Math.ceil(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
              map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
              raysMoveY >= Math.min(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) &&
              raysMoveY <= Math.max(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100)) {

            raysDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (raysDistance < nearestDistance) {
              raysFinalX = raysMoveX;
              raysFinalY = raysMoveY;
              nearestDistance = raysDistance;
            }
          }
        }
      }
    }

    // player looking left (angle between 90 and 270)
    if (currentRayAngle > 90 && currentRayAngle < 270) {
      for (float raysMoveX = (float) raysStartX; raysMoveX > raysFinalX; raysMoveX -= 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = (float)Math.floor(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - raysStartX) * (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveY = (float) raysStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
          map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
          raysMoveY >= Math.min(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) &&
          raysMoveY <= Math.max(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100)) {

            raysDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (raysDistance < nearestDistance) {
              raysFinalX = raysMoveX;
              raysFinalY = raysMoveY;
              nearestDistance = raysDistance;
            }
          }
        }
      }
    }
  }

  private void detectDiagonalCollisions(double currentRayAngle, float nearestDistance) {
    for (int diagonalMoveMap = 0; diagonalMoveMap < map.getSectorZero().length; diagonalMoveMap += 5) {

      float mSlope = (float)((map.getSectorZero()[diagonalMoveMap+3]-map.getSectorZero()[diagonalMoveMap+1])/
                             (map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]));

      float bIntercept = (float)(map.getSectorZero()[diagonalMoveMap+1]-mSlope*map.getSectorZero()[diagonalMoveMap])*100;

      float xDelta = (float)(map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]);

      float denominator = (float)(Math.sin(Math.toRadians(currentRayAngle)) - mSlope * Math.cos(Math.toRadians(currentRayAngle)));
      if (Math.abs(denominator) > 1e-6) {
        float tScale = (mSlope * player.getPlayerPositionX() - mSlope * xDelta + bIntercept - player.getPlayerPositionY()) / denominator;
        if (tScale > 0) {
          float raysXIntercept = (float)(player.getPlayerPositionX() + tScale * Math.cos(Math.toRadians(currentRayAngle)));
          float raysYIntercept = (float)(player.getPlayerPositionY() + tScale * Math.sin(Math.toRadians(currentRayAngle)));

          // check for horizontal or vertical walls
          if (map.getSectorZero()[diagonalMoveMap]*100 != map.getSectorZero()[diagonalMoveMap+2]*100 ||
          map.getSectorZero()[diagonalMoveMap+1]*100 != map.getSectorZero()[diagonalMoveMap+3]*100) { 
          // to do not let ray get out of the bounds
            float tolerance = 5.0f;
            if (raysXIntercept >= Math.min(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) - tolerance &&
            raysXIntercept <= Math.max(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) + tolerance &&
            raysYIntercept >= Math.min(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) - tolerance &&
            raysYIntercept <= Math.max(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) + tolerance ||
            distanceToPoint(raysXIntercept, raysYIntercept, (float)map.getSectorZero()[diagonalMoveMap]*100,
            (float)map.getSectorZero()[diagonalMoveMap+1]*100) 
            <= tolerance ||
            distanceToPoint(raysXIntercept, raysYIntercept, (float)map.getSectorZero()[diagonalMoveMap+2]*100, 
            (float)map.getSectorZero()[diagonalMoveMap+3]*100) 
            <= tolerance) {

              raysDistance = distanceToPoint(raysXIntercept, raysYIntercept, player.getPlayerPositionX(), player.getPlayerPositionY());

              if (raysDistance < nearestDistance) {
                raysFinalX = raysXIntercept;
                raysFinalY = raysYIntercept;

                nearestDistance = raysDistance;
              }
            }
          }
        }
      }
    }
  }

private float distanceToPoint(float x1, float y1, float x2, float y2) {
    // sqrt ((x2-x1)^2+(y2-y1)^2)
    return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }

  public double getRaysStartX() { return raysStartX; }
  public double getRaysStartY() { return raysStartY; }
  public double getRaysFinalX() { return raysFinalX; }
  public double getRaysFinalY() { return raysFinalY; }
  public double getRaysDistance() { return raysDistance; }
}
