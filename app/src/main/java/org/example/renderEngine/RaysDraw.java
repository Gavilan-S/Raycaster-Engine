package org.example.renderEngine;

import static org.lwjgl.opengl.GL11.*;

import org.example.map.Map;
import org.example.player.Player;

public class RaysDraw {
  private float rayX, rayY, rayAngle, xOffSet, yOffSet, rayDistance;
  private int ray, mapX, mapY, mapPosition, depthOffField;

  private Player player = new Player(300, 300);
  private Map realMap;

  public RaysDraw(Player player, Map map, int WIDTHGAME3D) {
    this.player = player;
    this.realMap = map;

    this.rayX = 0;
    this.rayY = 0;
    this.rayAngle = 0; 
    this.xOffSet = 0; 
    this.yOffSet = 0; 
    this.rayDistance = 0;

    this.ray = 0; 
    this.mapX = 0; 
    this.mapY = 0; 
    this.mapPosition = 0; 
    this.depthOffField = 0; 
  }

  // teorema de pitagoras (a y b son catetos, no supe como mas llamarlos)
  public float distancePlayerEndPoint(float aX, float aY, float bX, float bY, float angle) {
    return ((float)(Math.sqrt((bX - aX) * (bX - aX) + (bY - aY) * (bY - aY))));
  }

