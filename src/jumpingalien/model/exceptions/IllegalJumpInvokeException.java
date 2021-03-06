package jumpingalien.model.exceptions;
import jumpingalien.model.game.Alien;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal jumping invokes for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class IllegalJumpInvokeException extends RuntimeException {
	
	public IllegalJumpInvokeException(Alien character){
		this.character = character;
	}

	/**
	 * Return the Mazub character for this illegal jumping invoke exception.
	 */
	@Basic@Immutable
	public Alien getCharacter(){
		return this.character;
	}
	
	/**
	 * A variable registering the Mazub character involved in this 
	 * illegal jumping invoke exception.
	 */
	private final Alien character;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200305L;
}
