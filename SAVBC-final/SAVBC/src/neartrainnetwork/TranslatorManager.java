package neartrainnetwork;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga, guarda y ofrece los idiomas disponibles (los Translator).
 *
 * El español es el idioma por defecto (no traduce) y siempre está. Además, por
 * cada fichero .txt que haya en la carpeta dictionaries/ se crea un idioma más.
 * Usamos un LinkedHashMap para conservar el orden de inserción (español primero
 * y luego los demás), que es el orden en que se verán en el carrusel.
 *
 * Para añadir un idioma: deja un fichero "Idioma.txt" en dictionaries/. Para
 * cambiar la carpeta o el idioma por defecto, toca las constantes de abajo.
 */
public class TranslatorManager {

    private static final String DICTIONARIES_DIR = "dictionaries";
    private static final String DEFAULT_LANGUAGE = "Español";

    private Translator currentTranslator;                        // idioma activo
    private final Map<String, Translator> translatorMap = new LinkedHashMap<>();

    public TranslatorManager() {
        loadDefaultTranslator(); // primero el español (por defecto)
        loadTranslators();       // luego los idiomas de la carpeta dictionaries/
    }

    public Translator getCurrentTranslator() {
        return currentTranslator;
    }

    /** Cambia el idioma activo por el del nombre indicado (si existe). */
    public void setCurrentTranslator(String language) {
        Translator translator = translatorMap.get(language);
        if (translator != null) {
            currentTranslator = translator;
        }
    }

    /** Lista de idiomas disponibles, en orden (para mostrarlos en el carrusel). */
    public Translator[] getTranslatorArray() {
        return translatorMap.values().toArray(new Translator[0]);
    }

    /** Crea el idioma por defecto (español, sin traducción) y lo deja activo. */
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
            return; // la carpeta aún no existe: solo habrá español
        }
        for (File file : files) {
            String name = file.getName();
            if (!name.toLowerCase().endsWith(".txt")) {
                continue; // ignoramos lo que no sea .txt
            }
            // El nombre del idioma es el del fichero sin la extensión.
            String language = name.substring(0, name.lastIndexOf('.'));
            translatorMap.put(language, new Translator(language, file.getPath()));
        }
    }
}
