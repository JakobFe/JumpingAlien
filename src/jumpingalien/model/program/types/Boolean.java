package jumpingalien.model.program.types;

public class Boolean extends Type {

	public Boolean(boolean value){
		this.value = value;
	}
	
	public Boolean(){
		this(false);
	}
	
	public java.lang.Boolean getValue(){
		return value;
	}
	
	private final boolean value;
	
	@Override
	public String toString() {
		return "Types.Boolean";
	}
}
