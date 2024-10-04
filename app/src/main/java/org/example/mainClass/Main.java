package org.example.mainClass;

import org.example.map.Map;
import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.Rays3D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main {
  private final int WIDTHRAYCAST2D = 1024, HEIGHTRAYCAST2D = 576;
  private final int WIDTHGAME = 1024, HEIGHTGAME = 576;
  private DisplayMananger displayRayCast2D;
  private DisplayMananger displayGame;
  private Player player;
  private Map map;
  private Rays3D rays3d; 

  // Thread help us to run the same code at the same time
  public Thread threadGame;
  public Thread threadRayCast2D;

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    displayRayCast2D = new DisplayMananger(WIDTHRAYCAST2D, HEIGHTRAYCAST2D, "RayCasting2D");
    displayRayCast2D.createDisplay();
    displayRayCast2D.setBackgroundColor(0.48f, 0.50f, 0.52f);

    displayGame = new DisplayMananger(WIDTHGAME, HEIGHTGAME, "Game3D");
    displayGame.createDisplay();
    displayGame.setBackgroundColor(0.48f, 0.50f, 0.52f);

    this.player = new Player(300, 300);
    this.map = new Map();
    this.rays3d = new Rays3D(player, map);
  }

  public void runGame3D() {
    init();
    System.out.println("runnig Game");
    // while is not close -> update, render
    while (!displayGame.closeDisplay()) {

      GLFW.glfwMakeContextCurrent(displayGame.getDisplay());
      updateGame();
      renderGame3D();
      GLFW.glfwPollEvents();

      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;

    }
    displayGame.destroyFree();
  }

  private void updateGame() {
    displayGame.updateDisplay();
  }

  public void runRayCast2D() {
    init();
    System.out.println("runnig RayCasting2D");
    // while is not close -> update, render
    while (!displayRayCast2D.closeDisplay() && !displayGame.closeDisplay()) {

      GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());
      updateRayCast2D();
      renderRayCast2D();

      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
      GLFW.glfwPollEvents();
    }
    displayRayCast2D.destroyFree();
  }

  private void updateRayCast2D() {
    displayRayCast2D.updateDisplay();
  }

  private void renderRayCast2D() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());

    map.drawMap2d();
    player.drawPlayer();
    rays3d.drawRays3D();
    player.movePlayer(WIDTHGAME, HEIGHTGAME);
    displayRayCast2D.swapBuffers(); 
  }

  private void renderGame3D() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    GLFW.glfwMakeContextCurrent(displayGame.getDisplay());
    
    rays3d.drawRays3D();
    displayGame.swapBuffers(); 
  }

  public static void main(String[] args) {
    Main mainInstance = new Main();
    mainInstance.init();

    // Crear hilos para cada ventana
    Thread threadRayCast2D = new Thread(mainInstance::runRayCast2D, "RayCasting2D Thread");
    Thread threadGame3D = new Thread(mainInstance::runGame3D, "Game3D Thread");

    threadRayCast2D.start();
    threadGame3D.start();

    // Esperar a que ambos hilos terminen
    try {
      threadRayCast2D.join();
      threadGame3D.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
