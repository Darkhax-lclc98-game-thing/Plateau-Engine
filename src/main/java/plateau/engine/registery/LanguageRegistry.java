package plateau.engine.registery;

import plateau.engine.block.Block;
import plateau.engine.lang.Language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LanguageRegistry {
	private static LanguageRegistry instance = new LanguageRegistry();

	private Language lang = Language.ENGLISH_US;
	private Properties languageTable = new Properties();

	public static LanguageRegistry instance() {
		return instance;
	}

	public static void register(Block block) {

	}

	public void setLanguage(Language lang) {
		this.lang = lang;
	}

	public void loadLanguageTable() {
		try {
			languageTable.clear();
			languageTable.load(new FileInputStream(new File(lang.getLangCode() + ".lang")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
