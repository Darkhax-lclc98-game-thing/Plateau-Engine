package plateau.engine.resource;

import org.lwjgl.BufferUtils;
import plateau.engine.PlateauDisplay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class ResourceLoader {
	private static HashMap<String, Integer> textureIndex = new HashMap<String, Integer>();

	public static ByteBuffer[] getIcon(String file) {
		try {
			BufferedImage bufferedimage = ImageIO.read(new File(file));

			int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
			ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
			int[] aint1 = aint;
			int i = aint.length;

			for (int j = 0; j < i; ++j) {
				int k = aint1[j];
				bytebuffer.putInt(k << 8 | k >> 24 & 255);
			}

			bytebuffer.flip();
			return new ByteBuffer[]{bytebuffer};
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void bindTextures(String path) {
		if (path == null || path.equals("")) {
			glBindTexture(GL_TEXTURE_2D, 0);
		} else {
			int textureID;
			if (textureIndex.containsKey(path)) {
				textureID = textureIndex.get(path);
			} else {
				textureID = bindTexture(path);
				textureIndex.put(path, textureID);
			}
			glBindTexture(GL_TEXTURE_2D, textureID);
		}
	}

	private static int bindTexture(String file) {
		try {
			BufferedImage image = ImageIO.read(PlateauDisplay.class.getResourceAsStream("../../" + file));
			int[] pixels = new int[image.getWidth() * image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF));
					buffer.put((byte) ((pixel >> 8) & 0xFF));
					buffer.put((byte) (pixel & 0xFF));
					buffer.put((byte) ((pixel >> 24) & 0xFF));
				}
			}

			buffer.flip();

			int textureID = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, textureID);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			return textureID;
		} catch (IOException e) {
			return -1;
		}
	}
}
