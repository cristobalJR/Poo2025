package neartrainnetwork;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Loads, stores and provides the available language translators.
 * Spanish is the default (identity); every .txt file inside the "dictionaries"
 * directory adds another language.
 */
public class TranslatorManager {

    private static final String DICTIONARIES_DIR = "dictionaries";
    private static final String DEFAULT_LANGUAGE = "Español";

    private Translator currentTranslator;
    private final Map<String, Translator> translatorMap = new LinkedHashMap<>();

    public TranslatorManager() {
        loadDefaultTranslator();
        loadTranslators();
    }

    public Translator getCurrentTranslator() {
        return currentTranslator;
    }

    public void setCurrentTranslator(String language) {
        Translator translator = translatorMap.get(language);
        if (translator != null) {
            currentTranslator = translator;
        }
    }

    public Translator[] getTranslatorArray() {
        return translatorMap.values().toArray(new Translator[0]);
    }

    private void loadDefaultTranslator() {
        Translator spanish = new Translator(DEFAULT_LANGUAGE);
        translatorMap.put(DEFAULT_LANGUAGE, spanish);
        currentTranslator = spanish;
    }

    private void loadTranslators() {
        File directory = new File(DICTIONARIES_DIR);
        File[] files = directory.listFiles();
        if (files == null) {
            return; // the directory does not exist yet
        }
        for (File file : files) {
            String name = file.getName();
            if (!name.toLowerCase().endsWith(".txt")) {
                continue;
            }
            String language = name.substring(0, name.lastIndexOf('.'));
            translatorMap.put(language, new Translator(language, file.getPath()));
        }
    }
}
