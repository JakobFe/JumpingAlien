package jumpingalien.model.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jumpingalien.model.exceptions.*;
import be.kuleuven.cs.som.annotate.*;
import static jumpingalien.tests.util.TestUtils.intArray;

/**
 * A class concerning the world in which the game is played.
 * 
 * @invar	...
 * 			| hasProperTiles()
 * @invar	...
 * 			| hasProperGameObjects()
 * @invar	...
 * 			| hasProperSchools()
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version 3.0
 */
public class World {
	
	/**
	 * Create a new world with the given tile size, number of tiles in horizontal and vertical
	 * direction, window width and height and horizontal and vertical tile position of the target tile.
	 * 
	 * @param 	tileSize
	 * 			The given tile size by which the game world should be initialized.
	 * @param 	nbTilesX
	 * 			The number of tiles in x-direction (horizontal direction).
	 * @param 	nbTilesY
	 * 			The number of tiles in y-direction (vertical direction).
	 * @param 	visibleWindowWidth
	 * 			The width of the displayed window of the game world.
	 * @param 	visibleWindowHeight
	 * 			The height of the displayed window of the game world.
	 * @param 	targetTileX
	 * 			The tile position in x-direction of the target tile.
	 * @param 	targetTileY
	 * 			The tile position in y-direction of the target tile.
	 * @post 	...
	 * 			| new.getTileSize() = tileSize
	 * @post	...
	 * 			| new.getWorldWidth() = tileSize * nbTilesX
	 * @post	...
	 * 			| new.getWorldHeight() = tileSize * nbTilesY
	 * @post	...
	 *			| if(canHaveAsVisibleWindowWidth(visibleWindowWidth,tileSize*nbTilesX)
	 *			|	then new.getVisibleWindowWidth() = visibleWindowWidth
	 *			| else
	 *			|	new.getVisibleWindowWidth() = tileSize*nbTilesX
	 * @post	...
	 *			| if(canHaveAsVisibleWindowHeight(visibleWindowHeight,tileSize*nbTilesY)
	 *			|	then new.getVisibleWindowHeight() = visibleWindowHeight
	 *			| else
	 *			|	new.getVisibleWindowHeight() = tileSize*nbTilesY
	 * @post	...
	 * 			| new.getMaxWindowXPos() = getWorldWidth()-getVisibleWindowWidth()
	 * @post	...
	 * 			| new.getMaxWindowYPos() = getWorldHeight()-getVisibleWindowHeight()
	 * @post	...
	 * 			| let
	 * 			|	matrixOfTiles = new Tile[nbTilesY][nbTilesX]
	 * 			| in
	 *			|	new.getWorldTiles().equals(matrixOfTiles)
	 * @post	...
	 * 			| let
	 * 			|	targetTile = new Tile(this,targetTileX*tileSize,targetTileY*tileSize,true),
	 * 			|	(for each row in 0..nbTilesY-1:
	 * 			|		(for each col in 0..nbTilesX-1:
	 * 			|			if (row == targetTileY && col == targetTileX)
	 * 			|				then tile = targetTile
	 * 			|			else tile = new Tile(this, col*tileSize, row*tileSize,false) ) )
	 * 			| in
	 * 			|	new.getTileAtTilePos(col,row).equals(tile),
	 * 			|	new.getTargetTile().equals(targetTile)
	 * @throws	IllegalArgumentException("Illegal window size!")
	 * 			...
	 * 			| !isValidVisibleWindowWidth(visibleWindowWidth) ||
	 * 			| !isValidVisibleWindowHeight(visibleWindowHeight)
	 */
	@Raw
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight,
			int targetTileX,int targetTileY) throws IllegalArgumentException{
		if (!isValidVisibleWindowWidth(visibleWindowWidth) ||
			!isValidVisibleWindowHeight(visibleWindowHeight))
			throw new IllegalArgumentException("Illegal window size!");
		if(canHaveAsVisibleWindowWidth(visibleWindowWidth, tileSize*nbTilesX))
			this.visibleWindowWidth = visibleWindowWidth;
		else
			this.visibleWindowWidth = tileSize*nbTilesX;
		if(canHaveAsVisibleWindowHeight(visibleWindowHeight, tileSize*nbTilesY))
			this.visibleWindowHeight = visibleWindowHeight;
		else
			this.visibleWindowHeight = tileSize*nbTilesY;
		this.tileSize = tileSize;
		this.worldWidth = tileSize * nbTilesX;
		this.worldHeight = tileSize * nbTilesY;
		maxWindowXPos = getWorldWidth()-getVisibleWindowWidth(); 
		maxWindowYPos = getWorldHeight()-getVisibleWindowHeight();
		this.targetTile = new Tile(this,targetTileX*tileSize,targetTileY*tileSize,true);
		this.worldTiles = new Tile[nbTilesY][nbTilesX];
		for (int row = 0; row < nbTilesY; row++){
			for (int col = 0; col < nbTilesX; col++){
				if (row == targetTileY && col == targetTileX)
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
	
	/**
	 * Return the belonging tile position of the given x position.
	 * 
	 * @param 	xPosition
	 * 			The given x position.
	 * @return	...
	 * 			| result == xPosition/getTileSize()
	 */
	@Model
	protected int getBelongingTileXPosition(int xPosition){
		return xPosition/getTileSize();
	}

	/**
	 * Return the belonging tile position of the given y position.
	 * 
	 * @param 	yPosition
	 * 			The given y position
	 * @return	...
	 * 			| result == yPosition/getTileSize()
	 */
	@Model
	protected int getBelongingTileYPosition(int yPosition){
		return yPosition/getTileSize();
	}
	
	/**
	 * Returns the target tile of this game world. 
	 * The Mazub should reach this tile in order to win the game.
	 * 
	 * @note	Although this function is public, it is for internal use only.
	 */
	public Tile getTargetTile() {
		return targetTile;
	}

	/**
	 * A variable storing the target tile.
	 */
	private final Tile targetTile;
	
	/**
	 * Returns a set of all tiles within the given rectangular region. 
	 * 
	 * @param 	pixelLeft
	 *          The x-coordinate of the left side of the rectangular region.
	 * @param 	pixelBottom
	 *          The y-coordinate of the bottom side of the rectangular region.
	 * @param 	pixelRight
	 *         	The x-coordinate of the right side of the rectangular region.
	 * @param 	pixelTop
	 *          The y-coordinate of the top side of the rectangular region.
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| let
	 * 			|	tilePositions = getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop),
	 * 			|	for each index in 0...tilePositions.length-1:
	 * 			|		tile = getTileAtTilePos(tilePositions[index][0], tilePositions[index][1])
	 *  		| in
	 * 			|	result.contains(tile)
	 */
	@Model
	protected HashSet<Tile> getTilesIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		int[][] tilePositions = getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
		HashSet<Tile> result = new HashSet<Tile>();
		for(int index=0;index<tilePositions.length;index++){
			result.add(getTileAtTilePos(tilePositions[index][0], tilePositions[index][1]));
		}
		return result;
	}
	
	/**
	 * Returns a set of all tiles in the game world.
	 * 
	 * @return	...
	 * 			| result == getTilesIn(0, 0, getWorldWidth()-1, getWorldHeight()-1)
	 * @note	Although this function is public, it is for internal use only.
	 */
	public HashSet<Tile> getAllTiles(){
		return getTilesIn(0, 0, getWorldWidth()-1, getWorldHeight()-1);
	}
	
	/**
	 * Returns a set of all impassable tiles in the game world.
	 * 
	 * @return ...
	 * 			| for each tile in result:
	 * 			|	!tile.getGeoFeature().isPassable()
	 * 
	 * @note	Although this function is public, it is for internal use only.
	 */
	public Set<Tile> getImpassableTiles() {
		Stream<Tile> stream = getAllTiles().stream();
		Stream<Tile> filteredStream = stream.filter(t-> !t.getGeoFeature().isPassable());
		return filteredStream.collect(Collectors.toSet());
	}
	
	/**
	 * Returns the tile positions of all tiles within the given rectangular region.
	 * 
	 * @param 	pixelLeft
	 *          The x-coordinate of the left side of the rectangular region.
	 * @param 	pixelBottom
	 *          The y-coordinate of the bottom side of the rectangular region.
	 * @param 	pixelRight
	 *         	The x-coordinate of the right side of the rectangular region.
	 * @param 	pixelTop
	 *          The y-coordinate of the top side of the rectangular region.
	 * @return	...
	 * 			| result.length == (getBelongingTileXPosition(pixelRight) - getBelongingTileXPosition(pixelLeft) + 1)
	 * 			|			* (getBelongingTileYPosition(pixelTop) - getBelongingTileYPosition(pixelBottom) + 1) 
	 * @return	...
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb].length == 2
	 * @return	...
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb] =
	 * 			|		intArray(rowNb%(getBelongingTileXPosition(pixelRight) - getBelongingTileXPosition(pixelLeft) + 1)
	 * 			|		+ getBelongingTileXPosition(pixelLeft),
	 * 			|				rowNb/(getBelongingTileXPosition(pixelRight) - getBelongingTileXPosition(pixelLeft) + 1)
	 * 			|		+ getBelongingTileYPosition(pixelBottom))
	 */
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
	
	/**
	 * Returns the tile at a given tile position.
	 * 
	 * @param 	tileXPos
	 * 			The given tile position in x-direction
	 * @param 	tileYPos
	 * 			The given tile position in y-direction
	 * @return	...
	 * 			| let
	 * 			|	tileXPos = Math.min(tileXPos, worldTiles[0].length-1)
	 * 			|	tileYPos = Math.min(tileYPos, worldTiles.length-1)
	 * 			| in
	 * 			|	result.getTileXPos().equals(tileXPos)
	 * 			|	result.getTileYPos().equals(tileYPos)
	 */
	public Tile getTileAtTilePos(int tileXPos, int tileYPos){
		tileXPos = Math.min(tileXPos, worldTiles[0].length-1);
		tileYPos = Math.min(tileYPos, worldTiles.length-1);
		return worldTiles[tileYPos][tileXPos];
	}
	
	/**
	 * Returns the tile at a given position.
	 * 
	 * @param 	xPos
	 * 			The given x-position
	 * @param 	yPos
	 * 			The given y-position
	 * @return	...
	 * 			| result == getTileAtTilePos(xPos/getTileSize(),yPos/getTileSize())
	 */
	public Tile getTileAtPos(int xPos, int yPos){
		return getTileAtTilePos(xPos/getTileSize(),yPos/getTileSize());
	}
	
	/**
	 * Checks whether this world has the given tile as tile or not.
	 * 
	 * @param 	tile
	 * 			The tile to check
	 * @return	...
	 * 			| result == (tile != null && worldTiles[tile.getTileXPos()][tile.getTileYPos()] == tile)
	 */
	@Model
	protected boolean hasAsTile(Tile tile){
		return (tile != null && worldTiles[tile.getTileXPos()][tile.getTileYPos()] == tile);
	}
	
	/**
	 * Check whether this world has proper tiles.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	for some tile in getAllTiles()
	 * 			|		tile.getWorld() != this
	 */
	boolean hasProperTiles(){
		for(Tile tile: getAllTiles()){
			if(tile.getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * A matrix storing the tiles of this world. 
	 * 
	 * @invar	...
	 * 			| worldTiles != null
	 * 			| for each tile in worldTiles:
	 * 			|	tile.getWorld() == this
	 */
	private final Tile[][] worldTiles;	

	/**
	 * Returns the width of the world.
	 */
	@Basic
	public int getWorldWidth() {
		return worldWidth;
	}

	/**
	 * A variable storing the width of the world.
	 */
	private final int worldWidth;
	
	/**
	 * Returns the height of the world.
	 */
	@Basic
	public int getWorldHeight() {
		return worldHeight;
	}

	/**
	 * A variable storing the height of the world.
	 */
	private final int worldHeight;
	
	/**
	 * Returns the width of the visible window.
	 */
	@Basic
	public int getVisibleWindowWidth() {
		return visibleWindowWidth;
	}

	/**
	 * Check whether the width of the visible window is valid or not.
	 * 
	 * @param 	width
	 * 			the width to check
	 * @return	...
	 * 			| result == (width>2*MIN_BORDER_DISTANCE)
	 */
	@Model
	private static boolean isValidVisibleWindowWidth(int width){
		return (width> 2*MIN_BORDER_DISTANCE);
	}
	
	/**
	 * Check whether a world with the given width can have a window with given width.
	 * 
	 * @param 	width
	 * 			The width of the window to check.
	 * @param 	worldWith
	 * 			The width of the world to check against.
	 * @return	...
	 * 			| result ==  isValidVisibleWindowWidth(width) && width<=worldWith
	 */
	@Model
	private static boolean canHaveAsVisibleWindowWidth(int width,int worldWith){
		return isValidVisibleWindowWidth(width) && width<=worldWith;
	}

	/**
	 * A variable storing the width of the visible window.
	 */
	private final int visibleWindowWidth;
	
	/**
	 * Returns the height of the visible window.
	 */
	@Basic
	public int getVisibleWindowHeight() {
		return visibleWindowHeight;
	}
	
	/**
	 * Check whether the height of the visible window is valid or not.
	 * 
	 * @param 	height
	 * 			the height to check
	 * @return	...
	 * 			| result == (height >2*MIN_BORDER_DISTANCE)
	 */
	@Model
	private static boolean isValidVisibleWindowHeight(int height){
		return (height> 2*MIN_BORDER_DISTANCE);
	}
	
	/**
	 * Check whether a world with the given height can have a window with given height.
	 * 
	 * @param 	height
	 * 			The height of the window to check.
	 * @param 	worldHeight
	 * 			The height of the world to check against.
	 * @return	...
	 * 			| result ==  isValidVisibleWindowHeight(height) && height<=worldHeight
	 */
	@Model
	private static boolean canHaveAsVisibleWindowHeight(int height,int worldHeight){
		return isValidVisibleWindowWidth(height) && height<=worldHeight;
	}
	
	/**
	 * A variable storing the height of the visible window.
	 */
	private final int visibleWindowHeight;
	
	/**
	 * A variable storing the minimum amount of pixels there should be between the Mazub
	 * and the borders of the visible window, if the Mazub isn't positioned close to the borders of the game world. 
	 */
	private final static int MIN_BORDER_DISTANCE = 225;
	
	/**
	 * Returns the x-position of the visible window.
	 */
	@Basic
	public int getWindowXPos() {
		return windowXPos;
	}

	/**
	 * Sets the x-position of the visible window to the given x-position.
	 * Like all other positions, this position should be within the borders of the game world.
	 * 
	 * @param 	windowXPos
	 * 			The given x-position
	 * @post	...
	 * 			| if (windowXPos < 0)
	 *			|	then new.getWindowXPos() = 0
	 *			| else if (windowXPos > getMaxWindowXPos())
	 *			|	then new.getWindowXPos() = getMaxWindowXPos()
	 *			| else 
	 *			|	new.getWindowXPos() = windowXPos
	 */
	@Model
	protected void setWindowXPos(int windowXPos) {
		if (windowXPos < 0)
			this.windowXPos = 0;
		else if (windowXPos > getMaxWindowXPos())
			this.windowXPos = getMaxWindowXPos();
		else
			this.windowXPos = windowXPos;
	}

	/**
	 * A variable storing the x-position of the visible window.
	 */
	private int windowXPos;
	
	/**
	 * Returns the y-position of the visible window.
	 */
	@Basic
	public int getWindowYPos() {
		return windowYPos;
	}

	/**
	 * Sets the y-position of the visible window to the given y-position.
	 * Like all other positions, this position should be within the borders of the game world.
	 * 
	 * @param 	windowYPos
	 * 			The given y-position
	 * @post	...
	 * 			| if (windowYPos < 0)
	 *			|	then new.getWindowYPos() = 0
	 *			| else if (windowYPos > getMaxWindowYPos())
	 *			|	then new.getWindowYPos() = getMaxWindowYPos()
	 *			| else 
	 *			| 	new.getWindowYPos() = windowYPos
	 */
	@Model
	protected void setWindowYPos(int windowYPos) {
		if (windowYPos < 0)
			this.windowYPos = 0;
		else if (windowYPos > getMaxWindowYPos())
			this.windowYPos = getMaxWindowYPos();
		else
			this.windowYPos = windowYPos;
	}

	/**
	 * A variable storing the x-position of the visible window.
	 */
	private int windowYPos;
	
	/**
	 * Returns the maximum x-position of the visible window.
	 */
	@Basic@Model
	protected int getMaxWindowXPos() {
		return maxWindowXPos;
	}

	/**
	 * A variable storing the maximum x-position of the visible window.
	 */
	private final int maxWindowXPos;

	/**
	 * Returns the maximum y-position of the visible window.
	 */
	@Basic@Model
	protected int getMaxWindowYPos() {
		return maxWindowYPos;
	}

	/**
	 * A variable storing the maximum y-position of the visible window.
	 */
	private final int maxWindowYPos;
	
	/**
	 * Returns the one and only Mazub of this game world.
	 * 
	 * @note	Although this function is public, it is for internal use only.
	 */
	public Mazub getMazub() {
		return mazub;
	}

	/**
	 * Checks whether this world can have the given Mazub is its Mazub.
	 * 
	 * @param 	alien
	 * 			The Mazub to check
	 * @return	...
	 * 			| result == ((alien == null) || (!alien.isDead() && canAddGameObjects()))
	 */
	@Model
	private boolean canHaveAsMazub(Mazub alien){
		return (alien == null) || (!alien.isDead() && canAddGameObjects());
	}
	
	/**
	 * Sets the Mazub of this game world to the given Mazub.
	 * 
	 * @param 	alien
	 * 			The Mazub to set
	 * @effect	...
	 * 			| if (getMazub() != null)
	 *			|	then getMazub().setWorld(null), getAllGameObjects().remove(getMazub())
	 * @post	...
	 * 			| if(canHaveAsMazub(alien))
	 *			|	then new.getMazub() = alien
	 * @effect	...
	 * 			| if (alien != null)
	 * 			|	then getMazub().setWorld(this)
	 */
	public void setMazub(Mazub alien){
		if (getMazub() != null){
			getMazub().setWorld(null);
			getAllGameObjects().remove(getMazub());
		}
		if(canHaveAsMazub(alien)){
			this.mazub = alien;
			addAsGameObject(alien);
		}
		if (alien != null){
			getMazub().setWorld(this);
		}
	}
	
	/**
	 * A variable storing the Mazub of this game world.
	 */
	private Mazub mazub;
	
	/**
	 * Returns a set of all game objects attached to this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each object in GameObject:
	 * 			|	result.contains(object) == (object.getWorld() == this)
	 * @note	Although this function is public, it is for internal use only.
	 */
	@Basic
	public HashSet<GameObject> getAllGameObjects(){
		return allGameObjects;
	}
	
	/**
	 * Checks whether game objects can still be added.
	 * 
	 * @return	...
	 * 			| result == !isGameStarted()
	 */
	@Model
	boolean canAddGameObjects(){
		return !isGameStarted();
	}
	
	/**
	 * Checks whether the given game object is effective.
	 * 
	 * @param 	object
	 * 			The object to check
	 * @return	...
	 * 			| result == object != null
	 */
	@Model
	static boolean isValidGameObject(GameObject object){
		return object != null;
	}
	
	/**
	 * Check whether this world can have the given object as one of its game objects.
	 * 
	 * @param 	object
	 * 			The game object to check.
	 * @return	...
	 * 			| if(!isValidGameObject(object))
	 * 			|	then result == false
	 * 			| else if(object instanceof Slime)
	 * 			|	then result == (!object.isTerminated() && canAddGameObjects()) &&
	 * 			|		(hasAsSchool(((Slime)object).getSchool()) || canAddSchool())
	 * 			| else
	 * 			|	result == (!object.isTerminated() && canAddGameObjects())
	 */
	@Model
	protected boolean canHaveAsGameObject(GameObject object){
		if(!isValidGameObject(object))
			return false;
		else if(object instanceof Slime)
			return (!object.isTerminated() && canAddGameObjects()) &&
					((hasAsSchool(((Slime)object).getSchool()) || canAddSchool()));
		else 
			return (!object.isTerminated() && canAddGameObjects());
	}
	
	/**
	 * Check whether the given game object is located in this world.
	 * 
	 * @param 	object
	 * 			The game object to check
	 * @return	...
	 * 			| result == allGameObjects.contains(object)
	 */
	@Model
	protected boolean hasAsGameObject(GameObject object){
		return allGameObjects.contains(object);
	}
	
	/**
	 * Add the given game object to the collection of game objects in this world.
	 * 
	 * @param 	object
	 * 			The game object to add.
	 * @post	...
	 * 			| if(canAddGameObjects() && canHaveAsGameObject(object))
	 * 			|	then new.getAllGameObjects().contains(object),
	 * 			|		 (new object).getWorld() == this
	 * @effect	...
	 * 			| if(canAddGameObjects() && canHaveAsGameObject(object)) &&
	 * 			|	(object instanceof Slime)
	 * 			|	then addAsSchool(((Slime)object).getSchool())
	 */
	public void addAsGameObject(GameObject object){
		if(canAddGameObjects() && canHaveAsGameObject(object)){
			getAllGameObjects().add(object);
			object.setWorld(this);
			if(object instanceof Slime)
				addAsSchool(((Slime)object).getSchool());
		}
	}
	
	/**
	 * Remove the given game object out of the collection of game objects in this world.
	 * 
	 * @param 	object
	 * 			The game object to remove.
	 * @pre		...
	 * 			| hasAsGameObject(object)
	 * @post	...
	 * 			| !(new.getAllGameObjects().contains(object)),
	 * 			| (new object).getWorld() == null
	 * @effect	...
	 * 			| if(object instanceof Slime)
	 * 			|	then decrementValueOfSchool(((Slime)object).getSchool())
	 */
	public void removeAsGameObject(GameObject object){
		assert hasAsGameObject(object);
		getAllGameObjects().remove(object);
		object.setWorld(null);
		if(object instanceof Slime)
			decrementValueOfSchool(((Slime)object).getSchool());			
	}
	
	/**
	 * Return a set of game object that all satisfy the condition of the given predicate.
	 * 
	 * @param 	predicate
	 * 			The predicate used to filter the set of all game objects in this world.
	 * @return	...
	 * 			| for each object in getAllGameObjects():
	 * 			|	if(predicate.test(object))
	 * 			|		then result.contains(object)
	 * @note	Although this function is public, it is for internal use only.
	 */
	public Set<? extends GameObject> filterAllGameObjects(Predicate<GameObject> predicate){
		Stream<GameObject> stream = allGameObjects.stream();
		Stream<GameObject> filteredStream = stream.filter(t -> predicate.test(t));
		return filteredStream.collect(Collectors.toSet());
	}
	
	/**
	 * Return the set of all unterminated game objects in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<GameObject>)filterAllGameObjects(t-> (!t.isTerminated()))
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<GameObject> getAllUnterminatedGameObjects(){
		return (Set<GameObject>)filterAllGameObjects(t-> (!t.isTerminated())); 
	}
	
	/**
	 * Return the set of all aliens in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Alien>)filterAllGameObjects(t-> t instanceof Alien)
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<? extends Alien> getAllAliens() {
		return (Set<Alien>)filterAllGameObjects(t-> t instanceof Alien);
	}
	
	/**
	 * Return the set of all buzams in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Buzam>)filterAllGameObjects(t-> (t instanceof Buzam))
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<Buzam> getAllBuzams() {
		return (Set<Buzam>)filterAllGameObjects(t-> (t instanceof Buzam));
	}
	
	/**
	 * Return the set of all plants in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Plant>)filterAllGameObjects(t-> t instanceof Plant)	
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<Plant> getAllPlants() {
		return (Set<Plant>)filterAllGameObjects(t-> t instanceof Plant);	
	}
	
	/**
	 * Return the set of all sharks in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Shark>)filterAllGameObjects(t-> (t instanceof Shark))
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<Shark> getAllSharks(){
		return (Set<Shark>)filterAllGameObjects(t-> (t instanceof Shark));
	}
		
	/**
	 * Return the set of all slimes in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Slime>)filterAllGameObjects(t-> (t instanceof Slime))
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<Slime> getAllSlimes() {
		return (Set<Slime>)filterAllGameObjects(t-> (t instanceof Slime));
	}
	
	/**
	 * Return the set of all characters in this world.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	(Set<Character>)filterAllGameObjects(t-> (t instanceof Character))
	 * @note	Although this function is public, it is for internal use only.
	 */
	@SuppressWarnings("unchecked")
	public Set<Character> getAllCharacters() {
		return (Set<Character>)filterAllGameObjects(t-> (t instanceof Character));
	}
	
	/**
	 * Checks whether this game world has proper game objects or not.
	 * 
	 * @return	...
	 * 			| if(getMazub() == null && getAllGameObjects.size() > 100)
	 *			|	then result == false
	 *			...
	 *			| else if(getAllGameObjects.size()>101)
	 *			|	then result == false
	 *			...
	 *			| else
	 *			|	result == 
	 *			|		(for some object in getAllGameObjects():
	 *			|			!this.canHaveAsGameObject(object)))
	 */
	@Model
	private boolean hasProperGameObjects(){
		HashSet<GameObject> allGameObjects = getAllGameObjects();
		if(getMazub() == null && allGameObjects.size() > 100)
			return false;
		else if(allGameObjects.size()>101)
			return false;
		else{
			for(GameObject gameObject: allGameObjects){
				if(!this.canHaveAsGameObject(gameObject))
					return false;
			}
		}
		return true;		
	}
	
	/**
	 * A variable storing the set of all game objects that are located inside this world.
	 * 
	 * @invar	...
	 * 			| allGameObjects != null
	 * @invar	...
	 * 			| for each object in allGameObjects:
	 * 			|	this.canHaveAsGameObject(object)
	 */
	private final HashSet<GameObject> allGameObjects = new HashSet<GameObject>();
	
	/**
	 * Return the map of all schools that have or had a slime in this world, combined
	 * with the amount of slimes that each school currently has in this world.
	 */
	@Model
	private Map<School,Integer> getAllSchoolsWithValues(){
		return allSchools;
	}
	/**
	 * Returns a set of all schools in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each slime in Slime:
	 * 			|	result.contains(slime.getSchool()) == (slime.getWorld() == this)
	 * @note	Although this function is public, it is for internal use only.
	 */
	public Set<School> getAllSchools() {
		return allSchools.keySet();
	}
		
	/**
	 * Check whether this world has or had a slime with the given school.
	 * 
	 * @param 	school
	 * 			The school to check.
	 * @return	...
	 * 			| result == getAllSchoolsWithValues().containsKey(school)
	 */
	@Model
	protected boolean hasAsSchool(School school){
		return allSchools.containsKey(school);
	}
	
	/**
	 * Add the school to the map of schools.
	 * 
	 * @param 	school
	 * 			The school to be added
	 * @post	...
	 * 			| if(!hasAsSchool(school) && canAddSchool())
	 * 			|	then new.getAllSchools().contains(school)
	 * @effect	...
	 * 			| if(!hasAsSchool(school))
	 * 			|	then incrementValueOfSchool(school)			
	 */
	@Raw
	public void addAsSchool(School school){
		if(!hasAsSchool(school)){
			if(canAddSchool())
				allSchools.put(school,1);
		}
		else
			incrementValueOfSchool(school);
	}
	
	/**
	 * Remove the school from the map of schools.
	 * 
	 * @param 	school
	 * 			The school to remove.
	 * @pre		...
	 * 			| hasAsSchool(school)
	 * @pre		...
	 * 			| getAllSchoolsWithValues().get(school) == 0
	 * @post	...
	 * 			| !new.getAllSchools().contains(school)
	 */
	@Model
	protected void removeAsSchool(School school){
		assert hasAsSchool(school);
		assert allSchools.get(school) == 0;
		allSchools.remove(school);
	}
	
	/**
	 * Check whether this world can add a new school.
	 * 
	 * @return	...
	 * 			| result == numberOfSchools() < MAX_NUMBER_OF_SCHOOLS
	 */
	@Model
	protected boolean canAddSchool(){
		return (numberOfSchools() < MAX_NUMBER_OF_SCHOOLS);
	}
	/**
	 * Return the amount of distinct schools in this world.
	 * 
	 * @return	...
	 * 			| result == getAllSchools().size()
	 */
	@Model
	protected int numberOfSchools(){
		return allSchools.size();
	}
	
	/**
	 * A variable storing the maximum amount of different schools that can be 
	 * part of this world.
	 */
	private static final int MAX_NUMBER_OF_SCHOOLS = 10;
	
	/**
	 * Increment the value of each school in this world with one.
	 * 
	 * @param 	school
	 * 			The school to increment the value of.
	 * @pre		...
	 * 			| hasAsSchool(school)
	 * @post	...
	 * 			| new.getAllSchoolsWithValues().get(school) ==
	 * 			|	getAllSchoolsWithValues().get(school)+1 
	 */
	@Model
	protected void incrementValueOfSchool(School school){
		assert hasAsSchool(school);
		allSchools.put(school, allSchools.get(school) + 1);
	}
	
	/**
	 * Decrement the value of each school in this world with one.
	 * 
	 * @param 	school
	 * 			The school to decrement the value of.
	 * @pre		...
	 * 			| hasAsSchool(school)
	 * @post	...
	 * 			| if(getAllSchoolsWithValues().get(school) > 0)
	 * 			| 	then new.getAllSchoolsWithValues().get(school) ==
	 * 			|			 getAllSchoolsWithValues().get(school)-1
	 * @effect	...
	 * 			| if(getAllSchoolsWithValues().get(school) == 0 ||
	 * 			|	 getAllSchoolsWithValues().get(school) == 1)
	 * 			|	then removeAsSchool(school)
	 */
	@Model
	protected void decrementValueOfSchool(School school){
		assert hasAsSchool(school);
		if(allSchools.get(school) > 0)
			allSchools.put(school, allSchools.get(school) - 1);
		if(allSchools.get(school) == 0)
			removeAsSchool(school);

	}
	
	/**
	 * Check whether this world has proper schools.
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	for some school in getAllSchools()
	 * 			|		!school.hasProperWorld()
	 */
	@Model
	boolean hasProperSchools(){
		for(School school: allSchools.keySet()){
			if(!school.hasProperWorld())
				return false;
		}
		return true;
	}

	/**
	 * A variable storing a map with as keys the schools of all slimes that have 
	 * this world as their world, and as values the amount of slimes in that school that
	 * have this world as their world.
	 * 
	 * @invar	...
	 * 			| allSchools != null
	 * @invar	...
	 * 			| for each school in allSchools.keyset()
	 * 			|	for each slime in school:
	 * 			|		slime.getWorld() == this
	 * 			|	school.size() == allSchools.get(school)
	 * 			|	school.hasProperWorld()
	 * @invar	...
	 * 			| allSchools.size() <= MAX_NUMBER_OF_SCHOOLS
	 */
	private final HashMap<School,Integer> allSchools = new HashMap<School,Integer>();

		
	/**
	 * Returns whether the player has won the game or not.
	 * 
	 * @return	...
	 * 			| if (getMazub() == null)
	 * 			|	then result == false
	 * @return	...
	 * 			| let
	 * 			|	affectedTiles = getTilesIn(getMazub().getPosition().getDisplayedXPosition(),
	 *			| 	getMazub().getPosition().getDisplayedYPosition(),getMazub().getPosition().getDisplayedXPosition()
	 *			|	+getMazub().getWidth()-1, getMazub().getPosition().getDisplayedYPosition()+getMazub().getHeight()-1)
	 *			| in
	 *			|	result == 
	 *			|		(for some tile in affectedTiles:
	 *			|			(tile = getTargetTile()))
	 */
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
	
	/**
	 * Checks whether the game is over or not.
	 * 
	 * @return	...
	 * 			| result == (getMazub() == null || didPlayerWin())
	 */
	public boolean isGameOver(){
		return (getMazub() == null || didPlayerWin());
	}

	/**
	 * Returns whether the game is started or not.
	 */
	protected boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Sets the game started on true or false.
	 * 
	 * @param 	gameStarted
	 * 			The boolean to set
	 * @post	...
	 * 			| new.isGameStarted() == gameStarted
	 */
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * A boolean variable saying whether the game is started or not.
	 */
	private boolean gameStarted = false;
	
	//No documentation asked for this function
	public void advanceTime(double timeDuration) throws
	IllegalXPositionException,IllegalYPositionException{
		if(isGameStarted() && isGameOver())
			terminate();
		if (getMazub() != null){
			getMazub().advanceTime(timeDuration);
			updateWindowPos();
		}
		for(Buzam buzam: getAllBuzams()){
			if(!buzam.isTerminated())
				try {
					buzam.advanceTime(timeDuration);
				} catch (IllegalJumpInvokeException | IllegalStateException e) {
					System.out.println("Illegal operation by Buzam");
					System.out.println(buzam.getVertDirection());
					e.printStackTrace();
				}
		}
		for(Plant plant: getAllPlants()){
			if(!plant.isTerminated())
				plant.advanceTime(timeDuration);
		}
		for(Shark shark: getAllSharks()){
			if(!shark.isTerminated())
				shark.advanceTime(timeDuration);
		}
		for(Slime slime: getAllSlimes()){
			if(!slime.isTerminated())
				slime.advanceTime(timeDuration);
		}
	}
	
	/**
	 * A method to update the position of the visible window.
	 * 
	 * @effect	...
	 * 			| if(getMazub() != null)
	 *			|	then setWindowXPos(getMazub().getPosition().getDisplayedXPosition()-
	 *			|		  (getVisibleWindowWidth()-getMazub().getWidth())/2),
	 *			|		 setWindowYPos(getMazub().getPosition().getDisplayedYPosition()-
	 *			|		  (getVisibleWindowHeight()-getMazub().getHeight())/2)
	 */
	private void updateWindowPos(){
		if(getMazub() != null){
			setWindowXPos(getMazub().getPosition().getDisplayedXPosition()-
						  (getVisibleWindowWidth()-getMazub().getWidth())/2);
			setWindowYPos(getMazub().getPosition().getDisplayedYPosition()-
						  (getVisibleWindowHeight()-getMazub().getHeight())/2);
		}
	}
	
	/**
	 * Terminate this world.
	 * 
	 * @pre		...
	 * 			| isGameStarted()
	 * @pre		...
	 * 			| isGameOver()
	 * @effect	...
	 * 			| getAllGameObjects().clear()
	 * @effect	...
	 * 			| getAllSchoolsWithValues().clear()
	 * @effect	...
	 * 			| if(getMazub() != null)
	 * 			|	then getMazub().setWorld(null)
	 * @effect	...
	 * 			| setMazub(null)
	 * @effect	...
	 * 			| Arrays.fill(worldTiles,null)
	 */
	@Model
	private void terminate(){
		assert isGameStarted();
		assert isGameOver();
		allGameObjects.clear();
		allSchools.clear();
		if(getMazub() != null)
			getMazub().setWorld(null);
		setMazub(null);
		Arrays.fill(worldTiles,null);
	}
	
	/**
	 * Return a textual representation of this world.
	 * 
	 * @return	...
	 * 			| result.contains("World with dimensions ")
	 * @return	...
	 * 			| result.contains(String.valueOf(getWorldWidth()) + ", ")
	 * @return	...
	 * 			| result.contains(String.valueOf(getWorldHeight()) + ".") 
	 */
	@Override
	public String toString(){
		return "World with dimensions " + String.valueOf(getWorldWidth()) + ", " + 
				String.valueOf(getWorldHeight()) + ".";
	}
}
