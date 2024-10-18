package org.example.renderEngine;

import java.util.ArrayList;
import java.util.List;

import org.example.map.MapSectors;
import org.example.player.Player;
import org.example.player.ViewPlayerConfiguration;

public class RayCastingSystem {
  private Player player;
  private MapSectors map;
  private ViewPlayerConfiguration viewPlayerConfig;
  private RayCast rayCast;
  private RayRender rayRender;
  private List<Ray> currentRays;

  public RayCastingSystem(Player player, MapSectors map) {
    this.player = player;
    this.map = map;
    this.viewPlayerConfig = new ViewPlayerConfiguration(100, 60.0f);
    this.rayCast = new RayCast(player, map, viewPlayerConfig);
    this.rayRender = new RayRender();
    this.currentRays = new ArrayList<>();
  }

  public void update() {
    // No need to call player.movePlayer() here as it's likely handled elsewhere
    currentRays = rayCast.castRays();
  }

  public void render() {
    rayRender.renderRays(currentRays);
  }
}

