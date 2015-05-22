package jumpingalien.part3.tests.statementTests;

import static org.junit.Assert.*;
import jumpingalien.model.game.Buzam;
import jumpingalien.model.game.Position;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.StartMovement;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;
import static jumpingalien.tests.util.TestUtils.*;

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
		sprites = spriteArrayForSize(10, 10);
	}

	@Before
	public void setUp() throws Exception {
		startRunLeft = new StartMovement(loc, left);
		startRunRight = new StartMovement(loc, right);
		startJump = new StartMovement(loc, up);
		startDuck = new StartMovement(loc, down);
		pos= new Position(100,100);
	}
	
	private static SourceLocation loc;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> left;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> right;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> up;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> down;
	private static Sprite[] sprites;
	private StartMovement startRunLeft;
	private StartMovement startRunRight;
	private StartMovement startJump;
	private StartMovement startDuck;
	private Position pos;
	
	@Test
	public void executeStartRunLeftTest(){
		Program theProgram = new Program(startRunLeft, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunLeft.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.LEFT));
	}
	
	@Test
	public void executeStartRunLeftNoEfect(){
		Program theProgram = new Program(startRunRight, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunLeft.executeSingleStatement();
		assertFalse(alien.isMoving(jumpingalien.model.game.Direction.LEFT));
	}
	
	@Test
	public void executeStartRunRightTest(){
		Program theProgram = new Program(startRunRight, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunRight.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.RIGHT));
	}
	
	@Test
	public void executeStartJumpTest(){
		Program theProgram = new Program(startJump, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startJump.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.UP));
	}
	
	@Test
	public void executeStartDuckTest(){
		Program theProgram = new Program(startDuck, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startDuck.executeSingleStatement();
		assertTrue(alien.getIsDucked());
	}
	
}
