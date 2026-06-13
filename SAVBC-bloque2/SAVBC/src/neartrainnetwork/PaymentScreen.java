package neartrainnetwork;

import sienens.SelfOrderKiosk;

public class PaymentScreen extends Screen {

    public PaymentScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Pago"));
        kiosk.setDescription("(Pago: pendiente de implementar)");
        kiosk.setOption('F', translator.translate("Cancelar"));
        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk); // provisional
    }
}
