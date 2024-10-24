package org.example.renderEngine;

import java.util.ArrayList;
import java.util.List;

import org.example.map.MapSectors;
import org.example.player.Player;
import org.example.player.ViewPlayerConfiguration;

public class RayCast {
  private Player player;
  private MapSectors map;
  private ViewPlayerConfiguration viewPlayerConfig;
  
  private float horizontalVerticalSteps = 50.0f;

  public RayCast(Player player, MapSectors map, ViewPlayerConfiguration viewConfig) {
    this.player = player;
    this.map = map;
    this.viewPlayerConfig = viewConfig;
  }

  public List<Ray> castRays() {
    List<Ray> raysList = new ArrayList<>();
    double angleBetweenRays = viewPlayerConfig.getFieldOfView() / viewPlayerConfig.getNumberOfRays();
    double startRayAngle = player.getPlayerAngle() - (viewPlayerConfig.getFieldOfView() / 2);

    for (int rayActual = 0; rayActual < viewPlayerConfig.getNumberOfRays(); rayActual++) {
      double rayAngle = startRayAngle + (rayActual * angleBetweenRays);
      Ray ray = new Ray(player.getPlayerPositionX(), player.getPlayerPositionY(), rayAngle);
      calculateRayCollision(ray);
      raysList.add(ray);
    }
    return raysList;
  }

  private void calculateRayCollision(Ray ray) {
    float nearestDistance = Float.MAX_VALUE;

    nearestDistance = detectDiagonalCollisions(ray, nearestDistance);
    nearestDistance = detectHorizontalCollisions(ray, nearestDistance);
    nearestDistance = detectVerticalCollisions(ray, nearestDistance);
  }

  private float detectHorizontalCollisions(Ray ray, float nearestDistance) {
    double rayAngle = ray.getRayAngle();
    double rayStartX = ray.getRayStartX();
    double rayStartY = ray.getRayStartY();
    double rayEndY = ray.getRayEndY();

    // player looking y up 
    if (rayAngle < 180 && rayAngle > 0) {
      for (float rayMoveY = (float) rayStartY; rayMoveY < rayEndY; rayMoveY += horizontalVerticalSteps) {
        if (rayMoveY % horizontalVerticalSteps != 0) {
          rayMoveY = (float)Math.ceil(rayMoveY/ horizontalVerticalSteps) * horizontalVerticalSteps;
        }
        // x move to draw
        float rayDeltaX = (float)(rayMoveY - rayStartY) /
                          (float)Math.tan(Math.toRadians(rayAngle));
        float rayMoveX = (float)rayStartX + rayDeltaX;

        nearestDistance = checkHorizontalCollisions(rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);

        if (nearestDistance == distanceToPoint(rayMoveX, rayMoveY, rayStartX, rayStartY)) {
          ray.setCollisionPoint(rayMoveX, rayMoveY);
        }
      }
    }
    // player looking y down
    else if (rayAngle > 180 && rayAngle < 360) {
      for (float rayMoveY = (float) rayStartY; rayMoveY > rayEndY; rayMoveY -= horizontalVerticalSteps) {
        if (rayMoveY % horizontalVerticalSteps != 0) {
          rayMoveY = (float)Math.floor(rayMoveY / horizontalVerticalSteps) * horizontalVerticalSteps;
        }

        // x move to draw
        float rayDeltaX = (float)(rayMoveY - rayStartY) / 
                          (float) Math.tan(Math.toRadians(rayAngle));
        float rayMoveX = (float)rayStartX + rayDeltaX;

        nearestDistance = checkHorizontalCollisions(rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);

        if (nearestDistance == distanceToPoint(rayMoveX, rayMoveY, rayStartX, rayStartY)) {
          ray.setCollisionPoint(rayMoveX, rayMoveY);
        }
      }
    }
    return nearestDistance;
  }

  private float checkHorizontalCollisions(float rayMoveForCheckX, float rayMoveForCheckY,
                                          double rayStartForCheckX, double rayStartForCheckY,
                                          float nearestDistanceForCheck) {

    for (int yMoveMap = 1; yMoveMap < map.getSectorZero().length; yMoveMap += 5) {
      if (rayMoveForCheckY == map.getSectorZero()[yMoveMap]*100 && 
      map.getSectorZero()[yMoveMap] == map.getSectorZero()[yMoveMap+2] &&
      rayMoveForCheckX >= Math.min(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100) &&
      rayMoveForCheckX <= Math.max(map.getSectorZero()[yMoveMap-1]*100, map.getSectorZero()[yMoveMap+1]*100)) {

        float rayDistance = distanceToPoint(rayMoveForCheckX, rayMoveForCheckY, rayStartForCheckX, rayStartForCheckY);

        if (rayDistance < nearestDistanceForCheck) {
          nearestDistanceForCheck = rayDistance;
          return nearestDistanceForCheck;
        }
      }
    }
    return nearestDistanceForCheck;
  }

