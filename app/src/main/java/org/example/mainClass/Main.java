package org.example.mainClass;

import org.example.map.Map;
import org.example.map.MapRender;
import org.example.player.Player;
import org.example.displayConfig.DisplayManager;
import org.example.renderEngine.Inputs;
import org.example.renderEngine.RayCastingSystem;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL11;

// basic stuff: start (for thread), init (create window) <- run <- (init, update, render)

public class Main implements Runnable {
  private static DisplayManager displayRayCast2D;
  private static int displayRayCast2dWidth = 1024, displayRayCast2dHeight = 680;
  private static String displayRayCast2dTitle = "RayCast2D";

  private static DisplayManager displayGame;
  private static int displayGameWidth = 1800, displayGameHeight = 800;
  private static String displayGameTitle = "Game";

  private Player player;
  private RayCastingSystem rayCastingSystem;

	private Map map;
	private MapRender mapRender;


  // Thread help us to run the same code at the same time
  public Thread threadOne;


  public void start() {
    // create the thread
    // the thread use run cause main implements runnable
    threadOne = new Thread(this, "first thread");
    threadOne.start();

		this.map = new Map();
		mapRender = new MapRender(map);

    this.player = new Player();
    this.rayCastingSystem = new RayCastingSystem(player, map);
  }

  public void init() {
    System.out.println("init new game: 2 windows");
    // crate the window with DisplayMananger class

    displayRayCast2D = new DisplayManager(displayRayCast2dWidth, displayRayCast2dHeight, displayRayCast2dTitle);
    displayRayCast2D.createDisplay();
    
    displayGame = new DisplayManager(displayGameWidth, displayGameHeight, displayGameTitle);
    displayGame.createDisplay();
  }

  public void run() {
    init();
    System.out.println("runnig new game");
    // while is not close -> update, render
    while (!displayRayCast2D.closeDisplay() || !displayGame.closeDisplay()) {
      update();
      render();
      // exit while if ESCAPE is press
      if (Inputs.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
    }
    displayRayCast2D.destroyFree();
    displayGame.destroyFree();
  }

  private void update() {
    displayRayCast2D.updateDisplay();
    displayGame.updateDisplay();

    // No need to call player.movePlayer() here as it's likely handled elsewhere
    rayCastingSystem.update();
  }

  private void render() {
    // render 2D
    GLFW.glfwMakeContextCurrent(displayRayCast2D.getDisplay());
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		mapRender.render();

    player.movePlayer();
    player.drawPlayer();
    rayCastingSystem.render();

    displayRayCast2D.swapBuffers(); 

    // reder 3d
    GLFW.glfwMakeContextCurrent(displayGame.getDisplay());
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    rayCastingSystem.renderTest();

    displayGame.swapBuffers(); 
  }
  
  public static void main(String[] args) {
    new Main().start();
  }

  public static int getRayCast2dDisplayWidth() { return displayRayCast2dWidth; }
  public static int getRayCast2dDisplayHeight() { return displayRayCast2dHeight; }
  public static String getRayCastDisplayTitle() { return displayRayCast2dTitle; }

  public static int getGameDisplayWidth() { return displayGameWidth; }
  public static int getGameDisplayHeight() { return displayGameHeight; }
  public static String getGameDisplayTitle() { return displayGameTitle; }

}
