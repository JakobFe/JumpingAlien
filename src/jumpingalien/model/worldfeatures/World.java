package jumpingalien.model.worldfeatures;

import java.util.HashSet;

import jumpingalien.model.exceptions.*;
import jumpingalien.model.gameobjects.*;
import jumpingalien.model.gameobjects.Character;
import be.kuleuven.cs.som.annotate.*;
import static jumpingalien.tests.util.TestUtils.intArray;

/**
 * A class concerning the world in which the game is played.
 * 
 * @invar	...
 * 			| hasProperGameObjects()
 * @invar	...
 * 			| hasProperTiles()
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
	
	public Tile getTileAtTilePos(int tileXPos, int tileYPos){
		if(tileXPos >= worldTiles[0].length)
			tileXPos = (getWorldWidth()-1)/getTileSize();
		if(tileYPos >= worldTiles.length)
			tileYPos = (getWorldHeight()-1)/getTileSize();
		return worldTiles[tileYPos][tileXPos];
	}
	
	public Tile getTileAtPos(int xPos, int yPos){
		return getTileAtTilePos(xPos/getTileSize(),yPos/getTileSize());
	}
	
	public boolean hasAsTile(Tile tile){
		return true;
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
	
	private boolean canHaveAsMazub(Mazub alien){
		return (alien == null) || (!alien.isDead() && canAddGameObjects());
	}
	
	public void setMazub(Mazub alien){
		if (getMazub() != null){
			getMazub().setWorld(null);
			getAllGameObjects().remove(getMazub());
		}
		/*if(canHaveAsMazub(alien))
			this.mazub = alien;*/
		this.mazub = alien;
		if (alien != null){
			getMazub().setWorld(this);
		}
	}
	
	private Mazub mazub;
	
	
	public HashSet<Plant> getAllPlants() {
		return allPlants;
	}
	
	public HashSet<Plant> getAllUnterminatedPlants(){
		HashSet<Plant> result = new HashSet<Plant>();
		for(Plant plant: allPlants){
			if(!plant.isTerminated())
				result.add(plant);
		}	
		return result;	
	}
	
	public boolean hasAsPlant(Plant plant){
		return getAllPlants().contains(plant);
	}
	
	private boolean hasProperPlants(){
		for(Plant plant: allPlants){
			if(plant.getWorld() != this)
				return false;
		}
		return true;
	}
	
	public void addAsPlant(Plant plant){
		if(canAddGameObjects()){
			allPlants.add(plant);
			plant.setWorld(this);
		}
	}
	
	public void removeAsPlant(Plant plant){
		assert hasAsPlant(plant);
		allPlants.remove(plant);
	}

	private final HashSet<Plant> allPlants = new HashSet<Plant>();

	public HashSet<Slime> getAllSlimes() {
		return allSlimes;
	}
	
	public HashSet<Slime> getAllUnterminatedSlimes(){
		HashSet<Slime> result = new HashSet<Slime>();
		for(Slime slime: getAllSlimes()){
			if(!slime.isTerminated())
				result.add(slime);
		}	
		return result;	
	}
	
	public boolean hasAsSlime(Slime slime){
		return getAllSlimes().contains(slime);
	}
	
	private boolean hasProperSlimes(){
		for(Slime slime: allSlimes){
			if(slime.getWorld() != this)
				return false;
		}
		return true;
	}
	
	public void addAsSlime(Slime slime){
		if(canAddGameObjects()){
			getAllSlimes().add(slime);
			slime.setWorld(this);
		}
	}
	
	public void removeAsSlime(Slime slime){
		assert hasAsSlime(slime);
		allSlimes.remove(slime);
	}

	private final HashSet<Slime> allSlimes = new HashSet<Slime>();
	
	public HashSet<Shark> getAllSharks(){
		return allSharks;
	}
	
	public HashSet<Shark> getAllUnterminatedSharks(){
		HashSet<Shark> result = new HashSet<Shark>();
		for(Shark shark: allSharks){
			if(!shark.isTerminated())
				result.add(shark);
		}	
		return result;	
	}
	
	
	public boolean hasAsShark(Shark shark){
		return allSharks.contains(shark);
	}
	
	private boolean hasProperScharks(){
		for(Shark shark: allSharks){
			if(shark.getWorld() != this)
				return false;
		}
		return true;
	}
	
	public void addAsShark(Shark shark){
		if(canAddGameObjects()){
			getAllSharks().add(shark);
			shark.setWorld(this);
		}
	}
	
	public void removeAsShark(Shark shark){
		assert hasAsShark(shark);
		getAllSharks().remove(shark);
	}
	
	private final HashSet<Shark> allSharks = new HashSet<Shark>();
	
	private boolean canAddGameObjects(){
		return true;
	}
	
	public HashSet<GameObject> getAllGameObjects(){
		HashSet<GameObject> result = new HashSet<GameObject>();
		result.addAll(allSharks);
		result.addAll(allPlants);
		result.addAll(allSlimes);
		result.add(getMazub());
		return result;
	}
	
	private boolean hasProperGameObjects(){
		HashSet<GameObject> allGameObjects = getAllGameObjects();
		if(getMazub()!= null && allGameObjects.size() > 100)
			return false;
		else if(allGameObjects.size()>101)
			return false;
		else{
			for(GameObject gameObject: allGameObjects){
				if(gameObject.getWorld() != this)
					return false;
			}
		}
		return true;
			
	}
	
	public HashSet<Character> getAllCharacters(){
		HashSet<Character> result = new HashSet<Character>();
		result.addAll(allSharks);
		result.addAll(allSlimes);
		result.add(getMazub());
		return result;
	}
	
	
	
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
			getMazub().advanceTime(timeDuration);
			updateWindowPos();
		}
		for(Plant plant: getAllUnterminatedPlants()){
			if(!plant.isTerminated())
				plant.advanceTime(timeDuration);
		}
		for(Shark shark: getAllUnterminatedSharks()){
			if(!shark.isTerminated())
				shark.advanceTime(timeDuration);
		}
		for(Slime slime: getAllUnterminatedSlimes()){
			if(!slime.isTerminated())
				slime.advanceTime(timeDuration);
		}
	}
	
	private void updateWindowPos(){
		if(getMazub() != null){
			setWindowXPos(getMazub().getPosition().getDisplayedXPosition()-
						  (getVisibleWindowWidth()-getMazub().getWidth())/2);
			setWindowYPos(getMazub().getPosition().getDisplayedYPosition()-
						  (getVisibleWindowHeight()-getMazub().getHeight())/2);
		}
	}
	
	@Override
	public String toString(){
		return "world with dimensions " + getWorldWidth() + "," + getWorldHeight() + ".";
	}
}
