package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Traduce los textos del programa a un idioma, usando un diccionario
 * que carga de un fichero. 
 * El idioma por defecto no tiene fichero y
 * devuelve el texto tal cual.
 */
public class Translator {

    private final String language;
    private final Map<String, String> dictionary = new HashMap<>();

    public Translator(String language) {
        this.language = language;
    }

    public Translator(String language, String fileName) {
        this.language = language;
        load(fileName);
    }

    /** Devuelve la traducción del texto, o el mismo texto si no está en el diccionari. */
    public String translate(String text) {
        return dictionary.getOrDefault(text, text);
    }

    @Override
    public String toString() {
        return language;
    }

    /** Carga del fichero los las dos partes texto-original y traducción (separados por un tab). */
    private void load(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    dictionary.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
