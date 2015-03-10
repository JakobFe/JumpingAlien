package jumpingalien.model;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class that implements the player character with the ability to jump, duck and
 * run to the left and to the right. This class is a part of the project JumpingAlien.
 * 
 * @invar	The x position of this Mazub must be valid.
 * 			| isValidXPosition(getXPosition())
 * @invar	The y position of this Mazub must be valid.
 * 			| isValidYPosition(getYPosition())
 * @invar	The horizontal direction of this Mazub must be valid.
 * 			| isValidHorDirection(getHorDirection())
 * @invar	The vertical direction of this Mazub must be valid.
 * 			| isValidVertDirection(getVertDirection())
 * @invar 	The initial horizontal velocity of this Mazub must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of the Mazub must be valid.
 * 			| canHaveAsMaxHorVelocity(getMaxHorVelocity())
 * @invar 	The actual horizontal velocity of the Mazub must be valid.
 * 			| canHaveAsHorVelocity(getVelocity()) 
 * 
 * @note	TODO: Formal documentation of constructor. Annotations (Raw!).
 * 			Method ToString? New exception NoSpriteException(for getWidth(),
 * 			getHeight())? .... See tasks!
 * 			
 * 
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 *
 */
public class Mazub {
	
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
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @effect	This Mazub is initialized with the given x as its effective x position.
	 * 			| new.getEffectiveXPosition = setEffectiveXPos(x)
	 * @effect	This Mazub is initialized with the given y as its effective y position.
	 * 			| new.getEffectiveYPosition = setEffectiveYPos(y)
	 * @post	The new Mazub has the given sprites as its sprites.
	 * 			| ...
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x position is not a valid x position.
	 * 			| !isValidXPosition(x)
	 * @throws	IllegalYPositionException(y,this)
	 * 			The given y position is not a valid y position.
	 * 			| !isValidYPosition(y)
	 */
	@Raw
	public Mazub(int x, int y, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) 
			throws IllegalXPositionException,IllegalYPositionException{
		setEffectiveXPos(x);
		setEffectiveYPos(y);
		this.initHorVelocity = initHorVelocity;
		this.maxHorVelocityRunning = maxHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		this.horVelocity = 0;
		this.horAcceleration = 0;
		this.sprites = sprites;
		this.m = (this.sprites.length - 10)/2;
	}
	
	/**
	 * Return the displayed x position of this Mazub.
	 */
	public int getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Check whether the given x is a valid x position.
	 * 
	 * @param 	x
	 * 			x position to check
	 * @return 	True if and only if the given x is not negative and smaller than 
	 * 			the screen width.
	 * 			| result == (x >= 0) && (x < getScreenWidth())
	 */
	public static boolean isValidXPosition(int x){
		return ((x >= 0) && (x < getScreenWidth()));
	}
	
	/**
	 * Set the x-position to display to the given position.
	 * 
	 * @param	x
	 * 			The new x position to be displayed.
	 * @post	The new x position of this Mazub is equal to the given x position.
	 * 			| new.getXPosition() == x
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x position is not a valid x position.
	 * 			| !isValidXPosition(x)
	 */
	public void setXPosition(int x) throws IllegalXPositionException{
		if (!isValidXPosition(x))
			throw new IllegalXPositionException(x,this);
		this.xPosition = x;
	}
	
	/**
	 * Variable storing the x position to be displayed.
	 * 
	 * The x position refers to the x-coordinate in Cartesian coordinates.   
	 * This variable does not store the effective x-coordinate of this Mazub,
	 * but the effective x-coordinate rounded down to an integer number.
	 * This variable is only used in methods concerning the display
	 * of this Mazub in the game world. For all other intentions,
	 * the effective x position is used. 
	 */
	private int xPosition;
	
	/**
	 * Return the effective x-position of this Mazub.
	 */
	@Basic
	public double getEffectiveXPos(){
		return this.effectiveXPos;
	}
	
