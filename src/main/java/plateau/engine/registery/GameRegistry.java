package plateau.engine.registery;

import plateau.engine.Plateau;
import plateau.engine.block.Block;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.IKeyboard;
import plateau.engine.input.IMouse;
import plateau.engine.world.IWorld;

import java.util.ArrayList;

public class GameRegistry {
	private static ArrayList<IWorld> worldList = new ArrayList<IWorld>();

	public static void registerWorld(IWorld world) {
		if (!worldList.contains(world)) {
			worldList.add(world);
		}
	}

	public static IWorld getWorld(int id) {
		for (IWorld world : worldList) {
			if (world.getWorldID() == id) {
				return world;
			}
		}
		EntityPlayer player = Plateau.getPlayer();
		player.setWorldID(0);
		return getWorld(player.getWorldID());
	}

	public static void registerBlock(Block block) {
		LanguageRegistry.register(block);
	}

	public static void registerInput(IKeyboard keyboard) {
		Plateau.getInput().registerInput(keyboard);
	}

	public static void registerInput(IMouse mouse) {
		Plateau.getInput().registerInput(mouse);
	}
}
