package jumpingalien.model;

public class Moving  {
	public boolean isDucking(){
		return (getMaxHorVelocity() == 1);
	}
	public boolean isMoving(){
		return (getHorDirection() != 0);
	}
	public boolean isMovingRight(){
		return (getHorDirection() == 1);
	}
	public boolean isMovingLeft(){
		return (getHorDirection() == -1);
	}
	public boolean wasMovingLeft(){
		return false;
	}
	public boolean wasMovingRight(){
		return false;
	}
	public boolean isJumping(){
		return (getVertDirection() != 0);
	}
	
}
