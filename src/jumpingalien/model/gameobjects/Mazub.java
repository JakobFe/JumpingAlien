package jumpingalien.model.gameobjects;


import java.util.HashSet;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.CollisionException;
import jumpingalien.model.exceptions.IllegalJumpInvokeException;
import jumpingalien.model.exceptions.IllegalTimeIntervalException;
import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import jumpingalien.model.other.Direction;
import jumpingalien.model.other.Position;
import jumpingalien.model.worldfeatures.Terrain;
import jumpingalien.model.worldfeatures.Tile;
import jumpingalien.model.worldfeatures.World;
import static jumpingalien.tests.util.TestUtils.doubleArray;

/**
 * A class that implements the player character with the ability to jump, duck and
 * run to the left and to the right. This class is a part of the project JumpingAlien.
 *
 * @invar 	The initial horizontal velocity of this Mazub must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of the Mazub must be valid.
 * 			| canHaveAsMaxHorVelocity(getMaxHorVelocity())
 * @invar 	The current horizontal velocity of this Mazub must be valid.
 * 			| canHaveAsHorVelocity(getVelocity()) 
 * 			
 * @author	Jakob Festraets, Vincent Kemps
 * 			| Course of studies: 2nd Bachelor of engineering
 * 			| Code repository: https://github.com/JakobFe/JumpingAlien
 * @version	2.0
 *
 */
public class Mazub extends Character{
	
	/**
	 * Initialize this new Mazub with given x position, given y position, 
	 * given initial horizontal velocity, given maximum horizontal velocity and 
	 * given sprites.
	 * 
	 * At the moment of initialization the velocity and acceleration are zero
	 * in all directions.
	 * 
	 * @param 	x
	 * 		  	Initial x position for this Mazub.
	 * @param 	y
	 * 			Initial y position for this Mazub.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this Mazub.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity while running for this Mazub.
	 * @param	sprites
	 * 			An array containing the different sprites for this Mazub.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Mazub is initialized as a new character with given x position,
	 * 			given y position, given initial horizontal velocity, given maximum
	 * 			horizontal velocity, given sprites and 100 hit points.
	 */
	@Raw
	public Mazub(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		super(x,y,initHorVelocity,maxHorVelocity,sprites);
		assert isValidArrayOfSprites(sprites);
		this.maxHorVelocityRunning = maxHorVelocity;
		this.numberOfWalkingSprites = (this.getAllSprites().length - 10)/2;
	}
	
	/**
	 * Initialize this new Mazub with given x position, given y position, 
	 * 1 [m/s] as its initial horizontal velocity, 3 [m/s] as its maximum 
	 * horizontal velocity and with the given sprites.
	 * 
	 * @param 	x
	 * 		  	Initial x position for this Mazub.
	 * @param 	y
	 * 			Initial y position for this Mazub.
	 * @param	sprites
	 * 			An array containing the different sprites for this Mazub.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Mazub is initialized with given x position, given 
	 * 			y position, given sprites, 1 as its initial horizontal 
	 * 			velocity and 3 as its maximum horizontal velocity.
	 * 			| this(x,y,1,3,sprites)
	 */
	@Raw
	public Mazub(int x,int y, Sprite[] sprites) 
			throws IllegalXPositionException, IllegalYPositionException{
		this(x,y,1,3,sprites);
	}
	
	/**
	 * Check if the given number of hit points is a valid number of hit points.
	 * 
	 * @return	True if the given number is greater than or equal to zero and 
	 * 			smaller than or equal to 500.
	 * 			| result == ((hitPoints >= 0) && (hitPoints <= 500))
	 */
	protected boolean isValidHitPoints(int hitPoints){
		return (hitPoints>=0 && hitPoints<=500); 
	}
	
	protected boolean canConsumePlant(){
		return isValidHitPoints(getHitPoints()+50); 
	}
	
	protected void consumePlant(){
		if(canConsumePlant())
			setHitPoints(getHitPoints()+50);
	}
	
