package plateau.engine.world;

import plateau.engine.Plateau;
import plateau.engine.PlateauDisplay;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.resource.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public abstract class IWorld {

	public boolean showTerrain;
	private ArrayList<Integer> renderHeightmap = new ArrayList<Integer>();
	private float[][] data;
	private int[][] intArray;
	private EntityPlayer player;
	private int width, height;

	public IWorld() {
		init();
		this.player = Plateau.getPlayer();
	}

	public abstract int getWorldID();

	public abstract int getChunkSize();

	public void init() {
		try {
			BufferedImage image = ImageIO.read(PlateauDisplay.class.getResourceAsStream("../../" + heightmap()));
			width = image.getWidth();
			height = image.getHeight();
			data = new float[width][height];
			intArray = new int[width][width];

			int displayList;
			int xTemp = 0;
			int zTemp = 0;

			for (int x = 0; x < width; x++) {
				for (int z = 0; z < data[x].length; z++) {
					data[x][z] = new Color(image.getRGB(x, z)).getRed();
				}
			}

			for (int i = 0; i < 5; i++) {
				for (int x = 0; x < width; x++) {
					for (int z = 0; z < data[x].length; z++) {
						smooth(x, z);
					}
				}
			}


			for (int i = 0; i < width / getChunkSize() * height / getChunkSize(); i++) {
				displayList = glGenLists(1);
				glNewList(displayList, GL_COMPILE);

				if (zTemp + getChunkSize() > height - 1) {
					zTemp = 0;
					xTemp += getChunkSize();
				}

				if (xTemp > width - getChunkSize() - 1) {
					glEndList();
					break;
				}

				for (int z = zTemp; z < zTemp + getChunkSize(); z++) {
					glBegin(GL_QUAD_STRIP);
					for (int x = xTemp; x < xTemp + getChunkSize() + 1; x++) {
						glTexCoord2f((float) x / image.getWidth(), (float) z / image.getHeight());
						glVertex3f(x, data[x][z], z);
						glVertex3f(x, data[x][z + 1], z + 1);
						intArray[x][z] = displayList;
					}
					glEnd();
				}

				if (zTemp % getChunkSize() == 0) {
					zTemp += getChunkSize();
				}

				glEndList();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String heightmap();

	public abstract String texture();

	public void render() {
		glEnable(GL_TEXTURE_2D);

		ResourceLoader.bindTextures(texture());

		if (showTerrain) {
			for (int x = 0; x < width / getChunkSize(); x++) {
				for (int z = 0; z < height / getChunkSize(); z++) {
					glCallList(intArray[x * getChunkSize()][z * getChunkSize()]);
				}
			}
		} else {
			for (int z = -5; z < 5; z++) {
				for (int x = -5; x < 5; x++) {
					int tempX = getChunkSize() * x;
					int tempZ = getChunkSize() * z;
					if (player.getX() + tempX > 0 && player.getZ() + tempZ > 0 && player.getX() + tempX < width && player.getZ() + tempZ < height) {
						renderHeightmap.add(intArray[(int) (tempX + player.getX())][(int) (tempZ + player.getZ())]);
					}
				}
			}
			for (int chunk : renderHeightmap) {
				glCallList(chunk);
			}
			renderHeightmap.clear();
		}

		glDisable(GL_TEXTURE_2D);
	}

	public float getHeightAt(int posX, int posZ) {
		float median = 0;
		int div = 0;
		try {
			for (int x = -3; x < 3; x++) {
				for (int z = -3; z < 3; z++) {
					int x1 = x + posX;
					int z1 = z + posZ;
					if (x1 > 0 && z1 > 0 && x1 < width && z1 < height) {
						median += data[x1][z1];
						div++;
					}
				}
			}
			return median / div;
		} catch (ArrayIndexOutOfBoundsException e) {
			try {
				return data[posX][posZ];
			} catch (ArrayIndexOutOfBoundsException es) {
				return 0;
			}
		}
	}

	public void smooth(int posX, int posZ) {
		float median = 0;
		int div = 0;
		for (int x = -5; x < 5; x++) {
			for (int z = -5; z < 5; z++) {
				int x1 = x + posX;
				int z1 = z + posZ;
				if (x1 > 0 && z1 > 0 && x1 < width && z1 < height) {
					median += data[x1][z1];
					div++;
				}
			}
		}
		data[posX][posZ] = median / div;
	}
}