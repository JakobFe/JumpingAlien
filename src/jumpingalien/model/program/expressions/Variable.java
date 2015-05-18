package jumpingalien.model.program.expressions;

import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Variable extends Expression {
	
	public Variable(SourceLocation sourceLocation, String name, Type type){
		super(sourceLocation);
		this.name = name;
		this.type = type;
		this.value = type.getValue();
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
		return getValue();
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private Object value;
		
	@Override
	public String toString() {
		return "Declaration of the variable " + getName() + " of type " + getType() + " with value " + 
				String.valueOf(getValue()) + " at sourcelocation " + getSourceLocation();
	}
}
