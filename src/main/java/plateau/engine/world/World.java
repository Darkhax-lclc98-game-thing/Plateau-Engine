package plateau.engine.world;

import java.util.ArrayList;

import plateau.engine.block.Block;
import plateau.engine.registery.GameRegistry;

public class World {

	int x, z;
	private ArrayList<Chunk> chunk = new ArrayList<Chunk>();

	public World() {
		GameRegistry.registerWorld(this);

		for (int i = 0; i < 5; i++)
			chunk.add(new Chunk(x++, z++));
	}

	public Block getBlock(int x, int y, int z) {
		int x1 = x >> 4;
		int z1 = z >> 4;

		for (int i = 0; i < chunk.size(); i++) {
			Chunk chunks = chunk.get(i);

			if (chunks.getChunkX() != x1 || chunks.getChunkZ() != z1) continue;

			return chunks.getBlock(x & 15, y, z & 15);
		}
		// TODO Return empty chunk
		return null;
	}

	public Chunk getChunk(int x, int z) {
		for (int i = 0; i < chunk.size(); i++) {
			Chunk chunks = chunk.get(i);

			if (chunks.getChunkX() == x || chunks.getChunkZ() == z) {
				return chunks;
			}
		}
		// TODO Return empty chunk
		return null;
	}

	public boolean isAirBlock(int x, int y, int z) {
		return this.getBlock(x & 15, y, z & 15) == null;
	}

	public void loadChunk(int x, int z) {

	}

}
