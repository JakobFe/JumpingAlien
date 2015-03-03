package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalHeightException extends RuntimeException {
	/**
     * Initialize this new illegal x-coordinate exception with a given value.
     * 
     * @param   height
     *          The value for the new illegal x-coordinate exception.
     * @post    The value of the new illegal x-coordinate exception is set
     *          to the given value.
     *          | new.getValue() == value
     */
	public IllegalHeightException(int height, Mazub character){
		this.height = height;
		this.character = character;
	}
	
	/**
	 * Return the x-coordinate for this illegal x position exception.
	 */
	@Basic@Immutable
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * A variable registering the x position involved in this 
	 * illegal x position exception.
	 */
	private final int height;
	
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
	private final Mazub character;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200303L;
}
