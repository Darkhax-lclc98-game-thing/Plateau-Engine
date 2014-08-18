package plateau.engine.registery;

import plateau.engine.block.Block;
import plateau.engine.world.World;

import java.util.ArrayList;

public class GameRegistry {
	private static ArrayList<World> worldList = new ArrayList<World>();

	public static void registerWorld(World world) {
		if (!worldList.contains(world)) {
			worldList.add(world);
		}
	}
	public static ArrayList<World> getWorldList(){
		return worldList;
	}

	public static void registerBlock(Block block) {
		LanguageRegistry.register(block);
	}
}
