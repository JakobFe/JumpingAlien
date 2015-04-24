package jumpingalien.model;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.model.exceptions.*;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class concerning characters as a subclass of game objects.
 * Characters have additional features such as a vertical velocity and acceleration.
 * 
 * @invar	This character must have a valid vertical direction.
 * 
 * @author 	Jakob Festraets, Vincent Kemps
 * @version 1.0
 *
 */
public abstract class Character extends GameObject{
	
	/**
	 * Initialize this new character with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites, given number of hit points and no world.
	 * 
	 * @param 	position
	 * 			The start position for this character.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this character.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity for this character.
	 * @param	sprites
	 * 			An array containing the different sprites for this character.
	 * @param	hitPoints
	 * 			The hit points for this new character. 
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @effect	This character is created as a new game object with given x position,
	 * 			given y position, given initial horizontal velocity,
	 * 			given sprites and given hit points.
	 * 			| super(x,y,initHorVelocity,maxHorVelocity,sprites,hitPoints)
	 */
	@Raw
	protected Character(Position position, double initHorVelocity, 
			double maxHorVelocity, Sprite[] sprites, int hitPoints) 
			throws IllegalArgumentException{
		super(position,initHorVelocity,maxHorVelocity,sprites,hitPoints);
	}
	
	/**
	 * Initialize this new character with given position, 
	 * given initial horizontal velocity, given maximum horizontal velocity,
	 * given sprites, 100 hit points and no world.
	 * 
	 * @param 	position
	 * 			The start position for this character.
	 * @param 	initHorVelocity
	 * 			Initial horizontal velocity for this character.
	 * @param 	maxHorVelocity
	 * 			Maximum horizontal velocity for this character.
	 * @param	sprites
	 * 			An array containing the different sprites for this character.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity,initHorVelocity)
	 * @effect	This character is created with given x position,
	 * 			given y position, given initial horizontal velocity,
	 * 			given sprites and 100 hit points.
	 * 			| this(x,y,initHorVelocity,maxHorVelocity,sprites,100)
	 */
	@Raw
	protected Character(Position position, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		this(position,initHorVelocity,maxHorVelocity,sprites,100);
	}

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
	
	/**
	 * Checks whether the given horizontal acceleration is valid.
	 * 
	 * @param 	horAcceleration
	 * 			Horizontal acceleration to check.
	 * @return	True if and only if the given value lies between
	 * 			zero and the maximum horizontal acceleration.
	 * 			| result == 
	 * 			|	(horAcceleration >= 0) && (horAcceleration <= getMaxHorAcceleration())
	 */
	@Model
	protected boolean canHaveAsHorAcceleration(double horAcceleration){
		return ((horAcceleration >= 0) && (horAcceleration <= getMaxHorAcceleration()));
	}

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
	 * Returns the current vertical direction of this character.
	 */
	@Basic
	public Direction getVertDirection() {
		return vertDirection;
	}
	
	/**
	 * A method to check whether a given direction is a valid vertical direction.
	 * 
	 * @param 	direction
	 * 			The direction to check.
	 * @return	True if the given direction is null, down or up.
	 * 			| result == (direction == Direction.NULL || direction == Direction.DOWN ||
	 *			|			 direction == Direction.UP)
	 */
	public boolean isValidVertDirection(Direction direction){
		return (direction == Direction.NULL || direction == Direction.DOWN ||
				direction == Direction.UP);
	}
	
	/**
	 * Set the vertical direction of this character to the given direction.
	 * 
	 * @param	vertDirection
	 * 			Vertical direction to set.
	 * @pre		The given direction must be a valid direction.
	 * 			| isValidVertDirection(vertDirection)
	 * @post	The new vertical direction of this character is set to the given direction.
	 * 			| new.getVertDirection() == vertDirection
	 */
	@Model
	protected void setVertDirection(Direction vertDirection) {
		assert isValidVertDirection(vertDirection);
		this.vertDirection = vertDirection;
	}
	
	/**
	 * A variable storing the vertical direction.
	 */
	private Direction vertDirection = Direction.NULL;
	
