package org.example.mainClass;

import org.example.map.Map;
import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private final int WIDTH = 1024, HEIGHT = 576;
  public DisplayMananger display;
  public Player player;
  public Map map;

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player(300, 300);
    this.map = new Map();
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    display = new DisplayMananger(WIDTH, HEIGHT, "RayCasting");
    display.setBackgroundColor(0.48f, 0.50f, 0.52f);
    display.createDisplay();
  }

  public void run() {
    init();
    System.out.println("runnig new game");
    // while is not close -> update, render
    while (!display.closeDisplay()) {
      update();
      render();
      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
    }
    display.destroyFree();
  }

  private void update() {
    display.updateDisplay();
  }

  private void render() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    map.drawMap2d();
    player.drawPlayer();
    player.movePlayer(WIDTH, HEIGHT);
    player.check();
    display.swapBuffers(); 
  }

  public static void main(String[] args) {
    new Main().start();
  }


}
