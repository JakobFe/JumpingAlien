package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class World {
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		this.tileSize = tileSize;
		this.worldWidth = tileSize * nbTilesX;
		this.worldHeight = tileSize * nbTilesY;
		if (isValidVisibleWindowWidth(visibleWindowWidth))
			this.visibleWindowWidth = visibleWindowWidth;
		else
			this.visibleWindowWidth = getWorldWidth();
		if (isValidVisibleWindowHeight(visibleWindowHeight))
			this.visibleWindowHeight = visibleWindowHeight;
		else
			this.visibleWindowHeight = getWorldHeight();
	}
	
	/**
	 * Return the size of the tiles.
	 */
	@Basic@Immutable
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * A variable storing the size of the square tiles.
	 */
	private final int tileSize;
	
	public int getWorldWidth() {
		return worldWidth;
	}

	private final int worldWidth;
	
	public int getWorldHeight() {
		return worldHeight;
	}

	private final int worldHeight;
	
	public int getVisibleWindowWidth() {
		return visibleWindowWidth;
	}
	
	private boolean isValidVisibleWindowWidth(int width){
		return (width <= getWorldWidth());
	}

	private final int visibleWindowWidth;
	
	public int getVisibleWindowHeight() {
		return visibleWindowHeight;
	}

	private boolean isValidVisibleWindowHeight(int height){
		return (height <= getWorldHeight());
	}
	
	private final int visibleWindowHeight;
	
	
}
