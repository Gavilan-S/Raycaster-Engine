package org.example.renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayMananger {
  private String title = "Render Engine";
  private long display;

  private int resolution = 1;// 0=160*120, 1=360*240, 4*640*480
  private int width = 160*resolution, height = 120*resolution;
  private int widthTwo = width/2, heightTwo = height/2;

  private float pixelScale = 6/resolution;
  private int glWidth = (int)(width*pixelScale);
  private int glHeight = (int)(height*pixelScale);

  public int frames;
  public static long time;

  public Inputs inputs;
  
  public static float backgroundRed, backgroundGreen, backgroundBlue;


  public DisplayMananger() {
    this.glWidth= glWidth;
    this.glHeight = glHeight;
    this.resolution = resolution;
    this.pixelScale = pixelScale;
    this.title = title;
  }

  public void createDisplay() {
    if (!GLFW.glfwInit()) {
      System.err.println("Error: could not initiallize GLFW");
      System.exit(-1);
    }

    // object of class Inputs
    inputs = new Inputs();

    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

    // args4: fullscreen or other. args5: multiple monitors
    display = GLFW.glfwCreateWindow(glWidth, glHeight, title, 0, 0);

    // display is a long
    if (display == 0) {
      System.err.println("Window: could not be created");
      System.exit(-1);
    }

    // position of the window "where is the monitor
    // 1: get my monitor
    // 2: set position
    // 3: (set SwapInterval) at this moment all the render operations will be aplied to the window
    GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    GLFW.glfwSetWindowPos(display, (vidMode.width() - glWidth) / 2,  (vidMode.height() - glHeight) / 2);
    GLFW.glfwMakeContextCurrent(display);

    // garantiza que tu aplicación sepa qué funcionalidades están disponibles en el entorno de ejecución actual
    GL.createCapabilities();

    // use the getter of the keyboard, mouseMove and mouseButtons
    GLFW.glfwSetKeyCallback(display, inputs.getKeyboardCallBack());
    GLFW.glfwSetCursorPosCallback(display, inputs.getMouseMoveCallBack());
    GLFW.glfwSetMouseButtonCallback(display, inputs.getMouseButtonsCallBack());

    // show the window it open and close at this point
    GLFW.glfwShowWindow(display);

    // frame rate: 1 = 60 fps
    GLFW.glfwSwapInterval(1);
    // get the time
    time = System.currentTimeMillis();

    // create OpenGl
    GL.createCapabilities();

    // draw cords 
    GL11.glViewport(0, 0, glWidth, glHeight);

    // 1. how to proyect cords
    // 2. new []
    // 3. change 0,0
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glPointSize(pixelScale);
    GL11.glOrtho(0, glWidth, 0, glHeight, -1, 1); // Invertir el eje Y para que (0,0) esté en la esquina inferior izquierda

  }

  // method to know when windowShouldClose for a loop
  public boolean closeDisplay() {
    return GLFW.glfwWindowShouldClose(display);
  }

  // to set on ESP close the window
  public void destroyFree() {
    inputs.destroyFree();
    GLFW.glfwWindowShouldClose(display);
    GLFW.glfwDestroyWindow(display);
    GLFW.glfwTerminate();
  }

  public void setBackgroundColor(float red, float green, float blue) {
    backgroundRed = red;
    backgroundGreen = green;
    backgroundBlue = blue;
  }


  public void updateDisplay() {
    // color on screeen (0.0 to 1.0. GlCrear use GlCrearColor to set the color
    GL11.glClearColor(backgroundRed, backgroundGreen, backgroundBlue, 1.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

    // interración user/window
    GLFW.glfwPollEvents();

    // start to add frames and if there is more tham 1000 millis print frames
    frames++;
    if (System.currentTimeMillis() > time + 1000) {
      GLFW.glfwSetWindowTitle(display, title + " | FPS: " + frames);
      time = System.currentTimeMillis();
      frames = 0;
    }
  }

  public void swapBuffers() {
    // el contenido del buffer oculto se intercambia con el buffer visible, imagen más suave
    GLFW.glfwSwapBuffers(display);

  }

  public long getDisplay() {
    return display;
  }

  public int getScreenWidth() {
    return width;
  }

  public int getScreenHeight() {
    return height;
  }

  public int getScreenGLWidth() {
    return glWidth;
  }

  public int getScreenGLHeight() {
    return glHeight;
  }

  public int getScreenWidthTwo() {
    return widthTwo;
  }

  public int getScreenHeightTwo() {
    return heightTwo;
  }

  public int getResolution() {
    return resolution;
  }
  
  public double getPixelScale() {
    return pixelScale;
  }

}
