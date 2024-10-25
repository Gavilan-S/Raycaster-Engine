package org.example.map;

public class Wall {
  private double wallX0, wallY0, wallX1, wallY1;
  private int connectedSector;

  public Wall(double wallX0, double wallY0, double wallX1, double wallY1, int connectedSector) {
    this.wallX0 = wallX0;
    this.wallY0 = wallY0;
    this.wallX1 = wallX1;
    this.wallY1 = wallY1;
    this.connectedSector = connectedSector;
  }

  public double getWallX0() { return wallX0; }
  public double getWallY0() { return wallY0; }
  public double getWallX1() { return wallX1; }
  public double getWallY1() { return wallY1; }
  public int getConnectedSector() { return connectedSector; }
}
