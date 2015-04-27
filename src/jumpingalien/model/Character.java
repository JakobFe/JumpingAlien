package jumpingalien.model;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import jumpingalien.model.exceptions.*;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class concerning characters as a subclass of game objects.
 * Characters have additional features such as a vertical velocity and acceleration.
 * 
 * @invar	Each character can have its horizontal acceleration as its 
 * 			horizontal acceleration.
 * 			| canHaveAsHorAcceleration(getHorAcceleration())
 * @invar	Each character must have a valid vertical velocity.
 * 			| isValidVertVelocity(getVertVelocity())
 * @invar	Each character can have its vertical acceleration as its vertical acceleration.
 * 			| canHaveAsVertAcceleration(getVertAcceleration())
 * @invar	This character must have a valid vertical direction.
 * 			| isValidVertDirection(getVertDirection())
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
	protected boolean isValidVertDirection(Direction direction){
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
	@Basic@Model
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
	 * @post	If the given vertical velocity is valid, the vertical velocity
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
	@Basic@Model
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
	 * Method to update the position and velocity of the character based on the current position,
	 * velocity and a given time duration in seconds.
	 *
	 * @param	timeDuration
	 * 			A variable indicating the length of the time interval
	 * 			to simulate the movement of this game object. 
	 * @effect	The vertical velocity is updated with the given timeDuration.
	 * 			| updateVertVelocity(timeDuration)			
	 */
	@Override
	public void advanceTime(double timeDuration) throws IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updateMovement();
		double td = getTimeToMoveOnePixel(timeDuration);
		double timeLeft = timeDuration;
		while (timeLeft>td){
			simulateMovement(td);
			timeLeft -= td;
		}
		simulateMovement(timeLeft);
		updateTimers(timeDuration);
		updateHitPoints();
	}
	
	/**
	 * A method to simulate the movement of this character for a small amount of time, 
	 * used in advance time to prevent collisions.
	 * 
	 * @param 	td
	 * 			The time to simulate the movement.
	 * @effect	The position is updated with the given time duration.
	 * 			| updatePosition(td)
	 * @effect	The horizontal velocity is updated with the given time duration.
	 * 			| updateHorVelocity(td)
	 * @effect	The vertical velocity is updated with the given time duration.
	 * 			| updateVertVelocity(td)
	 * @effect	If this character ends up outside the borders of the game world,
	 * 			the hit points are set to zero, the time sum of the hit points 
	 * 			timer is set to 100 and this character is terminated.
	 * 			| let newPos = f(getPosition(),getHorVelocity(),getVertVelocity(),
	 * 			|			     getHorAcceleration(),getVertAcceleration()),
	 * 			| in
	 * 			| 	  if(!Position.isValidXPosition(newPos.getXPosition()) ||
	 * 			|		 !Position.isValidYPosition(newPos.getYPosition()))
	 * 			|			then setHitPoints(0),getHpTimer().setTimeSum(100),
	 * 			|				 terminate() 
	 * 
	 */
	private void simulateMovement(double td) {
		try {
			updatePosition(td);
			updateHorVelocity(td);
			updateVertVelocity(td);
		} catch (NullPointerException e) {
		}
		catch(IllegalXPositionException | IllegalYPositionException exc){
			System.out.println("Illegal position!");
			setHitPoints(0);
			getHpTimer().setTimeSum(100);
			terminate();
		}
	}	
	

	
	/**
	 * A method to update the movements of this game object.
	 * As an effect of this method, certain movements may be started
	 * 
	 * @post	If this game object is dead, the horizontal acceleration is set 
	 * 			to zero.
	 * 			| if(isDead())
	 * 			|	then new.getHorAcceleration() == 0
	 * @post	If this game object is dead and the character is moving up,
	 * 			the vertical velocity is set to zero and the vertical direction 
	 * 			is set to null.
	 * 			| if(isDead() && isMoving(Direction.UP))
	 * 			|	then new.getVertVelocity() == 0 &&
	 * 			|		 new.getVertDirection() == Direction.NULL
	 */
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
	 * A method to check whether this game object can be hurt by a certain terrain type.
	 * 
	 * @param 	terrain
	 * 			The terrain to check.
	 */
	protected abstract boolean canBeHurtBy(Terrain terrain);
	
	/**
	 * A method to check whether a certain terrain type deals damage at contact
	 * to this game object.
	 * 
	 * @param 	terrain
	 * 			The terrain to check.
	 * @return	True if the terrain is of type magma.
	 * 			| if(terrain == Terrain.MAGMA)
	 * 			|	then result == true
	 */
	protected boolean damageAtContact(Terrain terrain){
		return (terrain == Terrain.MAGMA);
	}
	
	/**
	 * A method to update the hit points as  a consequence of being in contact
	 * with a terrain type.
	 * 
	 * @param	terrain
	 * 			The terrain to be damaged by.
	 * @effect	If the game object is not dead and it can be hurt by this 
	 * 			terrain type and the given terrain type deals damage at contact
	 * 			and the time sum of the hit points timer equals zero, an amount
	 * 			of hit points is subtracted from the hit points of this character.
	 * 			| if(!isDead() && canBeHurtBy(terrain) && damageAtContact(terrain))
	 * 			|	if(getHpTimer().getTimeSum() == 0)
	 * 			|		then subtractHp(terrain.getHpLoss())
	 * 			Else if the time sum of the hit points timer is greater than 0.2,
	 * 			the same amount of hit points is subtracted from the hit points 
	 * 			of this character and the time sum of the hit points timer
	 * 			is decremented with 0.2.
	 * 			|	else if(getHpTimer().getTimeSum() > 0.2)
	 * 			|		then subtractHp(terrain.getHpLoss()) && getHpTimer().decrement(0.2)
	 * @effect	Else if the game object is not dead and it can be hurt by this 
	 * 			terrain type and the given terrain type doesn't deal damage at contact
	 * 			and the the time sum of the hit points timer is greater than 0.2,
	 * 			an amount of hit points is subtracted from the hit points of this character 
	 * 			and the time sum of the hit points timer is decremented with 0.2.
	 * 			| else if(!isDead() && canBeHurtBy(terrain) && !damageAtContact(terrain) &&
	 * 			|		   getHpTimer().getTimeSum() == 0)
	 * 			|		then subtractHp(terrain.getHpLoss()) && getHpTimer().decrement(0.2)
	 */
	protected void updateHitPointsTerrain(Terrain terrain){
		if(!isDead() && canBeHurtBy(terrain)){
			if(damageAtContact(terrain)){
				if (getHpTimer().getTimeSum() == 0)
					subtractHp(terrain.getHpLoss());
				else if (getHpTimer().getTimeSum() > 0.2){
					subtractHp(terrain.getHpLoss());
					getHpTimer().decrement(0.2);
				}
			}
			else if (getHpTimer().getTimeSum() > 0.2){
				subtractHp(terrain.getHpLoss());
				getHpTimer().decrement(0.2);
			}
		}		
	}
	
	/**
	 * Return the immune timer belonging to this character.
	 */
	@Basic
	protected Timer getImmuneTimer() {
		return immuneTimer;
	}
	
	/**
	 * Check whether this character is immune.
	 * 
	 * @return	True if the time sum of the immune timer is lower than 0.6.
	 * 			| result == (getImmuneTimer().getTimeSum() < 0.6)
	 */
	public boolean isImmune() {
		return (getImmuneTimer().getTimeSum() < 0.6);
	}
	
	/**
	 * A variable storing a period of elapsed time. This variable 
	 * functions as a timer that increments subsequent time intervals
	 * in the method advanceTime and is used to update the immunity 
	 * of this character.
	 */
	private Timer immuneTimer = new Timer(0.6);
	
	/**
	 * A method to add a given time duration to all timers belonging 
	 * to this game object.
	 * 
	 * @effect	The time duration is added to the immune timer.
	 * 			| getImmuneTimer().counter(timeDuration)
	 */
	@Override
	protected void updateTimers(double timeDuration){
		super.updateTimers(timeDuration);
		getImmuneTimer().increment(timeDuration);
	}
	
	/**
	 * A method to estimate the time duration needed to travel 0.01 meters,
	 * given a certain time duration.
	 * 
	 * @return	The returned value is the result of the following function:
	 * 			| let tdHor  = 0.01/(Math.abs(getHorVelocity())
	 *			|			 + Math.abs(getHorAcceleration())*timeDuration),
	 *			| 	  tdVert = 0.01/(Math.abs(getVertVelocity())
	 *			|			 + Math.abs(getVertAcceleration())*timeDuration),
	 *			|	  min = Math.min(tdHor, tdVert)
	 *			| in
	 *			|	if(min>timeDuration)
	 *			|		then result = timeDuration
	 *			|	else
	 *			|		result = min 			 
	 */
	protected double getTimeToMoveOnePixel(double timeDuration){
		double tdHor = 0.01/(Math.abs(getHorVelocity())
				+Math.abs(getHorAcceleration())*timeDuration);
		double tdVert = 0.01/(Math.abs(getVertVelocity())
				+Math.abs(getVertAcceleration())*timeDuration);
		if(Math.min(tdHor, tdVert) > timeDuration)
			return timeDuration;
		if(Math.min(tdHor, tdVert) > 1.0 && Math.min(tdHor, tdVert) < 10){
			System.out.print("Distance too long for object ");
			System.out.println(this.toString());
			System.out.println(Math.min(tdHor, tdVert));
		}
		return Math.min(tdHor, tdVert);
	}
	
	/**
	 * A method to start falling, this means start moving down with 
	 * the maximum vertical acceleration as vertical acceleration.
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
	
	/**
	 * A method to check whether this character stands on an impassable tile.
	 * 
	 * @return	True if and only if this character is colliding with an impassable
	 * 			tile in the direction down.
	 * 			| result == 
	 * 			|		for some tile in getWorld().getImpassableTiles()
	 * 			|			this.isColliding(Direction.DOWN,tile)
	 */
	protected boolean standsOnTile(){
		for (Tile impassableTile: getWorld().getImpassableTiles()){
			if (this.isOverlappingWith(impassableTile)){
				if (isColliding(Direction.DOWN, impassableTile))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * A method to check whether this character stands on a game object.
	 * 
	 * @return	True if and only if this character is colliding with another 
	 * 			game object in the direction down.
	 * 			| result == 
	 * 			|		for some other in getWorld().getAllGameObjects()
	 * 			|			(this != other) && this.isColliding(Direction.DOWN,other)
	 */
	protected boolean standsOnObject(){
		for (GameObject gameObject: getWorld().getAllGameObjects()){
			if ((gameObject != this) && this.isOverlappingWith(gameObject)){
				if (isColliding(Direction.DOWN, gameObject))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * A method to check whether this character stands on a given game object.
	 * 
	 * @param 	other
	 * 			The game object to check.
	 * @return	True if and only if the other game object is different from
	 * 			this character and if this character is colliding down with the other 
	 * 			game object and the other game object is colliding up with this character.
	 * 			| result == ((other != this) && this.isOverlappingWith(other)
	 *			|			 && isColliding(Direction.DOWN, other) &&
	 *			|		     other.isColliding(Direction.UP, this))
	 */
	protected boolean standsOn(GameObject other){
		return ((other != this) && this.isOverlappingWith(other)
				&& isColliding(Direction.DOWN, other) &&
				other.isColliding(Direction.UP, this));
	}
	
	/**
	 * A method that receives a position in the form of a double array 
	 * and returns the corrected position, after the given position has been checked 
	 * for whether or not this game object would collide with impassable tiles
	 * if the given position would be assigned to this game object.
	 */
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
	
	/**
	 * A method to update the horizontal velocity over a given time interval.
	 * 
	 * @post	If the object is dead, the velocity is left unchanged.
	 * 			| if(isDead())
	 * 			|	then new.getHorVelocity() == this.getHorVelocity()
	 */
	@Model @Override
	protected void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (!isDead()){
			if (newVel > getMaxHorVelocity()){
				setHorVelocity(getMaxHorVelocity());
				setHorAcceleration(0);
			}
			else
				setHorVelocity(newVel);
		}
		
	}
	
	/**
	 * A method to update the vertical velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new vertical velocity.
	 * @effect	The new vertical velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this character.
	 */
	protected void updateVertVelocity(double timeDuration){
		double newVel = getVertDirection().getFactor()*getVertVelocity() + 
				getVertAcceleration() * timeDuration;
		if (newVel<0){
			newVel = -newVel;
			setVertDirection(Direction.DOWN);
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
	protected void endMovement(Direction direction){
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
	
	/**
	 * Check whether the game object is moving in the given direction.
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
	protected boolean isMoving(Direction direction){
		assert (direction != Direction.NULL);
		return (getHorDirection() == direction || getVertDirection() == direction);
	}
}
