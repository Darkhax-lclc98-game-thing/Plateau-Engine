package plateau.engine.renderer.entity;

import plateau.engine.entity.player.EntityPlayer;

import static org.lwjgl.opengl.GL11.*;

public class RendererPlayer {
	private EntityPlayer player;

	public RendererPlayer(EntityPlayer player) {
		this.player = player;
	}

	public void update() {
		glLoadIdentity();
		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_MODELVIEW);

		glRotatef(player.getPitch(), 1, 0, 0);
		glRotatef(player.getYaw(), 0, 1, 0);
		glRotatef(player.getRoll(), 0, 0, 1);

		glTranslated(-player.getX(), -player.getY(), -player.getZ());
		glPopAttrib();
	}
}
