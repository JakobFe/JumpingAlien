package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

@Value
public class Position {
	
	public Position(double x, double y,World world) throws 
	IllegalXPositionException,IllegalYPositionException{
		setWorld(world);
		setXPosition(x);
		setYPosition(y);
	}
	
	
	public Position(double x, double y) throws
	IllegalXPositionException,IllegalYPositionException{
		this(x,y,null);
	}
	
	
	/**
	 * Return the effective x-position of this Mazub.
	 */
	@Basic@Immutable@Model
	public double getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Return the displayed x position of this Mazub.
	 */
	public int getDisplayedXPosition(){
		return (int) Math.floor(getXPosition());
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
	private boolean isValidXPosition(double x){
		if (getWorld() == null)
			return ((x >= 0) && ((int) Math.floor(x) < getScreenWidth()));
		else
			return ((x >= 0) && ((int) Math.floor(x) < 
					(getWorld().getWorldWidth())-getWorld().getMazub().getWidth()));
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
	public void setXPosition(double x) throws IllegalXPositionException{
		if (!isValidXPosition(x))
			throw new IllegalXPositionException((int) Math.floor(x));
		this.xPosition = x;
	}
	
	private double xPosition;
	
	/**
	 * Return the effective y-position of this Mazub.
	 */
	@Basic@Immutable@Model
	public double getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Return the y-position of this Mazub.
	 */
	public int getDisplayedYPosition(){
		return (int) Math.floor(getYPosition());
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
	private boolean isValidYPosition(double y){
		if (getWorld() == null)
			return (((y >= 0) && ((int) Math.floor(y) < getScreenHeight())));
		else
			return (((y >= 0) && ((int) Math.floor(y) < getWorld().getWorldHeight())));
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
	public void setYPosition(double y) throws IllegalYPositionException{
		if (!isValidYPosition(y))
			throw new IllegalYPositionException((int) Math.floor(y));
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
	private double yPosition = 0;	
	
		
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
	
	private World getWorld(){
		return this.world;
	}
	
	private void setWorld(World world){
		// nog precondities opleggen
		this.world = world;
	}
	
	private World world;
	
	public void terminate(){
		this.world = null;
	}
}
