package plateau.game;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import org.lwjgl.input.Keyboard;

import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.IKeyboard;

public class PlateauKeyboard implements IKeyboard {

	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_ESCAPE};
	}

	@Override
	public String[] getName() {
		return new String[]{"Forward", "Back", "Left", "Right", "Escape"};
	}

	@Override
	public void onPressed(String name, boolean isRepeated, boolean onPressed) {
		if (onPressed) {
			float speed = 0.5f / 3f;
			if (name.equalsIgnoreCase("Forward")) {
				move(0, -speed);
			} else if (name.equalsIgnoreCase("Back")) {
				move(0, speed);
			} else if (name.equalsIgnoreCase("Left")) {
				move(-speed, 0);
			} else if (name.equalsIgnoreCase("Right")) {
				move(speed, 0);
			} else if(name.equalsIgnoreCase("Escape"))
				Plateau.shutdown();
		}
	}

	private void move(float dx, float dz){
		move(dx, 0, dz, false);
	}
	
	private void move(float dx, float dy, float dz, boolean yMovement) {
		EntityPlayer player = Plateau.scene.getPlayer();
		float x = (float) (player.getX() - dx * (float) sin(toRadians(player.getYaw() - 90)) + dz * sin(toRadians(-player.getYaw())));
		float y = (float) player.getY();
		if(yMovement)
			y = (float) (player.getY() + dy * (float) sin(toRadians(player.getPitch() - 90)) + dz * sin(toRadians(player.getPitch())));
		float z = (float) (player.getZ() + dx * (float) cos(toRadians(player.getYaw() - 90)) + dz * cos(toRadians(-player.getYaw())));

		player.setX(x);
		player.setY(y);
		player.setZ(z);
	}

	@Override
	public void onKeyHeldDown(String name, boolean isRepeated, boolean onPressed) {
		this.onPressed(name, isRepeated, onPressed);
	}
}
