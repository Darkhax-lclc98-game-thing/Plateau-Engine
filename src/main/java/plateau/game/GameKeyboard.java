package plateau.game;

import org.lwjgl.input.Keyboard;
import plateau.engine.Plateau;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.input.IKeyboard;
import plateau.engine.registery.GameRegistry;
import plateau.engine.world.IWorld;

import static java.lang.Math.*;

public class GameKeyboard implements IKeyboard {

	private EntityPlayer entityPlayer;

	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, Keyboard.KEY_ESCAPE, Keyboard.KEY_F5, Keyboard.KEY_P};
	}

	@Override
	public String[] getName() {
		return new String[]{"Forward", "Back", "Left", "Right", "Escape", "3rd_Person", "showWorld"};
	}

	@Override
	public void onPressed(String name, boolean isRepeated, boolean onPressed) {
		EntityPlayer player = getPlayer();
		if (onPressed) {
			float speed = 1.5f / 1f;
			if (name.equalsIgnoreCase("Forward")) {
				move(0, -speed);
			} else if (name.equalsIgnoreCase("Back")) {
				move(0, speed);
			} else if (name.equalsIgnoreCase("Left")) {
				move(-speed, 0);
			} else if (name.equalsIgnoreCase("Right")) {
				move(speed, 0);
			} else if (name.equalsIgnoreCase("Escape")) {
				Game.shutdown();
			} else if (name.equalsIgnoreCase("3rd_Person") && !isRepeated) {
				player.is3rdPerson = !player.is3rdPerson;
			} else if (name.equalsIgnoreCase("showWorld")) {
				IWorld world = GameRegistry.getWorld(player.getWorldID());
				world.showTerrain = !world.showTerrain;
			}
		}
	}

	private void move(float dx, float dz) {
		move(dx, 0, dz, false);
	}

	private void move(float dx, float dy, float dz, boolean yMovement) {
		EntityPlayer player = getPlayer();
		float x = (float) (player.getMotionX() - dx * (float) sin(toRadians(player.getYaw() - 90)) + dz * sin(toRadians(-player.getYaw())));
		float y = (float) player.getMotionY();
		if (yMovement)
			y = (float) (player.getMotionY() + dy * (float) sin(toRadians(player.getPitch() - 90)) + dz * sin(toRadians(player.getPitch())));
		float z = (float) (player.getMotionZ() + dx * (float) cos(toRadians(player.getYaw() - 90)) + dz * cos(toRadians(-player.getYaw())));

		player.setMotionX(x);
		player.setMotionY(y);
		player.setMotionZ(z);
	}

	@Override
	public void onKeyHeldDown(String name, boolean isRepeated, boolean onPressed) {
		this.onPressed(name, isRepeated, onPressed);
	}

	public EntityPlayer getPlayer() {
		if (entityPlayer == null) {
			entityPlayer = Plateau.getPlayer();

		}
		return entityPlayer;
	}
}
