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
 * @invar	The width of this Mazub must be valid.
 * 			| isValidWidth(getWidth())
 * @invar	The height of this Mazub must be valid.
 * 			| isValidHeight(getHeight())
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
 * 
 * @note	2 methods are doing exactly the same: isValidVertDirection() and
 * 			isValidHorDirection(). Maybe we should generalize it to a method isValidDirection().
 * 			There is still redundancy in the documentation of methods concerning positions.
 * 			Maybe we should work with the dimensions of the game world instead of 1024x768
 * 
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 *
 */
public class Mazub {
	
	/**
	 * Initialize this new Mazub with given x-coordinate, given y-coordinate, given width,
	 * given heigth, given initial horizontal velocity and given maximum horizontal velocity.
	 * At the moment of initialization the velocity is zero.
	 * @param 	x
	 * 		  	initial x-coordinate for this Mazub.
	 * @param 	y
	 * 			initial y-coordinate for this Mazub.
	 * @param 	width
	 * 			initial width for this Mazub.
	 * @param 	height
	 * 			initial height for this Mazub.
	 * @param 	initHorVelocity
	 * 			initial horizontal velocity.
	 * @param 	maxHorVelocity
	 * 			maximum horizontal velocity.
	 * @pre		The initial horizontal velocity must be valid.
	 * 			| isValidInitHorVelocity(getInitHorVelocity())
	 * @effect	This Mazub is initialized with the given x as its effective x-coordinate.
	 * 			| new.xPosition = setEffectiveXPos(x)
	 * @effect	This Mazub is initialized with the given y as its effective y-coordinate.
	 * 			| new.yPosition = setEffectiveYPos(y)
	 * @effect	This Mazub is initialized with the given width as its width.
	 * 			| new.width = setWidth(width)
	 * @effect	This Mazub is initialized with the given height as its height.
	 * 			| new.height = setHeight(height)
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x-coordinate is not a valid x-coordinate.
	 * 			| !isValidXPosition(x)
	 * @throws	IllegalYPositionException(y,this)
	 * 			The given y-coordinate is not a valid y-coordinate.
	 * 			| !isValidYPosition(y)
	 * @throws	IllegalWidthException(width,this)
	 * 			The given width is not a valid width.
	 * 			| !isValidWidth(width)
	 * @throws	IllegalHeightException(height,this)
	 * 			The given height is not a valid height.
	 * 			| !isValidHeight(height)
	 */
	@Raw
	public Mazub(int x, int y, int width, int height, double initHorVelocity, double maxHorVelocity, Sprite[] sprites) throws IllegalXPositionException,
	IllegalYPositionException,IllegalWidthException,IllegalHeightException {
		setEffectiveXPos(x);
		setEffectiveYPos(y);
		setWidth(width);
		setHeight(height);
		this.initHorVelocity = initHorVelocity;
		this.maxHorVelocityRunning = maxHorVelocity;
		setMaxHorVelocity(maxHorVelocity);
		this.horVelocity = 0;
		this.horAcceleration = 0;
		this.sprites = sprites;
		this.m = (this.sprites.length - 10)/2;
	}
	
	/**
	 * Return the displayed x-position of this Mazub.
	 */
	@Basic
	public int getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Check whether the given x is a valid x position.
	 * @param 	x
	 * 			x position to check
	 * @return 	True if and only if the given x is not negative and smaller than 1024.
	 * 			| result == (x >= 0) && (x < 1024)
	 */
	public static boolean isValidXPosition(int x){
		return ((x >= 0) && (x < 1024));
	}
	
	/**
	 * Set the x-position to display to the given position.
	 * @param	x
	 * 			The new x position.
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
	 * @param	x
	 * 			The new effective x position.
	 * @post	The new effective x position of this Mazub is equal to the given x.
	 * 			| new.getEffectiveXPos() == x
	 * @effect	The displayed x position is set to the rounded effective x-coordinate.
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
	 * The effective x position refers to the x-coordinate in Cartesian coordinates.   
	 * This variable is used in methods concerning game physics.
	 */
	private double effectiveXPos;
	
	/**
	 * Return the y-position of this Mazub.
	 */
	@Basic
	public int getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Check whether the given y is a valid y position.
	 * @param 	y
	 * 			y position to check
	 * @return 	True if and only if y is not negative and smaller than 768.
	 * 			| result == (y >= 0) && (y < 768)
	 */
	public static boolean isValidYPosition(int y){
		return ((y >= 0) && (y < 768));
	}
	
