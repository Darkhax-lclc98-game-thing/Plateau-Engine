package plateau.engine;


import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPS {
	private long timerTicksPerSecond = Sys.getTimerResolution();
	private long lastLoopTime = getTime();
	private long lastFpsTime;
	private int fps;

	private int currentFps;

	/**
	 * @return current time of pc
	 */
	private synchronized long getTime() {
		return (Sys.getTime() * 1000) / this.timerTicksPerSecond;
	}

	/**
	 * @return the current fps
	 */
	public synchronized int fps() {
		long delta = getTime() - this.lastLoopTime;
		this.lastLoopTime = getTime();
		this.lastFpsTime += delta;
		this.fps++;

		if (this.lastFpsTime >= 1000) {
			this.currentFps = this.fps;
			Display.setTitle("FPS: " + this.currentFps);
			this.lastFpsTime = 0;
			this.fps = 0;
		}
		return currentFps;
	}

}