	/**
	 * Check whether the given x is a valid effective x position.
	 * 
	 * @param 	x
	 * 			effective x position to check
	 * @return 	True if and only if the given x, rounded down to an integer number,
	 * 			is a valid x-position.
	 * 			| result == isValidXPosition( (int) Math.floor(x))
	 */
	public static boolean isValidEffectiveXPos(double x){
		return isValidXPosition((int) Math.floor(x));
	}
	
	/**
	 * Set the effective x-position to the given position.
	 * 
	 * @param	x
	 * 			The new effective x position.
	 * @post	The new effective x position of this Mazub is equal to the given x.
	 * 			| new.getEffectiveXPos() == x
	 * @effect	The displayed x position is set to the effective x-coordinate,
	 * 			rounded down to an integer number.
	 * 			| setXPosition((int) Math.floor(x))
	 * @throws	IllegalXPositionException((int) Math.floor(x),this)
	 * 			The given x position is not a valid effective x position.
	 * 			| !isValidEffectiveXPos(x)
	 */
	public void setEffectiveXPos(double x) throws IllegalXPositionException{
		if (!isValidEffectiveXPos(x))
			throw new IllegalXPositionException((int) Math.floor(x),this);
		this.effectiveXPos = x;
		setXPosition((int) Math.floor(x));
		
	}
	
	/**
	 * Variable storing the effective x position.
	 * 
	 * The effective x position refers to the x-coordinate in Cartesian coordinates.   
	 * This variable is used in methods concerning game physics.
	 */
	private double effectiveXPos;
	
	/**
	 * Return the y-position of this Mazub.
	 */
	public int getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Check whether the given y is a valid y position.
	 * 
	 * @param 	y
	 * 			y position to check
	 * @return 	True if and only if y is not negative and smaller than
	 * 			the screen height.
	 * 			| result == (y >= 0) && (y < getScreenHeight())
	 */
	public static boolean isValidYPosition(int y){
		return ((y >= 0) && (y < getScreenHeight()));
	}
	
	/**
	 * Set the y-position to the given position.
	 * 
	 * @param	y
	 * 			The new y position.
	 * @post	The new y position of this Mazub is equal to the given y position.
	 * 			| new.getYPosition() == y
	 * @throws	IllegalYPositionException(y,this)
	 * 			The given y position is not a valid y position.
	 * 			| !isValidYPosition(y)
	 */
	public void setYPosition(int y) throws IllegalYPositionException{
		if (!isValidYPosition(y))
			throw new IllegalYPositionException(y,this);
		this.yPosition = y;
	}
	
	/**
	 * Variable storing the y position to be displayed.
	 * 
	 * The y position refers to the y-coordinate in Cartesian coordinates.   
	 * This variable does not store the effective y-coordinate of this Mazub,
	 * but the effective y-coordinate rounded down to an integer number.
	 * This variable is only used in methods concerning the display
	 * of this Mazub in the game world. For all other intentions,
	 * the effective y position is used. 
	 */
	private int yPosition;	
	
	/**
	 * Return the effective y-position of this Mazub.
	 */
	@Basic
	public double getEffectiveYPos(){
		return this.effectiveYPos;
	}
	
	/**
	 * Check whether the given y is a valid effective y position.
	 * 
	 * @param 	y
	 * 			effective y position to check
	 * @return 	True if and only if the given y, rounded down
	 * 			to an integer number, is a valid y-position.
	 * 			| result == isValidYPosition((int) Math.floor(y))
	 */
	public static boolean isValidEffectiveYPos(double y){
		return isValidYPosition((int) Math.floor(y));
	}
	
	/**
	 * Set the effective y position to the given position.
	 * 
	 * @param	y
	 * 			The new effective y position.
	 * @post	The new effective y position of this Mazub is equal to the given y.
	 * 			| new.getEffectiveYPos() == y
	 * @effect	The displayed y position is set to the effective y position,
	 * 			rounded down to an integer number.
	 * 			| setYPosition((int) Math.floor(y))
	 * @throws	IllegalYPositionException((int) Math.floor(y),this)
	 * 			The given y is not a valid effective y position.
	 * 			| !isValidEffectiveYPosition(y)
	 */
	public void setEffectiveYPos(double y) throws IllegalYPositionException{
		if (!isValidEffectiveYPos(y))
			throw new IllegalYPositionException((int) Math.floor(y),this);
		this.effectiveYPos = y;
		setYPosition((int) Math.floor(y));
	}
	
