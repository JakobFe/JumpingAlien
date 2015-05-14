package jumpingalien.model.game;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;

/**
 * A class concerning game objects with a position, a horizontal velocity,
 * sprites, hit points and a world.
 *
 * @invar	The horizontal direction of this game object must be valid.
 * 			| isValidHorDirection(getHorDirection())
 * @invar 	The initial horizontal velocity of this game object must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of this game object must be valid.
 * 			| canHaveAsMaxHorVelocity(getMaxHorVelocity())
 * @invar	The hit points of this game object must be a valid number of hit points.
 * 			| isValidHitPoints(hitPoints)
 * @invar	This game object must have a proper world.
 * 			| hasProperWorld()
 * @invar	The position belonging to this game object must be a valid position
 * 			for this game object in view of its world.
 * 			| isValidPosition(getPosition(),getWorld())
 *  
 * @author 	Jakob Festraets, Vincent Kemps
 * @version	1.1
 * 
 */
public abstract class GameObject {
	
	/**
	 * Initialize this new game object with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites, given number of hit points and no world.
	 * 
	 * @param 	position
	 * 			The start position for this game object.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this game object.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity for this game object.
	 * @param	sprites
	 * 			An array containing the different sprites for this game object.
	 * @param	hitPoints
	 * 			The hit points for this new game object. 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @post	If the given number of hit points is valid, the new game object
	 * 			has this number as its hit points.
	 * 			| if(isValidHitPoints(hitPoints)
	 * 			|	then new.getHitPoints() == hitPoints
	 * @post	This new game object has the given sprites as its sprites.
	 * 			| new.getAllSprites() == sprites
	 * @post	This game object is initialized with position (x,y).
	 * 			| new.getPosition() == position
	 * @post	This game object has no world.
	 * 			| new.getWorld() == null
	 * @throws	IllegalArgumentException
	 * 			This game object can not have the given position as its position.
	 * 			| !isValidPosition(position,null)
	 */
	@Raw@Model
	protected GameObject(Position position, double initHorVelocity, double maxHorVelocity, Sprite[] sprites,
			int hitPoints, Program program) throws IllegalArgumentException{
		assert isValidInitHorVelocity(initHorVelocity);
		assert canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity);
		if(!isValidPosition(position,null))
			throw new IllegalArgumentException("Invalid position!");
		setPosition(position);
		setHitPoints(hitPoints);
		this.initHorVelocity = initHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		this.sprites = sprites;
		this.program = program;
	}
	
	/**
	 * Initialize this new game object with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites, given number of hit points and no world.
	 * 
	 * @param 	position
	 * 			The start position for this game object.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this game object.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity for this game object.
	 * @param	sprites
	 * 			An array containing the different sprites for this game object.
	 * @param	hitPoints
	 * 			The hit points for this new game object. 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @post	If the given number of hit points is valid, the new game object
	 * 			has this number as its hit points.
	 * 			| if(isValidHitPoints(hitPoints)
	 * 			|	then new.getHitPoints() == hitPoints
	 * @post	This new game object has the given sprites as its sprites.
	 * 			| new.getAllSprites() == sprites
	 * @post	This game object is initialized with position (x,y).
	 * 			| new.getPosition() == position
	 * @post	This game object has no world.
	 * 			| new.getWorld() == null
	 * @throws	IllegalArgumentException
	 * 			This game object can not have the given position as its position.
	 * 			| !isValidPosition(position,null)
	 */
	@Raw@Model
	protected GameObject(Position position, double initHorVelocity, double maxHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalArgumentException{
		assert isValidInitHorVelocity(initHorVelocity);
		assert canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity);
		if(!isValidPosition(position,null))
			throw new IllegalArgumentException("Invalid position!");
		setPosition(position);
		setHitPoints(hitPoints);
		this.initHorVelocity = initHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		this.sprites = sprites;
		this.program = null;
	}
	
	@Raw@Model
	protected GameObject(Position position, double initHorVelocity, Sprite[] sprites,
			int hitPoints,Program program) throws IllegalArgumentException{
		this(position,initHorVelocity,initHorVelocity,sprites,hitPoints,program);
	}
	
	/**
	 * Initialize this new game object with given x position, given y position, 
	 * given initial horizontal velocity given sprites and given hit points.
	 * 
	 * In this case the maximum horizontal velocity will be equal to the initial
	 * horizontal velocity.
	 * 
	 * @param 	position
	 * 			The start position for this game object.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity and maximum horizontal 
	 * 			velocity for this game object.
	 * @param	sprites
	 * 			An array containing the different sprites for this game object.
	 * @param	hitPoints
	 * 			The hit points for this new game object. 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @effect	This game object is initialized with the given x position, given y position, 
	 * 			given initial horizontal velocity as its initial and maximum horizontal 
	 * 			velocity, given sprites and given hit points.
	 * 			| this(position,initHorVelocity,initHorVelocity,sprites,hitPoints)
	 */
	@Raw@Model
	protected GameObject(Position position, double initHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalArgumentException{
		this(position,initHorVelocity,initHorVelocity,sprites,hitPoints);
	}
	
	/**
	 * Return the current position of this game object.
	 */
	@Basic
	public Position getPosition(){
		return position.copy();
	}
	
	/**
	 * Check whether or not the given position is a valid position.
	 * 
	 * @param 	position
	 * 			The position to check.
	 * @param	world
	 * 			The world to check this position against.
	 * @return	True if the world belonging to the given position and
	 * 			the given world are the same.
	 * 			| result == (world == position.getWorld())
	 */
	protected static boolean isValidPosition(Position position, World world){
		return world == position.getWorld();
	}
	
	/**
	 * Set the position of this game object to the given position.
	 * 
	 * @param	position
	 * 			The position to set.
	 * @post	The game object refers to the given position.
	 * 			| new.getPosition() == position
	 * @throws	IllegalArgumentException
	 * 			The given position is not a valid position for this game object.
	 * 			| !isValidPosition(position,getWorld()) 
	 */
	protected void setPosition(Position position)
			throws IllegalArgumentException{
		if(!isValidPosition(position, getWorld()))
			throw new IllegalArgumentException("Invalid world!");
		this.position = position;
	}
	
	/**
	 * A method to convert a double array to a position with a given world.
	 * 
	 * @param 	doubleArray
	 * 			The position in the form of a double array.
	 * @param 	world
	 * 			The world to add to this new position.
	 * @pre		The given double array must have two entries.
	 * 			| doubleArray.length() == 2
	 * @return	A new position with the first entry of the double array
	 * 			as its x position, the second entry of the double array
	 * 			as its y position and the given world as its world.
	 * @throws	IllegalXPositionException
	 * 			The first entry is not a valid x position in the given world.
	 * 			| !Position.isValidXPosition(doubleArray[0],world)
	 * @throws	IllegalYPositionException
	 * 			The first entry is not a valid y position in the given world.
	 * 			| !Position.isValidYPosition(doubleArray[1],world)
	 */
	protected static Position toPosition(double[] doubleArray, World world)
		throws IllegalXPositionException,IllegalYPositionException{
		assert doubleArray.length == 2;
		return new Position(doubleArray[0],doubleArray[1],world);
	}
	
	/**
	 * A variable storing the current position of this game object.
	 */
	private Position position;
	
	/**
	 * Return the width of the current sprite of this game object.
	 */
	public int getWidth(){
		return getCurrentSprite().getWidth();
	}
	
	/**
	 * Return the height of the current sprite of this game object.
	 */
	public int getHeight(){
		return getCurrentSprite().getHeight();
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the outer left side of this object.
	 * 
	 * @return	The resulting matrix will have a number of rows equal to 
	 * 			the height of this game object.
	 * 			| result.length == getHeight()
	 * @return	The resulting matrix will have 2 columns.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb].length == 2
	 * @return	Each row will have as first entry the current displayed x position
	 * 			of this game object.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][0] = getPosition().getDisplayedXPosition()
	 * @return	Each row will have as second entry the current displayed y position
	 * 			incremented with the number of the row.
	 *			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][1] = getPosition().getDisplayedYPosition() + rowNb 			
	 */
	protected int[][] getLeftPerimeter(){
		int xPos = getPosition().getDisplayedXPosition();
		int yPos = getPosition().getDisplayedYPosition();
		int [][] result = new int[getHeight()][2];
		for(int index=0;index<getHeight();index++){
			result[index][0] = xPos;
			result[index][1] = yPos + index;
		}
		return result;
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the outer right side of this object.
	 * 
	 * @return	The resulting matrix will have a number of rows equal to 
	 * 			the height of this game object.
	 * 			| result.length == getHeight()
	 * @return	The resulting matrix will have 2 columns.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb].length == 2
	 * @return	Each row will have as first entry the current displayed x position
	 * 			of this game object incremented with the width of this game object
	 * 			and decremented with 1.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][0] = getPosition().getDisplayedXPosition() +
	 * 			|					   getWidth() - 1
	 * @return	Each row will have as second entry the current displayed y position
	 * 			incremented with the number of the row.
	 *			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][1] = getPosition().getDisplayedYPosition() + rowNb 	
	 */
	protected int[][] getRightPerimeter(){
		int xPos = getPosition().getDisplayedXPosition() + getWidth()-1;
		int yPos = getPosition().getDisplayedYPosition();
		int [][] result = new int[getHeight()][2];
		for(int index=0;index<getHeight();index++){
			result[index][0] = xPos;
			result[index][1] = yPos + index;
		}
		return result;
	}

	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the bottom of this object.
	 * 
	 * @return	The resulting matrix will have a number of rows equal to 
	 * 			the width of this game object.
	 * 			| result.length == getWidth()
	 * @return	The resulting matrix will have 2 columns.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb].length == 2
	 * @return	Each row will have as first entry the current displayed x position
	 * 			of this game object incremented with the number of the row.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][0] = getPosition().getDisplayedXPosition() + rowNb
	 * @return	Each row will have as second entry the current displayed y position.
	 *			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][1] = getPosition().getDisplayedYPosition() 	
	 */
	protected int[][] getLowerPerimeter(){
		int xPos = getPosition().getDisplayedXPosition();
		int yPos = getPosition().getDisplayedYPosition();
		int [][] result = new int[getWidth()][2];
		for(int index=0;index<getWidth();index++){
			result[index][0] = xPos + index;
			result[index][1] = yPos;
		}
		return result;
	}
	
	/**
	 * A method to return a matrix containing all positions of this 
	 * game object that are located at the top of this object.
	 * 
	 * @return	The resulting matrix will have a number of rows equal to 
	 * 			the width of this game object.
	 * 			| result.length == getWidth()
	 * @return	The resulting matrix will have 2 columns.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb].length == 2
	 * @return	Each row will have as first entry the current displayed x position
	 * 			of this game object incremented with the number of the row.
	 * 			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][0] = getPosition().getDisplayedXPosition() + rowNb
	 * @return	Each row will have as second entry the current displayed y position 
	 * 			incremented with the height of this object and decremented with 1.
	 *			| for each rowNb in 0..(result.length-1)
	 * 			|	result[rowNb][1] = getPosition().getDisplayedYPosition() +
	 * 			|					   getHeigth() - 1 	
	 */			
	protected int[][] getUpperPerimeter(){
		int xPos = getPosition().getDisplayedXPosition();
		int yPos = getPosition().getDisplayedYPosition() + getHeight()-1;
		int [][] result = new int[getWidth()][2];
		for(int index=0;index<getWidth();index++){
			result[index][0] = xPos + index;
			result[index][1] = yPos;
		}
		return result;
	}
	
	/**
	 * A method to return the perimeter at the side of the given direction.
	 * @param 	direction
	 * 			An indicator for which perimeter to select.
	 * @return	If the given direction is down, the lower perimeter is returned.
	 * 			| if (direction == Direction.DOWN)
	 * 			|	then result = getLowerPerimeter()
	 * 			Else if the given direction is up, the upper perimeter is returned.
	 * 			| if (direction == Direction.UP)
	 * 			|	then result = getUpperPerimeter()
	 *			Else if the given direction is right, the right perimeter is returned.
	 * 			| if (direction == Direction.RIGHT)
	 * 			|	then result = getRightPerimeter()
	 * 			Else if the given direction is left, the left perimeter is returned.
	 * 			| if (direction == Direction.LEFT)
	 * 			|	then result = getLeftPerimeter()
	 * 			Else, an empty matrix of integers is returned.
	 * 			| else
	 * 			|	result == new int[0][0]
	 */
	protected int[][] getPerimeter(Direction direction){
		if(direction == Direction.DOWN)
			return getLowerPerimeter();
		else if(direction == Direction.UP)
			return getUpperPerimeter();
		else if(direction == Direction.RIGHT)
			return getRightPerimeter();
		else if(direction == Direction.LEFT)
			return getLeftPerimeter();
		else
			return new int[0][0];
	}
	
	/**
	 * A method to check whether this game object occupies a given position.
	 * 
	 * A game object occupies a position if this position is located in between the 
	 * borders of the current sprite of this game object.
	 * 
	 * @param 	position
	 * 			The position to check.
	 * @return	True if the given position is located between the bottom left,
	 * 			bottom right, top left and top right corner of this game object.
	 * 			| result == 
	 * 			| (position.getDisplayedXPosition() >= getPosition().getDisplayedXPosition() &&
	 * 			|  position.getDisplayedXPosition() <= getPosition().getDisplayedXPosition() + getWidth() - 1 &&
	 * 			|  position.getDisplayedYPosition() >= getPosition().getDisplayedYPosition() &&
	 * 			|  position.getDisplayedYPosition() <= getPosition().getDisplayedYPosition() + getHeight() - 1)
	 */
	protected boolean occupiesPosition(Position position){
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
	 * Check if this game object is dead.
	 * 
	 * A game object that is dead will soon be terminated.
	 * 
	 * @return	True if the current number of hit points is zero.
	 * 			| result == (getHitPoints() == 0)
	 */
	public boolean isDead() {
		return getHitPoints() == 0;
	}

	/**
	 * Set the number of hit points to the given number of hit points.
	 * 
	 * @param	hitPoints
	 * 			The amount of hit points to set.
	 * @post	If the given amount of hit points is a valid number of hit points,
	 * 			the new amount of hit points is equal to the given amount 
	 * 			of hit points.
	 * 			| if(isValidHitPoints()
	 * 			|	then new.getHitPoints() == hitPoints
	 * @post	If the given amount of hit points is negative, the new amount of 
	 * 			hit points is zero.
	 * 			| if(hitPoints < 0)
	 * 			|	then new.getHitPoints() == 0
	 */
	protected void setHitPoints(int hitPoints) {
		if (isValidHitPoints(hitPoints))
			this.hitPoints = hitPoints;
		else if(hitPoints < 0){
			this.hitPoints = 0;
		}
	}
	
	/**
	 * A method to subtract an amount of hit points from the current 
	 * number of hit points.
	 *  
	 * @param 	amount
	 * 			The amount of hit points to subtract from the current
	 * 			number of hit points.
	 * @effect	The hit points are set to the current number of hit points
	 * 			subtracted with the given amount of hit points.
	 * 			| setHitPoints(getHitPoints()-amount)
	 */
	protected void subtractHp(int amount){
		setHitPoints(getHitPoints()-amount);
	}
	
	/**
	 * A method to add an amount of hit points to the current 
	 * number of hit points.
	 *  
	 * @param 	amount
	 * 			The amount of hit points to add from the current
	 * 			number of hit points.
	 * @effect	The hit points are set to the current number of hit points
	 * 			incremented with the given amount of hit points.
	 * 			| setHitPoints(getHitPoints()+amount)
	 */
	protected void addHp(int amount){
		setHitPoints(getHitPoints()+amount);
	}
	
	/**
	 * A method to update the hit points of this game object.
	 * A game object can damage other objects and can be damaged
	 * by other game objects.
	 */
	protected abstract void updateHitPoints();
	
	/**
	 * A method to get damage by another game object.
	 * 
	 * @param	other
	 * 			The game object to take damage from.
	 */
	protected abstract void getHurtBy(GameObject other);
	
	/**
	 * A method to damage another game object.
	 * 
	 * @param	other
	 * 			The game object to damage.
	 */
	protected abstract void hurt(GameObject other);
	
	/**
	 * A variable storing the current amount of hit points.
	 */
	private int hitPoints;
	
	/**
	 * Returns the world where this game object is located.
	 * 
	 * Null is returned if this game object doesn't belong to any world.
	 */
	// ZOU NIET PUBLIC MOGEN ZIJN!!!!!
	@Basic
	public World getWorld() {
		return world;
	}
	/**
	 * A method to check whether a world is a valid world for this game object.
	 * 
	 * @param 	world
	 * 			The world to check.
	 * @return	True if and only if the world is null or this game object can
	 * 			be added to the given world.
	 * 			| result == (world == null) || canBeAddedTo(world)
	 */
	protected boolean isValidWorld(World world){
		return (world == null) || canBeAddedTo(world);
	}
	
	/**
	 * Check whether this game object can be added to the given world.
	 * 
	 * @param 	world
	 * 			The world to check.
	 * @return	False if the given world is not effective.
	 * 			| if (world == null)
	 * 			|	then result == false
	 * 			Otherwise false if the world can not add game objects. 
	 * 			| else if (!world.canAddGameObjects())
	 * 			|	then result == false
	 */
	protected  boolean canBeAddedTo(World world){
		if(world == null)
			return false;
		else
			return (world.canAddGameObjects());
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * 
	 * @return	True if there is no effective world attached to this game object.
	 * 			| if (getWorld() == null)
	 * 			|	then result == true
	 */
	protected abstract boolean hasProperWorld();
	
	/**
	 * A method to set the world of this game object to the given world.
	 * 
	 * @param	world
	 * 			The world to set.
	 * @pre		The given world must be a valid world for this game object.
	 * 			| isValidWorld(world)
	 * @post	The game object refers to the given world.
	 * 			| new.getWorld() == world
	 */
	protected void setWorld(@Raw World world) {
		assert isValidWorld(world);
		this.world = world;
	}
	
	/**
	 * A variable storing the world where this game object is located.
	 * If this game object does not yet belong to any world, this variable 
	 * references null.
	 */
	private World world = null;
	
	public Program getProgram() {
		return program;
	}
	
	protected abstract boolean canHaveProgram();
	
	private final Program program;

	/**
	 * Returns the current horizontal direction of this game object.
	 */
	@Basic
	public Direction getHorDirection() {
		return horDirection;
	}
	
	/**
	 * A method to check whether a given direction is a valid horizontal direction.
	 * 
	 * @param 	direction
	 * 			The direction to check.
	 * @return	True if the given direction is null, left or right.
	 * 			| result == (direction == Direction.NULL || direction == Direction.LEFT || 
	 *			| 			 direction == Direction.RIGHT)
	 */
	protected boolean isValidHorDirection(Direction direction){
		return (direction == Direction.NULL || direction == Direction.LEFT || 
				direction == Direction.RIGHT);
	}
	
	/**
	 * Set the horizontal direction of this game object to the given horizontal direction.
	 * 
	 * @param	horDirection
	 * 			Horizontal direction to set.
	 * @pre		The given direction must be a valid horizontal direction.
	 * 			| isValidHorDirection(horDirection)
	 * @post	The new horizontal direction of this game object is set to the given direction.
	 * 			| new.getHorDirection() == horDirection
	 */
	@Model
	protected void setHorDirection(Direction horDirection) {
		assert isValidHorDirection(horDirection);
		this.horDirection = horDirection;
	}
	
	/**
	 * A variable storing the horizontal direction.
	 */
	private Direction horDirection = Direction.NULL;
	
	/**
	 * Return the initial horizontal velocity of this game object.
	 */
	@Basic@Immutable@Model
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
	 * This variable will not change once initialized. 
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
	 * horizontal velocity as its maximum horizontal velocity 
	 * in combination with a given initial horizontal velocity.
	 * 
	 * @param	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @param	initHorVelocity
	 * 			The initial horizontal velocity to check against.
	 * @return	True if the given maximum horizontal velocity is above or 
	 * 			equal to the initial horizontal velocity.
	 * 			| if (maxHorVelocity >= initHorVelocity)
	 * 			|	then result == true
	 */
	@Model
	protected boolean canHaveAsMaxHorVelocity(double maxHorVelocity, double initHorVelocity){
		return (maxHorVelocity >= initHorVelocity);
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
		if (canHaveAsMaxHorVelocity(maxHorVelocity,getInitHorVelocity()))
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
	 * A method to check if a given initial horizontal velocity,
	 * a current horizontal velocity and a maximum horizontal velocity are acceptable.
	 * 
	 * @param 	initHorVelocity
	 * 			The initial horizontal velocity to check.
	 * @param 	horVelocity
	 * 			The current horizontal velocity to check.
	 * @param 	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @return	True if the given horizontal velocity is above or equal to
	 * 			the given initial horizontal velocity and below or 
	 * 			equal to the given maximum horizontal velocity, or
	 * 			if the given horizontal velocity is 0.
	 * 			| result == (horVelocity == 0) ||
	 * 			|			((horVelocity >= initHorVelocity) &&
	 * 			|			(horVelocity <= maxHorVelocity)) 
	 * 
	 */
	private boolean matchesInitHorVelocityHorVelocityMaxHorVelocity
	(double initHorVelocity, double horVelocity, double maxHorVelocity){
		return (horVelocity == 0) ||
				((horVelocity >= initHorVelocity) && (horVelocity <= maxHorVelocity));
	}
	
	/**
	 * Checks whether this game object can have the given horizontal velocity as
	 * its horizontal velocity.
	 * 
	 * @param	horVelocity
	 * 			The horizontal velocity to check.
	 * @return	True if the given horizontal velocity matches with the
	 * 			initial horizontal velocity and the maximum horizontal velocity
	 * 			of this game object.
	 * 			| result == matchesInitHorVelocityHorVelocityMaxHorVelocity
	 *			|			(getInitHorVelocity(), horVelocity, getMaxHorVelocity())
	 */
	@Model
	protected boolean canHaveAsHorVelocity(double horVelocity){
		return  matchesInitHorVelocityHorVelocityMaxHorVelocity
				(getInitHorVelocity(), horVelocity, getMaxHorVelocity());
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
	 * Return the maximum horizontal acceleration of the character.
	 */
	@Basic @Model
	protected double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this character.
	 */
	private double maxHorAcceleration;

	/**
	 * Return the current horizontal acceleration of this character.
	 */
	@Basic
	public double getHorAcceleration() {
		return horAcceleration;
	}
	
	protected abstract boolean canHaveAsHorAcceleration(double horAcceleration);
	
	/**
	 * Sets the horizontal acceleration to the given value.
	 * 
	 * @param 	horAcceleration 
	 * 			The horAcceleration to set.
	 * @post	If the given value is valid, the horizontal acceleration is set to the given
	 * 			value.
	 * 			| if (canHaveAsHorAcceleration(horAcceleration))
	 * 			|	then new.getHorAcceleration() = horAcceleration
	 */
	@Model
	protected void setHorAcceleration(double horAcceleration) {
		if (canHaveAsHorAcceleration(horAcceleration))
			this.horAcceleration = horAcceleration;			
	}

	/**
	 * A variable storing the current horizontal acceleration.
	 * This variable will always store a positive number of type double, 
	 * or it will store zero.
	 */
	private double horAcceleration = 0;

	/**
	 * Checks whether the game object is moving in horizontal direction.
	 * 
	 * @return	True if and only if the game object is moving to the left or to the right.
	 * 			| result == (getHorDirection() == Direction.LEFT ||
	 * 			|			 getHorDirection() == Direction.RIGHT)
	 */
	protected boolean isMoving(){
		return (getHorDirection() != Direction.NULL);
	}
	
	/**
	 * Check whether the game object is moving in the given direction.
	 * @param 	direction
	 * 			The direction to check.
	 * @pre		The direction must be different from null.
	 * 			| direction != Direction.NULL
	 * @return	True if the horizontal direction is equal to the given direction.
	 * 			| if (getHorDirection() == direction)
	 * 			|	then result == true
	 */
	public boolean isMoving(Direction direction){
		assert (direction != Direction.NULL);
		return getHorDirection() == direction;
	}
	
	/**
	 * A method to end the movement in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction in which the movement must be ended.
	 * @pre		The given direction must be left or right.
	 * 			| direction == (Direction.LEFT) || direction == (Direction.RIGHT)
	 * @post	If the given direction is left or right and the game object is moving
	 * 			in that direction, the new horizontal velocity is zero, 
	 * 			the new horizontal direction is null and the time sum of the
	 * 			sprites timer is reset to zero.
	 * 			| if (direction == Direction.LEFT || direction == Direction.RIGHT)
	 * 			|	then new.getHorVelocity() == 0 && new.getHorDirection() == Direction.NULL &&
	 * 			|		 new.getSpritesTimer().getTimeSum() == 0
	 */
	public void endMovement(Direction direction){
		assert (direction != Direction.NULL);
		if ((direction == Direction.LEFT || direction == Direction.RIGHT) &&
			 isMoving(direction)){
			setHorVelocity(0);
			setHorDirection(Direction.NULL);
			getSpritesTimer().reset();
		} 
	}
	
	/**
	 * A method to update the movements of this game object.
	 * As an effect of this method, certain movements may be started.
	 * 
	 * @post	If this game object is dead, the horizontal direction is set 
	 * 			to null and the horizontal velocity is set to zero.
	 * 			| if(isDead())
	 * 			|	then new.getHorDirection() == Direction.NULL &&
	 * 			|		 new.getHorVelocity() == 0
	 */
	protected void updateMovement(){
		if(isDead()){
			setHorDirection(Direction.NULL);
			setHorVelocity(0);
		}
	}
	
	/**
	 * Method to update the position and velocity of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this game object. 
	 * @post	If the game object crosses the borders of the game world,
	 * 			this object is terminated.
	 * @effect	The movements of this game object are updated.
	 * 			| updateMovement()
	 * @effect	The position is updated with the given time duration.
	 * 			| updatePosition(timeDuration)
	 * @effect	The horizontal velocity is updated with the given time duration.
	 * 			| updateHorVelocity(timeDuration)
	 * @effect	The timers are updated with the given time duration.
	 * 			| updateTimers(timeDuration)
	 * @effect	The hit points are updated.
	 * 			| updateHitPoints()
	 * @throws	IllegalTimeIntervalException(this)
	 * 			The given time duration is not a valid time interval.
	 * 			| !(isValidTimeInterval(timeDuration))
	 */
	public abstract void advanceTime(double timeDuration) throws IllegalTimeIntervalException;

	public void startMove(Direction direction) {
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		assert !isDead();
		setHorVelocity(getInitHorVelocity());
		setHorDirection(direction);
		setHorAcceleration(getMaxHorAcceleration());
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
	 * @return	False if the given tile is not effective.
	 * 			| if (tile == null)
	 * 			|	then result == false
	 * 			Otherwise true if this game object and the other 
	 * 			game object have at least one pixel in common. 
	 * 			This means that there exists a position that is occupied by this game object,
	 * 			and that is located in the given tile.
	 */
	protected boolean isOverlappingWith(Tile tile){
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
	 * @param 	other
	 * 			The game object to check overlapping with.
	 * @return	False if the other game object is not effective.
	 * 			| if(other == null)
	 * 			|	then result == false 
	 * 			Otherwise true if this game object and the other 
	 * 			game object have at least one pixel in common.
	 * 			This means that there exists a position that is occupied by
	 * 			this game object and by the other game object. 			
	 */
	protected boolean isOverlappingWith(GameObject other){
		try {
			return !(((getPosition().getDisplayedXPosition()+getWidth()-1) < 
					   other.getPosition().getDisplayedXPosition()) ||
					((other.getPosition().getDisplayedXPosition()+ other.getWidth()-1) < 
							getPosition().getDisplayedXPosition()) ||
					((getPosition().getDisplayedYPosition() + getHeight() - 1) < 
					  other.getPosition().getDisplayedYPosition()) ||
					((other.getPosition().getDisplayedYPosition()+other.getHeight()-1) 
					  < getPosition().getDisplayedYPosition()));
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/**
	 * A method to check whether this game object is overlapping with a terrain type.
	 * 
	 * @param 	terrain
	 * 			The terrain type to check.
	 * @return	False if this game object has no effective world.
	 * 			| if (getWorld() == null)
	 * 			|	then result == false
	 * 			Else true if this game object occupies at least one pixel 
	 * 			that is located in a tile with as terrain type the given terrain.
	 */
	protected boolean isOverlappingWith(Terrain terrain){
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
	 */
	protected boolean isColliding(Direction direction, Tile tile){
		assert (direction != Direction.NULL);
		if (!isOverlappingWith(tile))
			return false;
		int[][] positionsToCheck = getPerimeter(direction);
		for(int index=1;index<positionsToCheck.length-1;index++){
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
	 */
	protected boolean isColliding(Direction direction, GameObject object){
		assert (direction != Direction.NULL);
		if (!isOverlappingWith(object))
			return false;
		int[][] positionsToCheck = getPerimeter(direction);
		for(int index=1;index<positionsToCheck.length-1;index++){
			if(object.occupiesPosition(new Position(positionsToCheck[index][0],
			   positionsToCheck[index][1])))
				return true;
		}
		return false;
	}
	
	/**
	 *  A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with impassable tiles
	 * if the given position would be assigned to this game object.
	 * 
	 * @param 	newPos
	 * 			The position to check in the form of a double array.
	 * 			The first entry of this array represents the x position, the
	 * 			second entry represents the y position.
	 * @pre		The given position must have 2 entries.
	 * 			| newPos.length == 2
	 * @return	If the given position would be assigned to this game object and
	 * 			as a result of that, this game object would collide with an
	 * 			impassable tile in a horizontal direction, then the returned array 
	 * 			will have as first entry the current x position.
	 * 			Else, the returned array will have as first entry the first entry 
	 * 			of the given position.
	 * @return	If the given position would be assigned to this game object and
	 * 			as a result of that, this game object would collide with an
	 * 			impassable tile in a vertical direction, then the returned array
	 * 			will have as second entry the current y position.
	 * 			Else, the returned array will have as second entry the second entry 
	 * 			of the given position.
	 * @note	In the current state, this method violates several rules connected
	 * 			to good programming. It changes the state of an object and returns a value.
	 * 			We are aware of this problem and we will solve it by defensive programming
	 * 			before we hand in the final solution. 
	 */
	protected abstract void updatePositionTileCollision(double[] newPos);
	
	/**
	 * A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with other game objects 
	 * of a given collection if the given position would be assigned to this game object.
	 *  
	 * @param 	newPos
	 * 			The position to check in the form of a double array.
	 * 			The first entry of this array represents the x position, the
	 * 			second entry represents the y position.
	 * @param 	collection
	 * 			The collection to check for collisions against.
	 * @pre		The given position must have 2 entries.
	 * 			| newPos.length == 2
	 * @return	If the given position would be assigned to this game object and
	 * 			as a result of that, this game object would collide with another
	 * 			game object out of the given collection in a horizontal direction, 
	 * 			then the returned array will have as first entry the current x position.
	 * 			Else, the returned array will have as first entry the first entry 
	 * 			of the given position.
	 * @return	If the given position would be assigned to this game object and
	 * 			as a result of that, this game object would collide with another
	 * 			game object out of the given collection in a vertical direction, 
	 * 			then the returned array will have as second entry the current y position.
	 * 			Else, the returned array will have as second entry the second entry 
	 * 			of the given position.
	 * @note	In the current state, this method violates several rules connected
	 * 			to good programming. It changes the state of an object and returns a value.
	 * 			We are aware of this problem and we will solve it by defensive programming
	 * 			before we hand in the final solution. 
	 */
	protected void setPositionAfterCollision(double[] newPos, HashSet<GameObject> collection){
		assert newPos.length == 2;
		double newXPos = newPos[0];
		double newYPos = newPos[1];
		for (GameObject other: collection){
			if ((other != this) && this.isOverlappingWith(other)){
				if (isColliding(Direction.DOWN, other)){
					//System.out.print("Colliding down with object");
					//System.out.print(object.toString());
					if (this.isMoving(Direction.DOWN) || other.isMoving(Direction.UP)){
						//newYPos = gameObject.getPosition().getYPosition()+gameObject.getHeight()-1;
						newYPos = this.getPosition().getYPosition();
					}
					endMovement(Direction.DOWN);
				}
				else if(isColliding(Direction.UP, other)){
					if (isMoving(Direction.UP) || other.isMoving(Direction.DOWN)){
						//newYPos = gameObject.getPosition().getYPosition()-getHeight()+1;
						newYPos = this.getPosition().getYPosition();
					}
					endMovement(Direction.UP);
					//System.out.println("Colliding up");
				}
				if(isColliding(Direction.LEFT, other)){
					if (isMoving(Direction.LEFT) || other.isMoving(Direction.RIGHT)){
						//newXPos = gameObject.getPosition().getXPosition()+gameObject.getWidth()-1;
						newXPos = this.getPosition().getXPosition();
					}
					endMovement(Direction.LEFT);
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, other)){
					if (isMoving(Direction.RIGHT) || other.isMoving(Direction.LEFT)){
						//newXPos = gameObject.getPosition().getXPosition()-getWidth()+1;
						newXPos = this.getPosition().getXPosition();
					}
					endMovement(Direction.RIGHT);
					//System.out.println("Colliding right");
				}
			}
		}
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
	}
	
	/**
	 * A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with other game objects
	 * that can block the movement of this game object.
	 * 
	 * @param 	newPos
	 * 			The position to check in the form of a double array.
	 * 			The first entry of this array represents the x position, the
	 * 			second entry represents the y position.
	 * @pre		The given position must have 2 entries.
	 * 			| newPos.length == 2
	 * @return	The result from the method getPositionAfterCollision(newPos,collection)
	 * 			with as collection the game objects that can block the movement of this game object.
	 * 			| result == getPositionAfterCollision(newPos,getBlockingObjects())
	 * @note	In the current state, this method violates several rules connected
	 * 			to good programming. It changes the state of an object and returns a value.
	 * 			We are aware of this problem and we will solve it by defensive programming
	 * 			before we hand in the final solution.  
	 */
	protected void updatePositionObjectCollision(double[] newPos){
		setPositionAfterCollision(newPos, getBlockingObjects());
	}
	
	/**
	 * Returns all game objects that can block the movement of this game object.
	 * 
	 * @return	A hash set of all game objects that can block the movement of this game object.
	 */
	protected abstract HashSet<GameObject> getBlockingObjects();
	
	/**
	 * A method to update the horizontal velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new horizontal velocity.
	 * @effect	The new horizontal velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this game object.
	 */
	@Model
	protected void updateHorVelocity(double timeDuration){}
	
	/**
	 * Return the sprites timer belonging to this game object.
	 */
	@Basic
	protected Timer getSpritesTimer(){
		return spritesTimer;
	}
	
	/**
	 * A variable storing a period of elapsed time. This variable 
	 * functions as a timer that increments subsequent time intervals
	 * in the method advanceTime and is mainly used to update the sprite
	 * of this game object.
	 */
	private final Timer spritesTimer = new Timer();
	
	/**
	 * Return the hit points timer belonging to this game object.
	 */
	@Basic
	protected Timer getHpTimer(){
		return hpTimer;
	}
	
	/**
	 * A variable storing a period of elapsed time. This variable 
	 * functions as a timer that increments subsequent time intervals
	 * in the method advanceTime and is used to update the hit points 
	 * of this game object.
	 */
	private final Timer hpTimer = new Timer();
	
	/**
	 * A method to add a given time duration to all timers belonging 
	 * to this game object.
	 * 
	 * @param 	timeDuration
	 * 			The time duration to add to all the timers.
	 * @effect	The time duration is added to the sprites timer.
	 * 			| getSpritesTimer().counter(timeDuration)
	 * @effect	The time duration is added to the hit points timer.
	 * 			| getHpTimer().counter(timeDuration)
	 */
	protected void updateTimers(double timeDuration){
		getSpritesTimer().increment(timeDuration);
		getHpTimer().increment(timeDuration);
	}
	
	/**
	 * A method to estimate the time duration needed to travel 0.01 meters,
	 * given a certain time duration.
	 *  
	 * @param 	timeDuration
	 * 			The time duration used in the estimation.
	 */
	protected abstract double getTimeToMoveOnePixel(double timeDuration);
	
	/**
	 * Return the index of the current sprite in the array of sprites,
	 * belonging to this game object.
	 */
	@Basic @Model
	protected int getIndex() {
		return index;
	}
	
	/**
	 * Checks whether the given index is a valid index.
	 * 
	 * @param 	index
	 * 			The index to check.
	 * @return	True if and only if the index is between zero and the length of sprites.
	 * 			| result == (index>=0 && index <= getAllSprites().length)
	 */
	protected boolean isValidIndex(int index){
		return (index >= 0 && index<= sprites.length);
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
	 * A method to update the sprite index.
	 */
	protected abstract void updateSpriteIndex();
	
	/**
	 * A variable storing the index of the current sprite.
	 * The index is an integer number and refers to the position
	 * of the current sprite in the array of sprites, belonging to this game object.
	 */
	private int index;
	
	/**
	 * A method to retrieve the sprite belonging to the current state
	 * of this game object.
	 * 
	 * @return	The sprite located at the current index in the array
	 * 			of sprites of this game object.
	 * 			| result == getAllSprites[getIndex()]
	 */
	@Basic
	public Sprite getCurrentSprite(){
		return sprites[getIndex()];
	}
	
	/**
	 * Return the array of sprites representing the different states of this game object.	
	 */
	@Model
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
	protected boolean isTerminated(){
		return isTerminated;
	}
	
	protected void setIsTerminated(){
		assert (getHitPoints()==0);
		assert getHpTimer().getTimeSum()>0.6;
		this.isTerminated = true;
	}
	
	/**
	 * Terminate this game object.
	 * 
	 * @pre		The game object must be dead.
	 * 			| isDead()
	 * @pre		The time sum belonging to the hit point timer of this
	 * 			game object must be greater than 0.6 seconds.
	 * 			| getHpTimer().getTimeSum()>0.6
	 * @post	The game object is terminated.
	 * 			| new.isTerminated() == true 
	 */
	protected void terminate(){
		assert (getHitPoints()==0);
		assert getHpTimer().getTimeSum()>0.6;
		setIsTerminated();
	}
	
	/**
	 * A variable registering whether or not this game object has been terminated.
	 */
	private boolean isTerminated;
}
