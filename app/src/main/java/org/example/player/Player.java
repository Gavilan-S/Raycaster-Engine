package org.example.player;

import static org.lwjgl.opengl.GL11.*;

import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

public class Player {
  private float playerMouseX= (float) Inputs.getMouseX(), playerDeltaX, playerDeltaY, playerAngle, playerPositionX, playerPositionY; 

  public Player(int posX, int posY) {
    this.playerPositionX = posX;
    this.playerPositionY = posY;
    this.playerDeltaX = (float) Math.cos(playerAngle)*5; 
    this.playerDeltaY = (float) Math.sin(playerAngle)*5; 
    this.playerAngle = playerAngle; 
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
      playerPositionY += playerDeltaY*0.1;
      playerPositionX += playerDeltaX*0.1;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionY -= playerDeltaY*0.1;
      playerPositionX -= playerDeltaX*0.1;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionY += (Math.cos(playerAngle) * 0.5); 
      playerPositionX -= (Math.sin(playerAngle) * 0.5);
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionY -= (Math.cos(playerAngle) * 0.5); 
      playerPositionX += (Math.sin(playerAngle) * 0.5);
    }

    // do not get out of the screen
    playerPositionX = Math.max(0, Math.min(playerPositionX, displayWidth)); 
    playerPositionY = Math.max(0, Math.min(playerPositionY, displayHeight));

    // player vision move
    if (Inputs.getCursorMoved()) {
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 0.05;
        if (playerAngle < 0) {
          playerAngle += 2 * Math.PI;
        }
        playerDeltaX = (float) (Math.cos(playerAngle)*4);
        playerDeltaY = (float) (Math.sin(playerAngle)*4);

        playerMouseX = (float) Inputs.getMouseX();
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 0.05;
        if (playerAngle > 2*Math.PI) {
          playerAngle -= 2 * Math.PI;
        }
        playerDeltaX = (float) (Math.cos(playerAngle)*4);
        playerDeltaY = (float) (Math.sin(playerAngle)*4);

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
