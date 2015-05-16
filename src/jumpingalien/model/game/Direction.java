package jumpingalien.model.game;

import jumpingalien.model.program.expressions.Variable;

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
	
	public static jumpingalien.model.game.Direction convertDirection(
			Variable dir)
			throws IllegalArgumentException{
		// TO DO
		if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.LEFT)
			return jumpingalien.model.game.Direction.LEFT;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.RIGHT)
			return jumpingalien.model.game.Direction.RIGHT;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.DOWN)
			return jumpingalien.model.game.Direction.DOWN;
		else if (dir.outcome() == jumpingalien.part3.programs.IProgramFactory.Direction.UP)
			return jumpingalien.model.game.Direction.UP;
		else
			throw new IllegalArgumentException();
	}


}
