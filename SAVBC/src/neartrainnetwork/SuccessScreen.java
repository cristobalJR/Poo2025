package neartrainnetwork;

import sienens.SelfOrderKiosk;

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
        kiosk.setDescription("(Pantalla de éxito: pendiente de implementar)");
        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk); // provisional
    }
}
