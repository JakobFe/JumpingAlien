package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StartMovementTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loc = new SourceLocation(0,0);
		left = new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>(loc,
				jumpingalien.part3.programs.IProgramFactory.Direction.LEFT);
		right = new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>(loc,
				jumpingalien.part3.programs.IProgramFactory.Direction.RIGHT);
		up = new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>(loc,
				jumpingalien.part3.programs.IProgramFactory.Direction.UP);
		down = new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>(loc,
				jumpingalien.part3.programs.IProgramFactory.Direction.DOWN);
	}

	@Before
	public void setUp() throws Exception {
	}
	
	private static SourceLocation loc;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> left;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> right;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> up;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> down;
	private Statement startRunLeft;
	private Statement startRunRight;
	private Statement startJump;
	private Statement startEnd;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
