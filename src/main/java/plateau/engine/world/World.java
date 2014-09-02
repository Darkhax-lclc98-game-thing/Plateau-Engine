package plateau.engine.world;

import plateau.engine.block.Block;
import plateau.engine.registery.GameRegistry;

import java.util.ArrayList;

public class World {

	private ArrayList<Chunk> chunk = new ArrayList<Chunk>();

	public World() {
		GameRegistry.registerWorld(this);
	}

	public Block getBlock(int x, int y, int z) {
		int x1 = x >> 4;
		int z1 = z >> 4;

		return getChunk(x1, z1).getBlock(x & 15, y, z & 15);
	}

	public Chunk getChunk(int x, int z) {
		for (int i = 0; i < chunk.size(); i++) {
			Chunk chunks = chunk.get(i);

			if (chunks.getChunkX() == x || chunks.getChunkZ() == z) {
				return chunks;
			}
		}

		return loadChunk(x, z);
	}

	public boolean isAirBlock(int x, int y, int z) {
		return this.getBlock(x, y, z) == Block.air;
	}

	public Chunk loadChunk(int x, int z) {
		for (int i = 0; i < chunk.size(); i++) {
			if (chunk.get(i).getChunkX() == x && chunk.get(i).getChunkZ() == z) {
				return chunk.get(i);
			}
		}
		Chunk chunk1 = new Chunk(x, z);
		chunk.add(chunk1);
		return chunk1;
	}

}
