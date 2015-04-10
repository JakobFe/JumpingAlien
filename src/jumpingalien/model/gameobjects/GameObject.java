package jumpingalien.model.gameobjects;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.model.other.*;
import jumpingalien.model.worldfeatures.*;
import jumpingalien.util.Sprite;

public abstract class GameObject {
	@Raw@Model
	protected GameObject(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalXPositionException,IllegalYPositionException{
		setPosition(new Position(x,y));
		setHitPoints(hitPoints);
		assert isValidInitHorVelocity(initHorVelocity);
		this.initHorVelocity = initHorVelocity;
		assert canHaveAsMaxHorVelocity(maxHorVelocity);
		this.maxHorVelocityRunning = maxHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		this.sprites = sprites;
	}
	
	@Raw@Model
	protected GameObject(int x, int y, double initHorVelocity, Sprite[] sprites,
			int hitPoints) throws IllegalXPositionException,IllegalYPositionException{
		this(x,y,initHorVelocity,initHorVelocity,sprites,hitPoints);
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	protected void setPosition(Position position){
		this.position = position;
	}
	
	private Position position;
	
	/**
	 * Return the width of the current sprite of this Mazub.
	 */
	public int getWidth(){
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * Return the height of the current sprite of this Mazub.
	 */
	public int getHeight(){
		return this.getCurrentSprite().getHeight();
	}
	//Deze methodes zijn getest en werken.
	public int[][] getLeftPerimeter(){
		int xPos = this.getPosition().getDisplayedXPosition();
		int yPos = this.getPosition().getDisplayedYPosition();
		int [][] result = new int[getHeight()][2];
		for(int index=0;index<getHeight();index++){
			result[index][0] = xPos;
			result[index][1] = yPos + index;
		}
		return result;
	}
	
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
	
	@Basic
	public int getHitPoints() {
		return hitPoints;
	}
	
	protected static boolean isValidHitPoints(int hitPoints){
		return (hitPoints>=0); 
	}

	public void setHitPoints(int hitPoints) {
		if (isValidHitPoints(hitPoints))
			this.hitPoints = hitPoints;
	}

	private int hitPoints;
	
	
	protected World getWorld() {
		return world;
	}
	
	protected abstract boolean isValidWorld(World world);
	
	public void setWorld(World world) {
		assert isValidWorld(world);
		this.world = world;
	}

	private World world = null;
	
	/**
	 * Returns the current horizontal direction of this Game object.
	 */
	@Basic
	public Direction getHorDirection() {
		return horDirection;
	}
	
	/**
	 * Set the horizontal direction of this Mazub to the given horizontal direction.
	 * 
	 * @param	horDirection
	 * 			Horizontal direction to set.
	 * @pre		The given direction must be a valid direction.
	 * 			| isValidDirection(horDirection)
	 * @post	The new horizontal direction of this Mazub is set to the given direction.
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
	 * Return the initial horizontal velocity of this Mazub.
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
	 * 			or equal to 1.
	 * 			| result == (initHorVelocity >= 1)
	 */
	@Model
	protected boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity >= 0);
	}
	
	/**
	 * A variable storing the initial horizontal velocity of this Mazub.
	 */
	private final double initHorVelocity;
	
	/**
	 * Return the maximum horizontal velocity while running for this Mazub.
 	 */
	@Basic @Immutable
	protected double getMaxHorVelocityRunning(){
		return this.maxHorVelocityRunning;
	}

	/**
	 * A variable storing the maximum horizontal velocity while running.
	 */
	private final double maxHorVelocityRunning;
	/**
	 * Return the current maximum horizontal velocity.
	 */
	@Basic @Model
	protected double getMaxHorVelocity(){
		return this.maxHorVelocity;
	}
	
	/**
	 * Checks whether this Mazub can have the given maximum 
	 * horizontal velocity as its maximum horizontal velocity.
	 * 
	 * @param	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @return	True if the given maximum horizontal velocity is above or 
	 * 			equal to the initial horizontal velocity of this Mazub, 
	 * 			or if the given maximum horizontal velocity is equal
	 * 			to the maximum horizontal velocity while ducking.
	 * 			| result == (maxHorVelocity >= getInitHorVelocity()) ||
	 * 			|			maxHorVelocity == getMaxHorVelocityDucking()
	 */
	@Model
	protected boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
		return (maxHorVelocity >= getInitHorVelocity());
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
	 * A variable storing the current maximal velocity of this Mazub.
	 */
	private double maxHorVelocity;
	
	/**
	 * Return the current horizontal velocity of this Mazub.
	 */
	@Basic
	public double getHorVelocity() {
		return horVelocity;
	}
	/**
	 * Checks whether this Mazub can have the given horizontal velocity as
	 * its horizontal velocity.
	 * 
	 * @param	horVelocity
	 * 			The horizontal velocity to check.
	 * @return	True if the given horizontal velocity is above or equal to
	 * 			the initial horizontal velocity of this Mazub and below or 
	 * 			equal to the maximum horizontal velocity of this Mazub, or
	 * 			if the given horizontal velocity is 0.
	 * 			| result == (horVelocity == 0) ||
	 * 			|			((horVelocity >= getInitHorVelocity()) &&
	 * 			|			(horVelocity <= getMaxHorVelocity())) 
	 */
	@Model
	protected boolean canHaveAsHorVelocity(double horVelocity){
		return  ((horVelocity >= this.getInitHorVelocity()) &&
				(horVelocity <= getMaxHorVelocityRunning()));
	}
	
	/**
	 * Set the horizontal velocity to a given value.
	 * 
	 * @param 	horVelocity
	 * 			The new horizontal velocity for this Mazub.
	 * @post	If the given horizontal velocity is possible,
	 * 			the new horizontal velocity of this Mazub is equal to 
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
	 * A variable storing the current horizontal velocity of this Mazub.
	 * This value will always be a positive number of type double, or zero.
	 */
	private double horVelocity = 0;
	
	public void endMovement(Direction direction){
		assert (direction != Direction.NULL);
		if (isMoving(direction)){
			if (direction == Direction.LEFT || direction == Direction.RIGHT){
				// zelfde als in endMove
				setHorVelocity(0);
				setHorDirection(Direction.NULL);
				setTimeSum(0);
			} 
		}
	}
	
	/**
	 * Checks whether the Mazub is moving.
	 * 
	 * @return	True if and only if the Mazub is moving to the left or to the right, which is
	 * 			equivalent to if the horizontal direction is not zero (1 or -1).
	 * 			| result == (getHorDirection() != 0)
	 */
	protected boolean isMoving(){
		return (getHorDirection() != Direction.NULL);
	}
	
	public boolean isMoving(Direction direction){
		return (getHorDirection() == direction);
	}
	
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updatePosition(timeDuration);
		updateHorVelocity(timeDuration);
		counter(timeDuration);
	}

