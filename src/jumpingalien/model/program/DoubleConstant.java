package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class DoubleConstant extends Expression {

	public DoubleConstant(SourceLocation sourceLocation, double value){
		super(sourceLocation);
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	private final double value;
	
	@Override
	public Double outcome() {
		return getValue();
	}

}
