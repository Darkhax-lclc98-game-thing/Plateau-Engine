package plateau.engine;


import org.lwjgl.Sys;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.renderer.FontRenderer;

import java.text.DecimalFormat;

public class DebugTitleInfo {
	private long timerTicksPerSecond = Sys.getTimerResolution();
	private long lastLoopTime = getTime();
	private long lastFpsTime;
	private long maxMemory = Runtime.getRuntime().maxMemory() / 1048576;
	private long freeMemory;
	private long usedMemory;
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
	public synchronized void fps() {
		long delta = getTime() - this.lastLoopTime;
		this.lastLoopTime = getTime();
		this.lastFpsTime += delta;
		this.fps++;

		if (this.lastFpsTime >= 1000) {
			this.currentFps = this.fps;
			this.lastFpsTime = 0;
			this.fps = 0;
		}
	}

	public synchronized void memory() {
		this.freeMemory = Runtime.getRuntime().freeMemory() / 1048576;
		this.usedMemory = (this.maxMemory - this.freeMemory);
	}

	public synchronized void updateDebugTitle(FontRenderer renderer) {
		EntityPlayer player = Plateau.getPlayer();
		fps();
		memory();
		renderer.drawString(10, 10, "FPS: " + this.currentFps + " Memory: " + this.usedMemory + "MB/" + this.maxMemory + "MB");
		DecimalFormat df = new DecimalFormat("#.##");
		renderer.drawString(100, 100, "X: " + df.format(player.getX()) + " Y" + df.format(player.getY()) + " Z:" + df.format(player.getZ()));
		//Display.setTitle("FPS: " + this.currentFps + " Memory: " + this.usedMemory + "MB/" + this.maxMemory + "MB");
	}
}
