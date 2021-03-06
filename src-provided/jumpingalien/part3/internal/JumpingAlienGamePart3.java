package jumpingalien.part3.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.lang.Boolean;

import jumpingalien.common.game.IActionHandler;
import jumpingalien.common.game.JumpingAlienGame;
import jumpingalien.common.game.WorldInfoProvider;
import jumpingalien.common.sprites.ImageSprite;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.game.*;
import jumpingalien.part2.internal.AlienInfoProvider2;
import jumpingalien.part2.internal.ObjectInfoProvider;
import jumpingalien.part2.internal.Part2WorldInfoProvider;
import jumpingalien.part2.internal.tmxfile.TMXFileReader;
import jumpingalien.part2.internal.tmxfile.data.ImageTile;
import jumpingalien.part2.internal.tmxfile.data.ImageTile.TileType;
import jumpingalien.part2.internal.tmxfile.data.Layer;
import jumpingalien.part2.internal.tmxfile.data.Map;
import jumpingalien.part2.internal.tmxfile.data.MapObject;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.util.Sprite;
import ogp.framework.messages.Message;
import ogp.framework.messages.MessageType;
import ogp.framework.util.GUIUtils;

public class JumpingAlienGamePart3 extends JumpingAlienGame {

	private static final String ATTR_BUZAM_INITIAL_Y = "initialBuzamY";
	private static final String ATTR_BUZAM_INITIAL_X = "initialBuzamX";
	private Mazub alien;
	private Buzam buzam;
	private World world;

	private Map map;

	private int tileSize;
	private final ObjectInfoProvider objectInfoProvider;
	private String currentMap;

	private final Random random;

	public JumpingAlienGamePart3(Part3Options options, IFacadePart3 facade) {
		super(options, facade);
		this.random = new Random(options.getRandomSeed());
		this.objectInfoProvider = createObjectInfoProvider();
		this.evilTwinProvider = createEvilTwinInfoProvider();
	}

	@Override
	public void restart() {
		this.world = null;
		this.alien = null;

		super.restart();
	}

	@Override
	public IFacadePart3 getFacade() {
		return (IFacadePart3) super.getFacade();
	}

	@Override
	public Part3Options getOptions() {
		return (Part3Options) super.getOptions();
	}

	private boolean readLevelFile(String filename) {
		try {
			TMXFileReader reader = new TMXFileReader("levels/");

			map = reader.read(filename);

			if (map.getTileSizeY() != map.getTileSizeX()) {
				throw new IllegalArgumentException(
						"Can only work with square tile sizes");
			}

			if (map.getLayer("Terrain") == null) {
				throw new IllegalArgumentException(
						"The map must have a layer called 'Terrain'");
			}

			tileSize = map.getTileSizeY();
		} catch (Throwable e) {
			addMessage(new Message(MessageType.ERROR, "Error while reading "
					+ filename + ": " + e.getMessage()));
			return false;
		}

		return true;
	}

	@Override
	public void load() {
	}

	public int[] getWorldSize() {
		return new int[] { map.getPixelWidth(), map.getPixelHeight() };
	}

	@Override
	protected void createModel() {

		setWorld(getFacade().createWorld(tileSize, map.getNbTilesX(),
				map.getNbTilesY(), getVisibleScreenWidth(),
				getVisibleScreenHeight(), map.getTargetTileX(),
				map.getTargetTileY()));

		setTileTypes();

		addGameObjects();

		addBuzam();

		addMazub();

		// no more object creations or tile changes after starting
		getFacade().startGame(getWorld());
	}

	private void addGameObjects() {
		for (MapObject obj : map.getObjects()) {
			addObject(obj);
		}
	}

	private void addMazub() {
		setAlien(getFacade().createMazub(map.getInitialPositionX(),
				map.getInitialPositionY(), JumpingAlienSprites.ALIEN_SPRITESET));

		getFacade().setMazub(getWorld(), getAlien());
	}

