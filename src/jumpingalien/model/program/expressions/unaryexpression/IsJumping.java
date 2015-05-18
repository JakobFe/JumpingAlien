package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.Mazub;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.expressions.UnaryOperator;
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
