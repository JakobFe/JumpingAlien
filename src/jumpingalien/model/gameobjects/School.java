package jumpingalien.model.gameobjects;

import java.util.HashSet;

public class School {
	
	public School(){}
	
	int getNbSlimes(){
		return slimes.size();
	}
	
	HashSet<Slime> getAllSlimes(){
		return slimes;
	}
	
	/**
	 * reduce all buiten deze slime
	 * @param slime
	 */
	void reduceHpAll(Slime other){
		for(Slime slime: slimes){
			if(slime != other)
				slime.setHitPoints(slime.getHitPoints()-1);
		}
	}
	
	/**
	 * add all buiten deze slime
	 * @param slime
	 */
	void addHpAll(Slime other){
		for(Slime slime: slimes){
			if(slime != other)
				slime.setHitPoints(slime.getHitPoints()+1);
		}
	}
	
	void addSlime(Slime slime){
		slimes.add(slime);
		slime.setSchool(this);
	}
	
	boolean hasAsSlime(Slime slime){
		return slimes.contains(slime);
	}
	
	void removeSlime(Slime slime){
		assert hasAsSlime(slime);
		slimes.remove(slime);
	}
	
	private HashSet<Slime> slimes = new HashSet<Slime>();
	
	@Override
	public String toString(){
		return " school " + hashCode()%5;
	}
}
