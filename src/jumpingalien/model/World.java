package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class World {
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		this.tileSize = tileSize;
	}
	
	/**
	 * Return the size of the tiles.
	 */
	@Basic
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * A variable storing the size of the square tiles.
	 */
	private final int tileSize;
	
	
}
