package jumpingalien.model.exceptions;

import jumpingalien.model.game.Direction;


/**
 * A class for signaling illegal x-coordinates for Mazub characters.
 * @author	Jakob Festraets, Vincent Kemps
 * @version	1.0
 */
public class CollisionException extends RuntimeException {
	
	public CollisionException(Direction direction){
		this.direction = direction;
	}
	
	public CollisionException(){
		this(null);
	}
	
	public Direction getDirection() {
		return direction;
	}

	private final Direction direction;
	
	/**
	 * A variable to explicitly define a version number for this class
	 * that implements the interface Serializable.
	 */
	private static final long serialVersionUID = 100200306L;
}
