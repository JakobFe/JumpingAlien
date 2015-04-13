package jumpingalien.part2.internal;

import java.util.Collection;
import java.util.Optional;

import jumpingalien.common.sprites.ImageSprite;
import jumpingalien.model.gameobjects.Plant;
import jumpingalien.model.gameobjects.School;
import jumpingalien.model.gameobjects.Shark;
import jumpingalien.model.gameobjects.Slime;

public interface ObjectInfoProvider {

	public Collection<Slime> getSlimes();

	public Collection<Shark> getSharks();

	public Collection<Plant> getPlants();

	public Optional<int[]> getLocation(Plant plant);

	public Optional<int[]> getLocation(Shark shark);

	public Optional<int[]> getLocation(Slime slime);

	public Optional<ImageSprite> getCurrentSprite(Plant plant);

	public Optional<ImageSprite> getCurrentSprite(Shark shark);

	public Optional<ImageSprite> getCurrentSprite(Slime slime);

	public Optional<School> getSchool(Slime slime);

}