	/**
	 * Set the y-position to the given position.
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
	 * @param 	y
	 * 			effective y position to check
	 * @return 	True if and only if the given y, rounded down
	 * 			to an integer number, is a valid y-position.
	 * 			| result == isValidYPosition((int) Math.floor(y))
	 */
	@Basic
	public static boolean isValidEffectiveYPos(double y){
		return isValidYPosition((int) Math.floor(y));
	}
	
	/**
	 * Set the effective y position to the given position.
	 * @param	y
	 * 			The new effective y position.
	 * @post	The new effective y position of this Mazub is equal to the given y.
	 * 			| new.getEffectiveYPos() == y
	 * @effect	The displayed y position is set to the rounded effective y position.
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
	 * The effective y position refers to the y-coordinate in Cartesian coordinates.   
	 * This variable is used in methods concerning game physics.
	 */
	private double effectiveYPos;
	
	/**
	 * Return the width of the current sprite of this Mazub.
	 */
	@Basic
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Check whether the given width is a valid width.
	 * @param 	width
	 * 			width to check
	 * @return 	True if and only if width is strictly positive.
	 * 			| result == (width > 0)
	 */
	public static boolean isValidWidth(int width){
		return (width > 0);
	}
	
	/**
	 * Set the width to the given width.
	 * @param	width
	 * 			The new width.
	 * @post	The new width of this Mazub is equal to the given width.
	 * 			| new.getWidth() == width
	 * @throws	IllegalWidthException(width,this)
	 * 			The given width is not a valid width.
	 * 			| !isValidWidth(width)
	 */
	public void setWidth(int width) throws IllegalWidthException{
		if (!isValidWidth(width))
			throw new IllegalWidthException(width,this);
		this.width = width;
	}
	
	/**
	 * Variable storing the width of this Mazub.
	 */
	private int width;
	
	/**
	 * Return the height of this Mazub.
	 */
	@Basic
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * Check whether the given height is a valid height.
	 * @param 	height
	 * 			height to check
	 * @return 	True if and only if height is strictly positive 
	 * 			and if height is smaller than 768.
	 * 			| result == ((height > 0) && (height < 768))
	 */
	public static boolean isValidHeight(int height){
		return (height > 0);
	}
	
	/**
	 * Set the height to the given height.
	 * @param	height
	 * 			The new height.
	 * @post	The new height of this Mazub is equal to the given height.
	 * 			| new.getHeight() == height
	 * @throws	IllegalHeightException(height,this)
	 * 			The given height is not a valid height.
	 * 			| !isValidHeight(height)
	 */
	public void setHeight(int height) throws IllegalHeightException{
		if (!isValidHeight(height))
			throw new IllegalHeightException(height,this);
		this.height = height;
	}
	
	/**
	 * Variable storing the height of the current sprite of this Mazub.
	 */
	private int height;
	
	/**
	 * Returns the current horizontal direction of this Mazub.
	 */
	@Basic
	public int getHorDirection() {
		return horDirection;
	}
	
	/**
	 * Checks whether the given horizontal direction is valid.
	 * @param	horDirection
	 * 			The horizontal direction to check.
	 * @return	True if and only if the given direction equals 1,-1 or 0.
	 * 			| result == (horDirection==1 || horDirection == -1 || horDirection == 0)
	 */
	public static boolean isValidHorDirection(int horDirection){
		return (horDirection==1 || horDirection == -1 || horDirection == 0);
	}
	
	/**
	 * Set the horizontal direction of this Mazub to the given horizontal direction.
	 * @param	horDirection
	 * 			Horizontal direction to set.
	 * @pre		The given direction must be a valid horizontal direction.
	 * 			| isValidHorDirection(horDirection)
	 * @post	The new horizontal direction of this Mazub is set to the given direction.
	 * 			| new.getHorDirection() == horDirection
	 */
	public void setHorDirection(int horDirection) {
		assert isValidHorDirection(horDirection);
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
	 * Checks whether the given vertical direction is valid.
	 * @param	vertDirection
	 * 			The vertical direction to check.
	 * @return	True if and only if the given direction equals 1,-1 or 0.
	 * 			| result == (vertDirection==1 || vertDirection == -1 || vertDirection == 0)
	 */
	public static boolean isValidVertDirection(int vertDirection){
		return (vertDirection==1 || vertDirection == -1 || vertDirection == 0);
	}
	
	/**
	 * Set the vertical direction of this Mazub to the given direction.
	 * @param	vertDirection
	 * 			Vertical direction to set.
	 * @pre		The given direction must be a valid vertical direction.
	 * 			| isValidVertDirection(vertDirection)
	 * @post	The new vertical direction of this Mazub is set to the given direction.
	 * 			| new.getVertDirection() == vertDirection
	 */
	public void setVertDirection(int vertDirection) {
		assert isValidVertDirection(vertDirection);
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
	 * @param	initHorVelocity
	 * @return	True if the given initial horizontal velocity is greater than
	 * 			or equal to 1.
	 * 			| result == (initHorVelocity >= 1)
	 */
	public static boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity >= 1);
	}
	
