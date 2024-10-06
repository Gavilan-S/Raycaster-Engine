package org.example.mainClass;

import org.example.map.Map;
import org.example.map.MapSectors;
import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.RaysDraw;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private final int WIDTHRAYCAST2D = 1700, HEIGHTRAYCAST2D = 1000, WIDTHGAME3D = 1024, HEIGHTGAME3D = 576;
  private DisplayMananger displayRayCast2D;
  private DisplayMananger displayGame3D;
  private Player player;
  private Map map;
  private RaysDraw raysDraw; 

  private MapSectors mapSectors;

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player(300, 300);
    this.map = new Map();
    this.raysDraw = new RaysDraw(player, map, WIDTHGAME3D);

    this.mapSectors = new MapSectors();
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    displayRayCast2D = new DisplayMananger(WIDTHRAYCAST2D, HEIGHTRAYCAST2D, "RayCasting2D");
    displayRayCast2D.createDisplay();

    displayGame3D = new DisplayMananger(WIDTHGAME3D, HEIGHTGAME3D, "Game3D");
    displayGame3D.createDisplay();

  }

  public void run() {
    init();
    System.out.println("runnig new game");
    // while is not close -> update, render
    while (!displayRayCast2D.closeDisplay() && !displayGame3D.closeDisplay()) {
      update();
      render();
      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
    }
    displayRayCast2D.destroyFree();
    displayGame3D.destroyFree();
  }

  private void update() {
    displayRayCast2D.updateDisplay();
    displayGame3D.updateDisplay();
  }

  private void render() {
    // render 2D
    GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    mapSectors.drawMapSectors();

    displayRayCast2D.swapBuffers(); 

    // render 3D
    GLFW.glfwMakeContextCurrent(displayGame3D.getDisplay());
    GL11.glClearColor(0.48f, 0.50f, 0.52f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    raysDraw.drawRays("raysuse3d");


    displayGame3D.swapBuffers(); 
  }

  public int getWIDTHGAME3D() {
    return WIDTHGAME3D;
  }

  public static void main(String[] args) {
    new Main().start();
  }


}
