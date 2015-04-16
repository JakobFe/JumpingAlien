package jumpingalien.model.gameobjects;

import java.util.HashSet;

public class School {
	
	public School(){}
	
	int getNbSlimes(){
		return slimes.size();
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
}
