package jumpingalien.model.program.expressions.unaryexpression;

import jumpingalien.model.game.Alien;
import jumpingalien.model.program.expressions.Expression;
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
		return ((Alien) getOperand().outcome()).isJumping();
	}

}
