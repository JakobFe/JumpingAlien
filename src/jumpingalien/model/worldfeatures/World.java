package jumpingalien.model.worldfeatures;

import java.util.HashSet;

import jumpingalien.model.exceptions.*;
import jumpingalien.model.gameobjects.*;
import be.kuleuven.cs.som.annotate.*;
import static jumpingalien.tests.util.TestUtils.intArray;

/**
 * A class concerning the world in which the game is played.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 1.0
 */
public class World {
	
	/**
	 * Create a new world with the given tile size, number of tiles in horizontal and vertical
	 * direction, window width and height, horizontal and vertical tile position of the target tile.
	 * 
	 * @param 	tileSize
	 * 			The given tile size by which the game world should be initialized.
	 * @param 	nbTilesX
	 * 			The number of tiles in x-direction or horizontal direction.
	 * @param 	nbTilesY
	 * 			The number of tiles in y-direction or vertical direction.
	 * @param 	visibleWindowWidth
	 * 			The width of the displayed window of the game world.
	 * @param 	visibleWindowHeight
	 * 			The height of the displayed window of the game world.
	 * @param 	targetTileX
	 * 			The tile position in x-direction of the target tile.
	 * @param 	targetTileY
	 * 			The tile position in y-direction of the target tile.
	 * 
	 */
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
		if (alien != null)
			getMazub().setWorld(this);
	}
	
	private Mazub mazub;
	
	
	public HashSet<Plant> getAllPlants() {
		return allPlants;
	}
	
	public HashSet<Plant> getAllUnterminatedPlants(){
		HashSet<Plant> result = new HashSet<Plant>();
		for(Plant plant: getAllPlants()){
			if(!plant.isTerminated())
				result.add(plant);
		}	
		return result;	
	}
	
	public boolean hasAsPlant(Plant plant){
		return getAllPlants().contains(plant);
	}
	
	public void addAsPlant(Plant plant){
		getAllPlants().add(plant);
		plant.setWorld(this);
	}
	
	public void removeAsPlant(Plant plant){
		assert hasAsPlant(plant);
		allPlants.remove(plant);
	}

	private final HashSet<Plant> allPlants = new HashSet<Plant>();
	
	public HashSet<Shark> getAllSharks(){
		return allSharks;
	}
	
	public boolean hasAsShark(Shark shark){
		return allSharks.contains(shark);
	}
	
	public void addAsShark(Shark shark){
		getAllSharks().add(shark);
		shark.setWorld(this);
	}
	
	public void removeAsShark(Shark shark){
		assert hasAsShark(shark);
		getAllSharks().remove(shark);
	}
	
	private final HashSet<Shark> allSharks = new HashSet<Shark>();
	
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
		if (getMazub() == null || getMazub().isTerminated() || didPlayerWin())
			return true;
		else
			return false;
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
		if (getMazub() != null){
			double tdHor = 0.01/(Math.abs(getMazub().getHorVelocity())
					+Math.abs(getMazub().getHorAcceleration())*timeDuration);
			double tdVert = 0.01/(Math.abs(getMazub().getVertVelocity())
					+Math.abs(getMazub().getVertAcceleration())*timeDuration);
			double td = Math.min(tdHor, tdVert);
			if (td > timeDuration)
				td = timeDuration;
			for (int index = 0; index < timeDuration/td; index++){
				try {
					getMazub().advanceTime(td);
				} catch (NullPointerException e) {
					System.out.println("null pointer advance time world");
				}
			}
			if (getMazub() != null)
				updateWindowPos();
		}
		for(Plant plant: getAllUnterminatedPlants()){
			if(!plant.isTerminated())
				plant.advanceTime(timeDuration);
		}
		for(Shark shark: getAllSharks()){
			if(!shark.isTerminated())
				shark.advanceTime(timeDuration);
		}
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
