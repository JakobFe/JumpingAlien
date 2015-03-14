package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal y-coordinates for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class IllegalYPositionException extends RuntimeException {
	/**
     * Initialize this new illegal y-coordinate exception with a given value.
     * 
     * @param   yPosition
     *          The value for the new illegal y-coordinate exception.
     * @post    The value of the new illegal y-coordinate exception is set
     *          to the given value.
     *          | new.getValue() == value
     */
	public IllegalYPositionException(int yPosition, Mazub character){
		this.yPosition = yPosition;
		this.character = character;
	}
	
	/**
	 * Return the y-coordinate for this illegal y position exception.
	 */
	@Basic@Immutable
	public double getyPosition(){
		return this.yPosition;
	}
	
	/**
	 * A variable registering the y position involved in this 
	 * illegal y position exception.
	 */
	private final double yPosition;
	
	/**
	 * Return the Mazub character for this illegal y position exception.
	 */
	@Basic@Immutable
	public Mazub getCharacter(){
		return this.character;
	}
	
	/**
	 * A variable registering the Mazub character involved in this 
	 * illegal y position exception.
	 */
	private final Mazub character;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200301L;
}
