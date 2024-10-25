package org.example.map;

import java.util.ArrayList;
import java.util.List;

public class Sector {
  private int id;
    private int index;
    private double floorHeight;
    private double ceilingHeight;
    private List<Wall> walls;

    public Sector(int id, int index, double floorHeight, double ceilingHeight) {
      this.id = id;
      this.index = index;
      this.floorHeight = floorHeight;
      this.ceilingHeight = ceilingHeight;
      this.walls = new ArrayList<>();
    }

    public void addWall(Wall wall) { walls.add(wall); }

    public List<Wall> getWalls() { return walls; }
    public int getId() { return id; }
    public int getIndex() { return index; }
    public double getFloorHeight() { return floorHeight; }
    public double getCeilingHeight() { return ceilingHeight; }
}

