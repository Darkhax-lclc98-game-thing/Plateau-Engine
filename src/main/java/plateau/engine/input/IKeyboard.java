package plateau.engine.input;

public interface IKeyboard {

	/**
	 * The list of keys used by this class
	 *
	 * @return: List of keys used in class on pressed
	 */
	int[] getKey();

	/**
	 * Used to be compared with the list of keys
	 *
	 * @return: List of key names to match keys
	 */
	String[] getName();

	void onPressed(String getName, boolean isRepeated, boolean onPressed);
	
	void onKeyHeldDown(String getName, boolean isRepeated, boolean onPressed);
}
