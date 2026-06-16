package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pregunta si el usuario tiene tarjeta de familia numerosa antes de pagar.
 * Si dice que sí,se le aplica un 20% de descuento.
 */
public class FamilyDiscountScreen extends Screen {

    private static final char YES = 'A';
    private static final char NO = 'B';
    private static final char CANCEL = 'F';

    public FamilyDiscountScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("¿Dispone de tarjeta de familia numerosa?"));
        kiosk.setDescription(
                translator.translate("Si dispone de tarjeta de familia numerosa se aplicará un 20% de descuento."));
        kiosk.setOption(YES, translator.translate("Sí"));
        kiosk.setOption(NO, translator.translate("No"));
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(30);
            switch (event) {
                case YES -> {
                    operationContext.setLargeFamily(true);
                    return new PaymentScreen(kiosk);
                }
                case NO -> {
                    operationContext.setLargeFamily(false);
                    return new PaymentScreen(kiosk);
                }
                case CANCEL -> {
                    return new WelcomeScreen(kiosk);
                }
                default -> {
                }
            }
        }
    }
}
