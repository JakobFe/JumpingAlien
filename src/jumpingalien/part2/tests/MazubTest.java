package jumpingalien.part2.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import jumpingalien.part1.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.model.*;
import jumpingalien.model.exceptions.*;
import jumpingalien.util.*;

import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.*;

import org.junit.Before;


/**
 * This test case contains tests for each public method of the class Mazub,
 * excluding inspectors.
 * 
 * @author	Jakob Festraets, Vincent Kemps
 */ 

public class MazubTest {

	@Before
	public void setUp() throws Exception {
		sprites = spriteArrayForSize(2, 2, 10 + 2 * 10);
		mazubPos_0_0 = new Mazub(0,0,1,3,sprites);
		mazubPos_50_0 = new Mazub(50,0,1,3,sprites);
		mazubPos_800_0 = new Mazub(800,0,1,3,sprites);
	}
	
	private Sprite[] sprites;
	private Mazub mazubPos_0_0;
	private Mazub mazubPos_50_0;
	private Mazub mazubPos_800_0;

	////////////////////////////////////////////////////////////////////////////
	// Tests for some public methods in the class Mazub.
	
	@Test
	public void getDirectionsCorrect(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		mazubPos_0_0.startJump();
		for (int i = 0; i < 5; i++) {
			mazubPos_0_0.advanceTime(0.15);
		}
		assertEquals(Direction.RIGHT,mazubPos_0_0.getHorDirection());
		assertEquals(Direction.UP,mazubPos_0_0.getVertDirection());
	}
	
	@Test
	public void getDirectionsCorrect2(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		mazubPos_0_0.startJump();
		for (int i = 0; i < 10; i++) {
			mazubPos_0_0.advanceTime(0.15);
		}
		mazubPos_0_0.endMove(Direction.RIGHT);
		assertEquals(Direction.NULL,mazubPos_0_0.getHorDirection());
		assertEquals(Direction.DOWN,mazubPos_0_0.getVertDirection());
	}
	
