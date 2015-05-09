package jumpingalien.model.program;

import jumpingalien.model.game.Direction;
import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends UnaryOperator {

	protected IsMoving(SourceLocation sourceLocation, Expression operand,Expression direction) {
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
		return ((GameObject) getOperand().outcome()).isMoving(getDirection());
	}

}
