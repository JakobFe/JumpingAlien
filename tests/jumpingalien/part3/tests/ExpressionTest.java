package jumpingalien.part3.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.model.game.School;
import jumpingalien.model.game.Slime;
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
		theSlime = new Slime(new Position(4,5),spriteArrayForSize(5, 5),new School());
		theMazub = new Mazub(new Position(8,6),spriteArrayForSize(4, 4));
		theSlimeConstant = new Variable(loc, "slimeConstant",
									new jumpingalien.model.program.types.ObjectOfWorld());
		theSlimeConstant.setValue(theSlime);
		theMazubConstant = new Variable(loc, "mazubConstant",
				new jumpingalien.model.program.types.ObjectOfWorld());
		theMazubConstant.setValue(theMazub);
	}
	
	private SourceLocation loc;
	private Slime theSlime;
	private Mazub theMazub;
	private Variable theSlimeConstant;
	private Variable theMazubConstant;
	
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
	public void executeIsGameObjectCorrect(){
		IsGameObject<Slime> isSlime = new IsGameObject<Slime>(loc, theSlimeConstant, Slime.class);
		assertEquals(true, isSlime.outcome());
	}
}
