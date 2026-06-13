package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de éxito. Avisa de que la compra salió bien y pide retirar la
 * tarjeta y el billete. Tras un momento (o en cuanto el usuario retira la
 * tarjeta) vuelve a la bienvenida para el siguiente cliente.
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

        // Esperamos un poco (hasta 30 s, o hasta que retire la tarjeta) y
        // volvemos al inicio. El usuario no tiene que pulsar nada.
        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk);
    }
}
