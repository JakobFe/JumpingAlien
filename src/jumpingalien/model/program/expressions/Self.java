package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Self extends Expression {
	
	public Self(SourceLocation sourceLocation){
		super(sourceLocation);
	}
	
	@Override
	public Object outcome() {
		if(getProgram() != null){
			return getProgram().getGameObject();
		}	
		else
			return null;
	}
	
	@Override
	public String toString() {
		return "Expression: self connected to program " + getProgram().toString() +
				" at source location " + getSourceLocation() + ".";
	}

}
