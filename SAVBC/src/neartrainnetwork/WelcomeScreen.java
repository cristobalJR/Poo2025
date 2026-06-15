package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de inicio: deja elegir entre cambiar el idioma o iniciar la compra.
 * Es la pantalla a la que se vuelve al cancelar o al terminar una compra.
 */
public class WelcomeScreen extends Screen {

    public WelcomeScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Venta de billetes de Cercanías"));
        kiosk.setDescription(translator.translate("Bienvenido. Seleccione una opción."));
        kiosk.setOption('A', translator.translate("Cambiar idioma"));
        kiosk.setOption('B', translator.translate("Iniciar compra"));

        while (true) {
            char event = kiosk.waitEvent(30);
            switch (event) {
                case 'A' -> {
                    return new LanguageSelectionScreen(kiosk);
                }
                case 'B' -> {
                    operationContext.reset();
                    return new StationSelectionScreen(
                            "Seleccione la estación de origen", kiosk);
                }
                default -> {
                }
            }
        }
    }
}
