package jumpingalien.model.program.programs;

import java.util.Optional;

import jumpingalien.model.program.ProgramFactory;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.statements.Statement;
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
			System.out.println(theProgram);
		}
		catch(Exception exc){
			System.out.println("Experiment aborted due to a parsing failure.");
		}
	}
}