  public void drawRays(String raysUse) {
    rayAngle = (float) (player.getPlayerAngle() - Math.toRadians(1)*30);
    if (rayAngle < 0) {
      rayAngle += 2*Math.PI;
    }
    if (rayAngle > 2*Math.PI) {
      rayAngle -= 2*Math.PI;
    }

    for (ray = 0; ray < 480; ray++) {
      // check horizontal lines
      depthOffField = 0;
      float distanceHorizontal = 10000000, horizontalX = player.getPlayerPositionX(), horizontalY = player.getPlayerPositionY();
      float aTan = (float) (-1/Math.tan(rayAngle));

      // player looking down 
      if (rayAngle > Math.PI)  {
        rayY = (float) ((((int) player.getPlayerPositionY() >> 6) << 6) - 0.0001);
        rayX = (player.getPlayerPositionY() - rayY) * aTan + player.getPlayerPositionX();
        yOffSet = -64;
        xOffSet = -yOffSet * aTan;
      }

      // player looking up
      if (rayAngle < Math.PI) { 
        rayY = (float) ((((int) player.getPlayerPositionY() >> 6) << 6) + 64);
        rayX = (player.getPlayerPositionY() - rayY) * aTan + player.getPlayerPositionX();
        yOffSet = 64;
        xOffSet = -yOffSet * aTan;
      } 

      // player looking straight left or right
      if (rayAngle == 0 || rayAngle == Math.PI) {
        rayX = player.getPlayerPositionX();
        rayY = player.getPlayerPositionY();
        depthOffField = 8;
      }

      while (depthOffField < 8) {
        mapX = (int) (rayX) >> 6;
        mapY = (int) (rayY) >> 6;
        mapPosition = mapY * realMap.getMapUnitsX() + mapX; 

        if ( mapPosition > 0 && mapPosition < realMap.getMapUnitsY() * realMap.getMapUnitsX() && realMap.getMap()[mapPosition] == 1) { // ray hit a wall
          horizontalX = rayX;
          horizontalY = rayY;
          distanceHorizontal = distancePlayerEndPoint(player.getPlayerPositionX(), player.getPlayerPositionY(), horizontalX, horizontalY, rayAngle); 
          depthOffField = 8;
        } else {
          rayX += xOffSet;
          rayY += yOffSet;
          depthOffField += 1;
        }
      }

      // check vertical lines
      depthOffField = 0;
      float distanceVertical = 10000000, verticalX = player.getPlayerPositionX(), verticalY = player.getPlayerPositionY();
      float negativeTan = (float) (-Math.tan(rayAngle));

      // player looking left
      if (rayAngle > Math.PI/2 && rayAngle < 3*Math.PI/2)  {
        rayX = (float) ((((int) player.getPlayerPositionX() >> 6) << 6) - 0.0001);
        rayY = (player.getPlayerPositionX() - rayX) * negativeTan + player.getPlayerPositionY();
        xOffSet = -64;
        yOffSet = -xOffSet * negativeTan;
      }

      // player looking right
      if (rayAngle < Math.PI/2 || rayAngle > 3*Math.PI/2)  {
        rayX = (float) ((((int) player.getPlayerPositionX() >> 6) << 6) + 64);
        rayY = (player.getPlayerPositionX() - rayX) * negativeTan + player.getPlayerPositionY();
        xOffSet = 64;
        yOffSet = -xOffSet * negativeTan;
      } 

      // player looking straight up or down 
      if (rayAngle == 0 || rayAngle == Math.PI) {
        rayX = player.getPlayerPositionX();
        rayY = player.getPlayerPositionY();
        depthOffField = 8;
      }

      while (depthOffField < 8) {
        mapX = (int) (rayX) >> 6;
        mapY = (int) (rayY) >> 6;
        mapPosition = mapY * realMap.getMapUnitsX() + mapX; 

        // ray hit a wall
        if (mapPosition > 0 && mapPosition < realMap.getMapUnitsY() * realMap.getMapUnitsX() && realMap.getMap()[mapPosition] == 1) { 
          verticalX = rayX;
          verticalY = rayY;
          distanceVertical = distancePlayerEndPoint(player.getPlayerPositionX(), player.getPlayerPositionY(), verticalX, verticalY, rayAngle);          

          depthOffField = 8;
        } else {
          rayX += xOffSet;
          rayY += yOffSet;
          depthOffField += 1;
        }
      }

      // draw 3D rays
      // shorther distance
      if (distanceVertical < distanceHorizontal) { 
        rayX = verticalX;
        rayY = verticalY;

        glColor3f(0, 0.9f, 0);

        rayDistance = distanceVertical;
      }

      // shorther distance
      if (distanceVertical > distanceHorizontal) {
        rayX = horizontalX;
        rayY = horizontalY;

        glColor3f(0, 0.7f, 0);

        rayDistance = distanceHorizontal;
      }

      // calcule 3D walls
      float cameraEye = player.getPlayerAngle() - rayAngle;
      if (cameraEye < 0) {
        cameraEye += 2*Math.PI;
      }
      if (cameraEye > 2*Math.PI) {
        cameraEye -= 2*Math.PI;
      }

      // fix fishEye
      rayDistance = (float) (rayDistance * Math.cos(cameraEye));
      // line height
      float lineHeight = (realMap.getMapSquare()*320)/rayDistance;
      if ( lineHeight > 320 ) {
        lineHeight = 320;
      }
      // line OffSet
      float lineOffSet = 160-lineHeight/2;

      rayAngle += Math.toRadians(0.125);
      if (rayAngle < 0) {
        rayAngle += 2*Math.PI;
      }
      if (rayAngle > 2*Math.PI) {
        rayAngle -= 2*Math.PI;
      }

      // for 2 screens
      switch (raysUse.toLowerCase()) {
        case "raysuse2d":
        glColor3f(0, 1, 0);
        glLineWidth(1);
        glBegin(GL_LINES);
        glVertex2f(player.getPlayerPositionX(), player.getPlayerPositionY());
        glVertex2f(rayX, rayY);
        glEnd();
        break;

        // draw 3D walls
        case "raysuse3d":
        glLineWidth(8);
        glBegin(GL_LINES);
        glVertex2d((ray * 2.2), lineOffSet+50);
        glVertex2d((ray * 2.2), lineHeight+lineOffSet+ 50);
        glEnd();
        break;

        default:
        break;
      }

    }
  }
}
