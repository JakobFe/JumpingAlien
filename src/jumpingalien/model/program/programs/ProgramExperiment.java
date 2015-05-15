package jumpingalien.model.program.programs;

import java.util.Optional;

import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.StatementIterator;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ProgramParser;

public class ProgramExperiment {
	
	public static void main(String[] args) {
		IProgramFactory<Expression, Statement, Type, Program> theFactory = 
				new ProgramFactory();
		ProgramParser<Expression, Statement, Type, Program> theParser = 
				new ProgramParser<>(theFactory);
		try{
			Optional<Program> parseResult = theParser.parseFile(
					"resources/programs/eigen_test_file.txt");
			Program theProgram;
			
			if(parseResult.isPresent())
				theProgram = parseResult.get();
			else{
				System.out.println(theParser.getErrors());
				System.out.println();
				throw new IllegalArgumentException();
			}
			Statement main = theProgram.getMainStatement();
			StatementIterator<Statement> iter = main.iterator();
			
			Statement theNext;
			for(int i=0;i<10;i++){
				System.out.println("\n\nNEXT STATEMENT:");
				theNext = iter.next();
				System.out.println(theNext);
				if(theNext != null)
					theNext.execute();
			}
			
		}
		catch(Exception exc){
			System.out.println("Experiment aborted due to a parsing failure.");
		}
	}
}
