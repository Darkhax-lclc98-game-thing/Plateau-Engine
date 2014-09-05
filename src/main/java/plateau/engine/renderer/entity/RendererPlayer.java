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

		if(!player.is3rdPerson) {
			glRotatef(player.getPitch(), 1, 0, 0);
			glRotatef(player.getYaw(), 0, 1, 0);
			glRotatef(player.getRoll(), 0, 0, 1);
		}else {
			glRotatef(player.getRoll(), 0, 0, 1);

			double d7 = (double) (player.thirdPersonDistanceTemp + (player.thirdPersonDistance - player.thirdPersonDistanceTemp));
			float f6 = player.getYaw();
			float f2 = player.getPitch();

			glRotatef(player.getYaw() - f6, 0.0F, 1.0F, 0.0F);
			glTranslatef(0.0F, 0.0F, (float) (-d7));
			glRotatef(f6 - player.getYaw(), 0.0F, 1.0F, 0.0F);
			glRotatef(f2 - player.getPitch(), 1.0F, 0.0F, 0.0F);

			glRotatef(player.getPitch(), 1, 0, 0);
			glRotatef(player.getYaw(), 0, 1, 0);
		}

		glTranslated(-player.getX(), -player.getY(), -player.getZ());
		glPopAttrib();
	}
}
