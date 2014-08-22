package plateau.engine.block;

import plateau.engine.renderer.Icon;

public class BlockAir extends Block {

	@Override
	public boolean isSideSolid(int side) {
		return false;
	}

	@Override
	public Icon getIcon(int side) {
		return null;
	}
}
