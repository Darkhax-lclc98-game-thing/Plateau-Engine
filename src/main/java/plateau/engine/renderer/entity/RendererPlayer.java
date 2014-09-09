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

		glRotatef(player.getRoll(), 0, 0, 1);

		if (player.is3rdPerson) {
			glTranslatef(0.0F, 0.0F, (-(player.thirdPersonDistanceTemp + (player.thirdPersonDistance - player.thirdPersonDistanceTemp))));
		}

		glRotatef(player.getPitch(), 1, 0, 0);
		glRotatef(player.getYaw(), 0, 1, 0);

		glTranslated(-player.getX(), -player.getY(), -player.getZ());
		glPopAttrib();
	}
}
