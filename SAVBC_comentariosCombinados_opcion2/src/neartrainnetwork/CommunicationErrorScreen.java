package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de error de comunicación con el banco. Espera a que vuelva la
 * comunicación (o a que el usuario cancele) y vuelve a la pantalla de inicio.
 */
// EXTENSION ->
/**
 * Pantalla de error de comunicación con el banco. Mientras se muestra,
 * comprueba cada 5 segundos si la comunicación ha vuelto; cuando vuelve,
 * regresa a la bienvenida. También se puede volver pulsando Cancelar, sin
 * esperar a la reconexión. El corte que simula el banco dura un tiempo
 * aleatorio (hasta ~1 minuto).
 *
 * Recibe el banco desde la pantalla de pago para poder preguntarle si la
 * comunicación está disponible (comunicationAvaiable()).
 */
public class CommunicationErrorScreen extends Screen {

    private static final char CANCEL = 'F';

    private final UrjcBankServer bank;

    public CommunicationErrorScreen(SelfOrderKiosk kiosk, UrjcBankServer bank) {
        super(kiosk);
        this.bank = bank;
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Problema de comunicación"));
        kiosk.setDescription(
                translator.translate("Retire su tarjeta. Recuperando comunicación, espere..."));
        // EXTENSION -> Botón de cancelar (R24): permite volver al inicio sin esperar a que
        // se recupere la comunicación.
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(5);   // EXTENSION -> espera 5 s entre comprobaciones
            if (event == CANCEL) {             // EXTENSION -> el usuario decide cancelar
                return new WelcomeScreen(kiosk);
            }
            if (bank.comunicationAvaiable()) { // EXTENSION -> ha vuelto la comunicación
                return new WelcomeScreen(kiosk);
            }
        }
    }
}
