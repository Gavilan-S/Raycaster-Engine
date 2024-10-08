package org.example.player;

import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

public class Player {
  private float playerMouseX = (float) Inputs.getMouseX(), playerMouseY = (float) Inputs.getMouseY();

  private float playerAngle, playerPositionX, playerPositionY, playerPositionZ, playerLookUpDown; 

  private float playerDeltaX, playerDeltaY;

  public Player(float posX, float posY, float posZ, float lookUpDown, float angle) {
    this.playerPositionX = posX;
    this.playerPositionY = posY;
    this.playerPositionZ = posZ;
    this.playerLookUpDown = lookUpDown;
    this.playerAngle = angle; 

  }

  public void update() {
    this.playerDeltaX = (float) Math.sin(Math.toRadians(playerAngle)) * 10;
    this.playerDeltaY = (float) Math.cos(Math.toRadians(playerAngle)) * 10;
  }

  public void movePlayer(int displayWidth, int displayHeight) {
    update();
    if (Inputs.isKeyDown(GLFW.GLFW_KEY_W)) { 
      playerPositionX += playerDeltaX;
      playerPositionY += playerDeltaY;
      System.out.println("W");
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionX -= playerDeltaX;
      playerPositionY -= playerDeltaY;
      System.out.println("S");
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionX += playerDeltaY;
      playerPositionY -= playerDeltaX;
      System.out.println("D");
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionX -= playerDeltaY;
      playerPositionY += playerDeltaX;
      System.out.println("S");

    }

    // do not get out of the screen
    // playerPositionX = Math.max(0, Math.min(playerPositionX, displayWidth)); 
    // playerPositionY = Math.max(0, Math.min(playerPositionY, displayHeight));

    // player vision move
    if (Inputs.getCursorMoved()) {
      // looking left/rigth
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 4;
        if (playerAngle < 0) {
          playerAngle += 360;
        }
        System.out.println("left");
        playerMouseX = (float) Inputs.getMouseX();
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 4;
        if (playerAngle > 359) {
          playerAngle -= 360;
        }
        System.out.println("right");
        playerMouseX = (float) Inputs.getMouseX();
      }

      // looking up/down
      if(Inputs.getMouseY() < playerMouseY) {
        playerLookUpDown -= 1;
        System.out.println("up");
        playerMouseY = (float) Inputs.getMouseY();
      }

      if(Inputs.getMouseY() > playerMouseY) {
        playerLookUpDown += 1;
        System.out.println("down");
        playerMouseY = (float) Inputs.getMouseY();
      }
    }
  }

  public float getPlayerPositionX() {
    return playerPositionX;
  }

  public float getPlayerPositionY() {
    return playerPositionY;
  }    

  public float getPlayerPositionZ() {
    return playerPositionZ;
  }    

  public float getPlayerAngle() {
    return playerAngle;
  }

  public float getPlayerLookUpDown() {
    return playerLookUpDown;
  }

  public float getPlayerDeltaX() {
    return playerDeltaX;
  }

  public float getPlayerDeltaY() {
    return playerDeltaY;
  }




}