	/**
	 * Return the initial vertical velocity of this character.
	 */
	@Basic @Model
	protected double getInitVertVelocity() {
		return initVertVelocity;
	}
	
	/**
	 * A variable storing the initial vertical velocity of this character. 
	 */
	private double initVertVelocity;

	/**
	 * Return the current vertical velocity of this character.
	 */
	@Basic
	public double getVertVelocity() {
		return vertVelocity;
	}
	
	/**
	 * A method to check whether the given vertical velocity is valid.
	 * 
	 * @param 	vertVelocity
	 * 			The vertical velocity to check.
	 * @return	Returns true if and only if the given value is positive or zero.
	 * 			| result == (vertVelocity >= 0)
	 */
	@Model
	protected static boolean isValidVertVelocity(double vertVelocity){
		return (vertVelocity >= 0); 
	}

	/**
	 * Set the vertical velocity to the given value.
	 * 
	 * @param 	vertVelocity 
	 * 			The vertVelocity to set
	 * @post	If the given vertical is valid, the vertical velocity
	 * 			is set to the given value.
	 * 			| if (isValidVertVelocity(vertVelocity))
				| 	then new.vertVelocity = vertVelocity
	 */
	@Model
	protected void setVertVelocity(double vertVelocity) {
		if (isValidVertVelocity(vertVelocity))
			this.vertVelocity = vertVelocity;
	}
	
	/**
	 * A variable storing the current vertical velocity.
	 * This value will always be a positive number of type double.
	 */
	private double vertVelocity = 0;
	
	/**
	 * Return the maximum vertical acceleration.
	 */
	@Basic @Model
	protected static double getMaxVertAcceleration() {
		return MAX_VERT_ACCELERATION;
	}
	
	/**
	 * A variable storing the maximum vertical acceleration,
	 * the gravitational constant. This variable will always have
	 * (-10 m/s^2) as its value.
	 */
	private static final double MAX_VERT_ACCELERATION = -10;
	
	/**
	 * Return the current vertical acceleration of this character.
	 */
	@Basic
	public double getVertAcceleration() {
		return vertAcceleration;
	}
	
	/**
	 * A method to check whether the given value is a 
	 * valid vertical acceleration.
	 * 
	 * @param 	vertAcceleration
	 * 			Vertical acceleration to check.
	 * @return	True if the given value equals zero or the maximum vertical acceleration.
	 * 			| if(vertAcceleration == 0 || vertAcceleration == getMaxVertAcceleration())
	 * 			|	then result == true
	 */
	@Model
	protected boolean canHaveAsVertAcceleration(double vertAcceleration){
		return (vertAcceleration == 0 || vertAcceleration == getMaxVertAcceleration());
	}
	
	/**
	 * Sets the vertical acceleration to the given value.
	 * 
	 * @param 	vertAcceleration 
	 * 			The vertical acceleration to set.
	 * @post	If the given value is a valid vertical acceleration,
	 * 			the vertical acceleration is set to the given value.
	 * 			| if (canHaveAsVertAcceleration(vertAcceleration))
				| 	then new.vertAcceleration = vertAcceleration
	 */
	@Model
	protected void setVertAcceleration(double vertAcceleration) {
		if (canHaveAsVertAcceleration(vertAcceleration))
			this.vertAcceleration = vertAcceleration;
	}
	
	/**
	 * A variable storing the current vertical acceleration.
	 */
	private double vertAcceleration = 0;	

	/**
	 * Method to update the position and velocity of the Mazub based on the current position,
	 * velocity and a given time duration in seconds.
	 *
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this game object. 
	 * @effect	The vertical velocity is updated with the given timeDuration.
	 * 			| updateVertVelocity(timeDuration)			
	 */
	@Override
	public void advanceTime(double timeDuration){
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updateMovement();
		double td = getTimeToMoveOnePixel(timeDuration);
		if (td > timeDuration)
			td = timeDuration;
		for (int index = 0; index < timeDuration/td; index++){
			try {
				updatePosition(td);
				updateHorVelocity(td);
				updateVertVelocity(td);
			} catch (NullPointerException e) {
			}
			catch(IllegalXPositionException | IllegalYPositionException exc){
				System.out.println("Illegal position!");
				setHitPoints(0);
				terminate();
			}
		}
		updateTimers(timeDuration);
		updateHitPoints();
	}	
	
