package jumpingalien.model.program.statements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jumpingalien.model.program.expressions.*;
import jumpingalien.part3.programs.SourceLocation;

public class Experiment {
	SourceLocation loc = new SourceLocation(2, 3);

	ComposedStatement stat = new IfStatement(new Constant<Boolean>(loc,true),
			new Print(new Constant<Double>(loc,8.0), loc), null, loc);
	
	List<Statement> substat = stat.getSubStatements();
	
	Stream<Statement> filtered = substat.stream().filter(s -> s != null);
	
	List<Statement> list = filtered.collect(Collectors.toList());

}
