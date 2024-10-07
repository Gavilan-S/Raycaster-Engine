package org.example.player;

import static org.lwjgl.opengl.GL11.*;

import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

public class Player {
  private float playerMouseX= (float) Inputs.getMouseX(), playerDeltaX, playerDeltaY, playerAngle, playerPositionX, playerPositionY, playerPositionZ, playerLookUpDown; 

  public Player(int posX, int posY, int posZ, int lookUpDown, int angle) {
    this.playerPositionX = posX;
    this.playerPositionY = posY;
    this.playerPositionZ = posZ;
    this.playerLookUpDown = lookUpDown;
    this.playerAngle = angle; 

    this.playerDeltaX = (float) Math.sin(playerAngle)*10; 
    this.playerDeltaY = (float) Math.cos(playerAngle)*10; 
  }

  public void drawPlayer() {
    // square for player
    glColor3f(1.0f, 1.0f, 0.0f);
    glPointSize(10);
    glBegin(GL_POINTS);
    glVertex2f(playerPositionX, playerPositionY);
    glEnd();

    // line for player vision
    glLineWidth(3);
    glBegin(GL_LINES);
    glVertex2f(playerPositionX, playerPositionY);
    glVertex2f(playerPositionX+playerDeltaX*5, playerPositionY+playerDeltaY*5);
    glEnd();
  }

  public void movePlayer(int displayWidth, int displayHeight) {
    if (Inputs.isKeyDown(GLFW.GLFW_KEY_W)) { 
      playerPositionX += playerDeltaX;
      playerPositionY += playerDeltaY;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionX -= playerDeltaX;
      playerPositionY -= playerDeltaY;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionX -= (Math.sin(playerAngle));
      playerPositionY += (Math.cos(playerAngle)); 
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionX += (Math.sin(playerAngle));
      playerPositionY -= (Math.cos(playerAngle)); 
    }

    // do not get out of the screen
    playerPositionX = Math.max(0, Math.min(playerPositionX, displayWidth)); 
    playerPositionY = Math.max(0, Math.min(playerPositionY, displayHeight));

    // player vision move
    if (Inputs.getCursorMoved()) {
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 4;
        if (playerAngle < 0) {
          playerAngle += 360;
        }
        playerMouseX = (float) Inputs.getMouseX();
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 4;
        if (playerAngle > 359) {
          playerAngle -= 360;
        }
        playerMouseX = (float) Inputs.getMouseX();
      }
    }

  }
  public float getPlayerPositionX() {
    return playerPositionX;
  }

  public float getPlayerPositionY() {
    return playerPositionY;
  }    

  public float getPlayerAngle() {
    return playerAngle;
  }


}
