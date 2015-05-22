package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.program.statements.*;
import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IfStatementTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		trueConst = new Constant<Boolean>(loc,true);
		falseConst = new Constant<Boolean>(loc,false);
		doubleConst = new Constant<Double>(loc,8.0);
	}

	@Before
	public void setUp() throws Exception {
		ifBody = new Print(trueConst, loc);
		elseBody = new Print(doubleConst,loc);
		ifStat1 = new IfStatement(trueConst, ifBody, elseBody, loc);
		sequence = new SequenceStatement(loc, ifBody,elseBody);
		ifStat2 = new IfStatement(falseConst, sequence, null, loc);
		ifStat3 = new IfStatement(falseConst, ifBody, sequence, loc);
	}
	
	private static SourceLocation loc;
	private static Constant<Boolean> trueConst;
	private static Constant<Boolean> falseConst;
	private static Constant<Double> doubleConst;
	private IfStatement ifStat1;
	private IfStatement ifStat2;
	private IfStatement ifStat3;
	private Statement ifBody;
	private Statement elseBody;
	private Statement sequence;

	@Test
	public void getConditionSingleCase(){
		assertEquals(trueConst, ifStat1.getCondition());
	}
	
	@Test
	public void getIfBodySingleCase() {
		assertEquals(ifBody, ifStat1.getIfBody());
	}
	
	@Test
	public void getElseBodyNotNull() {
		assertEquals(elseBody, ifStat1.getElseBody());
	}

	@Test
	public void getElseBodyNull() {
		assertEquals(null, ifStat2.getElseBody());
	}
	
	@Test
	public void iteratorTestConditionTrue(){
		StatementIterator<Statement> iter = ifStat1.iterator();
		assertTrue(iter.hasNext());
		assertEquals(ifStat1,iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifBody,iter.next());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void iteratorTestConditionFalseNoElse(){
		StatementIterator<Statement> iter = ifStat2.iterator();
		assertTrue(iter.hasNext());
		assertEquals(ifStat2,iter.next());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void iteratorTestConditionFalseWithElse(){
		StatementIterator<Statement> iter = ifStat3.iterator();
		assertTrue(iter.hasNext());
		assertEquals(ifStat3,iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifBody, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(elseBody, iter.next());
		assertFalse(iter.hasNext());
	}
}
