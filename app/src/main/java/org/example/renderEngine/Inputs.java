package org.example.renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Inputs {
  // save on arrays or variables
  private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
  private static double mouseX, mouseY;
  private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

  // start the input callbacks
  private GLFWKeyCallback keyboard;
  private GLFWCursorPosCallback mouseMove;
  private GLFWMouseButtonCallback mouseButtons;

  // method to know if player used keyboard, mouse or mouseButtons 
  public Inputs() {
    keyboard = new GLFWKeyCallback() {
      //mods = Ctrl, Shift
      public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = (action != GLFW.GLFW_RELEASE);
      }
    };

    mouseMove = new GLFWCursorPosCallback () {
      public void invoke(long window, double xpos, double ypos) {
        mouseX = xpos;
        mouseY = ypos;
      }
    };

    mouseButtons = new GLFWMouseButtonCallback () {
      public void invoke(long window, int button, int action, int mods) {
        buttons[button] = (action != GLFW.GLFW_RELEASE);
      }
    };
  }
  
  // are inputs begin used?
  public static boolean isKeyDown(int key) {
    return keys[key];
  }

  public static boolean isButtonDown(int button) {
    return buttons[button];
  }

  // no more callbacks
  public void destroyFree() {
    keyboard.free();
    mouseMove.free();
    mouseButtons.free();
  }

  public static double getMouseX() {
    return mouseX;
  }

  public static double getMouseY() {
    return mouseY;
  }

  public GLFWKeyCallback getKeyboardCallBack() {
    return keyboard;
  }

  public GLFWCursorPosCallback getMouseMoveCallBack() {
    return mouseMove;
  }

  public GLFWMouseButtonCallback getMouseButtonsCallBack() {
    return mouseButtons;
  }

}