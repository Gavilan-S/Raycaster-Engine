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
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionX -= playerDeltaX;
      playerPositionY -= playerDeltaY;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionX += playerDeltaY;
      playerPositionY -= playerDeltaX;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionX -= playerDeltaY;
      playerPositionY += playerDeltaX;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
      playerPositionZ += 4;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
      playerPositionZ -= 4;
    }

    // player vision move
    if (Inputs.getCursorMoved()) {
      // looking left/rigth
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 2;
        if (playerAngle < 0) {
          playerAngle += 360;
        }
        playerMouseX = (float) Inputs.getMouseX();
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 2;
        if (playerAngle > 359) {
          playerAngle -= 360;
        }
        playerMouseX = (float) Inputs.getMouseX();
      }

      // looking up/down
      if(Inputs.getMouseY() < playerMouseY) {
        playerLookUpDown -= 1;
        playerMouseY = (float) Inputs.getMouseY();
      }

      if(Inputs.getMouseY() > playerMouseY) {
        playerLookUpDown += 1;
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
