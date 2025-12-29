package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la traducción de cadenas de texto para un idioma específico.
 * Carga las traducciones desde un archivo de diccionario.
 */
public class Translator {
    private final Map<String, String> dictionary;
    private final String languageName;
    
    /**
     * Constructor que carga el diccionario desde un archivo.
     * @param fileName Ruta al archivo de diccionario (ej: "dictionaries/es.txt")
     */
    public Translator(String fileName) {
        this.dictionary = new HashMap<>();
        this.languageName = extractLanguageName(fileName);
        loadDictionary(fileName);
    }
    
    /**
     * Extrae el nombre del idioma del nombre del archivo.
     * Ejemplo: "dictionaries/es.txt" -> "Español"
     */
    private String extractLanguageName(String fileName) {
        String name = fileName.substring(fileName.lastIndexOf('/') + 1);
        name = name.replace(".txt", "");
        
        // Mapeo de códigos a nombres
        switch (name.toLowerCase()) {
            case "es": return "Español";
            case "en": return "English";
            default: return name;
        }
    }
    
    /**
     * Carga el diccionario desde el archivo.
     * Formato esperado: "clave española"="traducción"
     */
    private void loadDictionary(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Ignorar líneas vacías y comentarios
                }
                
                // Parsear formato: "clave"="valor"
                int equalsIndex = line.indexOf("=\"");
                if (equalsIndex > 0 && line.startsWith("\"")) {
                    String key = line.substring(1, equalsIndex);
                    String value = line.substring(equalsIndex + 2, line.length() - 1);
                    dictionary.put(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando diccionario " + fileName + ": " + e.getMessage());
        }
    }
    
    /**
     * Traduce una cadena de texto.
     * @param text Texto a traducir
     * @return Traducción si existe, o el texto original si no hay traducción
     */
    public String translate(String text) {
        return dictionary.getOrDefault(text, text);
    }
    
    /**
     * Devuelve el nombre del idioma para mostrar en la interfaz.
     */
    @Override
    public String toString() {
        return languageName;
    }
    
    public String getLanguageName() {
        return languageName;
    }
}