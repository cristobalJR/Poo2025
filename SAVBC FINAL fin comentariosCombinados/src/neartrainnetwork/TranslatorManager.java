package neartrainnetwork;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga y guarda los idiomas disponibles. El español es el idioma por defecto y
 * siempre está, los demás se cargan de los ficheros de la carpeta dictionaries/   */
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

    /** Cambia el idioma activo por el indicado (si existe). */
    public void setCurrentTranslator(String language) {
        Translator translator = translatorMap.get(language);
        if (translator != null) {
            currentTranslator = translator;
        }
    }

    public Translator[] getTranslatorArray() {
        return translatorMap.values().toArray(new Translator[0]);
    }

    /** Crea el idioma por defecto (español, sin traducción) y lo deja activo.*/
    private void loadDefaultTranslator() {
        Translator spanish = new Translator(DEFAULT_LANGUAGE);
        translatorMap.put(DEFAULT_LANGUAGE, spanish);
        currentTranslator = spanish;
    }

    /** Crea un idioma por cada fichero .txt de la carpeta dictionaries/. */
    private void loadTranslators() {
        File directory = new File(DICTIONARIES_DIR);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
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
