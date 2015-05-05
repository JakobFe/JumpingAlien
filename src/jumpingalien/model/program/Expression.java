package jumpingalien.model.program;

public abstract class Expression {
	
	public abstract Object outcome();
	
	public boolean hasSameOutcomeAs(Object other) {
		if(!(other instanceof Expression))
			return false;
		else
			return this.outcome() == ((Expression) other).outcome();
	}
	
	

}