	/**
	 * Variable storing the effective y position.
	 * 
	 * The effective y position refers to the y-coordinate in Cartesian coordinates.   
	 * This variable is used in methods concerning game physics.
	 */
	private double effectiveYPos;
	
	/**
	 * Returns the screen width of the game world.
	 */
	@Basic @Immutable
	public static int getScreenWidth(){
		return SCREEN_WIDTH;
	}
	
	/**
	 * A variable storing the screen width of the game world.
	 */
	public static final int SCREEN_WIDTH = 1024;
	
	/**
	 * Returns the screen height of the game world.
	 */
	@Basic @Immutable
	public static int getScreenHeight(){
		return SCREEN_HEIGHT;
	}
	
	/**
	 * A variable storing the screen height of the game world.
	 */
	public static final int SCREEN_HEIGHT = 768;
	
	/**
	 * Return the width of the current sprite of this Mazub.
	 */
	@Basic
	public int getWidth(){
		return this.getCurrentSprite().getWidth();
		//return 1;
	}
	
	/**
	 * Return the height of the current sprite of this Mazub.
	 */
	@Basic
	public int getHeight(){
		return this.getCurrentSprite().getHeight();
		//return 1;
	}
	
	/**
	 * Returns the current horizontal direction of this Mazub.
	 */
	@Basic
	public int getHorDirection() {
		return horDirection;
	}
	
	/**
	 * Checks whether the given direction is valid.
	 * @param	direction
	 * 			The direction to check.
	 * @return	True if and only if the given direction equals 1,-1 or 0.
	 * 			| result == (direction==1 || direction == -1 || direction == 0)
	 */
	public static boolean isValidDirection(int direction){
		return (direction==1 || direction == -1 || direction == 0);
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
	public void setHorDirection(int horDirection) {
		assert isValidDirection(horDirection);
		this.horDirection = horDirection;
	}
	
	/**
	 * An integer value storing the horizontal direction.
	 * 1 indicates moving right, -1 indicates moving left,
	 * 0 indicates not moving in horizontal direction.
	 */
	private int horDirection;
	
	/**
	 * Returns the current vertical direction of this Mazub.
	 */
	@Basic
	public int getVertDirection() {
		return vertDirection;
	}
	
	/**
	 * Set the vertical direction of this Mazub to the given direction.
	 * 
	 * @param	vertDirection
	 * 			Vertical direction to set.
	 * @pre		The given direction must be a valid direction.
	 * 			| isValidDirection(vertDirection)
	 * @post	The new vertical direction of this Mazub is set to the given direction.
	 * 			| new.getVertDirection() == vertDirection
	 */
	public void setVertDirection(int vertDirection) {
		assert isValidDirection(vertDirection);
		this.vertDirection = vertDirection;
	}
	
	/**
	 * An integer value storing the vertical direction.
	 * 1 indicates moving up, -1 indicates moving down,
	 * 0 indicates not moving in vertical direction.
	 */
	private int vertDirection;
	
	/**
	 * Return the initial horizontal velocity of this Mazub.
	 */
	@Basic @Immutable
	public double getInitHorVelocity(){
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
	public static boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity >= 1);
	}
	
	/**
	 * A variable storing the initial horizontal velocity of this Mazub.
	 */
	private final double initHorVelocity;
	
	/**
	 * Return the maximum horizontal velocity while running for this Mazub.
 	 */
	@Basic @Immutable
	public double getMaxHorVelocityRunning(){
		return this.maxHorVelocityRunning;
	}

	/**
	 * A variable storing the maximum horizontal velocity while running.
	 */
	private final double maxHorVelocityRunning;
	
	/**
	 * Return the maximum horizontal velocity while ducking for this Mazub.
 	 */
	@Basic @Immutable
	public static double getMaxHorVelocityDucking(){
		return MAX_HOR_VELOCITY_DUCKING;
	}
	