	@Override
	protected void updateMovement() {
		super.updateMovement();
		if(isDead()){
			setHorAcceleration(0);
			if(isMoving(Direction.UP)){
				setVertVelocity(0);
				setVertDirection(Direction.NULL);
			}
		}
	}
	
	/**
	 * A method to update the hit points as  a consequence of being in contact
	 * with a terrain type.
	 * @param terrain
	 */
	protected void updateHitPointsTerrain(Terrain terrain){
		if(!isDead()){
			if(damageAtContact(terrain)){
				if (getHpTimer().getTimeSum() == 0)
					setHitPoints(getHitPoints()-terrain.getHpLoss());
				else if (getHpTimer().getTimeSum() > 0.2){
					setHitPoints(getHitPoints()-terrain.getHpLoss());
					getHpTimer().setTimeSum(getHpTimer().getTimeSum()-0.2);
				}
			}
			else if (getHpTimer().getTimeSum() > 0.2){
				setHitPoints(getHitPoints()-terrain.getHpLoss());
				getHpTimer().setTimeSum(getHpTimer().getTimeSum()-0.2);
			}
		}		
	}
	
	protected boolean damageAtContact(Terrain terrain){
		return (terrain == Terrain.MAGMA);
	}
	
	@Override
	protected void updateTimers(double timeDuration){
		super.updateTimers(timeDuration);
		getImmuneTimer().counter(timeDuration);
	}
	
	protected double getTimeToMoveOnePixel(double timeDuration){
		double tdHor = 0.01/(Math.abs(getHorVelocity())
				+Math.abs(getHorAcceleration())*timeDuration);
		double tdVert = 0.01/(Math.abs(getVertVelocity())
				+Math.abs(getVertAcceleration())*timeDuration);
		if(Math.min(tdHor, tdVert) > 1.0 && Math.min(tdHor, tdVert) < 10){
			System.out.print("Distance too long for object ");
			System.out.println(this.toString());
			System.out.println(Math.min(tdHor, tdVert));
		}
		return Math.min(tdHor, tdVert);
	}
	
	/**
	 * A method to start falling, this means start moving down.
	 * 
	 * @effect	The vertical direction is set to down.
	 * 			| setVertDirection(Direction.DOWN)
	 * @effect	Set the vertical acceleration to the maximum vertical acceleration.
	 * 			| setVertAcceleration(getMaxVertAcceleration())
	 */
	protected void startFall(){
		setVertDirection(Direction.DOWN);
		setVertAcceleration(getMaxVertAcceleration());
	}
	
