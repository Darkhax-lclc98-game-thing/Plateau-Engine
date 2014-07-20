package plateau.engine.input;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;

public class InputHandler {
	private HashMap<Integer, IInput> inputList = new HashMap<>();

	public void registerInput(IInput input) {
		inputList.put(input.getKey(), input);
	}

	public void update() {
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (inputList.containsKey(key)) {
				inputList.get(key).onPressed(false, Keyboard.getEventKeyState());
			}
		}
	}
}
