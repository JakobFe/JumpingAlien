package jumpingalien.model.exceptions;
import jumpingalien.model.gameobjects.GameObject;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal jumping invokes for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class IllegalTimeIntervalException extends RuntimeException {
	
	public IllegalTimeIntervalException(GameObject character){
		this.character = character;
	}

	/**
	 * Return the Mazub character for this illegal time interval exception.
	 */
	@Basic@Immutable
	public GameObject getCharacter(){
		return this.character;
	}
	
	/**
	 * A variable registering the Mazub character involved in this 
	 * illegal time interval exception.
	 */
	private final GameObject character;

	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200306L;
}
