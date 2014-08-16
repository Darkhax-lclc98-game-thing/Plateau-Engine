package plateau.engine.world;

import plateau.engine.PlateauDisplay;
import plateau.engine.block.Block;

public class World {

	private Chunk[][] chunk = new Chunk[1][1];

	public World() {
		PlateauDisplay.scene.registerWorld(this);

		for (int x = 0; x < chunk.length; x++) {
			for (int z = 0; z < chunk[x].length; z++) {
				chunk[x][z] = new Chunk();
			}
		}
	}

	public Block getBlock(int x, int y, int z) {
		int x1 = x >> 4;
		int z1 = z >> 4;
		Chunk chunks = null;
		if (x1 >= 0 && z1 >= 0 && x1 < chunk.length && z1 < chunk[x1].length){
			chunks = this.getChunk(x >> 4, z >> 4);
		}else {
			chunks = this.getChunk(0, 0);
		}
		return chunks.getBlock(x & 15, y, z & 15);
	}

	private Chunk getChunk(int x, int z) {
		return chunk[x][z];
	}

	public boolean isAirBlock(int x, int y, int z) {
		Chunk chunk = this.getChunk(x >> 4, z >> 4);
		return chunk.getBlock(x & 15, y, z & 15) == null;
	}
}