	@Override
	protected void updateHitPoints(){
		if (isOverlappingWith(Terrain.WATER)){
			if (getTimeSumHp() > 0.2){
				setHitPoints(getHitPoints()-2);
				setTimeSumHp(getTimeSumHp()-0.2);
			}
		}
		if (isOverlappingWith(Terrain.MAGMA)){
			if (getTimeSumHp() == 0)
				setHitPoints(getHitPoints()-50);
			else if (getTimeSumHp() > 0.2){
				setHitPoints(getHitPoints()-50);
				setTimeSumHp(getTimeSumHp()-0.2);
			}
		}
		super.updateHitPoints();
	}
	
	public boolean isImmune() {
		return (getImmuneTimer() < 0.6);
	}

	public double getImmuneTimer() {
		return immuneTimer;
	}

	public void setImmuneTimer(double immuneTimer) {
		this.immuneTimer = immuneTimer;
	}
	
	public void counterImmune(double timeDuration){
		setImmuneTimer(getImmuneTimer()+timeDuration);
	}

	private double immuneTimer = 0.6;
	
	/**
	 * Check whether the given world is a valid world for this Mazub.
	 * 
	 * @return	True if the given world is effective and already references this Mazub.
	 * 			| result == (world != null && world.getMazub() == this) 
	 */
	public boolean isValidWorld(World world){
		return (world == null || world.getMazub() == this);
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
	@Model@Override
	protected boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity >= 1);
	}
	
	/**
	 * Return the maximum horizontal velocity while running for this game object.
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
	 * Return the maximum horizontal velocity while ducking for this Mazub.
 	 */
	@Basic @Immutable @Model
	private static double getMaxHorVelocityDucking(){
		return MAX_HOR_VELOCITY_DUCKING;
	}
	
	/**
	 * A variable storing the maximum horizontal velocity while ducking.
	 * This variable will always have 1 m/s as its value.
	 */
	private static final double MAX_HOR_VELOCITY_DUCKING = 1;
	
	
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
	@Model@Override
	protected boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
		return (maxHorVelocity >= getInitHorVelocity()) ||
				maxHorVelocity == getMaxHorVelocityDucking();
	}	
	
	/**
	 * Return the maximum horizontal acceleration of the Mazub.
	 */
	@Basic @Immutable @Model @Override
	protected double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this Mazub.
	 * This variable must always store a positive number of type double or zero.
	 */
	private static final double maxHorAcceleration = 0.9;
	
	/**
	 * Return the initial vertical velocity of this Mazub.
	 */
	@Basic @Model @Override
	protected double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	/**
	 * A variable storing the initial vertical velocity. 
	 * This variable will always have 8 m/s as its value.
	 */
	private static final double INIT_VERT_VELOCITY = 8;
	
	@Override
	protected boolean isValidVertAcceleration(double vertAcceleration){
		return (vertAcceleration == 0 || vertAcceleration == getMaxVertAcceleration());
	}

	/**
	 * Method to start the movement of the Mazub to the given direction.
	 * 
	 * @pre		The given direction must be left or right.
	 * 			| (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @effect	The horizontal velocity of this Mazub is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to the given horizontal direction.
	 * 			| setHorDirection(direction)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void startMove(Direction direction){
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		setHorVelocity(getInitHorVelocity());
		setHorDirection(direction);
		setHorAcceleration(getMaxHorAcceleration());
		setTimeSum(0);
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the left.
	 * 
	 * @pre		The given direction must be left or right.
	 * 			| (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @pre		This Mazub must be moving to the given direction.
	 * 			| isMoving(direction)
	 * @effect	If this Mazub is moving left,
	 * 			the horizontal velocity, the horizontal direction and
	 * 			the horizontal acceleration are set to zero.
	 * 			| if (isMovingLeft())
	 * 			| 	then setHorVelocity(0),setHorDirection(0),
	 * 			|	setHorAcceleration(0)
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void endMove(Direction direction){
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		assert (isMoving(direction));
		setHorVelocity(0);
		setHorDirection(Direction.NULL);
		setHorAcceleration(0);
		setTimeSum(0);
	}
	
	/**
	 * Method to start the jumping movement of the Mazub.
	 * 
	 * @effect	The vertical velocity of the Mazub is set to the initial vertical velocity.
	 * 			| setVertVelocity(getInitVertVelocity())
	 * @effect	The vertical acceleration of the Mazub is set to the maximum vertical acceleration.
	 * 			| setVertAcceleration(getMaxVertAcceleration())
	 * @effect	The vertical direction of the Mazub is set to 1 (moving up).
	 * 			| setVertDirection(1)
	 * @throws 	IllegalJumpInvokeException(this)
	 * 			startJump can not be invoked when Mazub is still in the air.
	 * 			| (isJumping())
	 */
	public void startJump() throws IllegalJumpInvokeException{
		if (isJumping()) {
			throw new IllegalJumpInvokeException(this);
		}
		setVertVelocity(getInitVertVelocity());
		setVertAcceleration(getMaxVertAcceleration());
		setVertDirection(Direction.UP);
	}
	
	/**
	 * Method to end the jumping movement of the Mazub.
	 * 
	 * @effect	if the Mazub is still moving up, the vertical velocity is set to zero.
	 * 			| if (getVertDirection() == Direction.UP)
	 *			|	setVertVelocity(0)
	 */
	public void endJump(){
		if (getVertDirection() == Direction.UP)
			setVertVelocity(0);
	}
	
	
	
	/**
	 * Returns the current ducking state of this Mazub.
	 */
	@Basic @Model
	private boolean getIsDucked(){
		return this.isDucked;
	}
	
	/**
	 * A method to set the ducking state of this Mazub to true.
	 * 
	 * @param	isDucking
	 * 			A boolean value indicating the new ducking state
	 * 			of this Mazub.
	 * @post	The new ducking state of this Mazub is set to the 
	 * 			given flag.
	 * 			| new.isDucked == isDucking
	 */
	@Model
	private void setIsDucked(boolean isDucking){
		this.isDucked = isDucking;
	}
	
	/**
	 * A variable storing whether this Mazub is ducking or not.
	 */
	private boolean isDucked;
	
	
	/**
	 * Method to start the ducking movement of the Mazub.
	 * 
	 * @effect	The ducking state is set to true.
	 * 			| setIsDucked(true)
	 * @effect	The maximum horizontal velocity is set to the maximum horizontal velocity while ducking.
	 * 			| setMaxHorVelocity(getMaxHorVelocityDucking())
	 */
	public void startDuck(){
		setMaxHorVelocity(getMaxHorVelocityDucking());
		setIsDucked(true);
	}
	
	/**
	 * Method to end the ducking movement of the Mazub.
	 * 
	 * @effect	The ducking state is set to false.
	 * 			| setIsDucked(false)
	 * @effect	The maximum horizontal velocity is set to the maximum horizontal velocity while running.
	 * 			| setMaxHorVelocity(getMaxHorVelocityRunning())
	 * @effect	If the ducking movement stops when moving to the right, the Mazub starts running to the right.
	 * 			| if (isMovingRight())
	 *			|	startMoveRight()
	 * @effect	If the ducking movement stops when moving to the left, the Mazub starts running to the left.
	 * 			| if (isMovingLeft())
	 *			|	startMoveLeft()
	 */
	public void endDuck(){
		try {
			setIsDucked(false);
			updateSpriteIndex();
			HashSet<Tile> affectedTiles = getWorld().getTilesIn(getPosition().getDisplayedXPosition(),
					getPosition().getDisplayedYPosition(),getPosition().getDisplayedXPosition()
					+getWidth()-1, getPosition().getDisplayedYPosition()+getHeight()-1);
			for(Tile tile: affectedTiles){
				if(!(tile.getGeoFeature().isPassable) &&
				   isColliding(Direction.UP, tile))
					throw new CollisionException();
			}
			setEnableStandUp(false);
			setMaxHorVelocity(getMaxHorVelocityRunning());
			if (isMoving(Direction.RIGHT))
				startMove(Direction.RIGHT);
			else if (isMoving(Direction.LEFT))
				startMove(Direction.LEFT);
		} catch (CollisionException e) {
			setIsDucked(true);
			updateSpriteIndex();
			setEnableStandUp(true);
		}
	}
	
	public boolean isEnableStandUp() {
		return enableStandUp;
	}

	public void setEnableStandUp(boolean enableStandUp) {
		this.enableStandUp = enableStandUp;
	}

	private boolean enableStandUp = false;
	
	/**
	 * Method to update the position and velocity of the Mazub based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this Mazub. 
	 * @effect	The horizontal position is updated with the given timeDuration.
	 * 			| updateHorPosition(timeDuration)
	 * @effect	The horizontal velocity is updated with the given timeDuration.
	 * 			| updateHorVelocity(timeDuration)
	 * @effect	The vertical position is updated with the given timeDuration.
	 * 			| updateVertPosition(timeDuration)
	 * @effect	The vertical velocity is updated with the given timeDuration.
	 * 			| updateVertVelocity(timeDuration)
	 * @effect	The given timeDuration is added to the timeSum.
	 * 			| counter(timeDuration)
	 * @effect	The last direction in which the Mazub was moving is updated.
	 * 			| updateLastDirection()
	 * @throws	IllegalTimeIntervalException(this)
	 * 			The given timeduration is not a valid time interval.
	 * 			| !(isValidTimeInterval(timeDuration))
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
				IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		if (isEnableStandUp())
			endDuck();
		double tdHor = 0.01/(Math.abs(getHorVelocity())
				+Math.abs(getHorAcceleration())*timeDuration);
		double tdVert = 0.01/(Math.abs(getVertVelocity())
				+Math.abs(getVertAcceleration())*timeDuration);
		double td = Math.min(tdHor, tdVert);
		if (td > timeDuration)
			td = timeDuration;
		for (int index = 0; index < timeDuration/td; index++){
			try {
				updatePosition(td);
			} catch (IllegalXPositionException | IllegalYPositionException e) {
				setHitPoints(0);
				terminate();
			}
			try {
				updateHorVelocity(td);
				updateVertVelocity(td);
			} catch (NullPointerException e) {
			}
		}
		updateLastDirection();
		counter(timeDuration);
		updateHitPoints();
		//System.out.println(getImmuneTimer());
		counterImmune(timeDuration);
		if (isOverlappingWith(Terrain.WATER) || isOverlappingWith(Terrain.MAGMA))
			counterHp(timeDuration);
		else
			setTimeSumHp(0);
	}
	
	
	
	@Override
	protected void updatePosition(double timeDuration){
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		double newYPos = getPosition().getYPosition() + 
				((getVertDirection().getFactor()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;
		//assert ((Math.abs(newXPos - getPosition().getXPosition()) <= 1) &&
		//		(Math.abs(newYPos - getPosition().getYPosition()) <= 1));
		if(newXPos != getPosition().getXPosition())
			//System.out.println((Math.abs(newXPos - getPosition().getXPosition())));
		if (newYPos<0)
			newYPos = 0;
		boolean enableFall = true;
		double[] newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
		updatePositionObjectCollision(newPos);
		newXPos = newPos[0];
		newYPos = newPos[1];
		
		if(standsOnTile() || standsOnObject())
			enableFall = false;
		
		if (enableFall && !isMoving(Direction.UP)){
			startFall();
		}
		getPosition().terminate();
		setPosition(new Position(newXPos,newYPos,getWorld()));
	}
	
	/**
	 * A method to update the horizontal velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new horizontal velocity.
	 * @effect	The new horizontal velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this Mazub.
	 */
	@Model @Override
	protected void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (newVel > getMaxHorVelocity()){
			setHorVelocity(getMaxHorVelocity());
			setHorAcceleration(0);
		}
		else
			setHorVelocity(newVel);
	}
			
	
	/**
	 * Return the last registered horizontal direction of the Mazub.
	 */
	@Basic @Model
	private Direction getLastDirection() {
		return lastDirection;
	}

	/**
	 * Sets the last registered horizontal direction to the given last direction.
	 * 
	 * @param 	lastDirection
	 * 			the lastDirection to set.
	 * @post	The new last direction equals the given lastDirection.
	 * 			| new.getLastDirection == lastDirection
	 */
	@Model
	private void setLastDirection(Direction lastDirection) {
		this.lastDirection = lastDirection;
	}

	/**
	 * A method to update the last registered horizontal direction of the Mazub.
	 * 
	 * @post	If Mazub is moving to the left or the right, the last registered direction 
	 * 			will be updated.
	 * 			| if (getHorDirection() != 0)
	 *			|	new.getHorDirection() = getHorDirection()
	 */
	@Model
	private void updateLastDirection() {
		if (getHorDirection().getFactor() != 0)
			setLastDirection(getHorDirection());
	}
	
	/**
	 * A variable storing the last horizontal direction of movement of this Mazub
	 * within the last second of in-game-time.
	 */
	private Direction lastDirection;

	
	/**
	 * Checks whether the given index is a valid index.
	 * 
	 * @param 	index
	 * 			The index to check.
	 * @return	True if and only if the index is between zero and the length of sprites.
	 * 			| result == (index >= 0 && index < getNumberOfWalkingSprites()*2+10)
	 */
	@Override
	protected boolean isValidIndex(int index){
		return (index >= 0 || index < getNumberOfWalkingSprites()*2+10);
	}

	/**
	 * Checks whether the Mazub is moving to the right.
	 * 
	 * @return	True if and only if the horizontal direction of the Mazub is equal to the given direction.
	 * 			| result == (getHorDirection() == direction)
	 */
	@Override
	public boolean isMoving(Direction direction){
		return ((getHorDirection() == direction) ||
				getVertDirection() == direction);
	}
	
	/**
	 * A method to check whether the Mazub has moved left or right 
	 * within the last second of in-game-time.
	 * 
	 * @return	True if and only if the last registered horizontal direction
	 * 			of this Mazub is not zero and timeSum has not reached 1 second yet.
	 * 			| result == ((getLastDirection() != 0) && (getTimeSum() < 1))
	 * 
	 */
	private boolean wasMoving(){
		if (getLastDirection() != Direction.NULL &&
			getTimeSum() < 1.0)
				return true;
		return false;
	}
	
	/**
	 * Checks whether the Mazub has moved to the given direction within the last second of in-game-time.
	 * 
	 * @return	True if and only if this Mazub was moving within the last second
	 * 			of in-game-time and its last direction was equal to the given direction.
	 * 			| result == (wasMoving() && (getLastDirection() == -1))
	 */
	private boolean wasMoving(Direction direction){
		return (wasMoving() && getLastDirection() == direction);
	}
	
	/**
	 * A method to check whether this Mazub is jumping. 
	 * 
	 * @return	True if and only if the current vertical direction
	 * 			differs from zero.
	 * 			| result == (getVertDirection() != 0)
	 */
	private boolean isJumping(){
		return (getVertDirection() != Direction.NULL);
	}
	
	/**
	 * A method to update the index in the array of sprites.
	 * 
	 * @post	If this Mazub is not moving horizontally, has not moved
	 * 			horizontally within the last second of in-game-time and
	 * 			is not ducking, the index is set to 0.
	 * @post	If this Mazub is not moving horizontally, has not moved
	 * 			horizontally within the last second of in-game-time and
	 * 			is ducking, the index is set to 1.
	 * @post	If this Mazub is not moving horizontally, has moved
	 * 			right within the last second of in-game-time and
	 * 			is not ducking, the index is set to 2.
	 * @post	If this Mazub is not moving horizontally, has moved
	 * 			left within the last second of in-game-time and
	 * 			is not ducking, the index is set to 3.
	 * @post	If this Mazub is moving to the right and is jumping
	 * 			and is not ducking, the index is set to 4.
	 * @post	If this Mazub is moving to the left and is jumping
	 * 			and is not ducking, the index is set to 5.
	 * @post	If this Mazub is ducking and moving to the right or was moving
	 * 			to the right within the last second of in-game-time, the index is set to 6.
	 * @post	If this Mazub is ducking and moving to the left or was moving
	 * 			to the left within the last second of in-game-time, the index is set to 7.
	 * @effect	If this Mazub is neither ducking nor jumping and moving to the right,
	 * 			the index is set to the next walking animation to the right. 
	 * @effect	If this Mazub is neither ducking nor jumping and moving to the left,
	 * 			the index is set to the next walking animation to the left.
	 */
	@Override
	public void updateSpriteIndex(){
		if (getIsDucked()){
			if (isMoving() || wasMoving()){
				if (isMoving(Direction.RIGHT) || wasMoving(Direction.RIGHT))
					setIndex(6);
				else
					// isMovingLeft() == true
					setIndex(7);
				}
			else
				// only possibility left
				setIndex(1);
		}
		else if (isMoving()){
				if (isJumping()){
					if (isMoving(Direction.RIGHT))
						setIndex(4);
					else
						// isMovingLeft() == true
						setIndex(5);
				}
				else if (isMoving(Direction.RIGHT)){
						//8..8+m
						updateWalkingAnimationRight();
				}
				else{
					//isMovingLeft() == true
					//9+m..9+2m
					updateWalkingAnimationLeft();
				}
		}
		else if (wasMoving(Direction.RIGHT))
				setIndex(2);
		else if (wasMoving(Direction.LEFT))
				setIndex(3);
		else
			// Mazub is standing still longer than 1 second
			setIndex(0);
	}
	
	/**
	 * A method to increment the index of the sprite while running to the right.
	 * 
	 * @effect	If the current index is lower than 8 or higher then 8 plus the number of walking sprites (m),
	 * 			the index is set to 8.
	 * @effect	If the time sum has reached a value higher than or equal to 0.075, an incremented
	 * 			index is calculated. The time sum is set to the current time sum modulo 0.075.
	 * @effect	If the incremented index is higher than (8+m),
	 * 			the index is set to 8 plus the deficit of the incremented index and 8, modulo m.
	 * @effect	If the incremented index in not higher than (8+m), the index is set to the incremented index. 
	 */
	@Model
	private void updateWalkingAnimationRight() {
		if ((getIndex() < 8 || getIndex() > (8+getNumberOfWalkingSprites())))
			setIndex(8);
		if (getTimeSum()>0.075){
			int newIndex = getIndex() + (int) (Math.floor(getTimeSum()/0.075));
			if (newIndex > (8+getNumberOfWalkingSprites()))
				setIndex((newIndex-8)%getNumberOfWalkingSprites() + 8);
			else
				setIndex(newIndex);
			setTimeSum(getTimeSum()%0.075);}
	}

	/**
	 * A method to increment the index of the sprite while running to the left.
	 * 
	 * @effect	If the current index is lower than (9+m), the index is set to (9+m).
	 * @effect	If the time sum has reached a value higher than or equal to 0.075, an incremented
	 * 			index is calculated. The time sum is set to the current time sum modulo 0.075.
	 * @effect	If the incremented index is higher than (9+2*m), the index is set to 
	 * 			(9+m) plus the deficit of the incremented index and (9+m), modulo m.
	 * @effect	If the incremented index in not higher than (9+2*m), the index is set to the incremented index.
	 */
	@Model
	private void updateWalkingAnimationLeft() {
		if ((getIndex() < (9+getNumberOfWalkingSprites())))
			setIndex(9+getNumberOfWalkingSprites());
		if (getTimeSum()>0.075){
			int newIndex = getIndex() + (int) (Math.floor(getTimeSum()/0.075));
			if (newIndex > (9+2*getNumberOfWalkingSprites()))
				setIndex((newIndex-(9+getNumberOfWalkingSprites()))%getNumberOfWalkingSprites()
						+ 9+getNumberOfWalkingSprites());
			else
				setIndex(newIndex);
			setTimeSum(getTimeSum()%0.075);}
	}
	
	
	/**
	 * A method to check whether the given array of sprites is valid. 
	 * @return	True if and only if the length of the array is greater than
	 * 			or equal to 10 and the length is an even number.
	 * 			| result == (sprites.length >= 10 && sprites.length %2 == 0)
	 */
	@Model
	private boolean isValidArrayOfSprites(Sprite[] sprites){
		return (sprites.length >= 10 && sprites.length%2 == 0);
	}
	
			
	/**
	 * Return the number of sprites used for the animation of walking of this Mazub.
	 */
	private int getNumberOfWalkingSprites() {
		return numberOfWalkingSprites;
	}
	
	/**
	 * A variable storing the number of sprites used for animation of walking of the Mazub.
	 */
	private final int numberOfWalkingSprites;
	
	@Override
	public void terminate(){
		assert (getHitPoints()==0);
		super.terminate();
		getWorld().setMazub(null);
		setWorld(null);
		//setPosition(null);
	}
		
	@Override
	public String toString(){
		return "Mazub at position " + (getPosition().getDisplayedXPosition()) + ","
				+ getPosition().getDisplayedYPosition();
	}
}
