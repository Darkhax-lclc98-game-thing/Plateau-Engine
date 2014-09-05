package plateau.engine.block;

import plateau.engine.renderer.Icon;
import plateau.engine.util.Logger;

import java.util.ArrayList;

public class Block {



	public ArrayList<Block> blockList = new ArrayList<Block>();
	private Icon blockIcon;
	private String blockName;

	public static Block air = new BlockAir();
	public static Block stone = new BlockStone();
	public static Block grass = new BlockGrass();

	public Block() {
		if (blockList.contains(this)) {
			Logger.log("Block is already registered", Logger.LogLevel.ERROR);
		} else {
			blockList.add(this);
		}
	}

	public Icon getIcon(int side) {
		return this.blockIcon;
	}

	public boolean isSideSolid(int side) {
		return true;
	}

	public String getBlockName() {
		return blockName;
	}
}
