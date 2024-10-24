package org.example.map;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Map {
	private List<Sector> sectors;
  // sectors: <id> <index> <nWalls> <floor> <ceiling>
	private Sector sectorZero = new Sector(0, 0, 0, 5);
	private Sector sectorOne = new Sector(1, 8, 1, 2);
	private Sector sectorTwo = new Sector(2, 11, 0.2, 6);


  public Map() {
    sectors = new ArrayList<>();
		mapBuilder();
  }

	private void mapBuilder() {
		sectorZero.addWall(new Wall(2, 1, 3, 1, -1));
		sectorZero.addWall(new Wall(3, 1, 4, 2, -1));
		sectorZero.addWall(new Wall(4, 2, 4, 3, 2));
		sectorZero.addWall(new Wall(3, 4, 4, 3, -1));
		sectorZero.addWall(new Wall(2, 4, 3, 4, -1));
		sectorZero.addWall(new Wall(1, 3, 2, 4, 1));
		sectorZero.addWall(new Wall(1, 2, 1, 3, -1));
		sectorZero.addWall(new Wall(1, 2, 2, 1, -1));

		sectorOne.addWall(new Wall(1, 3, 2, 4, 0));
		sectorOne.addWall(new Wall(0.5, 4.5, 2, 4, -1));
		sectorOne.addWall(new Wall(0.5, 4.5, 1, 3, -1));

		sectorTwo.addWall(new Wall(4, 2, 8, 2, -1));
		sectorTwo.addWall(new Wall(8, 2, 8, 3, -1));
		sectorTwo.addWall(new Wall(4, 3, 8, 3, -1));
		sectorTwo.addWall(new Wall(4, 2, 4, 3, 0));

		addSector(sectorZero);
		addSector(sectorOne);
		addSector(sectorTwo);
	}


	private boolean hasSector(int sectorId) {
		for (Sector sector : sectors) {
			if (sector.getId() == sectorId) {
				return true;
			}
		}
		return false;
	}

	public Sector getSector(int sectorId) {
		for (Sector sector : sectors) {
			if (sector.getId() == sectorId) {
				return sector;
			}
		}
		throw new IllegalArgumentException("Sector " + sectorId + " no encontrado");
	}
	public List<Sector> getConnectedSectors(int sectorId) {
		List<Sector> connected = new ArrayList<>();
		Sector sector = getSector(sectorId);
		for (Wall wall : sector.getWalls()) {
			int connectedId = wall.getConnectedSector();
			if (connectedId != -1 && hasSector(connectedId)) {
				connected.add(getSector(connectedId));
			}
		}
		return connected;
	}

	public List<Sector> getAllSectors() { return new ArrayList<>(sectors); }
	public int getSectorCount() { return sectors.size(); }

	private void addSector(Sector sector) { sectors.add(sector); }
}

