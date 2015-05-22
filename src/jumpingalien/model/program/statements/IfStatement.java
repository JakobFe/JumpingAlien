package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class IfStatement extends ComposedStatement {

	public IfStatement(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation, ifBody, elseBody);
		this.condition = condition;
	}
	
	public Expression getCondition() {
		return condition;
	}

	private final Expression condition;
	
	public Statement getIfBody(){
		return getSubStatementAt(0);
	}
	
	public Statement getElseBody(){
		try{
			return getSubStatementAt(1);
		}
		catch(Exception e){
			return null;
		}
	}
		
	@Override
	public void setProgram(Program program) {
		assert (program!=null && program.hasAsStatement(this));
		for(Statement subStatement: getSubStatements()){
			assert program.hasAsStatement(subStatement) ||
				   subStatement == null;
		}
		super.setProgram(program);
		getCondition().setProgram(program);
	}

	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!subIteratorsInitialized)
					initialiseSubIterators();
				if(!hasNext())
					throw new NoSuchElementException();
				else{
					if(getIndex() == 0){
						if((Boolean)getCondition().outcome()){
							setIndex(1);
						}
						else
							setIndex(2);
						return IfStatement.this;
					}
					else if(getIndex() == 1){
						if(ifIter.hasNext())
							return ifIter.next();
						else
							setIndex(3);
					}
					else if((getIndex() == 2) && (getNbOfSubStatements() == 2)){
						if(elseIter.hasNext())
							return elseIter.next();
						else
							setIndex(3);
					}
					return null;
				}
			}
			
			@Override
			public boolean hasNext() {
				if (!subIteratorsInitialized || getIndex() == 0)
					return true;
				else if(getIndex() == 1)
					return ifIter.hasNext();
				else if(getIndex() == 2 && getElseBody() != null)
					return elseIter.hasNext();
				else return false;
			}
			
			@Override
			public void restart() {
				setIndex(0);
				if(subIteratorsInitialized){
					ifIter.restart();
					if(elseIter != null){
						elseIter.restart();
					}
				}
			}
			
			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
			@Override
			public int getIndex() {
				return this.index;
			}
			
			private int index;
			
			private void initialiseSubIterators(){
				subIteratorsInitialized = true;
				ifIter = getIfBody().iterator();
				if(getElseBody() != null)
					elseIter = getElseBody().iterator();
			}
			
			private boolean subIteratorsInitialized = false;
			
			private StatementIterator<Statement> ifIter; 
			
			private StatementIterator<Statement> elseIter;

		};
	}
	
	@Override
	public String toString() {
		return "Statement: if (" + getCondition().toString() + ")\n"+ "then " + "\n\t" + 
				getIfBody() + "\n" + "else\n\t" + getElseBody().toString() + "\n" + 
				"at source location " + getSourceLocation().toString() + ".";
	}

}
