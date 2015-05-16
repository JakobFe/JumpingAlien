package jumpingalien.model.program.statements;

import java.util.NoSuchElementException;

import jumpingalien.model.program.expressions.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class IfStatement extends ComposedStatement {

	public IfStatement(Expression condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation, ifBody, elseBody);
		this.condition = condition;
	}
	
	public Statement  getIfBody(){
		return getSubStatementAt(0);
	}
	
	public Statement getElseBody(){
		return getSubStatementAt(1);
	}
	
	public Expression getCondition() {
		return condition;
	}

	private final Expression condition;

	@Override
	public void execute() {
		// moet nog rekening houden met de 0.001s van de conditie.
		if((Boolean)getCondition().outcome()){
			thisIterator.setIndex(1);
		}
		else{
			thisIterator.setIndex(2);
		}
	}
	
	private final StatementIterator<Statement> thisIterator = iterator();

	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				else{
					if(thisIterator.getIndex() == 0){
						return IfStatement.this;
					}
					else if(thisIterator.getIndex() == 1){
						if(ifIter.hasNext()){
							return ifIter.next();
						}
						else
							setIndex(3);
					}
					else if((thisIterator.getIndex() == 2) && (getNbOfSubStatements() == 2)){
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
				return (getIndex() <= 2);
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
			
			private final StatementIterator<Statement> ifIter = getIfBody().iterator(); 
			
			private final StatementIterator<Statement> elseIter = getElseBody().iterator(); 

		};
	}
	
	@Override
	public String toString() {
		return "Statement: if (" + getCondition().toString() + ")\n"+ "then " + "\n\t" + 
				getIfBody() + "\n" + "else\n\t" + getElseBody().toString() + "\n" + 
				"at source location " + getSourceLocation().toString() + ".";
	}

}
