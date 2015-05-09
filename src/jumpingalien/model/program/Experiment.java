package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Experiment {

	public static void main(String[] args) {
		SourceLocation loc = new SourceLocation(2, 3);
		
		Addition ad = new Addition(loc, new Double(8), new Double(3));
		System.out.print("Expected: 11");
		System.out.println(ad.outcome());
		
		Multiplication mult = new Multiplication(loc, new Double(8), new Double(3));
		System.out.print("Expected: 24");
		System.out.println(mult.outcome());
		
		
		
		
		ConditionalAnd and = new ConditionalAnd(loc, true, false);
		System.out.print("Expected: false ");
		System.out.println(and.outcome());

		ConditionalOr or = new ConditionalOr(loc, true, false);
		System.out.print("Expected: true ");
		System.out.println(or.outcome());
	}
	
}
