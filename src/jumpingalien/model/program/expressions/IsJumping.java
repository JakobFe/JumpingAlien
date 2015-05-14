package jumpingalien.model.program.expressions;

import jumpingalien.model.game.Mazub;
import jumpingalien.part3.programs.SourceLocation;

public class IsJumping extends UnaryOperator {

	public IsJumping(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isjumping";
	}

	@Override
	public Boolean outcome() {
		return ((Mazub) getOperand().outcome()).isJumping();
	}

}