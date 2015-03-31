package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import static jumpingalien.tests.util.TestUtils.intArray;

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
					worldTiles[row][col] = new Tile(this, col*tileSize, row*tileSize,false);
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
	
	public int getBelongingTileXPosition(int xPosition){
		return xPosition/getTileSize();
	}

	public int getBelongingTileYPosition(int yPosition){
		return yPosition/getTileSize();
	}
	
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		int firstTileXPos = getBelongingTileXPosition(pixelLeft);
		int firstTileYPos = getBelongingTileYPosition(pixelBottom);
		int lastTileXPos = getBelongingTileXPosition(pixelRight);
		int lastTileYPos = getBelongingTileYPosition(pixelTop);
		int numberHorTiles = (lastTileXPos-firstTileXPos+1);
		int numberVerTiles = (lastTileYPos-firstTileYPos+1);
		int[][] result = new int[numberHorTiles * numberVerTiles][2];
		for (int index = 0; index < (numberHorTiles * numberVerTiles); index++){
			result[index] = intArray(index%numberHorTiles+firstTileXPos
					,index/numberHorTiles + firstTileYPos);
		}
		return result;
	}

	
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
	
	public Tile getTileAtTilePos(int tileXPos, int tileYPos){
		return getWorldTiles()[tileYPos][tileXPos];
	}
	
	public Tile getTileAtPos(int xPos, int yPos){
		return getTileAtTilePos(yPos/getTileSize(),xPos/getTileSize());
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
	
	public Mazub getMazub() {
		return mazub;
	}
	
	public void setMazub(Mazub alien){
		this.mazub = alien;
	}
	
	private Mazub mazub;
	
	public void advanceTime(double timeDuration) throws
	IllegalXPositionException,IllegalYPositionException{
		getMazub().advanceTime(timeDuration);
	}
}
