package org.example.player;

import static org.lwjgl.opengl.GL11.*;

import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

public class Player {
  private float playerPositionX, playerPositionY, playerDeltaX, playerDeltaY, playerAngle; 


  public Player(int posX, int posY) {
    this.playerPositionX = posX;
    this.playerPositionY = posY;
    this.playerDeltaX = playerDeltaX; 
    this.playerDeltaY = playerDeltaY; 
    this.playerAngle = playerAngle; 
  }

  public void drawPlayer() {
    glColor3f(1.0f, 1.0f, 0.0f);
    glPointSize(10);
    glBegin(GL_POINTS);
    glVertex2f(playerPositionX, playerPositionY);
    glEnd();
  }

  public void movePlayer(int displayWidth, int displayHeight) {
    if (Inputs.isKeyDown(GLFW.GLFW_KEY_W)) { 
      playerPositionY += 2.5;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_S)) { 
      playerPositionY -= 2.5;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_D)) { 
      playerPositionX += 2.5;
    }

    if (Inputs.isKeyDown(GLFW.GLFW_KEY_A)) { 
      playerPositionX -= 2.5;
    }
    

    playerPositionX = Math.max(0, Math.min(playerPositionX, displayWidth)); 
    playerPositionY = Math.max(0, Math.min(playerPositionY, displayHeight));

  }

}
