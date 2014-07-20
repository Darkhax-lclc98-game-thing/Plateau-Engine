package test;

import org.lwjgl.input.Keyboard;
import plateau.engine.input.IInput;

public class TestInput implements IInput {


	@Override
	public int[] getKey() {
		return new int[]{Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D};
	}

	@Override
	public String[] getName() {
		return new String[]{"Forward", "Back", "Left", "Right"};
	}

	@Override
	public void onPressed(int key, String name, boolean isRepeated, boolean onPressed) {
		switch (name) {
			
		}
	}

}
