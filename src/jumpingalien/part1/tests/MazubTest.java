package jumpingalien.part1.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import jumpingalien.part1.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.model.Mazub;
import jumpingalien.util.*;

import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Test;


/**
 * This test case contains tests for each public method of the class Mazub,
 * excluding inspectors.
 * @author	Jakob Festraets, Vincent Kemps
 */ 

public class MazubTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * A variable referencing a Mazub at position(0,0), with 
	 * default settings for all other attributes.
	 */
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MazubPos_0_0 = new Mazub(0,0,1,3,spriteArrayForSize(2, 2, 30));
		MazubPos_50_0 = new Mazub(50,0,1,3,spriteArrayForSize(2, 2, 30));
		MazubPos_800_0 = new Mazub(800,0,1,3,spriteArrayForSize(2, 2, 30));
	}

	private Mazub MazubPos_0_0;
	private Mazub MazubPos_50_0;
	private Mazub MazubPos_800_0;
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	////////////////////////////////////////////////////////////////////////////
	// Tests for some public methods in the class Mazub.
	
	
	////////////////////////////////////////////////////////////////////////////
	// Tests for the class Facade.
	
	@Test
	public void createMazubCorrect(){
		IFacade facade = new Facade();
		Mazub alien = facade.createMazub(20,0,spriteArrayForSize(2, 2));
		assertArrayEquals(intArray(20,0),facade.getLocation(alien));
	}
	
	@Test(expected=ModelException.class)
	public void createMazubIllegalXPos(){
		IFacade facade = new Facade();
		facade.createMazub(-4,50,spriteArrayForSize(2, 2));
	}
	
	@Test(expected=ModelException.class)
	public void createMazubIllegalYPos(){
		IFacade facade = new Facade();
		facade.createMazub(40,999999,spriteArrayForSize(2, 2));
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void startMoveLeftCorrectLocation(){
		IFacade facade = new Facade();
		facade.startMoveLeft(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		// x_new [m] = 0.50 [m] - 1 [m/s] * 0.1 [s] - 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.3955 [m] = 39.55 [cm], which falls into pixel (39, 0)
		
		assertArrayEquals(intArray(39,0),facade.getLocation(MazubPos_50_0));
	}
	
	// Given test.
	@Test
	public void startMoveRightCorrect() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)
		
		assertArrayEquals(intArray(10, 0), facade.getLocation(alien));
	}
	
	@Test
	public void startJumpCorrectLocation(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		// y_new [m] = 0 + 8 [m/s] * 0.1 [s] - 1/2 10 [m/s^2] * (0.1 [s])^2 =
		// 0.75 [m] = 75 [cm], which falls into pixel (50,75)
		assertArrayEquals(intArray(50,75),facade.getLocation(MazubPos_50_0));
	}
	
	@Test
	public void combinedMovementCorrectLocation(){
		IFacade facade = new Facade();
		facade.startMoveLeft(MazubPos_50_0);
		facade.startJump(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		assertArrayEquals(intArray(39,75),facade.getLocation(MazubPos_50_0));
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void startMoveRightCorrectStartVelocity(){
		IFacade facade = new Facade();
		facade.startMoveRight(MazubPos_0_0);
		assertArrayEquals(doubleArray(1,0),facade.getVelocity(MazubPos_0_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightCorrectUpdateVelocity(){
		IFacade facade = new Facade();
		facade.startMoveRight(MazubPos_0_0);
		facade.advanceTime(MazubPos_0_0,0.1);
		// vx_new [m] = 1 [m/s] + 0.9 [m/s^2] * (0.1 [s]) = 1.09
		assertArrayEquals(doubleArray(1.09,0),facade.getVelocity(MazubPos_0_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftCorrectUpdateVelocity(){
		IFacade facade = new Facade();
		facade.startMoveLeft(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.15);
		// vx_new [m] = -1 [m/s] - 0.9 [m/s^2] * (0.15 [s]) = -1.135
		assertArrayEquals(doubleArray(-1.135,0),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
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
		IFacade facade = new Facade();
		facade.startMoveLeft(MazubPos_800_0);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 110; i++) {
			facade.advanceTime(MazubPos_800_0, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(-3, 0), facade.getVelocity(MazubPos_800_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endMoveRightCorrectVelocity(){
		IFacade facade = new Facade();
		facade.startMoveRight(MazubPos_0_0);
		for (int i = 0; i < 110; i++) {
			facade.advanceTime(MazubPos_0_0, 0.2 / 9);
		}
		facade.endMoveRight(MazubPos_0_0);
		assertArrayEquals(doubleArray(0,0),facade.getVelocity(MazubPos_0_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightThenStartDuckCorrectMaxVelocity(){
		IFacade facade = new Facade();
		facade.startMoveRight(MazubPos_0_0);
		for (int i = 0; i < 20; i++) {
			facade.advanceTime(MazubPos_0_0, 0.2 / 9);
		}
		facade.startDuck(MazubPos_0_0);
		facade.advanceTime(MazubPos_0_0, 0.15);
		// When ducking, max velocity is 1.
		assertArrayEquals(doubleArray(1,0),facade.getVelocity(MazubPos_0_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startDuckThenStartMoveLeftCorrectMaxVelocity(){
		IFacade facade = new Facade();
		facade.startDuck(MazubPos_50_0);
		facade.startMoveLeft(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0, 0.15);
		// When ducking, max velocity is 1.
		assertArrayEquals(doubleArray(-1,0),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startJumpCorrectVelocity(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		// v_new [m] = 8 [m/s]  -  10 [m/s^2] * (0.1 [s]) = 7 [m/s] 
		assertArrayEquals(doubleArray(0,7),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endJumpCorrectVelocity(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		facade.endJump(MazubPos_50_0); 
		assertArrayEquals(doubleArray(0,0),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	
	// Gegeven test.
	@Test
	public void testAccellerationZeroWhenNotMoving() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveRightCorrectStartAcceleration(){
		IFacade facade = new Facade();
		facade.startMoveRight(MazubPos_0_0);
		assertArrayEquals(doubleArray(0.9,0),facade.getAcceleration(MazubPos_0_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startMoveLeftAcceleartionZeroAfterMaxSpeedReached() {
		IFacade facade = new Facade();
		facade.startMoveLeft(MazubPos_800_0);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 110; i++) {
			facade.advanceTime(MazubPos_800_0, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(0, 0), facade.getAcceleration(MazubPos_800_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startJumpCorrectAcceleration(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		assertArrayEquals(doubleArray(0, -10), facade.getAcceleration(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
		}
	
	////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void startJumpDenied(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		facade.advanceTime(MazubPos_50_0,0.1);
		// v_new [m] = 8 [m/s]  -  10 [m/s^2] * (0.1 [s]) = 7 [m/s]
		facade.startJump(MazubPos_50_0);
		// You can't double jump.
		assertArrayEquals(doubleArray(0,7),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void startJumpFallingCorrect(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		for (int i=0 ; i<9; i++){
			facade.advanceTime(MazubPos_50_0,0.1);
		}
		// v_new [m] = 8 [m/s]  - 8 * ( 10 [m/s^2] * (0.1 [s])) = -1 [m/s]
		facade.startJump(MazubPos_50_0);
		
		assertArrayEquals(doubleArray(0,-1),facade.getVelocity(MazubPos_50_0),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void endJumpNoEffect(){
		IFacade facade = new Facade();
		facade.startJump(MazubPos_50_0);
		for (int i=0 ; i<9; i++){
			facade.advanceTime(MazubPos_50_0,0.1);
		}
		// v_new [m] = 8 [m/s]  - 8 * ( 10 [m/s^2] * (0.1 [s])) = -1 [m/s]
		facade.endJump(MazubPos_50_0);
		// Has no effect since this mazub is moving down0
		assertArrayEquals(doubleArray(0,-1),facade.getVelocity(MazubPos_50_0),
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
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(1000, 0, sprites);

		facade.startMoveLeft(alien);

		facade.advanceTime(alien, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(alien, 0.075);
		}

		assertEquals(sprites[9+2*m], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite1Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[1], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite1CorrectWasMovingLongAGo(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveRight(alien);
		
		for (int i = 0; i < 6; i++) {
			facade.advanceTime(alien, 0.19);
		}
		
		assertEquals(sprites[1], facade.getCurrentSprite(alien));
	}
	
	
	@Test
	public void getCurrentSprite2Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[2], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite3Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[3], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite4Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.startJump(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[4], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite5Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		facade.startJump(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[5], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite6Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[6], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite6CorrectWasMoving(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveRight(alien);
		
		for (int i = 0; i < 5; i++) {
			facade.advanceTime(alien, 0.19);
		}
		
		assertEquals(sprites[6], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite7Correct(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		
		assertEquals(sprites[7], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite7CorrectWasMoving(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(500, 0, sprites);
		
		facade.startMoveLeft(alien);
		facade.advanceTime(alien, 0.15);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveLeft(alien);
		
		for (int i = 0; i < 5; i++) {
			facade.advanceTime(alien, 0.19);
		}
		
		assertEquals(sprites[7], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testWalkAnimationFirstFrameRight() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien);

		facade.advanceTime(alien, 0.005);

		assertEquals(sprites[8], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testWalkAnimationSecondFrameRight() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien);

		facade.advanceTime(alien, 0.080);

		assertEquals(sprites[9], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testWalkAnimationFirstFrameLeft() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(200, 0, sprites);

		facade.startMoveLeft(alien);

		facade.advanceTime(alien, 0.005);

		assertEquals(sprites[9+m], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testWalkAnimationSecondFrameLeft() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(200, 0, sprites);

		facade.startMoveLeft(alien);

		facade.advanceTime(alien, 0.080);

		assertEquals(sprites[9+m+1], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void getCurrentSprite0CorrectWasMovingLongTimeAgo(){
		IFacade facade = new Facade();
		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.15);
		facade.endMoveRight(alien);
		
		for (int i = 0; i < 6; i++) {
			facade.advanceTime(alien, 0.19);
		}
		
		assertEquals(sprites[0], facade.getCurrentSprite(alien));
	}
	
}
	
	