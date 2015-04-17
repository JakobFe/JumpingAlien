package jumpingalien.model.gameobjects;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.model.other.*;
import jumpingalien.model.worldfeatures.*;
import jumpingalien.util.Sprite;

/**
 * A class concerning game objects with a position, a horizontal velocity,
 * sprites and hit points.
 * 
 * @invar	This game object must have a proper position.
 * 			| hasProperPosition()
 * @invar	The horizontal direction of this game object must be valid.
 * 			| isValidHorDirection(getHorDirection())
 * @invar 	The initial horizontal velocity of this game object must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of this game object must be valid.
 * 			| canHaveAsMaxHorVelocity(getMaxHorVelocity())
 * @invar	The hit points of this game object must be a valid number of hit points.
 * 			| isValidHitPoints(hitPoints)
 *  f
 * @author 	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public abstract class GameObject {
	
	/**
	 * Initialize this new game object with given x position, given y position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites and given number of hit points.
	 * 
	 * At the moment of initialization the velocity and acceleration are zero
	 * in all directions.
	 * 
	 * @param 	x
	 * 		  	Initial x position for this game object.
	 * @param 	y
	 * 			Initial y position for this game object.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this game object.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity while running for this game object.
	 * @param	sprites
	 * 			An array containing the different sprites for this game object.
	 * @param	hitPoints
	 * 			The hit points for this new game object. 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @pre		The number of hit points must be a valid number of hit points.
	 * 			| isValidHitPoints(hitPoints)
	 * @effect	This game object is initialized with position (x,y).
	 * 			| setPosition(new Position(x,y))
	 * @post	This new game object has the given sprites as its sprites.
	 * 			| new.getAllSprites() == sprites
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x position is not a valid x position.
	 * 			| !Position.isValidXPosition(x)
	 * @throws	IllegalYPositionException(y,this)
	 * 			The given y position is not a valid y position.
	 * 			| !Position.isValidYPosition(y)
	 */
	@Raw@Model
	protected GameObject(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalXPositionException,IllegalYPositionException{
		setPosition(new Position(x,y));
		setHitPoints(hitPoints);
		assert isValidInitHorVelocity(initHorVelocity);
		this.initHorVelocity = initHorVelocity;
		assert canHaveAsMaxHorVelocity(maxHorVelocity);
		setMaxHorVelocity(maxHorVelocity);
		setMaxHorVelocity(maxHorVelocity);
		this.sprites = sprites;
	}
	
	/**
	 * Initialize this new game object with given x position, given y position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites and 100 hit points.
	 * 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @pre		The number of hit points must be a valid number of hit points.
	 * 			| isValidHitPoints(hitPoints)
	 * @effect	This game object is initialized with the given x position, given y position, 
	 * 			given initial horizontal velocity, given maximum horizontal velocity,
	 * 			given sprites and 100 hit points.
	 * 			| this(x,y,initHorVelocity,initHorVelocity,sprites,hitPoints)
	 */
	@Raw@Model
	protected GameObject(int x, int y, double initHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalXPositionException,IllegalYPositionException{
		this(x,y,initHorVelocity,initHorVelocity,sprites,hitPoints);
	}
	
	/**
	 * Return the current position of this game object.
	 */
	public Position getPosition(){
		return position.copy();
	}
	
	/**
	 * Check whether or not the given position is a valid position.
	 * 
	 * @param 	position
	 * 			The position to check.
	 * @return	True if the x component of the given position is a valid
	 * 			x position and if the y component of the given position
	 * 			is a valid y position.
	 * 			| result == position.isValidXPosition(position.getXPosition()) &&
			   	|			position.isValidYPosition(position.getYPosition())
	 */
	protected boolean isValidPosition(Position position){
		return position.isValidXPosition(position.getXPosition()) &&
			   position.isValidYPosition(position.getYPosition());
	}
	
	/**
	 * Check whether or not this game object has a proper position.
	 * 
	 * @return	True if the position of this game object is a valid position.
	 * 			| result == isValidPosition()
	 */
	protected boolean hasProperPosition(){
		return isValidPosition(getPosition());
	}
	
	/**
	 * Set the position of this game object to the given position.
	 * 
	 * @param	position
	 * 			The position to set.
	 * @post	The game object refers to the given position.
	 * 			| new.getPosition() == position
	 * @throws	IllegalXPosition
	 * 			The x component of this position is not a valid x position.
	 * @throws	IllegalYPosition
	 * 			The y component of this position is not a valid y position.	 * 
	 */
	protected void setPosition(Position position)
			throws IllegalXPositionException,IllegalYPositionException{
		this.position = position;
	}
	
	/**
	 * A variable storing the current position of this game object.
	 */
	private Position position;
	
	/**
	 * Return the width of the current sprite of this game object.
	 */
	public int getWidth(){
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * Return the height of the current sprite of this game object.
	 */
	public int getHeight(){
		return this.getCurrentSprite().getHeight();
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the left side.
	 * 
	 * @return	A matrix containing all positions of this 
	 * 			game object that are located at the left side.
	 * 			| ... 
	 */
	protected int[][] getLeftPerimeter(){
		int xPos = this.getPosition().getDisplayedXPosition();
		int yPos = this.getPosition().getDisplayedYPosition();
		int [][] result = new int[getHeight()][2];
		for(int index=0;index<getHeight();index++){
			result[index][0] = xPos;
			result[index][1] = yPos + index;
		}
		return result;
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the right side.
	 * 
	 * @return	A matrix containing all positions of this 
	 * 			game object that are located at the right side.
	 * 			| ...
	 */
	public int[][] getRightPerimeter(){
		int xPos = this.getPosition().getDisplayedXPosition() + this.getWidth()-1;
		int yPos = this.getPosition().getDisplayedYPosition();
		int [][] result = new int[getHeight()][2];
		for(int index=0;index<getHeight();index++){
			result[index][0] = xPos;
			result[index][1] = yPos + index;
		}
		return result;
	}

	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the bottom.
	 * 
	 * @return	A matrix containing all positions of this 
	 * 			game object that are located at the bottom.
	 * 			| ...
	 */
	public int[][] getLowerPerimeter(){
		int xPos = this.getPosition().getDisplayedXPosition();
		int yPos = this.getPosition().getDisplayedYPosition();
		int [][] result = new int[getWidth()][2];
		for(int index=0;index<getWidth();index++){
			result[index][0] = xPos + index;
			result[index][1] = yPos;
		}
		return result;
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the top.
	 * 
	 * @return	A matrix containing all positions of this 
	 * 			game object that are located at the top.
	 * 			| ...
	 */
	public int[][] getUpperPerimeter(){
		int xPos = this.getPosition().getDisplayedXPosition();
		int yPos = this.getPosition().getDisplayedYPosition() + getHeight()-1;
		int [][] result = new int[getWidth()][2];
		for(int index=0;index<getWidth();index++){
			result[index][0] = xPos + index;
			result[index][1] = yPos;
		}
		return result;
	}
	
	/**
	 * A method to check whether this game object occupies a given position.
	 * 
	 * @param 	position
	 * 			The position to check
	 * @pre		The given position must be a valid position.
	 * 			| isValidPosition(position)
	 * @return	True if the given position is located between the bottom left,
	 * 			bottom right, top left and top right corner of this game object.
	 * 			| ...
	 */
	public boolean occupiesPosition(Position position){
		assert isValidPosition(position);
		int xpos = position.getDisplayedXPosition();
		int ypos = position.getDisplayedYPosition();
		return (xpos>=getPosition().getDisplayedXPosition() &&
				xpos<=getPosition().getDisplayedXPosition() + getWidth() - 1 &&
				ypos>=getPosition().getDisplayedYPosition() &&
				ypos<=getPosition().getDisplayedYPosition() + getHeight() - 1);
	}
	
	/**
	 * Returns the current number of hit points.
	 */
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}
	
	/**
	 * Check if the given number of hit points is a valid number of hit points.
	 * 
	 * @return	True if the given number is greater than or equal to zero.
	 * 			| result == (hitPoints >= 0)
	 */
	protected boolean isValidHitPoints(int hitPoints){
		return (hitPoints>=0); 
	}

	/**
	 * Set the number of hit points to the given number of hit points.
	 * 
	 * @param	hitPoints
	 * 			The amount of hit points to set.
	 * @post	If the amount of hit points is a valid number of hit points,
	 * 			the new amount of hit points is equal to the given amount 
	 * 			of hit points.
	 * 			| if(isValidHitPoints()
	 * 			|	then new.getHitPoints() == hitPoints
	 */
	public void setHitPoints(int hitPoints) {
		if (isValidHitPoints(hitPoints))
			this.hitPoints = hitPoints;
		else if(hitPoints < 0){
			this.hitPoints = 0;
		}
	}
	
	protected void updateHitPoints(){
		if(getHitPoints() == 0)
			terminate();
	}
	
	/**
	 * A variable storing the current amount of hit points.
	 * 
	 * If the amount of hit points is zero, a game object dies.
	 */
	private int hitPoints;
	
	/**
	 * Returns the world where this game object is located.
	 * 
	 * Null is returned if this game object doesn't belong to any world.
	 */
	protected World getWorld() {
		return world;
	}
	
	/**
	 * Check whether the given world is a valid world for this game object.
	 * @param 	world
	 * 			The world to check.
	 */
	protected abstract boolean isValidWorld(World world);
	
	/**
	 * A method to set the world of this game object to the given world.
	 * @param	world
	 * 			The world to set.
	 * @pre		The given world must be a valid world.
	 * 			| isValidWorld(world)
	 * @post	The game object refers to the given world.
	 * 			| new.getWorld() == world
	 */
	public void setWorld(World world) {
		assert isValidWorld(world);
		this.world = world;
	}
	
	/**
	 * A variable storing the world where this game object is located.
	 * If this game object does not yet belong to any world, this variable 
	 * references null.
	 */
	private World world = null;
	
	/**
	 * Returns the current horizontal direction of this game object.
	 */
	@Basic
	public Direction getHorDirection() {
		return horDirection;
	}
	
	/**
	 * Set the horizontal direction of this game object to the given horizontal direction.
	 * 
	 * @param	horDirection
	 * 			Horizontal direction to set.
	 * @pre		The given direction must be a valid direction.
	 * 			| isValidDirection(horDirection)
	 * @post	The new horizontal direction of this game object is set to the given direction.
	 * 			| new.getHorDirection() == horDirection
	 */
	@Model
	protected void setHorDirection(Direction horDirection) {
		this.horDirection = horDirection;
	}
	
	/**
	 * A variable storing the horizontal direction.
	 */
	private Direction horDirection = Direction.NULL;
	
	/**
	 * Return the initial horizontal velocity of this game object.
	 */
	@Basic @Immutable @Model
	protected double getInitHorVelocity(){
		return this.initHorVelocity;
	}
	
	/**
	 * Checks whether the given initial horizontal velocity is valid.
	 * 
	 * @param	initHorVelocity
	 * 			The initial horizontal velocity to check.
	 * @return	True if the given initial horizontal velocity is greater than
	 * 			or equal to 0.
	 * 			| result == (initHorVelocity >= 0)
	 */
	@Model
	protected boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity >= 0);
	}
	
	/**
	 * A variable storing the initial horizontal velocity of this game object.
	 */
	private final double initHorVelocity;
	
	/**
	 * Return the current maximum horizontal velocity.
	 */
	@Basic @Model
	protected double getMaxHorVelocity(){
		return this.maxHorVelocity;
	}
	
	/**
	 * Checks whether this game object can have the given maximum 
	 * horizontal velocity as its maximum horizontal velocity.
	 * 
	 * @param	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @return	True if the given maximum horizontal velocity is above or 
	 * 			equal to the initial horizontal velocity.
	 * 			| if (maxHorVelocity >= getInitHorVelocity)
	 * 			|	then result == true
	 */
	@Model
	protected boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
		if (maxHorVelocity >= getInitHorVelocity())
			return true;
		else
			return false;
	}
	
	/**
	 * Sets the maximum horizontal velocity to the given value.
	 * 
	 * @param 	maxHorVelocity
	 * 			The maximum horizontal velocity to set.
	 * @post	If the given maximum horizontal velocity is valid,
	 * 			the maximum horizontal velocity is set to the given value.
	 * 			| if (canHaveAsMaxHorVelocity(maxHorVelocity))
	 * 			| 	then new.getMaxHorVelocity() == maxHorVelocity
	 */
	@Model
	protected void setMaxHorVelocity(double maxHorVelocity){
		if (canHaveAsMaxHorVelocity(maxHorVelocity))
			this.maxHorVelocity = maxHorVelocity;
	}
	
	/**
	 * A variable storing the current maximal velocity of this game object.
	 */
	private double maxHorVelocity;
	
	/**
	 * Return the current horizontal velocity of this game object.
	 */
	@Basic
	public double getHorVelocity() {
		return horVelocity;
	}
	/**
	 * Checks whether this game object can have the given horizontal velocity as
	 * its horizontal velocity.
	 * 
	 * @param	horVelocity
	 * 			The horizontal velocity to check.
	 * @return	True if the given horizontal velocity is above or equal to
	 * 			the initial horizontal velocity of this game object and below or 
	 * 			equal to the maximum horizontal velocity of this game object, or
	 * 			if the given horizontal velocity is 0.
	 * 			| result == (horVelocity == 0) ||
	 * 			|			((horVelocity >= getInitHorVelocity()) &&
	 * 			|			(horVelocity <= getMaxHorVelocity())) 
	 */
	@Model
	protected boolean canHaveAsHorVelocity(double horVelocity){
		return  ((horVelocity >= getInitHorVelocity()) &&
				(horVelocity <= getMaxHorVelocity()));
	}
	
	/**
	 * Set the horizontal velocity to a given value.
	 * 
	 * @param 	horVelocity
	 * 			The new horizontal velocity for this game object.
	 * @post	If the given horizontal velocity is possible,
	 * 			the new horizontal velocity of this game object is equal to 
	 * 			the given horizontal velocity.
	 * 			| if (canHaveAsHorVelocity())
	 * 			| 	then new.getHorVelocity() == horVelocity
	 */
	@Model
	protected void setHorVelocity(double horVelocity) {
		if (canHaveAsHorVelocity(horVelocity))
			this.horVelocity = horVelocity;
	}
	
	/**
	 * A variable storing the current horizontal velocity of this game object.
	 * This value will always be a positive number of type double, or zero.
	 */
	private double horVelocity = 0;
	
	/**
	 * A method to end the movement in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction in which the movement must be ended.
	 * @pre		The given direction must be left or right.
	 * 			| direction == (Direction.LEFT) || direction == (Direction.RIGHT)
	 * @post	If the given direction is left or right, the horizontal velocity is
	 * 			is zero, the new horizontal direction is null and the time sum is reset to zero.
	 * 			| if (direction == Direction.LEFT || direction == Direction.RIGHT)
	 * 			|	then new.getHorVelocity() == 0 && new.getHorDirection() == Direction.NULL &&
	 * 			|		 new.getTimeSum() == 0
	 */
	public void endMovement(Direction direction){
		assert (direction != Direction.NULL);
		if (isMoving(direction)){
			if (direction == Direction.LEFT || direction == Direction.RIGHT){
				setHorVelocity(0);
				setHorDirection(Direction.NULL);
				setTimeSum(0);
			} 
		}
	}
	
	/**
	 * Checks whether the game object is moving.
	 * 
	 * @return	True if and only if the game object is moving to the left or to the right.
	 * 			| result == (getHorDirection() != Direction.NULL)
	 */
	protected boolean isMoving(){
		return (getHorDirection() != Direction.NULL);
	}
	
	/**
	 * Check whether the game object is moving in the given direction.
	 * @param 	direction
	 * 			The direction to check.
	 * @return	True if the given direction is a horizontal direction and
	 * 			the current horizontal direction is equal to the given direction.
	 * 			| if (getHorDirection() == direction)
	 * 			|	then result == true
	 */
	public boolean isMoving(Direction direction){
		if(getHorDirection() == direction)
			return true;
		else
			// Vrij te kiezen volgens mij.
			return false;
	}
	
	/**
	 * Method to update the position and velocity of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this game object. 
	 * @effect	The position is updated with the given timeDuration.
	 * 			| updatePosition(timeDuration)
	 * @effect	The horizontal velocity is updated with the given timeDuration.
	 * 			| updateHorVelocity(timeDuration)
	 * @effect	The given timeDuration is added to the timeSum.
	 * 			| counter(timeDuration)
	 * @throws	IllegalTimeIntervalException(this)
	 * 			The given timeduration is not a valid time interval.
	 * 			| !(isValidTimeInterval(timeDuration))
	 */
	public void advanceTime(double timeDuration) throws IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updatePosition(timeDuration);
		updateHorVelocity(timeDuration);
		counter(timeDuration);
	}

	/**
	 * A method to check whether the given time interval is a valid
	 * time interval to simulate the movement of a game object.
	 * 
	 * @param 	timeDuration
	 * 			The time interval to check.
	 * @return	True if and only if the given time interval is not negative
	 * 			and it is not greater than 0.2.
	 * 			| result == (timeDuration >= 0 && timeDuration <= 0.2)
	 */
	@Model
	protected static boolean isValidTimeInterval(double timeDuration){
		return (timeDuration >= 0 && timeDuration <= 0.2);
	}
	
	/** 
	 * Method to update the position of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this game object.
	 */
	protected abstract void updatePosition(double timeDuration);
	
	/**
	 * A method to check whether this game object is overlapping with a given tile.
	 * 
	 * @param 	tile
	 * 			The tile to check overlapping with.
	 * @return	True if this object and the given tile both occupy at least one exact same pixel.
	 * 			| ...
	 */
	public boolean isOverlappingWith(Tile tile){
		
		try {
			return !(((getPosition().getDisplayedXPosition()+getWidth()-1) < tile.getXPosition()) ||
					((tile.getXPosition()+getWorld().getTileSize()-1) < getPosition().getDisplayedXPosition())
					|| ((getPosition().getDisplayedYPosition() + getHeight() -1) < tile.getYPosition())
					|| ((tile.getYPosition()+getWorld().getTileSize()-1) < getPosition().getDisplayedYPosition()));
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/**
	 * A method to check whether this game object is overlapping with another game object.
	 * 
	 * @param 	object
	 * 			The game object to check overlapping with.
	 * @return	True if this object and the other game object both occupy at least one exact same pixel.
	 * 			| ...
	 */
	public boolean isOverlappingWith(GameObject object){
		try {
			return !(((getPosition().getDisplayedXPosition()+getWidth()-1) < 
					   object.getPosition().getDisplayedXPosition()) ||
					((object.getPosition().getDisplayedXPosition()+ object.getWidth()-1) < 
							getPosition().getDisplayedXPosition()) ||
					((getPosition().getDisplayedYPosition() + getHeight() - 1) < 
					  object.getPosition().getDisplayedYPosition()) ||
					((object.getPosition().getDisplayedYPosition()+object.getHeight()-1) 
					  < getPosition().getDisplayedYPosition()));
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public boolean isOverlappingWith(Terrain terrain){
		if (getWorld() == null)
			return false;
		HashSet<Tile> affectedTiles = getWorld().getTilesIn(getPosition().getDisplayedXPosition(),
				getPosition().getDisplayedYPosition(), getPosition().getDisplayedXPosition()+getWidth()-1,
				getPosition().getDisplayedYPosition()+getHeight()-1);
		for (Tile tile: affectedTiles){
			if (tile.getGeoFeature() == terrain)
				return true;
		}
		return false;
	}
	
	/**
	 * Check whether this game object collides with a given tile in a given direction.
	 * 
	 * @param 	direction
	 * 			The direction to check collision in.
	 * @param 	tile
	 * 			The tile to check collision with.
	 * @pre		The given direction must differ from null.
	 * 			| direction != Direction.NULL
	 * @return	False if this game object is not overlapping with the given tile.
	 * 			| if (! this.isOverlappingWith(tile)
	 * 			|	then result == false
	 * 			Else true if at least one pixel at the most far end in the given direction
	 * 			of this game object is located in the given tile.
	 * 			| ...
	 */
	public boolean isColliding(Direction direction, Tile tile){
		assert (direction != Direction.NULL);
		if (!isOverlappingWith(tile))
			return false;
		int[][] positionsToCheck;
		if(direction == Direction.DOWN)
			positionsToCheck = getLowerPerimeter();
		else if(direction == Direction.UP)
			positionsToCheck = getUpperPerimeter();
		else if(direction == Direction.RIGHT)
			positionsToCheck = getRightPerimeter();
		else if(direction == Direction.LEFT)
			positionsToCheck = getLeftPerimeter();
		else
			positionsToCheck = new int[0][0];
		for(int index=1;index<positionsToCheck.length-2;index++){
			if((getWorld().getBelongingTileXPosition(positionsToCheck[index][0]) == tile.getTileXPos()) 
				&& getWorld().getBelongingTileYPosition(positionsToCheck[index][1]) == tile.getTileYPos())
				return true;
		}
		return false;
	}
	
	/** Check whether this game object collides with another game object in a given direction.
	 * 
	 * @param 	direction
	 * 			The direction to check collision in.
	 * @param 	object
	 * 			The game object to check collision with.
	 * @pre		The given direction must differ from null.
	 * 			| direction != Direction.NULL
	 * @return	False if this game object is not overlapping with the given game object.
	 * 			| if (! this.isOverlappingWith(object)
	 * 			|	then result == false
	 * 			Else true if at least one pixel at the most far end in the given direction
	 * 			of this game object is also occupied by the other game object.
	 * 			| ...
	 */
	public boolean isColliding(Direction direction, GameObject object){
		assert (direction != Direction.NULL);
		if (!isOverlappingWith(object))
			return false;
		int[][] positionsToCheck;
		if(direction == Direction.DOWN)
			positionsToCheck = getLowerPerimeter();
		else if(direction == Direction.UP)
			positionsToCheck = getUpperPerimeter();
		else if(direction == Direction.RIGHT)
			positionsToCheck = getRightPerimeter();
		else if(direction == Direction.LEFT)
			positionsToCheck = getLeftPerimeter();
		else
			positionsToCheck = new int[0][0];
		for(int index=1;index<positionsToCheck.length-2;index++){
			if(object.occupiesPosition(new Position(positionsToCheck[index][0],
			   positionsToCheck[index][1])))
				return true;
		}
		return false;
	}

	/**
	 * A method to update the horizontal velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new horizontal velocity.
	 * @effect	The new horizontal velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this game object.
	 */
	@Model
	protected void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity();
		if (newVel > getMaxHorVelocity()){
			setHorVelocity(getMaxHorVelocity());
		}
		else
			setHorVelocity(newVel);
	}

	/**
	 * Return the stored period of elapsed time .
	 */
	@Basic @Model
	protected double getTimeSum() {
		return timeSum;
	}

	/**
	 * Sets the time sum to a given sum.
	 * 
	 * @param 	timeSum
	 * 			The timeSum to set.
	 * @post	The new time sum is equal to the given timeSum.
	 * 			| new.getTimeSum() = timeSum 
	 */
	@Model
	protected void setTimeSum(double timeSum) {
		this.timeSum = timeSum;
	}
	
	/**
	 * A method to increment the time sum with the given time duration.
	 * 
	 * @param 	timeDuration
	 * 			The time duration to add to time sum.
	 * @post	The new time sum is incremented with the given time duration.
	 * 			| new.getTimeSum() == this.getTimeSum() + timeDuration
	 */
	@Model
	protected void counter(double timeDuration){
		setTimeSum(getTimeSum()+timeDuration);
	}
	
	/**
	 * A variable storing a period of elapsed time. This variable 
	 * functions as a timer that increments subsequent time intervals
	 * in the method advanceTime.
	 */
	private double timeSum;

	/**
	 * Return the index of the current sprite in the array of sprites,
	 * belonging to this game object.
	 */
	@Basic @Model
	protected int getIndex() {
		return this.index;
	}
	
	/**
	 * Checks whether the given index is a valid index.
	 * 
	 * @param 	index
	 * 			The index to check.
	 * @return	True if and only if the index is between zero and the length of sprites.
	 * 			| if(index >= 0)
	 * 			|	then result == true
	 */
	protected boolean isValidIndex(int index){
		return (index >= 0);
	}

	/**
	 * Sets the index of the current sprite to the given index.
	 * 
	 * @param	index
	 * 			The index to set.
	 * @pre		The given index must be a valid index for this game object.
	 * 			| isValidIndex(index)
	 * @post	The new index is equal to the given index.
	 * 			| new.getIndex() == index
	 */
	protected void setIndex(int index) {
		assert isValidIndex(index);
		this.index = index;
	}
	
	/**
	 * A variable storing the index of the current sprite.
	 * The index is an integer number and refers to the position
	 * of the current sprite in the array of sprites, belonging to this game object.
	 */
	private int index;
	
	/**
	 * A method to update the sprite index.
	 */
	public abstract void updateSpriteIndex();
	
	/**
	 * A method to retrieve the sprite belonging to the current state
	 * of this game object.
	 * 
	 * @return	The sprite located at the current index in the array
	 * 			of sprites of this game object.
	 * 			| result == getAllSprites[getIndex()]
	 */
	public Sprite getCurrentSprite(){
		return getAllSprites()[getIndex()];
	}
	
	/**
	 * Return the array of sprites representing the different states of this game object.	
	 */
	protected Sprite[] getAllSprites(){
		return this.sprites;
	}

	/**
	 * A variable storing all possible sprites for this character.
	 * The sprites are images. Each sprite represents 
	 * a different state of this game object.
	 */
	private final Sprite[] sprites;
	
	/**
	 * Check whether or not this game object has been terminated.
	 */
	public boolean isTerminated(){
		return isTerminated;
	}
	
	/**
	 * Terminate this game object.
	 * 
	 * @pre		The current number of hit points must be equal to zero.
	 * 			| getHitPoints() == 0
	 * @effect	The game object is terminated.
	 * 			| new.isTerminated() == true
	 */
	protected void terminate(){
		assert (getHitPoints()==0);
		this.isTerminated = true;
	}
	
	/**
	 * A variable registering whether or not this game object has been terminated.
	 */
	private boolean isTerminated;

	
}
