package org.example.mainClass;

import org.example.player.Player;
import org.example.renderEngine.DisplayMananger;
import org.example.renderEngine.Inputs;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private final int WIDTHGAME = 1024, HEIGHTGAME = 576;

  private DisplayMananger displayGame;
  private Player player;

  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

    this.player = new Player(70, 110, 20, 0, 0);
  }

  public void init() {
    System.out.println("init new game");
    // crate the window with DisplayMananger class
    // displayRayCast2D = new DisplayMananger(WIDTHRAYCAST2D, HEIGHTRAYCAST2D, "RayCasting2D");
    // displayRayCast2D.createDisplay();

    displayGame = new DisplayMananger(WIDTHGAME, HEIGHTGAME, "Game");
    displayGame.createDisplay();
  }

  public void run() {
    init();
    System.out.println("runnig new game");
    // while is not close -> update, render
    while (!displayGame.closeDisplay()) {
      update();
      render();
      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
    }
    displayGame.destroyFree();
  }

  private void update() {
    displayGame.updateDisplay();
  }

  private void render() {
    // render 2D
    GLFW.glfwMakeContextCurrent(displayGame.getDisplay());
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    displayGame.swapBuffers(); 

    // render 3D
    //GLFW.glfwMakeContextCurrent(displayGame3D.getDisplay());
    // GL11.glClearColor(0.48f, 0.50f, 0.52f, 1.0f);
    // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

  }

  public static void main(String[] args) {
    new Main().start();
  }
}
