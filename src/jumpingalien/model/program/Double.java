package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Double extends Expression {
	
	public Double(double value){
		this.value = value;
	}
	
	private final double value;
	
	
	protected Double(SourceLocation sourceLocation) {
		super(sourceLocation);
		value = 0;
	}

	@Override
	public Double outcome() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public double getValue(){
		return value;
	}
	

}
