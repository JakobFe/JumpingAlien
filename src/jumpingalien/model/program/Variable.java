package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Variable extends Expression {
	
	public Variable(SourceLocation sourceLocation, String Name, Type type){
		super(sourceLocation);
	}
	
	
	@Override
	public Object outcome() {
		return null;
	}

}
