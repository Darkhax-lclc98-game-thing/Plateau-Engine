package plateau.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import plateau.engine.PlateauDisplay;
import plateau.engine.registery.GameRegistry;
import plateau.engine.security.PlateauSecurityManager;
import plateau.game.level.Level1;

public class Game extends PlateauDisplay {

	public static void main(String[] args) {
		new Game().createWindowDisplay("Game", 854, 480, null);
	}



	@Override
	public void init() {
		Mouse.setGrabbed(true);

		Keyboard.enableRepeatEvents(true);
		GameRegistry.registerInput(new GameKeyboard());
		GameRegistry.registerInput(new GameMouse());
		GameRegistry.registerWorld(new Level1());

		System.setSecurityManager(new PlateauSecurityManager());
	}

	public static void shutdown() {
		Display.destroy();
		System.exit(0);
	}

	@Override
	public void runLoop() {
		super.runLoop();
	}
}