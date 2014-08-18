package plateau.engine.util;

public class Logger {

	public static void log(String msg, LogLevel level) {
		switch (level) {
			case FATAL:
				System.err.println(msg);
				break;
			case ERROR:
				System.err.println(msg);
				break;
			case WARN:
				System.err.println(msg);
				break;
			case INFO:
				System.out.println(msg);
				break;
			case DEBUG:
				System.out.println(msg);
				break;
			case TRACE:
				System.out.println(msg);
				break;
			case ALL:
				System.out.println(msg);
				break;
		}
	}

	public static void log(String msg) {
		log(msg, LogLevel.INFO);
	}

	public enum LogLevel {
		FATAL,
		ERROR,
		WARN,
		INFO,
		DEBUG,
		TRACE,
		ALL,

	}
}
