import be.kuleuven.cs.som.annotate.*;

/**
 * A class that implements the player character with the ability to jump, duck and
 * run to the left and to the right. This class is a part of the project JumpingAlien.
 * 
 * @invar 	The initial horizontal velocity of the Mazub must be valid.
 * 			| isValidInitHorVelocity(getInitHorVelocity())
 * @invar 	The maximum horizontal velocity of the Mazub must be valid.
 * 			| isValidMaxHorVelocity(getMaxHorVelocity())
 * @invar 	The actual velocity of the Mazub must be valid.
 * 			| isValidVelocity(getVelocity()) 
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
	 * @effect	This Mazub is initialized with the given x as its x-coordinate.
	 * 			| new.xPosition = setXPosition(x)
	 * @effect	This Mazub is initialized with the given y as its y-coordinate.
	 * 			| new.yPosition = setYPosition(y)
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
	public Mazub(int x, int y, int width, int height, double initHorVelocity, double maxHorVelocity) throws IllegalXPositionException,
	IllegalYPositionException,IllegalWidthException,IllegalHeightException {
		setXPosition(x);
		setYPosition(y);
		setWidth(width);
		setHeight(height);
		this.initHorVelocity = initHorVelocity;
		this.maxHorVelocity = maxHorVelocity;
		this.horVelocity = 0;
		this.horAcceleration = 0;
	}
	
	/**
	 * Return the x-position of this Mazub.
	 */
	public int getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Check whether the given x is a valid x-coordinate.
	 * @param 	x
	 * 			x-coordinate to check
	 * @return 	True if and only if x is not negative and smaller than 1024.
	 * 			| result == ((x >= 0) && (x < 1024))
	 */
	@Basic
	public static boolean isValidXPosition(int x){
		return ((x >= 0) && (x < 1024));
	}
	
	/**
	 * Set the x-position to the given position.
	 * @param	x
	 * 			The new x-coordinate.
	 * @post	The new x-coordinate of this Mazub is equal to the given x-coordinate.
	 * 			| new.getXCoordinate() == x
	 * @throws	IllegalXPositionException(x,this)
	 * 			The given x-coordinate is not a valid x-coordinate.
	 * 			| !isValidXPosition(x)
	 */
	public void setXPosition(int x) throws IllegalXPositionException{
		if (!isValidXPosition(x))
			throw new IllegalXPositionException(x,this);
		this.xPosition = x;
	}
	
	/**
	 * Variable storing x-coordinate.
	 */
	private int xPosition;
	
	/**
	 * Return the y-position of this Mazub.
	 */
	public int getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Check whether the given y is a valid y-coordinate.
	 * @param 	y
	 * 			y-coordinate to check
	 * @return 	True if and only if y is not negative and smaller than 768.
	 * 			| result == (y >= 0) && (y < 768)
	 */
	@Basic
	public static boolean isValidYPosition(int y){
		return ((y >= 0) && (y < 768));
	}
	
	/**
	 * Set the y-position to the given position.
	 * @param	y
	 * 			The new y-coordinate.
	 * @post	The new y-coordinate of this Mazub is equal to the given y-coordinate.
	 * 			| new.getYCoordinate() == y
	 * @throws	IllegalXPositionException(y,this)
	 * 			The given y-coordinate is not a valid y-coordinate.
	 * 			| !isValidYPosition(y)
	 */
	public void setYPosition(int y) throws IllegalYPositionException{
		if (!isValidYPosition(y))
			throw new IllegalYPositionException(y,this);
		this.yPosition = y;
	}
	
	/**
	 * Variable storing y-coordinate.
	 */
	private int yPosition;
	
	/**
	 * Return the width of this Mazub.
	 */
	@Basic
	public int getWidth(){
		return this.width;
	}
	
	/**
	 * Check whether the given width is a valid width.
	 * @param 	width
	 * 			width to check
	 * @return 	True if and only if width is not negative.
	 * 			| result == (width > 0)
	 */
	public static boolean isValidWidth(){
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
	public int getHeigth(){
		return this.height;
	}
	
	/**
	 * Check whether the given height is a valid height.
	 * @param 	height
	 * 			height to check
	 * @return 	True if and only if height is not negative.
	 * 			| result == (height > 0)
	 */
	public static boolean isValidHeight(){
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
	 * Variable storing the height of this Mazub.
	 */
	private int height;
	
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
	 * @return	True if the given initial horizontal velocity is above 1.
	 * 			| result == (initHorVelocity > 1)
	 */
	public static boolean isValidInitHorVelocity(double initHorVelocity){
		return (initHorVelocity > 1);
	}
	
	/**
	 * Return the maximum horizontal velocity of this Mazub.
 	 */
	@Basic @Immutable
	public double getMaxHorVelocity(){
		return this.maxHorVelocity;
	}
	
	/**
	 * Checks whether the given maximum horizontal velocity is valid.
	 * @param	maxHorVelocity
	 * 			The maximum horizontal velocity to check.
	 * @return	True if the given maximum horizontal velocity is above 
	 * 			the initial horizontal velocity of the Mazub.
	 * 			| result == (maxHorVelocity > getInitHorVelocity())
	 */
	public boolean isValidMaxHorVelocity(double maxHorVelocity){
		return (maxHorVelocity > this.getInitHorVelocity());
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
	 * @return	True if the given maximum horizontal velocity is above 
	 * 			the initial horizontal velocity of the Mazub and below
	 * 			the maximum horizontal velocity of the Mazub.
	 * 			| result == (getHorVelocity() > getInitHorVelocity()) &&
	 * 			|				(getHorVelocity() < getMaxHorVelocity())
	 */
	public boolean isValidHorVelocity(double horVelocity){
		return (horVelocity > this.getInitHorVelocity());
	}
	
	/**
	 * Sets the horizontal velocity to a given value.
	 * @param 	horVelocity
	 * 			The new velocity for the Mazub.
	 * @pre		The new velocity must be a valid velocity.
	 * 			|isValidVelocity(getVelocity())
	 * @post	The new velocity of this Mazub is equal to the given velocity.
	 * 			| new.getVelocity() == velocity
	 */
	public void setHorVelocity(double horVelocity) {
		assert isValidVelocity(horVelocity);
		this.velocity = horVelocity;
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
		if isValidHorAcceleration(horAcceleration)
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
		return ((horAcceleration == 0) || (horAcceleration == getMaxHorAcceleration()))
	}
	
	/**
	 * Method to start the movement of the Mazub to the left.
	 */
	public void startMoveLeft(){
		setHorVelocity(getInitHorVelocity());
		
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the left.
	 */
	public void endMoveLeft(){
		setHorVelocity(0);
	}
	
	/**
	 * Method to start the movement of the Mazub to the right.
	 */
	public void startMoveRight(){
		setHorVelocity(getInitHorVelocity());
	}
	
	/**
	 * Method to end the movement of the Mazub when moving to the right.
	 */
	public void endMoveRight(){
		setHorVelocity(0);
	}
	
	private final double initHorVelocity;
	private final double maxHorVelocity;
	private static final double maxHorAcceleration = 0.9;
	private double horVelocity;
	private double horAcceleration;
	
}
