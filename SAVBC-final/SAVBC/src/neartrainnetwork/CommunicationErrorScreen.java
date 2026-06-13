package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de error de comunicación con el banco. Mientras se muestra,
 * comprueba cada 5 segundos si la comunicación ha vuelto; cuando vuelve,
 * regresa a la bienvenida. El usuario no tiene que hacer nada salvo esperar
 * (el corte que simula el banco dura un tiempo aleatorio, hasta ~1 minuto).
 *
 * Recibe el banco desde la pantalla de pago para poder preguntarle si la
 * comunicación está disponible (comunicationAvaiable()).
 */
public class CommunicationErrorScreen extends Screen {

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

        // waitEvent(5) hace de "espera 5 segundos" entre comprobaciones (y de
        // paso recoge eventos como retirar la tarjeta, que aquí no usamos).
        while (true) {
            kiosk.waitEvent(5);
            if (bank.comunicationAvaiable()) {
                return new WelcomeScreen(kiosk);
            }
        }
    }
}