	protected boolean standsOnTile(){
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if (isColliding(Direction.DOWN, impassableTile))
					return true;
			}
		}
		return false;
	}
	
	protected boolean standsOnObject(){
		for (GameObject gameObject: getWorld().getAllGameObjects()){
			if ((gameObject != this) && this.isOverlappingWith(gameObject)){
				if (isColliding(Direction.DOWN, gameObject))
					return true;
			}
		}
		return false;
	}
	
	protected boolean standsOn(Character character){
		return ((character != this) && this.isOverlappingWith(character)
				&& isColliding(Direction.DOWN, character) &&
				character.isColliding(Direction.UP, this));
	}
	
	protected double[] updatePositionTileCollision(double[] newPos){
		assert newPos.length == 2;
		double newXPos = newPos[0];
		double newYPos = newPos[1];
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if (isColliding(Direction.DOWN, impassableTile)){
					//System.out.println("Colliding down");
					if (isMoving(Direction.DOWN))
						//newYPos = impassableTile.getYPosition() + getWorld().getTileSize() -1; 
						newYPos = this.getPosition().getYPosition();
					endMovement(Direction.DOWN);
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
					//System.out.println("Colliding left");
				}
				else if(isColliding(Direction.RIGHT, impassableTile)){
					if (isMoving(Direction.RIGHT))
						newXPos = this.getPosition().getXPosition();
					endMovement(Direction.RIGHT);
					//System.out.println("Colliding right");
				}
			}
		}
		return doubleArray(newXPos,newYPos);
	}

	@Model @Override
	protected void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (getHitPoints() != 0){
			if (newVel > getMaxHorVelocity()){
				setHorVelocity(getMaxHorVelocity());
				setHorAcceleration(0);
			}
			else
				setHorVelocity(newVel);
		}
		else{
			setHorVelocity(0);
			setHorAcceleration(0);
		}
	}
	
	/**
	 * A method to update the vertical velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new vertical velocity.
	 * @effect	The new vertical velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this character.
	 * @effect	If this character reaches the ground, the y position is set to zero,
	 * 			the vertical velocity and the vertical acceleration are set to zero
	 * 			and the vertical direction is set to null.
	 * 			| if(getPosition().getYPosition() <= 0)
	 * 			|	then setVertVelocity(0), setVertDirection(Direction.NULL),
	 * 			|		 setVertAcceleration(0);
	 */
	protected void updateVertVelocity(double timeDuration){
		double newVel = getVertDirection().getFactor()*getVertVelocity() + 
				getVertAcceleration() * timeDuration;
		if (newVel<0){
			newVel = -newVel;
			setVertDirection(Direction.DOWN);
		}
		if (getPosition().getYPosition() <= 0){
			getPosition().setYPosition(0);
			setVertVelocity(0);
			setVertDirection(Direction.NULL);
			setVertAcceleration(0);
		}
		setVertVelocity(newVel);
	}
	
	/**
	 * A method to end the movement in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction in which the movement must be ended.
	 * @pre		The given direction must be different from null.
	 * 			| direction != Direction.NULL
	 * @post	If the given direction is left or right, the new horizontal velocity and acceleration
	 * 			are zero, the new horizontal direction is null and the time sum is reset to zero.
	 * 			| if (direction == Direction.LEFT || direction == Direction.RIGHT)
	 * 			|	then new.getHorVelocity() == 0 && new.getHorDirection() == Direction.NULL &&
	 * 			|		 new.getTimeSum() == 0 && new.getHorAcceleration() == 0
	 * @post	If the given direction is up, the new vertical velocity is zero and
	 * 			the new vertical direction is down.
	 * 			| if (direction == Direction.UP)
	 * 			| 	then new.getVertVelocity() == 0 && new.getVertDirection() == Direction.DOWN
	 * @post	If the given direction is down, the new vertical velocity and acceleration are zero 
	 * 			and the new vertical direction is null.
	 * 			| if (direction == Direction.DOWN)
	 * 			| 	then new.getVertVelocity() == 0 && new.getVertDirection() == Direction.NULL &&
	 * 			|		 new.getVertAcceleration() == 0
	 * 
	 */
	@Override
	public void endMovement(Direction direction){
		assert (direction != Direction.NULL);
		if (isMoving(direction)){
			if (direction == Direction.LEFT || direction == Direction.RIGHT){
				setHorVelocity(0);
				setHorDirection(Direction.NULL);
				setHorAcceleration(0);
				getSpritesTimer().reset();
			} 
			else if (direction == Direction.DOWN){
				setVertVelocity(0);
				setVertAcceleration(0);
				setVertDirection(Direction.NULL);
			}
			else if (direction == Direction.UP){
				setVertVelocity(0);
				setVertDirection(Direction.DOWN);
			}
		}
	}
	
	@Override
	public boolean isMoving(Direction direction){
		assert (direction != Direction.NULL);
		return (getHorDirection() == direction || getVertDirection() == direction);
	}
	
	public boolean isImmune() {
		return (getImmuneTimer().getTimeSum() < 0.6);
	}

	public Timer getImmuneTimer() {
		return immuneTimer;
	}

	private Timer immuneTimer = new Timer(0.6);
}
