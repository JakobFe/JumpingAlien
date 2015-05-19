package jumpingalien.model.program.expressions;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import jumpingalien.model.game.Mazub;
import jumpingalien.model.game.Position;
import jumpingalien.model.game.School;
import jumpingalien.model.game.Slime;
import jumpingalien.model.program.expressions.binaryexpression.*;
import jumpingalien.model.program.expressions.unaryexpression.*;
import jumpingalien.part3.programs.SourceLocation;

public class Experiment {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		SourceLocation loc = new SourceLocation(2, 3);
		Slime theSlime = new Slime(new Position(4,5),spriteArrayForSize(5, 5),new School());
		Mazub theMazub = new Mazub(new Position(8,6),spriteArrayForSize(4, 4));
		
		///////////////////////////////////////////////////////////////////////
		
		Expression doubleConstant = new Constant<Double>(loc,4.0);
		System.out.print("Expected: 4.0 -> Received: ");
		System.out.println(doubleConstant.outcome());
		
		Expression booleanConstant = new Constant<Boolean>(loc,true);
		System.out.print("Expected: true -> Received: ");
		System.out.println(booleanConstant.outcome());
		
		
		///////////////////////////////////////////////////////////////////////
		
		Addition ad = new Addition(loc, new Constant<Double>(loc,8.0), new Constant<Double>(loc,3.0));
		System.out.print("Expected: 11.0 -> Received: ");
		System.out.println(ad.outcome());
		
		Multiplication mult = new Multiplication(loc, new Constant<Double>(loc,8.0),new Constant<Double>(loc,3.0));
		System.out.print("Expected: 24.0 -> Received: ");
		System.out.println(mult.outcome());
		
		Subtraction sub = new Subtraction(loc, new Constant<Double>(loc,8.0), new Constant<Double>(loc,3.0));
		System.out.print("Expected: 5.0 -> Received: ");
		System.out.println(sub.outcome());
		
		Division div = new Division(loc, new Constant<Double>(loc,8.0), new Constant<Double>(loc,3.0));
		System.out.print("Expected: 2.666 -> Received: ");
		System.out.println(div.outcome());
		
		SquareRoot sqrt = new SquareRoot(loc, new Constant<Double>(loc,8.0));
		System.out.print("Expected: 2.8284 -> Received: ");
		System.out.println(sqrt.outcome());
		
		///////////////////////////////////////////////////////////////////////
		
		ConditionalAnd and = new ConditionalAnd(loc, new Constant<Boolean>(loc,true), 
				new Constant<Boolean>(loc,false));
		System.out.print("Expected: false -> Received: ");
		System.out.println(and.outcome());

		ConditionalOr or = new ConditionalOr(loc, new Constant<Boolean>(loc,true), 
				new Constant<Boolean>(loc,false));
		System.out.print("Expected: true -> Received: ");
		System.out.println(or.outcome());
		
		///////////////////////////////////////////////////////////////////////
		
		IsGameObject<Slime> isSlime = new IsGameObject<Slime>(loc, theSlime, Slime.class);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isSlime.outcome());
		
		IsGameObject<Slime> isSlime2 = new IsGameObject<Slime>(loc, theMazub, Slime.class);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isSlime2.outcome());
		
		IsGameObject<Mazub> isMazub = new IsGameObject<Mazub>(loc, theSlime, Mazub.class);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isMazub.outcome());

		IsGameObject<Mazub> isMazub2 = new IsGameObject<Mazub>(loc, theMazub, Mazub.class);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isMazub2.outcome());
		
		IsTerrainOfType isWater = new IsTerrainOfType(loc, Terrain.WATER, Terrain.WATER);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isWater.outcome());
		
		IsTerrainOfType isWater2 = new IsTerrainOfType(loc, Terrain.WATER, Terrain.MAGMA);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isWater2.outcome());

		IsTerrainOfType isMagma = new IsTerrainOfType(loc, Terrain.MAGMA, Terrain.MAGMA);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isMagma.outcome());

		IsTerrainOfType isAir = new IsTerrainOfType(loc, Terrain.AIR, Terrain.AIR);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isAir.outcome());

		IsTerrain isTerrain = new IsTerrain(loc, Terrain.GROUND);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isTerrain.outcome());
		
		IsTerrain isTerrain2 = new IsTerrain(loc, theSlime);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isTerrain2.outcome());
		
	}
	
}
