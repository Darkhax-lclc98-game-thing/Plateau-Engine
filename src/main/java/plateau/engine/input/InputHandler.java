package plateau.engine.input;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;

public class InputHandler {
	private HashMap<Integer, IInput> inputList = new HashMap<>();
	private HashMap<Integer, String> nameList = new HashMap<>();

	public void registerInput(IInput input) {
		for (int i = 0; i < input.getKey().length; i++) {
			inputList.put(input.getKey()[i], input);
			nameList.put(input.getKey()[i], input.getName()[i]);
		}
	}

	public void update() {
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (inputList.containsKey(key) && nameList.containsKey(key)) {
				IInput input = inputList.get(key);
				input.onPressed(key, nameList.get(key), false, Keyboard.getEventKeyState());
			}
		}
	}
}
