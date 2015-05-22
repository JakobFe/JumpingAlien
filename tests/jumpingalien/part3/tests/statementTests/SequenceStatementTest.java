package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.statements.IfStatement;
import jumpingalien.model.program.statements.Print;
import jumpingalien.model.program.statements.SequenceStatement;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.StatementIterator;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SequenceStatementTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		trueConst = new Constant<Boolean>(loc,true);
		doubleConst = new Constant<Double>(loc,8.0);
	}

	@Before
	public void setUp() throws Exception {
		ifBody = new Print(trueConst, loc);
		elseBody = new Print(doubleConst,loc);
		ifStat1 = new IfStatement(trueConst, ifBody, elseBody, loc);
		sequence = new SequenceStatement(loc, ifStat1,ifBody,elseBody);
	}
	
	private static SourceLocation loc;
	private static Constant<Boolean> trueConst;
	private static Constant<Double> doubleConst;
	private IfStatement ifStat1;
	private Statement ifBody;
	private Statement elseBody;
	private Statement sequence;


	@Test
	public void iteratorTest(){
		StatementIterator<Statement> iter = sequence.iterator();
		assertTrue(iter.hasNext());
		assertEquals(ifStat1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifBody, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(ifBody, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(elseBody, iter.next());
		assertFalse(iter.hasNext());
	}
}
