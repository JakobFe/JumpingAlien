package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal ducking invokes for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class IllegalDuckInvokeException extends RuntimeException {
	
	public IllegalDuckInvokeException(Mazub character){
		this.character = character;
	}

	/**
	 * Return the Mazub character for this illegal ducking invoke exception.
	 */
	@Basic@Immutable
	public Mazub getCharacter(){
		return this.character;
	}
	
	/**
	 * A variable registering the Mazub character involved in this 
	 * illegal ducking invoke exception.
	 */
	private final Mazub character;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200306L;
}
