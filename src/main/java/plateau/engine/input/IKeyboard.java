package plateau.engine.input;

public interface IKeyboard {

	int[] getKey();

	String[] getName();

	void onPressed(int key, String getName, boolean isRepeated, boolean onPressed);
}
