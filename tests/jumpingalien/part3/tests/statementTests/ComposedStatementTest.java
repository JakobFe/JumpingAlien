package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;


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
		print = new Print(doubleConst, loc);
		sequence1 = new SequenceStatement(loc,skip,wait);
		sequence2 = new SequenceStatement(loc,print,print,print);
	}

	private static SourceLocation loc;
	private static Constant<Double> doubleConst;
	private Statement skip;
	private Statement wait;
	private Statement print;
	private ComposedStatement sequence1;
	private ComposedStatement sequence2;
	
	@Test
	public void getNbSubStatementsSingleCase(){
		assertEquals(2, sequence1.getNbOfSubStatements());
	}
	
	@Test
	public void hasAsSubStatementCorrect(){
		assertTrue(sequence1.hasAsSubStatement(skip));
		assertTrue(sequence1.hasAsSubStatement(wait));
	}
	
	@Test
	public void hasAsSubStatementInCorrect(){
		assertFalse(sequence1.hasAsSubStatement(print));
	}
	
	@Test
	public void hasActionStatAsSubStatCorrect(){
		assertTrue(sequence1.hasActionStatAsSubStat());
	}
	
	@Test
	public void hasActionStatAsSubStatInCorrect(){
		assertFalse(sequence2.hasActionStatAsSubStat());
	}
	
	@Test
	public void getSubStatementAtCorrect(){
		assertEquals(wait, sequence1.getSubStatementAt(1));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getSubStatementAtException1(){
		sequence1.getSubStatementAt(2);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getSubStatementAtException2(){
		sequence1.getSubStatementAt(-2);
	}

}
