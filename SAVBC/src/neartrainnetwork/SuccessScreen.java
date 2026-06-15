package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de éxito: avisa de que la compra fue bien y pide retirar la tarjeta
 * y el billete. Después vuelve a la pantalla de inicio.
 */
public class SuccessScreen extends Screen {

    public SuccessScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Operación realizada con éxito"));
        kiosk.setDescription(translator.translate("Retire la tarjeta y el billete."));
        kiosk.setOption('F', translator.translate("Cancelar"));

        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk);
    }
}
