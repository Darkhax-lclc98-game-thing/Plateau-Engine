package plateau.engine.world;

import plateau.engine.block.Block;

public class Chunk {

	private Block[][][] blocks = new Block[16][16][16];

	private int chunkX, chunkZ;

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;

		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks[x].length; y++) {
				for (int z = 0; z < blocks[x][y].length; z++) {
					blocks[x][y][z] = new Block();
				}
			}
		}
	}

	public Block getBlock(int x, int y, int z) {
		if (y < 0 || y > 14 || x < 0 || x > 14 || z < 0 || z > 14) {
			return new Block() {
				@Override
				public boolean isSideSolid(int side) {
					return false;
				}
			};
		}
		return blocks[x][y][z];
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}
}
