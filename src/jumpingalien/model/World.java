package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

public class World {
	
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight,
			int targetTileX,int targetTileY){
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
		this.targetTile = new Tile(this,targetTileX*tileSize,targetTileY*tileSize,true);
		this.worldTiles = new Tile[nbTilesY][nbTilesX];
		for (int row = 0; row < nbTilesY; row++){
			for (int col = 0; col < nbTilesX; col++){
				if (row == targetTileY && col == targetTileX )
					worldTiles[row][col] = getTargetTile();
				else
					worldTiles[row][col] = new Tile(this, row*tileSize, col*tileSize,false);
			}
		}
		
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
	
	@Basic
	public Tile[][] getWorldTiles() {
		return worldTiles;
	}
	
	public Tile getTileAt(int tileXPos, int tileYPos){
		return getWorldTiles()[tileXPos][tileYPos];
	}
	
	private final Tile[][] worldTiles;
	
	
	
	public Tile getTargetTile() {
		return targetTile;
	}

	private final Tile targetTile;
	
	public int getWindowXPos() {
		return windowXPos;
	}

	public void setWindowXPos(int windowXPos) {
		this.windowXPos = windowXPos;
	}

	private int windowXPos;
	
	public int getWindowYPos() {
		return windowYPos;
	}

	public void setWindowYPos(int windowYPos) {
		this.windowYPos = windowYPos;
	}

	private int windowYPos;
}
