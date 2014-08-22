package plateau.engine.api;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;

public class PlateauClassLoader extends URLClassLoader {
	private URLClassLoader parent;
	private ArrayList<URL> sources;

	public PlateauClassLoader(URLClassLoader loader) {
		super(loader.getURLs(), null);
		parent = loader;
		sources = new ArrayList<URL>(Arrays.asList(loader.getURLs()));
		Thread.currentThread().setContextClassLoader(this);
	}

	public void addFile(File file) {
		try {
			super.addURL(file.toURI().toURL());
		} catch (Exception e) {
		}
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Implement API
		return super.findClass(name);
	}
}
