package neartrainnetwork;

import sienens.SelfOrderKiosk;

/** Home screen: two buttons, change language or start a purchase. */
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
                    return new StationSelectionScreen(
                            "Seleccione la estación de origen", kiosk);
                }
                default -> {
                    // no event or card event: keep waiting
                }
            }
        }
    }
}
