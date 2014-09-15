package plateau.game;

import plateau.engine.Plateau;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.IMouse;

public class GameMouse implements IMouse {

	@Override
	public void onPress(int eventButton) {

	}

	@Override
	public void onMove(int x, int y) {
		EntityPlayer player = Plateau.getPlayer();

		float mouseDX = x * 0.16f;
		float mouseDY = y * 0.16f;
		if (player.getYaw() + mouseDX >= 360) {
			player.setYaw(player.getYaw() + mouseDX - 360);
		} else if (player.getYaw() + mouseDX < 0) {
			player.setYaw(360 - player.getYaw() + mouseDX);
		} else {
			player.setYaw(player.getYaw() + mouseDX);
		}
		int maxLookDown = -80;
		int maxLookUp = 80;
		if (player.getPitch() - mouseDY >= maxLookDown && player.getPitch() - mouseDY <= maxLookUp) {
			player.setPitch(player.getPitch() + -mouseDY);
		} else if (player.getPitch() - mouseDY < maxLookDown) {
			player.setPitch(maxLookDown);
		} else if (player.getPitch() - mouseDY > maxLookUp) {
			player.setPitch(maxLookUp);
		}
	}
}
