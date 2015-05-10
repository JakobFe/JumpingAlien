package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends UnaryOperator {

	@SuppressWarnings("unchecked")
	public IsMoving(SourceLocation sourceLocation, Expression operand,
			Expression direction) {
		super(sourceLocation, operand);
		this.direction = 
		(Constant<jumpingalien.part3.programs.IProgramFactory.Direction>)direction;
	}

	public Constant<jumpingalien.part3.programs.IProgramFactory.Direction> getDirection() {
		return direction;
	}
	
	private final Constant<jumpingalien.part3.programs.IProgramFactory.Direction>  direction;
	
	@Override
	public String getOperatorSymbol() {
		return null;
	}

	@Override
	public Boolean outcome() {
		jumpingalien.model.game.Direction theDirection =
				convertDirection(getDirection());
		return ((GameObject) getOperand().outcome()).isMoving(theDirection);
	}
	
	
	public jumpingalien.model.game.Direction convertDirection(
			Constant<jumpingalien.part3.programs.IProgramFactory.Direction> dir)
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
