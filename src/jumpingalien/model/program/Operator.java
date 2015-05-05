package jumpingalien.model.program;


public abstract class Operator extends Expression {
	
	public abstract Expression getOperandAt(int index) throws IndexOutOfBoundsException;
	
	public abstract int getNbOperands();
	
	public abstract boolean canHaveAsOperandAt(int index, Expression expression);
	
	public abstract String getOperatorSymbol();

}
