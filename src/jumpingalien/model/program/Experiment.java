package jumpingalien.model.program;

import jumpingalien.part3.programs.SourceLocation;

public class Experiment {

	public static void main(String[] args) {
		SourceLocation loc = new SourceLocation(2, 3);
		
		Addition ad = new Addition(loc, new Double(8), new Double(3));
		System.out.println("Expected: 11.0");
		System.out.println(ad.outcome());
		System.out.println();
		
		Multiplication mult = new Multiplication(loc, new Double(8), new Double(3));
		System.out.println("Expected: 24.0");
		System.out.println(mult.outcome());
		System.out.println();
		
		Subtraction sub = new Subtraction(loc, new Double(8), new Double(3));
		System.out.println("Expected: 5.0");
		System.out.println(sub.outcome());
		System.out.println();
		
		Division div = new Division(loc, new Double(8), new Double(3));
		System.out.println("Expected: 2.666");
		System.out.println(div.outcome());
		System.out.println();
	}
	
}
