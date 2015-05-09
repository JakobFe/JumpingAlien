package jumpingalien.model.program;

import jumpingalien.model.game.Mazub;
import jumpingalien.part3.programs.SourceLocation;

public class isDucking extends UnaryOperator {

	protected isDucking(SourceLocation sourceLocation, Mazub operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "isducking";
	}

	@Override
	public Boolean outcome() {
		return ((Mazub) getOperand()).getIsDucked();
	}

}
