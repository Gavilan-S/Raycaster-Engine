package org.example.renderEngine;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

public class RayRender {
    public void renderRays(List<Ray> rays) {
        for (Ray ray : rays) {
            glColor3f(1.0f, 1.0f, 1.0f);
            glLineWidth(1);
            glBegin(GL_LINES);
            glVertex2d(ray.getRayStartX(), ray.getRayStartY());
            glVertex2d(ray.getRayEndX(), ray.getRayEndY());
            glEnd();
        }
    }
}
