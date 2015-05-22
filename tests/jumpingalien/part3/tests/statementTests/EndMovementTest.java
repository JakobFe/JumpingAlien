package jumpingalien.part3.tests.statementTests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.game.Buzam;
import jumpingalien.model.game.Position;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.EndMovement;
import jumpingalien.model.program.statements.SequenceStatement;
import jumpingalien.model.program.statements.StartMovement;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EndMovementTest{

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
		pos= new Position(100,100);
		startRunLeft = new StartMovement(loc, left);
		startRunRight = new StartMovement(loc, right);
		startJump = new StartMovement(loc, up);
		startDuck = new StartMovement(loc, down);
		endRunLeft = new EndMovement(loc, left);
		endRunRight = new EndMovement(loc, right);
		endJump = new EndMovement(loc, up);
		endDuck = new EndMovement(loc, down);
	}
	
	private static SourceLocation loc;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> left;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> right;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> up;
	private static Constant<jumpingalien.part3.programs.IProgramFactory.Direction> down;
	private Position pos;
	private StartMovement startRunLeft;
	private StartMovement startRunRight;
	private StartMovement startJump;
	private StartMovement startDuck;
	private static Sprite[] sprites;
	private EndMovement endRunLeft;
	private EndMovement endRunRight;
	private EndMovement endJump;
	private EndMovement endDuck;
	
	@Test
	public void endRunLeftNoEffect(){
		Program theProgram = new Program(startRunRight, null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunRight.executeSingleStatement();
		endRunRight.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.RIGHT));
	}
	
	@Test
	public void endRunLeftCorrect(){
		SequenceStatement seq = new SequenceStatement(loc, startRunLeft,endRunLeft);
		Program theProgram = new Program(seq,null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunLeft.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.LEFT));
		endRunLeft.executeSingleStatement();
		assertFalse(alien.isMoving(jumpingalien.model.game.Direction.LEFT));
	}
	
	@Test
	public void endRunRightCorrect(){
		SequenceStatement seq = new SequenceStatement(loc, startRunRight,endRunRight);
		Program theProgram = new Program(seq,null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startRunRight.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.RIGHT));
		endRunRight.executeSingleStatement();
		assertFalse(alien.isMoving(jumpingalien.model.game.Direction.RIGHT));
	}
	
	@Test
	public void endJumpCorrect(){
		SequenceStatement seq = new SequenceStatement(loc, startJump,endJump);
		Program theProgram = new Program(seq,null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startJump.executeSingleStatement();
		assertTrue(alien.isMoving(jumpingalien.model.game.Direction.UP));
		endJump.executeSingleStatement();
		assertFalse(alien.isMoving(jumpingalien.model.game.Direction.UP));
	}
	
	@Test
	public void endDuckCorrect(){
		SequenceStatement seq = new SequenceStatement(loc, startDuck,endDuck);
		Program theProgram = new Program(seq,null);
		Buzam alien = new Buzam(pos, sprites,theProgram);
		startDuck.executeSingleStatement();
		assertTrue(alien.getIsDucked());
		endDuck.executeSingleStatement();
		assertFalse(alien.getIsDucked());
	}
}
