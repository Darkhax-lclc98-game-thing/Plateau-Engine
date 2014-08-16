package plateau.engine.block;

import plateau.engine.renderer.Icon;

import java.util.ArrayList;

public class Block {

	public ArrayList<Block> blockList = new ArrayList<Block>();
	private Icon blockIcon;

	public Icon getIcon(int side) {
		return this.blockIcon;
	}

	public boolean isSideSolid(int side) {
		return true;
	}
}
