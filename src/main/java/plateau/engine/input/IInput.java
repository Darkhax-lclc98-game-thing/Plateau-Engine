package plateau.engine.input;

public interface IInput {

	int[] getKey();

	String[] getName();

	void onPressed(int key, String getName, boolean isRepeated, boolean onPressed);
}
