package org.example.mainClass;

import org.example.map.MapSectors;
import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.RaysDraw;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private DisplayMananger displayRayCast2D;
  private Player player;
  private RaysDraw raysDraw;

  private MapSectors mapSectors;

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player();
    this.mapSectors = new MapSectors();
    this.raysDraw = new RaysDraw();
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    displayRayCast2D = new DisplayMananger();
    displayRayCast2D.createDisplay();


  }

  public void run() {
    init();
    System.out.println("runnig new game");
    // while is not close -> update, render
    while (!displayRayCast2D.closeDisplay()) {
      update();
      render();
      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
    }
    displayRayCast2D.destroyFree();
  }

  private void update() {
    displayRayCast2D.updateDisplay();
  }

  private void render() {
    // render 2D
    GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    mapSectors.mapPoints();
    mapSectors.drawMapSectors();
    player.drawPlayer();
    player.movePlayer();
    raysDraw.draw();

    displayRayCast2D.swapBuffers(); 

  }
  
  public static void main(String[] args) {
    new Main().start();
  }
}
