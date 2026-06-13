package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Shown when the bank communication fails. Every 5 seconds it checks whether
 * communication is back; when it is, it returns to the welcome screen.
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

        while (true) {
            kiosk.waitEvent(5); // espera 5 segundos entre comprobaciones
            if (bank.comunicationAvaiable()) {
                return new WelcomeScreen(kiosk);
            }
        }
    }
}
