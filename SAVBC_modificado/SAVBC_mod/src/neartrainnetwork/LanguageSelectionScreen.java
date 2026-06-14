package neartrainnetwork;

import java.util.Arrays;
import sienens.SelfOrderKiosk;

/**
 * Pantalla de selección de idioma. Muestra los idiomas disponibles en el
 * carrusel y, al elegir uno, lo deja como idioma actual y vuelve a la
 * bienvenida (que ya se verá traducida).
 *
 * Hereda de CarruselScreen porque la lista de idiomas podría no caber en una
 * sola página (aunque normalmente quepa; en ese caso no se muestran << ni >>).
 */
public class LanguageSelectionScreen extends CarruselScreen {

    public LanguageSelectionScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        // El gestor de idiomas vive dentro del contexto: de ahí sacamos la
        // lista de idiomas y, más abajo, fijamos el elegido.
        TranslatorManager translatorManager = operationContext.getTranslatorManager();
        Translator[] languages = translatorManager.getTranslatorArray();

        configureButtons();
        kiosk.setTitle(translator.translate("Seleccione el idioma"));
        configureNavigationButtons(languages.length);             // << y >> solo si hay más de una página
        kiosk.setOption(CANCEL, translator.translate("Cancelar")); // botón 'P'

        index = 0; // empezamos en la primera página
        while (true) {
            // Mostramos la página actual. Math.min evita pasarnos del final
            // cuando la última página tiene menos de 15 idiomas.
            int end = Math.min(index + PAGE_SIZE, languages.length);
            configureSelectionButtons(Arrays.copyOfRange(languages, index, end));

            char event = kiosk.waitEvent(30);
            if (event == PREVIOUS || event == NEXT) {
                updateIndex(event, languages.length);   // cambiar de página
            } else if (event == CANCEL) {
                return new WelcomeScreen(kiosk);         // cancelar -> inicio
            } else {
                // ¿Botón de selección ('A'..'O')? La posición dentro de la
                // página es event - 'A' (0 para 'A', 1 para 'B'...).
                int selected = event - 'A';
                if (selected >= 0 && selected < end - index) {
                    Translator chosen = languages[index + selected];
                    // Lo dejamos como idioma actual en el gestor (para futuras
                    // compras) y en el contexto (para que se aplique ya).
                    translatorManager.setCurrentTranslator(chosen.toString());
                    operationContext.setTranslator(chosen);
                    return new WelcomeScreen(kiosk);
                }
                // Si fue '0' o un evento de tarjeta, seguimos esperando.
            }
        }
    }
}
