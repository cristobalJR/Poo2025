package neartrainnetwork;

import sienens.SelfOrderKiosk;

/** Tells the user the operation succeeded and asks to take the card and ticket. */
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

        kiosk.waitEvent(30); // da tiempo a retirar la tarjeta y luego vuelve al inicio
        return new WelcomeScreen(kiosk);
    }
}
