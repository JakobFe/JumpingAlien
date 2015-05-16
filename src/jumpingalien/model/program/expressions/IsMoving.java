package jumpingalien.model.program.expressions;

import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends UnaryOperator {

	public IsMoving(SourceLocation sourceLocation, Expression operand,
			Expression direction) {
		super(sourceLocation, operand);
		this.direction = 
		(Variable)direction;
	}

	public Variable getDirection() {
		return direction;
	}
	
	private final Variable direction;
	
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
