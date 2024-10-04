package org.example.mainClass;

import org.example.map.Map;
import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.Rays3D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private final int WIDTH = 1024, HEIGHT = 576;
  private DisplayMananger displayRayCast2D;
  private DisplayMananger displayGame3D;
  private Player player;
  private Map map;
  private Rays3D rays3d; 

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player(300, 300);
    this.map = new Map();
    this.rays3d = new Rays3D(player, map);
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    displayRayCast2D = new DisplayMananger(WIDTH, HEIGHT, "RayCasting2D");
    displayRayCast2D.createDisplay();

    displayGame3D = new DisplayMananger(WIDTH, HEIGHT, "Game3D");
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
    GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());
    GL11.glClearColor(0.48f, 0.50f, 0.52f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    map.drawMap2d();
    player.drawPlayer();
    player.movePlayer(WIDTH, HEIGHT);
    rays3d.drawRays3D();
    displayRayCast2D.swapBuffers(); 

    GLFW.glfwMakeContextCurrent(displayGame3D.getDisplay());
    GL11.glClearColor(0.48f, 0.50f, 0.52f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    map.drawMap2d();
    player.drawPlayer();
    player.movePlayer(WIDTH, HEIGHT);
    rays3d.drawRays3D();
    displayGame3D.swapBuffers(); 
  }

  public static void main(String[] args) {
    new Main().start();
  }


}
