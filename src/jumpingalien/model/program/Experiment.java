package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Experiment {

	public static void main(String[] args) {
		SourceLocation loc = new SourceLocation(2, 3);
		
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
		
		ConditionalAnd and = new ConditionalAnd(loc, true, false);
		System.out.print("Expected: false -> Received: ");
		System.out.println(and.outcome());

		ConditionalOr or = new ConditionalOr(loc, true, false);
		System.out.print("Expected: true -> Received: ");
		System.out.println(or.outcome());
	}
	
}
