package jumpingalien.model.program.expressions;

import jumpingalien.model.game.Mazub;
import jumpingalien.part3.programs.SourceLocation;

public class IsDucking extends UnaryOperator {

	public IsDucking(SourceLocation sourceLocation, Expression operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isducking";
	}

	@Override
	public Boolean outcome() {
		return ((Mazub) getOperand().outcome()).getIsDucked();
	}

}
