package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.part3.programs.SourceLocation;
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
	}

	@Before
	public void setUp() throws Exception {
		skip = new Skip(loc);
		theProgram = new Program(skip, new HashMap<String,Type>());
	}
	
	private static SourceLocation loc;
	private Statement skip;
	private Program theProgram;

	@Test
	public void setProgramCorrect(){
		skip.setProgram(null);
		skip.setProgram(theProgram);
		assertEquals(theProgram,skip.getProgram());
	}

}