	/**
	 * A method to check whether the given time interval is a valid
	 * time interval to simulate the movement of a Mazub.
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

	protected abstract void updatePosition(double timeDuration);
	
	public boolean isOverlapping(Tile tile){
		return !(((getPosition().getDisplayedXPosition()+getWidth()-1) < tile.getXPosition()) ||
				((tile.getXPosition()+getWorld().getTileSize()-1) < getPosition().getDisplayedXPosition())
				|| ((getPosition().getDisplayedYPosition() + getHeight() -1) < tile.getYPosition())
				|| ((tile.getYPosition()+getWorld().getTileSize()-1) < getPosition().getDisplayedYPosition()));
	}
	
	public boolean isColliding(Direction direction, Tile tile){
		assert (direction != Direction.NULL);
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
		for(int index=1;index<positionsToCheck.length-1;index++){
			if((getWorld().getBelongingTileXPosition(positionsToCheck[index][0]) == tile.getTileXPos()) 
				&& getWorld().getBelongingTileYPosition(positionsToCheck[index][1]) == tile.getTileYPos())
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
	 * 			time interval and the current attributes of this Mazub.
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
	 * A method to increment the time sum with the given timeduration.
	 * 
	 * @param 	timeDuration
	 * 			the timeduration to add to time sum.
	 * @post	The new time sum is incremented with the given timeDuration.
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
	 * belonging to this Mazub.
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
	 * 			| result == (index >= 0 && index < getNumberOfWalkingSprites()*2+10)
	 */
	protected boolean isValidIndex(int index){
		return (index >= 0);
	}

	/**
	 * Sets the index of the current sprite to the given index.
	 * 
	 * @param	index
	 * 			The index to set.
	 * @pre		The given index must be a valid index for this Mazub.
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
	 * of the current sprite in the array of sprites, belonging to this Mazub.
	 */
	private int index;

	public abstract void updateSpriteIndex();
	
	/**
	 * A method to retrieve the sprite belonging to the current state
	 * of this Mazub.
	 * 
	 * @return	The sprite located at the current index in the array
	 * 			of sprites of this Mazub.
	 * 			| result == getAllSprites[getIndex()]
	 */
	public Sprite getCurrentSprite(){
		return getAllSprites()[getIndex()];
	}
	
	/**
	 * Return the array of sprites representing the different states of this Mazub.	
	 */
	protected Sprite[] getAllSprites(){
		return this.sprites;
	}

	/**
	 * A variable storing all possible sprites for this character.
	 * The sprites are images. Each sprite represents 
	 * a different state of this Mazub.
	 */
	private final Sprite[] sprites;

	public boolean isTerminated(){
		return isTerminated;
	}
	
	protected void terminate(){
		assert (getHitPoints()==0);
		this.isTerminated = true;
	}
	
	private boolean isTerminated;

	
}
