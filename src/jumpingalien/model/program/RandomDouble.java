package jumpingalien.model.program;

import java.util.Random;

import jumpingalien.part3.programs.SourceLocation;

public class RandomDouble extends UnaryOperator {

	protected RandomDouble(SourceLocation sourceLocation, Double operand) {
		super(sourceLocation, operand);
	}

	@Override
	public String getOperatorSymbol() {
		return "random";
	}

	@Override
	public Double outcome() {
		Random rn = new Random();
		return rn.nextDouble()*(Double)getOperand();
	}

}
