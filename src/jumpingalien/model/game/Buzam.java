package jumpingalien.model.game;

import jumpingalien.model.program.programs.Program;
import jumpingalien.util.Sprite;

public class Buzam extends Alien{

	public Buzam(Position position, Sprite[] sprites) 
			throws IllegalArgumentException{
		super(position,sprites,null);
		setHitPoints(500);
	}

	public Buzam(Position position, Sprite[] sprites, Program program) 
			throws IllegalArgumentException{
		super(position,sprites,program);
		setHitPoints(500);
	}
	
	public boolean canBeAddedTo(World world){
		return super.canBeAddedTo(world) && (world == null || world.getBuzam() == this);
	}
	
	@Override
	protected boolean hasProperWorld() {
		return getWorld() == null || getWorld().getBuzam() == this;
	}
	
	@Override
	protected void updateHitPoints(){
		Mazub alien = getWorld().getMazub();
		boolean isHurt = false;
		if(!isDead()){
			if (!isOverlappingWith(Terrain.WATER) && !isOverlappingWith(Terrain.MAGMA))
				getHpTimer().reset();
			if(isOverlappingWith(Terrain.WATER)){
				updateHitPointsTerrain(Terrain.WATER);
			}
			if(isOverlappingWith(Terrain.MAGMA)){
				updateHitPointsTerrain(Terrain.MAGMA);
			}		
			if (!isDead() && alien != null && !alien.isImmune() && isOverlappingWith(alien)){
				if (!alien.standsOn(this)){
					alien.getHurtBy(this);
				}
				if(!isImmune()){
					getHurtBy(alien);
					isHurt = true;
				}
			}
			for (Slime slime: getWorld().getAllUnterminatedSlimes()){
				if(!isDead() && isOverlappingWith(slime)){
					if(!isImmune()){
						getHurtBy(slime);
						isHurt = true;
					}
					if(!slime.isImmune())
						slime.getHurtBy(this);
				}
			}
			for (Shark shark: getWorld().getAllUnterminatedSharks()){
				if(!isDead() && isOverlappingWith(shark)){
					if(!isImmune()){
						getHurtBy(shark);
						isHurt = true;
					}
					if(!shark.isImmune())
						shark.getHurtBy(this);
				}
			}
		}
		if(isHurt)
			getImmuneTimer().reset();
		if (isHurt && isDead())
			getHpTimer().reset();
		else if (isDead() && getHpTimer().getTimeSum()>= 0.6){
			terminate();
		}
	}

	@Override
	protected void getHurtBy(GameObject other){
		if(!isImmune()){
			if((other instanceof Mazub) || (other instanceof Shark) || (other instanceof Slime))
				subtractHp(50);
			else if(!(other instanceof Buzam))
				other.hurt(this);
		}
	}

	protected void hurt(GameObject other){
		if(other instanceof Mazub && !((Mazub) other).isImmune() &&
				!((Mazub) other).standsOn(this)){
			((Mazub) other).getImmuneTimer().reset();
			other.subtractHp(50);
			if (other.isDead())
				other.getHpTimer().reset();
		}	
		else if(((other instanceof Shark) || (other instanceof Slime)) && !((Character) other).isImmune()){
			other.subtractHp(50);
			if(other instanceof Slime)
				((Slime)other).getSchool().reduceHpAll((Slime)other);
			((Character)other).getImmuneTimer().reset();
			if (other.isDead())
				other.getHpTimer().reset();
		}
		else if(!(other instanceof Buzam))
			other.getHurtBy(this);
	}

	@Override
	public void terminate(){
		assert (getHitPoints()==0);
		assert getHpTimer().getTimeSum() > 0.6;
		setIsTerminated();
		getWorld().setBuzam(null);
		setWorld(null);
	}
	
	@Override
	public String toString(){
		//return "Buzam at position " + getPosition().toString();
		return String.valueOf(getProgram() != null);
	}

	@Override
	protected boolean canHaveProgram() {
		return true;
	}
	
}
