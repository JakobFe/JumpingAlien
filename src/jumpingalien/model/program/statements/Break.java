package jumpingalien.model.program.statements;

import jumpingalien.part3.programs.SourceLocation;

public class Break extends SingleStatement {

	public Break(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void executeSingleStatement() {
		if(getProgram() != null){
			Statement currentStatement = getProgram().getMainStatement();
			Statement statementToBreak;
			boolean breakFound = false;
			while(!breakFound){
				if(currentStatement instanceof IfStatement){
					
				}
			}
		}
	}
	
//	@Override
//	public void execute() {
//		if(getProgram() != null){
//			Statement mainStatement = getProgram().getMainStatement();
//			Statement statementToBreak;
//			Statement nextStatement;
//			StatementIterator<Statement> mainIterator = mainStatement.iterator();
//			boolean breakFound = false;
//			while(mainIterator.hasNext() && !breakFound){
//				nextStatement = mainIterator.next();
//				if(nextStatement instanceof Break)
//					breakFound = true;
//				else if(nextStatement instanceof While || nextStatement instanceof Foreach)
//					statementToBreak = nextStatement;
//				else if(nextStatement instanceof IfStatement){
//					nextStatement.execute();
//				}
//			}
//		}
//	}
}
