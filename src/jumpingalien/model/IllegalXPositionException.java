package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal x-coordinates for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class IllegalXPositionException extends RuntimeException {
	/**
     * Initialize this new illegal x-coordinate exception with a given value.
     * 
     * @param   xPosition
     *          The value for the new illegal x-coordinate exception.
     * @post    The value of the new illegal x-coordinate exception is set
     *          to the given value.
     *          | new.getValue() == value
     */
	public IllegalXPositionException(int xPosition, Mazub character){
		this.xPosition = xPosition;
		this.character = character;
	}
	
	public IllegalXPositionException(int xPosition){
		this.xPosition = xPosition;
	}
	
	/**
	 * Return the x-coordinate for this illegal x position exception.
	 */
	@Basic@Immutable
	public int getXPosition(){
		return this.xPosition;
	}
	
	/**
	 * A variable registering the x position involved in this 
	 * illegal x position exception.
	 */
	private final int xPosition;
	
	/**
	 * Return the Mazub character for this illegal x position exception.
	 */
	@Basic@Immutable
	public Mazub getCharacter(){
		return this.character;
	}
	
	/**
	 * A variable registering the Mazub character involved in this 
	 * illegal x position exception.
	 */
	private Mazub character = null;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200300L;
}
