package jumpingalien.model.program.types;

public class Double extends Type {
	
	public Double(double value){
		this.value = value;
	}
	
	public Double(){
		this(0);
	}
	
	public java.lang.Double getValue(){
		return value;
	}
	
	private final double value;
}
