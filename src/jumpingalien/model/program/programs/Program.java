package jumpingalien.model.program.programs;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Program {
	
	public Program(Statement mainStatement,
			Map<String,Type> declaredVariables){
		this.mainStatement = mainStatement;
		initialiseGlobalVariables(declaredVariables);
		mainStatement.setProgram(this);
	}
	
	public Statement getMainStatement() {
		return mainStatement;
	}

	private final Statement mainStatement;
	
	public boolean hasAsStatement(Statement statement){
		return (mainStatement.hasAsSubStatement(statement));
	}
	
	private void initialiseGlobalVariables(Map<String,Type> declaredVariables){		
		SourceLocation loc = new SourceLocation(0, 0);
		for (String name: declaredVariables.keySet()){
			globalVariables.put(name, new Variable(loc,name,
											declaredVariables.get(name)));
		}
	}
	
	private void resetVariables(){
		Type currType;
		SourceLocation loc = new SourceLocation(0, 0);
		for(String name: globalVariables.keySet()){
			currType = globalVariables.get(name).getType();
			globalVariables.put(name, new Variable(loc, name, currType));
		}
	}
	
	public Map<String, Variable> getGlobalVariables() {
		return globalVariables;
	}

	private final Map<String, Variable> globalVariables = new HashMap<String,Variable>();

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		assert gameObject.getProgram() == this;
		this.gameObject = gameObject;
	}

	private GameObject gameObject = null;
	
	public void execute(double timeDuration){
		double td = timeDuration;
		while(td > 0){
			if(getMainStatement().getThisIterator().hasNext()){
				Statement nextStatement = getMainStatement().getThisIterator().next();
				if(nextStatement != null){
					td -= 0.001;
					//System.out.println(nextStatement.getSourceLocation());
					nextStatement.execute();
				}
			}
			else{
				System.out.println("End Of Program");
				resetVariables();
				getMainStatement().getThisIterator().restart();
			}
		}
	}
	
	@Override
	public String toString() {
		String listOfVariables = "\n";
		for(String variable: globalVariables.keySet()){
			listOfVariables += ("\t" + variable + ", " + "\n");
		}
		if(globalVariables.size() == 0)
			listOfVariables += "none ";
		return "Program with global variables: " + 
				listOfVariables + "and main statement: " + "\n" + 
				getMainStatement().toString() + ".";
	}
}
