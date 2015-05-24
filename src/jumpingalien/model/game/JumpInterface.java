package jumpingalien.model.game;

/**
 * An interface that allows game objects to jump.
 * 
 * @author 	Jakob Festraets, Vincent Kemps
 * @version	1.0 
 * 
 */
public interface JumpInterface {
	
	/**
	 * A method to start jumping.
	 */
	void startJump();
	
	/**
	 * A method to end jumping.
	 */
	void endJump();
	
	/**
	 * Returns whether or not this object is jumping.
	 */
	boolean isJumping();
}
