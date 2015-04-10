package jumpingalien.model;

import java.util.HashSet;

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
		MAX_WINDOW_X_POS = getWorldWidth()-getVisibleWindowWidth()-1; 
		MAX_WINDOW_Y_POS = getWorldHeight()-getVisibleWindowHeight()-1;
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
	
	public HashSet<Tile> getTilesIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		int[][] tilePositions = getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
		HashSet<Tile> result = new HashSet<Tile>();
		for(int index=0;index<tilePositions.length;index++){
			result.add(getTileAtTilePos(tilePositions[index][0], tilePositions[index][1]));
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
		return (width <= getWorldWidth() && width>
				2*MIN_BORDER_DISTANCE);
	}

	private final int visibleWindowWidth;
	
	public int getVisibleWindowHeight() {
		return visibleWindowHeight;
	}

	private boolean isValidVisibleWindowHeight(int height){
		return (height <= getWorldHeight() && height> 
			   (2*MIN_BORDER_DISTANCE));
	}
	
	private final int visibleWindowHeight;
	
	private final int MIN_BORDER_DISTANCE = 200;
	
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
	
	public HashSet<Tile> getImpassableTiles() {
		return impassableTiles;
	}
	
	private static boolean isValidImpassableTile(Tile tile){
		return (!tile.getGeoFeature().isPassable());
	}
	
	public void addAsImpassableTile(Tile tile){
		assert(isValidImpassableTile(tile));
		this.impassableTiles.add(tile);
	}
	
	public boolean hasAsImpassableTile(Tile tile){
		return getImpassableTiles().contains(tile);
	}

	private final HashSet<Tile> impassableTiles = new HashSet<Tile>();
	
	public Tile getTargetTile() {
		return targetTile;
	}

	private final Tile targetTile;
	
	public int getWindowXPos() {
		return windowXPos;
	}

	public void setWindowXPos(int windowXPos) {
		if (windowXPos < 0)
			this.windowXPos = 0;
		else if (windowXPos > getMaxWindowXPos())
			this.windowXPos = getMaxWindowXPos();
		else
			this.windowXPos = windowXPos;
	}

	private int windowXPos;
	
	public int getWindowYPos() {
		return windowYPos;
	}

	public void setWindowYPos(int windowYPos) {
		if (windowYPos < 0)
			this.windowYPos = 0;
		else if (windowYPos > getMaxWindowYPos())
			this.windowYPos = getMaxWindowYPos();
		else
			this.windowYPos = windowYPos;
	}

	private int windowYPos;
	
	
	public int getMaxWindowXPos() {
		return MAX_WINDOW_X_POS;
	}

	private final int MAX_WINDOW_X_POS;
	
	public int getMaxWindowYPos() {
		return MAX_WINDOW_Y_POS;
	}

	private final int MAX_WINDOW_Y_POS;
	
	public Mazub getMazub() {
		return mazub;
	}
	
	public void setMazub(Mazub alien){
		this.mazub = alien;
		getMazub().setWorld(this);
	}
	
	private Mazub mazub;
	
	public boolean didPlayerWin(){
		if (getMazub() == null)
			return false;
		HashSet<Tile> affectedTiles = getTilesIn(getMazub().getPosition().getDisplayedXPosition(),
				getMazub().getPosition().getDisplayedYPosition(),getMazub().getPosition().getDisplayedXPosition()
				+getMazub().getWidth()-1, getMazub().getPosition().getDisplayedYPosition()+getMazub().getHeight()-1);
		for(Tile tile: affectedTiles){
			if (tile == getTargetTile())
				return true;
		}
		return false;
	}
	
	public boolean isGameOver(){
		if (getMazub() == null)
			return false;
		else
			return (getMazub().isTerminated() || didPlayerWin());
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	private boolean gameStarted = true;
	
	public void advanceTime(double timeDuration) throws
	IllegalXPositionException,IllegalYPositionException{
		double tdHor = 0.01/(Math.abs(getMazub().getHorVelocity())
				+Math.abs(getMazub().getHorAcceleration())*timeDuration);
		double tdVert = 0.01/(Math.abs(getMazub().getVertVelocity())
				+Math.abs(getMazub().getVertAcceleration())*timeDuration);
		double td = Math.min(tdHor, tdVert);
		if (td > timeDuration)
			td = timeDuration;
		//System.out.println(td);
		for (int index = 0; index < timeDuration/td; index++){
			getMazub().advanceTime(td);			
		}
		//getMazub().advanceTime(timeDuration);
		updateWindowPos();
	}
	
	private void updateWindowPos(){
		setWindowXPos(getMazub().getPosition().getDisplayedXPosition()-
					  (getVisibleWindowWidth()-getMazub().getWidth())/2);
		setWindowYPos(getMazub().getPosition().getDisplayedYPosition()-
					  (getVisibleWindowHeight()-getMazub().getHeight())/2);
	}
	
	@Override
	public String toString(){
		return "world with dimensions " + getWorldWidth() + "," + getWorldHeight() + ".";
	}
}