	/**
	 * Return the maximum horizontal velocity while running for this Mazub.
 	 */
	@Basic @Immutable
	public double getMaxHorVelocityRunning(){
		return this.maxHorVelocityRunning;
	}

	/**
	 * Return the maximum horizontal velocity while ducking for this Mazub.
 	 */
	@Basic @Immutable
	public double getMaxHorVelocityDucking(){
		return MAX_HOR_VELOCITY_DUCKING;
	}
	
	@Basic
	/**
	 * Return the current maximum horizontal velocity.
	 */
	public double getMaxHorVelocity(){
		return this.maxHorVelocity;
	}
	/**
	 * Checks whether the given maximum horizontal velocity is valid.
	 * @param	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @return	True if the given maximum horizontal velocity is above 
	 * 			the initial horizontal velocity of this Mazub.
	 * 			| result == (maxHorVelocity > getInitHorVelocity())
	 */
	public boolean canHaveAsMaxHorVelocity(double maxHorVelocity){
		return (maxHorVelocity >= this.getInitHorVelocity());
	}
	
	/**
	 * Sets the maximum horizontal velocity to the given value.
	 * @param 	maxHorVelocity
	 * 			The maximum horizontal velocity to set.
	 * @pre		The given value must be a valid maximum horizontal velocity.
	 * 			| canHaveAsMaxHorVelocity(maxHorVelocity)
	 * @post	The maximum horizontal velocity is set to the given value.
	 * 			| new.getMaxHorVelocity() == maxHorVelocity
	 */
	public void setMaxHorVelocity(double maxHorVelocity){
		assert canHaveAsMaxHorVelocity(maxHorVelocity);
		this.maxHorVelocity = maxHorVelocity;
	}
	
	/**
	 * Return the horizontal velocity of this Mazub.
	 */
	@Basic
	public double getHorVelocity() {
		return horVelocity;
	}
	
	/**
	 * Checks whether the given horizontal velocity is valid.
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
		return ((horVelocity == 0) ||
				((horVelocity >= this.getInitHorVelocity()) &&
				(horVelocity <= getMaxHorVelocityRunning())));
	}
	
	/**
	 * Set the horizontal velocity to a given value.
	 * @param 	horVelocity
	 * 			The new horizontal velocity for this Mazub.
	 * @pre		The new velocity must be a valid horizontal velocity.
	 * 			|isValidHorVelocity(horVelocity)
	 * @post	The new horizontal velocity of this Mazub is equal to 
	 * 			the given horizontal velocity.
	 * 			| new.getHorVelocity() == horVelocity
	 */
	public void setHorVelocity(double horVelocity) {
		assert canHaveAsHorVelocity(horVelocity);
		this.horVelocity = horVelocity;
	}
	
	/**
	 * Return the horizontal acceleration of the Mazub.
	 */
	public double getHorAcceleration() {
		return horAcceleration;
	}
	
	/**
	 * Return the maximum horizontal acceleration of the Mazub.
	 */
	public static double getMaxHorAcceleration(){
		return maxHorAcceleration;
	}
	

	/**
	 * Sets the horizontal acceleration to the given value if the given value is valid.
	 * If it isn't the horizontal acceleration will be left unchanged.
	 * @param 	horAcceleration 
	 * 			the horAcceleration to set
	 * @post	If the given value is valid, the horizontal acceleration is set to the given
	 * 			value.
	 * 			| if (isValidHorAcceleration(horAcceleration))
	 * 			|	then new.getHorAcceleration() = horAcceleration
	 * @post	If the given value is not valid, the horizontal acceleration is left unchanged.
	 * 			| if (!isValidHorAcceleration(horAcceleration))
	 * 			|	then new.getHorAcceleration() = this.getHorAcceleration()
	 */
	public void setHorAcceleration(double horAcceleration) {
		if (isValidHorAcceleration(horAcceleration))
			this.horAcceleration = horAcceleration;			
	}
	
