package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.types.Type;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StatementTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		constant = new Constant<Double>(loc,0.005);
	}

	@Before
	public void setUp() throws Exception {
		skip = new Skip(loc);
		wait = new Wait(loc,constant);
		assign = new Assignment("x", new jumpingalien.model.program.types.Double(), constant, loc);
		theProgram = new Program(skip, new HashMap<String,Type>());
	}
	
	private static SourceLocation loc;
	private static Constant<Double> constant;
	private Statement skip;
	private Statement wait;
	private Statement assign;
	private Program theProgram;

	@Test
	public void hasAsSubStatementCorrect(){
		assertTrue(skip.hasAsSubStatement(skip));
	}
	
	@Test
	public void hasAsSubStatementIncorrect(){
		assertFalse(skip.hasAsSubStatement(wait));
	}
	
	@Test
	public void isActionStatementCorrect(){
		assertTrue(Statement.isActionStatement(skip));
	}
	
	@Test
	public void isActionStatementIncorrect(){
		assertFalse(Statement.isActionStatement(assign));
	}
	
	@Test
	public void setProgramCorrect(){
		skip.setProgram(null);
		skip.setProgram(theProgram);
		assertEquals(theProgram,skip.getProgram());
	}

}
