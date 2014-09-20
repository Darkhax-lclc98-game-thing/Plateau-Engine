package plateau.engine;

import org.lwjgl.Sys;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.registery.GameRegistry;
import plateau.engine.world.IWorld;

public class GameTimer {
	private static final long numTicksPerSecond = 50;
	private static final long DT_PER_TICK = (1000 / numTicksPerSecond);
	private long timerTicksPerSecond = Sys.getTimerResolution();
	private long lastLoopTime = getTime();
	private long timeSinceLastTick = 0;
	private long numTicks = 0;
	private long startTime = getTime();
	private long endTime;

	int falling;

	private synchronized long getTime() {
		return (Sys.getTime() * 1000) / this.timerTicksPerSecond;
	}

	public boolean tick() {
		long dt = getTime() - lastLoopTime;
		System.out.println(dt);
		lastLoopTime = getTime();
		timeSinceLastTick += dt;

		if (timeSinceLastTick > DT_PER_TICK) {
			numTicks++;
			System.err.println("Ticking. Tick Count: " + numTicks);
			if (numTicks % 100 == 0) {
				endTime = getTime();
				System.err.println(endTime - startTime);
				startTime = endTime;
				//Plateau.shutdown();
			}
			timeSinceLastTick = 0;
			return true;
		}
		return false;
	}

	public void physics() {
		EntityPlayer player = Plateau.getPlayer();
		IWorld world = GameRegistry.getWorld(player.getWorldID());

		if (player.getY() <= world.getHeightAt((int) player.getX(), (int) player.getZ()) + 10) {
			player.setMotionY(0);
			player.setY(world.getHeightAt((int) player.getX(), (int) player.getZ()) + 10);
			falling = 0;
		} else {
			falling++;
			player.setMotionY(-((9.8f / DT_PER_TICK )* (0.001 * falling)));
		}
	}
}
