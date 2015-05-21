package jumpingalien.model.program.statements;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jumpingalien.model.program.programs.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class ComposedStatement extends Statement {
	
	public ComposedStatement(SourceLocation sourceLocation, List<Statement> subStatements){
		super(sourceLocation);
		Stream<Statement> filteredStream = subStatements.stream().filter(s -> s != null);
		List<Statement> filteredList = filteredStream.collect(Collectors.toList());
		this.subStatements = filteredList;
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
	
	@Override
	public boolean hasActionStatAsSubStat() {
		for(Statement subStatement: subStatements){
			if(isActionStatement(subStatement))
				return true;
		}
		return false;
	
	}
	
	@SuppressWarnings("unused")
	public boolean hasForAsSubStat(){
		for(Statement subStatement: subStatements){
		}
		return false;
	}
	
	public Statement getSubStatementAt(int index) throws IndexOutOfBoundsException{
		if((index >= getSubStatements().size()) || index<0)
			throw new IndexOutOfBoundsException();
		else
			return getSubStatements().get(index);
	}

	private final List<Statement> subStatements;
	
	@Override
	public boolean isWellFormed() {
		boolean result = true;
		for(Statement subStat: getSubStatements()){
			if((subStat!= null) && !subStat.isWellFormed())
				result = false;
		}
		return result;
	}
	
	// kan beter
	@Override
	public void setProgram(Program program){
		for(Statement subStatement: subStatements){
			assert program.hasAsStatement(subStatement);
		}
		super.setProgram(program);
		for(Statement subStatement: subStatements){
			if(subStatement != null)
				subStatement.setProgram(program);
		}
	}
}
