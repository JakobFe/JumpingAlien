package jumpingalien.model.program;

import jumpingalien.part3.programs.Addition;
import jumpingalien.part3.programs.SourceLocation;

public class Experiment {

	public static void main(String[] args) {
		SourceLocation loc = new SourceLocation(2, 3);
		Addition<Double> ad = new Addition<Double>(loc, new Double(8), new Double(3));
		System.out.println(ad.outcome());
		Multiplication<Double> mult = new Multiplication<Double>(loc, new Double(8), new Double(3));
		System.out.println(mult.outcome());
	}
	
}
