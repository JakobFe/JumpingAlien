package jumpingalien.model.program;

import jumpingalien.model.game.Mazub;
import jumpingalien.part3.programs.SourceLocation;

public class isJumping extends UnaryOperator {

	protected isJumping(SourceLocation sourceLocation, Mazub operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isjumping";
	}

	@Override
	public Boolean outcome() {
		return ((Mazub) getOperand()).isJumping();
	}

}
