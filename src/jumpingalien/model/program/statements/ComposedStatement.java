package jumpingalien.model.program.statements;

import java.util.List;

import jumpingalien.part3.programs.SourceLocation;

public class ComposedStatement extends Statement {
	
	public ComposedStatement(SourceLocation sourceLocation, List<Statement> subStatements){
		super(sourceLocation);
		this.subStatements = subStatements;
	}
	
	public List<Statement> getSubStatements() {
		return subStatements;
	}
	
	public Statement getSubStatementAt(int index) throws IndexOutOfBoundsException{
		if((index >= subStatements.size()))
			throw new IndexOutOfBoundsException();
		else
			return subStatements.get(index);
	}
	
	public void setStatementAt(int index, Statement statement) throws IndexOutOfBoundsException{
		if(!(index >= subStatements.size()))
			subStatements.add(statement);
		else
			subStatements.set(index, statement);
		
	}

	private final List<Statement> subStatements;
}
