package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.statements.Assignment;
import jumpingalien.model.program.statements.Skip;
import jumpingalien.model.program.statements.StartMovement;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.StatementIterator;
import jumpingalien.model.program.statements.Wait;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleStatementTest {

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
	}
	
	private static SourceLocation loc;
	private static Constant<Double> constant;
	private Statement skip;
	private Statement wait;
	private Statement assign;
	
	@Test
	public void hasAsSubStatementCorrect(){
		assertTrue(skip.hasAsSubStatement(skip));
	}
	
	@Test
	public void hasAsSubStatementIncorrect(){
		assertFalse(skip.hasAsSubStatement(wait));
	}
	
	@Test
	public void hasActionStatAsSubStatCorrect(){
		Statement startRunLeft = new StartMovement(loc, new Constant<Direction>(loc,Direction.LEFT));
		assertTrue(startRunLeft.hasActionStatAsSubStat());
	}
	
	@Test
	public void hasActionStatAsSubStatIncorrect(){
		assertFalse(assign.hasActionStatAsSubStat());
	}
	
	@Test
	public void iteratorCorrectOrder(){
		StatementIterator<Statement> iter = skip.iterator();
		assertTrue(iter.hasNext());
		assertTrue(iter.next() == skip);
		assertFalse(iter.hasNext());
	}
}
