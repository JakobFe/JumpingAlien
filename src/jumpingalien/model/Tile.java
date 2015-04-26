package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class involving tiles with a position and a geological feature
 * as a part of a game world.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version	1.0
 * 
 * @invar	...
 * 			| hasProperWorld()
 * @invar	...
 * 			| isValidXPosition(getXPosition())
 * @invar	...
 * 			| isValidYPosition(getYPosition())
 *
 */
public class Tile {
	
	/**
	 * Initialize this tile with given x and y position, given world,
	 * given geological feature and target tile attribute.
	 * 
	 * @param 	world
	 * 			The world belonging to this new tile.
	 * @param 	xPosition
	 * 			The x position for this new tile.
	 * @param 	yPosition
	 * 			The y position for this new tile.
	 * @param 	geologicalFeature
	 * 			The geological feature for this new tile.
	 * @param 	isTargetTile
	 * 			Indicates whether this tile is the target tile in the given world.
	 * @pre		...
	 * 			| isValidXPosition(xPosition,world)
	 * @pre		...
	 * 			| isValidYPosition(yPosition,world)
	 * @post	...
	 * 			| new.getWorld() == world
	 * @post	...
	 * 			| new.getXPosition() == xPosition
	 * @post	...
	 * 			| new.getYPosition() == yPosition
	 * @post	...
	 * 			| new.isTargetTile() == isTartgetTile
	 * @effect	...
	 * 			| setGeoFeature(geologicalFeature)
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !isValidWorld(world)
	 */
	@Raw
	public Tile(World world, int xPosition, int yPosition, Terrain geologicalFeature,
			boolean isTargetTile) throws IllegalArgumentException{
		assert isValidXPosition(xPosition,world);
		assert isValidYPosition(yPosition,world);
		if(!isValidWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		setGeoFeature(geologicalFeature);
		this.isTargetTile = isTargetTile;
	}
	
	/**
	 * Initialize this tile with given x and y position, given world,
	 * air as its geological feature and the given target tile attribute.
	 * 
	 * @param 	world
	 * 			The world belonging to this new tile.
	 * @param 	xPosition
	 * 			The x position for this new tile.
	 * @param 	yPosition
	 * 			The y position for this new tile.
	 * @param 	isTargetTile
	 * 			Indicates whether this tile is the target tile in the given world.
	 * @pre		...
	 * 			| isValidXPosition(xPosition,world)
	 * @pre		...
	 * 			| isValidYPosition(yPosition,world)
	 * @effect	...
	 * 			| this(world,xPosition,yPosition,Terrain.AIR,isTargetTile);
	 */
	@Raw
	public Tile(World world, int xPosition, int yPosition, boolean isTargetTile)
	throws IllegalArgumentException{
		this(world,xPosition,yPosition,Terrain.AIR,isTargetTile);
	}
	
	/**
	 * Return the world where this tile is located.
	 */
	World getWorld() {
		return world;
	}
	
	/**
	 * Check whether a given world is a valid world.
	 * @param 	world
	 * 			The world to check.
	 * @return	...
	 * 			| result == (world != null)
	 */
	@Model
	private boolean isValidWorld(World world){
		return world != null;
	}
	
	/**
	 * Check whether this tile has a proper world.
	 * 
	 * @return	...
	 * 			| result == isValidWorld(getWorld()) && getWorld().hasAsTile(this)
	 */
	public boolean hasProperWorld(){
		return isValidWorld(getWorld()) && getWorld().hasAsTile(this);
	}
	
	/**
	 * A variable storing the world where this tile is located.
	 */
	private final World world;
	
	/**
	 * Return the x position of this tile.
	 */
	@Basic@Immutable
	public int getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Return the tile x position of this tile.
	 * 
	 * @return	...
	 * 			| result == getXPosition()/getWorld().getTileSize()
	 */
	public int getTileXPos(){
		return getXPosition()/getWorld().getTileSize();
	}
	
	/**
	 * Check whether a given x position is a valid x position.
	 * 
	 * @param 	xPos
	 * 			The x position to check.
	 * @param	world
	 * 			The world to check against.
	 * @return	...
	 * 			| result == (xpos >=0 && xpos<= world.getWorldWidth() &&
	 *			|			(xpos%world.getTileSize() == 0))
	 */
	private static boolean isValidXPosition(int xPos, World world){
		return (xPos >=0 && xPos<= world.getWorldWidth() &&
				(xPos%world.getTileSize() == 0));
	}
	
	/**
	 * A variable storing the x position for this tile.
	 */
	private final int xPosition;
	
	/**
	 * Return the y position of this tile.
	 */
	@Basic@Immutable
	public int getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Return the tile y position of this tile.
	 * 
	 * @return	...
	 * 			| result == getYPosition()/getWorld().getTileSize()
	 */
	int getTileYPos(){
		return getYPosition()/getWorld().getTileSize();
	}
	
	/**
	 * Check whether a given y position is a valid y position.
	 * 
	 * @param 	yPos
	 * 			The y position to check.
	 * @param	world
	 * 			The world to check against.
	 * @return	...
	 * 			| result == (yPos >=0 && yPos<= world.getWorldWidth() &&
	 *			| 			(yPos%world.getTileSize() == 0));
	 */
	private static boolean isValidYPosition(int yPos, World world){
		return (yPos >=0 && yPos<= world.getWorldWidth() &&
				(yPos%world.getTileSize() == 0));
	}
	
	/**
	 * A variable storing the x position for this tile.
	 */
	private final int yPosition;
	
	/**
	 * Returns the geological feature for this tile. 
	 */
	public Terrain getGeoFeature() {
		return geoFeature;
	}
	
	/**
	 * A method to set the geological feature to a given terrain.
	 * 
	 * @param 	geoFeature
	 * 			The geological feature to set.
	 * @post	...
	 * 			| new.getGeoFeature() == geoFeature
	 * @post	...
	 * 			| if(!getGeoFeature().isPassable() && geoFeature.isPassable())
	 *			|	then getWorld().removeAsImpassableTile(this)
	 * 			| else if (!geoFeature.isPassable() && !geoFeature.isPassable()))
	 * 			|	then getWorld().addAsImpassableTile(this)
	 */
	public void setGeoFeature(Terrain geoFeature) {
		Terrain oldFeature = getGeoFeature();
		this.geoFeature = geoFeature;
		if(!oldFeature.isPassable() && 
		     geoFeature.isPassable())
			getWorld().removeAsImpassableTile(this);
		else if (oldFeature.isPassable() && !geoFeature.isPassable())
			getWorld().addAsImpassableTile(this);
	}
	
	/**
	 * A variable storing the geological feature for this tile.
	 */
	private Terrain geoFeature = Terrain.AIR;
	
	/**
	 * Returns whether this tile is the target tile for its world.
	 */
	boolean isTargetTile(){
		return this.isTargetTile;
	}
	
	/**
	 * A variable storing whether this tile is the target tile for its world.
	 */
	private final boolean isTargetTile;
	
	/**
	 * Return a textual representation for this tile.
	 * 
	 * @return	...
	 * 			| result.contains("Tile at tile position ")
	 * @return	...
	 * 			| result.contains(String.valueOf(getTileXPos()))
	 * @return	...
	 * 			| result.contains(",")
	 * @return	...
	 * 			| result.contains(String.valueOf(getTileYPos()))
	 * @return	...
	 * 			| result.contains(" in ")
	 * @return	...
	 * 			| result.contains(getWorld().toString())
	 */
	@Override
	public String toString(){
		return "Tile at tile position " + String.valueOf(getTileXPos()) + "," 
				+ String.valueOf(getTileYPos()) + 
				" in " + getWorld().toString();
	}
}