  private float detectVerticalCollisions(Ray ray, float nearestDistance) {
    double rayAngle = ray.getRayAngle();
    double rayStartX = ray.getRayStartX();
    double rayStartY = ray.getRayStartY();
    double rayEndX = ray.getRayEndX();

    // player looking right (angle near 0)
    if (rayAngle < 90 || rayAngle > 270) {
      for (float rayMoveX = (float) rayStartX; rayMoveX < rayEndX; rayMoveX += horizontalVerticalSteps) {
        if (rayMoveX % horizontalVerticalSteps != 0) {
          rayMoveX = (float)Math.ceil(rayMoveX / horizontalVerticalSteps) * horizontalVerticalSteps;
        }
        // y move to draw
        float rayDeltaY = (float) (rayMoveX - rayStartX) * (float) Math.tan(Math.toRadians(rayAngle));
        float rayMoveY = (float) rayStartY + rayDeltaY;

        nearestDistance = checkVerticalCollisions(rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);

        if (nearestDistance == distanceToPoint(rayMoveX, rayMoveY, rayStartX, rayStartY)) {
          ray.setCollisionPoint(rayMoveX, rayMoveY);
        }
      }
    }
    // player looking left (angle between 90 and 270)
    else if (rayAngle > 90 && rayAngle < 270) {
      for (float rayMoveX = (float) rayStartX; rayMoveX > rayEndX; rayMoveX -= horizontalVerticalSteps) {
        if (rayMoveX % 50 != 0) {
          rayMoveX = (float)Math.floor(rayMoveX / horizontalVerticalSteps) * horizontalVerticalSteps;
        }
        // y move to draw
        float rayDeltaY = (float) (rayMoveX - rayStartX) * (float) Math.tan(Math.toRadians(rayAngle));
        float rayMoveY = (float) rayStartY + rayDeltaY;

        nearestDistance = checkVerticalCollisions(rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);

        if (nearestDistance == distanceToPoint(rayMoveX, rayMoveY, rayStartX, rayStartY)) {
          ray.setCollisionPoint(rayMoveX, rayMoveY);
        }
      }
    }
    return nearestDistance;
  }

  public float checkVerticalCollisions(float rayMoveForCheckX, float rayMoveForCheckY,
                                       double rayStartForCheckX, double rayStartForCheckY,
                                       float nearestDistanceForCheck) {

    for (int xMoveMap = 0; xMoveMap < map.getSectorZero().length; xMoveMap += 5) {
      // do not let the ray to get out of bounds
      float tolerance = 2.0f;
      if (rayMoveForCheckX == map.getSectorZero()[xMoveMap]*100 && 
      map.getSectorZero()[xMoveMap] == map.getSectorZero()[xMoveMap+2] &&
      rayMoveForCheckY >= Math.min(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) - tolerance &&
      rayMoveForCheckY <= Math.max(map.getSectorZero()[xMoveMap+1]*100, map.getSectorZero()[xMoveMap+3]*100) + tolerance) {

        float rayDistance = distanceToPoint(rayMoveForCheckX, rayMoveForCheckY, rayStartForCheckX, rayStartForCheckY);

        if (rayDistance < nearestDistanceForCheck) {
          nearestDistanceForCheck = rayDistance;
        }
      }
    }
    return nearestDistanceForCheck;
  }

  private float detectDiagonalCollisions(Ray ray, float nearestDistance) {
    double rayAngle = ray.getRayAngle();
    double rayStartX = ray.getRayStartX();
    double rayStartY = ray.getRayStartY();

    for (int diagonalMoveMap = 0; diagonalMoveMap < map.getSectorZero().length; diagonalMoveMap += 5) {
      float mSlope = (float)((map.getSectorZero()[diagonalMoveMap+3]-map.getSectorZero()[diagonalMoveMap+1])/
    (map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]));

      float bIntercept = (float)(map.getSectorZero()[diagonalMoveMap+1]-mSlope*map.getSectorZero()[diagonalMoveMap])*100;

      float xDelta = (float)(map.getSectorZero()[diagonalMoveMap+2]-map.getSectorZero()[diagonalMoveMap]);

      float denominator = (float)(Math.sin(Math.toRadians(rayAngle)) - mSlope * Math.cos(Math.toRadians(rayAngle)));
      if (Math.abs(denominator) > 1e-6) {
        double tScale = (mSlope * rayStartX - mSlope * xDelta + bIntercept - rayStartY) / denominator;
        if (tScale > 0) {
          float raysMoveX = (float)(rayStartX + tScale * Math.cos(Math.toRadians(rayAngle)));
          float raysMoveY = (float)(rayStartY + tScale * Math.sin(Math.toRadians(rayAngle)));

          // check for horizontal or vertical walls
          if (map.getSectorZero()[diagonalMoveMap]*100 != map.getSectorZero()[diagonalMoveMap+2]*100 ||
          map.getSectorZero()[diagonalMoveMap+1]*100 != map.getSectorZero()[diagonalMoveMap+3]*100) { 
            // to do not let ray get out of the bounds
            float tolerance = 2.0f;
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

              float rayDistance = distanceToPoint(raysMoveX, raysMoveY, rayStartX, rayStartY);

              if (rayDistance < nearestDistance) {
                ray.setCollisionPoint(raysMoveX, raysMoveY);
                nearestDistance = rayDistance;
              }
            }
          }
        }
      }
    }
    return nearestDistance;
  }

  private float distanceToPoint(float x1, float y1, double x2, double y2) {
    return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }
}
