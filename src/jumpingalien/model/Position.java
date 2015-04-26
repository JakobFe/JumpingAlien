package jumpingalien.model;

import jumpingalien.model.exceptions.IllegalXPositionException;
import jumpingalien.model.exceptions.IllegalYPositionException;
import be.kuleuven.cs.som.annotate.*;
import static jumpingalien.tests.util.TestUtils.*;

@Value
public class Position {
	
	/**
	 * Initialize this position with a given x and y position and a given world.
	 * 
	 * @param 	x
	 * 			The x position for this new position.
	 * @param 	y
	 * 			The y position for this new position.	
	 * @param 	world
	 * 			The world belonging to this new position.
	 * @post	...
	 * 			| new.getXPosition() == x
	 * @post	...
	 * 			| new.getYPosition() == y
	 * @effect	...
	 * 			| setWorld(world)
	 * @throws 	IllegalXPositionException
	 * 			...
	 * 			| isValidXPosition(x,world) 
	 * @throws 	IllegalYPositionException
	 * 			...
	 * 			| isValidYPosition(y,world)
	 */
	public Position(double x, double y,World world) throws 
	IllegalXPositionException,IllegalYPositionException{
		setWorld(world);
		setXPosition(x);
		setYPosition(y);
	}
	
	/**
	 * Initialize this position with a given x and y position and a no world.
	 * @param 	x
	 * 			The x position for this new position.
	 * @param 	y
	 * 			The y position for this new position.	
	 * @effect	...
	 * 			| this(x,y,null)
	 */
	public Position(double x, double y) throws
	IllegalXPositionException,IllegalYPositionException{
		this(x,y,null);
	}
	
	
	/**
	 * Return the x component of this position.
	 */
	@Basic@Immutable
	public double getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * Return the displayed x component of this position.
	 */
	public int getDisplayedXPosition(){
		return (int) Math.floor(getXPosition());
	}
	
	/**
	 * Check whether the given x is a valid effective x position.
	 * 
	 * @param 	x
	 * 			Effective x position to check
	 * @param	world
	 * 			The world to check against.
	 * @return 	...
	 * 			| if(world == null)
	 * 			|	then result == true
	 * 			| else
	 * 			|	result == ((x >= 0) && ((int) Math.floor(x) < (world.getWorldWidth())))
	 */
	@Model
	private static boolean isValidXPosition(double x, World world){
		if (world == null)
			return true;
		else
			return ((x >= 0) && ((int) Math.floor(x) < (world.getWorldWidth())));
	}
	
	/**
	 * Set the effective x-position to the given position.
	 * 
	 * @param	x
	 * 			The new effective x position.
	 * @post	...
	 * 			| new.getEffectiveXPos() == x
	 * @throws	IllegalXPositionException((int) Math.floor(x),this)
	 * 			...
	 * 			| !isValidXPos(x,getWorld())
	 */
	@Model
	private void setXPosition(double x) throws IllegalXPositionException{
		if (!isValidXPosition(x,getWorld()))
			throw new IllegalXPositionException((int) Math.floor(x));
		this.xPosition = x;
	}
	
	/**
	 * A variable storing the x component of this position.
	 */
	private double xPosition;
	
	/**
	 * Return the y component of this position.
	 */
	@Basic@Immutable
	public double getYPosition(){
		return this.yPosition;
	}
	
	/**
	 * Return the displayed y component of this position.
	 */
	public int getDisplayedYPosition(){
		return (int) Math.floor(getYPosition());
	}
	
	/**
	 * Check whether the given y is a valid y position.
	 * 
	 * @param 	y
	 * 			y position to check.
	 * @param	world
	 * 			The world to check against.
	 * @return 	...
	 * 			| if(world == null)
	 * 			|	then result == true
	 * 			| else
	 * 			|	result == (((y >= 0) && ((int) Math.floor(y) < world.getWorldHeight())))
	 */
	@Model
	private static boolean isValidYPosition(double y, World world){
		if (world == null)
			return true;
		else
			return (((y >= 0) && ((int) Math.floor(y) < world.getWorldHeight())));
	}
	
	/**
	 * Set the y-position to the given position.
	 * 
	 * @param	y
	 * 			The new y position.
	 * @post	The new y component of this position is equal to the given y position.
	 * 			| new.getYPosition() == y
	 * @throws	IllegalYPositionException((int) Math.floor(y))
	 * 			| !isValidYPosition(y,getWorld())
	 */
	@Model
	private void setYPosition(double y) throws IllegalYPositionException{
		if (!isValidYPosition(y,getWorld()))
			throw new IllegalYPositionException((int) Math.floor(y));
		this.yPosition = y;
	}
	
	/**
	 * Variable storing the y component of this position.
	 */
	private double yPosition = 0;	
	
	/**
	 * A method to format this position into a double array.
	 * 
	 * @return	...
	 * 			| result.length == 2
	 * @return	...
	 * 			| result[0] == getXPosition()
	 * 			| result[1] == getYPosition()
	 */
	public double[] toDoubleArray(){
		return doubleArray(this.getXPosition(),this.getYPosition());
	}
	
	/**
	 * Return the world belonging to this position.
	 */
	World getWorld(){
		return this.world;
	}
	
	/**
	 * Sets the world of this position to the given world.
	 * 
	 * @param 	world
	 * 			The world to set.
	 * @post	...
	 * 			| new.getWorld() == world
	 */
	private void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * A variable storing the world belonging to this position.
	 */
	private World world;
	
	/**
	 * A method to terminate this position.
	 * 
	 * @post	...
	 * 			| new.getWorld() == null
	 */
	public void terminate(){
		this.world = null;
	}
	
	/**
	 * Return a copy of this position.
	 */
	public Position copy(){
		return new Position(getXPosition(),getYPosition(),getWorld());
	}
	
	/**
	 * A method to check whether two positions are the same.
	 * 
	 * @return	...
	 * 			| if(other == null || !(other instanceof Position))
	 * 			|	then result == false
	 * 			| else
	 * 			| 	result ==
	 * 			|		((this.getXPosition() == ((Position)other).getXPosition()) &&
	 *			|  		(this.getYPosition() == ((Position)other).getYPosition()) &&
	 *			|  		(this.getWorld() == ((Position)other).getWorld()))
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null || !(other instanceof Position))
			return false;
		else
			return((this.getXPosition() == ((Position)other).getXPosition()) &&
				   (this.getYPosition() == ((Position)other).getYPosition()) &&
				   (this.getWorld() == ((Position)other).getWorld()));
	}
	
	/**
	 * Return the hash code for this position.
	 * 
	 * @return	...
	 * 			| result == (int)Math.floor(getXPosition()+getYPosition())
	 */
	@Override
	public int hashCode() {
		return (int)Math.floor(getXPosition()+getYPosition());
	}
	
	/**
	 * Return a textual representation for this position.
	 * 
	 * @return	...
	 * 			| result.contains("x: ")
	 * @return	...
	 * 			| result.contains(String.valueOf(getDisplayedXPosition()))
	 * @return	...
	 * 			| result.contains("y: ")
	 * @return	...
	 * 			| result.contains(String.valueOf(getDisplayedYPosition()))
	 */
	@Override
	public String toString(){
		return "x: " + String.valueOf(getDisplayedXPosition()) + ", y: " + 
				String.valueOf(getDisplayedYPosition());
	}
}
