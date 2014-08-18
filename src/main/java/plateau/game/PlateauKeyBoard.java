package plateau.game;

import org.lwjgl.input.Keyboard;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.IKeyboard;

import static java.lang.Math.*;

public class PlateauKeyboard implements IKeyboard {

	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D};
	}

	@Override
	public String[] getName() {
		return new String[]{"Forward", "Back", "Left", "Right"};
	}

	@Override
	public void onPressed(String name, boolean isRepeated, boolean onPressed) {
		if (onPressed) {
			float speed = 0.09f;
			if (name.equalsIgnoreCase("Forward")) {
				move(0, -speed);
			} else if (name.equalsIgnoreCase("Back")) {
				move(0, speed);
			} else if (name.equalsIgnoreCase("Left")) {
				move(-speed, 0);
			} else if (name.equalsIgnoreCase("Right")) {
				move(speed, 0);
			}
		}
	}

	private void move(float dx, float dz) {
		EntityPlayer player = Plateau.scene.getPlayer();
		float x = (float) (player.getX() - dx * (float) sin(toRadians(player.getYaw() - 90)) + dz * sin(toRadians(-player.getYaw())));
		float z = (float) (player.getZ() + dx * (float) cos(toRadians(player.getYaw() - 90)) + dz * cos(toRadians(-player.getYaw())));

		player.setX(x);
		player.setZ(z);
	}
}