	/**
	 * Checks whether the given horizontal acceleration is valid.
	 * @param 	horAcceleration
	 * 			horizontal acceleration to check.
	 * @return	True if and only if the given value is equal to zero
	 * 			or the maximum horizontal acceleration.
	 * 			| result == 
	 * 			|	(horAcceleration == 0) || (horAcceleration == getMaxHorAcceleration())
	 */
	public static boolean isValidHorAcceleration(double horAcceleration){
		return ((horAcceleration == 0) || (horAcceleration == getMaxHorAcceleration()));
	}
	
	/**
	 * @return the initVertVelocity
	 */
	public double getInitVertVelocity() {
		return INIT_VERT_VELOCITY;
	}

	/**
	 * @return the vertVelocity
	 */
	public double getVertVelocity() {
		return vertVelocity;
	}
	
	public static boolean isValidVertVelocity(double vertVelocity){
		return (vertVelocity >= 0); 
	}

	/**
	 * @param 	vertVelocity 
	 * 			the vertVelocity to set
	 */
	public void setVertVelocity(double vertVelocity) {
		if (isValidVertVelocity(vertVelocity))
			this.vertVelocity = vertVelocity;
	}

	/**
	 * @return the vertAcceleration
	 */
	public double getVertAcceleration() {
		return vertAcceleration;
	}

	public static boolean isValidVertAcceleration(double vertAcceleration){
		return (vertAcceleration == 0 || vertAcceleration == -10);
	}
	
	/**
	 * @param vertAcceleration the vertAcceleration to set
	 */
	public void setVertAcceleration(double vertAcceleration) {
		this.vertAcceleration = vertAcceleration;
	}

	/**
	 * @return the maxvertacceleration
	 */
	public static double getMaxVertAcceleration() {
		return MAX_VERT_ACCELERATION;
	}

	/**
	 * Method to start the movement of the Mazub to the left.
	 */
	public void startMoveLeft(){
		setHorVelocity(getInitHorVelocity());
		setHorDirection(-1);
		setHorAcceleration(getMaxHorAcceleration());
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the left.
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
	 */
	public void startMoveRight(){
		setHorVelocity(getInitHorVelocity());
		setHorDirection(1);
		setHorAcceleration(getMaxHorAcceleration());
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the right.
	 */
	public void endMoveRight(){
	if (getHorDirection() == 1){	
		setHorVelocity(0);
		setHorDirection(0);
		setHorAcceleration(0);
		}
	}
	
	public void startJump(){
		if (getVertDirection() == 0){
			setVertVelocity(getInitVertVelocity());
			setVertAcceleration(getMaxVertAcceleration());
			setVertDirection(1);
		}
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
	public boolean wasMovingLeft(){
		return false;
	}
	public boolean wasMovingRight(){
		return false;
	}
	public boolean isJumping(){
		return (getVertDirection() != 0);
	}
	
	public Sprite getCurrentSprite(){
		if (isDucking()){
			if (isMoving()){
				if (isMovingRight())
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
	 * Method to update the position and velocity of the Mazub based on the current position,
	 * velocity and a given time duration in seconds.
	 */
	public void advanceTime(double timeDuration){
		updateHorPosition(timeDuration);
		updateHorVelocity(timeDuration);
		updateVertPosition(timeDuration);
		updateVertVelocity(timeDuration);
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
	
	public void updateVertPosition(double timeDuration){
		double newYPos = getEffectiveYPos() + 
				((getVertDirection()*getVertVelocity()*timeDuration)+ 
				0.5*getHorAcceleration()*Math.pow(timeDuration, 2))*100;
		if (newYPos<0)
			setEffectiveYPos(0);
		else
			setEffectiveYPos(newYPos);
	}
	
	
	
	private final double initHorVelocity;
	private final double maxHorVelocityRunning;
	private final double MAX_HOR_VELOCITY_DUCKING = 1;
	private double maxHorVelocity;
	private static final double maxHorAcceleration = 0.9;
	private double horVelocity;
	private double horAcceleration;
	private static final double INIT_VERT_VELOCITY = 8;
	private double vertVelocity;
	private double vertAcceleration;
	private static final double MAX_VERT_ACCELERATION = -10;
	public Sprite[] sprites;
	public int index;
	public int m;
}