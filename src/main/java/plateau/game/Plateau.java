package plateau.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import plateau.engine.PlateauDisplay;
import plateau.engine.block.BlockAir;
import plateau.engine.entity.player.EntityPlayer;
import plateau.engine.registery.GameRegistry;
import plateau.engine.world.World;

public class Plateau extends PlateauDisplay {

	private static int width = 854;
	private static int height = 480;

	public static void main(String[] args) {
		new Plateau().createWindowDisplay("Test", width, height, null);
	}

	@Override
	public void init() {
		Mouse.setGrabbed(true);
		Keyboard.enableRepeatEvents(true);
		input.registerInput(new PlateauKeyboard());
		input.registerInput(new PlateauMouse());

		new World();
	}

	@Override
	public void runLoop() {
		super.runLoop();
	}
}