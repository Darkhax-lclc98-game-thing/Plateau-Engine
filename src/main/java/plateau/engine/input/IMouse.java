package plateau.engine.input;

public interface IMouse {
	void onPress(int eventButton);

	void onMove(int x, int y);
}
