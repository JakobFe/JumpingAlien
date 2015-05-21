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
		doubleConst = new Constant<Double>(loc,8.0);
	}

	@Before
	public void setUp() throws Exception {
		ifBody = new Print(trueConst, loc);
		elseBody = new Print(doubleConst,loc);
		ifStat = new IfStatement(trueConst, ifBody, elseBody, loc);
	}
	
	private static SourceLocation loc;
	private static Constant<Boolean> trueConst;
	private static Constant<Double> doubleConst;
	private IfStatement ifStat;
	private Statement ifBody;
	private Statement elseBody;

	@Test
	public void getConditionSingleCase(){
		assertEquals(trueConst, ifStat.getCondition());
	}
	
	@Test
	public void getIfBodySingleCase() {
		assertEquals(ifBody, ifStat.getIfBody());
	}
	
	@Test
	public void getElseBodySingleCase() {
		assertEquals(elseBody, ifStat.getElseBody());
	}

}
