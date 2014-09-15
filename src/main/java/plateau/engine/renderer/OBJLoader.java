package plateau.engine.renderer;

import org.lwjgl.opengl.GL11;
import plateau.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class OBJLoader {

	public float toppoint = 0; // y+
	public float bottompoint = 0; // y-
	public float leftpoint = 0; // x-
	public float rightpoint = 0; // x+
	public float farpoint = 0; // z-
	public float nearpoint = 0; // z+

	private ArrayList<float[]> vertexsets = new ArrayList<float[]>();
	private ArrayList<float[]> vertexsetsnorms = new ArrayList<float[]>();
	private ArrayList<float[]> vertexsetstexs = new ArrayList<float[]>();

	private ArrayList<int[]> faces = new ArrayList<int[]>();
	private ArrayList<int[]> facestexs = new ArrayList<int[]>();
	private ArrayList<int[]> facesnorms = new ArrayList<int[]>();

	private int objectlist;
	private int numpolys = 0;

	public OBJLoader(String obj, boolean centerit) {
		loadobject(new BufferedReader(new InputStreamReader(Game.class.getResourceAsStream(obj))));
		if (centerit) {
			centerit();
		}
		drawToList();
		numpolys = faces.size();
		cleanup();
	}

	private void cleanup() {
		vertexsets.clear();
		vertexsetsnorms.clear();
		vertexsetstexs.clear();
		faces.clear();
		facestexs.clear();
		facesnorms.clear();
	}

	private void loadobject(BufferedReader br) {
		int lineCount = 0;
		try {

			String line;
			boolean firstpass = true;

			while (((line = br.readLine()) != null)) {
				lineCount++;
				line = line.trim();
				if (line.length() > 0) {
					if (line.charAt(0) == 'v' && line.charAt(1) == ' ') {
						float[] coords = new float[4];
						String[] coordstext = line.split("\\s+");

						for (int i = 1; i < coordstext.length; i++) {
							coords[i - 1] = Float.valueOf(coordstext[i]);
						}

						if (firstpass) {
							rightpoint = coords[0];
							leftpoint = coords[0];
							toppoint = coords[1];
							bottompoint = coords[1];
							nearpoint = coords[2];
							farpoint = coords[2];
							firstpass = false;
						}
						if (coords[0] > rightpoint) {
							rightpoint = coords[0];
						}
						if (coords[0] < leftpoint) {
							leftpoint = coords[0];
						}
						if (coords[1] > toppoint) {
							toppoint = coords[1];
						}
						if (coords[1] < bottompoint) {
							bottompoint = coords[1];
						}
						if (coords[2] > nearpoint) {
							nearpoint = coords[2];
						}
						if (coords[2] < farpoint) {
							farpoint = coords[2];
						}
						vertexsets.add(coords);
					}
					if (line.charAt(0) == 'v' && line.charAt(1) == 't') {
						float[] coords = new float[4];
						String[] coordstext = line.split("\\s+");
						for (int i = 1; i < coordstext.length; i++) {
							coords[i - 1] = Float.valueOf(coordstext[i]);
						}
						vertexsetstexs.add(coords);
					}
					if (line.charAt(0) == 'v' && line.charAt(1) == 'n') {
						float[] coords = new float[4];
						String[] coordstext = line.split("\\s+");
						for (int i = 1; i < coordstext.length; i++) {
							coords[i - 1] = Float.valueOf(coordstext[i]);
						}
						vertexsetsnorms.add(coords);
					}
					if (line.charAt(0) == 'f' && line.charAt(1) == ' ') {
						String[] coordstext = line.split("\\s+");
						int[] v = new int[coordstext.length - 1];
						int[] vt = new int[coordstext.length - 1];
						int[] vn = new int[coordstext.length - 1];

						for (int i = 1; i < coordstext.length; i++) {
							String fixstring = coordstext[i].replaceAll("//", "/0/");
							String[] tempstring = fixstring.split("/");
							v[i - 1] = Integer.valueOf(tempstring[0]);
							if (tempstring.length > 1) {
								vt[i - 1] = Integer.valueOf(tempstring[1]);
							} else {
								vt[i - 1] = 0;
							}
							if (tempstring.length > 2) {
								vn[i - 1] = Integer.valueOf(tempstring[2]);
							} else {
								vn[i - 1] = 0;
							}
						}
						faces.add(v);
						facestexs.add(vt);
						facesnorms.add(vn);
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Failed to read file: " + br.toString());
		} catch (NumberFormatException e) {
			System.out.println("Malformed OBJ (on line " + lineCount + "): " + br.toString() + "\r \r" + e.getMessage());
		}

	}

	private void centerit() {
		float xshift = (rightpoint - leftpoint) / 2f;
		float yshift = (toppoint - bottompoint) / 2f;
		float zshift = (nearpoint - farpoint) / 2f;

		for (int i = 0; i < vertexsets.size(); i++) {
			float[] coords = new float[4];

			coords[0] = vertexsets.get(i)[0] - leftpoint - xshift;
			coords[1] = vertexsets.get(i)[1] - bottompoint - yshift;
			coords[2] = vertexsets.get(i)[2] - farpoint - zshift;

			vertexsets.set(i, coords); // = coords;
		}

	}

	public float getXWidth() {
		return rightpoint - leftpoint;
	}

	public float getYHeight() {
		return toppoint - bottompoint;
	}

	public float getZDepth() {
		return nearpoint - farpoint;
	}

	public int numpolygons() {
		return numpolys;
	}

	public void drawToList() {

		this.objectlist = GL11.glGenLists(1);

		GL11.glNewList(objectlist, GL11.GL_COMPILE);
		for (int i = 0; i < faces.size(); i++) {
			int[] tempfaces = faces.get(i);
			int[] tempfacesnorms = facesnorms.get(i);
			int[] tempfacestexs = facestexs.get(i);

			int polytype;
			if (tempfaces.length == 3) {
				polytype = GL11.GL_TRIANGLES;
			} else if (tempfaces.length == 4) {
				polytype = GL11.GL_QUADS;
			} else {
				polytype = GL11.GL_POLYGON;
			}
			GL11.glBegin(polytype);

			for (int w = 0; w < tempfaces.length; w++) {
				if (tempfacesnorms[w] != 0) {
					float normtempx = vertexsetsnorms.get(tempfacesnorms[w] - 1)[0];
					float normtempy = vertexsetsnorms.get(tempfacesnorms[w] - 1)[1];
					float normtempz = vertexsetsnorms.get(tempfacesnorms[w] - 1)[2];
					GL11.glNormal3f(normtempx, normtempy, normtempz);
				}

				if (tempfacestexs[w] != 0) {
					float textempx = vertexsetstexs.get(tempfacestexs[w] - 1)[0];
					float textempy = vertexsetstexs.get(tempfacestexs[w] - 1)[1];
					float textempz = vertexsetstexs.get(tempfacestexs[w] - 1)[2];
					GL11.glTexCoord3f(textempx, 1f - textempy, textempz);
				}

				float tempx = vertexsets.get(tempfaces[w] - 1)[0];
				float tempy = vertexsets.get(tempfaces[w] - 1)[1];
				float tempz = vertexsets.get(tempfaces[w] - 1)[2];
				GL11.glVertex3f(tempx, tempy, tempz);
			}
			GL11.glEnd();
		}
		GL11.glEndList();
	}

	public void render() {
		GL11.glCallList(objectlist);
	}

}