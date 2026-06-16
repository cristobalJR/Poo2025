package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Crea las piezas de la aplicación y ejecuta el bucle que va mostrando las pantallas.
 */
public class TicketDispenserManager {

    private TrainNetwork trainNetwork;
    private TranslatorManager translatorManager;
    private SelfOrderKiosk kiosk;
    private OperationContext operationContext;

    public TicketDispenserManager() {
    }

    /** Crea los objetos y lanza el bucle:cada pantalla se muestra y devuelve la siguiente. */
    public void start() {
        trainNetwork = new TrainNetwork();
        translatorManager = new TranslatorManager();
        kiosk = new SelfOrderKiosk();
        operationContext = new OperationContext(translatorManager, trainNetwork);

        Screen currentScreen = new WelcomeScreen(kiosk);
        while (currentScreen != null) {
            currentScreen = currentScreen.show(operationContext);
        }
    }
}
