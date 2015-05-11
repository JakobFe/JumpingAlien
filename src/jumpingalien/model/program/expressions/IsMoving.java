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
				jumpingalien.model.game.Direction.convertDirection(getDirection());
		return ((GameObject) getOperand().outcome()).isMoving(theDirection);
	}
}
