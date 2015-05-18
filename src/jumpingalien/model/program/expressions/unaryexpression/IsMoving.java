package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends UnaryOperator {

	public IsMoving(SourceLocation sourceLocation, Expression operand,
			Expression direction) {
		super(sourceLocation, operand);
		this.direction = direction;
	}

	public Expression getDirection() {
		return direction;
	}
	
	private final Expression direction;
	
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
