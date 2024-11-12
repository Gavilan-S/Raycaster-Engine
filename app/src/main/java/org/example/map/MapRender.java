package org.example.map;

import static org.lwjgl.opengl.GL11.*;

import org.example.mainClass.Main;

public class MapRender {
	private Map map;
	private static float scale = 100;
	private boolean showGrid;


	public MapRender(Map map) {
		this.map = map;
		this.showGrid = true;
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

		for (float gridX = 0; gridX <= Main.getRayCast2dDisplayWidth(); gridX += scale) {
			glVertex2f(gridX, 0);
			glVertex2f(gridX, Main.getRayCast2dDisplayHeight());
		}

		for (float gridY = 0; gridY <= Main.getRayCast2dDisplayHeight(); gridY += scale) {
			glVertex2f(0, gridY);
			glVertex2f(Main.getRayCast2dDisplayWidth(), gridY);
		}
		glEnd();
	}

  public static float getScale() { return scale; }
}
