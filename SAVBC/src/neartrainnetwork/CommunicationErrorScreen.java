package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de error de comunicación con el banco. Espera a que vuelva la
 * comunicación (o a que el usuario cancele) y vuelve a la pantalla de inicio.
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
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(5);
            if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            }
            if (bank.comunicationAvaiable()) {
                return new WelcomeScreen(kiosk);
            }
        }
    }
}
