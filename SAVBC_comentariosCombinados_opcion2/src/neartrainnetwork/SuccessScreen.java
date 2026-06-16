package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de éxito: avisa de que la compra fue bien y pide retirar la tarjeta
 * y el billete. Después vuelve a la pantalla de inicio.
 */
// EXTENSION ->
/**
 * Pantalla de éxito. Avisa de que la compra salió bien y pide retirar la
 * tarjeta y el billete. Tras un momento (o en cuanto el usuario retira la
 * tarjeta o pulsa el botón) vuelve a la bienvenida para el siguiente cliente.
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
        // EXTENSION -> Botón de cancelar (R24: en todas las pantallas). Aquí, al pulsarlo
        // —igual que al retirar la tarjeta o al agotarse el tiempo— se vuelve
        // al inicio. Si prefieres, puedes renombrarlo a "Aceptar"/"Finalizar".
        kiosk.setOption('F', translator.translate("Cancelar"));

        // EXTENSION -> Esperamos un poco (hasta 30 s, o hasta que pulse o retire la tarjeta)
        // y volvemos al inicio.
        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk);
    }
}
