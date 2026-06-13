package neartrainnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Translates the program's (Spanish) strings into one language.
 * The default language uses an empty dictionary, so translate() returns the
 * original text (identity). Other languages are loaded from a tab-separated
 * file: "original&lt;TAB&gt;translation" per line.
 */
public class Translator {

    private final String language;
    private final Map<String, String> dictionary = new HashMap<>();

    /** Default / identity language (e.g. Spanish): no file. */
    public Translator(String language) {
        this.language = language;
    }

    /** Language loaded from a dictionary file. */
    public Translator(String language, String fileName) {
        this.language = language;
        load(fileName);
    }

    public String translate(String text) {
        return dictionary.getOrDefault(text, text);
    }

    @Override
    public String toString() {
        return language;
    }

    private void load(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    dictionary.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
