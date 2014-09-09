package plateau.engine;

import org.lwjgl.Sys;

public class GameTimer {
	private static final long numTicksPerSecond = 60;
	private static final long DT_PER_TICK = (1000 / numTicksPerSecond);
	private long timerTicksPerSecond = Sys.getTimerResolution();
	private long lastLoopTime = getTime();
	private long timeSinceLastTick = 0;
	private long numTicks = 0;
	private long startTime = getTime();
	private long endTime;

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
}
