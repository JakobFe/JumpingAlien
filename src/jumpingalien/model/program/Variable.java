package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Variable extends Expression {
	
	public Variable(SourceLocation sourceLocation, String name, Type type){
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
		return null;
	}

}
