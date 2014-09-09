package plateau.engine.renderer;

import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

// TODO Rewrite
public class FontRenderer {
	public final static int
			ALIGN_LEFT = 0,
			ALIGN_RIGHT = 1,
			ALIGN_CENTER = 2;

	private IntObject[] charArray = new IntObject[256];

	private Map<Character, IntObject> customChars = new HashMap<Character, IntObject>();

	private boolean antiAlias;

	private int fontSize = 0;
	private int fontHeight = 0;
	private int fontTextureID;
	private int textureWidth = 512;
	private int textureHeight = 512;
	private int correctL = 5;
	private int correctR = 0;

	private Font font;

	public FontRenderer(Font font, boolean antiAlias, char[] additionalChars) {
		this.font = font;
		this.fontSize = font.getSize() + 3;
		this.antiAlias = antiAlias;

		createSet(additionalChars);

		fontHeight = 1;
	}

	public FontRenderer(Font font, boolean antiAlias) {
		this(font, antiAlias, null);
	}

	public static int loadImage(BufferedImage image) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return -1;
	}

	public static boolean isSupported(String fontname) {
		Font font[] = getFonts();
		for (int i = font.length - 1; i >= 0; i--) {
			if (font[i].getName().equalsIgnoreCase(fontname))
				return true;
		}
		return false;
	}

	public static Font[] getFonts() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	}

	private BufferedImage getFontImage(char ch) {
		// Create a temporary image to extract the character's size
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if (antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);

		FontMetrics fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch) + 8;

		if (charwidth <= 0) {
			charwidth = 7;
		}
		int charheight = fontMetrics.getHeight() + 3;
		if (charheight <= 0) {
			charheight = fontSize;
		}

		// Create another image holding the character we are creating
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if (antiAlias) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		gt.setFont(font);
		gt.setColor(Color.WHITE);
		int charx = 3;
		int chary = 1;
		gt.drawString(String.valueOf(ch), (charx), (chary) + fontMetrics.getAscent());

		return fontImage;
	}

	private void createSet(char[] customCharsArray) {
		if (customCharsArray != null && customCharsArray.length > 0) {
			textureWidth *= 2;
		}

		try {
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			g.setColor(new Color(0, 0, 0, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);

			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;

			int customCharsLength = (customCharsArray != null) ? customCharsArray.length : 0;

			for (int i = 0; i < 256 + customCharsLength; i++) {
				// get 0-255 characters and then custom characters
				char ch = (i < 256) ? (char) i : customCharsArray[i - 256];
				BufferedImage fontImage = getFontImage(ch);

				IntObject newIntObject = new IntObject();

				newIntObject.width = fontImage.getWidth();
				newIntObject.height = fontImage.getHeight();

				if (positionX + newIntObject.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;

				if (newIntObject.height > fontHeight) {
					fontHeight = newIntObject.height;
				}
				if (newIntObject.height > rowHeight) {
					rowHeight = newIntObject.height;
				}

				// Draw it here
				g.drawImage(fontImage, positionX, positionY, null);
				positionX += newIntObject.width;

				if (i < 256) { // standard characters
					charArray[i] = newIntObject;
				} else { // custom characters
					customChars.put(new Character(ch), newIntObject);
				}
			}

			fontTextureID = loadImage(imgTemp);

		} catch (Exception e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}

	private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2) {
		float drawWidth = drawX2 - drawX;
		float drawHeight = drawY2 - drawY;
		float textureSrcX = srcX / textureWidth;
		float textureSrcY = srcY / textureHeight;
		float renderWidth = ((srcX2 - srcX) / textureWidth);
		float renderHeight = ((srcY2 - srcY) / textureHeight);

		glTexCoord2f(textureSrcX, textureSrcY);
		glVertex2f(drawX, drawY);
		glTexCoord2f(textureSrcX, textureSrcY + renderHeight);
		glVertex2f(drawX, drawY + drawHeight);
		glTexCoord2f(textureSrcX + renderWidth, textureSrcY + renderHeight);
		glVertex2f(drawX + drawWidth, drawY + drawHeight);
		glTexCoord2f(textureSrcX + renderWidth, textureSrcY);
		glVertex2f(drawX + drawWidth, drawY);
	}

	public void drawString(float x, float y, String text) {
		drawString(x, y, text, 0, text.length() - 1, 1, 1, ALIGN_LEFT);
	}

	public void drawString(float x, float y, String text, int startIndex, int endIndex, float scaleX, float scaleY, int format) {
		glEnable(GL_TEXTURE_2D);
		IntObject intObject;
		int charCurrent;

		int totalwidth = 0;
		int i = startIndex, d, c;
		float startY = 0;

		switch (format) {
			case ALIGN_RIGHT: {
				d = -1;
				c = correctR;

				while (i < endIndex) {
					if (text.charAt(i) == '\n') startY -= fontHeight;
					i++;
				}
				break;
			}
			case ALIGN_CENTER: {
				for (int l = startIndex; l <= endIndex; l++) {
					charCurrent = text.charAt(l);
					if (charCurrent == '\n') break;

					if (charCurrent < 256) {
						intObject = charArray[charCurrent];
					} else {
						intObject = customChars.get(new Character((char) charCurrent));
					}
					totalwidth += intObject.width - correctL;
				}
				totalwidth /= -2;
			}
			case ALIGN_LEFT:
			default: {
				d = 1;
				c = correctL;
				break;
			}

		}

		glBindTexture(GL_TEXTURE_2D, fontTextureID);
		glBegin(GL_QUADS);

		while (i >= startIndex && i <= endIndex) {

			charCurrent = text.charAt(i);
			if (charCurrent < 256) {
				intObject = charArray[charCurrent];
			} else {
				intObject = customChars.get(new Character((char) charCurrent));
			}

			if (intObject != null) {
				if (d < 0) totalwidth += (intObject.width - c) * d;
				if (charCurrent == '\n') {
					startY -= fontHeight * d;
					totalwidth = 0;
					if (format == ALIGN_CENTER) {
						for (int l = i + 1; l <= endIndex; l++) {
							charCurrent = text.charAt(l);
							if (charCurrent == '\n') break;
							if (charCurrent < 256) {
								intObject = charArray[charCurrent];
							} else {
								intObject = customChars.get(new Character((char) charCurrent));
							}
							totalwidth += intObject.width - correctL;
						}
						totalwidth /= -2;
					}
				} else {
					drawQuad((totalwidth + intObject.width) * scaleX + x, startY * scaleY + y,
							totalwidth * scaleX + x,
							(startY + intObject.height) * scaleY + y, intObject.storedX + intObject.width,
							intObject.storedY + intObject.height, intObject.storedX,
							intObject.storedY);
					if (d > 0) totalwidth += (intObject.width - c) * d;
				}
				i += d;

			}
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_TEXTURE_2D);
	}


	private class IntObject {
		public int width;
		public int height;
		public int storedX;
		public int storedY;
	}
}