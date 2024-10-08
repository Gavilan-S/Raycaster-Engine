package org.example.mainClass;

import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Draw3D;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.Pixels;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {

  private DisplayMananger display;
  private Player player;
  private Pixels pixels;
  private Draw3D draw3d;

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player(70, -110, 20, 0, 0);
    this.pixels = new Pixels();
    this.draw3d = new Draw3D();
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    // displayRayCast2D = new DisplayMananger(WIDTHRAYCAST2D, HEIGHTRAYCAST2D, "RayCasting2D");
    // displayRayCast2D.createDisplay();

    display = new DisplayMananger();
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
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
        System.out.println("Exit Render Engine");
        return;
      }
    }
    display.destroyFree();
  }

  private void update() {
    display.updateDisplay();
  }

  private void render() {
    // render 2D
    GLFW.glfwMakeContextCurrent(display.getDisplay());
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    GL11.glBegin(GL11.GL_POINTS);
    pixels.clearBackground();
    draw3d.draw3d();
    GL11.glEnd();

    player.movePlayer(display.getScreenWidth(), display.getScreenHeight());

    display.swapBuffers(); 

    // render 3D
    //GLFW.glfwMakeContextCurrent(displayGame3D.getDisplay());
    // GL11.glClearColor(0.48f, 0.50f, 0.52f, 1.0f);
    // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

  }

  public static void main(String[] args) {
    new Main().start();
  }
}
