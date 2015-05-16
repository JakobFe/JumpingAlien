package jumpingalien.model.program.statements;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Assignment extends SingleStatement {
	
	public Assignment(String variableName, Type variableType,
			Expression value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.name = variableName;
		this.type = variableType;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	private final String name;

	public Type getType() {
		return type;
	}

	private final Type type;
	
	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}

	private Expression value;
	
	public void execute(){
		if(getProgram() != null){
			getProgram().getGlobalVariables().get(getName()).setValue(getValue().outcome());
		}
	}
	
	@Override
	public String toString() {
		return "Statement: Assignement of variable " + getName() + 
				" of type " + getType().toString() + " to the value \n \t" +
				getValue().toString() +  "\n \t" + "at source location " + 
				getSourceLocation().toString() + ".";
	}
	
}
