import be.kuleuven.cs.som.annotate.*;


public class Mazub {
	
	/**
	 * Initialize this new Mazub with given x-coordinate, given y-coordinate, given width
	 * and given heigth.
	 * @param 	x
	 * 		  	initial x-coordinate for this Mazub.
	 * @param 	y
	 * 			initial y-coordinate for this Mazub.
	 * @param 	width
	 * 			initial width for this Mazub.
	 * @param 	height
	 * 			initial height for this Mazub.
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
	public Mazub(int x, int y, int width, int height) throws IllegalXPositionException,
	IllegalYPositionException,IllegalWidthException,IllegalHeightException {
		setXPosition(x);
		setYPosition(y);
		setWidth(width);
		setHeight(height);
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
	 * 			| result == (x >= 0) && (x < 1024)
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
}
