package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
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
		theTile = new Tile(testWorld, 0, 0, false);
		theTile.setGeoFeature(Terrain.WATER);
		
		theSlimeVariable = new Variable(loc, "slimeVariable",
									new jumpingalien.model.program.types.ObjectOfWorld());
		theSlimeVariable.setValue(theSlime);
		
		theMazubVariable = new Variable(loc, "mazubVariable",
				new jumpingalien.model.program.types.ObjectOfWorld());
		theMazubVariable.setValue(theMazub);
		
		theTileVariable = new Variable(loc, "tileVariable", 
								new jumpingalien.model.program.types.ObjectOfWorld());
		theTileVariable.setValue(theTile);
	}
	
	private SourceLocation loc;
	private World testWorld;
	private Slime theSlime;
	private Mazub theMazub;
	private Tile theTile;
	private Variable theSlimeVariable;
	private Variable theMazubVariable;
	private Variable theTileVariable;
	
	@Test
	public void createDoubleConstantCorrect(){
		Expression doubleConstant = new Constant<Double>(loc,4.0);
		assertEquals(4.0, doubleConstant.outcome());
	}

	@Test
	public void createBooleanConstantCorrect(){
		Expression booleanConstant = new Constant<Boolean>(loc,true);
		assertEquals(true, booleanConstant.outcome());
	}

	@Test
	public void executeAdditionCorrect(){
		Addition ad = new Addition(loc, 
									new Constant<Double>(loc,8.0),
									new Constant<Double>(loc,3.0));
		assertEquals(11.0, ad.outcome());
	}

	@Test
	public void executeMultiplicationCorrect(){
		Multiplication mult = new Multiplication(loc, 
												new Constant<Double>(loc,8.0),
												new Constant<Double>(loc,3.0));
		assertEquals(24.0, mult.outcome());
	}

	@Test
	public void executeSubtractionCorrect(){
		Subtraction sub = new Subtraction(loc,
										new Constant<Double>(loc,8.0), 
										new Constant<Double>(loc,3.0));
		assertEquals(5.0, sub.outcome());
	}

	@Test
	public void executeDivisionCorrect(){
		Division div = new Division(loc,
									new Constant<Double>(loc,8.0), 
									new Constant<Double>(loc,3.0));
		double expected = 8.0/3.0;
		assertEquals(expected, div.outcome());
	}

	@Test
	public void executeSquareRootCorrect(){
		SquareRoot sqrt = new SquareRoot(loc, new Constant<Double>(loc,8.0));
		double expected = Math.sqrt(8.0);
		assertEquals(expected, sqrt.outcome());
	}

	@Test
	public void executeAndCorrect(){
		ConditionalAnd and = new ConditionalAnd(loc,
												new Constant<Boolean>(loc,true), 
												new Constant<Boolean>(loc,false));
		assertEquals(false, and.outcome());
	}

	@Test
	public void executeOrCorrect(){
		ConditionalOr or = new ConditionalOr(loc,
											new Constant<Boolean>(loc,true), 
											new Constant<Boolean>(loc,false));
		assertEquals(true, or.outcome());
	}

	@Test
	public void executeIsGameObjectCorrect1(){
		IsGameObject<Slime> isSlime = new IsGameObject<Slime>(loc, theSlimeVariable, Slime.class);
		assertEquals(true, isSlime.outcome());
	}
	
	@Test
	public void executeIsGameObjectIncorrect1(){
		IsGameObject<Slime> isNotSlime = new IsGameObject<Slime>(loc, theMazubVariable, Slime.class);
		assertEquals(false, isNotSlime.outcome());
	}

	@Test
	public void executeIsGameObjectCorrect2(){
		IsGameObject<Mazub> isMazub = new IsGameObject<Mazub>(loc, theMazubVariable, Mazub.class);
		assertEquals(true, isMazub.outcome());
	}
	
	@Test
	public void executeIsGameObjectIncorrect2(){
		IsGameObject<Mazub> isNotMazub = new IsGameObject<Mazub>(loc, theSlimeVariable, Mazub.class);
		assertEquals(false, isNotMazub.outcome());
	}
	
	@Test
	public void executeIsTerrainOfTypeCorrect(){
		IsTerrainOfType isWater = new IsTerrainOfType(loc, theTileVariable, Terrain.WATER);
		assertEquals(true, isWater.outcome());
	}

	@Test
	public void executeIsTerrainOfTypeIncorrect(){
		IsTerrainOfType isNotWater = new IsTerrainOfType(loc, theTileVariable, Terrain.MAGMA);
		assertEquals(false, isNotWater.outcome());
	}
	
	@Test
	public void executeIsTerrainCorrect(){
		IsTerrain isTerrain = new IsTerrain(loc, theTileVariable);
		assertEquals(true, isTerrain.outcome());
	}
	
	@Test
	public void executeIsTerrainIncorrect(){
		IsTerrain isNotTerrain = new IsTerrain(loc, theMazubVariable);
		assertEquals(false, isNotTerrain.outcome());
	}
}
