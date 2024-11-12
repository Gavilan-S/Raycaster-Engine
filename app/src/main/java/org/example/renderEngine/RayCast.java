package org.example.renderEngine;

import java.util.ArrayList;
import java.util.List;

import org.example.map.Map;
import org.example.map.Sector;
import org.example.map.Wall;
import org.example.player.Player;
import org.example.player.ViewPlayerConfiguration;

public class RayCast {
  private Player player;
	private Map map;
  private ViewPlayerConfiguration viewPlayerConfig;
  
  private float scale = 100;
  private float horizontalVerticalSteps = 50.0f;

  public RayCast(Player player, Map map, ViewPlayerConfiguration viewConfig) {
    this.player = player;
    this.viewPlayerConfig = viewConfig;
		this.map = map;
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

  public float calculateRayCollision(Ray ray) {
    float nearestDistance = Float.MAX_VALUE;

    nearestDistance = detectDiagonalHorizontalCollisions(ray, nearestDistance);
    nearestDistance = detectVerticalCollisions(ray, nearestDistance);

    return nearestDistance;
  }

  private float detectVerticalCollisions(Ray ray, float nearestDistance) {
    double rayAngle = ray.getRayAngle();
    double rayStartX = ray.getRayStartX();
    double rayStartY = ray.getRayStartY();
    double rayEndX = ray.getRayEndX();

    // player looking right (angle near 0)
    if (rayAngle < 90 || rayAngle > 270) {
      for (double rayMoveX = (float) rayStartX; rayMoveX < rayEndX; rayMoveX += horizontalVerticalSteps) {
        if (rayMoveX % horizontalVerticalSteps != 0) {
          rayMoveX = Math.ceil(rayMoveX / horizontalVerticalSteps) * horizontalVerticalSteps;
        }
        // y move to draw
        double rayDeltaY = (rayMoveX - rayStartX) * Math.tan(Math.toRadians(rayAngle));
        double rayMoveY = rayStartY + rayDeltaY;

        nearestDistance = checkVerticalCollisions(ray, rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);
      }
    }
    // player looking left (angle between 90 and 270)
    else if (rayAngle > 90 && rayAngle < 270) {
      for (double rayMoveX = rayStartX; rayMoveX > rayEndX; rayMoveX -= horizontalVerticalSteps) {
        if (rayMoveX % 50 != 0) {
          rayMoveX = Math.floor(rayMoveX / horizontalVerticalSteps) * horizontalVerticalSteps;
        }
        // y move to draw
        double rayDeltaY = (rayMoveX - rayStartX) * Math.tan(Math.toRadians(rayAngle));
        double rayMoveY = rayStartY + rayDeltaY;

        nearestDistance = checkVerticalCollisions(ray, rayMoveX, rayMoveY, rayStartX, rayStartY, nearestDistance);
      }
    }
    return nearestDistance;
  }

  private float checkVerticalCollisions(Ray ray, double rayMoveForCheckX, double rayMoveForCheckY,
                                       double rayStartForCheckX, double rayStartForCheckY,
																			 float nearestDistanceForCheck) {
		for (Sector sector : map.getAllSectors()) {
			for (Wall wall : sector.getWalls()) {
				if (rayMoveForCheckX == wall.getWallX0()*scale &&
				wall.getWallX0() * scale == wall.getWallX1()*scale &&
				rayMoveForCheckY >= Math.min(wall.getWallY0()*scale, wall.getWallY1()*scale) &&
				rayMoveForCheckY <= Math.max(wall.getWallY0()*scale, wall.getWallY1()*scale) &&
        wall.getConnectedSector() == -1) {
					float rayDistance = distanceToPoint(rayMoveForCheckX, rayMoveForCheckY, rayStartForCheckX, rayStartForCheckY);
					if (rayDistance < nearestDistanceForCheck) {
            nearestDistanceForCheck = rayDistance;
            ray.setRayDistance(nearestDistanceForCheck);
            ray.setCollisionPoint(rayMoveForCheckX, rayMoveForCheckY);
            return nearestDistanceForCheck;
          }
				}
			}
		}
    return nearestDistanceForCheck;
  }

  private float detectDiagonalHorizontalCollisions(Ray ray, float nearestDistance) {
    double rayAngle = ray.getRayAngle();
    double rayStartX = ray.getRayStartX();
    double rayStartY = ray.getRayStartY();

    for(Sector sector : map.getAllSectors()) {
      for(Wall wall : sector.getWalls()) {
        // y = mx + b
        double mSlope = (wall.getWallY1() - wall.getWallY0()) /
                        (wall.getWallX1() - wall.getWallX0());
        // must be * scale
        double bIntercept = (wall.getWallY0() - mSlope * wall.getWallX0())*scale;
        double xDelta = (wall.getWallX1() - wall.getWallX0());
        double tDenominator = (Math.sin(Math.toRadians(rayAngle)) - mSlope * Math.cos(Math.toRadians(rayAngle)));
        if(Math.abs(tDenominator) > 1e-6) {
          double tScale = (mSlope * rayStartX - mSlope * xDelta + bIntercept - rayStartY) / tDenominator;
          if(tScale > 0) {
            double rayMoveX = rayStartX + tScale * Math.cos(Math.toRadians(rayAngle));
            double rayMoveY = rayStartY + tScale * Math.sin(Math.toRadians(rayAngle));

            // do not let the ray get out of the bounds
            float tolerance = 2.0f;
            if(checkWallCollision(rayMoveX, rayMoveY, wall, tolerance))  {

              float rayDistance = distanceToPoint(rayMoveX, rayMoveY, rayStartX, rayStartY);

              if (rayDistance < nearestDistance) {
                nearestDistance = rayDistance;
                ray.setRayDistance(nearestDistance);
                ray.setCollisionPoint(rayMoveX, rayMoveY);
              } 
            }
          }
        }
      }
    }
    return nearestDistance;
  }

  private boolean checkWallCollision(double rayMoveX, double rayMoveY, Wall wall, float tolerance){
    return rayMoveX >= Math.min(wall.getWallX0()*scale, wall.getWallX1()*scale)- tolerance  &&
           rayMoveX <= Math.max(wall.getWallX0()*scale, wall.getWallX1()*scale)+ tolerance &&
           rayMoveY >= Math.min(wall.getWallY0()*scale, wall.getWallY1()*scale)- tolerance && 
           wall.getConnectedSector() == -1 &&
           rayMoveY <= Math.max(wall.getWallY0()*scale, wall.getWallY1()*scale)+ tolerance ||
           distanceToPoint(rayMoveX, rayMoveY, wall.getWallX0()*scale, wall.getWallY0()*scale) <= tolerance ||
           distanceToPoint(rayMoveX, rayMoveY, wall.getWallX1()*scale, wall.getWallY1()*scale) <= tolerance;
  }

  private float distanceToPoint(double x1, double y1, double x2, double y2) {
    return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }
}
