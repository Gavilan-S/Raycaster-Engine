package org.example.renderEngine;

import org.example.map.MapSectors;
import org.example.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Rays {
  private Player player; 
  private MapSectors map;

  private double rayStartX, rayStartY, rayEndX, rayEndY, rayAngle;
  private float rayDistance;

  private List<Float> raysDistanceList = new ArrayList<>();

  public Rays() {
    this.player = new Player();
    this.map = new MapSectors();

    this.rayStartX = player.getPlayerPositionX();
    this.rayStartY = player.getPlayerPositionY();
    this.rayEndX = rayEndX;
    this.rayEndY = rayEndY;
    this.rayAngle = rayAngle;
  }

  public void updateRay() {
    player.movePlayer();
    player.drawPlayer();

    int nOfRays = 100;
    float FOV =  60.0f;

    double angleBetweenRays = FOV / nOfRays;
    double startRayAngle = player.getPlayerAngle() - (FOV / 2);

    double rayLength = 800;

    // clearList
    raysDistanceList.clear();
    for (int ray = 0; ray < nOfRays; ray++) {
      rayAngle = (float)(startRayAngle + (ray * angleBetweenRays));

      rayStartX = player.getPlayerPositionX();
      rayStartY = player.getPlayerPositionY();

      rayEndX = player.getPlayerPositionX() + Math.cos(Math.toRadians(rayAngle)) * rayLength;
      rayEndY = player.getPlayerPositionY() + Math.sin(Math.toRadians(rayAngle)) * rayLength;

      hitRay(rayAngle); 

      raysDistanceList.add(rayDistance);

      glColor3f(1.0f, 1.0f, 1.0f);
      glLineWidth(1);
      glBegin(GL_LINES);
      glVertex2d(rayStartX, rayStartY); 
      glVertex2d(rayEndX, rayEndY);
      glEnd();
      
    }
  }


  private void hitRay(double currentRayAngle) {
    float nearestDistance = Float.MAX_VALUE;
    // diagonal lines
    detectDiagonalCollisions(currentRayAngle, nearestDistance);

    // horizontal lines
    detectHorizontalCollisions(currentRayAngle, nearestDistance);

    // vertical lines
    detectVerticalCollisions(currentRayAngle, nearestDistance);
  }

  private void detectHorizontalCollisions(double currentRayAngle, float nearestDistance) {
    // player looking y up 
    if (currentRayAngle < 180 && currentRayAngle > 0 ) {
      for (float raysMoveY = (float) rayStartY; raysMoveY < rayEndY; raysMoveY += 50) {
        if (raysMoveY % 50 != 0) {
          raysMoveY = (float)Math.ceil(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - rayStartY) /
                           (float)Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveX = (float)rayStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 &&
          map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
          raysMoveY >= Math.min(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100) &&
          raysMoveY <= Math.max(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100)) {

            rayDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (rayDistance < nearestDistance) {
              rayEndX = raysMoveX;
              rayEndY = raysMoveY;
              nearestDistance = rayDistance;
            }
          }
        }
      }
    }
    // player looking y down
    if (currentRayAngle > 180 && currentRayAngle < 360) {
      for (float raysMoveY = (float) rayStartY; raysMoveY > rayEndY; raysMoveY -= 50) {
        if (raysMoveY % 50 != 0) {
          raysMoveY = (float)Math.floor(raysMoveY/50.0f)*50;
        }

        // x move to draw
        float raysDeltaX = (float)(raysMoveY - rayStartY) / (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveX = (float)rayStartX + raysDeltaX;

        for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
          if (raysMoveY == map.getSectorZero()[yMoveMap]*100 && 
          map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
          raysMoveX >= Math.min(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100) &&
          raysMoveX <= Math.max(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100)) {

            rayDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (rayDistance < nearestDistance) {
              rayEndX = raysMoveX;
              rayEndY = raysMoveY;
              nearestDistance = rayDistance;
            }
          }
        }
      }
    }
  }

  private void detectVerticalCollisions(double currentRayAngle, float nearestDistance) {
    // player looking right (angle near 0)
    if (currentRayAngle < 90 || currentRayAngle > 270) {
      for (float raysMoveX = (float) rayStartX; raysMoveX < rayEndX; raysMoveX += 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = (float)Math.ceil(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - rayStartX) * (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveY = (float) rayStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
              map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
              raysMoveY >= Math.min(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) &&
              raysMoveY <= Math.max(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100)) {

            rayDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (rayDistance < nearestDistance) {
              rayEndX = raysMoveX;
              rayEndY = raysMoveY;
              nearestDistance = rayDistance;
            }
          }
        }
      }
    }

    // player looking left (angle between 90 and 270)
    if (currentRayAngle > 90 && currentRayAngle < 270) {
      for (float raysMoveX = (float) rayStartX; raysMoveX > rayEndX; raysMoveX -= 50) {
        if (raysMoveX % 50 != 0) {
          raysMoveX = (float)Math.floor(raysMoveX / 50.0f) * 50;
        }

        // y move to draw
        float raysDeltaY = (float) (raysMoveX - rayStartX) * (float) Math.tan(Math.toRadians(currentRayAngle));
        float raysMoveY = (float) rayStartY + raysDeltaY;

        for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
          if (raysMoveX == map.getSectorZero()[xMoveMap]*100 && 
          map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
          raysMoveY >= Math.min(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) &&
          raysMoveY <= Math.max(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100)) {

            rayDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

            if (rayDistance < nearestDistance) {
              rayEndX = raysMoveX;
              rayEndY = raysMoveY;
              nearestDistance = rayDistance;
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
          float raysMoveX = (float)(player.getPlayerPositionX() + tScale * Math.cos(Math.toRadians(currentRayAngle)));
          float raysMoveY = (float)(player.getPlayerPositionY() + tScale * Math.sin(Math.toRadians(currentRayAngle)));

          // check for horizontal or vertical walls
          if (map.getSectorZero()[diagonalMoveMap]*100 != map.getSectorZero()[diagonalMoveMap+2]*100 ||
          map.getSectorZero()[diagonalMoveMap+1]*100 != map.getSectorZero()[diagonalMoveMap+3]*100) { 
          // to do not let ray get out of the bounds
            float tolerance = 5.0f;
            if (raysMoveX >= Math.min(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) - tolerance &&
            raysMoveX <= Math.max(map.getSectorZero()[diagonalMoveMap]*100, map.getSectorZero()[diagonalMoveMap+2]*100) + tolerance &&
            raysMoveY >= Math.min(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) - tolerance &&
            raysMoveY <= Math.max(map.getSectorZero()[diagonalMoveMap+1]*100, map.getSectorZero()[diagonalMoveMap+3]*100) + tolerance ||
            distanceToPoint(raysMoveX, raysMoveY, (float)map.getSectorZero()[diagonalMoveMap]*100,
            (float)map.getSectorZero()[diagonalMoveMap+1]*100) 
            <= tolerance ||
            distanceToPoint(raysMoveX, raysMoveY, (float)map.getSectorZero()[diagonalMoveMap+2]*100, 
            (float)map.getSectorZero()[diagonalMoveMap+3]*100) 
            <= tolerance) {

              rayDistance = distanceToPoint(raysMoveX, raysMoveY, player.getPlayerPositionX(), player.getPlayerPositionY());

              if (rayDistance < nearestDistance) {
                rayEndX = raysMoveX;
                rayEndY = raysMoveY;

                nearestDistance = rayDistance;
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

  public double getRayStartX() { return rayStartX; }
  public double getRayStartY() { return rayStartY; }
  public double getRayFinalX() { return rayEndX; }
  public double getRayFinalY() { return rayEndY; }
  public double getRayDistance() { return rayDistance; }

  public List<Float> getRaysDistanceList() { return raysDistanceList; }
}
