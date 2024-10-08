package org.example.renderEngine;

import org.example.player.Player;

public class Draw3D {
  private Player player = new Player(70, -110, 20, 0, 0);
  private DisplayMananger display = new DisplayMananger();
  private Pixels pixels = new Pixels();

  private int[] wordX, wordY, wordZ;
  private double playerCos, playerSin; 

  // offSet bottom 2 points by player
  private int pointOneX, pointOneY;
  private int pointTwoX, pointTwoY;

  public Draw3D() {
    this.wordX = new int[4];
    this.wordY = new int[4];
    this.wordZ = new int[4];
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
    wordX[0] = pointOneX*(int)playerCos-pointOneY*(int)playerSin;
    wordX[1] = pointTwoX*(int)playerCos-pointTwoY*(int)playerSin;
    wordX[2] = wordX[0]; // top line has same X
    wordX[3] = wordX[1];

    // word y position (depth)
    wordY[0] = pointOneY*(int)playerCos+pointOneX*(int)playerSin;
    wordY[1] = pointTwoY*(int)playerCos+pointTwoX*(int)playerSin;
    wordY[2] = wordY[0]; //top line has same Y
    wordY[3] = wordY[1];

    // word z height
    wordZ[0] = 0-(int)player.getPlayerPositionZ()+(int)((player.getPlayerLookUpDown()*wordY[0])/32.0);
    wordZ[1] = 0-(int)player.getPlayerPositionZ()+(int)((player.getPlayerLookUpDown()*wordY[1])/32.0);
    wordZ[2] = wordZ[0]+40; //top line has new Z
    wordZ[3] = wordZ[1]+40;


    //secreen (x,y) position
    wordX[0] = wordX[0]*200/wordY[0]+display.getScreenWidthTwo();
    wordY[0] = wordZ[0]*200/wordY[0]+display.getScreenHeightTwo();
    wordX[1] = wordX[1]*200/wordY[1]+display.getScreenWidthTwo();
    wordY[1] = wordZ[1]*200/wordY[1]+display.getScreenHeightTwo();

    // draw points
    // if (wordX[0] > 0 && wordX[0] < display.getScreenWidth() && wordY[0] > 0 && wordY[0] < display.getScreenHeight() ) pixels.pixel(wordX[0], wordY[0], 0);
    // if (wordX[1] > 0 && wordX[1] < display.getScreenWidth() && wordY[1] > 0 && wordY[1] < display.getScreenHeight() ) pixels.pixel(wordX[1], wordY[1], 0);
    

    drawWall(wordX[0], wordX[1], wordY[0], wordY[1]);
  }

  public void drawWall(int pointOneX, int pointTwoX, int botLineOne, int botLineTwo) {
    int distanceBotY = botLineTwo - botLineOne; // y distace of bottom line

    int distanceX = pointTwoX - pointOneX; 
    if ( distanceX == 0 ) distanceX = 1;

    int pointOneStartingPositionX = pointOneX; // hold initial pointOneX startingPosition
  
    // draw X veticle lines
    for (int pointOneXFor = pointOneX; pointOneXFor < pointTwoX; pointOneXFor++) {
      // y start an end point
      double pointOneYDraw = distanceBotY * (pointOneXFor - pointOneStartingPositionX + 0.5)/distanceX+botLineOne; // y bottom point
      pixels.pixel(pointOneXFor, (int)pointOneYDraw, 0); // bottom
    }


  }


}















