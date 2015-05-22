package jumpingalien.part3.tests.statementTests;

import jumpingalien.model.exceptions.BreakException;
import jumpingalien.model.program.statements.Break;
import jumpingalien.model.program.statements.SingleStatement;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BreakTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(4, 5);
	}

	@Before
	public void setUp() throws Exception {
		theBreak = new Break(loc);
	}
	
	private static SourceLocation loc;
	private SingleStatement theBreak;

	@Test(expected = BreakException.class)
	public void executeCorrect(){
		theBreak.executeSingleStatement();
	}

}
