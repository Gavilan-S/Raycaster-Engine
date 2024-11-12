package org.example.renderEngine;

public class Ray {
  private double rayStartX, rayStartY, rayEndX, rayEndY;
  private double rayAngle;
  private float rayDistance;

  public Ray(double rayStartX, double rayStartY, double rayAngle) {
    this.rayStartX = rayStartX;
    this.rayStartY = rayStartY;
    this.rayAngle = rayAngle;
    this.rayDistance = 1000;
    calculateEndPoint();
  }

  private void calculateEndPoint() {
    this.rayEndX = rayStartX + Math.cos(Math.toRadians(rayAngle)) * rayDistance;
    this.rayEndY = rayStartY + Math.sin(Math.toRadians(rayAngle)) * rayDistance;
  }

  public void setCollisionPoint(double rayEndX, double rayEndY) {
    this.rayEndX = rayEndX;
    this.rayEndY = rayEndY;
  }

	public double getRayStartX() {	return rayStartX; }
	public double getRayEndY() { return rayEndY; }
	public double getRayStartY() { return rayStartY; }
	public double getRayEndX() { return rayEndX; }
	public double getRayAngle() { return rayAngle; }
	public float getRayDistance() { return rayDistance; }

  public void setRayDistance(float actualRayDistance) { this.rayDistance = actualRayDistance; }
}
