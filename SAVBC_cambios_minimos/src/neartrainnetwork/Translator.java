package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Traduce los textos del programa (que están en español) a un idioma.
 *
 * El idioma por defecto (español) usa un diccionario vacío, así que translate()
 * devuelve el texto tal cual (no traduce). Los demás idiomas se cargan de un
 * fichero con una traducción por línea, en dos columnas separadas por un
 * TABULADOR:  texto original  <TAB>  traducción.
 *
 * Para añadir un idioma nuevo no hay que tocar código: basta con crear un
 * fichero "Idioma.txt" en la carpeta dictionaries/ con esos pares.
 */
public class Translator {

    private final String language;                          // nombre del idioma
    private final Map<String, String> dictionary = new HashMap<>(); // original -> traducción

    /** Idioma por defecto / identidad (p. ej. español): sin fichero. */
    public Translator(String language) {
        this.language = language;
    }

    /** Idioma cargado de un fichero de diccionario. */
    public Translator(String language, String fileName) {
        this.language = language;
        load(fileName);
    }

    /** Devuelve la traducción del texto, o el propio texto si no está en el diccionario. */
    public String translate(String text) {
        return dictionary.getOrDefault(text, text);
    }

    @Override
    public String toString() {
        return language; // lo que se ve en el botón al elegir idioma
    }

    /** Carga los pares "original<TAB>traducción" del fichero. */
    private void load(String fileName) {
        // Patrón clásico try/catch/finally con cierre explícito en el finally.
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\t"); // separador: tabulador
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
