package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ComposedStatementTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		doubleConst = new Constant<Double>(loc,0.02);
	}

	@Before
	public void setUp() throws Exception {
		skip = new Skip(loc);
		wait = new Wait(loc, doubleConst);
		
	}

	private static SourceLocation loc;
	private static Constant<Double> doubleConst;
	private Statement skip;
	private Statement wait;
	private ComposedStatement sequence;
	private Program theProgram;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
