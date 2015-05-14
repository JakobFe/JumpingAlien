package jumpingalien.model.program.statements;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public class ComposedStatement extends Statement {
	
	public ComposedStatement(SourceLocation sourceLocation, List<Statement> subStatements){
		super(sourceLocation);
		
		this.subStatements = subStatements;
	}
	
	public ComposedStatement(SourceLocation sourceLocation, Statement...subStatements){
		super(sourceLocation);
		this.subStatements = Arrays.asList(subStatements);
	}
	
	public int getNbOfSubStatements(){
		return getSubStatements().size();
	}
	
	public List<Statement> getSubStatements() {
		return subStatements;
	}
	
	@Override
	public boolean hasAsSubStatement(Statement other){
		if (other == this)
			return true;
		else{
			for(Statement subStatement: subStatements){
				if(subStatement.hasAsSubStatement(other))
					return true;
			}
			return false;
		}
				
	}
	
	public Statement getSubStatementAt(int index) throws IndexOutOfBoundsException{
		if((index >= getSubStatements().size()))
			throw new IndexOutOfBoundsException();
		else
			return getSubStatements().get(index);
	}

	public void setStatementAt(int index, Statement statement)
			throws IndexOutOfBoundsException{
		if(!(index >= getSubStatements().size()))
			getSubStatements().add(statement);
		else
			getSubStatements().set(index, statement);
	}

	private final List<Statement> subStatements;
	
	// kan beter
	@Override
	public void setProgram(Program program){
		for(Statement subStatement: subStatements){
			assert program.hasAsStatement(subStatement);
		}
		super.setProgram(program);
		for(Statement subStatement: subStatements){
			subStatement.setProgram(program);
		}
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){

			@Override
			public boolean hasNext() {
				return (getSubStatementAt(getNbOfSubStatements()-1).iterator().hasNext());
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				else if(getSubStatementAt(index).iterator().hasNext()){			
					return getSubStatementAt(index).iterator().next();
				}
				else{
					incrementIndex();
					return getSubStatementAt(index).iterator().next();
				}
			}
			
			public void restart(){
				this.index = 0;
			}
			
			public int getIndex() {
				return index;
			}

			public void incrementIndex() {
				this.index += 1;
			}

			private int index = 0;
			
		};
	}

	@Override
	public void execute(){
		for(Statement subStatement: subStatements){
			subStatement.execute();
		}
	}
}
