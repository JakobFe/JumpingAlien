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
		System.out.println("\n\n\n\n\n\n\nEXECUTE ASSIGNEMENT:");
		if(getProgram() != null){
			System.out.println("BEFORE");
			System.out.println(getProgram().getGlobalVariables().get(getName()));
			System.out.println(getValue().outcome());
			
			getProgram().getGlobalVariables().get(getName()).setValue(getValue());
			
			System.out.println("AFTER");
			System.out.println(getProgram().getGlobalVariables().get(getName()));
			System.out.println(getValue().outcome());
		}
		System.out.println("\n\n\n\n\n");
	}
	
	@Override
	public String toString() {
		return "Statement: Assignement of variable " + getName() + 
				" of type " + getType().toString() + " to the value \n \t" +
				getValue().toString() +  "\n \t" + "at source location " + 
				getSourceLocation().toString() + ".";
	}
	
}
