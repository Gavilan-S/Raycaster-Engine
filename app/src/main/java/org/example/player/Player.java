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
    glColor3f(1.0f, 1.0f, 0.0f);
    glPointSize(10);
    glBegin(GL_POINTS);
    glVertex2f(playerPositionX, playerPositionY);
    glEnd();

    glLineWidth(3);
    glBegin(GL_LINES);
    glVertex2f(playerPositionX, playerPositionY);
    glVertex2f(playerPositionX+playerDeltaX*5, playerPositionY+playerDeltaY*5);
    glEnd();
  }

  public void movePlayer(int displayWidth, int displayHeight) {
    if (Inputs.isKeyDown(GLFW.GLFW_KEY_W)) { 
      playerPositionY += 1;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionY -= 0.8;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionX += 0.6;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionX -= 0.6;
    }

    playerPositionX = Math.max(0, Math.min(playerPositionX, displayWidth)); 
    playerPositionY = Math.max(0, Math.min(playerPositionY, displayHeight));

    if (Inputs.getCursorMoved()) {
      if(Inputs.getMouseX() < playerMouseX) {
        playerAngle -= 0.05;
        if (playerAngle < 0) {
          playerAngle += 2 * Math.PI;
        }
        playerDeltaX = (float) (Math.cos(playerAngle)*5);
        playerDeltaY = (float) (Math.sin(playerAngle)*5);

        playerMouseX = (float) Inputs.getMouseX();
        System.out.println("pda=" + playerAngle);
      }

      if(Inputs.getMouseX() > playerMouseX) {
        playerAngle += 0.05;
        if (playerAngle > 2*Math.PI) {
          playerAngle -= 2 * Math.PI;
        }
        playerDeltaX = (float) (Math.cos(playerAngle)*5);
        playerDeltaY = (float) (Math.sin(playerAngle)*5);

        playerMouseX = (float) Inputs.getMouseX();
        System.out.println("pda=" + playerAngle);
      }
    }

  }

    public void check() {
    if (Inputs.getCursorMoved()) {
      System.out.println(Inputs.getMouseX());
    }
    Inputs.setCursorMoved(false);
  }
      
}
