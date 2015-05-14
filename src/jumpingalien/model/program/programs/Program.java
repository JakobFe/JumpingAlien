package jumpingalien.model.program.programs;

import java.util.Map;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Statement;

public class Program {
	
	public Program(Statement mainStatement,
			Map<String, Variable> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
		mainStatement.setProgram(this);
	}
	
	public Statement getMainStatement() {
		return mainStatement;
	}

	private final Statement mainStatement;
	
	public boolean hasAsStatement(Statement statement){
		return (mainStatement.hasAsSubStatement(statement));
	}
	
	public Map<String, Variable> getGlobalVariables() {
		return globalVariables;
	}

	private final Map<String, Variable> globalVariables;

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	private GameObject gameObject = null;
	
	public void execute(double timeDuration){
		double td = timeDuration;
		while(td > 0){
			if(getMainStatement().iterator().hasNext()){
				getMainStatement().iterator().next().execute();
				td -= 0.001;
			}
			else
				getMainStatement().iterator().restart();
		}
	}
}
