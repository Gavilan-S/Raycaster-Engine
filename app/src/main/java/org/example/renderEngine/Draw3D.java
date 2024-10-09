package org.example.renderEngine;

import org.example.player.Player;

public class Draw3D {
  private Player player = new Player(70, -110, 20, 0, 0);
  private DisplayMananger display = new DisplayMananger();
  private Pixels pixels = new Pixels();

  private int[] wallX, wallY, wallZ;
  private double playerCos, playerSin; 

  // offSet bottom 2 points by player
  private int pointOneX, pointOneY;
  private int pointTwoX, pointTwoY;

  public Draw3D() {
    this.wallX = new int[4];
    this.wallY = new int[4];
    this.wallZ = new int[4];
  }

  public void update() {
    player.movePlayer(display.getScreenWidth(), display.getScreenHeight());
    playerCos = player.getPlayerDeltaY();
    playerSin = player.getPlayerDeltaX();
  }

  public void draw3d() {
    update();

    // points by player
    pointOneX = 20-(int)player.getPlayerPositionX();
    pointOneY = 5-(int)player.getPlayerPositionY();
    pointTwoX = 20-(int)player.getPlayerPositionX();
    pointTwoY= 150-(int)player.getPlayerPositionY();
    // word x position
    wallX[0] = pointOneX*(int)playerCos-pointOneY*(int)playerSin;
    wallX[1] = pointTwoX*(int)playerCos-pointTwoY*(int)playerSin;
    wallX[2] = wallX[0]; // top line has same X
    wallX[3] = wallX[1];

    // word y position (depth)
    wallY[0] = pointOneY*(int)playerCos+pointOneX*(int)playerSin;
    wallY[1] = pointTwoY*(int)playerCos+pointTwoX*(int)playerSin;
    wallY[2] = wallY[0]; //top line has same Y
    wallY[3] = wallY[1];

    // word z height
    wallZ[0] = 0-(int)player.getPlayerPositionZ()+(int)(((player.getPlayerLookUpDown()*wallY[0])/32.0)+0.01);
    wallZ[1] = 0-(int)player.getPlayerPositionZ()+(int)(((player.getPlayerLookUpDown()*wallY[1])/32.0)+0.01);
    wallZ[2] = wallZ[0]+190; //top line has new Z
    wallZ[3] = wallZ[1]+190;

    // do not draw if behind player
    if (wallY[0] < 0.1 && wallY[1] < 0.1 ) return;

    // pointOne behind player, clip 
    if (wallY[0] < 1) {
      clipBehindPlayer(wallX[0], wallY[0], wallZ[0], wallX[1], wallY[1], wallZ[1]); // bot line
      clipBehindPlayer(wallX[2], wallY[2], wallZ[2], wallX[3], wallY[3], wallZ[3]); // top line
    }

    // pointTwo behind player, clip
    if (wallY[1] < 1) {
      clipBehindPlayer(wallX[1], wallY[1], wallZ[1], wallX[0], wallY[0], wallZ[0]); // bot line
      clipBehindPlayer(wallX[3], wallY[3], wallZ[3], wallX[2], wallY[2], wallZ[2]); // top line
    }


    //secreen (x,y) position
    if (wallY[0] != 0) {
      wallX[0] = wallX[0] * 200 / wallY[0] + display.getScreenWidthTwo();
      wallY[0] = wallZ[0] * 200 / wallY[0] + display.getScreenHeightTwo();
    } else {
      wallX[0] = 1;
      wallY[0] = 1;
    }
    if (wallY[1] != 0) {
      wallX[1] = wallX[1] * 200 / wallY[1] + display.getScreenWidthTwo();
      wallY[1] = wallZ[1] * 200 / wallY[1] + display.getScreenHeightTwo();
    } else {
      wallX[1] = 1;
      wallY[1] = 1;
    }
    if (wallY[2] != 0) {
      wallX[2] = wallX[2] * 200 / wallY[2] + display.getScreenWidthTwo();
      wallY[2] = wallZ[2] * 200 / wallY[2] + display.getScreenHeightTwo();
    } else {
      wallX[2] = 1;
      wallY[2] = 1;
    }
    if (wallY[3] != 0) {
      wallX[3] = wallX[3] * 200 / wallY[3] + display.getScreenWidthTwo();
      wallY[3] = wallZ[3] * 200 / wallY[3] + display.getScreenHeightTwo();
    } else {
      wallX[3] = 1;
      wallY[3] = 1;
    }

    // draw points
    drawWall(wallX[0], wallX[1], wallY[0], wallY[1], wallY[2], wallY[3]);
  }

  private void clipBehindPlayer(float clipPointOneX, float clipPointOneY, float clipPointOneZ, float clipPointTwoX, float clipPointTwoY, float clipPointTwoZ) {
    float distancePointA = clipPointOneY; // distance plane -> pointA
    float distancePointB = clipPointTwoY; // distance plane -> pointB
    float distanceTotal = distancePointB - distancePointA;
    if (distanceTotal == 0) distanceTotal = 1;
    float intersection = distancePointA/(distancePointA-distancePointB);

    clipPointOneX = clipPointOneX + intersection*(clipPointTwoX-(clipPointOneX));
    clipPointOneY = clipPointOneY + intersection*(clipPointTwoY-(clipPointOneX));
    if (clipPointOneY == 0) clipPointOneY = 1; // prevent divide by zero error
    clipPointOneZ = clipPointOneZ + intersection*(clipPointTwoZ-(clipPointOneZ));
  }

  private void drawWall(int pointOneX, int pointTwoX, int botLineOne, int botLineTwo, int topLineOne, int topLineTwo) {
    int distanceBotY = botLineTwo - botLineOne; // y distace of bottom line
    int distanceTopY = topLineTwo - topLineOne; // y distace of top line
    int distanceX = pointTwoX - pointOneX; 
    if ( distanceX == 0 ) distanceX = 1;

    int pointOneStartingPositionX = pointOneX; // hold initial pointOneX startingPosition
    // clip X
    if (pointOneX < 0) pointOneX = 0; // clip left
    if (pointTwoX < 0) pointTwoX = 0; // clip left
    if (pointOneX > display.getScreenWidth()-0) pointOneX = display.getScreenWidth()-0; // clip right
    if (pointTwoX > display.getScreenWidth()-0) pointTwoX = display.getScreenWidth()-0; // clip right
  
    // draw X veticle lines
    for (int pointOneXFor = pointOneX; pointOneXFor < pointTwoX; pointOneXFor++) {
      // y start an end point
      
      double pointOneY = distanceBotY * (pointOneXFor - pointOneStartingPositionX + 0.5)/distanceX+botLineOne; // y bottom point
      double pointTwoY = distanceTopY * (pointOneXFor - pointOneStartingPositionX + 0.5)/distanceX+topLineOne; // y top point
      // clip Y
      if (pointOneY < 0) pointOneY = 0; // clip y
      if (pointTwoY < 0) pointTwoY = 0; // clip y
      if (pointOneY > display.getScreenHeight()-0) pointOneY = display.getScreenHeight()-0; // clip y 
      if (pointTwoY > display.getScreenHeight()-0) pointTwoY = display.getScreenHeight()-0; // clip y 
      
      for (int pointOneYFor = (int)pointOneY; pointOneYFor < (int)pointTwoY; pointOneYFor++) {
        pixels.pixel(pointOneXFor, pointOneYFor, 0);
      }
    }
  }
}





