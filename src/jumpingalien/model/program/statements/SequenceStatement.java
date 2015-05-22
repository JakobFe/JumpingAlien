package jumpingalien.model.program.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import jumpingalien.part3.programs.SourceLocation;

public class SequenceStatement extends ComposedStatement {

	public SequenceStatement(SourceLocation sourceLocation,
			List<Statement> subStatements) {
		super(sourceLocation, subStatements);
	}
	
	public SequenceStatement(SourceLocation sourceLocation, Statement...subStatements) {
		super(sourceLocation,subStatements);
	}
	
	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){

			@Override
			public boolean hasNext() {
				return(!subIteratorsInitialized || getIteratorAt(getNbOfSubStatements()-1).hasNext());
			}

			@Override
			public Statement next() throws NoSuchElementException{
				if(!subIteratorsInitialized)
					initialiseSubIterators();
				if(!hasNext())
					throw new NoSuchElementException();
				else if(getIteratorAt(index).hasNext()){
					return (Statement)getIteratorAt(index).next();
				}
				else{
					incrementIndex();
					return (Statement)getIteratorAt(index).next();
				}
			}
			
			@Override
			public void restart(){
				setIndex(0);
				for(StatementIterator<Statement> subIter: subIterators){
					subIter.restart();
				}
			}
			
			@Override
			public int getIndex() {
				return index;
			}

			public void incrementIndex() {
				setIndex(getIndex()+1);
			}

			private int index = 0;

			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
			private void initialiseSubIterators(){
				subIteratorsInitialized = true;
				for(Statement substat: getSubStatements()){
					subIterators.add(substat.iterator());
				}

			}
			
			private StatementIterator<Statement> getIteratorAt(int index){
				return subIterators.get(index);
			}
			
			private final List<StatementIterator<Statement>> subIterators = 
					new ArrayList<StatementIterator<Statement>>() ;
			
			private boolean subIteratorsInitialized = false;
		};
	}

	@Override
	public String toString() {
		String subStatements = "";
		for(Statement subStat: getSubStatements()){
			subStatements += ("\n\t" + subStat.toString() + "\n");
		}
		return "Statement: Sequence:" + "\n" + subStatements + "\n\n" +
				"at source location " + getSourceLocation().toString() + ".";
	}
	
}
