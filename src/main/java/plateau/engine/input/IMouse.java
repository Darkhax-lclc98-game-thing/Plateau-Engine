package plateau.engine.input;

public interface IMouse {
	void onPress(int button, int x, int y);

	void onMove(int x, int y);
}
