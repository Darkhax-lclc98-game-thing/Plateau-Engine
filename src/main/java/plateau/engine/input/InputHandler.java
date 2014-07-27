package plateau.engine.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.HashMap;

public class InputHandler {
	private HashMap<Integer, IKeyboard> keyboardInput = new HashMap<Integer, IKeyboard>();
	private HashMap<Integer, String> nameList = new HashMap<Integer, String>();
	private ArrayList<IMouse> mouseInput = new ArrayList<IMouse>();

	public void registerInput(IKeyboard input) {
		for (int i = 0; i < input.getKey().length; i++) {
			keyboardInput.put(input.getKey()[i], input);
			nameList.put(input.getKey()[i], input.getName()[i]);
		}
	}

	public void registerInput(IMouse input) {
		mouseInput.add(input);
	}

	public void update() {
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			if (keyboardInput.containsKey(key) && nameList.containsKey(key)) {
				IKeyboard input = keyboardInput.get(key);
				input.onPressed(key, nameList.get(key), false, Keyboard.getEventKeyState());
			}
		}

		while (Mouse.next()) {
			int eventButton = Mouse.getEventButton();
			if (eventButton != -1) {
				for (int i = 0; i < mouseInput.size(); i++) {
					mouseInput.get(i).onPress(eventButton);
				}
			} else {
				for (int i = 0; i < mouseInput.size(); i++) {
					mouseInput.get(i).onMove(Mouse.getDX(), Mouse.getDY());
				}
			}
		}
	}
}
