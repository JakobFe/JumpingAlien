package jumpingalien.model.program.programs;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.model.game.GameObject;
import jumpingalien.model.program.expressions.Variable;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

public class Program {
	
	public Program(Statement mainStatement,
			Map<String,Type> declaredVariables) throws IllegalStateException{
		this.mainStatement = mainStatement;
		initialiseGlobalVariables(declaredVariables);
		mainStatement.setProgram(this);
	}
	
	public Statement getMainStatement() {
		return mainStatement;
	}

	private final Statement mainStatement;
	
	public boolean hasAsStatement(Statement statement){
		return (getMainStatement().hasAsSubStatement(statement));
	}
	
	private void initialiseGlobalVariables(Map<String,Type> declaredVariables){		
		if(declaredVariables != null){	
			SourceLocation loc = new SourceLocation(0, 0);
			for (String name: declaredVariables.keySet()){
				globalVariables.put(name, new Variable(loc,name,
												declaredVariables.get(name)));
			}
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
		assert getGameObject() == null;
		assert gameObject != null;
		assert gameObject.getProgram() == this;
		this.gameObject = gameObject;
	}

	private GameObject gameObject = null;
	
	public StatementIterator<Statement> getProgramIterator() {
		if(programIterator == null){
			programIterator = getMainStatement().iterator();
		}
		return programIterator;
	}

	private StatementIterator<Statement> programIterator = null;	
	
	
	public boolean isWellFormed(Statement statement, boolean inWhile){
		Statement currentStatement = statement;
		if(currentStatement instanceof Break && !inWhile)
			return false;
		else if(currentStatement instanceof While)
			return(isWellFormed(((While)currentStatement).getBody(), true));
		else if(currentStatement instanceof ComposedStatement){
			for(Statement subStat: ((ComposedStatement)currentStatement).getSubStatements()){
				if(subStat instanceof Foreach){
					if(subStat.hasActionStatAsSubStat()){
						return false;
					}
				}
				else if(!isWellFormed(subStat,inWhile))
					return false;
			}
		}
		return true;
	}
	
	public boolean isWellFormed(){
		return isWellFormed(getMainStatement(), false);
	}
	
	public void execute(double timeDuration){
		double td = timeDuration;
		while(td > 0){
			if(getGameObject().getWorld().isGameOver())
				break;
			if(getProgramIterator().hasNext()){
				Statement nextStatement = getProgramIterator().next();
				if(nextStatement != null){
					td -= 0.001;
					//System.out.println(nextStatement.getSourceLocation());
					//nextStatement.execute();
				}
			}
			else{
				//System.out.println("End Of Program");
				resetVariables();
				getProgramIterator().restart();
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
