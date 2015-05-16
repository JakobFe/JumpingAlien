package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class IfStatement extends ComposedStatement {

	public IfStatement(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation, ifBody, elseBody);
		this.condition = condition;
	}
	
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
	
	public Expression getCondition() {
		return condition;
	}

	private final Expression condition;

	@Override
	public void execute() {
		// moet nog rekening houden met de 0.001s van de conditie.
		if((Boolean)getCondition().outcome()){
			getThisIterator().setIndex(1);
		}
		else{
			getThisIterator().setIndex(2);
		}
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
					if(getThisIterator().getIndex() == 0){
						return IfStatement.this;
					}
					else if(getThisIterator().getIndex() == 1){
						if(ifIter.hasNext()){
							return ifIter.next();
						}
						else{
							setIndex(3);
						}
					}
					else if((getThisIterator().getIndex() == 2) && (getNbOfSubStatements() == 2)){
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
				return (!subIteratorsInitialized || getIndex() <= 2);
			}
			
			@Override
			public void restart() {
				setIndex(0);
				ifIter.restart();
				elseIter.restart();
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
