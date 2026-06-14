package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla que pregunta al usuario si dispone de tarjeta de familia numerosa.
 * Se muestra DESPUÉS de elegir el destino y ANTES del pago.
 *
 * Si responde que sí, se marca el descuento en el contexto (un 20% que aplica
 * OperationContext.getPrice()); si responde que no, se deja el precio normal.
 * En ambos casos se pasa a la pantalla de pago. También tiene botón de cancelar
 * (R24), que vuelve a la pantalla de bienvenida.
 *
 * Usa el modo de 6 botones (setMode(0)), igual que la bienvenida y el pago.
 */
public class FamilyDiscountScreen extends Screen {

    private static final char YES = 'A';     // botón "Sí"
    private static final char NO = 'B';      // botón "No"
    private static final char CANCEL = 'F';  // botón "Cancelar" (modo 0)

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
                    // Tiene tarjeta: se marca el descuento y se pasa al pago.
                    operationContext.setLargeFamily(true);
                    return new PaymentScreen(kiosk);
                }
                case NO -> {
                    // No tiene tarjeta: precio normal y al pago.
                    operationContext.setLargeFamily(false);
                    return new PaymentScreen(kiosk);
                }
                case CANCEL -> {
                    return new WelcomeScreen(kiosk);
                }
                default -> {
                    // '0' (sin pulsación) o eventos de tarjeta: seguimos esperando.
                }
            }
        }
    }
}