	@Test(expected=IllegalTimeIntervalException.class)
	public void advanceTimeIllegalArgument(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		mazubPos_0_0.advanceTime(0.45);
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void startMoveLeftCorrectLocation(){
		mazubPos_50_0.startMove(Direction.LEFT);
		mazubPos_50_0.advanceTime(0.1);
		// x_new [m] = 0.50 [m] - 1 [m/s] * 0.1 [s] - 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.3955 [m] = 39.55 [cm], which falls into pixel (39, 0)
		
		assertArrayEquals(intArray(39,0),intArray(
				mazubPos_50_0.getPosition().getDisplayedXPosition(),
				mazubPos_50_0.getPosition().getDisplayedYPosition()));
	}
	
	// Given test.
	@Test
	public void startMoveRightCorrect() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(2, 2));
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)
		
		assertArrayEquals(intArray(10, 0), intArray(
				alien.getPosition().getDisplayedXPosition(),
				alien.getPosition().getDisplayedYPosition()));
	}
	
	@Test
	public void startJumpCorrectLocation(){
		mazubPos_50_0.startJump();
		mazubPos_50_0.advanceTime(0.1);
		// y_new [m] = 0 + 8 [m/s] * 0.1 [s] - 1/2 10 [m/s^2] * (0.1 [s])^2 =
		// 0.75 [m] = 75 [cm], which falls into pixel (50,75)
		assertArrayEquals(intArray(50,75),intArray(
				mazubPos_50_0.getPosition().getDisplayedXPosition(),
				mazubPos_50_0.getPosition().getDisplayedYPosition()));
	}
	
	@Test
	public void combinedMovementCorrectLocation(){
		mazubPos_50_0.startMove(Direction.LEFT);
		mazubPos_50_0.startJump();
		mazubPos_50_0.advanceTime(0.1);
		assertArrayEquals(intArray(39,75),intArray(
				mazubPos_50_0.getPosition().getDisplayedXPosition(),
				mazubPos_50_0.getPosition().getDisplayedYPosition()));
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void startMoveRightCorrectStartVelocity(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		assertArrayEquals(doubleArray(1,0),doubleArray(
				mazubPos_0_0.getHorVelocity(),
				mazubPos_0_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightCorrectUpdateVelocity(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		mazubPos_0_0.advanceTime(0.1);
		// vx_new [m] = 1 [m/s] + 0.9 [m/s^2] * (0.1 [s]) = 1.09
		assertArrayEquals(doubleArray(1.09,0),doubleArray(
				mazubPos_0_0.getHorVelocity(),
				mazubPos_0_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftCorrectUpdateVelocity(){
		mazubPos_50_0.startMove(Direction.LEFT);
		mazubPos_50_0.advanceTime(0.15);
		// vx_new [m] = -1 [m/s] - 0.9 [m/s^2] * (0.15 [s]) = -1.135
		assertArrayEquals(doubleArray(-1.135,0),doubleArray(
				mazubPos_50_0.getHorVelocity()*mazubPos_50_0.getHorDirection().getFactor(),
				mazubPos_50_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	// Given test.
	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftSpeedAfterMaxSpeedReached() {
		mazubPos_800_0.startMove(Direction.LEFT);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 110; i++) {
			mazubPos_800_0.advanceTime(0.2 / 9);
		}

		assertArrayEquals(doubleArray(-3, 0), doubleArray(
				mazubPos_800_0.getHorVelocity()*mazubPos_800_0.getHorDirection().getFactor(),
				mazubPos_800_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endMoveRightCorrectVelocity(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		for (int i = 0; i < 110; i++) {
			mazubPos_0_0.advanceTime( 0.2 / 9);
		}
		mazubPos_0_0.endMove(Direction.RIGHT);
		assertArrayEquals(doubleArray(0,0),doubleArray(
				mazubPos_0_0.getHorVelocity(),
				mazubPos_0_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightThenStartDuckCorrectMaxVelocity(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		for (int i = 0; i < 20; i++) {
			mazubPos_0_0.advanceTime(0.2 / 9);
		}
		mazubPos_0_0.startDuck();
		mazubPos_0_0.advanceTime(0.15);
		// When ducking, max velocity is 1.
		assertArrayEquals(doubleArray(1,0),doubleArray(
				mazubPos_0_0.getHorVelocity(),
				mazubPos_0_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startDuckThenStartMoveLeftCorrectMaxVelocity(){
		mazubPos_50_0.startDuck();
		mazubPos_50_0.startMove(Direction.LEFT);
		mazubPos_50_0.advanceTime(0.15);
		// When ducking, max velocity is 1.
		assertArrayEquals(doubleArray(-1,0),doubleArray(
				mazubPos_50_0.getHorVelocity()*mazubPos_50_0.getHorDirection().getFactor(),
				mazubPos_50_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startJumpCorrectVelocity(){
		mazubPos_50_0.startJump();
		mazubPos_50_0.advanceTime(0.1);
		// v_new [m] = 8 [m/s]  -  10 [m/s^2] * (0.1 [s]) = 7 [m/s] 
		assertArrayEquals(doubleArray(0,7),doubleArray(
				mazubPos_50_0.getHorVelocity(),
				mazubPos_50_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endJumpCorrectVelocity(){
		mazubPos_50_0.startJump();
		mazubPos_50_0.advanceTime(0.1);
		mazubPos_50_0.endJump(); 
		assertArrayEquals(doubleArray(0,0),doubleArray(
				mazubPos_50_0.getHorVelocity(),
				mazubPos_50_0.getVertVelocity()),Util.DEFAULT_EPSILON);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	
	// Gegeven test.
	@Test
	public void testAccellerationZeroWhenNotMoving() {
		Mazub alien = new Mazub(0, 0, spriteArrayForSize(2, 2));
		assertArrayEquals(doubleArray(0.0, 0.0),doubleArray(
				alien.getHorAcceleration(),
				alien.getVertAcceleration()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightCorrectStartAcceleration(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		assertArrayEquals(doubleArray(0.9,0),doubleArray(
				mazubPos_0_0.getHorAcceleration(),
				mazubPos_0_0.getVertAcceleration()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftAcceleartionZeroAfterMaxSpeedReached() {
		mazubPos_800_0.startMove(Direction.LEFT);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 110; i++) {
			mazubPos_800_0.advanceTime(0.2 / 9);
		}

		assertArrayEquals(doubleArray(0, 0), doubleArray(
				mazubPos_800_0.getHorAcceleration(),
				mazubPos_800_0.getVertAcceleration()),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startJumpCorrectAcceleration(){
		mazubPos_50_0.startJump();
		assertArrayEquals(doubleArray(0, -10), doubleArray(
				mazubPos_50_0.getHorAcceleration(),
				mazubPos_50_0.getVertAcceleration()),Util.DEFAULT_EPSILON);
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test(expected=IllegalJumpInvokeException.class)
	public void startJumpDenied(){
		mazubPos_50_0.startJump();
		mazubPos_50_0.advanceTime(0.1);
		// v_new [m] = 8 [m/s]  -  10 [m/s^2] * (0.1 [s]) = 7 [m/s]
		mazubPos_50_0.startJump();
		// You can't double jump.
	}
	
	@Test
	public void startJumpFallingCorrect(){
		mazubPos_50_0.startJump();
		for (int i=0 ; i<9; i++){
			mazubPos_50_0.advanceTime(0.1);
		}
		// v_new [m] = 8 [m/s]  - 8 * ( 10 [m/s^2] * (0.1 [s])) = -1 [m/s]
		
		assertArrayEquals(doubleArray(0,-1),doubleArray(
				mazubPos_50_0.getHorVelocity(),
				mazubPos_50_0.getVertVelocity()*mazubPos_50_0.getVertDirection().getFactor()),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endJumpNoEffect(){
		mazubPos_50_0.startJump();
		for (int i=0 ; i<9; i++){
			mazubPos_50_0.advanceTime(0.1);
		}
		// v_new [m] = 8 [m/s]  - 8 * ( 10 [m/s^2] * (0.1 [s])) = -1 [m/s]
		mazubPos_50_0.endJump();
		// Has no effect since this mazub is moving down0
		assertArrayEquals(doubleArray(0,-1),doubleArray(
				mazubPos_50_0.getHorVelocity(),
				mazubPos_50_0.getVertVelocity()*mazubPos_50_0.getVertDirection().getFactor()),
				Util.DEFAULT_EPSILON);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	
	// Given test
	@Test
	public void testWalkAnimationLastFrameRight() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien);

		facade.advanceTime(alien, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(alien, 0.075);
		}

		assertEquals(sprites[8+m], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testWalkAnimationLastFrameLeft() {
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(1000, 0, sprites);

		alien.startMove(Direction.LEFT);

		alien.advanceTime(0.005);
		for (int i = 0; i < m; i++) {
			alien.advanceTime(0.075);
		}
		
		alien.updateSpriteIndex();
		assertEquals(sprites[9+2*m], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite1Correct(){
		Mazub alien = new Mazub(0, 0, sprites);
		
		alien.startDuck();
		alien.advanceTime(0.15);
		alien.updateSpriteIndex();
		assertEquals(sprites[1], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite1CorrectWasMovingLongAGo(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		alien.startDuck();
		alien.advanceTime(0.15);
		alien.endMove(Direction.RIGHT);
		
		for (int i = 0; i < 6; i++) {
			alien.advanceTime(0.19);
		}
		
		alien.updateSpriteIndex();
		assertEquals(sprites[1], alien.getCurrentSprite());
	}
	
	
	@Test
	public void getCurrentSprite2Correct(){		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(0, 0, sprites);
		
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		alien.endMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[2], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite3Correct(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.LEFT);
		alien.advanceTime(0.15);
		alien.endMove(Direction.LEFT);
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[3], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite4Correct(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		alien.startJump();
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[4], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite5Correct(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.LEFT);
		alien.advanceTime(0.15);
		alien.startJump();
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[5], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite6Correct(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		alien.startDuck();
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[6], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite6CorrectWasMoving(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.RIGHT);
		alien.advanceTime(0.15);
		alien.startDuck();
		alien.advanceTime(0.15);
		alien.endMove(Direction.RIGHT);
		
		for (int i = 0; i < 5; i++) {
			alien.advanceTime(0.19);
		}
		
		alien.updateSpriteIndex();
		assertEquals(sprites[6], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite7Correct(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.LEFT);
		alien.advanceTime(0.15);
		alien.startDuck();
		alien.advanceTime(0.15);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[7], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite7CorrectWasMoving(){
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(500, 0, sprites);
		
		alien.startMove(Direction.LEFT);
		alien.advanceTime(0.15);
		alien.startDuck();
		alien.advanceTime(0.15);
		alien.endMove(Direction.LEFT);
		
		for (int i = 0; i < 5; i++) {
			alien.advanceTime(0.19);
		}
		
		alien.updateSpriteIndex();
		assertEquals(sprites[7], alien.getCurrentSprite());
	}
	
	@Test
	public void testWalkAnimationFirstFrameRight() {
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(0, 0, sprites);

		alien.startMove(Direction.RIGHT);

		alien.advanceTime(0.005);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[8], alien.getCurrentSprite());
	}
	
	@Test
	public void testWalkAnimationSecondFrameRight() {
		Mazub alien = mazubPos_0_0;

		alien.startMove(Direction.RIGHT);

		alien.advanceTime(0.080);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[9], alien.getCurrentSprite());
	}
	
	@Test
	public void testWalkAnimationFirstFrameLeft() {
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(200, 0, sprites);

		alien.startMove(Direction.LEFT);

		alien.advanceTime(0.005);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[9+m], alien.getCurrentSprite());
	}
	
	@Test
	public void testWalkAnimationSecondFrameLeft() {
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(200, 0, sprites);

		alien.startMove(Direction.LEFT);

		alien.advanceTime(0.080);
		
		alien.updateSpriteIndex();
		assertEquals(sprites[9+m+1], alien.getCurrentSprite());
	}
	
	@Test
	public void getCurrentSprite0CorrectWasMovingLongTimeAgo(){
		mazubPos_0_0.startMove(Direction.RIGHT);
		mazubPos_0_0.advanceTime(0.15);
		mazubPos_0_0.endMove(Direction.RIGHT);
		
		for (int i = 0; i < 6; i++) {
			mazubPos_0_0.advanceTime(0.19);
		}
		
		mazubPos_0_0.updateSpriteIndex();
		assertEquals(sprites[0], mazubPos_0_0.getCurrentSprite());
	}
	
}
	
	