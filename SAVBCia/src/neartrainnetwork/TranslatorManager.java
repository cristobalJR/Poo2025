package neartrainnetwork;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona los traductores disponibles y el idioma actual.
 * Carga automáticamente todos los archivos de diccionario del directorio.
 */
public class TranslatorManager {
    private Translator currentTranslator;
    private final Map<String, Translator> translatorMap;
    private static final String DICTIONARIES_DIR = "dictionaries";
    private static final String DEFAULT_LANGUAGE = "es";
    
    /**
     * Constructor que carga todos los traductores disponibles.
     */
    public TranslatorManager() {
        this.translatorMap = new HashMap<>();
        loadTranslators();
        loadDefaultTranslator();
    }
    
    /**
     * Carga todos los archivos de diccionario del directorio "dictionaries".
     */
    private void loadTranslators() {
        File dictDir = new File(DICTIONARIES_DIR);
        
        if (!dictDir.exists() || !dictDir.isDirectory()) {
            System.err.println("Directorio de diccionarios no encontrado: " + DICTIONARIES_DIR);
            return;
        }
        
        File[] files = dictDir.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (files != null) {
            for (File file : files) {
                String fileName = file.getPath();
                Translator translator = new Translator(fileName);
                
                // Usar el código de idioma como clave (ej: "es", "en")
                String code = file.getName().replace(".txt", "");
                translatorMap.put(code, translator);
            }
        }
    }
    
    /**
     * Carga el traductor por defecto (español).
     */
    private void loadDefaultTranslator() {
        currentTranslator = translatorMap.get(DEFAULT_LANGUAGE);
        
        if (currentTranslator == null && !translatorMap.isEmpty()) {
            // Si no existe el idioma por defecto, usar el primero disponible
            currentTranslator = translatorMap.values().iterator().next();
        }
    }
    
    /**
     * Establece el traductor actual por código de idioma.
     * @param languageCode Código del idioma (ej: "es", "en")
     */
    public void setCurrentTranslator(String languageCode) {
        Translator translator = translatorMap.get(languageCode);
        if (translator != null) {
            currentTranslator = translator;
        }
    }
    
    /**
     * Establece el traductor actual directamente.
     * @param translator El traductor a establecer
     */
    public void setCurrentTranslator(Translator translator) {
        if (translator != null) {
            currentTranslator = translator;
        }
    }
    
    /**
     * Obtiene el traductor actual.
     */
    public Translator getCurrentTranslator() {
        return currentTranslator;
    }
    
    /**
     * Devuelve un array con todos los traductores disponibles.
     */
    public Translator[] getTranslatorArray() {
        return translatorMap.values().toArray(new Translator[0]);
    }
    
    /**
     * Obtiene un traductor específico por código.
     */
    public Translator getTranslator(String languageCode) {
        return translatorMap.get(languageCode);
    }
}