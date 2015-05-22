package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;


import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.statements.*;
import jumpingalien.part3.programs.IProgramFactory.Direction;

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
		assign = new Assignment("x", new jumpingalien.model.program.types.Double(), constant, loc);
	}
	
	private static SourceLocation loc;
	private static Constant<Double> constant;
	private Statement skip;
	private Statement assign;
	
	@Test
	public void isActionStatementCorrect1(){
		assertTrue(Statement.isActionStatement(skip));
	}
	
	@Test
	public void isActionStatementCorrect2(){
		Statement startRunLeft = new StartMovement(loc, new Constant<Direction>(loc,Direction.LEFT));
		assertTrue(Statement.isActionStatement(startRunLeft));
	}
	
	@Test
	public void isActionStatementCorrect3(){
		Statement endRunRight = new EndMovement(loc, new Constant<Direction>(loc,Direction.RIGHT));
		assertTrue(Statement.isActionStatement(endRunRight));
	}
	
	@Test
	public void isActionStatementCorrect4(){
		Statement startJump = new StartMovement(loc, new Constant<Direction>(loc,Direction.UP));
		assertTrue(Statement.isActionStatement(startJump));
	}
	
	@Test
	public void isActionStatementCorrect5(){
		Statement endDuck = new EndMovement(loc, new Constant<Direction>(loc,Direction.DOWN));
		assertTrue(Statement.isActionStatement(endDuck));
	}
	
	@Test
	public void isActionStatementIncorrect(){
		assertFalse(Statement.isActionStatement(assign));
	}
}
