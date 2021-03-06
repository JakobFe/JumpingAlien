package jumpingalien.model.game;

import java.util.HashSet;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.doubleArray;

public abstract class Alien extends Character implements JumpInterface{
		
	/**
	 * Initialize this new Alien with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites and 100 hit points.
	 * 
	 * 
	 * @param 	position
	 * 			The position for this new Alien.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this Alien.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity while running for this Alien.
	 * @param	sprites
	 * 			An array containing the different sprites for this Alien.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Alien is initialized as a new character with given position,
	 * 			given initial horizontal velocity, given maximum
	 * 			horizontal velocity, given sprites and 100 hit points.
	 * 			| super(position,initHorVelocity,maxHorVelocity,sprites)
	 * @post	The maximum horizontal velocity while running is set
	 * 			to the given maximum horizontal velocity.
	 * 			| new.getMaxHorVelocityRunning() == maxHorVelocity
	 * @post	The number of walking sprites is set to the half of the length of the given
	 * 			array of sprites, substracted with 10.	
	 * 			| new.getNumberOfWalkingSprites() == (sprites.length - 10)/2
	 */
	@Raw
	public Alien(Position position, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalArgumentException{
		super(position,initHorVelocity,maxHorVelocity,sprites,null);
		assert isValidArrayOfSprites(sprites);
		this.maxHorVelocityRunning = maxHorVelocity;
		this.numberOfWalkingSprites = (sprites.length - 10)/2;
	}
	
	/**
	 * Initialize this new Alien with given position, 
	 * 1 [m/s] as its initial horizontal velocity, 3 [m/s] as its maximum 
	 * horizontal velocity and with the given sprites.
	 * 
	 * @param 	position
	 * 		  	Initial position for this Alien.
	 * @param	sprites
	 * 			An array containing the different sprites for this Alien.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Alien is initialized with given x position, given 
	 * 			y position, given sprites, 1 as its initial horizontal 
	 * 			velocity and 3 as its maximum horizontal velocity.
	 * 			| this(x,y,1,3,sprites)
	 */
	@Raw
	public Alien(Position position, Sprite[] sprites) 
			throws IllegalXPositionException, IllegalYPositionException{
		this(position,1,3,sprites,null);
	}

	/**
	 * Initialize this new Alien with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites, given program and 100 hit points.
	 * 
	 * 
	 * @param 	position
	 * 			The position for this new Alien.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this Alien.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity while running for this Alien.
	 * @param	sprites
	 * 			An array containing the different sprites for this Alien.
	 * @param	program
	 * 			The program for this Alien.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Alien is initialized as a new character with given position,
	 * 			given initial horizontal velocity, given maximum
	 * 			horizontal velocity, given sprites, given program and 100 hit points.
	 * 			| super(position,initHorVelocity,maxHorVelocity,sprites,program)
	 * @post	The maximum horizontal velocity while running is set
	 * 			to the given maximum horizontal velocity.
	 * 			| new.getMaxHorVelocityRunning() == maxHorVelocity
	 * @post	The number of walking sprites is set to the half of the length of the given
	 * 			array of sprites, substracted with 10.	
	 * 			| new.getNumberOfWalkingSprites() == (sprites.length - 10)/2
	 */
	public Alien(Position position, double initHorVelocity, double maxHorVelocity,
			Sprite[] sprites, Program program) 
			throws IllegalArgumentException{
		super(position,initHorVelocity,maxHorVelocity,sprites,program);
		assert isValidArrayOfSprites(sprites);
		this.maxHorVelocityRunning = maxHorVelocity;
		this.numberOfWalkingSprites = (sprites.length - 10)/2;
	}

	/**
	 * Initialize this new Alien with given position, 
	 * 1 [m/s] as its initial horizontal velocity, 3 [m/s] as its maximum 
	 * horizontal velocity, given program and with the given sprites.
	 * 
	 * @param 	position
	 * 		  	Initial position for this Alien.
	 * @param	sprites
	 * 			An array containing the different sprites for this Alien.
	 * @param	program
	 * 			The program for this Alien.
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Alien is initialized with given x position, given 
	 * 			y position, given sprites, given program, 1 as its initial horizontal 
	 * 			velocity and 3 as its maximum horizontal velocity.
	 * 			| this(x,y,1,3,sprites,program)
	 */
	public Alien(Position position, Sprite[] sprites, Program program) 
			throws IllegalXPositionException, IllegalYPositionException{
		this(position,1,3,sprites,program);
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
	
	/**
	 * A method to check whether this Alien can consume a plant.
	 * 
	 * @return	True if the current hit points of this Alien, incremented with
	 * 			50, is a valid amount of hit points.
	 * 			| result == isValidHitPoints(getHitPoints()+50)
	 */
	protected boolean canConsumePlant(){
		return isValidHitPoints(getHitPoints()+50); 
	}
	
	/**
	 * A method to consume a plant.
	 * 
	 * @effect	If this Alien can consume plants, the hit points 
	 * 			of this Alien are incremented with 50.
	 * 			| if(canConsumePlant())
 	 *			|	then addHp(50)
	 */
	protected void consumePlant(){
		if(canConsumePlant())
			addHp(50);
	}
	
	/**
	 * A method to check whether this game object can be hurt by a certain terrain type.
	 * 
	 * @return	True if the given terrain is of type "water" or of type "Magma".
	 * 			| result == (terrain == Terrain.WATER || terrain == Terrain.MAGMA)
	 */
	@Override
	protected boolean canBeHurtBy(Terrain terrain) {
		return (terrain == Terrain.WATER || terrain == Terrain.MAGMA);
	}
		
	/**
	 * Checks whether the given initial horizontal velocity is valid.
	 * 
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
	 * Return the maximum horizontal velocity while ducking for this Alien.
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
	 * Checks whether this Alien can have the given maximum 
	 * horizontal velocity as its maximum horizontal velocity.
	 * 
	 * @return	True if the given maximum horizontal velocity is above or 
	 * 			equal to the initial horizontal velocity of this Alien, 
	 * 			or if the given maximum horizontal velocity is equal
	 * 			to the maximum horizontal velocity while ducking.
	 * 			| result == (maxHorVelocity >= getInitHorVelocity()) ||
	 * 			|			maxHorVelocity == getMaxHorVelocityDucking()
	 */
	@Model@Override
	protected boolean canHaveAsMaxHorVelocity(double maxHorVelocity, double initHorVelocity){
		return (maxHorVelocity >= initHorVelocity) ||
				maxHorVelocity == getMaxHorVelocityDucking();
	}	
	
	/**
	 * Return the maximum horizontal acceleration of the Alien.
	 */
	@Basic @Immutable @Model @Override
	protected double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this Alien.
	 * This variable must always store a positive number of type double or zero.
	 */
	private static final double maxHorAcceleration = 0.9;
	
	/**
	 * Return the initial vertical velocity of this Alien.
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

	/**
	 * Method to start the movement of the Alien to the given direction.
	 * 
	 * @param 	direction
	 * 			The given direction.
	 * @pre		The given direction must be left or right.
	 * 			| (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @pre		The Alien may not be dead.
	 * 			| !isDead()
	 * @effect	The horizontal velocity of this Alien is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to the given horizontal direction.
	 * 			| setHorDirection(direction)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 * @effect	The timer is reset to zero.
	 * 			| getSpritesTimer().reset()
	 */
	@Override
	public void startMove(Direction direction){
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		assert !isDead();
		setHorVelocity(getInitHorVelocity());
		setHorDirection(direction);
		setHorAcceleration(getMaxHorAcceleration());
		getSpritesTimer().reset();
	}
	
	/**
	 * Method to end the movement of the Alien when moving to the left.
	 * 
	 * @param 	direction
	 * 			The given direction.
	 * @pre		The given direction must be left or right.
	 * 			| (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @pre		This Alien may not be moving to the opposite direction of the given direction.
	 * 			| !isMoving(oppositeDirection(direction))
	 * @effect	The horizontal velocity and horizontal acceleration are set to zero.
	 * 			| setHorVelocity(0), setHorAcceleration(0)
	 * @effect	The horizontal direction is set to null.
	 * 			| setHorDirection(Direction.NULL)
	 * @effect	The timer is reset to zero.
	 * 			| getSpritesTimer().reset()
	 */
	public void endMove(Direction direction){
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		assert (!isMoving(oppositeDirection(direction)));
		setHorVelocity(0);
		setHorDirection(Direction.NULL);
		setHorAcceleration(0);
		getSpritesTimer().reset();
	}
	
	/**
	 * Returns the opposite direction of the given direction.
	 * 
	 * @param 	direction
	 * 			The given direction.
	 * @pre		The given direction must be left or right.
	 * 			| (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @return	Return right if the given direction is left, return left otherwise.
	 * 			| if (direction == Direction.LEFT)
	 * 			|	then result == Direction.RIGHT
	 * 			| else result == Direction.LEFT
	 */
	public Direction oppositeDirection(Direction direction){
		assert ((direction == Direction.LEFT) || (direction == Direction.RIGHT));
		if (direction == Direction.LEFT)
			return Direction.RIGHT;
		else
			return Direction.LEFT;
	}
	
	/**
	 * Method to start the jumping movement of the Alien.
	 * 
	 * @effect	The vertical velocity of the Alien is set to the initial vertical velocity.
	 * 			| setVertVelocity(getInitVertVelocity())
	 * @effect	The vertical acceleration of the Alien is set to the maximum vertical acceleration.
	 * 			| setVertAcceleration(getMaxVertAcceleration())
	 * @effect	The vertical direction of the Alien is set to up.
	 * 			| setVertDirection(Direction.UP)
	 * @throws 	IllegalJumpInvokeException(this)
	 * 			startJump can not be invoked when Alien is still in the air or is dead.
	 * 			| (isJumping() || isDead())
	 */
	public void startJump() throws IllegalJumpInvokeException{
		if (isJumping() || isDead()) {
			throw new IllegalJumpInvokeException(this);
		}
		setVertVelocity(getInitVertVelocity());
		setVertAcceleration(getMaxVertAcceleration());
		setVertDirection(Direction.UP);
	}
	
	/**
	 * Method to end the jumping movement of the Alien.
	 * 
	 * @effect	The vertical velocity of the Alien is set to zero.
	 * 			| setVertVelocity(0)
	 * @effect	The vertical direction of the Alien is set to null.
	 * 			| setVertDirection(Direction.NULL)
	 * @throws 	IllegalStateException("This alien is not jumping!")
	 * 			endJump can not be invoked when Alien is still going up.
	 * 			| (getVertDirection() != Direction.UP)
	 */
	public void endJump() throws IllegalStateException{
		if (getVertDirection() != Direction.UP)
			throw new IllegalStateException("This alien is not jumping!");
		setVertVelocity(0);
		setVertDirection(Direction.NULL);
	}
	
	/**
	 * Returns the current ducking state of this Alien.
	 */
	@Basic @Model
	public boolean getIsDucked(){
		return this.isDucked;
	}
	
	/**
	 * A method to set the ducking state of this Alien to true.
	 * 
	 * @param	isDucking
	 * 			A boolean value indicating the new ducking state
	 * 			of this Alien.
	 * @post	The new ducking state of this Alien is set to the 
	 * 			given flag.
	 * 			| new.isDucked == isDucking
	 */
	@Model
	private void setIsDucked(boolean isDucking){
		this.isDucked = isDucking;
	}
	
	/**
	 * A variable storing whether this Alien is ducking or not.
	 */
	private boolean isDucked;
	
	
	/**
	 * Method to start the ducking movement of the Alien.
	 * 
	 * @effect	The ducking state is set to true.
	 * 			| setIsDucked(true)
	 * @effect	The maximum horizontal velocity is set to the maximum horizontal velocity while ducking.
	 * 			| setMaxHorVelocity(getMaxHorVelocityDucking())
	 * @throws 	IllegalStateException()
	 * 			startDuck can not be invoked when Alien is dead.
	 * 			| isDead()
	 */
	public void startDuck(){
		if(isDead())
			throw new IllegalStateException();
		setMaxHorVelocity(getMaxHorVelocityDucking());
		setIsDucked(true);
	}
	
	/**
	 * Method to end the ducking movement of the Alien.
	 * 
	 * @effect	If one of the following conditions hold, no actions are taken.
	 * 			At least one of the tile the Alien overlaps with is not passable
	 * 			and Alien is colliding with this tile in the upper direction.
	 * 			| let
	 * 			|	affectedTiles = getWorld().getTilesIn(getPosition().getDisplayedXPosition(),
	 *			|	getPosition().getDisplayedYPosition(),getPosition().getDisplayedXPosition()
	 *			|	+getWidth()-1, getPosition().getDisplayedYPosition()+getHeight()-1)
	 *			| in 
	 *			|	for some tile in affectedTiles:
	 *			|		(!(tile.getGeoFeature().isPassable()) && isColliding(Direction.UP, tile))
	 *			At least one of all game objects in this game world overlaps with Alien
	 *			and Alien is colliding with it in the upper direction.
	 *			| for some object in getWorld().getAllGameObjects():
	 *			|	(object != this && isColliding(Direction.UP, object))
	 * @effect	Else, the following actions are taken.
	 * 			The ducking state is set to false.
	 * 			| setIsDucked(false)
	 * 			The sprite index of the Alien is updated.
	 * 			updateSpriteIndex()
	 * 			The Alien is no longer able to stand up, because it will no longer be ducking.
	 * 			setEnableStandUp(false)
	 * 			The maximum horizontal velocity is set to the maximum horizontal velocity while running.
	 * 			| setMaxHorVelocity(getMaxHorVelocityRunning())
	 * 			If the ducking movement stops when moving to the right, the Alien starts running to the right.
	 * 			| if (isMovingRight())
	 *			|	startMoveRight()
	 * 			Else if the ducking movement stops when moving to the left, the Alien starts running to the left.
	 * 			| if (isMovingLeft())
	 *			|	startMoveLeft()
	 * @throws	CollisionException()
	 * 			This Alien is colliding if there is a world and at least one of the tiles is not passable and colliding
	 * 			with the Alien in the upper direction.
	 * 			| ((getWorld()!= null) && (!(tile.getGeoFeature().isPassable()) && isColliding(Direction.UP, tile)))
	 * 			This Alien is colliding if there is a world and at least one of the game objects is colliding
	 * 			with the Alien in the upper direction.
	 * 			| ((getWorld()!= null) && (object != this && isColliding(Direction.UP, object)))
	 */
	public void endDuck(){
		try {
			setIsDucked(false);
			if(getWorld()!= null){
				updateSpriteIndex();
				HashSet<Tile> affectedTiles = getWorld().getTilesIn(getPosition().getDisplayedXPosition(),
						getPosition().getDisplayedYPosition(),getPosition().getDisplayedXPosition()
						+getWidth()-1, getPosition().getDisplayedYPosition()+getHeight()-1);
				for(Tile tile: affectedTiles){
					if(!(tile.getGeoFeature().isPassable()) &&
					   isColliding(Direction.UP, tile))
						throw new CollisionException();
				}
				for(GameObject object: getWorld().getAllGameObjects()){
					if(object != this && isColliding(Direction.UP, object))
						throw new CollisionException();
				}
				setEnableStandUp(false);
				setMaxHorVelocity(getMaxHorVelocityRunning());
				if (isMoving(Direction.RIGHT))
					startMove(Direction.RIGHT);
				else if (isMoving(Direction.LEFT))
					startMove(Direction.LEFT);
			}
		} catch (CollisionException e) {
			setIsDucked(true);
			updateSpriteIndex();
			setEnableStandUp(true);
		}
	}
	
	/**
	 * Returns whether the Alien is able to stand up or not.
	 */
	public boolean isEnableStandUp() {
		return enableStandUp;
	}

	/**
	 * Sets the variable storing Alien being able to stand up to the given boolean value.
	 * 
	 * @param 	enableStandUp
	 * 			The given boolean to set.
	 */
	public void setEnableStandUp(boolean enableStandUp) {
		this.enableStandUp = enableStandUp;
	}

	/**
	 * A boolean variable to store whether the Alien is able to stand up or not.
	 */
	private boolean enableStandUp = false;
	
	/**
	 * A method that receives a position in the form of a double array 
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
	 * @note	In the current state, this method violates several rules connected
	 * 			to good programming. It changes the state of an object and returns a value.
	 * 			We are aware of this problem and we will solve it by defensive programming
	 * 			before we hand in the final solution. 
	 */
	@Override
	protected double[] updatePositionTileCollision(double[] newPos){
		assert newPos.length == 2;
		double newXPos = newPos[0];
		double newYPos = newPos[1];
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if (isColliding(Direction.DOWN, impassableTile)){
					//System.out.println("Colliding down");
					if (isMoving(Direction.DOWN))
						newYPos = this.getPosition().getYPosition();
					endMovement(Direction.DOWN);
					setCanFall(false);
				}
				else if(isColliding(Direction.UP, impassableTile)){
					if (isMoving(Direction.UP))
						newYPos = this.getPosition().getYPosition();
					endMovement(Direction.UP);
					//System.out.println("Colliding up");
				}
				if(isColliding(Direction.LEFT, impassableTile)){
					if (isMoving(Direction.LEFT)){
						newXPos = this.getPosition().getXPosition();
					}
					endMovement(Direction.LEFT);
					if(isMoving(Direction.UP))
						endJump();
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, impassableTile)){
					if (isMoving(Direction.RIGHT)){
						newXPos = this.getPosition().getXPosition();
					}
					endMovement(Direction.RIGHT);
					if(isMoving(Direction.UP))
						endJump();
					//System.out.println("Colliding right");
				}
			}
		}
		return doubleArray(newXPos,newYPos);
	}
	
	/**
	 * Returns all game objects that can block the movement of this game object.
	 * 
	 * @return	The resulting hash set contains all game objects belonging to the 
	 * 			world of this Alien.
	 * 			| result.contains(getWorld().getAllGameObjects())
	 */
	@Override
	protected HashSet<GameObject> getBlockingObjects() {
		return getWorld().getAllGameObjects();
	}
	
	/**
	 * Method to update the position and velocity of the character based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @effect	Advance the time of Character with the given time duration.
	 * 			| super.advanceTime(timeDuration)
	 * @effect	The last direction of this Alien is updated.
	 * 			| updateLastDirection()
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
	IllegalYPositionException,IllegalTimeIntervalException{
		super.advanceTime(timeDuration);
		updateLastDirection();
	}
	
	/**
	 * A method to update the movements of this game object.
	 * As an effect of this method, certain movements may be started
	 * 
	 * @effect	Update the movement of Character.
	 * 			| super.updateMovement()
	 * @post	If this Alien is ducked and can come out of ducking state,
	 * 			the Alien ends ducking.
	 * 			| if (isEnableStandUp())
	 * 			|	then endDuck()
	 */
	@Override
	protected void updateMovement(){
		super.updateMovement();
		if (isEnableStandUp())
			endDuck();
	}
	
	/** 
	 * Method to update the position of this game object based on the current position,
	 * velocity and a given time duration in seconds.
	 * 
	 * @post	Calculate the new position as a function of the current attributes
	 * 			of Alien. If there is no world attached to this Alien, this position
	 * 			will be the new position for this Alien.
	 * 			Else, some checkers inspect whether this Alien can have the newly 
	 * 			calculated position as its position. They return the corrected position.
	 * 			This corrected position is than set as the new position for this Alien.
	 * 			| let
	 * 			|	oldPos = getPosition(),
	 * 			| 	if(getWorld() != null)
	 * 			|	newPos = f(getPosition(),getHorDirection(),getHorVelocity(),
	 * 			|			   getHorAcceleration(),getVertDirection(),
	 * 			|			   getVertVelocity(),getVertAcceleration(),timeDuration),
	 * 			|	newPos = updatePositionTileCollision(newPos.toDoubleArray()),
	 * 			|	newPos = updatePositionObjectCollision(newPos)
	 * 			| in
	 * 			|	setPosition(toPosition(newPos,getWorld())),
	 * 			|	oldPosition.terminate()
	 */
	@Override
	protected void updatePosition(double timeDuration){
		double newXPos = getPosition().getXPosition() + getHorDirection().getFactor()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		double newYPos = getPosition().getYPosition() + 
				((getVertDirection().getFactor()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;

		double[] newPos = doubleArray(newXPos,newYPos);
		if(getWorld() != null){
			newPos = updatePositionTileCollision(doubleArray(newXPos,newYPos));
			newPos = updatePositionObjectCollision(newPos);
			
			if (canFall() && !isMoving(Direction.UP)){
				startFall();
			}
			else
				setCanFall(true);
		}
		getPosition().terminate();
		setPosition(toPosition(newPos, getWorld()));
	}

	/**
	 * Return the last registered horizontal direction of the Alien.
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
	 * A method to update the last registered horizontal direction of the Alien.
	 * 
	 * @post	If Alien is moving to the left or the right, the last registered direction 
	 * 			will be updated.
	 * 			| if (getHorDirection() != Direction.NULL)
	 *			|	new.getHorDirection() = getHorDirection()
	 */
	@Model
	private void updateLastDirection() {
		if (getHorDirection() != Direction.NULL)
			setLastDirection(getHorDirection());
	}
	
	/**
	 * A variable storing the last horizontal direction of movement of this Alien
	 * within the last second of in-game-time.
	 */
	private Direction lastDirection = Direction.NULL;

	
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
	 * Check whether the Alien is moving in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction to check.
	 * @pre		The direction must be different from null.
	 * 			| direction != Direction.NULL
	 * @return	True if the horizontal direction is equal to the given direction
	 * 			or if the vertical direction is equal to the given direction.
	 * 			| result == (getHorDirection() == direction || 
	 * 			|		     getVertDirection() == direction)
	 */
	@Override
	public boolean isMoving(Direction direction){
		assert (direction != Direction.NULL);
		return (getHorDirection() == direction || getVertDirection() == direction);
	}
	
	/**
	 * A method to check whether the Alien has moved left or right 
	 * within the last second of in-game-time.
	 * 
	 * @return	True if and only if the last registered horizontal direction
	 * 			of this Alien is not zero and timeSum has not reached 1 second yet.
	 * 			| result == ((getLastDirection() != Direction.NULL)
	 * 			|				 && (getSpritesTimer().getTimeSum() < 1.0))
	 * 
	 */
	private boolean wasMoving(){
		return (getLastDirection() != Direction.NULL && getSpritesTimer().getTimeSum() < 1.0);
				
	}
	
	/**
	 * Checks whether the Alien has moved to the given direction within the last second of in-game-time.
	 * 
	 * @param	direction
	 * 			The direction to check for.
	 * @return	True if and only if this Alien was moving within the last second
	 * 			of in-game-time and its last direction was equal to the given direction.
	 * 			| result == (wasMoving() && (getLastDirection() == direction))
	 */
	private boolean wasMoving(Direction direction){
		return (wasMoving() && getLastDirection() == direction);
	}
	
	/**
	 * A method to check whether this Alien is jumping. 
	 * 
	 * @return	True if and only if the current vertical direction
	 * 			differs from null.
	 * 			| result == (getVertDirection() != Direction.NULL)
	 */
	public boolean isJumping(){
		return (getVertDirection() != Direction.NULL);
	}
	
	/**
	 * A method to update the index in the array of sprites.
	 * 
	 * @post	If this Alien is not moving horizontally, has not moved
	 * 			horizontally within the last second of in-game-time and
	 * 			is not ducking, the index is set to 0.
	 * @post	If this Alien is not moving horizontally, has not moved
	 * 			horizontally within the last second of in-game-time and
	 * 			is ducking, the index is set to 1.
	 * @post	If this Alien is not moving horizontally, has moved
	 * 			right within the last second of in-game-time and
	 * 			is not ducking, the index is set to 2.
	 * @post	If this Alien is not moving horizontally, has moved
	 * 			left within the last second of in-game-time and
	 * 			is not ducking, the index is set to 3.
	 * @post	If this Alien is moving to the right and is jumping
	 * 			and is not ducking, the index is set to 4.
	 * @post	If this Alien is moving to the left and is jumping
	 * 			and is not ducking, the index is set to 5.
	 * @post	If this Alien is ducking and moving to the right or was moving
	 * 			to the right within the last second of in-game-time, the index is set to 6.
	 * @post	If this Alien is ducking and moving to the left or was moving
	 * 			to the left within the last second of in-game-time, the index is set to 7.
	 * @effect	If this Alien is neither ducking nor jumping and moving to the right,
	 * 			the index is set to the next walking animation to the right. 
	 * @effect	If this Alien is neither ducking nor jumping and moving to the left,
	 * 			the index is set to the next walking animation to the left.
	 */
	@Override
	public void updateSpriteIndex(){
		if (getIsDucked()){
			if (isMoving() || wasMoving()){
				if (isMoving(Direction.RIGHT) || wasMoving(Direction.RIGHT))
					setIndex(6);
				else if (isMoving(Direction.LEFT) || wasMoving(Direction.LEFT))
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
		if (getSpritesTimer().getTimeSum()>0.075){
			int newIndex = getIndex() + (int) (Math.floor(getSpritesTimer().getTimeSum()/0.075));
			if (newIndex > (8+getNumberOfWalkingSprites()))
				setIndex((newIndex-8)%getNumberOfWalkingSprites() + 8);
			else
				setIndex(newIndex);
			getSpritesTimer().setTimeSum(getSpritesTimer().getTimeSum()%0.075);}
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
		if (getSpritesTimer().getTimeSum()>0.075){
			int newIndex = getIndex() + (int) (Math.floor(getSpritesTimer().getTimeSum()/0.075));
			if (newIndex > (9+2*getNumberOfWalkingSprites()))
				setIndex((newIndex-(9+getNumberOfWalkingSprites()))%getNumberOfWalkingSprites()
						+ 9+getNumberOfWalkingSprites());
			else
				setIndex(newIndex);
			getSpritesTimer().setTimeSum(getSpritesTimer().getTimeSum()%0.075);}
	}
	
	
	/**
	 * A method to check whether the given array of sprites is valid.
	 * 
	 * @param	sprites
	 * 			The sprites to check.
	 * @return	True if and only if the length of the array is greater than
	 * 			or equal to 10 and the length is an even number.
	 * 			| result == (sprites.length >= 10 && sprites.length %2 == 0)
	 */
	@Model
	private static boolean isValidArrayOfSprites(Sprite[] sprites){
		return (sprites.length >= 10 && sprites.length%2 == 0);
	}
	
			
	/**
	 * Return the number of sprites used for the animation of walking of this Alien.
	 */
	private int getNumberOfWalkingSprites() {
		return numberOfWalkingSprites;
	}
	
	/**
	 * A variable storing the number of sprites used for animation of walking of the Alien.
	 */
	private final int numberOfWalkingSprites;
}
