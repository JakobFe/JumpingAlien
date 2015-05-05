package jumpingalien.model.game;

/**
 * An enumeration involving direction.
 * 
 * @author Jakob Festraets, Vincent Kemps
 * @version	1.0
 *
 */
public enum Direction {
	
	RIGHT(1), LEFT(-1), UP(1), DOWN(-1), NULL(0);
	
	/**
	 * Initialize this direction with a given factor.
	 * 
	 * @param 	factor
	 * 			The factor for this direction.
	 */
	private Direction(int factor){
		this.factor = factor;
	}
	
	/**
	 * Returns the factor associated with this direction.
	 */
	public int getFactor() {
		return factor;
	}
	
	/**
	 * A variable storing the factor belonging to this direction.
	 */
	private final int factor;

}
