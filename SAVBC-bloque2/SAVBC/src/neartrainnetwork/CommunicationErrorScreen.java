package neartrainnetwork;

import sienens.SelfOrderKiosk;

public class CommunicationErrorScreen extends Screen {

    public CommunicationErrorScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Problema de comunicación"));
        kiosk.setDescription("(Error de comunicación: pendiente de implementar)");
        kiosk.waitEvent(5);
        return new WelcomeScreen(kiosk); // provisional
    }
}
