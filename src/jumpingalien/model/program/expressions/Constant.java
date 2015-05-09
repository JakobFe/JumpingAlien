package jumpingalien.model.program.expressions;

import jumpingalien.part3.programs.SourceLocation;

public class Constant<T> extends Expression {

	public Constant(SourceLocation sourceLocation, T value){
		super(sourceLocation);
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}

	private final T value;
	
	@Override
	public T outcome() {
		return getValue();
	}

}
