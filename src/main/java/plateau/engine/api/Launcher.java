package plateau.engine.api;

import java.net.URLClassLoader;

public class Launcher {
	private static Launcher instance = new Launcher();

	public static Launcher instance() {
		return instance;
	}

	public PlateauClassLoader launch(String args[]) {
		PlateauClassLoader loader = new PlateauClassLoader((URLClassLoader) getClass().getClassLoader());

		return loader;
	}
}
