package jumpingalien.part3.tests.statementTests;

import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.statements.*;
import jumpingalien.part3.programs.SourceLocation;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WhileTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		trueConst = new Constant<Boolean>(loc, true);
		doubleConst = new Constant<Double>(loc,8.0);
	}

	@Before
	public void setUp() throws Exception {
		ifBody = new Print(trueConst, loc);
		elseBody = new Print(doubleConst,loc);
		ifStat = new IfStatement(trueConst, ifBody, elseBody, loc);
		skip = new Skip(loc);
		wait = new Wait(loc,doubleConst);
		sequence = new SequenceStatement(loc, ifStat, skip);
		whileStat1 = new While(trueConst, sequence, loc);
		whileStat2 = new While(trueConst, ifStat, loc);
	}
	
	private static SourceLocation loc;
	private static Constant<Boolean> trueConst;
	private static Constant<Double> doubleConst;
	private Statement skip;
	private Statement wait;
	private Statement ifStat;
	private Statement ifBody;
	private Statement elseBody;
	private Statement sequence;
	private Statement whileStat1;
	private Statement whileStat2;
	
	@Test
	public void hasAsSubStatementCorrect(){
		assertTrue(whileStat1.hasAsSubStatement(whileStat1));
		assertTrue(whileStat1.hasAsSubStatement(ifBody));
		assertTrue(whileStat1.hasAsSubStatement(skip));
	}
	
	@Test
	public void hasAsSubStatementInCorrect(){
		assertFalse(whileStat1.hasAsSubStatement(wait));
	}
	
	@Test
	public void hasActionStatAsSubStatCorrect(){
		assertTrue(whileStat1.hasActionStatAsSubStat());
	}
	
	@Test
	public void hasActionStatAsSubStatInCorrect(){
		assertFalse(whileStat2.hasActionStatAsSubStat());
	}
	
	@Test
	public void iteratorTest(){
		StatementIterator<Statement> iter = whileStat1.iterator();
		assertTrue(iter.hasNext());
		assertEquals(whileStat1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifStat, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifBody, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(skip, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(null, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(whileStat1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifStat, iter.next());
		assertTrue(iter.hasNext());
	}
}
