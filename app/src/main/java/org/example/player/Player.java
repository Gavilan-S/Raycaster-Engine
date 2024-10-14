package org.example.player;

import static org.lwjgl.opengl.GL11.*;

import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

public class Player {
  private DisplayMananger display = new DisplayMananger(); 

  private float playerMouseX= (float) Inputs.getMouseX();
  private float playerAngle;
  private float playerPositionX = 300, playerPositionY = 300; 

  private float playerDeltaX, playerDeltaY;

  public Player() {
    this.playerDeltaX = (float) Math.cos(Math.toRadians(playerAngle)) * 5; 
    this.playerDeltaY = (float) Math.sin(Math.toRadians(playerAngle)) * 5;
  }

 public void drawPlayer() {
    // square for player
    glColor3f(1.0f, 1.0f, 0.0f);
    glPointSize(5);
    glBegin(GL_POINTS);
    glVertex2f(playerPositionX, playerPositionY);
    glEnd();

    // line for player vision
    glLineWidth(2);
    glBegin(GL_LINES);
    glVertex2f(playerPositionX, playerPositionY);
    glVertex2f(playerPositionX+playerDeltaX*4, playerPositionY+playerDeltaY*4);
    glEnd();
  }

  public void movePlayer() {
    drawPlayer();
    if (Inputs.isKeyDown(GLFW.GLFW_KEY_W)) { 
      playerPositionY += playerDeltaY*0.3;
      playerPositionX += playerDeltaX*0.3;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionY -= playerDeltaY*0.2;
      playerPositionX -= playerDeltaX*0.2;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionY -= Math.cos(Math.toRadians(playerAngle)) * 0.5; 
      playerPositionX += Math.sin(Math.toRadians(playerAngle)) * 0.5;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionY += Math.cos(Math.toRadians(playerAngle)) * 0.5; 
      playerPositionX -= Math.sin(Math.toRadians(playerAngle)) * 0.5;
    }

    // do not get out of the screen
    playerPositionX = Math.max(0, Math.min(playerPositionX, display.getWidth())); 
    playerPositionY = Math.max(0, Math.min(playerPositionY, display.getHeight()));

    // player vision move
    if (Inputs.getCursorMoved()) {
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 2.86f;
        if (playerAngle < 0) {
          playerAngle += 360;
        }
        playerDeltaX = (float) Math.cos(Math.toRadians(playerAngle)) * 4;
        playerDeltaY = (float) Math.sin(Math.toRadians(playerAngle)) * 4;

        playerMouseX = (float) Inputs.getMouseX();
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 2.86f;
        if (playerAngle >= 360) {
          playerAngle -= 360;
        }
        playerDeltaX = (float) Math.cos(Math.toRadians(playerAngle)) * 4;
        playerDeltaY = (float) Math.sin(Math.toRadians(playerAngle)) * 4;


        playerMouseX = (float) Inputs.getMouseX();
      }
    }
  }
  public float getPlayerPositionX() { return playerPositionX; }
  public float getPlayerPositionY() { return playerPositionY; }    
  public float getPlayerAngle() { return playerAngle; }

  public void setPlayerAngle(float playerAngle) { this.playerAngle = playerAngle; }

}
