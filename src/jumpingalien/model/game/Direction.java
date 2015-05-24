package jumpingalien.model.game;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.model.program.expressions.Expression;

/**
 * An enumeration involving directions.
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
	 * @post	...
	 * 			| new.getFactor() == factor
	 */
	private Direction(int factor){
		this.factor = factor;
	}
	
	/**
	 * Returns the factor associated with this direction.
	 */
	@Basic
	public int getFactor() {
		return factor;
	}
	
	/**
	 * A variable storing the factor belonging to this direction.
	 */
	private final int factor;
	
	/**
	 * A method to convert an  expression to a direction of this enumeration. 
	 * This method is only useful when an expression is provided that has as outcome
	 * a direction of the enumeration in IProgramFactory. IProgramFactory is part of
	 * the source code of part 3 and is used in the creation of programs.
	 * 
	 * @param 	dir
	 * 			The expression to convert.
	 * @return	The direction of this enumeration with the same name as
	 * 			the enumeration in IProgramFactory.
	 * @throws 	IllegalArgumentException
	 * 			The given expression doesn't evaluate to a direction
	 * 			from the enumeration in IProgramFactory.
	 */
	public static jumpingalien.model.game.Direction convertDirection(
			Expression dir)
			throws IllegalArgumentException{
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
