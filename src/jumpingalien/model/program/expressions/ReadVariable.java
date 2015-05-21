package jumpingalien.model.program.expressions;

import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public class ReadVariable extends Expression {
	
	public ReadVariable(SourceLocation sourceLocation, String name, Type type){
		super(sourceLocation);
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	private final String name;

	public Type getType() {
		return type;
	}

	private final Type type;
		
	@Override
	public Object outcome() {
		if(getProgram() != null)
			return getProgram().getGlobalVariables().get(getName()).outcome();
		throw new IllegalStateException("No program assigned to this read variable.");
	}
	
	@Override
	public String toString() {
		return "Read the variable " + getName() + " of type " + getType() + 
				" at sourcelocation " + getSourceLocation();
	}
}
