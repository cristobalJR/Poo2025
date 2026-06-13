package neartrainnetwork;

import sienens.SelfOrderKiosk;

public class StationSelectionScreen extends CarruselScreen {

    private final String screenTitle;

    public StationSelectionScreen(String screenTitle, SelfOrderKiosk kiosk) {
        super(kiosk);
        this.screenTitle = screenTitle;
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate(screenTitle));
        kiosk.setDescription("(Selección de estación: pendiente de implementar)");
        kiosk.setOption('F', translator.translate("Cancelar"));
        kiosk.waitEvent(30);
        return new WelcomeScreen(kiosk); // provisional
    }
}
