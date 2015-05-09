package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.*;
import jumpingalien.model.game.*;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;

public class Experiment {

	public static void main(String[] args) {
		SourceLocation loc = new SourceLocation(2, 3);
		Slime theSlime = new Slime(new Position(4,5),spriteArrayForSize(5, 5),new School());
		Mazub theMazub = new Mazub(new Position(8,6),spriteArrayForSize(4, 4));
		
		Addition ad = new Addition(loc, new Double(8), new Double(3));
		System.out.print("Expected: 11.0 -> Received: ");
		System.out.println(ad.outcome());
		
		Multiplication mult = new Multiplication(loc, new Double(8), new Double(3));
		System.out.print("Expected: 24.0 -> Received: ");
		System.out.println(mult.outcome());
		
		Subtraction sub = new Subtraction(loc, new Double(8), new Double(3));
		System.out.print("Expected: 5.0 -> Received: ");
		System.out.println(sub.outcome());
		
		Division div = new Division(loc, new Double(8), new Double(3));
		System.out.print("Expected: 2.666 -> Received: ");
		System.out.println(div.outcome());
		
		SquareRoot sqrt = new SquareRoot(loc, new Double(8));
		System.out.print("Expected: 2.8284 -> Received: ");
		System.out.println(sqrt.outcome());
		
		///////////////////////////////////////////////////////////////////////
		
		ConditionalAnd and = new ConditionalAnd(loc, true, false);
		System.out.print("Expected: false -> Received: ");
		System.out.println(and.outcome());

		ConditionalOr or = new ConditionalOr(loc, true, false);
		System.out.print("Expected: true -> Received: ");
		System.out.println(or.outcome());
		
		///////////////////////////////////////////////////////////////////////
		
		isGameObject<Slime> isSlime = new isGameObject<Slime>(loc, theSlime, Slime.class);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isSlime.outcome());

		isGameObject<Slime> isSlime2 = new isGameObject<Slime>(loc, theMazub, Slime.class);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isSlime2.outcome());
		
		isGameObject<Mazub> isMazub = new isGameObject<Mazub>(loc, theSlime, Mazub.class);
		System.out.print("Expected: false -> Received: ");
		System.out.println(isMazub.outcome());

		isGameObject<Mazub> isMazub2 = new isGameObject<Mazub>(loc, theMazub, Mazub.class);
		System.out.print("Expected: true -> Received: ");
		System.out.println(isMazub2.outcome());
	}
	
}
