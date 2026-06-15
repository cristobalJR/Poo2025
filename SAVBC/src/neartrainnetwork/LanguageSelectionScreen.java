package neartrainnetwork;

import java.util.Arrays;
import sienens.SelfOrderKiosk;

/**
 * Pantalla para elegir el idioma. Al elegir uno, lo deja como idioma actual y
 * vuelve a la pantalla de inicio (que ya se verá traducida).
 */
public class LanguageSelectionScreen extends CarruselScreen {

    public LanguageSelectionScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        TranslatorManager translatorManager = operationContext.getTranslatorManager();
        Translator[] languages = translatorManager.getTranslatorArray();

        configureButtons();
        kiosk.setTitle(translator.translate("Seleccione el idioma"));
        configureNavigationButtons(languages.length);
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        index = 0;
        while (true) {
            int end = Math.min(index + PAGE_SIZE, languages.length);
            configureSelectionButtons(Arrays.copyOfRange(languages, index, end));

            char event = kiosk.waitEvent(30);
            if (event == PREVIOUS || event == NEXT) {
                updateIndex(event, languages.length);
            } else if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            } else {
                int selected = event - 'A';
                if (selected >= 0 && selected < end - index) {
                    Translator chosen = languages[index + selected];
                    translatorManager.setCurrentTranslator(chosen.toString());
                    operationContext.setTranslator(chosen);
                    return new WelcomeScreen(kiosk);
                }
            }
        }
    }
}
