package jumpingalien.model.program.programs;

import java.util.Map;

import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Statement;

public class Program {
	
	public Program(Statement mainStatement,
			Map<String, Variable> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
	}
	
	public Statement getMainStatement() {
		return mainStatement;
	}

	private final Statement mainStatement;
	
	public Map<String, Variable> getGlobalVariables() {
		return globalVariables;
	}

	private final Map<String, Variable> globalVariables;
	
}
