package plateau.engine.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InputHandler {

	public static final int NUM_KEYCODES = 256;
	public static final int NUM_MOUSE_BUTTONS = 5;

	private HashMap<Integer, Set<IKeyboard>> keyboardInput = new HashMap<Integer, Set<IKeyboard>>();
	private HashMap<Integer, String> nameList = new HashMap<Integer, String>();
	private ArrayList<IMouse> mouseInput = new ArrayList<IMouse>();

	private ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private ArrayList<Integer> upKeys = new ArrayList<Integer>();

	private ArrayList<Integer> mouse = new ArrayList<Integer>();
	private ArrayList<Integer> mouseDown = new ArrayList<Integer>();
	private ArrayList<Integer> mouseUp = new ArrayList<Integer>();

	public void registerInput(IKeyboard input) {
		for (int i = 0; i < input.getKey().length; i++) {
			if (keyboardInput.get(input.getKey()[i]) == null)
				keyboardInput.put(input.getKey()[i], new HashSet<IKeyboard>());

			keyboardInput.get(input.getKey()[i]).add(input);
			nameList.put(input.getKey()[i], input.getName()[i]);
		}
	}

	public void registerInput(IMouse input) {
		if (!mouseInput.contains(input))
			mouseInput.add(input);
	}

	public void update() {
		mouseUp.clear();

		for (int i = 0; i < NUM_MOUSE_BUTTONS; i++) {
			if (!isMousePressed(i) && mouse.contains(i))
				mouseUp.add(i);
		}

		mouseDown.clear();
		for (int i = 0; i < NUM_MOUSE_BUTTONS; i++) {
			if (isMousePressed(i) && !mouse.contains(i))
				mouseDown.add(i);
		}

		mouse.clear();
		for (int i = 0; i < NUM_MOUSE_BUTTONS; i++) {
			if (isMousePressed(i))
				mouse.add(i);
		}

		upKeys.clear();

		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (!getKey(i) && keys.contains(i))
				upKeys.add(i);
		}

		downKeys.clear();
		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i) && !keys.contains(i))
				downKeys.add(i);
		}

		keys.clear();
		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i))
				keys.add(i);
		}

		doPressedKey();
		doKeyDown();
		doKeyUp();

		doMouseInput();
	}

	private void doMouseInput() {
		for (int mouseKey = 0; mouseKey < NUM_MOUSE_BUTTONS; mouseKey++) {
			for (IMouse mouseInp : mouseInput) {
				mouseInp.onMove(Mouse.getDX(), Mouse.getDY());
				if (mouse.contains(mouseKey)) {
					mouseInp.onPress(mouseKey, Mouse.getX(), Mouse.getY());
				}
			}
		}
	}

	private void doKeyUp() {
		for (int key = 0; key < NUM_KEYCODES; key++) {
			if (keys.contains(key) && downKeys.contains(key) && keyboardInput.containsKey(key) && nameList.containsKey(key)) {
				Set<IKeyboard> keyboard = keyboardInput.get(key);
				for (IKeyboard keyb : keyboard) {
					keyb.onPressed(nameList.get(key), false, true);
				}
			}
		}
	}

	private void doPressedKey() {
		for (int key = 0; key < NUM_KEYCODES; key++) {
			if (keys.contains(key) && keyboardInput.containsKey(key) && nameList.containsKey(key)) {
				Set<IKeyboard> keyboard = keyboardInput.get(key);
				for (IKeyboard keyb : keyboard) {
					keyb.onKeyHeldDown(nameList.get(key), false, true);
				}
			}
		}
	}

	private void doKeyDown() {
		for (int key = 0; key < NUM_KEYCODES; key++) {
			if (keys.contains(key) && downKeys.contains(key) && keyboardInput.containsKey(key) && nameList.containsKey(key)) {
				Set<IKeyboard> keyboard = keyboardInput.get(key);
				for (IKeyboard keyb : keyboard) {
					keyb.onPressed(nameList.get(key), false, true);
				}
			}
		}
	}

	public boolean getKey(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	public boolean getKeyDown(int keyCode) {
		return downKeys.contains(keyCode);
	}

	public boolean getKeyUp(int keyCode) {
		return upKeys.contains(keyCode);
	}

	public boolean isMousePressed(int buttonCode) {
		return Mouse.isButtonDown(buttonCode);
	}

	public boolean isMouseDown(int buttonCode) {
		return mouseDown.contains(buttonCode);
	}

	public boolean isMouseUp(int buttonCode) {
		return mouseUp.contains(buttonCode);
	}
}
