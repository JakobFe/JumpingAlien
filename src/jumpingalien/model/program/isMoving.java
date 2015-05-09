package jumpingalien.model.program;

import jumpingalien.model.game.Direction;
import jumpingalien.model.game.GameObject;
import jumpingalien.part3.programs.SourceLocation;

public class isMoving extends UnaryOperator {

	protected isMoving(SourceLocation sourceLocation, GameObject operand,Direction direction) {
		super(sourceLocation, operand);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
	
	private final Direction direction;
	
	@Override
	public String getOperatorSymbol() {
		return null;
	}

	@Override
	public Boolean outcome() {
		return ((GameObject) getOperand()).isMoving(getDirection());
	}

}
