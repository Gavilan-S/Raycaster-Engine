package org.example.map;

import static org.lwjgl.opengl.GL11.*;

import org.example.displayConfig.DisplayManager;

public class MapRender {
	private Map map;
	private float scale;
	private boolean showGrid;

	private DisplayManager display;

	public MapRender(Map map, float scale) {
		this.map = map;
		this.scale = scale;
		this.showGrid = true;

		this.display = new DisplayManager();
	}

	public void render() {
		if (showGrid) {
			drawGrid();
		}

		for (Sector sector : map.getAllSectors()) {
			drawSector(sector);
		}
	}

	private void drawSector(Sector sector) {
		for (Wall wall : sector.getWalls()) {
			drawWall(wall);
		}
	}

	private void drawWall(Wall wall) {
		if (wall.getConnectedSector() != -1) {
			glColor3f(1.0f, 0.2f, 0.5f);
		} else {
			glColor3f(0.2f, 7.0f, 1.0f);
		}

		glBegin(GL_LINES);
		glVertex2d(wall.getWallX0() * scale, wall.getWallY0() * scale);
		glVertex2d(wall.getWallX1() * scale, wall.getWallY1() * scale);
		glEnd();
	}

	private void drawGrid() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBegin(GL_LINES);
		glColor4f(1.0f, 1.0f, 1.0f, 0.1f);

		for (float gridX = 0; gridX <= display.getDisplayWidth(); gridX += scale) {
			glVertex2f(gridX, 0);
			glVertex2f(gridX, display.getDisplayHeight());
		}

		for (float gridY = 0; gridY <= display.getDisplayHeight(); gridY += scale) {
			glVertex2f(0, gridY);
			glVertex2f(display.getDisplayWidth(), gridY);
		}
		glEnd();
	}

  public float getScale() { return scale; }
}
