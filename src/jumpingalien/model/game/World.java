package jumpingalien.model.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.exceptions.*;
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
	 * @post 	...
	 * 			| new.getTileSize() = tileSize
	 * @post	...
	 * 			| new.getWorldWidth() = tileSize * nbTilesX
	 * @post	...
	 * 			| new.getWorldHeight() = tileSize * nbTilesY
	 * @post	...
	 *			| if (isValidVisibleWindowWidth(visibleWindowWidth))
	 *			|	then new.getVisibleWindowWidth() = visibleWindowWidth
	 *			| else
	 * 			|	then new.getVisibleWindowWidth() = getWorldWidth()
	 * @post	...
	 *			| if (isValidVisibleWindowHeight(visibleWindowHeight))
	 *			|	then new.getVisibleWindowHeight() = visibleWindowHeight
	 *			| else
	 * 			|	then new.getVisibleWindowHeight() = getWorldHeight()
	 * @post	...
	 * 			| new.getMaxWindowXPos() = getWorldWidth()-getVisibleWindowWidth()-1
	 * @post	...
	 * 			| new.getMaxWindowYPos() = getWorldHeight()-getVisibleWindowHeight()-1
	 * @post	...
	 * 			| let
	 * 			|	targetTile = Tile(this,targetTileX*tileSize,targetTileY*tileSize,true) 
	 * 			| in
	 * 			|	new.getTargetTile().equals(targetTile)
	 * @post	...
	 * 			| let
	 * 			|	matrixOfTiles = Tile[nbTilesY][nbTilesX]
	 * 			| in
	 *			|	new.getWorldTiles().equals(matrixOfTiles)
	 * @post	...
	 * 			| let
	 * 			|	(for each row in 0..nbTilesY-1:
	 * 			|		(for each col in 0..nbTilesX-1:
	 * 			|			if (row == targetTileY && col == targetTileX)
	 * 			|				then tile = getTargetTile()
	 * 			|			else tile = Tile(this, col*tileSize, row*tileSize,false) ) )
	 * 			| in
	 * 			|	new.getTileAtTilePos(col,row).equals(tile)
	 */
	@Raw
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
	 * @return	Return the tile position in horizontal direction of the tile in which
	 * 			the given xPosition is, the tile position is the given x position
	 * 			divided by the tile size.
	 * 			| result == xPosition/getTileSize()
	 */
	protected int getBelongingTileXPosition(int xPosition){
		return xPosition/getTileSize();
	}

	/**
	 * Return the belonging tile position of the given y position.
	 * 
	 * @param 	yPosition
	 * 			The given y position
	 * @return	Return the tile position in vertical direction of the tile in which
	 * 			the given yPosition is, the tile position is the given y position
	 * 			divided by the tile size.
	 * 			| result == yPosition/getTileSize()
	 */
	protected int getBelongingTileYPosition(int yPosition){
		return yPosition/getTileSize();
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
	 * 			|				rowNb/(getBelongingTileXPosition(pixelRight) - getBelongingTileXPosition(pixelLeft)
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
	 * 			|	tilePositions = getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop)
	 * 			| in
	 * 			|	for each index in 0...tilePositions.length-1:
	 * 			|		tile = getTileAtTilePos(tilePositions[index][0], tilePositions[index][1])
	 * 			|	result.contains(tile)
	 */
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
	 * Returns the width of the world.
	 */
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
	public int getVisibleWindowWidth() {
		return visibleWindowWidth;
	}

	/**
	 * Check whether the width of the visible window is valid or not.
	 * 
	 * @param 	width
	 * 			the width to check
	 * @return	...
	 * 			| result == (width <= getWorldWidth() && width>2*MIN_BORDER_DISTANCE)
	 */
	private boolean isValidVisibleWindowWidth(int width){
		return (width <= getWorldWidth() && width>
				2*MIN_BORDER_DISTANCE);
	}

	/**
	 * A variable storing the width of the visible window.
	 */
	private final int visibleWindowWidth;
	
	/**
	 * Returns the height of the visible window.
	 */
	public int getVisibleWindowHeight() {
		return visibleWindowHeight;
	}
	
	/**
	 * Check whether the height of the visible window is valid or not.
	 * 
	 * @param 	height
	 * 			the height to check
	 * @return	...
	 * 			| result == (height <= getWorldHeight() && height>(2*MIN_BORDER_DISTANCE))
	 */
	private boolean isValidVisibleWindowHeight(int height){
		return (height <= getWorldHeight() && height> 
			   (2*MIN_BORDER_DISTANCE));
	}
	
	/**
	 * A variable storing the height of the visible window.
	 */
	private final int visibleWindowHeight;
	
	/**
	 * A variable storing the minimum amount of pixels there should be between the Mazub
	 * and the borders of the visible window, if the Mazub isn't positioned close to the borders of the game world. 
	 */
	private final int MIN_BORDER_DISTANCE = 200;
	
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
	protected boolean hasAsTile(Tile tile){
		return (tile != null && worldTiles[tile.getTileXPos()][tile.getTileYPos()] == tile);
	}
	
	/**
	 * A matrix storing the tiles of this world. 
	 */
	private final Tile[][] worldTiles;
	
	/**
	 * Returns a set of all impassable tiles in the game world.
	 */
	// NORMAAL NIET PUBLIC!!!!!
	public HashSet<Tile> getImpassableTiles() {
		return impassableTiles;
	}
	
	// nodig in foreach
	public HashSet<Tile> getAllTiles(){
		return getTilesIn(0, 0, getWorldWidth()-1, getWorldHeight()-1);
	}

	/**
	 * Checks whether a tile is passable or not.
	 * 
	 * @param 	tile
	 * 			The tile to check
	 * @return	...
	 * 			| result == (!tile.getGeoFeature().isPassable())
	 */
	private static boolean isValidImpassableTile(Tile tile){
		return (!tile.getGeoFeature().isPassable());
	}
	
	/**
	 * Add the given tile as an impassable tile for this world.
	 * 
	 * @param 	tile
	 * 			The tile to add
	 * @pre		...
	 * 			| isValidImpassableTile(tile)
	 * @post	...
	 * 			| new.getImpassableTiles().contains(tile)
	 */
	void addAsImpassableTile(Tile tile){
		assert(isValidImpassableTile(tile));
		this.impassableTiles.add(tile);
	}
	
	/**
	 * Remove the given tile from the list of impassable tiles for this world.
	 * 
	 * @param 	tile
	 * 			The tile to remove.
	 * @pre		...
	 * 			| hasAsImpassableTile(tile)
	 * @post	...
	 * 			| !new.getImpassableTile().contains(tile)
	 */
	void removeAsImpassableTile(Tile tile){
		assert hasAsImpassableTile(tile);
		this.impassableTiles.remove(tile);
	}
	
	/**
	 * Checks whether the given tile is references to this world as an impassable tile.
	 * 
	 * @param 	tile
	 * 			The tile to check.
	 * @return	...
	 * 			| getImpassableTiles().contains(tile)
	 */
	protected boolean hasAsImpassableTile(Tile tile){
		return getImpassableTiles().contains(tile);
	}

	/**
	 * A set collecting references to impassable tiles attached to this world.
	 * 
	 * @invar	...
	 * 			| impassableTiles != null
	 * @invar	...
	 * 			| for each tile in impassableTiles:
	 * 			|	isValidImpassableTile(tile)
	 * @invar	...
	 * 			| for each tile in impassableTiles:
	 * 			|	(tile.getWorld() == this)
	 */
	private final HashSet<Tile> impassableTiles = new HashSet<Tile>();
	
	/**
	 * Returns the target tile of this game world. 
	 * The Mazub should reach this tile in order to win the game.
	 */
	public Tile getTargetTile() {
		return targetTile;
	}

	/**
	 * A variable storing the target tile.
	 */
	private final Tile targetTile;
	
	/**
	 * Returns the x-position of the visible window.
	 */
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
	 *			| else getWindowXPos() = windowXPos
	 */
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
	public int getWindowYPos() {
		return windowYPos;
	}

	/**
	 * Sets the x-position of the visible window to the given x-position.
	 * Like all other positions, this position should be within the borders of the game world.
	 * 
	 * @param 	windowYPos
	 * 			The given y-position
	 * @post	...
	 * 			| if (windowYPos < 0)
	 *			|	then new.getWindowYPos() = 0
	 *			| else if (windowYPos > getMaxWindowYPos())
	 *			|	then new.getWindowYPos() = getMaxWindowYPos()
	 *			| else getWindowYPos() = windowYPos
	 */
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
	protected int getMaxWindowXPos() {
		return MAX_WINDOW_X_POS;
	}

	/**
	 * A variable storing the maximum x-position of the visible window.
	 */
	private final int MAX_WINDOW_X_POS;

	/**
	 * Returns the maximum y-position of the visible window.
	 */
	protected int getMaxWindowYPos() {
		return MAX_WINDOW_Y_POS;
	}

	/**
	 * A variable storing the maximum y-position of the visible window.
	 */
	private final int MAX_WINDOW_Y_POS;
	
	/**
	 * Returns the one and only Mazub of this game world.
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
		if(canHaveAsMazub(alien))
			this.mazub = alien;
		if (alien != null){
			getMazub().setWorld(this);
		}
	}
	
	/**
	 * A variable storing the Mazub of this game world.
	 */
	private Mazub mazub;

	/**
	 * Returns the one and only Buzam of this game world.
	 */
	public Buzam getBuzam() {
		return buzam;
	}

	/**
	 * Checks whether this world can have the given Buzam is its Buzam.
	 * 
	 * @param 	alien
	 * 			The Buzam to check
	 * @return	...
	 * 			| result == ((alien == null) || (!alien.isDead() && canAddGameObjects()))
	 */
	private boolean canHaveAsBuzam(Buzam alien){
		return (alien == null) || (!alien.isDead() && canAddGameObjects());
	}
	
	/**
	 * Sets the Buzam of this game world to the given Buzam.
	 * 
	 * @param 	alien
	 * 			The Buzam to set
	 * @effect	...
	 * 			| if (getBuzam() != null)
	 *			|	then getBuzam().setWorld(null), getAllGameObjects().remove(get())
	 * @post	...
	 * 			| if(canHaveAsBuzam(alien))
	 *			|	then new.getBuzam() = alien
	 * @effect	...
	 * 			| if (alien != null)
	 * 			|	then getBuzam().setWorld(this)
	 */
	public void setBuzam(Buzam alien){
		if (getBuzam() != null){
			getBuzam().setWorld(null);
			getAllGameObjects().remove(getBuzam());
			getAllCharacters().remove(getBuzam());
		}
		if(canHaveAsBuzam(alien))
			this.buzam = alien;
		if (alien != null){
			getBuzam().setWorld(this);
		}
	}
	
	/**
	 * A variable storing the Buzam of this game world.
	 */
	private Buzam buzam;

	
	/**
	 * Returns a set of all plants in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each plant in Plant:
	 * 			|	result.contains(plant) == (plant.getWorld() == this)
	 */
	public HashSet<Plant> getAllPlants() {
		return allPlants;
	}
	
	/**
	 * Returns a set of all unterminated plants in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each plant in Plant:
	 * 			|	result.contains(plant) == (plant.getWorld() == this)
	 */
	public HashSet<Plant> getAllUnterminatedPlants(){
		HashSet<Plant> result = new HashSet<Plant>();
		for(Plant plant: allPlants){
			if(!plant.isTerminated())
				result.add(plant);
		}	
		return result;	
	}
	
	/**
	 * Check whether the given plant is attached to this game world.
	 * 
	 * @param 	plant
	 * 			The plant to check
	 * @return	...
	 * 			| result.contains(plant)
	 */
	protected boolean hasAsPlant(Plant plant){
		return getAllPlants().contains(plant);
	}
	
	/**
	 * Add the given plant to the set of plants.
	 * 
	 * @param 	plant
	 * 			The plant to be added
	 * @pre		...
	 * 			| isValidGameObject(plant)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then new.hasAsPlant(plant)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then (new plant).getWorld() == this
	 */
	@Raw
	public void addAsPlant(Plant plant){
		assert (isValidGameObject(plant));
		if(canAddGameObjects()){
			allPlants.add(plant);
			plant.setWorld(this);
		}
	}
	
	/**
	 * Remove the given plant from the set of plants.
	 * 
	 * @param 	plant
	 * 			The plant to be removed
	 * @pre		...
	 * 			| hasAsPlant(plant)
	 * @post	...
	 * 			| ! new.hasAsPlant(plant)
	 * @post	...
	 * 			| (new plant).getWorld() == null
	 */
	protected void removeAsPlant(Plant plant){
		assert hasAsPlant(plant);
		allPlants.remove(plant);
		plant.setWorld(null);
	}

	/**
	 * Set collecting references to all plants attached to this world.
	 * 
	 * @invar	...
	 * 			| allPlants != null
	 * @invar	...
	 * 			| for each plant in allPlants:
	 * 			|	isValidGameObject(plant)
	 * @invar	...
	 * 			| for each plant in allPlants:
	 * 			|	plant.getWorld() == this
	 */
	private final HashSet<Plant> allPlants = new HashSet<Plant>();

	/**
	 * Returns a set of all slimes in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each slime in Slime:
	 * 			|	result.contains(slime) == (slime.getWorld() == this)
	 */
	public HashSet<Slime> getAllSlimes() {
		return allSlimes;
	}
	
	/**
	 * Returns a set of all unterminated slimes in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each slime in Slime:
	 * 			|	result.contains(slime) == (slime.getWorld() == this)
	 */
	public HashSet<Slime> getAllUnterminatedSlimes(){
		HashSet<Slime> result = new HashSet<Slime>();
		for(Slime slime: getAllSlimes()){
			if(!slime.isTerminated())
				result.add(slime);
		}	
		return result;	
	}
	
	/**
	 * Check whether the given slime is attached to this game world.
	 * 
	 * @param 	slime
	 * 			The slime to check
	 * @return	...
	 * 			| result.contains(slime)
	 */
	protected boolean hasAsSlime(Slime slime){
		return getAllSlimes().contains(slime);
	}
	
	/**
	 * Add the given slime to the set of slimes.
	 * 
	 * @param 	slime
	 * 			The slime to be added
	 * @pre		...
	 * 			| isValidGameObject(slime)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then new.hasAsSlime(slime)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then (new slime).getWorld() == this
	 */
	@Raw
	public void addAsSlime(Slime slime){
		if(canAddGameObjects() && canHaveAsSlime(slime)){
			getAllSlimes().add(slime);
			slime.setWorld(this);
			addAsSchool(slime.getSchool());
		}
	}
	
	/**
	 * Remove the given slime from the set of slimes.
	 * 
	 * @param 	slime
	 * 			The slime to be removed
	 * @pre		...
	 * 			| hasAsSlime(slime)
	 * @post	...
	 * 			| ! new.hasAsSlime(slime)
	 * @post	...
	 * 			| (new slime).getWorld() == null
	 */
	protected void removeAsSlime(Slime slime){
		assert hasAsSlime(slime);
		allSlimes.remove(slime);
		decrementValueOfSchool(slime.getSchool());
	}

	protected boolean canHaveAsSlime(Slime slime){
		return (hasAsSchool(slime.getSchool()) || canAddSchool());
	}
	
	/**
	 * Set collecting references to all slimes attached to this world.
	 * 
	 * @invar	...
	 * 			| allSlimes != null
	 * @invar	...
	 * 			| for each slime in allSlimes:
	 * 			|	isValidGameObject(slime)
	 * @invar	...
	 * 			| for each slime in allSlimes:
	 * 			|	slime.getWorld() == this
	 */
	private final HashSet<Slime> allSlimes = new HashSet<Slime>();
	
	/**
	 * Returns a set of all slimes in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each slime in Slime:
	 * 			|	result.contains(slime) == (slime.getWorld() == this)
	 */
	public Set<School> getAllSchools() {
		return allSchools.keySet();
	}
		
	/**
	 * Check whether the given slime is attached to this game world.
	 * 
	 * @param 	slime
	 * 			The slime to check
	 * @return	...
	 * 			| result.contains(slime)
	 */
	protected boolean hasAsSchool(School school){
		return allSchools.containsKey(school);
	}
	
	/**
	 * Add the given slime to the set of slimes.
	 * 
	 * @param 	slime
	 * 			The slime to be added
	 * @pre		...
	 * 			| isValidGameObject(slime)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then new.hasAsSlime(slime)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then (new slime).getWorld() == this
	 */
	@Raw
	public void addAsSchool(School school){
		if(!hasAsSchool(school)){
			allSchools.put(school,1);
		}
		else
			incrementValueOfSchool(school);
	}
	
	/**
	 * Remove the given slime from the set of slimes.
	 * 
	 * @param 	slime
	 * 			The slime to be removed
	 * @pre		...
	 * 			| hasAsSlime(slime)
	 * @post	...
	 * 			| ! new.hasAsSlime(slime)
	 * @post	...
	 * 			| (new slime).getWorld() == null
	 */
	protected void removeAsSchool(School school){
		assert hasAsSchool(school);
		assert allSchools.get(school) == 0;
		allSchools.remove(school);
	}

	protected boolean canAddSchool(){
		return (numberOfSchools() < 10);
	}
	
	protected int numberOfSchools(){
		return allSchools.size();
	}
	
	protected void incrementValueOfSchool(School school){
		allSchools.put(school, allSchools.get(school) + 1);
	}
	
	protected void decrementValueOfSchool(School school){
		if(allSchools.get(school) > 0)
			allSchools.put(school, allSchools.get(school) - 1);
		if(allSchools.get(school) == 0)
			removeAsSchool(school);

	}

	/**
	 * Set collecting references to all slimes attached to this world.
	 * 
	 * @invar	...
	 * 			| allSlimes != null
	 * @invar	...
	 * 			| for each slime in allSlimes:
	 * 			|	isValidGameObject(slime)
	 * @invar	...
	 * 			| for each slime in allSlimes:
	 * 			|	slime.getWorld() == this
	 */
	private final HashMap<School,Integer> allSchools = new HashMap<School,Integer>();

	/**
	 * Returns a set of all sharks in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each shark in Shark:
	 * 			|	result.contains(shark) == (shark.getWorld() == this)
	 */
	public HashSet<Shark> getAllSharks(){
		return allSharks;
	}
	
	/**
	 * Returns a set of all unterminated sharks in this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each shark in Shark:
	 * 			|	result.contains(shark) == (shark.getWorld() == this)
	 */
	public HashSet<Shark> getAllUnterminatedSharks(){
		HashSet<Shark> result = new HashSet<Shark>();
		for(Shark shark: allSharks){
			if(!shark.isTerminated())
				result.add(shark);
		}	
		return result;	
	}
		
	/**
	 * Check whether the given shark is attached to this game world.
	 * 
	 * @param 	shark
	 * 			The shark to check
	 * @return	...
	 * 			| result.contains(shark)
	 */
	protected boolean hasAsShark(Shark shark){
		return allSharks.contains(shark);
	}
	
	/**
	 * Add the given shark to the set of sharks.
	 * 
	 * @param 	shark
	 * 			The shark to be added
	 * @pre		...
	 * 			| isValidGameObject(shark)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then new.hasAsShark(shark)
	 * @post	...
	 * 			| if(canAddGameObjects())
	 * 			|	then (new shark).getWorld() == this
	 */
	@Raw
	public void addAsShark(Shark shark){
		if(canAddGameObjects()){
			getAllSharks().add(shark);
			shark.setWorld(this);
		}
	}
	
	/**
	 * Remove the given shark from the set of sharks.
	 * 
	 * @param 	shark
	 * 			The shark to be removed
	 * @pre		...
	 * 			| hasAsShark(shark)
	 * @post	...
	 * 			| ! new.hasAsShark(shark)
	 * @post	...
	 * 			| (new shark).getWorld() == null
	 */
	protected void removeAsShark(Shark shark){
		assert hasAsShark(shark);
		getAllSharks().remove(shark);
	}
	
	/**
	 * Set collecting references to all sharks attached to this world.
	 * 
	 * @invar	...
	 * 			| allSharks != null
	 * @invar	...
	 * 			| for each shark in allSharks:
	 * 			|	isValidGameObject(shark)
	 * @invar	...
	 * 			| for each shark in allSharks:
	 * 			|	shark.getWorld() == this
	 */
	private final HashSet<Shark> allSharks = new HashSet<Shark>();
	
	/**
	 * Checks whether game objects can still be added.
	 * 
	 * @return	...
	 * 			| !isGameStarted()
	 */
	boolean canAddGameObjects(){
		return !isGameStarted();
	}
	
	/**
	 * Checks whether the given game object is effective.
	 * 
	 * @param 	object
	 * 			The object to check
	 * @return	...
	 * 			| object != null
	 */
	boolean isValidGameObject(GameObject object){
		return object != null;
	}
	
	/**
	 * Returns all game objects attached to this game world.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each object in GameObject:
	 * 			|	result.contains(object) == (object.getWorld() == this)
	 */
	// zou niet publiek mogen zijn!!!!!!
	public HashSet<GameObject> getAllGameObjects(){
		HashSet<GameObject> result = new HashSet<GameObject>();
		result.addAll(allSharks);
		result.addAll(allPlants);
		result.addAll(allSlimes);
		result.add(getMazub());
		result.add(getBuzam());
		return result;
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
	 *			|			gameObject.getWorld() != this))
	 */
	@SuppressWarnings("unused")
	private boolean hasProperGameObjects(){
		HashSet<GameObject> allGameObjects = getAllGameObjects();
		if(getMazub() == null && allGameObjects.size() > 100)
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
	
	/**
	 * Returns all characters attached to this game world.
	 * Those characters can be a Mazub, shark or slime.
	 * 
	 * @return	...
	 * 			| result != null
	 * @return	...
	 * 			| for each character in Character:
	 * 			|	result.contains(character) == (character.getWorld() == this)
	 */
	protected HashSet<Character> getAllCharacters(){
		HashSet<Character> result = new HashSet<Character>();
		result.addAll(allSharks);
		result.addAll(allSlimes);
		result.add(getMazub());
		result.add(getBuzam());
		return result;
	}
	
	/**
	 * Returns whether the player won the game or not.
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
	
	public void advanceTime(double timeDuration) throws
	IllegalXPositionException,IllegalYPositionException{
		if (getMazub() != null){
			getMazub().advanceTime(timeDuration);
			updateWindowPos();
		}
		if (getBuzam() != null){
			getBuzam().advanceTime(timeDuration);
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
	 * Return a textual representation of this world.
	 * 
	 * @return	...
	 * 			| result.contains("World with dimensions ")
	 * @return	...
	 * 			| result.contains(getWorldWidth() + ",")
	 * @return	...
	 * 			| result.contains(getWorldHeight() + ".") 
	 */
	@Override
	public String toString(){
		return "World with dimensions " + getWorldWidth() + "," + getWorldHeight() + ".";
	}
}
