package plateau.engine.lang;

public enum Language {
	ENGLISH_US("en_US");

	private String langCode;

	Language(String langCode) {
		this.langCode = langCode;
	}

	public String getLangCode() {
		return this.langCode;
	}
}
