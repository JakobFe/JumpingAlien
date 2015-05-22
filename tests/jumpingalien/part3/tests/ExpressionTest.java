package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;

import java.util.HashMap;

import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.model.game.School;
import jumpingalien.model.game.Slime;
import jumpingalien.model.game.Terrain;
import jumpingalien.model.game.Tile;
import jumpingalien.model.game.World;
import jumpingalien.model.program.expressions.*;
import jumpingalien.model.program.expressions.binaryexpression.*;
import jumpingalien.model.program.expressions.unaryexpression.*;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.statements.Print;
import jumpingalien.model.program.statements.Statement;
import jumpingalien.model.program.statements.While;
import jumpingalien.model.program.types.Type;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTest {
	
	@Before
	public void setUp() throws Exception {
		
		loc = new SourceLocation(2, 3);

		testWorld = new World(25, 40, 20, 1000, 1000, 0, 0);
		theSlime = new Slime(new Position(4,5),spriteArrayForSize(5, 5),new School());
		theMazub = new Mazub(new Position(8,6),spriteArrayForSize(4, 4));
		theTile = testWorld.getTileAtPos(0, 0);
		theTile.setGeoFeature(Terrain.WATER);
		
		testWorld.addAsGameObject(theSlime);
		testWorld.setMazub(theMazub);
		
		allVariables = new HashMap<String,Type>();
		theSlimeVariable = new Variable(loc, "slimeVariable",
									new jumpingalien.model.program.types.ObjectOfWorld());
		theSlimeVariable.setValue(theSlime);
		allVariables.put(theSlimeVariable.getName(),theSlimeVariable.getType());
		
		theMazubVariable = new Variable(loc, "mazubVariable",
				new jumpingalien.model.program.types.ObjectOfWorld());
		theMazubVariable.setValue(theMazub);
		allVariables.put(theMazubVariable.getName(), theMazubVariable.getType());
		
		theTileVariable = new Variable(loc, "tileVariable", 
								new jumpingalien.model.program.types.ObjectOfWorld());
		theTileVariable.setValue(theTile);
		allVariables.put(theTileVariable.getName(), theTileVariable.getType());
		
		printStatement = new Print(theMazubVariable, loc);
		main = new While(new Constant<Boolean>(loc, true),printStatement,loc);
		theProgram = new Program(main, allVariables);
		theProgram.getGlobalVariables().put(theSlimeVariable.getName(), theSlimeVariable);
		theProgram.getGlobalVariables().put(theMazubVariable.getName(), theMazubVariable);
		theProgram.getGlobalVariables().put(theTileVariable.getName(), theTileVariable);
		theProgram.setGameObject(theMazub);
		
		double8 = new Constant<Double>(loc, 8.0);
		double3 = new Constant<Double>(loc, 3.0);
		double5_4 = new Constant<Double>(loc, 5.4);
		
		trueConst = new Constant<Boolean>(loc,true);
		falseConst = new Constant<Boolean>(loc,false);
	}
	
	private SourceLocation loc;
	private World testWorld;
	private Slime theSlime;
	private Mazub theMazub;
	private Tile theTile;
	private Variable theSlimeVariable;
	private Variable theMazubVariable;
	private Variable theTileVariable;
	private HashMap<String,Type> allVariables;
	private Statement printStatement;
	private Statement main;
	private Program theProgram;
	private Expression double8;
	private Expression double3;
	private Expression double5_4;
	private Expression trueConst;
	private Expression falseConst;
	
	@Test
	public void createReadVariableSingleCase(){
		Expression readVar = new ReadVariable(loc, theSlimeVariable.getName(),theSlimeVariable.getType());
		readVar.setProgram(theProgram);
		assertEquals(theSlimeVariable.getValue(), readVar.outcome());
	}
	
	@Test
	public void createDoubleConstantSingleCase(){
		Expression doubleConstant = new Constant<Double>(loc,4.0);
		assertEquals(4.0, doubleConstant.outcome());
	}

	@Test
	public void createTrueSingleCase(){
		Expression booleanConstant = new Constant<Boolean>(loc,true);
		assertTrue((Boolean)booleanConstant.outcome());
	}
	
	@Test
	public void createFalseSingleCase(){
		Expression booleanConstant = new Constant<Boolean>(loc,false);
		assertFalse((Boolean)booleanConstant.outcome());
	}

	@Test
	public void createNullSingleCase(){
		Expression nothing = new Constant<Object>(loc,null);
		assertEquals(null, nothing.outcome());
	}
	
	@Test
	public void createSelfSingleCase(){
		Expression self = new Self(loc);
		self.setProgram(theProgram);
		assertEquals(theMazub,self.outcome());
	}
	
	@Test
	public void createDirectionConstantSingleCase(){
		Expression left = new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
			(loc, jumpingalien.part3.programs.IProgramFactory.Direction.LEFT);
		assertEquals(jumpingalien.part3.programs.IProgramFactory.Direction.LEFT, left.outcome());
	}
	
	@Test
	public void executeAdditionSingleCase(){
		Addition ad = new Addition(loc, double8,double3);
		assertEquals(11.0, ad.outcome());
	}
	
	@Test
	public void executeSubtractionSingleCase(){
		Subtraction sub = new Subtraction(loc,double8,double3);
		assertEquals(5.0, sub.outcome());
	}

	@Test
	public void executeMultiplicationSingleCase(){
		Multiplication mult = new Multiplication(loc, double8,double3);
		assertEquals(24.0, mult.outcome());
	}

	@Test
	public void executeDivisionSingleCase(){
		Division div = new Division(loc, double8,double3);
		double expected = 8.0/3.0;
		assertEquals(expected, div.outcome());
	}

	@Test
	public void executeSquareRootSingleCase(){
		SquareRoot sqrt = new SquareRoot(loc, double8);
		double expected = Math.sqrt(8.0);
		assertEquals(expected, sqrt.outcome());
	}
	
	@Test
	public void createRandomDoubleSingleCase(){
		Expression rand = new RandomDouble(loc, double3);
		assertTrue(((Double)rand.outcome()>=0.0) && ((Double)rand.outcome() <= 3.0));
	}

	@Test
	public void executeAndCorrect(){
		ConditionalAnd and = new ConditionalAnd(loc,trueConst,trueConst);
		assertTrue(and.outcome());
	}
	
	@Test
	public void executeAndInCorrect(){
		ConditionalAnd and = new ConditionalAnd(loc,trueConst,falseConst);
		assertFalse(and.outcome());
	}

	@Test
	public void executeOrCorrect(){
		ConditionalOr or = new ConditionalOr(loc,falseConst,trueConst);
		assertTrue(or.outcome());
	}
	
	@Test
	public void executeOrInCorrect(){
		ConditionalOr or = new ConditionalOr(loc,falseConst,falseConst);
		assertFalse(or.outcome());
	}
	
	@Test
	public void executeNotSingleCase(){
		Expression not = new NotBoolean(loc, trueConst);
		assertFalse((Boolean) not.outcome());
	}
	
	@Test
	public void executeLessThanCorrect(){
		Expression lessThan = new LessThan(loc,double5_4,double8);
		assertTrue((Boolean)lessThan.outcome());
	}
	
	@Test
	public void executeLessThanInCorrect(){
		Expression lessThan = new LessThan(loc,double5_4,double3);
		assertFalse((Boolean)lessThan.outcome());
	}
	
	@Test
	public void executeLessThanOrEqualToCorrect1(){
		Expression lessThanOrEqual = new LessThanOrEqualTo(loc,double3,double8);
		assertTrue((Boolean)lessThanOrEqual.outcome());
	}
	
	@Test
	public void executeLessThanOrEqualToCorrect2(){
		Expression lessThanOrEqual = new LessThanOrEqualTo(loc,double8,double8);
		assertTrue((Boolean)lessThanOrEqual.outcome());
	}
	
	@Test
	public void executeLessThanOrEqualToInCorrect(){
		Expression lessThanOrEqual = new LessThanOrEqualTo(loc,double8,double3);
		assertFalse((Boolean)lessThanOrEqual.outcome());
	}

	@Test
	public void greaterThanCorrect(){
		Expression t = new GreaterThan(loc, double5_4, double3);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void greaterThanIncorrect(){
		Expression t = new GreaterThan(loc, double5_4, double8);
		assertFalse((Boolean)t.outcome());
	}
	
	@Test
	public void executeGreaterThanOrEqualToCorrect1(){
		Expression t = new GreaterThanOrEqualTo(loc,double8,double3);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void executeGreaterThanOrEqualToCorrect2(){
		Expression t = new GreaterThanOrEqualTo(loc,double3,double3);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void executeGreaterThanOrEqualToIncorrect(){
		Expression t = new GreaterThanOrEqualTo(loc,double3,double5_4);
		assertFalse((Boolean)t.outcome());
	}
	
	@Test
	public void executeEqualsCorrect(){
		Expression t = new Equals(loc, double5_4, double5_4);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void executeEqualsIncorrect(){
		Expression t = new Equals(loc, double5_4, double3);
		assertFalse((Boolean)t.outcome());
	}
	
	@Test
	public void executeNotEqualsCorrect(){
		Expression t = new NotEquals(loc, double8, double5_4);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void executeNotEqualsInCorrect(){
		Expression t = new NotEquals(loc, double5_4, double5_4);
		assertFalse((Boolean)t.outcome());
	}
	
	@Test
	public void executeGetXSingleCase(){
		Expression t = new GetX(loc, theMazubVariable);
		assertEquals(8.0, t.outcome());
	}
	
	@Test
	public void executeGetYSingleCase(){
		Expression t = new GetX(loc, theSlimeVariable);
		assertEquals(4.0, t.outcome());
	}
	
	@Test
	public void getWidthSingleCase(){
		Expression t = new GetWidth(loc, theMazubVariable);
		assertEquals(4, t.outcome());
	}
	
	@Test
	public void getHeightSingleCase(){
		Expression t = new GetHeight(loc, theSlimeVariable);
		assertEquals(5, t.outcome());
	}
	
	@Test
	public void getHitPointsSingleCase(){
		Expression t = new GetHp(loc, theMazubVariable);
		assertEquals(100, t.outcome());
	}
	
	@Test
	public void getTileSingleCase(){
		Expression t = new GetTile(loc, new Constant<Double>(loc,0.59), new Constant<Double>(loc,1.0));
		t.setProgram(theProgram);
		assertEquals(theTile, t.outcome());
	}
	
	// Tests for SearchObject: see class SearchObjectTest
	
	@Test
	public void executeIsGameObjectCorrect1(){
		IsGameObject<Slime> isSlime = new IsGameObject<Slime>(loc, theSlimeVariable, Slime.class);
		assertTrue(isSlime.outcome());
	}
	
	@Test
	public void executeIsGameObjectIncorrect1(){
		IsGameObject<Slime> isNotSlime = new IsGameObject<Slime>(loc, theMazubVariable, Slime.class);
		assertFalse( isNotSlime.outcome());
	}

	@Test
	public void executeIsGameObjectCorrect2(){
		IsGameObject<Mazub> isMazub = new IsGameObject<Mazub>(loc, theMazubVariable, Mazub.class);
		assertTrue( isMazub.outcome());
	}
	
	@Test
	public void executeIsGameObjectIncorrect2(){
		IsGameObject<Mazub> isNotMazub = new IsGameObject<Mazub>(loc, theSlimeVariable, Mazub.class);
		assertFalse( isNotMazub.outcome());
	}
	
	@Test
	public void executeIsDeadCorrect(){
		Expression t = new IsDead(loc, theMazubVariable);
		World newWorld = new World(10, 5, 5, 100, 100, 3, 3);
		newWorld.setMazub(theMazub);
		newWorld.getTileAtPos(8, 6).setGeoFeature(Terrain.MAGMA);
		for(int i=0;i<41;i++)
			newWorld.advanceTime(0.01);
		assertTrue((Boolean)t.outcome());
	}
	
	@Test
	public void executeIsDeadIncorrect(){
		Expression t = new IsDead(loc, theMazubVariable);
		assertFalse((Boolean)t.outcome());
	}
	
	@Test
	public void executeIsPassableCorrect(){
		Expression isWater = new IsPassable(loc, theTileVariable);
		assertTrue((Boolean) isWater.outcome());
	}

	@Test
	public void executeIsIsPassableIncorrect(){
		Tile newTile = new Tile(testWorld, 5, 6, false);
		newTile.setGeoFeature(Terrain.GROUND);
		Expression isNotWater = new IsPassable(loc,new Constant<Tile>(loc,newTile));
		assertFalse((Boolean) isNotWater.outcome());
	}

	
	@Test
	public void executeIsTerrainOfTypeCorrect(){
		IsTerrainOfType isWater = new IsTerrainOfType(loc, theTileVariable, Terrain.WATER);
		assertTrue( isWater.outcome());
	}

	@Test
	public void executeIsTerrainOfTypeIncorrect(){
		IsTerrainOfType isNotWater = new IsTerrainOfType(loc, theTileVariable, Terrain.MAGMA);
		assertFalse( isNotWater.outcome());
	}
	
	@Test
	public void isMovingCorrect(){
		Expression t = new IsMoving(loc, theMazubVariable, 
				new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.LEFT));
		theMazub.startMove(jumpingalien.model.game.Direction.LEFT);
		assertTrue((Boolean) t.outcome());
	}
	
	@Test
	public void isMovingInCorrect(){
		Expression t = new IsMoving(loc, theMazubVariable, 
				new Constant<jumpingalien.part3.programs.IProgramFactory.Direction>
				(loc,jumpingalien.part3.programs.IProgramFactory.Direction.UP));
		theMazub.startMove(jumpingalien.model.game.Direction.RIGHT);
		assertFalse((Boolean) t.outcome());
	}
	
	@Test
	public void executeIsDuckingCorrect(){
		theMazub.startDuck();
		Expression t = new IsDucking(loc, theMazubVariable);
		assertTrue((Boolean) t.outcome());
	}
	
	@Test
	public void executeIsDuckingInCorrect(){
		theMazub.startJump();
		Expression t = new IsDucking(loc, theMazubVariable);
		assertFalse((Boolean) t.outcome());
	}
	
	@Test
	public void executeIsJumpingCorrect(){
		theMazub.startJump();
		Expression t = new IsJumping(loc, theMazubVariable);
		assertTrue((Boolean) t.outcome());
	}
	
	@Test
	public void executeIsJumpingInCorrect(){
		theMazub.startDuck();
		Expression t = new IsJumping(loc, theMazubVariable);
		assertFalse((Boolean) t.outcome());
	}
	
}