	private void addBuzam() {
		if (!map.hasAttribute(ATTR_BUZAM_INITIAL_X) || !map.hasAttribute(ATTR_BUZAM_INITIAL_Y)) {
			return;
		}
		int buzamX = Integer.parseInt(map.getAttribute(ATTR_BUZAM_INITIAL_X));
		int buzamY = Integer.parseInt(map.getAttribute(ATTR_BUZAM_INITIAL_Y));
		Optional<Program> program = parseProgram(Resources.BUZAM_PROGRAM_FILENAME);
		if (program.isPresent() && random.nextBoolean()) {
			buzam = getFacade().createBuzamWithProgram(buzamX, buzamY,
					Resources.BUZAM_SPRITESET, program.get());
		} else {
			buzam = getFacade().createBuzam(buzamX, buzamY,
					Resources.BUZAM_SPRITESET);
		}

		getFacade().addBuzam(getWorld(), buzam);
	}

	@SuppressWarnings("unchecked")
	private Optional<Program> parseProgram(String filename) {
		try {
			InputStream is = GUIUtils.openResource(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String text = br.lines().collect(Collectors.joining("\n"));
			ParseOutcome<?> outcome = getFacade().parse(text);
			if (outcome.isSuccess()) {
				return Optional.ofNullable((Program) outcome.getResult());
			} else {
				System.out.println("Errors while parsing program '" + filename
						+ "':");
				for (String error : (List<String>) outcome.getResult()) {
					System.out.println(error);
				}
			}
		} catch (IOException e) {
			System.out.println("Error while opening '" + filename + "': "
					+ e.getMessage());
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private void setTileTypes() {
		Layer terrainLayer = map.getLayer("Terrain");

		for (int tileY = 0; tileY < map.getNbTilesY(); tileY++) {
			for (int tileX = 0; tileX < map.getNbTilesX(); tileX++) {
				ImageTile tile = terrainLayer.getTile(tileX, tileY);
				if (tile != null) {
					getFacade().setGeologicalFeature(getWorld(), tileX, tileY,
							tile.getType().getValue());
				} else {
					getFacade().setGeologicalFeature(getWorld(), tileX, tileY,
							TileType.AIR.getValue());
				}
			}
		}
	}

	private void addObject(MapObject obj) {
		switch (obj.getTile().getOSIndependentFilename()) {
		case Resources.PLANT_LEFT_FILENAME: {
			addPlant(obj);
			break;
		}
		case Resources.SHARK_LEFT_FILENAME:
		case Resources.SHARK_LEFT2_FILENAME:
		case Resources.SHARK_DEAD_FILENAME: {
			addShark(obj);
			break;
		}
		case Resources.SLIME_LEFT_FILENAME:
		case Resources.SLIME_LEFT2_FILENAME:
		case Resources.SLIME_DEAD_FILENAME: {
			addSlime(obj);
			break;
		}
		default:
			System.out
					.println("ERROR while loading level: don't know how to deal with object "
							+ obj);
			break;
		}
	}

	private void addPlant(MapObject obj) {
		Optional<Program> program = parseProgram(Resources.PLANT_PROGRAM_FILENAME);
		if (program.isPresent() && random.nextBoolean()) {
			getFacade().addPlant(
					getWorld(),
					getFacade().createPlantWithProgram(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.PLANT_SPRITE_LEFT,
									Resources.PLANT_SPRITE_RIGHT },
							program.get()));
		} else {
			getFacade().addPlant(
					getWorld(),
					getFacade().createPlant(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.PLANT_SPRITE_LEFT,
									Resources.PLANT_SPRITE_RIGHT }));
		}
	}

	private void addShark(MapObject obj) {
		Optional<Program> program = parseProgram(Resources.SHARK_PROGRAM_FILENAME);
		if (program.isPresent() && random.nextBoolean()) {
			getFacade().addShark(
					getWorld(),
					getFacade().createSharkWithProgram(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.SHARK_SPRITE_LEFT,
									Resources.SHARK_SPRITE_RIGHT },
							program.get()));
		} else {
			getFacade().addShark(
					getWorld(),
					getFacade().createShark(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.SHARK_SPRITE_LEFT,
									Resources.SHARK_SPRITE_RIGHT }));
		}
	}

	private void addSlime(MapObject obj) {
		Optional<Program> program = parseProgram(Resources.SLIME_PROGRAM_FILENAME);
		int school = obj.getIntAttribute("school").orElse(0);
		if (program.isPresent() && random.nextBoolean()) {
			getFacade().addSlime(
					getWorld(),
					getFacade().createSlimeWithProgram(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.SLIME_SPRITE_LEFT,
									Resources.SLIME_SPRITE_RIGHT },
							getSlimeSchool(school), program.get()));
		} else {
			getFacade().addSlime(
					getWorld(),
					getFacade().createSlime(
							obj.getX(),
							obj.getY(),
							new Sprite[] { Resources.SLIME_SPRITE_LEFT,
									Resources.SLIME_SPRITE_RIGHT },
							getSlimeSchool(school)));
		}
	}

	private java.util.Map<Integer, School> schools = new HashMap<Integer, School>();

	private final AlienInfoProvider2<Buzam> evilTwinProvider;

	private School getSlimeSchool(int nb) {
		return schools.computeIfAbsent(nb, i -> getFacade().createSchool());
	}

	public Map getMap() {
		return map;
	}

	private void setAlien(Mazub alien) {
		if (this.alien != null) {
			throw new IllegalStateException("Mazub already created!");
		}
		this.alien = alien;
	}

	private void setWorld(World world) {
		if (this.world != null) {
			throw new IllegalStateException("World already created!");
		}
		this.world = world;
	}

	Mazub getAlien() {
		return alien;
	}

	private World getWorld() {
		return world;
	}

	@Override
	protected void advanceTime(double dt) {
		getFacade().advanceTime(getWorld(), dt);
		if (getFacade().isGameOver(getWorld())) {
			stop();
		}
	}

	@Override
	protected IActionHandler createActionHandler() {
		return new Part3ActionHandler(this);
	}

	@Override
	public AlienInfoProvider2<Mazub> getAlienInfoProvider() {
		return (AlienInfoProvider2<Mazub>) super.getAlienInfoProvider();
	}

	@Override
	protected AlienInfoProvider2<Mazub> createAlienInfoProvider() {
		return new AlienInfoProvider2<Mazub>() {

			@Override
			public Mazub getAlien() {
				return alien;
			}

			@Override
			public Optional<int[]> getAlienXY() {
				return catchErrorGet(() -> getFacade().getLocation(getAlien()));
			}

			@Override
			public Optional<double[]> getAlienVelocity() {
				return catchErrorGet(() -> getFacade().getVelocity(getAlien()));
			}

			@Override
			public Optional<double[]> getAlienAcceleration() {
				return catchErrorGet(() -> getFacade().getAcceleration(
						getAlien()));
			}

			@Override
			public Optional<int[]> getAlienSize() {
				return catchErrorGet(() -> getFacade().getSize(getAlien()));
			}

			@Override
			public Optional<Sprite> getPlayerSprite() {
				return catchErrorGet(() -> getFacade().getCurrentSprite(
						getAlien()));
			}

			@Override
			public Optional<Integer> getAlienHealth() {
				return catchErrorGet(() -> getFacade().getNbHitPoints(
						getAlien()));
			}

			@Override
			public Optional<Boolean> isImmune() {
				return catchErrorGet(() -> getFacade().isImmune(getAlien()));
			}
		};
	}

	protected AlienInfoProvider2<Buzam> createEvilTwinInfoProvider() {
		return new AlienInfoProvider2<Buzam>() {

			@Override
			public Buzam getAlien() {
				return buzam;
			}

			@Override
			public Optional<int[]> getAlienXY() {
				return catchErrorGet(() -> getFacade().getLocation(getAlien()));
			}

			@Override
			public Optional<double[]> getAlienVelocity() {
				return catchErrorGet(() -> getFacade().getVelocity(getAlien()));
			}

			@Override
			public Optional<double[]> getAlienAcceleration() {
				return catchErrorGet(() -> getFacade().getAcceleration(
						getAlien()));
			}

			@Override
			public Optional<int[]> getAlienSize() {
				return catchErrorGet(() -> getFacade().getSize(getAlien()));
			}

			@Override
			public Optional<Sprite> getPlayerSprite() {
				return catchErrorGet(() -> getFacade().getCurrentSprite(
						getAlien()));
			}

			@Override
			public Optional<Integer> getAlienHealth() {
				return catchErrorGet(() -> getFacade().getNbHitPoints(
						getAlien()));
			}

			@Override
			public Optional<Boolean> isImmune() {
				return Optional.of(false); // don't require this
			}
		};
	}

	@Override
	protected WorldInfoProvider createWorldInfoProvider() {
		return new Part2WorldInfoProvider() {

			@Override
			public Optional<int[]> getVisibleWindow() {
				return catchErrorGet(() -> getFacade().getVisibleWindow(
						getWorld()));
			}

			@Override
			public Optional<int[][]> getTilesIn(int left, int bottom,
					int right, int top) {
				return catchErrorGet(() -> getFacade().getTilePositionsIn(
						world, left, bottom, right, top));
			}

			@Override
			public Optional<TileType> getGeologicalFeature(int bottomLeftX,
					int bottomLeftY) {
				return catchErrorGet(() -> TileType.fromValue(getFacade()
						.getGeologicalFeature(getWorld(), bottomLeftX,
								bottomLeftY)));
			}

			@Override
			public Optional<int[]> getBottomLeftPixelOfTile(int tileX, int tileY) {
				return catchErrorGet(() -> getFacade()
						.getBottomLeftPixelOfTile(world, tileX, tileY));
			}

			@Override
			public Optional<int[]> getWorldSize() {
				return catchErrorGet(() -> getFacade().getWorldSizeInPixels(
						getWorld()));
			}

			@Override
			public int getTileLength() {
				return catchErrorGet(
						() -> getFacade().getTileLength(getWorld())).orElse(
						tileSize);
			}

			@Override
			public Optional<Boolean> isGameOver() {
				return catchErrorGet(() -> getFacade().isGameOver(getWorld()));
			}

			@Override
			public Optional<Boolean> didPlayerWin() {
				return catchErrorGet(() -> getFacade().didPlayerWin(getWorld()));
			}

		};
	}

	@Override
	public Part2WorldInfoProvider getWorldInfoProvider() {
		return (Part2WorldInfoProvider) super.getWorldInfoProvider();
	}

	public String[] getAvailableMaps() {
		return new File("levels").list((file, name) -> name.endsWith(".tmx"));
	}

	public boolean setMapFile(String currentMap) {
		this.currentMap = currentMap;
		return readLevelFile(currentMap);
	}

	protected ObjectInfoProvider createObjectInfoProvider() {
		return new ObjectInfoProvider() {

			@Override
			public Collection<Slime> getSlimes() {
				Collection<Slime> result = getFacade().getSlimes(world);
				if (result == null) {
					result = Collections.emptyList();
				}
				return result;
			}

			@Override
			public Optional<int[]> getLocation(Slime slime) {
				return Optional.of(getFacade().getLocation(slime));
			}

			@Override
			public Collection<Shark> getSharks() {
				Collection<Shark> result = getFacade().getSharks(world);
				if (result == null) {
					result = Collections.emptyList();
				}
				return result;
			}

			@Override
			public Optional<int[]> getLocation(Shark shark) {
				return Optional.of(getFacade().getLocation(shark));
			}

			@Override
			public Collection<Plant> getPlants() {
				Collection<Plant> result = getFacade().getPlants(world);
				if (result == null) {
					result = Collections.emptyList();
				}
				return result;
			}

			@Override
			public Optional<int[]> getLocation(Plant plant) {
				return Optional.of(getFacade().getLocation(plant));
			}

			@Override
			public Optional<ImageSprite> getCurrentSprite(Plant plant) {
				return Optional.of((ImageSprite) getFacade().getCurrentSprite(
						plant));
			}

			@Override
			public Optional<ImageSprite> getCurrentSprite(Shark shark) {
				return Optional.of((ImageSprite) getFacade().getCurrentSprite(
						shark));
			}

			@Override
			public Optional<ImageSprite> getCurrentSprite(Slime slime) {
				return Optional.of((ImageSprite) getFacade().getCurrentSprite(
						slime));
			}

			@Override
			public Optional<School> getSchool(Slime slime) {
				return catchErrorGet(() -> getFacade().getSchool(slime));
			}

		};
	}

	public ObjectInfoProvider getObjectInfoProvider() {
		return objectInfoProvider;
	}

	public String getMapFile() {
		return currentMap;
	}

	public AlienInfoProvider2<Buzam> getEvilTwinInfoProvider() {
		return evilTwinProvider;
	}
}
