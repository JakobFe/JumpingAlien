package jumpingalien.model;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class that implements the player character with the ability to jump, duck and
 * run to the left and to the right. This class is a part of the project JumpingAlien.
 * 
 * @invar	The effective x position of this Mazub must be valid.
 * 			| isValidEffectiveXPos(getEffectiveXPos())
 * @invar	The effective y position of this Mazub must be valid.
 * 			| isValidEffectiveYPos(getEffectiveYPos())
 * @invar	The horizontal direction of this Mazub must be valid.
 * 			| isValidHorDirection(getHorDirection())
 * @invar	The vertical direction of this Mazub must be valid.
 * 			| isValidVertDirection(getVertDirection())
 * @invar 	The initial horizontal velocity of this Mazub must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of the Mazub must be valid.
 * 			| canHaveAsMaxHorVelocity(getMaxHorVelocity())
 * @invar 	The current horizontal velocity of this Mazub must be valid.
 * 			| canHaveAsHorVelocity(getVelocity()) 
 * @invar	The x position must be equal to the effective x position, 
 * 			rounded down to an integer number.
 * 			| getXPosition() == (int) Math.floor(getEffectiveXPos())
 * @invar	The y position must be equal to the effective y position, 
 * 			rounded down to an integer number.
 * 			| getYPosition() == (int) Math.floor(getEffectiveYPos())
 * 			
 * @author	Jakob Festraets, Vincent Kemps
 * 			Course of studies: 2nd Bachelor of engineering
 * 			Code repository: https://github.com/JakobFe/JumpingAlien
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
	 * @param	sprites
	 * 			An array containing the different sprites for this Mazub.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(initHorVelocity)
	 * @pre		The maximum horizontal velocity must be valid.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @pre		The sprites must be an array with a valid number of sprites.
	 * 			| isValidArrayOfSprites(sprites)
	 * @effect	This Mazub is initialized with the given x as its effective x position.
	 * 			| setEffectiveXPos(x)
	 * @effect	This Mazub is initialized with the given y as its effective y position.
	 * 			| setEffectiveYPos(y)
	 * @post	The new Mazub has the given sprites as its sprites.
	 * 			| new.getAllSprites() == sprites
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
		assert isValidInitHorVelocity(initHorVelocity);
		this.initHorVelocity = initHorVelocity;
		assert canHaveAsMaxHorVelocity(maxHorVelocity);
		this.maxHorVelocityRunning = maxHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		assert isValidArrayOfSprites(sprites);
		this.sprites = sprites;
		this.numberOfWalkingSprites = (this.sprites.length - 10)/2;
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
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x position is not a valid x position.
	 * 			| !isValidXPosition(x)
	 * @throws	IllegalYPositionException(y,this)
	 * 			The given y position is not a valid y position.
	 * 			| !isValidYPosition(y)
	 */
	@Raw
	public Mazub(int x,int y, Sprite[] sprites) 
			throws IllegalXPositionException, IllegalYPositionException{
		this(x,y,1,3,sprites);
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
	@Model
	private static boolean isValidXPosition(int x){
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
	@Raw @Model
	private void setXPosition(int x) throws IllegalXPositionException{
		if (!isValidXPosition(x))
			throw new IllegalXPositionException(x,this);
		this.xPosition = x;
	}
	
	/**
	 * Variable storing the x position to be displayed.
	 * 
	 * The x position is an integer number and refers to the 
	 * x-coordinate of the left bottom pixel of this Mazub 
	 * in Cartesian coordinates. This variable does not store 
	 * the effective x-coordinate of this Mazub, but the 
	 * effective x-coordinate rounded down to an integer number.
	 * This variable is only used in methods concerning the display
	 * of this Mazub in the game world. For all other intentions,
	 * the effective x position is used. 
	 */
	private int xPosition;
	
	/**
	 * Return the effective x-position of this Mazub.
	 */
	@Basic @Model
	private double getEffectiveXPos(){
		return this.effectiveXPos;
	}
	
	/**
	 * Check whether the given x is a valid effective x position.
	 * 
	 * @param 	x
	 * 			Effective x position to check
	 * @return 	True if and only if the given x, rounded down to an integer number,
	 * 			is a valid x-position.
	 * 			| result == isValidXPosition( (int) Math.floor(x))
	 */
	@Model
	private static boolean isValidEffectiveXPos(double x){
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
	@Model
	private void setEffectiveXPos(double x) throws IllegalXPositionException{
		if (!isValidEffectiveXPos(x))
			throw new IllegalXPositionException((int) Math.floor(x),this);
		this.effectiveXPos = x;
		setXPosition((int) Math.floor(x));
	}
	
	/**
	 * Variable storing the effective x position.
	 * 
	 * The effective x position is a number of type double and
	 * refers to the x-coordinate of the left bottom pixel of this Mazub
	 * in Cartesian coordinates. This variable is used in 
	 * methods concerning game physics.
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
	@Model
	private static boolean isValidYPosition(int y){
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
	@Raw @Model
	private void setYPosition(int y) throws IllegalYPositionException{
		if (!isValidYPosition(y))
			throw new IllegalYPositionException(y,this);
		this.yPosition = y;
	}
	
	/**
	 * Variable storing the y position to be displayed.
	 * 
	 * The y position is an integer number and refers to the 
	 * y-coordinate of the left bottom pixel of this Mazub 
	 * in Cartesian coordinates. This variable does not store 
	 * the effective y-coordinate of this Mazub, but the 
	 * effective y-coordinate rounded down to an integer number.
	 * This variable is only used in methods concerning the display
	 * of this Mazub in the game world. For all other intentions,
	 * the effective y position is used. 
	 */
	private int yPosition = 0;	
	
	/**
	 * Return the effective y-position of this Mazub.
	 */
	@Basic @Model
	private double getEffectiveYPos(){
		return this.effectiveYPos;
	}
	
	/**
	 * Check whether the given y is a valid effective y position.
	 * 
	 * @param 	y
	 * 			effective y position to check.
	 * @return 	True if and only if the given y, rounded down
	 * 			to an integer number, is a valid y-position.
	 * 			| result == isValidYPosition((int) Math.floor(y))
	 */
	@Model
	private static boolean isValidEffectiveYPos(double y){
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
	@Model
	private void setEffectiveYPos(double y) throws IllegalYPositionException{
		if (!isValidEffectiveYPos(y))
			throw new IllegalYPositionException((int) Math.floor(y),this);
		this.effectiveYPos = y;
		setYPosition((int) Math.floor(y));
	}
	
	/**
	 * Variable storing the effective y position.
	 * 
	 * The effective y position is a number of type double and
	 * refers to the y-coordinate of the left bottom pixel of this Mazub
	 * in Cartesian coordinates. This variable is used in 
	 * methods concerning game physics.
	 */
	private double effectiveYPos = 0;
	
	/**
	 * Returns the screen width of the game world.
	 */
	@Basic @Immutable @Model
	private static int getScreenWidth(){
		return SCREEN_WIDTH;
	}
	
	/**
	 * A variable storing the screen width of the game world.
	 */
	private static final int SCREEN_WIDTH = 1024;
	
	/**
	 * Returns the screen height of the game world.
	 */
	@Basic @Immutable @Model
	private static int getScreenHeight(){
		return SCREEN_HEIGHT;
	}
	
	/**
	 * A variable storing the screen height of the game world.
	 */
	private static final int SCREEN_HEIGHT = 768;
	
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
	@Model
	private static boolean isValidDirection(int direction){
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
	@Model
	private void setHorDirection(int horDirection) {
		assert isValidDirection(horDirection);
		this.horDirection = horDirection;
	}
	
	/**
	 * An integer value storing the horizontal direction.
	 * 1 indicates moving right, -1 indicates moving left,
	 * 0 indicates not moving in horizontal direction.
	 */
	private int horDirection = 0;
	
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
	@Model
	private void setVertDirection(int vertDirection) {
		assert isValidDirection(vertDirection);
		this.vertDirection = vertDirection;
	}
	
	/**
	 * An integer value storing the vertical direction.
	 * 1 indicates moving up, -1 indicates moving down,
	 * 0 indicates not moving in vertical direction.
	 */
	private int vertDirection = 0;
	
	/**
	 * Return the initial horizontal velocity of this Mazub.
	 */
	@Basic @Immutable @Model
	private double getInitHorVelocity(){
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
	private static boolean isValidInitHorVelocity(double initHorVelocity){
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
	private double getMaxHorVelocityRunning(){
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
	 * Return the current maximum horizontal velocity.
	 */
	@Basic @Model
	private double getMaxHorVelocity(){
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
	private boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
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
	@Model
	private void setMaxHorVelocity(double maxHorVelocity){
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
	private boolean canHaveAsHorVelocity(double horVelocity){
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
	@Model
	private void setHorVelocity(double horVelocity) {
		if (canHaveAsHorVelocity(horVelocity))
			this.horVelocity = horVelocity;
	}
	
	/**
	 * A variable storing the current horizontal velocity of this Mazub.
	 * This value will always be a positive number of type double, or zero.
	 */
	private double horVelocity = 0;
	
	
	/**
	 * Return the maximum horizontal acceleration of the Mazub.
	 */
	@Basic @Immutable @Model
	private static double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	
	/**
	 * A variable storing the maximum horizontal acceleration for this Mazub.
	 * This variable must always store a positive number of type double or zero.
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
	@Model
	private static boolean isValidHorAcceleration(double horAcceleration){
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
	@Model
	private void setHorAcceleration(double horAcceleration) {
		if (isValidHorAcceleration(horAcceleration))
			this.horAcceleration = horAcceleration;			
	}

	/**
	 * A variable storing the current horizontal acceleration.
	 * This variable will always store a positive number of type double, 
	 * or it will store zero.
	 */
	private double horAcceleration = 0;
	
	/**
	 * Return the initial vertical velocity of this Mazub.
	 */
	@Basic @Model
	private static double getInitVertVelocity() {
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
	@Model
	private static boolean isValidVertVelocity(double vertVelocity){
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
	private void setVertVelocity(double vertVelocity) {
		if (isValidVertVelocity(vertVelocity))
			this.vertVelocity = vertVelocity;
	}
	
	/**
	 * A variable storing the current vertical velocity.
	 *  This value will always be a positive number of type double.
	 */
	private double vertVelocity = 0;
	
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
	@Model
	private static boolean isValidVertAcceleration(double vertAcceleration){
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
	@Model
	private void setVertAcceleration(double vertAcceleration) {
		if (isValidVertAcceleration(vertAcceleration))
			this.vertAcceleration = vertAcceleration;
	}
	
	/**
	 * A variable storing the current vertical acceleration.
	 * This variable will always be a negative number of type double, or zero.
	 */
	private double vertAcceleration = 0;
	
	/**
	 * Return the maximum vertical acceleration.
	 */
	@Basic @Model
	private static double getMaxVertAcceleration() {
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
	 * 			| isValidInitHorVelocity(getInitHorVelocity())
	 * @pre		This mazub must have a valid maximum horizontal acceleration.
	 * 			| isValidHorAcceleration(getMaxHorAcceleration())
	 * @effect	The horizontal velocity of this Mazub is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to -1.
	 * 			| setHorDirection(-1)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void startMoveLeft(){
		assert isValidInitHorVelocity(getInitHorVelocity());
		assert isValidHorAcceleration(getMaxHorAcceleration());
		setHorVelocity(getInitHorVelocity());
		setHorDirection(-1);
		setHorAcceleration(getMaxHorAcceleration());
		setTimeSum(0);
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the left.
	 * 
	 * @effect	If this Mazub is moving left,
	 * 			the horizontal velocity, the horizontal direction and
	 * 			the horizontal acceleration are set to zero.
	 * 			| if (isMovingLeft())
	 * 			| 	then setHorVelocity(0),setHorDirection(0),
	 * 			|	setHorAcceleration(0)
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void endMoveLeft(){
		if (isMovingLeft()){
			setHorVelocity(0);
			setHorDirection(0);
			setHorAcceleration(0);
			setTimeSum(0);
		}
	}
	
	/**
	 * Method to start the movement of the Mazub to the right.
	 * 
	 * @pre		This Mazub must have a valid initial horizontal velocity.
	 * 			| isValidInitHorVelocity(getInitHorVelocity())
	 * @pre		This mazub must have a valid maximum horizontal acceleration.
	 * 			| isValidHorAcceleration(getMaxHorAcceleration())
	 * @effect	The horizontal velocity of this Mazub is set to the initial
	 * 			horizontal velocity.
	 * 			| setHorVelocity(getInitHorVelocity())
	 * @effect	The horizontal direction is set to 1.
	 * 			| setHorDirection(1)
	 * @effect	The horizontal acceleration is set to the maximum 
	 * 			horizontal acceleration.
	 * 			| setHorAcceleration(getMaxHorAcceleration());
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void startMoveRight(){
		setHorVelocity(getInitHorVelocity());
		setHorDirection(1);
		setHorAcceleration(getMaxHorAcceleration());
		setTimeSum(0);
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the right.
	 * 
	 * @effect	If this Mazub is moving right,
	 * 			the horizontal velocity, the horizontal direction and
	 * 			the horizontal acceleration are set to zero.
	 * 			| if (isMovingRight()){
	 * 			| 	then setHorVelocity(0),setHorDirection(0),
	 * 			|	setHorAcceleration(0)
	 * @effect	The timer 'Timesum' is reset to zero.
	 * 			| setTimeSum(0)
	 */
	public void endMoveRight(){
	if (isMovingRight()){	
		setHorVelocity(0);
		setHorDirection(0);
		setHorAcceleration(0);
		setTimeSum(0);
		}
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
		setVertDirection(1);
	}
	
	/**
	 * Method to end the jumping movement of the Mazub.
	 * 
	 * @effect	if the Mazub is still moving up, the vertical velocity is set to zero.
	 * 			| if (getVertDirection() == 1)
	 *			|	setVertVelocity(0)
	 */
	public void endJump(){
		if (getVertDirection() == 1)
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
		setIsDucked(false);
		setMaxHorVelocity(getMaxHorVelocityRunning());
		if (isMovingRight())
			startMoveRight();
		else if (isMovingLeft())
			startMoveLeft();
	}
	
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
	 * 
	 */
	public void advanceTime(double timeDuration) throws IllegalXPositionException,
				IllegalYPositionException,IllegalTimeIntervalException{
		if (!isValidTimeInterval(timeDuration))
			throw new IllegalTimeIntervalException(this);
		updateHorPosition(timeDuration);
		updateHorVelocity(timeDuration);
		updateVertPosition(timeDuration);
		updateVertVelocity(timeDuration);
		counter(timeDuration);
		updateLastDirection();
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
	private static boolean isValidTimeInterval(double timeDuration){
		return (timeDuration >= 0 && timeDuration <= 0.2);
	}
	
	/**
	 * A method to update the horizontal position over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new horizontal position.
	 * @effect	The new effective x position is set to a new value based on the 
	 * 			time interval and the current attributes of this Mazub.
	 */
	@Model
	private void updateHorPosition(double timeDuration){
		double newXPos = getEffectiveXPos() + getHorDirection()*
				(getHorVelocity()*timeDuration+ 0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		setEffectiveXPos(newXPos);
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
	private void updateHorVelocity(double timeDuration){
		double newVel = getHorVelocity() + getHorAcceleration() * timeDuration;
		if (newVel > getMaxHorVelocity()){
			setHorVelocity(getMaxHorVelocity());
			setHorAcceleration(0);
		}
		else
			setHorVelocity(newVel);
	}
	
	/**
	 * A method to update the vertical position over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new vertical position.
	 * @effect	The new effective y position is set to a new value based on the 
	 * 			time interval and the current attributes of this Mazub.
	 */
	@Model
	private void updateVertPosition(double timeDuration)
			throws IllegalYPositionException{
		double newYPos = getEffectiveYPos() + 
				((getVertDirection()*getVertVelocity()*timeDuration)+ 
				0.5*getVertAcceleration()*Math.pow(timeDuration, 2))*100;
		if (newYPos<0)
			setEffectiveYPos(0);
		else
			setEffectiveYPos(newYPos);
	}
	
	/**
	 * A method to update the vertical velocity over a given time interval.
	 * 
	 * @param 	timeDuration
	 * 			The time interval needed to calculate the new vertical velocity.
	 * @effect	The new vertical velocity is set to a new value based on the 
	 * 			time interval and the current attributes of this Mazub.
	 */
	private void updateVertVelocity(double timeDuration){
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
	
	/**
	 * Return the stored period of elapsed time .
	 */
	@Basic @Model
	private double getTimeSum() {
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
	private void setTimeSum(double timeSum) {
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
	private void counter(double timeDuration){
		setTimeSum(getTimeSum()+timeDuration);
	}
	
	/**
	 * A variable storing a period of elapsed time. This variable 
	 * functions as a timer that increments subsequent time intervals
	 * in the method advanceTime.
	 */
	private double timeSum;
	
	/**
	 * Return the last registered horizontal direction of the Mazub.
	 */
	@Basic @Model
	private int getLastDirection() {
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
	private void setLastDirection(int lastDirection) {
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
		if (getHorDirection() != 0)
			setLastDirection(getHorDirection());
	}
	
	/**
	 * A variable storing the last horizontal direction of movement of this Mazub
	 * within the last second of in-game-time.
	 */
	private int lastDirection;

	
	/**
	 * Return the index of the current sprite in the array of sprites,
	 * belonging to this Mazub.
	 */
	@Basic @Model
	private int getIndex() {
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
	private boolean isValidIndex(int index){
		return (index >= 0 || index < getNumberOfWalkingSprites()*2+10);
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
	private void setIndex(int index) {
		assert isValidIndex(index);
		this.index = index;
	}
	
	/**
	 * A variable storing the index of the current sprite.
	 * The index is an integer number and refers to the position
	 * of the current sprite in the array of sprites, belonging to this Mazub.
	 */
	private int index;
	
	/**
	 * Checks whether the Mazub is moving.
	 * 
	 * @return	True if and only if the Mazub is moving to the left or to the right, which is
	 * 			equivalent to if the horizontal direction is not zero (1 or -1).
	 * 			| result == (getHorDirection() != 0)
	 */
	private boolean isMoving(){
		return (getHorDirection() != 0);
	}

	/**
	 * Checks whether the Mazub is moving to the right.
	 * 
	 * @return	True if and only if the horizontal direction of the Mazub is 1.
	 * 			| result == (getHorDirection() == 1)
	 */
	@Model
	private boolean isMovingRight(){
		return (getHorDirection() == 1);
	}

	/**
	 * Checks whether the Mazub is moving to the left.
	 * 
	 * @return	True if and only if the horizontal direction of the Mazub is -1.
	 * 			| result == (getHorDirection() == -1)
	 */
	@Model
	private boolean isMovingLeft(){
		return (getHorDirection() == -1);
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
		if (getLastDirection() != 0){
			if (getTimeSum() < 1.0)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the Mazub has moved left within the last second of in-game-time.
	 * 
	 * @return	True if and only if this Mazub was moving within the last second
	 * 			of in-game-time and its last direction was -1 (left).
	 * 			| result == (wasMoving() && (getLastDirection() == -1))
	 */
	private boolean wasMovingLeft(){
		return (wasMoving() && (getLastDirection() == -1));
	}
	
	/**
	 * Checks whether the Mazub has moved left within the last second of in-game-time.
	 * 
	 * @return	True if and only if this Mazub was moving within the last second
	 * 			of in-game-time and its last direction was 1 (right).
	 * 			| result == (wasMoving() && (getLastDirection() == 1))
	 */
	private boolean wasMovingRight(){
		return (wasMoving() && (getLastDirection() == 1));
	}
	
	/**
	 * A method to check whether this Mazub is jumping. 
	 * 
	 * @return	True if and only if the current vertical direction
	 * 			differs from zero.
	 * 			| result == (getVertDirection() != 0)
	 */
	private boolean isJumping(){
		return (getVertDirection() != 0);
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
	public void updateSpriteIndex(){
		if (getIsDucked()){
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
						updateWalkingAnimationRight();
				}
				else{
					//isMovingLeft() == true
					//9+m..9+2m
					updateWalkingAnimationLeft();
				}
		}
		else if (wasMovingRight())
				setIndex(2);
		else if (wasMovingLeft())
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
	private Sprite[] getAllSprites(){
		return this.sprites;
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
	 * A variable storing all possible sprites for this character.
	 * The sprites are images. Each sprite represents 
	 * a different state of this Mazub.
	 */
	private final Sprite[] sprites;
			
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
}
