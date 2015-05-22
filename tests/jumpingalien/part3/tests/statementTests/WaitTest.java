package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.StatementIterator;
import jumpingalien.model.program.statements.Wait;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WaitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(4, 5);
		doubleConst = new Constant<java.lang.Double>(loc,0.002);
		globals = new HashMap<String, jumpingalien.model.program.types.Type>();
		globals.put("x",new jumpingalien.model.program.types.Double());
	}

	@Before
	public void setUp() throws Exception {
		wait = new Wait(loc, doubleConst);
		theProgram = new Program(wait, globals);
	}
	
	private static SourceLocation loc;
	private static Constant<java.lang.Double> doubleConst;
	private static HashMap<String, jumpingalien.model.program.types.Type> globals;
	private Wait wait;
	private Program theProgram;


	@Test
	public void setProgramCorrect(){
		wait.setProgram(null);
		assertTrue(wait.getProgram() == null);
		assertTrue(wait.getDuration().getProgram() == null);
		wait.setProgram(theProgram);
		assertTrue(wait.getProgram() == theProgram);
		assertTrue(wait.getDuration().getProgram() == theProgram);
	}
	
	@Test
	public void iteratorTest(){
		StatementIterator<Statement> iter = wait.iterator();
		assertTrue(iter.hasNext());
		assertEquals(wait, iter.next());
		assertTrue(wait.getNbOfSkips() == 2);
		assertTrue(iter.hasNext());
		assertEquals(wait, iter.next());
		assertFalse(iter.hasNext());
	}

}
