package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Builds the elements of the application and runs the control loop that keeps
 * showing the current screen. Each screen decides which screen comes next.
 */
public class TicketDispenserManager {

    private TrainNetwork trainNetwork;
    private TranslatorManager translatorManager;
    private SelfOrderKiosk kiosk;
    private OperationContext operationContext;

    public TicketDispenserManager() {
    }

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
