package test;

import org.lwjgl.input.Keyboard;
import plateau.engine.input.IInput;

public class TestInput implements IInput {


	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W};
	}

	@Override
	public String[] getName() {
		return null;
	}

	@Override
	public void onPressed(int key, String getName, boolean isRepeated, boolean onPressed) {

	}

}