	/**
	 * A variable storing the maximum horizontal velocity while ducking.
	 * This variable will always have 1 m/s as its value.
	 */
	private static final double MAX_HOR_VELOCITY_DUCKING = 1;
	
	/**
	 * Return the current maximum horizontal velocity.
	 */
	public double getMaxHorVelocity(){
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
	public boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
		return (maxHorVelocity >= getInitHorVelocity()) ||
				maxHorVelocity == getMaxHorVelocityDucking();
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
	public void setMaxHorVelocity(double maxHorVelocity){
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
	public boolean canHaveAsHorVelocity(double horVelocity){
		return  (horVelocity == 0) ||
				((horVelocity >= this.getInitHorVelocity()) &&
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
	public void setHorVelocity(double horVelocity) {
		if (canHaveAsHorVelocity(horVelocity))
			this.horVelocity = horVelocity;
	}
	
	/**
	 * A variable storing the current horizontal velocity of this Mazub.
	 * This value will always be a positive number of type double, or zero.
	 */
	private double horVelocity;
	
	
	/**
	 * Return the maximum horizontal acceleration of the Mazub.
	 */
	@Basic @Immutable
	public static double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this Mazub.
	 */
	private static final double maxHorAcceleration = 0.9;
	
	/**
	 * Return the current horizontal acceleration of this Mazub.
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
	public static boolean isValidHorAcceleration(double horAcceleration){
		return ((horAcceleration >= 0) && (horAcceleration <= getMaxHorAcceleration()));
	}

	/**
	 * Sets the horizontal acceleration to the given value.
	 * 
	 * @param 	horAcceleration 
	 * 			The horAcceleration to set.
	 * @post	If the given value is valid, the horizontal acceleration is set to the given
	 * 			value.
	 * 			| if (isValidHorAcceleration(horAcceleration))
	 * 			|	then new.getHorAcceleration() = horAcceleration
	 */
	public void setHorAcceleration(double horAcceleration) {
		if (isValidHorAcceleration(horAcceleration))
			this.horAcceleration = horAcceleration;			
	}

	/**
	 * A variable storing the current horizontal acceleration.
	 * This variable will always store a positive number of type double, 
	 * or it will store zero.
	 */
	private double horAcceleration;
	
	/**
	 * Return the initial vertical velocity of this Mazub.
	 */
	@Basic
	public static double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}
	
	/**
	 * A variable storing the initial vertical velocity. 
	 * This variable will always have 8 m/s as its value.
	 */
	private static final double INIT_VERT_VELOCITY = 8;

	/**
	 * Return the current vertical velocity of this Mazub.
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
	public static boolean isValidVertVelocity(double vertVelocity){
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
	public void setVertVelocity(double vertVelocity) {
		if (isValidVertVelocity(vertVelocity))
			this.vertVelocity = vertVelocity;
	}
	
	/**
	 * A variable storing the current vertical velocity.
	 *  This value will always be a positive number of type double.
	 */
	private double vertVelocity;
	
	/**
	 * Return the current vertical acceleration of this Mazub.
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
	 * @return	True if and only is the given value equals zero
	 * 			or the maximum vertical acceleration.
	 * 			| result = (vertAcceleration == 0 || 
	 * 			|			vertAcceleration == getMaxVertAcceleration());
	 */
	public static boolean isValidVertAcceleration(double vertAcceleration){
		return (vertAcceleration == 0 || vertAcceleration == getMaxVertAcceleration());
	}
	
	/**
	 * Sets the vertical acceleration to the given value.
	 * 
	 * @param 	vertAcceleration 
	 * 			The vertical acceleration to set.
	 * @post	If the given value is a valid vertical acceleration,
	 * 			the vertical acceleration is set to the given value.
	 * 			| if (isValidVertAcceleration(vertAcceleration))
				| 	then new.vertAcceleration = vertAcceleration
	 */
	public void setVertAcceleration(double vertAcceleration) {
		if (isValidVertAcceleration(vertAcceleration))
			this.vertAcceleration = vertAcceleration;
	}
	
	/**
	 * A variable storing the current vertical acceleration.
	 * This variable will always be a negative number of type double, or zero.
	 */
	private double vertAcceleration;
	
	/**
	 * Return the maximum vertical acceleration.
	 */
	@Basic
	public static double getMaxVertAcceleration() {
		return MAX_VERT_ACCELERATION;
	}
	
	/**
	 * A variable storing the maximum vertical acceleration,
	 * the gravitational constant. This variable will always have
	 * (-10 m/s^2) as its value.
	 */
	private static final double MAX_VERT_ACCELERATION = -10;

	/**
	 * Method to start the movement of the Mazub to the left.
	 * 
	 * @pre		This Mazub must have a valid initial horizontal velocity.
	 * 			| ...
	 * @pre		This mazub must have a valid maximum horizontal acceleration.
	 * 			| ...
	 * @effect	The horizontal velocity of this Mazub is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to -1.
	 * 			| setHorDirection(-1)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 */
	public void startMoveLeft(){
		// asserts toevoegen
		setHorVelocity(getInitHorVelocity());
		setHorDirection(-1);
		setHorAcceleration(getMaxHorAcceleration());
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the left.
	 * 
	 * @effect	If the current horizontal direction equals -1,
	 * 			the horizontal velocity, the horizontal direction and
	 * 			the horizontal acceleration are set to zero.
	 * 			| if (getHorDirection()==-1)
	 * 			| 	then setHorVelocity(0),setHorDirection(0),
	 * 			|	setHorAcceleration(0)
	 */
	public void endMoveLeft(){
	if (getHorDirection()==-1){
		setHorVelocity(0);
		setHorDirection(0);
		setHorAcceleration(0);
		}
	}
	
	/**
	 * Method to start the movement of the Mazub to the right.
	 * 
	 * @pre		This Mazub must have a valid initial horizontal velocity.
	 * 			| ...
	 * @pre		This mazub must have a valid maximum horizontal acceleration.
	 * 			| ...
	 * @effect	The horizontal velocity of this Mazub is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to 1.
	 * 			| setHorDirection(1)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 */
	public void startMoveRight(){
		setHorVelocity(getInitHorVelocity());
		setHorDirection(1);
		setHorAcceleration(getMaxHorAcceleration());
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the right.
	 * 
	 * @effect	If the current horizontal direction equals 1,
	 * 			the horizontal velocity, the horizontal direction and
	 * 			the horizontal acceleration are set to zero.
	 * 			| if (getHorDirection()==1){
	 * 			| 	then setHorVelocity(0),setHorDirection(0),
	 * 			|	setHorAcceleration(0)
	 */
	public void endMoveRight(){
	if (getHorDirection() == 1){	
		setHorVelocity(0);
		setHorDirection(0);
		setHorAcceleration(0);
		}
	}
	
	public void startJump() throws IllegalJumpInvokeException{
		if (getVertDirection() != 0) {
			throw new IllegalJumpInvokeException(this);
		}
		setVertVelocity(getInitVertVelocity());
		setVertAcceleration(getMaxVertAcceleration());
		setVertDirection(1);
	}
	
	public void endJump(){
		if (getVertDirection() == 1)
			setVertVelocity(0);
	}
	
	public void startDuck(){
		setMaxHorVelocity(getMaxHorVelocityDucking());
	}
	
	public void endDuck(){
		setMaxHorVelocity(getMaxHorVelocityRunning());
		if (isMovingRight())
			startMoveRight();
		else if (isMovingLeft())
			startMoveLeft();
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}
	
	public boolean isValidIndex(int index){
		return (index >= 0 || index < m*2+10);
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		assert isValidIndex(index);
		this.index = index;
	}
	
	
	
	public boolean isDucking(){
		return (getMaxHorVelocity() == 1);
	}
	
	public boolean isMoving(){
		return (getHorDirection() != 0);
	}
	
	public boolean isMovingRight(){
		return (getHorDirection() == 1);
	}
	
	public boolean isMovingLeft(){
		return (getHorDirection() == -1);
	}
	
	public boolean wasMoving(){
		return wasMovingLeft() || wasMovingRight();
	}
	
	public boolean wasMovingLeft(){
		if (this.lastDirection == -1){
			if (timeSum < 1.0){
				return true;
			}
			else{
				setLastDirection(0);
				timeSum = 0;
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public boolean wasMovingRight(){
		if (this.lastDirection == 1){
			if (timeSum < 1.0){
				return true;
			}
			else{
				setLastDirection(0);
				timeSum = 0;
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public boolean isJumping(){
		return (getVertDirection() != 0);
	}
	
	public Sprite getCurrentSprite(){
		if (isDucking()){
			if (isMoving() || wasMoving()){
				if (isMovingRight() || wasMovingRight())
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
					if (isMovingRight())
						setIndex(4);
					else
						// isMovingLeft() == true
						setIndex(5);
				}
				else if (isMovingRight()){
						//8..8+m
						if ((index < 8) || (index >= (8+m)))
							setIndex(8);
						else
							setIndex(getIndex()+1);
				}
				else{
					//isMovingLeft() == true
					//9+m..9+2m
					if ((index < (9+m)) || (index >= (9+2*m)))
						setIndex(9+m);
					else
						setIndex(getIndex()+1);
						}
		}
		else if (wasMovingRight())
				setIndex(2);
		else if (wasMovingLeft())
				setIndex(3);
		else
			// Mazub is standing still longer than 1 second
			setIndex(0);
		return sprites[getIndex()];
	}	
	
	/**
	 * A variable storing all possible sprites for this character.
	 */
	public Sprite[] sprites;
	
	/**
	 * Method to update the position and velocity of the Mazub based on the current position,
	 * velocity and a given time duration in seconds.
	 */
	public void advanceTime(double timeDuration){
		updateHorPosition(timeDuration);
		updateHorVelocity(timeDuration);
		try{
			updateVertPosition(timeDuration);
		}
		catch(IllegalYPositionException exc){
			setEffectiveYPos(0);
		}
		updateVertVelocity(timeDuration);
		counter(timeDuration);
		updateLastDirection();
	}
	
	public void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (newVel > getMaxHorVelocity()){
			setHorVelocity(getMaxHorVelocity());
			setHorAcceleration(0);
		}
		else
			setHorVelocity(newVel);
	}
	
	public void updateHorPosition(double timeDuration){
		double newXPos = getEffectiveXPos() + getHorDirection()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		setEffectiveXPos(newXPos);
	}
	
	public void updateVertVelocity(double timeDuration){
		double newVel = getVertDirection()*getVertVelocity() + getVertAcceleration() * timeDuration;
		if (newVel<0){
			newVel = -newVel;
			setVertDirection(-1);
		}
		if (getYPosition() <= 0){
			setEffectiveYPos(0);
			setVertVelocity(0);
			setVertDirection(0);
			setVertAcceleration(0);
		}
		setVertVelocity(newVel);
	}
	
	public void updateVertPosition(double timeDuration)
			throws IllegalYPositionException{
		double newYPos = getEffectiveYPos() + 
				((getVertDirection()*getVertVelocity()*timeDuration)+ 
				0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		if (newYPos<0)
			throw new IllegalYPositionException(newYPos, this);
		else
			setEffectiveYPos(newYPos);
	}
	
	
	
	public void counter(double timeDuration){
		if (getHorDirection() == 0){
			this.timeSum += timeDuration;
		}
		else
			this.timeSum = 0;
	}
	
	/**
	 * @return the lastDirection
	 */
	public int getLastDirection() {
		return lastDirection;
	}

	/**
	 * @param lastDirection the lastDirection to set
	 */
	public void setLastDirection(int lastDirection) {
		this.lastDirection = lastDirection;
	}

	public void updateLastDirection() {
		if (getHorDirection() != 0)
			this.lastDirection = getHorDirection();
	}

	public int index;
	public int m;
	public double timeSum;
	public int lastDirection;
}