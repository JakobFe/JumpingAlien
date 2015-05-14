package jumpingalien.model.program.statements;

import java.util.Iterator;

public interface StatementIterator<T> extends Iterator<T> {
	
	void restart();
	
	int getIndex();
	
	void incrementIndex();
}
